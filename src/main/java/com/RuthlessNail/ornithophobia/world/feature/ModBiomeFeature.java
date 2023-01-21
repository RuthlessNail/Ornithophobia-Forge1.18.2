package com.RuthlessNail.ornithophobia.world.feature;

import net.minecraft.data.worldgen.placement.VegetationPlacements;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.BiomeGenerationSettings;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.levelgen.GenerationStep;

import static net.minecraft.data.worldgen.BiomeDefaultFeatures.caveSpawns;
import static net.minecraft.data.worldgen.BiomeDefaultFeatures.monsters;

public class ModBiomeFeature {
    public static void addEyeballFlowers(BiomeGenerationSettings.Builder pBuilder) {
        pBuilder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, ModPlacedFeatures.EYE_ROSE_PLACED);
    }

    public static void addFleshTrees(BiomeGenerationSettings.Builder pBuilder) {
        pBuilder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, ModPlacedFeatures.FLESH_TREE_PLACED);
    }

    public static void fleshForestSpawn(MobSpawnSettings.Builder pBuilder) {
        caveSpawns(pBuilder);
        monsters(pBuilder, 19, 1, 100, false);
    }
}
