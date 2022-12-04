package com.RuthlessNail.ornithophobia.datagen;

import com.RuthlessNail.ornithophobia.ModItemTags;
import com.RuthlessNail.ornithophobia.block.ModBlock;
import com.RuthlessNail.ornithophobia.item.ModItems;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Sets;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.logging.LogUtils;
import net.minecraft.advancements.critereon.*;
import net.minecraft.core.Registry;
import net.minecraft.data.BlockFamily;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.HashCache;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.HoneycombItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.SimpleCookingSerializer;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Consumer;



public class ModRecipeProvider extends RecipeProvider implements IConditionBuilder {

    private static final Logger LOGGER = LogUtils.getLogger();
    private static final Gson GSON = (new GsonBuilder()).setPrettyPrinting().create();
    protected static final ImmutableList<ItemLike> TIN_SMELTABLES = ImmutableList.of(ModBlock.TIN_ORE.get(), ModBlock.DEEPSLATE_TIN_ORE.get(), ModItems.RAW_TIN.get());

    protected static final Map<BlockFamily.Variant, BiFunction<ItemLike, ItemLike, RecipeBuilder>> shapeBuilders = ImmutableMap.<BlockFamily.Variant, BiFunction<ItemLike, ItemLike, RecipeBuilder>>builder().put(BlockFamily.Variant.BUTTON, (p_176733_, p_176734_) -> {
        return buttonBuilder(p_176733_, Ingredient.of(p_176734_));
    }).put(BlockFamily.Variant.CHISELED, (p_176730_, p_176731_) -> {
        return chiseledBuilder(p_176730_, Ingredient.of(p_176731_));
    }).put(BlockFamily.Variant.CUT, (p_176724_, p_176725_) -> {
        return cutBuilder(p_176724_, Ingredient.of(p_176725_));
    }).put(BlockFamily.Variant.DOOR, (p_176714_, p_176715_) -> {
        return doorBuilder(p_176714_, Ingredient.of(p_176715_));
    }).put(BlockFamily.Variant.FENCE, (p_176708_, p_176709_) -> {
        return fenceBuilder(p_176708_, Ingredient.of(p_176709_));
    }).put(BlockFamily.Variant.FENCE_GATE, (p_176698_, p_176699_) -> {
        return fenceGateBuilder(p_176698_, Ingredient.of(p_176699_));
    }).put(BlockFamily.Variant.SIGN, (p_176688_, p_176689_) -> {
        return signBuilder(p_176688_, Ingredient.of(p_176689_));
    }).put(BlockFamily.Variant.SLAB, (p_176682_, p_176683_) -> {
        return slabBuilder(p_176682_, Ingredient.of(p_176683_));
    }).put(BlockFamily.Variant.STAIRS, (p_176674_, p_176675_) -> {
        return stairBuilder(p_176674_, Ingredient.of(p_176675_));
    }).put(BlockFamily.Variant.PRESSURE_PLATE, (p_176662_, p_176663_) -> {
        return pressurePlateBuilder(p_176662_, Ingredient.of(p_176663_));
    }).put(BlockFamily.Variant.POLISHED, (p_176650_, p_176651_) -> {
        return polishedBuilder(p_176650_, Ingredient.of(p_176651_));
    }).put(BlockFamily.Variant.TRAPDOOR, (p_176638_, p_176639_) -> {
        return trapdoorBuilder(p_176638_, Ingredient.of(p_176639_));
    }).put(BlockFamily.Variant.WALL, (p_176608_, p_176609_) -> {
        return wallBuilder(p_176608_, Ingredient.of(p_176609_));
    }).build();



    public ModRecipeProvider(DataGenerator pGenerator) {
        super(pGenerator);
    }

    public void run(HashCache pCache) {
        Path path = this.generator.getOutputFolder();
        Set<ResourceLocation> set = Sets.newHashSet();
        buildCraftingRecipes((p_125991_) -> {
            if (!set.add(p_125991_.getId())) {
                throw new IllegalStateException("Duplicate recipe " + p_125991_.getId());
            } else {
                saveRecipe(pCache, p_125991_.serializeRecipe(), path.resolve("data/" + p_125991_.getId().getNamespace() + "/recipes/" + p_125991_.getId().getPath() + ".json"));
                JsonObject jsonobject = p_125991_.serializeAdvancement();
                if (jsonobject != null) {
                    saveAdvancement(pCache, jsonobject, path.resolve("data/" + p_125991_.getId().getNamespace() + "/advancements/" + p_125991_.getAdvancementId().getPath() + ".json"));
                }

            }
        });
    }

