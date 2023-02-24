package com.RuthlessNail.ornithophobia.enchantment;

import com.RuthlessNail.ornithophobia.item.ModItems;
import com.RuthlessNail.ornithophobia.util.ModTags;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.*;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraftforge.registries.ForgeRegistries;

public class HammerLightningEnchantment extends Enchantment {
    static EnchantmentCategory TITANIUM_HAMMER_TYPE = EnchantmentCategory.create("titanium_hammer",  item -> item == ModItems.TITANIUM_HAMMER.get());

    protected HammerLightningEnchantment(Rarity pRarity, EnchantmentCategory pCategory, EquipmentSlot... pApplicableSlots) {
        super(pRarity, TITANIUM_HAMMER_TYPE, pApplicableSlots);
    }

    @Override
    public int getMaxLevel() {
        return 1;
    }

    @Override
    public void doPostAttack(LivingEntity pAttacker, Entity pTarget, int pLevel) {
        if (!pAttacker.level.isClientSide()) {
            ServerLevel world = ((ServerLevel) pAttacker.level);
            BlockPos position = pTarget.blockPosition();


            EntityType.LIGHTNING_BOLT.spawn(world, null, null, position,
                    MobSpawnType.TRIGGERED, true, true);

            super.doPostAttack(pAttacker, pTarget, pLevel);
        }
    }

    @Override
    public boolean isTreasureOnly() {
        return true;
    }

    @Override
    public boolean isTradeable() {
        return false;
    }
}
