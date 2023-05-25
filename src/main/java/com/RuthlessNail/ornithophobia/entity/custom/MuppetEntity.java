package com.RuthlessNail.ornithophobia.entity.custom;

import com.RuthlessNail.ornithophobia.entity.ModEntityTypes;
import com.RuthlessNail.ornithophobia.entity.ai.goal.MuppetAttackGoal;
import com.RuthlessNail.ornithophobia.sound.ModSounds;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.EntityDamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.animal.Turtle;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.monster.Ghast;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.ZombifiedPiglin;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import software.bernie.geckolib3.core.AnimationState;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.util.GeckoLibUtil;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static software.bernie.geckolib3.core.builder.ILoopType.EDefaultLoopTypes.LOOP;
import static software.bernie.geckolib3.core.builder.ILoopType.EDefaultLoopTypes.PLAY_ONCE;

public class MuppetEntity extends Monster implements IAnimatable {

    private static final EntityDataAccessor<Boolean> ANGERED = SynchedEntityData.defineId(MuppetEntity.class, EntityDataSerializers.BOOLEAN);
    public MuppetEntity(EntityType<? extends Monster> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        this.xpReward = 10;
    }

    private static final UUID SPEED_MODIFIER_ATTACKING_UUID = UUID.fromString("A0C9E514-3914-4627-B9A9-86262FD078A4");
    private static final AttributeModifier SPEED_MODIFIER_ATTACKING = new AttributeModifier(SPEED_MODIFIER_ATTACKING_UUID, "Attacking speed boost", 0.3D, AttributeModifier.Operation.ADDITION);
    private static final EntityDataAccessor<Boolean> IS_ATTACKING = SynchedEntityData.defineId(MuppetEntity.class, EntityDataSerializers.BOOLEAN);

    public static AttributeSupplier setAttributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.MAX_HEALTH, 30.0f)
                .add(Attributes.ATTACK_DAMAGE, 6.0f)
                .add(Attributes.ATTACK_SPEED, 3.0f)
                .add(Attributes.FOLLOW_RANGE, 40.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.15f)
                .add(Attributes.ARMOR, 2.0D)
                .build();
    }

    private AnimationFactory factory = GeckoLibUtil.createFactory(this);


    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new FloatGoal(this));
        this.goalSelector.addGoal(2, new MuppetAttackGoal(this, 1.0D, false));
        this.goalSelector.addGoal(4, new WaterAvoidingRandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(5, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(1, (new HurtByTargetGoal(this)).setAlertOthers(MuppetEntity.class));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, AbstractVillager.class, false));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, IronGolem.class, true));

    }



    public SoundSource getSoundSource() {
        return SoundSource.HOSTILE;
    }

    int randomAmbient = 0;
    int pastAmbient = 0;
    protected SoundEvent getAmbientSound() {
        Random rand = new Random();

        while (randomAmbient == pastAmbient) {
            randomAmbient = rand.nextInt(3);
        }
        pastAmbient = randomAmbient;


        if (randomAmbient == 0) {
            return ModSounds.MUPPET_AMBIENT1.get();
        }
        else if (randomAmbient == 1) {
            return ModSounds.MUPPET_AMBIENT2.get();
        }
        else {
            return ModSounds.MUPPET_AMBIENT3.get();
        }

    }

    protected SoundEvent getHurtSound(DamageSource pDamageSource) {
        return ModSounds.MUPPET_HURT.get();
    }

    protected SoundEvent getDeathSound() {
        return ModSounds.MUPPET_DEATH.get();
    }

    protected void defineSynchedData() {
        super.defineSynchedData();
        this.getEntityData().define(ANGERED, false);
        this.getEntityData().define(IS_ATTACKING, false);
    }

    public boolean isAttacking() {
        return this.entityData.get(IS_ATTACKING);
    }

    public void setAttacking(boolean pAttacking) {
        if (!this.level.isClientSide) {
            this.entityData.set(IS_ATTACKING, pAttacking);
        }
    }

    @Override
    public int getCurrentSwingDuration() {
        return 20;
    }

    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
        if (event.isMoving() && !this.swinging) {
            if (this.getEntityData().get(ANGERED)) {
                event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.muppet.run", LOOP));
                return PlayState.CONTINUE;}

            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.muppet.walk", LOOP));
            return PlayState.CONTINUE;
        }
        else if(!event.isMoving() && !this.swinging){
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.muppet.idle", LOOP));
            return PlayState.CONTINUE;
        }
        return PlayState.STOP;
    }

    private PlayState attackPredicate(AnimationEvent event) {
        if (this.swinging) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.muppet.attack", PLAY_ONCE));
            return PlayState.CONTINUE;
        }
    event.getController().markNeedsReload();
        return PlayState.STOP;
    }

    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController(this, "controller",
                0, this::predicate));
        data.addAnimationController(new AnimationController(this, "attackController",
                0, this::attackPredicate));
    }

    @Override
    protected void customServerAiStep() {
        AttributeInstance attributeinstance = this.getAttribute(Attributes.MOVEMENT_SPEED);
        if (this.getEntityData().get(ANGERED)) {
            if (!attributeinstance.hasModifier(SPEED_MODIFIER_ATTACKING)) {
                attributeinstance.addTransientModifier(SPEED_MODIFIER_ATTACKING);
            }

        } else if (attributeinstance.hasModifier(SPEED_MODIFIER_ATTACKING)) {
            attributeinstance.removeModifier(SPEED_MODIFIER_ATTACKING);
        }

        super.customServerAiStep();
    }

    @Override
    public void aiStep() {
        if (!this.level.isClientSide && !this.getEntityData().get(ANGERED)) {
        setAngered();
        }
        super.aiStep();
    }

    public void setAngered() {

        LivingEntity pLivingEntity = this.getLastHurtByMob();
        if (pLivingEntity != null) {
            this.getEntityData().set(ANGERED, true);
            //MuppetEntity.this.playSound(ModSounds.MUPPET_ANGRY.get(), 10.0F, 0.95F + MuppetEntity.this.random.nextFloat() * 0.1F);
            //sound waiting for future rerecord
        }
        else {
            this.getEntityData().set(ANGERED, false);
        }
    }


    @Override
    public AnimationFactory getFactory() {
        return this.factory;
    }

    @Override
    protected void tickDeath() {
        if (this.deathTime == 20 && !this.level.isClientSide()) {
            ModEntityTypes.SPARROW.get().spawn((ServerLevel) this.level, null, null, this.blockPosition(), MobSpawnType.TRIGGERED, true, true);
        }
        super.tickDeath();
    }
}