    /**
     * Saves a recipe to a file.
     */
    private static void saveRecipe(HashCache pCache, JsonObject pRecipeJson, Path pPath) {
        try {
            String s = GSON.toJson((JsonElement)pRecipeJson);
            String s1 = SHA1.hashUnencodedChars(s).toString();
            if (!Objects.equals(pCache.getHash(pPath), s1) || !Files.exists(pPath)) {
                Files.createDirectories(pPath.getParent());
                BufferedWriter bufferedwriter = Files.newBufferedWriter(pPath);

                try {
                    bufferedwriter.write(s);
                } catch (Throwable throwable1) {
                    if (bufferedwriter != null) {
                        try {
                            bufferedwriter.close();
                        } catch (Throwable throwable) {
                            throwable1.addSuppressed(throwable);
                        }
                    }

                    throw throwable1;
                }

                if (bufferedwriter != null) {
                    bufferedwriter.close();
                }
            }

            pCache.putNew(pPath, s1);
        } catch (IOException ioexception) {
            LOGGER.error("Couldn't save recipe {}", pPath, ioexception);
        }

    }

    /**
     * Saves an advancement to a file.
     */
    protected void saveAdvancement(HashCache pCache, JsonObject pAdvancementJson, Path pPath) {
        try {
            String s = GSON.toJson((JsonElement)pAdvancementJson);
            String s1 = SHA1.hashUnencodedChars(s).toString();
            if (!Objects.equals(pCache.getHash(pPath), s1) || !Files.exists(pPath)) {
                Files.createDirectories(pPath.getParent());
                BufferedWriter bufferedwriter = Files.newBufferedWriter(pPath);

                try {
                    bufferedwriter.write(s);
                } catch (Throwable throwable1) {
                    if (bufferedwriter != null) {
                        try {
                            bufferedwriter.close();
                        } catch (Throwable throwable) {
                            throwable1.addSuppressed(throwable);
                        }
                    }

                    throw throwable1;
                }

                if (bufferedwriter != null) {
                    bufferedwriter.close();
                }
            }

            pCache.putNew(pPath, s1);
        } catch (IOException ioexception) {
            LOGGER.error("Couldn't save recipe advancement {}", pPath, ioexception);
        }

    }


//look here look here look here look here look here look here look here look here look here look here//
    @Override
    protected void buildCraftingRecipes(Consumer<FinishedRecipe> pFinishedRecipeConsumer) {
        nineBlockStorageRecipes(pFinishedRecipeConsumer, ModItems.EYEBALL.get(), ModBlock.EYEBALL_BLOCK.get(),
                "ornithophobia:eyeball_block", null,
                "ornithophobia:eyeball_from_eyeball_block", null);

        nineBlockStorageRecipes(pFinishedRecipeConsumer, ModItems.TIN_INGOT.get(), ModBlock.TIN_BLOCK.get(),
                "ornithophobia:tin_block", null,
                "ornithophobia:tin_from_tin_block", null);

        woodFromLogs(pFinishedRecipeConsumer, ModBlock.FLESH_WOOD.get(), ModBlock.FLESH_LOG.get());

        woodFromLogs(pFinishedRecipeConsumer, ModBlock.STRIPPED_FLESH_WOOD.get(), ModBlock.STRIPPED_FLESH_LOG.get());

        planksFromLogs(pFinishedRecipeConsumer, ModItems.STRANGE_FLESH.get(), ModItemTags.FLESH_LOGS);

        ShapedRecipeBuilder.shaped(ModItems.TIN_AXE.get()).define('#', Items.STICK).define('X', ModItems.TIN_INGOT.get())
                .pattern("XX")
                .pattern("X#")
                .pattern(" #")
                .unlockedBy("has_tin", has(ModItems.TIN_INGOT.get())).save(pFinishedRecipeConsumer);

        ShapedRecipeBuilder.shaped(ModItems.TIN_HOE.get()).define('#', Items.STICK).define('X', ModItems.TIN_INGOT.get())
                .pattern("XX")
                .pattern(" #")
                .pattern(" #")
                .unlockedBy("has_tin", has(ModItems.TIN_INGOT.get())).save(pFinishedRecipeConsumer);

        ShapedRecipeBuilder.shaped(ModItems.TIN_PICKAXE.get()).define('#', Items.STICK).define('X', ModItems.TIN_INGOT.get())
                .pattern("XXX")
                .pattern(" # ")
                .pattern(" # ")
                .unlockedBy("has_tin", has(ModItems.TIN_INGOT.get())).save(pFinishedRecipeConsumer);

        ShapedRecipeBuilder.shaped(ModItems.TIN_SHOVEL.get()).define('#', Items.STICK).define('X', ModItems.TIN_INGOT.get())
                .pattern(" X")
                .pattern(" #")
                .pattern(" #")
                .unlockedBy("has_tin", has(ModItems.TIN_INGOT.get())).save(pFinishedRecipeConsumer);

        ShapedRecipeBuilder.shaped(ModItems.TIN_SWORD.get()).define('#', Items.STICK).define('X', ModItems.TIN_INGOT.get())
                .pattern(" X")
                .pattern(" X")
                .pattern(" #")
                .unlockedBy("has_tin", has(ModItems.TIN_INGOT.get())).save(pFinishedRecipeConsumer);

        ShapedRecipeBuilder.shaped(ModItems.TIN_HELMET.get()).define('X', ModItems.TIN_INGOT.get())
                .pattern("XXX")
                .pattern("X X")
                .unlockedBy("has_tin", has(ModItems.TIN_INGOT.get())).save(pFinishedRecipeConsumer);

        ShapedRecipeBuilder.shaped(ModItems.TIN_CHESTPLATE.get()).define('X', ModItems.TIN_INGOT.get())
                .pattern("X X")
                .pattern("XXX")
                .pattern("XXX")
                .unlockedBy("has_tin", has(ModItems.TIN_INGOT.get())).save(pFinishedRecipeConsumer);

        ShapedRecipeBuilder.shaped(ModItems.TIN_LEGGINGS.get()).define('X', ModItems.TIN_INGOT.get())
                .pattern("XXX")
                .pattern("X X")
                .pattern("X X")
                .unlockedBy("has_tin", has(ModItems.TIN_INGOT.get())).save(pFinishedRecipeConsumer);

        ShapedRecipeBuilder.shaped(ModItems.TIN_BOOTS.get()).define('X', ModItems.TIN_INGOT.get())
                .pattern("X X")
                .pattern("X X")
                .unlockedBy("has_tin", has(ModItems.TIN_INGOT.get())).save(pFinishedRecipeConsumer);

        ShapelessRecipeBuilder.shapeless(ModItems.COMPACTED_COAL.get()).requires(ModItems.WOODEN_HAMMER.get()).
                requires(ItemTags.COALS).requires(ItemTags.COALS).requires(ItemTags.COALS).requires(ItemTags.COALS).
                unlockedBy("hasHammer", has(ModItems.WOODEN_HAMMER.get())).save(pFinishedRecipeConsumer);

        ShapedRecipeBuilder.shaped(ModItems.WOODEN_HAMMER.get()).define('#', Items.STICK).define('X', ItemTags.PLANKS)
                .pattern(" XX")
                .pattern(" #X")
                .pattern("#  ")
                .unlockedBy("has_planks", has(ItemTags.PLANKS)).save(pFinishedRecipeConsumer);

        ShapedRecipeBuilder.shaped(ModBlock.COPPER_LAMP.get()).define('#', Items.COPPER_INGOT).define('X', Items.GLOWSTONE).define('R', Items.REDSTONE)
                .pattern("#R#")
                .pattern("RXR")
                .pattern("#R#")
                .unlockedBy("has_planks", has(ItemTags.PLANKS)).save(pFinishedRecipeConsumer);

        oreSmelting(pFinishedRecipeConsumer, TIN_SMELTABLES, ModItems.TIN_INGOT.get(), 0.7F, 200, "tin_ingot");
        oreBlasting(pFinishedRecipeConsumer, TIN_SMELTABLES, ModItems.TIN_INGOT.get(), 0.7F, 100, "tin_ingot");

        ModCookingRecipeBuilder.smelting(Ingredient.of(ModItems.STRANGE_FLESH.get()), ModItems.COOKED_STRANGE_FLESH.get(), 0.35F,
                200).unlockedBy("has_strange_flesh", has(ModItems.STRANGE_FLESH.get())).
                save(pFinishedRecipeConsumer);

        cookRecipes(pFinishedRecipeConsumer, "smoking", RecipeSerializer.SMOKING_RECIPE, 100);
        cookRecipes(pFinishedRecipeConsumer, "campfire_cooking", RecipeSerializer.CAMPFIRE_COOKING_RECIPE, 600);

    }
    protected static void oneToOneConversionRecipe(Consumer<FinishedRecipe> pFinishedRecipeConsumer, ItemLike pResult, ItemLike pIngredient, @Nullable String pGroup) {
        oneToOneConversionRecipe(pFinishedRecipeConsumer, pResult, pIngredient, pGroup, 1);
    }

