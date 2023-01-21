package com.RuthlessNail.ornithophobia.item;

import com.RuthlessNail.ornithophobia.util.ModTags;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.ForgeTier;

public class ModTiers {
    public static final ForgeTier TIN = new ForgeTier(1, 85, 5f, 2f, 18, BlockTags.NEEDS_STONE_TOOL,
            () -> Ingredient.of(ModItems.TIN_INGOT.get()));

    public static final ForgeTier TITANIUM = new ForgeTier(4, 2310, 10f, 5.0f, 20, ModTags.Blocks.NEEDS_TITANIUM_TOOL,
            () -> Ingredient.of(ModItems.TITANIUM_INGOT.get()));

    public static final ForgeTier APOSTATE = new ForgeTier(0, 5000, 10f, 0.0f, 30, ModTags.Blocks.NEEDS_TITANIUM_TOOL,
            () -> Ingredient.of(ModItems.TITANIUM_INGOT.get()));

}
