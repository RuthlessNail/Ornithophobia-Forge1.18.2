package com.RuthlessNail.ornithophobia.item;

import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.ForgeTier;

public class ModTiers {
    public static final ForgeTier TIN = new ForgeTier(1, 85, 5f, 2f, 18, BlockTags.NEEDS_STONE_TOOL,
            () -> Ingredient.of(ModItems.TIN_INGOT.get()));
}
