package com.RuthlessNail.ornithophobia.entity.ai.goal;

import com.RuthlessNail.ornithophobia.entity.custom.MuppetEntity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;

public class MuppetAttackGoal extends MeleeAttackGoal {

    private MuppetEntity muppet;



    public MuppetAttackGoal(MuppetEntity pMuppet, double pSpeedModifier, boolean pFollowingTargetEvenIfNotSeen) {
        super(pMuppet, pSpeedModifier, pFollowingTargetEvenIfNotSeen);
        this.muppet = pMuppet;
    }

    @Override
    public void stop() {
        super.stop();
    }

    @Override
    protected void checkAndPerformAttack(LivingEntity pEnemy, double pDistToEnemySqr) {
        this.muppet.setAttacking(true);
        super.checkAndPerformAttack(pEnemy, pDistToEnemySqr);
    }
}