    protected static void oneToOneConversionRecipe(Consumer<FinishedRecipe> pFinishedRecipeConsumer, ItemLike pResult, ItemLike pIngredient, @Nullable String pGroup, int pResultCount) {
        ShapelessRecipeBuilder.shapeless(pResult, pResultCount).requires(pIngredient).group(pGroup).unlockedBy(getHasName(pIngredient), has(pIngredient)).save(pFinishedRecipeConsumer, getConversionRecipeName(pResult, pIngredient));
    }

    protected static void oreSmelting(Consumer<FinishedRecipe> pFinishedRecipeConsumer, List<ItemLike> pIngredients, ItemLike pResult, float pExperience, int pCookingTime, String pGroup) {
        oreCooking(pFinishedRecipeConsumer, RecipeSerializer.SMELTING_RECIPE, pIngredients, pResult, pExperience, pCookingTime, pGroup, "_from_smelting");
    }

    protected static void oreBlasting(Consumer<FinishedRecipe> pFinishedRecipeConsumer, List<ItemLike> pIngredients, ItemLike pResult, float pExperience, int pCookingTime, String pGroup) {
        oreCooking(pFinishedRecipeConsumer, RecipeSerializer.BLASTING_RECIPE, pIngredients, pResult, pExperience, pCookingTime, pGroup, "_from_blasting");
    }

