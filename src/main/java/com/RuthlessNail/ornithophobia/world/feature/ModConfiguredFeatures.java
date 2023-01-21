package com.RuthlessNail.ornithophobia.world.feature;

import com.RuthlessNail.ornithophobia.block.ModBlock;
import net.minecraft.core.Holder;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.data.worldgen.features.OreFeatures;
import net.minecraft.data.worldgen.features.TreeFeatures;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.util.random.SimpleWeightedRandomList;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.WeightedPlacedFeature;
import net.minecraft.world.level.levelgen.feature.configurations.*;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.foliageplacers.BlobFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FancyFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.RandomSpreadFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.WeightedStateProvider;
import net.minecraft.world.level.levelgen.feature.trunkplacers.BendingTrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.FancyTrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.StraightTrunkPlacer;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

import java.util.List;

public class ModConfiguredFeatures {
    public static final Holder<ConfiguredFeature<TreeConfiguration, ?>> FLESH_TREE =
            FeatureUtils.register("ebony", Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(
                    BlockStateProvider.simple(ModBlock.FLESH_LOG.get()),
                    new FancyTrunkPlacer(10, 6, 3),
                    new WeightedStateProvider
                            (SimpleWeightedRandomList.<BlockState>builder().add(ModBlock.FLESH_LEAVES.get().defaultBlockState(), 3)
                            .add(ModBlock.EYEBALL_LEAVES.get().defaultBlockState(), 1)),
                    new RandomSpreadFoliagePlacer(ConstantInt.of(2), ConstantInt.of(0), ConstantInt.of(2), 50),
                    new TwoLayersFeatureSize(1, 0, 2)).build());




    public static final Holder<PlacedFeature> FLESH_TREE_CHECKED = PlacementUtils.register("flesh_tree_checked", FLESH_TREE,
            PlacementUtils.filteredByBlockSurvival(ModBlock.FLESH_SAPLING.get()));

    public static final Holder<ConfiguredFeature<RandomFeatureConfiguration, ?>> FLESH_TREE_SPAWN =
            FeatureUtils.register("flesh_tree_spawn", Feature.RANDOM_SELECTOR,
                    new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(FLESH_TREE_CHECKED,
                            0.5F)), FLESH_TREE_CHECKED));

    public static final Holder<ConfiguredFeature<RandomPatchConfiguration, ?>> EYE_ROSE =
            FeatureUtils.register("flower_eye_rose", Feature.FLOWER,
                    new RandomPatchConfiguration(16, 6, 2, PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK,
                            new SimpleBlockConfiguration(BlockStateProvider.simple(ModBlock.EYE_ROSE.get())))));

    public static final List<OreConfiguration.TargetBlockState> OVERWORLD_TIN_ORES = List.of(
            OreConfiguration.target(OreFeatures.STONE_ORE_REPLACEABLES, ModBlock.TIN_ORE.get().defaultBlockState()),
            OreConfiguration.target(OreFeatures.DEEPSLATE_ORE_REPLACEABLES, ModBlock.DEEPSLATE_TIN_ORE.get().defaultBlockState()));

    public static final List<OreConfiguration.TargetBlockState> OVERWORLD_TITANIUM_ORES = List.of(
            OreConfiguration.target(OreFeatures.DEEPSLATE_ORE_REPLACEABLES, ModBlock.DEEPSLATE_TITANIUM_ORE.get().defaultBlockState()));

    public static final Holder<ConfiguredFeature<OreConfiguration, ?>> TIN_ORE = FeatureUtils.register("tin_ore",
            Feature.ORE, new OreConfiguration(OVERWORLD_TIN_ORES, 6));

    public static final Holder<ConfiguredFeature<OreConfiguration, ?>> TIN_ORE_LARGE = FeatureUtils.register("tin_ore_large",
            Feature.ORE, new OreConfiguration(OVERWORLD_TIN_ORES, 12));

    public static final Holder<ConfiguredFeature<OreConfiguration, ?>> TITANIUM_ORE = FeatureUtils.register("titanium_ore",
            Feature.ORE, new OreConfiguration(OVERWORLD_TITANIUM_ORES, 3));



}
