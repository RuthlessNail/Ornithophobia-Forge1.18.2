package com.RuthlessNail.ornithophobia.item;

import com.RuthlessNail.ornithophobia.Ornithophobia;
import com.RuthlessNail.ornithophobia.item.custom.ItemCompactedCoal;
import com.RuthlessNail.ornithophobia.item.custom.ItemWoodenHammer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.item.ShovelItem;
import net.minecraft.world.item.SwordItem;
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

    public static final RegistryObject<Item> TIN_SWORD = ITEMS.register("tin_sword",
            () -> new SwordItem(ModTiers.TIN,2,-2.4f,
                    new Item.Properties().tab(ModCreativeModeTab.ORNITHOPHOBIA_TAB)));
    public static final RegistryObject<Item> TIN_PICKAXE = ITEMS.register("tin_pickaxe",
            () -> new PickaxeItem(ModTiers.TIN,1,-2.8f,
                    new Item.Properties().tab(ModCreativeModeTab.ORNITHOPHOBIA_TAB)));
    public static final RegistryObject<Item> TIN_SHOVEL = ITEMS.register("tin_shovel",
            () -> new ShovelItem(ModTiers.TIN,0.5f,-3,
                    new Item.Properties().tab(ModCreativeModeTab.ORNITHOPHOBIA_TAB)));
    public static final RegistryObject<Item> TIN_AXE = ITEMS.register("tin_axe",
            () -> new SwordItem(ModTiers.TIN,6,-3.1f,
                    new Item.Properties().tab(ModCreativeModeTab.ORNITHOPHOBIA_TAB)));
    public static final RegistryObject<Item> TIN_HOE = ITEMS.register("tin_hoe",
            () -> new SwordItem(ModTiers.TIN,0,-3f,
                    new Item.Properties().tab(ModCreativeModeTab.ORNITHOPHOBIA_TAB)));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