    protected static void oreCooking(Consumer<FinishedRecipe> pFinishedRecipeConsumer, SimpleCookingSerializer<?> pCookingSerializer, List<ItemLike> pIngredients, ItemLike pResult, float pExperience, int pCookingTime, String pGroup, String pRecipeName) {
        for(ItemLike itemlike : pIngredients) {
            ModCookingRecipeBuilder.cooking(Ingredient.of(itemlike), pResult, pExperience, pCookingTime, pCookingSerializer).group(pGroup).unlockedBy(getHasName(itemlike), has(itemlike)).save(pFinishedRecipeConsumer, "ornithophobia:" + getItemName(pResult) + pRecipeName + "_" + getItemName(itemlike));
        }

    }

    protected static void netheriteSmithing(Consumer<FinishedRecipe> pFinishedRecipeConsumer, Item pIngredientItem, Item pResultItem) {
        UpgradeRecipeBuilder.smithing(Ingredient.of(pIngredientItem), Ingredient.of(Items.NETHERITE_INGOT), pResultItem).unlocks("has_netherite_ingot", has(Items.NETHERITE_INGOT)).save(pFinishedRecipeConsumer, getItemName(pResultItem) + "_smithing");
    }

    protected static void planksFromLog(Consumer<FinishedRecipe> pFinishedRecipeConsumer, ItemLike pPlanks, TagKey<Item> pLogs) {
        ShapelessRecipeBuilder.shapeless(pPlanks, 4).requires(pLogs).group("planks").unlockedBy("has_log", has(pLogs)).save(pFinishedRecipeConsumer);
    }

    protected static void planksFromLogs(Consumer<FinishedRecipe> pFinishedRecipeConsumer, ItemLike pPlanks, TagKey<Item> pLogs) {
        ShapelessRecipeBuilder.shapeless(pPlanks, 4).requires(pLogs).group("planks").unlockedBy("has_logs", has(pLogs)).save(pFinishedRecipeConsumer);
    }

    protected static void woodFromLogs(Consumer<FinishedRecipe> pFinishedRecipeConsumer, ItemLike pWood, ItemLike pLog) {
        ShapedRecipeBuilder.shaped(pWood, 3).define('#', pLog).pattern("##").pattern("##").group("bark").unlockedBy("has_log", has(pLog)).save(pFinishedRecipeConsumer);
    }

    protected static void woodenBoat(Consumer<FinishedRecipe> pFinishedRecipeConsumer, ItemLike pBoat, ItemLike pMaterial) {
        ShapedRecipeBuilder.shaped(pBoat).define('#', pMaterial).pattern("# #").pattern("###").group("boat").unlockedBy("in_water", insideOf(Blocks.WATER)).save(pFinishedRecipeConsumer);
    }

    protected static RecipeBuilder buttonBuilder(ItemLike pButton, Ingredient pMaterial) {
        return ShapelessRecipeBuilder.shapeless(pButton).requires(pMaterial);
    }

    protected static RecipeBuilder doorBuilder(ItemLike pDoor, Ingredient pMaterial) {
        return ShapedRecipeBuilder.shaped(pDoor, 3).define('#', pMaterial).pattern("##").pattern("##").pattern("##");
    }

    protected static RecipeBuilder fenceBuilder(ItemLike pFence, Ingredient pMaterial) {
        int i = pFence == Blocks.NETHER_BRICK_FENCE ? 6 : 3;
        Item item = pFence == Blocks.NETHER_BRICK_FENCE ? Items.NETHER_BRICK : Items.STICK;
        return ShapedRecipeBuilder.shaped(pFence, i).define('W', pMaterial).define('#', item).pattern("W#W").pattern("W#W");
    }

    protected static RecipeBuilder fenceGateBuilder(ItemLike pFenceGate, Ingredient pMaterial) {
        return ShapedRecipeBuilder.shaped(pFenceGate).define('#', Items.STICK).define('W', pMaterial).pattern("#W#").pattern("#W#");
    }

    protected static void pressurePlate(Consumer<FinishedRecipe> pFinishedRecipeConsumer, ItemLike pPressurePlate, ItemLike pMaterial) {
        pressurePlateBuilder(pPressurePlate, Ingredient.of(pMaterial)).unlockedBy(getHasName(pMaterial), has(pMaterial)).save(pFinishedRecipeConsumer);
    }

