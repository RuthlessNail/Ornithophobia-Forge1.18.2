package com.RuthlessNail.ornithophobia.item;

import com.RuthlessNail.ornithophobia.Ornithophobia;
import com.RuthlessNail.ornithophobia.entity.ModEntityTypes;
import com.RuthlessNail.ornithophobia.item.custom.ItemCompactedCoal;
import com.RuthlessNail.ornithophobia.item.custom.ItemHammer;
import com.RuthlessNail.ornithophobia.item.custom.ItemSwordOfApostate;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.ForgeSpawnEggItem;
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
            () -> new ItemHammer(Tiers.WOOD, 6, -3.6f, new Item.Properties().tab
                    (ModCreativeModeTab.ORNITHOPHOBIA_TAB))
            {
                @Override
                public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
                    if(Screen.hasShiftDown()) {
                        pTooltipComponents.add(new TranslatableComponent("tooltip.ornithophobia.wooden_hammer.tooltip"));
                    }
                    else {
                        pTooltipComponents.add(new TranslatableComponent("tooltip.ornithophobia.shift.tooltip"));
                    }
                }
            });

    public static final RegistryObject<Item> STONE_HAMMER = ITEMS.register("stone_hammer",
            () -> new ItemHammer(Tiers.STONE, 7, -3.6f, new Item.Properties().tab
                    (ModCreativeModeTab.ORNITHOPHOBIA_TAB))
            {
    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        if(Screen.hasShiftDown()) {
            pTooltipComponents.add(new TranslatableComponent("tooltip.ornithophobia.stone_hammer.tooltip"));
        }
        else {
            pTooltipComponents.add(new TranslatableComponent("tooltip.ornithophobia.shift.tooltip"));
        }
    }
            });

    public static final RegistryObject<Item> TIN_HAMMER = ITEMS.register("tin_hammer",
            () -> new ItemHammer(ModTiers.TIN, 6, -3.3f, new Item.Properties().tab
                    (ModCreativeModeTab.ORNITHOPHOBIA_TAB))
            {
                @Override
                public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
                    if(Screen.hasShiftDown()) {
                        pTooltipComponents.add(new TranslatableComponent("tooltip.ornithophobia.tin_hammer.tooltip"));
                    }
                    else {
                        pTooltipComponents.add(new TranslatableComponent("tooltip.ornithophobia.shift.tooltip"));
                    }
                }
            });

    public static final RegistryObject<Item> IRON_HAMMER = ITEMS.register("iron_hammer",
            () -> new ItemHammer(Tiers.IRON, 7, -3.5f, new Item.Properties().tab
                    (ModCreativeModeTab.ORNITHOPHOBIA_TAB))
            {
                @Override
                public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
                    if(Screen.hasShiftDown()) {
                        pTooltipComponents.add(new TranslatableComponent("tooltip.ornithophobia.iron_hammer.tooltip"));
                    }
                    else {
                        pTooltipComponents.add(new TranslatableComponent("tooltip.ornithophobia.shift.tooltip"));
                    }
                }
            });

    public static final RegistryObject<Item> GOLD_HAMMER = ITEMS.register("gold_hammer",
            () -> new ItemHammer(Tiers.GOLD, 7, -3.5f, new Item.Properties().tab
                    (ModCreativeModeTab.ORNITHOPHOBIA_TAB))
            {
                @Override
                public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
                    if(Screen.hasShiftDown()) {
                        pTooltipComponents.add(new TranslatableComponent("tooltip.ornithophobia.gold_hammer.tooltip"));
                    }
                    else {
                        pTooltipComponents.add(new TranslatableComponent("tooltip.ornithophobia.shift.tooltip"));
                    }
                }
            });

    public static final RegistryObject<Item> DIAMOND_HAMMER = ITEMS.register("diamond_hammer",
            () -> new ItemHammer(Tiers.DIAMOND, 7, -3.4f, new Item.Properties().tab
                    (ModCreativeModeTab.ORNITHOPHOBIA_TAB))
            {
                @Override
                public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
                    if(Screen.hasShiftDown()) {
                        pTooltipComponents.add(new TranslatableComponent("tooltip.ornithophobia.diamond_hammer.tooltip"));
                    }
                    else {
                        pTooltipComponents.add(new TranslatableComponent("tooltip.ornithophobia.shift.tooltip"));
                    }
                }
            });

    public static final RegistryObject<Item> TITANIUM_HAMMER = ITEMS.register("titanium_hammer",
            () -> new ItemHammer(ModTiers.TITANIUM, 7, -3.4f, new Item.Properties().tab
                    (ModCreativeModeTab.ORNITHOPHOBIA_TAB)));

    public static final RegistryObject<Item> COMPACTED_COAL = ITEMS.register("compacted_coal",
            () -> new ItemCompactedCoal(new Item.Properties().tab(ModCreativeModeTab.ORNITHOPHOBIA_TAB)));

    public static final RegistryObject<Item> IRON_PLATE = ITEMS.register("iron_plate",
            () -> new Item(new Item.Properties().tab(ModCreativeModeTab.ORNITHOPHOBIA_TAB)));
    public static final RegistryObject<Item> STEEL_PLATE = ITEMS.register("steel_plate",
            () -> new Item(new Item.Properties().tab(ModCreativeModeTab.ORNITHOPHOBIA_TAB)));

    public static final RegistryObject<Item> IRON_ROD = ITEMS.register("iron_rod",
            () -> new Item(new Item.Properties().tab(ModCreativeModeTab.ORNITHOPHOBIA_TAB)));
    public static final RegistryObject<Item> STEEL_ROD = ITEMS.register("steel_rod",
            () -> new Item(new Item.Properties().tab(ModCreativeModeTab.ORNITHOPHOBIA_TAB)));

    public static final RegistryObject<Item> TIN_SWORD = ITEMS.register("tin_sword",
            () -> new SwordItem(ModTiers.TIN,2,-2.4f,
                    new Item.Properties().tab(ModCreativeModeTab.ORNITHOPHOBIA_TAB)));
    public static final RegistryObject<Item> TIN_PICKAXE = ITEMS.register("tin_pickaxe",
            () -> new PickaxeItem(ModTiers.TIN,1,-2.8f,
                    new Item.Properties().tab(ModCreativeModeTab.ORNITHOPHOBIA_TAB).durability(59)));
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

    public static final RegistryObject<Item> STEEL_INGOT = ITEMS.register("steel_ingot",
            () -> new Item(new Item.Properties().tab(ModCreativeModeTab.ORNITHOPHOBIA_TAB)));

    public static final RegistryObject<Item> RAW_TITANIUM = ITEMS.register("raw_titanium",
            () -> new Item(new Item.Properties().tab(ModCreativeModeTab.ORNITHOPHOBIA_TAB)));

    public static final RegistryObject<Item> TITANIUM_INGOT = ITEMS.register("titanium_ingot",
            () -> new Item(new Item.Properties().tab(ModCreativeModeTab.ORNITHOPHOBIA_TAB)));

    public static final RegistryObject<Item> TITANIUM_SWORD = ITEMS.register("titanium_sword",
            () -> new SwordItem(ModTiers.TITANIUM,2,-2.4f,
                    new Item.Properties().tab(ModCreativeModeTab.ORNITHOPHOBIA_TAB)));
    public static final RegistryObject<Item> TITANIUM_PICKAXE = ITEMS.register("titanium_pickaxe",
            () -> new PickaxeItem(ModTiers.TITANIUM,1,-2.8f,
                    new Item.Properties().tab(ModCreativeModeTab.ORNITHOPHOBIA_TAB).durability(59)));
    public static final RegistryObject<Item> TITANIUM_SHOVEL = ITEMS.register("titanium_shovel",
            () -> new ShovelItem(ModTiers.TITANIUM,0.5f,-3,
                    new Item.Properties().tab(ModCreativeModeTab.ORNITHOPHOBIA_TAB)));
    public static final RegistryObject<Item> TITANIUM_AXE = ITEMS.register("titanium_axe",
            () -> new AxeItem(ModTiers.TITANIUM,4,-3.1f,
                    new Item.Properties().tab(ModCreativeModeTab.ORNITHOPHOBIA_TAB)));
    public static final RegistryObject<Item> TITANIUM_HOE = ITEMS.register("titanium_hoe",
            () -> new HoeItem(ModTiers.TITANIUM,0,-3f,
                    new Item.Properties().tab(ModCreativeModeTab.ORNITHOPHOBIA_TAB)));

    public static final RegistryObject<Item> APOSTATE_SWORD = ITEMS.register("apostate_sword",
            () -> new ItemSwordOfApostate(ModTiers.APOSTATE,14,-3.6f,
                    new Item.Properties().tab(ModCreativeModeTab.ORNITHOPHOBIA_TAB))
            {
                @Override
                public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
                    if(Screen.hasShiftDown()) {
                        pTooltipComponents.add(new TranslatableComponent("tooltip.ornithophobia.apostate_sword.tooltip"));
                    }
                    else {
                        pTooltipComponents.add(new TranslatableComponent("tooltip.ornithophobia.shift.tooltip"));
                    }
                }});


    public static final RegistryObject<Item> SPARROW_SPAWN_EGG = ITEMS.register("sparrow_spawn_egg",
            () -> new ForgeSpawnEggItem(ModEntityTypes.SPARROW,0xd9d9d9, 0xff5733,
                    new Item.Properties().tab(ModCreativeModeTab.ORNITHOPHOBIA_TAB)));

    public static final RegistryObject<Item> MUPPET_SPAWN_EGG = ITEMS.register("muppet_spawn_egg",
            () -> new ForgeSpawnEggItem(ModEntityTypes.MUPPET,0xd9d9d9, 0xff5733,
                    new Item.Properties().tab(ModCreativeModeTab.ORNITHOPHOBIA_TAB)));




    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
