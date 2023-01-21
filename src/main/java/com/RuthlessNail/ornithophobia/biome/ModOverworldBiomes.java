package com.RuthlessNail.ornithophobia.biome;

import com.RuthlessNail.ornithophobia.world.feature.ModBiomeFeature;
import net.minecraft.data.worldgen.BiomeDefaultFeatures;
import net.minecraft.sounds.Music;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.*;

import javax.annotation.Nullable;

public class ModOverworldBiomes {
    @Nullable
    private static final Music NORMAL_MUSIC = null;

    protected static int calculateSkyColor(float color) {
        float $$1 = color / 3.0F;
        $$1 = Mth.clamp($$1, -1.0F, 1.0F);
        return Mth.hsvToRgb(0.62222224F - $$1 * 0.05F, 0.5F + $$1 * 0.1F, 1.0F);
    }

    private static Biome biome(Biome.Precipitation precipitation, Biome.BiomeCategory category, float temperature, float downfall, MobSpawnSettings.Builder spawnBuilder, BiomeGenerationSettings.Builder biomeBuilder, @Nullable Music music) {
        return biome(precipitation, category, temperature, downfall, 4159204, 329011, spawnBuilder, biomeBuilder, music);
    }

    private static Biome biome(Biome.Precipitation precipitation, Biome.BiomeCategory category, float temperature, float downfall, int waterColor, int waterFogColor, MobSpawnSettings.Builder spawnBuilder, BiomeGenerationSettings.Builder biomeBuilder, @Nullable Music music) {
        return (new Biome.BiomeBuilder()).precipitation(precipitation).biomeCategory(category).temperature(temperature).downfall(downfall).specialEffects((new BiomeSpecialEffects.Builder()).waterColor(waterColor).waterFogColor(waterFogColor).fogColor(12638463).skyColor(calculateSkyColor(temperature)).ambientMoodSound(AmbientMoodSettings.LEGACY_CAVE_SETTINGS).backgroundMusic(music).build()).mobSpawnSettings(spawnBuilder.build()).generationSettings(biomeBuilder.build()).build();
    }

    private static Biome biomeWithColorOverrides(Biome.Precipitation precipitation, float temperature, float downfall,
                                                 int waterColor, int waterFogColor, int grassColor, int foliageColor,
                                                 MobSpawnSettings.Builder spawnBuilder, BiomeGenerationSettings.Builder biomeBuilder,
                                                 @Nullable Music music)
    {
        return (new Biome.BiomeBuilder()).precipitation(precipitation).temperature(temperature).downfall(downfall).specialEffects((new BiomeSpecialEffects.Builder()).waterColor(waterColor).waterFogColor(waterFogColor).fogColor(12638463).grassColorOverride(grassColor).foliageColorOverride(foliageColor).skyColor(calculateSkyColor(temperature)).ambientMoodSound(AmbientMoodSettings.LEGACY_CAVE_SETTINGS).backgroundMusic(music).build()).mobSpawnSettings(spawnBuilder.build()).generationSettings(biomeBuilder.build()).build();
    }

    private static Biome biomeWithColorOverrides(Biome.Precipitation precipitation, Biome.BiomeCategory category, float temperature, float downfall,
                                                 int waterColor, int waterFogColor, int fogColor, int grassColor,
                                                 int foliageColor, int skyColor, MobSpawnSettings.Builder spawnBuilder,
                                                 BiomeGenerationSettings.Builder biomeBuilder, @Nullable Music music)
    {
        return (new Biome.BiomeBuilder()).biomeCategory(category).precipitation(precipitation).temperature(temperature).downfall(downfall).specialEffects((new BiomeSpecialEffects.Builder()).waterColor(waterColor).waterFogColor(waterFogColor).fogColor(fogColor).grassColorOverride(grassColor).foliageColorOverride(foliageColor).skyColor(skyColor).ambientMoodSound(AmbientMoodSettings.LEGACY_CAVE_SETTINGS).backgroundMusic(music).build()).mobSpawnSettings(spawnBuilder.build()).generationSettings(biomeBuilder.build()).build();
    }

    private static void globalOverworldGeneration(BiomeGenerationSettings.Builder builder) {
        BiomeDefaultFeatures.addDefaultCarversAndLakes(builder);
        BiomeDefaultFeatures.addDefaultCrystalFormations(builder);
        BiomeDefaultFeatures.addDefaultMonsterRoom(builder);
        BiomeDefaultFeatures.addDefaultUndergroundVariety(builder);
        BiomeDefaultFeatures.addDefaultSprings(builder);
        BiomeDefaultFeatures.addSurfaceFreezing(builder);
    }

    public static Biome fleshForest() {
        MobSpawnSettings.Builder spawnBuilder = new MobSpawnSettings.Builder();
        ModBiomeFeature.fleshForestSpawn(spawnBuilder);

        BiomeGenerationSettings.Builder biomeBuilder = new BiomeGenerationSettings.Builder();
        BiomeDefaultFeatures.addFossilDecoration(biomeBuilder);
        globalOverworldGeneration(biomeBuilder);
        BiomeDefaultFeatures.addDefaultOres(biomeBuilder);
        BiomeDefaultFeatures.addDefaultSoftDisks(biomeBuilder);
        BiomeDefaultFeatures.addDefaultGrass(biomeBuilder);
        ModBiomeFeature.addEyeballFlowers(biomeBuilder);
        ModBiomeFeature.addFleshTrees(biomeBuilder);
        BiomeDefaultFeatures.addDefaultMushrooms(biomeBuilder);
        return biomeWithColorOverrides(Biome.Precipitation.NONE, Biome.BiomeCategory.FOREST,1.0F, 0.0F, 0xC23930,
                0xECB4B2, 0xECB4B2, 0x9C2927, 0xB9302E, 0xB9302E,
                spawnBuilder, biomeBuilder, NORMAL_MUSIC);
    }
}