    protected static RecipeBuilder pressurePlateBuilder(ItemLike pPressurePlate, Ingredient pMaterial) {
        return ShapedRecipeBuilder.shaped(pPressurePlate).define('#', pMaterial).pattern("##");
    }

    protected static void slab(Consumer<FinishedRecipe> pFinishedRecipeConsumer, ItemLike pSlab, ItemLike pMaterial) {
        slabBuilder(pSlab, Ingredient.of(pMaterial)).unlockedBy(getHasName(pMaterial), has(pMaterial)).save(pFinishedRecipeConsumer);
    }

    protected static RecipeBuilder slabBuilder(ItemLike pSlab, Ingredient pMaterial) {
        return ShapedRecipeBuilder.shaped(pSlab, 6).define('#', pMaterial).pattern("###");
    }

    protected static RecipeBuilder stairBuilder(ItemLike pStairs, Ingredient pMaterial) {
        return ShapedRecipeBuilder.shaped(pStairs, 4).define('#', pMaterial).pattern("#  ").pattern("## ").pattern("###");
    }

    protected static RecipeBuilder trapdoorBuilder(ItemLike pTrapdoor, Ingredient pMaterial) {
        return ShapedRecipeBuilder.shaped(pTrapdoor, 2).define('#', pMaterial).pattern("###").pattern("###");
    }

    protected static RecipeBuilder signBuilder(ItemLike pSign, Ingredient pMaterial) {
        return ShapedRecipeBuilder.shaped(pSign, 3).group("sign").define('#', pMaterial).define('X', Items.STICK).pattern("###").pattern("###").pattern(" X ");
    }

    protected static void coloredWoolFromWhiteWoolAndDye(Consumer<FinishedRecipe> pFinishedRecipeConsumer, ItemLike pDyedWool, ItemLike pDye) {
        ShapelessRecipeBuilder.shapeless(pDyedWool).requires(pDye).requires(Blocks.WHITE_WOOL).group("wool").unlockedBy("has_white_wool", has(Blocks.WHITE_WOOL)).save(pFinishedRecipeConsumer);
    }

    protected static void carpet(Consumer<FinishedRecipe> pFinishedRecipeConsumer, ItemLike pCarpet, ItemLike pMaterial) {
        ShapedRecipeBuilder.shaped(pCarpet, 3).define('#', pMaterial).pattern("##").group("carpet").unlockedBy(getHasName(pMaterial), has(pMaterial)).save(pFinishedRecipeConsumer);
    }

    protected static void coloredCarpetFromWhiteCarpetAndDye(Consumer<FinishedRecipe> pFinishedRecipeConsumer, ItemLike pDyedCarpet, ItemLike pDye) {
        ShapedRecipeBuilder.shaped(pDyedCarpet, 8).define('#', Blocks.WHITE_CARPET).define('$', pDye).pattern("###").pattern("#$#").pattern("###").group("carpet").unlockedBy("has_white_carpet", has(Blocks.WHITE_CARPET)).unlockedBy(getHasName(pDye), has(pDye)).save(pFinishedRecipeConsumer, getConversionRecipeName(pDyedCarpet, Blocks.WHITE_CARPET));
    }

    protected static void bedFromPlanksAndWool(Consumer<FinishedRecipe> pFinishedRecipeConsumer, ItemLike pBed, ItemLike pWool) {
        ShapedRecipeBuilder.shaped(pBed).define('#', pWool).define('X', ItemTags.PLANKS).pattern("###").pattern("XXX").group("bed").unlockedBy(getHasName(pWool), has(pWool)).save(pFinishedRecipeConsumer);
    }

    protected static void bedFromWhiteBedAndDye(Consumer<FinishedRecipe> pFinishedRecipeConsumer, ItemLike pDyedBed, ItemLike pDye) {
        ShapelessRecipeBuilder.shapeless(pDyedBed).requires(Items.WHITE_BED).requires(pDye).group("dyed_bed").unlockedBy("has_bed", has(Items.WHITE_BED)).save(pFinishedRecipeConsumer, getConversionRecipeName(pDyedBed, Items.WHITE_BED));
    }

    protected static void banner(Consumer<FinishedRecipe> pFinishedRecipeConsumer, ItemLike pBanner, ItemLike pMaterial) {
        ShapedRecipeBuilder.shaped(pBanner).define('#', pMaterial).define('|', Items.STICK).pattern("###").pattern("###").pattern(" | ").group("banner").unlockedBy(getHasName(pMaterial), has(pMaterial)).save(pFinishedRecipeConsumer);
    }

    protected static void stainedGlassFromGlassAndDye(Consumer<FinishedRecipe> pFinishedRecipeConsumer, ItemLike pStainedGlass, ItemLike pDye) {
        ShapedRecipeBuilder.shaped(pStainedGlass, 8).define('#', Blocks.GLASS).define('X', pDye).pattern("###").pattern("#X#").pattern("###").group("stained_glass").unlockedBy("has_glass", has(Blocks.GLASS)).save(pFinishedRecipeConsumer);
    }

