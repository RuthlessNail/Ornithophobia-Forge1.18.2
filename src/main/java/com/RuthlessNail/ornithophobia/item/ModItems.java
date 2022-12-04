package com.RuthlessNail.ornithophobia.item;

import com.RuthlessNail.ornithophobia.Ornithophobia;
import com.RuthlessNail.ornithophobia.item.custom.ItemCompactedCoal;
import com.RuthlessNail.ornithophobia.item.custom.ItemWoodenHammer;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, Ornithophobia.MOD_ID);

    public static final RegistryObject<Item> EYEBALL = ITEMS.register("eyeball",
            () -> new Item(new Item.Properties().tab(ModCreativeModeTab.ORNITHOPHOBIA_TAB).food(ModFoods.EYEBALL))
            {
                @Override
                public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
                    if(Screen.hasShiftDown()) {
                        pTooltipComponents.add(new TranslatableComponent("tooltip.ornithophobia.eyeball.tooltip"));
                    }
                    else {
                        pTooltipComponents.add(new TranslatableComponent("tooltip.ornithophobia.shift.tooltip"));
                    }
                }

            });

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
            () -> new AxeItem(ModTiers.TIN,6,-3.1f,
                    new Item.Properties().tab(ModCreativeModeTab.ORNITHOPHOBIA_TAB)));
    public static final RegistryObject<Item> TIN_HOE = ITEMS.register("tin_hoe",
            () -> new HoeItem(ModTiers.TIN,0,-3f,
                    new Item.Properties().tab(ModCreativeModeTab.ORNITHOPHOBIA_TAB)));

    public static final RegistryObject<Item> TIN_HELMET = ITEMS.register("tin_helmet",
            () -> new ArmorItem(ModArmorMaterials.TIN, EquipmentSlot.HEAD,
                    new Item.Properties().tab(ModCreativeModeTab.ORNITHOPHOBIA_TAB)));
    public static final RegistryObject<Item> TIN_CHESTPLATE = ITEMS.register("tin_chestplate",
            () -> new ArmorItem(ModArmorMaterials.TIN, EquipmentSlot.CHEST,
                    new Item.Properties().tab(ModCreativeModeTab.ORNITHOPHOBIA_TAB)));
    public static final RegistryObject<Item> TIN_LEGGINGS = ITEMS.register("tin_leggings",
            () -> new ArmorItem(ModArmorMaterials.TIN, EquipmentSlot.LEGS,
                    new Item.Properties().tab(ModCreativeModeTab.ORNITHOPHOBIA_TAB)));
    public static final RegistryObject<Item> TIN_BOOTS = ITEMS.register("tin_boots",
            () -> new ArmorItem(ModArmorMaterials.TIN, EquipmentSlot.FEET,
                    new Item.Properties().tab(ModCreativeModeTab.ORNITHOPHOBIA_TAB)));

    public static final RegistryObject<Item> STRANGE_FLESH = ITEMS.register("strange_flesh",
            () -> new Item(new Item.Properties().tab(ModCreativeModeTab.ORNITHOPHOBIA_TAB).food(ModFoods.STRANGE_FLESH))

            {
                @Override
                public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
                    if(Screen.hasShiftDown()) {
                        pTooltipComponents.add(new TranslatableComponent("tooltip.ornithophobia.strange_flesh.tooltip"));
                    }
                    else {
                        pTooltipComponents.add(new TranslatableComponent("tooltip.ornithophobia.shift.tooltip"));
                    }
                }

            });
    public static final RegistryObject<Item> COOKED_STRANGE_FLESH = ITEMS.register("cooked_strange_flesh",
            () -> new Item(new Item.Properties().tab(ModCreativeModeTab.ORNITHOPHOBIA_TAB).food(ModFoods.COOKED_STRANGE_FLESH))
            {
                @Override
                public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
                    if(Screen.hasShiftDown()) {
                        pTooltipComponents.add(new TranslatableComponent("tooltip.ornithophobia.cooked_flesh.tooltip"));
                    }
                    else {
                        pTooltipComponents.add(new TranslatableComponent("tooltip.ornithophobia.shift.tooltip"));
                    }
                }

            });


    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
