package com.RuthlessNail.ornithophobia.entity.custom;

import com.RuthlessNail.ornithophobia.sound.ModSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.BodyRotationControl;
import net.minecraft.world.entity.ai.control.LookControl;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.Cat;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.phys.Vec3;
import org.antlr.v4.codegen.model.Loop;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.builder.ILoopType;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.util.GeckoLibUtil;

import java.util.Comparator;
import java.util.EnumSet;
import java.util.List;

import static software.bernie.geckolib3.core.builder.ILoopType.EDefaultLoopTypes.LOOP;

public class SparrowEntity extends FlyingMob implements IAnimatable, Enemy {
    private AnimationFactory factory = GeckoLibUtil.createFactory(this);

    Vec3 moveTargetPoint = Vec3.ZERO;
    BlockPos anchorPoint = BlockPos.ZERO;
    SparrowEntity.AttackPhase attackPhase = SparrowEntity.AttackPhase.CIRCLE;

    public SparrowEntity(EntityType<? extends SparrowEntity> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        this.xpReward = 5;
        this.moveControl = new SparrowEntity.SparrowMoveControl(this);
        this.lookControl = new SparrowEntity.SparrowLookControl(this);
    }

    public static AttributeSupplier setAttributes() {
        return Animal.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 6.00)
                .add(Attributes.ATTACK_DAMAGE, 6.0f)
                .add(Attributes.ATTACK_SPEED, 2.0f)
                .add(Attributes.MOVEMENT_SPEED, 1f).build();
    }

    @Override
    public boolean canAttackType(EntityType<?> pType) {
        return true;
    }

    static enum AttackPhase {
        CIRCLE,
        SWOOP;
    }

    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
        if (event.isMoving()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.sparrow.fly", LOOP));
            return PlayState.CONTINUE;
        }

        event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.sparrow.idle", LOOP));
        return PlayState.CONTINUE;
    }

    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController(this, "controller",
                0, this::predicate));
    }

    @Override
    public AnimationFactory getFactory() {
        return this.factory;
    }

    @Override
    protected BodyRotationControl createBodyControl() {
        return new SparrowEntity.SparrowBodyRotationControl(this);
    }



    protected void registerGoals() {
        this.goalSelector.addGoal(1, new SparrowEntity.SparrowAttackStrategyGoal());
        this.goalSelector.addGoal(2, new SparrowEntity.SparrowSweepAttackGoal());
        this.goalSelector.addGoal(3, new SparrowEntity.SparrowCircleAroundAnchorGoal());
        this.targetSelector.addGoal(1, new SparrowEntity.SparrowAttackPlayerTargetGoal());
    }


    class SparrowAttackPlayerTargetGoal extends Goal {
        private final TargetingConditions attackTargeting = TargetingConditions.forCombat().range(64.0D);
        private int nextScanTick = reducedTickDelay(20);

        /**
         * Returns whether execution should begin. You can also read and cache any state necessary for execution in this
         * method as well.
         */
        public boolean canUse() {
            if (this.nextScanTick > 0) {
                --this.nextScanTick;
                return false;
            } else {
                this.nextScanTick = reducedTickDelay(60);
                List<Player> list = SparrowEntity.this.level.getNearbyPlayers(this.attackTargeting, SparrowEntity.this, SparrowEntity.this.getBoundingBox().inflate(16.0D, 64.0D, 16.0D));
                if (!list.isEmpty()) {
                    list.sort(Comparator.<Entity, Double>comparing(Entity::getY).reversed());

                    for (Player player : list) {
                        if (SparrowEntity.this.canAttack(player, TargetingConditions.DEFAULT)) {
                            SparrowEntity.this.setTarget(player);
                            return true;
                        }
                    }
                }

                return false;
            }
        }
        /**
         * Returns whether an in-progress EntityAIBase should continue executing
         */
        public boolean canContinueToUse() {
            LivingEntity livingentity = SparrowEntity.this.getTarget();
            return livingentity != null ? SparrowEntity.this.canAttack(livingentity, TargetingConditions.DEFAULT) : false;
        }
    }


    class SparrowAttackStrategyGoal extends Goal {
        private int nextSweepTick;

        /**
         * Returns whether execution should begin. You can also read and cache any state necessary for execution in this
         * method as well.
         */
        public boolean canUse() {
            LivingEntity livingentity = SparrowEntity.this.getTarget();
            return livingentity != null ? SparrowEntity.this.canAttack(livingentity, TargetingConditions.DEFAULT) : false;
        }

        /**
         * Execute a one shot task or start executing a continuous task
         */
        public void start() {
            this.nextSweepTick = this.adjustedTickDelay(10);
            SparrowEntity.this.attackPhase = SparrowEntity.AttackPhase.CIRCLE;
            this.setAnchorAboveTarget();
        }

        /**
         * Reset the task's internal state. Called when this task is interrupted by another one
         */
        public void stop() {
            SparrowEntity.this.anchorPoint = SparrowEntity.this.level.getHeightmapPos(Heightmap.Types.MOTION_BLOCKING, SparrowEntity.this.anchorPoint).above(10 + SparrowEntity.this.random.nextInt(20));
        }

        /**
         * Keep ticking a continuous task that has already been started
         */
        public void tick() {
            if (SparrowEntity.this.attackPhase == SparrowEntity.AttackPhase.CIRCLE) {
                --this.nextSweepTick;
                if (this.nextSweepTick <= 0) {
                    SparrowEntity.this.attackPhase = SparrowEntity.AttackPhase.SWOOP;
                    this.setAnchorAboveTarget();
                    this.nextSweepTick = this.adjustedTickDelay((8 + SparrowEntity.this.random.nextInt(4)) * 20);
                    SparrowEntity.this.playSound(ModSounds.SPARROW_SWOOP.get(), 10.0F, 0.95F + SparrowEntity.this.random.nextFloat() * 0.1F);
                }
            }

        }

        private void setAnchorAboveTarget() {
            SparrowEntity.this.anchorPoint = SparrowEntity.this.getTarget().blockPosition().above(20 + SparrowEntity.this.random.nextInt(20));
            if (SparrowEntity.this.anchorPoint.getY() < SparrowEntity.this.level.getSeaLevel()) {
                SparrowEntity.this.anchorPoint = new BlockPos(SparrowEntity.this.anchorPoint.getX(), SparrowEntity.this.level.getSeaLevel() + 1, SparrowEntity.this.anchorPoint.getZ());
            }

        }
    }

    class SparrowCircleAroundAnchorGoal extends SparrowEntity.SparrowMoveTargetGoal {
        private float angle;
        private float distance;
        private float height;
        private float clockwise;

        /**
         * Returns whether execution should begin. You can also read and cache any state necessary for execution in this
         * method as well.
         */
        public boolean canUse() {
            return SparrowEntity.this.getTarget() == null || SparrowEntity.this.attackPhase == SparrowEntity.AttackPhase.CIRCLE;
        }

        /**
         * Execute a one shot task or start executing a continuous task
         */
        public void start() {
            this.distance = 5.0F + SparrowEntity.this.random.nextFloat() * 10.0F;
            this.height = -4.0F + SparrowEntity.this.random.nextFloat() * 9.0F;
            this.clockwise = SparrowEntity.this.random.nextBoolean() ? 1.0F : -1.0F;
            this.selectNext();
        }

        /**
         * Keep ticking a continuous task that has already been started
         */
        public void tick() {
            if (SparrowEntity.this.random.nextInt(this.adjustedTickDelay(350)) == 0) {
                this.height = -4.0F + SparrowEntity.this.random.nextFloat() * 9.0F;
            }

            if (SparrowEntity.this.random.nextInt(this.adjustedTickDelay(250)) == 0) {
                ++this.distance;
                if (this.distance > 15.0F) {
                    this.distance = 5.0F;
                    this.clockwise = -this.clockwise;
                }
            }

            if (SparrowEntity.this.random.nextInt(this.adjustedTickDelay(450)) == 0) {
                this.angle = SparrowEntity.this.random.nextFloat() * 2.0F * (float)Math.PI;
                this.selectNext();
            }

            if (this.touchingTarget()) {
                this.selectNext();
            }

            if (SparrowEntity.this.moveTargetPoint.y < SparrowEntity.this.getY() && !SparrowEntity.this.level.isEmptyBlock(SparrowEntity.this.blockPosition().below(1))) {
                this.height = Math.max(1.0F, this.height);
                this.selectNext();
            }

            if (SparrowEntity.this.moveTargetPoint.y > SparrowEntity.this.getY() && !SparrowEntity.this.level.isEmptyBlock(SparrowEntity.this.blockPosition().above(1))) {
                this.height = Math.min(-1.0F, this.height);
                this.selectNext();
            }

        }

        private void selectNext() {
            if (BlockPos.ZERO.equals(SparrowEntity.this.anchorPoint)) {
                SparrowEntity.this.anchorPoint = SparrowEntity.this.blockPosition();
            }

            this.angle += this.clockwise * 15.0F * ((float)Math.PI / 180F);
            SparrowEntity.this.moveTargetPoint = Vec3.atLowerCornerOf(SparrowEntity.this.anchorPoint).add((double)(this.distance * Mth.cos(this.angle)), (double)(-4.0F + this.height), (double)(this.distance * Mth.sin(this.angle)));
        }
    }


    class SparrowLookControl extends LookControl {
        public SparrowLookControl(Mob pMob) {
            super(pMob);
        }

        /**
         * Updates look
         */
        public void tick() {
        }
    }

    class SparrowMoveControl extends MoveControl {
        private float speed = 0.1F;

        public SparrowMoveControl(Mob pMob) {
            super(pMob);
        }

        public void tick() {
            if (SparrowEntity.this.horizontalCollision) {
                SparrowEntity.this.setYRot(SparrowEntity.this.getYRot() + 180.0F);
                this.speed = 0.1F;
            }

            double d0 = SparrowEntity.this.moveTargetPoint.x - SparrowEntity.this.getX();
            double d1 = SparrowEntity.this.moveTargetPoint.y - SparrowEntity.this.getY();
            double d2 = SparrowEntity.this.moveTargetPoint.z - SparrowEntity.this.getZ();
            double d3 = Math.sqrt(d0 * d0 + d2 * d2);
            if (Math.abs(d3) > (double)1.0E-5F) {
                double d4 = 1.0D - Math.abs(d1 * (double)0.7F) / d3;
                d0 *= d4;
                d2 *= d4;
                d3 = Math.sqrt(d0 * d0 + d2 * d2);
                double d5 = Math.sqrt(d0 * d0 + d2 * d2 + d1 * d1);
                float f = SparrowEntity.this.getYRot();
                float f1 = (float)Mth.atan2(d2, d0);
                float f2 = Mth.wrapDegrees(SparrowEntity.this.getYRot() + 90.0F);
                float f3 = Mth.wrapDegrees(f1 * (180F / (float)Math.PI));
                SparrowEntity.this.setYRot(Mth.approachDegrees(f2, f3, 4.0F) - 90.0F);
                SparrowEntity.this.yBodyRot = SparrowEntity.this.getYRot();
                if (Mth.degreesDifferenceAbs(f, SparrowEntity.this.getYRot()) < 3.0F) {
                    this.speed = Mth.approach(this.speed, 1.8F, 0.005F * (1.8F / this.speed));
                } else {
                    this.speed = Mth.approach(this.speed, 0.2F, 0.025F);
                }

                float f4 = (float)(-(Mth.atan2(-d1, d3) * (double)(180F / (float)Math.PI)));
                SparrowEntity.this.setXRot(f4);
                float f5 = SparrowEntity.this.getYRot() + 90.0F;
                double d6 = (double)(this.speed * Mth.cos(f5 * ((float)Math.PI / 180F))) * Math.abs(d0 / d5);
                double d7 = (double)(this.speed * Mth.sin(f5 * ((float)Math.PI / 180F))) * Math.abs(d2 / d5);
                double d8 = (double)(this.speed * Mth.sin(f4 * ((float)Math.PI / 180F))) * Math.abs(d1 / d5);
                Vec3 vec3 = SparrowEntity.this.getDeltaMovement();
                SparrowEntity.this.setDeltaMovement(vec3.add((new Vec3(d6, d8, d7)).subtract(vec3).scale(0.2D)));
            }

        }
    }


    class SparrowBodyRotationControl extends BodyRotationControl {
        public SparrowBodyRotationControl(Mob pMob) {
            super(pMob);
        }

        /**
         * Update the Head and Body rendenring angles
         */
        public void clientTick() {
            SparrowEntity.this.yHeadRot = SparrowEntity.this.yBodyRot;
            SparrowEntity.this.yBodyRot = SparrowEntity.this.getYRot();
        }
    }

    abstract class SparrowMoveTargetGoal extends Goal {
        public SparrowMoveTargetGoal() {
            this.setFlags(EnumSet.of(Goal.Flag.MOVE));
        }

        protected boolean touchingTarget() {
            return SparrowEntity.this.moveTargetPoint.distanceToSqr(SparrowEntity.this.getX(), SparrowEntity.this.getY(),
                    SparrowEntity.this.getZ()) < 4.0D;
        }
    }

    public SoundSource getSoundSource() {
        return SoundSource.HOSTILE;
    }

    protected SoundEvent getAmbientSound() {
        return ModSounds.SPARROW_AMBIENT.get();
    }

    protected SoundEvent getHurtSound(DamageSource pDamageSource) {
        return ModSounds.SPARROW_HURT.get();
    }

    protected SoundEvent getDeathSound() {
        return ModSounds.SPARROW_DEATH.get();
    }


    class SparrowSweepAttackGoal extends SparrowEntity.SparrowMoveTargetGoal {
        private static final int CAT_SEARCH_TICK_DELAY = 20;
        private boolean isScaredOfCat;
        private int catSearchTick;

        /**
         * Returns whether execution should begin. You can also read and cache any state necessary for execution in this
         * method as well.
         */
        public boolean canUse() {
            return SparrowEntity.this.getTarget() != null && SparrowEntity.this.attackPhase == SparrowEntity.AttackPhase.SWOOP;
        }

        /**
         * Returns whether an in-progress EntityAIBase should continue executing
         */
        public boolean canContinueToUse() {
            LivingEntity livingentity = SparrowEntity.this.getTarget();
            if (livingentity == null) {
                return false;
            } else if (!livingentity.isAlive()) {
                return false;
            } else {
                if (livingentity instanceof Player) {
                    Player player = (Player)livingentity;
                    if (livingentity.isSpectator() || player.isCreative()) {
                        return false;
                    }
                }

                if (!this.canUse()) {
                    return false;
                } else {
                    if (SparrowEntity.this.tickCount > this.catSearchTick) {
                        this.catSearchTick = SparrowEntity.this.tickCount + 20;
                        List<Cat> list = SparrowEntity.this.level.getEntitiesOfClass(Cat.class, SparrowEntity.this.getBoundingBox().inflate(16.0D), EntitySelector.ENTITY_STILL_ALIVE);

                        for(Cat cat : list) {
                            cat.hiss();
                        }

                        this.isScaredOfCat = !list.isEmpty();
                    }

                    return !this.isScaredOfCat;
                }
            }
        }

        /**
         * Execute a one shot task or start executing a continuous task
         */
        public void start() {
        }

        /**
         * Reset the task's internal state. Called when this task is interrupted by another one
         */
        public void stop() {
            SparrowEntity.this.setTarget((LivingEntity)null);
            SparrowEntity.this.attackPhase = SparrowEntity.AttackPhase.CIRCLE;
        }

        /**
         * Keep ticking a continuous task that has already been started
         */
        public void tick() {
            LivingEntity livingentity = SparrowEntity.this.getTarget();
            if (livingentity != null) {
                SparrowEntity.this.moveTargetPoint = new Vec3(livingentity.getX(), livingentity.getY(0.5D), livingentity.getZ());
                if (SparrowEntity.this.getBoundingBox().inflate((double)0.2F).intersects(livingentity.getBoundingBox())) {
                    SparrowEntity.this.doHurtTarget(livingentity);
                    SparrowEntity.this.attackPhase = SparrowEntity.AttackPhase.CIRCLE;
                    if (!SparrowEntity.this.isSilent()) {
                        SparrowEntity.this.level.levelEvent(1039, SparrowEntity.this.blockPosition(), 0);
                    }
                } else if (SparrowEntity.this.horizontalCollision || SparrowEntity.this.hurtTime > 0) {
                    SparrowEntity.this.attackPhase = SparrowEntity.AttackPhase.CIRCLE;
                }

            }
        }
    }
}
