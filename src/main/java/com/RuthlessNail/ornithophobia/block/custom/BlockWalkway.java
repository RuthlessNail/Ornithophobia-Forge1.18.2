package com.RuthlessNail.ornithophobia.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class BlockWalkway extends Block {
    public BlockWalkway(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public void stepOn(Level pLevel, BlockPos pPos, BlockState pState, Entity pEntity) {
        super.stepOn(pLevel, pPos, pState, pEntity);
        if(!pLevel.isClientSide()) {
            LivingEntity livingEntity = ((LivingEntity) pEntity);
            livingEntity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 1, 3));
        }
    }
}
