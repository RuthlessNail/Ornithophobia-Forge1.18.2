package com.RuthlessNail.ornithophobia.datagen;

import com.RuthlessNail.ornithophobia.block.ModBlock;
import com.RuthlessNail.ornithophobia.item.ModItems;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;

import java.util.function.Consumer;

public class ModRecipeProvider extends RecipeProvider implements IConditionBuilder {
    public ModRecipeProvider(DataGenerator pGenerator) {
        super(pGenerator);
    }

    @Override
    protected void buildCraftingRecipes(Consumer<FinishedRecipe> pFinishedRecipeConsumer) {
        ShapedRecipeBuilder.shaped(ModBlock.EYEBALL_BLOCK.get())
                .define('E', ModItems.EYEBALL.get())
                .pattern("EEE")
                .pattern("EEE")
                .pattern("EEE")
                .unlockedBy("has_eyeball", inventoryTrigger(ItemPredicate.Builder.item().of(ModItems.EYEBALL.get()).build()))
                .save(pFinishedRecipeConsumer);

        ShapedRecipeBuilder.shaped(ModItems.EYEBALL.get())
                .define('E', ModBlock.EYEBALL_BLOCK.get())
                .pattern("EEE")
                .pattern("EEE")
                .pattern("EEE")
                .unlockedBy("has_eyeball_block", inventoryTrigger(ItemPredicate.Builder.item().of(ModBlock.EYEBALL_BLOCK.get()).build()))
                .save(pFinishedRecipeConsumer);


    }
}
