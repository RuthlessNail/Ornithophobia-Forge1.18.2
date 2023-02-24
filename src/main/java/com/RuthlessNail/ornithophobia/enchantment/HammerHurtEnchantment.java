package com.RuthlessNail.ornithophobia.enchantment;

import com.RuthlessNail.ornithophobia.util.ModTags;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraftforge.registries.ForgeRegistries;

public class HammerHurtEnchantment extends Enchantment {

    static EnchantmentCategory HAMMER_TYPE = EnchantmentCategory.create("hammer", item -> ForgeRegistries.ITEMS.tags().getTag(ModTags.Items.HAMMER).contains(item));
    protected HammerHurtEnchantment(Rarity pRarity, EnchantmentCategory pCategory, EquipmentSlot... pApplicableSlots) {
        super(pRarity, HAMMER_TYPE, pApplicableSlots);

    }


    @Override
    public int getMaxLevel() {
        return 5;
    }

    @Override
    public float getDamageBonus(int level, MobType mobType, ItemStack enchantedItem) {
        return 1.0F + (float)Math.max(0, level - 1) * 0.5F;
    }
}