    protected static void stainedGlassPaneFromStainedGlass(Consumer<FinishedRecipe> pFinishedRecipeConsumer, ItemLike pStainedGlassPane, ItemLike pStainedGlass) {
        ShapedRecipeBuilder.shaped(pStainedGlassPane, 16).define('#', pStainedGlass).pattern("###").pattern("###").group("stained_glass_pane").unlockedBy("has_glass", has(pStainedGlass)).save(pFinishedRecipeConsumer);
    }

    protected static void stainedGlassPaneFromGlassPaneAndDye(Consumer<FinishedRecipe> pFinishedRecipeConsumer, ItemLike pStainedGlassPane, ItemLike pDye) {
        ShapedRecipeBuilder.shaped(pStainedGlassPane, 8).define('#', Blocks.GLASS_PANE).define('$', pDye).pattern("###").pattern("#$#").pattern("###").group("stained_glass_pane").unlockedBy("has_glass_pane", has(Blocks.GLASS_PANE)).unlockedBy(getHasName(pDye), has(pDye)).save(pFinishedRecipeConsumer, getConversionRecipeName(pStainedGlassPane, Blocks.GLASS_PANE));
    }

    protected static void coloredTerracottaFromTerracottaAndDye(Consumer<FinishedRecipe> pFinishedRecipeConsumer, ItemLike pColoredTerracotta, ItemLike pDye) {
        ShapedRecipeBuilder.shaped(pColoredTerracotta, 8).define('#', Blocks.TERRACOTTA).define('X', pDye).pattern("###").pattern("#X#").pattern("###").group("stained_terracotta").unlockedBy("has_terracotta", has(Blocks.TERRACOTTA)).save(pFinishedRecipeConsumer);
    }

    protected static void concretePowder(Consumer<FinishedRecipe> pFinishedRecipeConsumer, ItemLike pDyedConcretePowder, ItemLike pDye) {
        ShapelessRecipeBuilder.shapeless(pDyedConcretePowder, 8).requires(pDye).requires(Blocks.SAND, 4).requires(Blocks.GRAVEL, 4).group("concrete_powder").unlockedBy("has_sand", has(Blocks.SAND)).unlockedBy("has_gravel", has(Blocks.GRAVEL)).save(pFinishedRecipeConsumer);
    }

    public static void candle(Consumer<FinishedRecipe> pFinishedRecipeConsumer, ItemLike pCandle, ItemLike pDye) {
        ShapelessRecipeBuilder.shapeless(pCandle).requires(Blocks.CANDLE).requires(pDye).group("dyed_candle").unlockedBy(getHasName(pDye), has(pDye)).save(pFinishedRecipeConsumer);
    }

    public static void wall(Consumer<FinishedRecipe> pFinishedRecipeConsumer, ItemLike pWall, ItemLike pMaterial) {
        wallBuilder(pWall, Ingredient.of(pMaterial)).unlockedBy(getHasName(pMaterial), has(pMaterial)).save(pFinishedRecipeConsumer);
    }

    public static RecipeBuilder wallBuilder(ItemLike pWall, Ingredient pMaterial) {
        return ShapedRecipeBuilder.shaped(pWall, 6).define('#', pMaterial).pattern("###").pattern("###");
    }

    public static void polished(Consumer<FinishedRecipe> pFinishedRecipeConsumer, ItemLike pResult, ItemLike pMaterial) {
        polishedBuilder(pResult, Ingredient.of(pMaterial)).unlockedBy(getHasName(pMaterial), has(pMaterial)).save(pFinishedRecipeConsumer);
    }

    public static RecipeBuilder polishedBuilder(ItemLike pResult, Ingredient pMaterial) {
        return ShapedRecipeBuilder.shaped(pResult, 4).define('S', pMaterial).pattern("SS").pattern("SS");
    }

    public static void cut(Consumer<FinishedRecipe> pFinishedRecipeConsumer, ItemLike pCutResult, ItemLike pMaterial) {
        cutBuilder(pCutResult, Ingredient.of(pMaterial)).unlockedBy(getHasName(pMaterial), has(pMaterial)).save(pFinishedRecipeConsumer);
    }

    public static ShapedRecipeBuilder cutBuilder(ItemLike pCutResult, Ingredient pMaterial) {
        return ShapedRecipeBuilder.shaped(pCutResult, 4).define('#', pMaterial).pattern("##").pattern("##");
    }

    public static void chiseled(Consumer<FinishedRecipe> pFinishedRecipeConsumer, ItemLike pChiseledResult, ItemLike pMaterial) {
        chiseledBuilder(pChiseledResult, Ingredient.of(pMaterial)).unlockedBy(getHasName(pMaterial), has(pMaterial)).save(pFinishedRecipeConsumer);
    }

