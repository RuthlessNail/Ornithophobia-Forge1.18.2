package com.RuthlessNail.ornithophobia;


import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public final class ModItemTags {
    public static final TagKey<Item> FLESH_LOGS = bind("ornithophobia:flesh_logs");

    private ModItemTags() {
    }

    private static TagKey<Item> bind(String pName) {
        return TagKey.create(Registry.ITEM_REGISTRY, new ResourceLocation(pName));
    }

    public static TagKey<Item> create(final ResourceLocation name) {
        return TagKey.create(Registry.ITEM_REGISTRY, name);
    }
}