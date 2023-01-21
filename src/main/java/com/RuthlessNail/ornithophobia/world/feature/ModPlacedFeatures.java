package com.RuthlessNail.ornithophobia.world.feature;

import net.minecraft.core.Holder;
import net.minecraft.data.worldgen.features.OreFeatures;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.data.worldgen.placement.VegetationPlacements;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.placement.*;

import static com.RuthlessNail.ornithophobia.world.feature.ModOrePlacement.commonOrePlacement;
import static com.RuthlessNail.ornithophobia.world.feature.ModOrePlacement.rareOrePlacement;

public class ModPlacedFeatures {
    public static final Holder<PlacedFeature> FLESH_TREE_PLACED = PlacementUtils.register("flesh_tree_placed",
            ModConfiguredFeatures.FLESH_TREE_SPAWN, VegetationPlacements.treePlacement(
                    PlacementUtils.countExtra(3, 0.1f, 2)));

    public static final Holder<PlacedFeature> EYE_ROSE_PLACED = PlacementUtils.register("eye_rose_placed",
            ModConfiguredFeatures.EYE_ROSE, RarityFilter.onAverageOnceEvery(16),
            InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome());

    public static final Holder<PlacedFeature> TIN_ORE_PLACED = PlacementUtils.register("tin_ore_placed",
            ModConfiguredFeatures.TIN_ORE, commonOrePlacement(7, // VeinsPerChunk
                    HeightRangePlacement.triangle(VerticalAnchor.absolute(-16), VerticalAnchor.absolute(94))));

    public static final Holder<PlacedFeature> TIN_ORE_LARGE_PLACED = PlacementUtils.register("tin_ore_large_placed",
            ModConfiguredFeatures.TIN_ORE_LARGE, commonOrePlacement(2,
                    HeightRangePlacement.triangle(VerticalAnchor.absolute(-16), VerticalAnchor.absolute(94))));

    public static final Holder<PlacedFeature> TITANIUM_ORE_PLACED = PlacementUtils.register("titanium_ore_placed",
            ModConfiguredFeatures.TITANIUM_ORE, commonOrePlacement(3,
                    HeightRangePlacement.triangle(VerticalAnchor.aboveBottom(0), VerticalAnchor.aboveBottom(32))));

}