    public static ShapedRecipeBuilder chiseledBuilder(ItemLike pChiseledResult, Ingredient pMaterial) {
        return ShapedRecipeBuilder.shaped(pChiseledResult).define('#', pMaterial).pattern("#").pattern("#");
    }

    protected static void stonecutterResultFromBase(Consumer<FinishedRecipe> pFinishedRecipeConsumer, ItemLike pResult, ItemLike pMaterial) {
        stonecutterResultFromBase(pFinishedRecipeConsumer, pResult, pMaterial, 1);
    }

    protected static void stonecutterResultFromBase(Consumer<FinishedRecipe> pFinishedRecipeConsumer, ItemLike pResult, ItemLike pMaterial, int pResultCount) {
        SingleItemRecipeBuilder.stonecutting(Ingredient.of(pMaterial), pResult, pResultCount).unlockedBy(getHasName(pMaterial), has(pMaterial)).save(pFinishedRecipeConsumer, getConversionRecipeName(pResult, pMaterial) + "_stonecutting");
    }

    protected static void smeltingResultFromBase(Consumer<FinishedRecipe> pFinishedRecipeConsumer, ItemLike pResult, ItemLike pIngredient) {
        ModCookingRecipeBuilder.smelting(Ingredient.of(pIngredient), pResult, 0.1F, 200).unlockedBy(getHasName(pIngredient), has(pIngredient)).save(pFinishedRecipeConsumer);
    }

    protected static void nineBlockStorageRecipes(Consumer<FinishedRecipe> pFinishedRecipeConsumer, ItemLike pUnpacked, ItemLike pPacked) {
        nineBlockStorageRecipes(pFinishedRecipeConsumer, pUnpacked, pPacked, getSimpleRecipeName(pPacked), (String)null, getSimpleRecipeName(pUnpacked), (String)null);
    }

    protected static void nineBlockStorageRecipesWithCustomPacking(Consumer<FinishedRecipe> pFinishedRecipeConsumer, ItemLike pUnpacked, ItemLike pPacked, String pPackingRecipeName, String pPackingRecipeGroup) {
        nineBlockStorageRecipes(pFinishedRecipeConsumer, pUnpacked, pPacked, pPackingRecipeName, pPackingRecipeGroup, getSimpleRecipeName(pUnpacked), (String)null);
    }

    protected static void nineBlockStorageRecipesRecipesWithCustomUnpacking(Consumer<FinishedRecipe> pFinishedRecipeConsumer, ItemLike pUnpacked, ItemLike pPacked, String pUnpackingRecipeName, String pUnpackingRecipeGroup) {
        nineBlockStorageRecipes(pFinishedRecipeConsumer, pUnpacked, pPacked, getSimpleRecipeName(pPacked), (String)null, pUnpackingRecipeName, pUnpackingRecipeGroup);
    }

    protected static void nineBlockStorageRecipes(Consumer<FinishedRecipe> pFinishedRecipeConsumer, ItemLike pUnpacked, ItemLike pPacked, String pPackingRecipeName, @Nullable String pPackingRecipeGroup, String pUnpackingRecipeName, @Nullable String pUnpackingRecipeGroup) {
        ShapelessRecipeBuilder.shapeless(pUnpacked, 9).requires(pPacked).group(pUnpackingRecipeGroup).unlockedBy(getHasName(pPacked), has(pPacked)).save(pFinishedRecipeConsumer, new ResourceLocation(pUnpackingRecipeName));
        ShapedRecipeBuilder.shaped(pPacked).define('#', pUnpacked).pattern("###").pattern("###").pattern("###").group(pPackingRecipeGroup).unlockedBy(getHasName(pUnpacked), has(pUnpacked)).save(pFinishedRecipeConsumer, new ResourceLocation(pPackingRecipeName));
    }

    protected static void cookRecipes(Consumer<FinishedRecipe> pFinishedRecipeConsumer, String pCookingMethod, SimpleCookingSerializer<?> pCookingSerializer, int pCookingTime) {
        simpleCookingRecipe(pFinishedRecipeConsumer, pCookingMethod, pCookingSerializer, pCookingTime, ModItems.STRANGE_FLESH.get(), ModItems.COOKED_STRANGE_FLESH.get(), 0.35F);
    }

    protected static void simpleCookingRecipe(Consumer<FinishedRecipe> pFinishedRecipeConsumer, String pCookingMethod, SimpleCookingSerializer<?> pCookingSerializer, int pCookingTime, ItemLike pIngredient, ItemLike pResult, float pExperience) {
        ModCookingRecipeBuilder.cooking(Ingredient.of(pIngredient), pResult, pExperience, pCookingTime, pCookingSerializer).unlockedBy(getHasName(pIngredient), has(pIngredient)).save(pFinishedRecipeConsumer, "ornithophobia:" + getItemName(pResult) + "_from_" + pCookingMethod);
    }

