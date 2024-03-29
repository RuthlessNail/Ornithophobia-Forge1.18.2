package com.RuthlessNail.ornithophobia.datagen;

import com.google.gson.JsonObject;
import java.util.function.Consumer;
import javax.annotation.Nullable;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.CriterionTriggerInstance;
import net.minecraft.advancements.RequirementsStrategy;
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger;
import net.minecraft.core.Registry;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.SimpleCookingSerializer;
import net.minecraft.world.level.ItemLike;

public class ModCookingRecipeBuilder implements RecipeBuilder {
    private final Item result;
    private final Ingredient ingredient;
    private final float experience;
    private final int cookingTime;
    private final Advancement.Builder advancement = Advancement.Builder.advancement();
    @Nullable
    private String group;
    private final SimpleCookingSerializer<?> serializer;

    private ModCookingRecipeBuilder(ItemLike pResult, Ingredient pIngredient, float pExperience, int pCookingTime, SimpleCookingSerializer<?> pSerializer) {
        this.result = pResult.asItem();
        this.ingredient = pIngredient;
        this.experience = pExperience;
        this.cookingTime = pCookingTime;
        this.serializer = pSerializer;
    }

    public static com.RuthlessNail.ornithophobia.datagen.ModCookingRecipeBuilder cooking(Ingredient pIngredient, ItemLike pResult, float pExperience, int pCookingTime, SimpleCookingSerializer<?> pSerializer) {
        return new com.RuthlessNail.ornithophobia.datagen.ModCookingRecipeBuilder(pResult, pIngredient, pExperience, pCookingTime, pSerializer);
    }

    public static ModCookingRecipeBuilder campfireCooking(Ingredient pIngredient, ItemLike pResult, float pExperience, int pCookingTime) {
        return cooking(pIngredient, pResult, pExperience, pCookingTime, RecipeSerializer.CAMPFIRE_COOKING_RECIPE);
    }

    public static ModCookingRecipeBuilder blasting(Ingredient pIngredient, ItemLike pResult, float pExperience, int pCookingTime) {
        return cooking(pIngredient, pResult, pExperience, pCookingTime, RecipeSerializer.BLASTING_RECIPE);
    }

    public static ModCookingRecipeBuilder smelting(Ingredient pIngredient, ItemLike pResult, float pExperience, int pCookingTime) {
        return cooking(pIngredient, pResult, pExperience, pCookingTime, RecipeSerializer.SMELTING_RECIPE);
    }

    public static ModCookingRecipeBuilder smoking(Ingredient pIngredient, ItemLike pResult, float pExperience, int pCookingTime) {
        return cooking(pIngredient, pResult, pExperience, pCookingTime, RecipeSerializer.SMOKING_RECIPE);
    }

    public ModCookingRecipeBuilder unlockedBy(String pCriterionName, CriterionTriggerInstance pCriterionTrigger) {
        this.advancement.addCriterion(pCriterionName, pCriterionTrigger);
        return this;
    }

    public ModCookingRecipeBuilder group(@Nullable String pGroupName) {
        this.group = pGroupName;
        return this;
    }

    public Item getResult() {
        return this.result;
    }

    public void save(Consumer<FinishedRecipe> pFinishedRecipeConsumer, ResourceLocation pRecipeId) {
        this.ensureValid(pRecipeId);
        this.advancement.parent(new ResourceLocation("ornithophobia:recipes/root")).addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(pRecipeId)).rewards(AdvancementRewards.Builder.recipe(pRecipeId)).requirements(RequirementsStrategy.OR);
        pFinishedRecipeConsumer.accept(new ModCookingRecipeBuilder.Result(pRecipeId, this.group == null ? "" : this.group, this.ingredient, this.result, this.experience, this.cookingTime, this.advancement, new ResourceLocation(pRecipeId.getNamespace(), "recipes/" + this.result.getItemCategory().getRecipeFolderName() + "/" + pRecipeId.getPath()), this.serializer));
    }

    /**
     * Makes sure that this obtainable.
     */
    private void ensureValid(ResourceLocation pId) {
        if (this.advancement.getCriteria().isEmpty()) {
            throw new IllegalStateException("No way of obtaining recipe " + pId);
        }
    }

    public static class Result implements FinishedRecipe {
        private final ResourceLocation id;
        private final String group;
        private final Ingredient ingredient;
        private final Item result;
        private final float experience;
        private final int cookingTime;
        private final Advancement.Builder advancement;
        private final ResourceLocation advancementId;
        private final RecipeSerializer<? extends AbstractCookingRecipe> serializer;

        public Result(ResourceLocation pId, String pGroup, Ingredient pIngredient, Item pResult, float pExperience, int pCookingTime, Advancement.Builder pAdvancement, ResourceLocation pAdvancementId, RecipeSerializer<? extends AbstractCookingRecipe> pSerializer) {
            this.id = pId;
            this.group = pGroup;
            this.ingredient = pIngredient;
            this.result = pResult;
            this.experience = pExperience;
            this.cookingTime = pCookingTime;
            this.advancement = pAdvancement;
            this.advancementId = pAdvancementId;
            this.serializer = pSerializer;
        }

        public void serializeRecipeData(JsonObject pJson) {
            if (!this.group.isEmpty()) {
                pJson.addProperty("group", this.group);
            }

            pJson.add("ingredient", this.ingredient.toJson());
            pJson.addProperty("result", Registry.ITEM.getKey(this.result).toString());
            pJson.addProperty("experience", this.experience);
            pJson.addProperty("cookingtime", this.cookingTime);
        }

        public RecipeSerializer<?> getType() {
            return this.serializer;
        }

        /**
         * Gets the ID for the recipe.
         */
        public ResourceLocation getId() {
            return this.id;
        }

        /**
         * Gets the JSON for the advancement that unlocks this recipe. Null if there is no advancement.
         */
        @Nullable
        public JsonObject serializeAdvancement() {
            return this.advancement.serializeToJson();
        }


        @Nullable
        public ResourceLocation getAdvancementId() {
            return this.advancementId;
        }
    }
}