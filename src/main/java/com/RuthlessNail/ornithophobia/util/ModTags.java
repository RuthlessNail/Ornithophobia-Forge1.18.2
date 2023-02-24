package com.RuthlessNail.ornithophobia.util;

import com.RuthlessNail.ornithophobia.Ornithophobia;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class ModTags {
    public static class Blocks {
        public static final TagKey<Block> FLESH_LOGS
                = tag("flesh_logs");
        public static final TagKey<Block> NEEDS_TITANIUM_TOOL
                = tag("need_titanium_tool");

        private static TagKey<Block> tag(String name) {
            return BlockTags.create(new ResourceLocation(Ornithophobia.MOD_ID, name));
        }

        private static TagKey<Block> forgeTag(String name) {
            return BlockTags.create(new ResourceLocation("forge", name));
        }
    }

    public static class Items {

        public static final TagKey<Item> FLESH_LOGS = tag("flesh_logs");
        public static final TagKey<Item> HAMMER = tag("hammer");

        private static TagKey<Item> tag(String name) {
            return ItemTags.create(new ResourceLocation(Ornithophobia.MOD_ID, name));
        }

        private static TagKey<Item> forgeTag(String name) {
            return ItemTags.create(new ResourceLocation("forge", name));
        }
    }
}