    protected static void waxRecipes(Consumer<FinishedRecipe> pFinishedRecipeConsumer) {
        HoneycombItem.WAXABLES.get().forEach((p_176578_, p_176579_) -> {
            ShapelessRecipeBuilder.shapeless(p_176579_).requires(p_176578_).requires(Items.HONEYCOMB).group(getItemName(p_176579_)).unlockedBy(getHasName(p_176578_), has(p_176578_)).save(pFinishedRecipeConsumer, getConversionRecipeName(p_176579_, Items.HONEYCOMB));
        });
    }

    protected static void generateRecipes(Consumer<FinishedRecipe> pFinishedRecipeConsumer, BlockFamily pFamily) {
        pFamily.getVariants().forEach((p_176529_, p_176530_) -> {
            BiFunction<ItemLike, ItemLike, RecipeBuilder> bifunction = shapeBuilders.get(p_176529_);
            ItemLike itemlike = getBaseBlock(pFamily, p_176529_);
            if (bifunction != null) {
                RecipeBuilder recipebuilder = bifunction.apply(p_176530_, itemlike);
                pFamily.getRecipeGroupPrefix().ifPresent((p_176601_) -> {
                    recipebuilder.group(p_176601_ + (p_176529_ == BlockFamily.Variant.CUT ? "" : "_" + p_176529_.getName()));
                });
                recipebuilder.unlockedBy(pFamily.getRecipeUnlockedBy().orElseGet(() -> {
                    return getHasName(itemlike);
                }), has(itemlike));
                recipebuilder.save(pFinishedRecipeConsumer);
            }

            if (p_176529_ == BlockFamily.Variant.CRACKED) {
                smeltingResultFromBase(pFinishedRecipeConsumer, p_176530_, itemlike);
            }

        });
    }

    protected static Block getBaseBlock(BlockFamily pFamily, BlockFamily.Variant pVariant) {
        if (pVariant == BlockFamily.Variant.CHISELED) {
            if (!pFamily.getVariants().containsKey(BlockFamily.Variant.SLAB)) {
                throw new IllegalStateException("Slab is not defined for the family.");
            } else {
                return pFamily.get(BlockFamily.Variant.SLAB);
            }
        } else {
            return pFamily.getBaseBlock();
        }
    }

    /**
     * Creates a new {@link EnterBlockTrigger} for use with recipe unlock criteria.
     */
    protected static EnterBlockTrigger.TriggerInstance insideOf(Block pBlock) {
        return new EnterBlockTrigger.TriggerInstance(EntityPredicate.Composite.ANY, pBlock, StatePropertiesPredicate.ANY);
    }

    protected static InventoryChangeTrigger.TriggerInstance has(MinMaxBounds.Ints pCount, ItemLike pItem) {
        return inventoryTrigger(ItemPredicate.Builder.item().of(pItem).withCount(pCount).build());
    }

    /**
     * Creates a new {@link InventoryChangeTrigger} that checks for a player having a certain item.
     */
    protected static InventoryChangeTrigger.TriggerInstance has(ItemLike pItemLike) {
        return inventoryTrigger(ItemPredicate.Builder.item().of(pItemLike).build());
    }

    /**
     * Creates a new {@link InventoryChangeTrigger} that checks for a player having an item within the given tag.
     */
    protected static InventoryChangeTrigger.TriggerInstance has(TagKey<Item> pTag) {
        return inventoryTrigger(ItemPredicate.Builder.item().of(pTag).build());
    }

    /**
     * Creates a new {@link InventoryChangeTrigger} that checks for a player having a certain item.
     */
    protected static InventoryChangeTrigger.TriggerInstance inventoryTrigger(ItemPredicate... pPredicates) {
        return new InventoryChangeTrigger.TriggerInstance(EntityPredicate.Composite.ANY, MinMaxBounds.Ints.ANY, MinMaxBounds.Ints.ANY, MinMaxBounds.Ints.ANY, pPredicates);
    }

    protected static String getHasName(ItemLike pItemLike) {
        return "has_" + getItemName(pItemLike);
    }

    protected static String getItemName(ItemLike pItemLike) {
        return Registry.ITEM.getKey(pItemLike.asItem()).getPath();
    }

    protected static String getSimpleRecipeName(ItemLike pItemLike) {
        return getItemName(pItemLike);
    }

    protected static String getConversionRecipeName(ItemLike pResult, ItemLike pIngredient) {
        return getItemName(pResult) + "_from_" + getItemName(pIngredient);
    }

    protected static String getSmeltingRecipeName(ItemLike pItemLike) {
        return getItemName(pItemLike) + "_from_smelting";
    }

    protected static String getBlastingRecipeName(ItemLike pItemLike) {
        return getItemName(pItemLike) + "_from_blasting";
    }

    /**
     * Gets a name for this provider, to use in logging.
     */
    public String getName() {
        return "Recipes";
    }
}


