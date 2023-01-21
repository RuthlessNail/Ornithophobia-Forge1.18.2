package com.RuthlessNail.ornithophobia.world;

import com.RuthlessNail.ornithophobia.biome.ModBiomesRegistry;
import com.mojang.datafixers.util.Pair;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.biome.Climate;
import terrablender.api.Region;
import terrablender.api.RegionType;

import java.util.List;
import java.util.function.Consumer;

import static terrablender.api.ParameterUtils.*;

public class ModRegion extends Region
{
    public ModRegion(ResourceLocation name, int weight)
    {
        super(name, RegionType.OVERWORLD, weight);
    }

    @Override
    public void addBiomes(Registry<Biome> registry, Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> mapper)
    {
        this.addModifiedVanillaOverworldBiomes(mapper, builder -> {

            builder.replaceBiome(Biomes.FOREST, ModBiomesRegistry.FLESH_FOREST);
            // More complex example:
            // Replace specific parameter points for the frozen peaks with our cold_blue biome
            List<Climate.ParameterPoint> forestInfectionZone = new ParameterPointListBuilder()
                    .temperature(Temperature.WARM, Temperature.HOT)
                    .humidity(Humidity.ARID, Humidity.DRY, Humidity.NEUTRAL)
                    .continentalness(Continentalness.span(Continentalness.COAST, Continentalness.FAR_INLAND), Continentalness.span(Continentalness.MID_INLAND, Continentalness.FAR_INLAND))
                    .erosion(Erosion.FULL_RANGE)
                    .depth(Depth.SURFACE, Depth.FLOOR)
                    .weirdness(Weirdness.FULL_RANGE)
                    .build();

            forestInfectionZone.forEach(point -> builder.replaceBiome(point, ModBiomesRegistry.FLESH_FOREST));
        });
    }
}