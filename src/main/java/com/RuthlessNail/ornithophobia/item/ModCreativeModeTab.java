package com.RuthlessNail.ornithophobia.item;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemNameBlockItem;
import net.minecraft.world.item.ItemStack;

public class ModCreativeModeTab {
    public static final CreativeModeTab ORNITHOPHOBIA_TAB = new CreativeModeTab("ornithophobiatab") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(ModItems.EYEBALL.get());
        }
    };
}
