package com.RuthlessNail.ornithophobia.biome;

import com.RuthlessNail.ornithophobia.Ornithophobia;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;

public class ModBiomesRegistry
{
    public static final ResourceKey<Biome> FLESH_FOREST = register("flesh_forest");

    private static ResourceKey<Biome> register(String name)
    {
        return ResourceKey.create(Registry.BIOME_REGISTRY, new ResourceLocation(Ornithophobia.MOD_ID, name));
    }
}
