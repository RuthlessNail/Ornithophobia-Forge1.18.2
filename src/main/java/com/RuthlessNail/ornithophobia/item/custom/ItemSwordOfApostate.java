package com.RuthlessNail.ornithophobia.item.custom;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.*;

public class ItemSwordOfApostate extends SwordItem {
    public ItemSwordOfApostate(Tier pTier, int pAttackDamageModifier, float pAttackSpeedModifier, Properties pProperties) {
        super(pTier, pAttackDamageModifier, pAttackSpeedModifier, pProperties);

    }

    @Override
    public Rarity getRarity(ItemStack pStack) {
        return Rarity.EPIC;
    }

    @Override
    public boolean canDisableShield(ItemStack stack, ItemStack shield, LivingEntity entity, LivingEntity attacker) {
        return true;
    }
}
