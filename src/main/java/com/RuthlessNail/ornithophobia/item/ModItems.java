package com.RuthlessNail.ornithophobia.item;

import com.RuthlessNail.ornithophobia.Ornithophobia;
import com.RuthlessNail.ornithophobia.item.custom.ItemCompactedCoal;
import com.RuthlessNail.ornithophobia.item.custom.ItemWoodenHammer;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, Ornithophobia.MOD_ID);

    public static final RegistryObject<Item> EYEBALL = ITEMS.register("eyeball",
            () -> new Item(new Item.Properties().tab(ModCreativeModeTab.ORNITHOPHOBIA_TAB).food(ModFoods.EYEBALL)));

    public static final RegistryObject<Item> RAW_TIN = ITEMS.register("raw_tin",
            () -> new Item(new Item.Properties().tab(ModCreativeModeTab.ORNITHOPHOBIA_TAB)));

    public static final RegistryObject<Item> TIN_INGOT = ITEMS.register("tin_ingot",
            () -> new Item(new Item.Properties().tab(ModCreativeModeTab.ORNITHOPHOBIA_TAB)));

    public static final RegistryObject<Item> WOODEN_HAMMER = ITEMS.register("wooden_hammer",
            () -> new ItemWoodenHammer(new Item.Properties().tab(ModCreativeModeTab.ORNITHOPHOBIA_TAB)));

    public static final RegistryObject<Item> COMPACTED_COAL = ITEMS.register("compacted_coal",
            () -> new ItemCompactedCoal(new Item.Properties().tab(ModCreativeModeTab.ORNITHOPHOBIA_TAB)));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
