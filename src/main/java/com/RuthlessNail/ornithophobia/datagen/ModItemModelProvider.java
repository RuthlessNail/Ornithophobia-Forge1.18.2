package com.RuthlessNail.ornithophobia.datagen;

import com.RuthlessNail.ornithophobia.Ornithophobia;
import com.RuthlessNail.ornithophobia.item.ModItems;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

public class ModItemModelProvider extends ItemModelProvider {
    public ModItemModelProvider(DataGenerator generator, ExistingFileHelper existingFileHelper) {
        super(generator, Ornithophobia.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        simpleItem(ModItems.EYEBALL.get());
        simpleItem(ModItems.STRANGE_FLESH.get());
        simpleItem(ModItems.COOKED_STRANGE_FLESH.get());

        handheldItem(ModItems.TIN_AXE.get());
        handheldItem(ModItems.TIN_SWORD.get());
        handheldItem(ModItems.TIN_SHOVEL.get());
        handheldItem(ModItems.TIN_PICKAXE.get());
        handheldItem(ModItems.TIN_HOE.get());

        handheldItem(ModItems.TITANIUM_SWORD.get());
        handheldItem(ModItems.TITANIUM_AXE.get());

        handheldItem(ModItems.WOODEN_HAMMER.get());
        handheldItem(ModItems.STONE_HAMMER.get());
        handheldItem(ModItems.TIN_HAMMER.get());
        handheldItem(ModItems.IRON_HAMMER.get());
        handheldItem(ModItems.GOLD_HAMMER.get());
        handheldItem(ModItems.DIAMOND_HAMMER.get());

        simpleItem(ModItems.COMPACTED_COAL.get());

        simpleItem(ModItems.TIN_HELMET.get());
        simpleItem(ModItems.TIN_CHESTPLATE.get());
        simpleItem(ModItems.TIN_LEGGINGS.get());
        simpleItem(ModItems.TIN_BOOTS.get());

        simpleItem(ModItems.RAW_TIN.get());
        simpleItem(ModItems.TIN_INGOT.get());

        simpleItem(ModItems.RAW_TITANIUM.get());
        simpleItem(ModItems.TITANIUM_INGOT.get());

        simpleItem(ModItems.STEEL_INGOT.get());

        simpleItem(ModItems.IRON_PLATE.get());
        simpleItem(ModItems.STEEL_PLATE.get());
        simpleItem(ModItems.IRON_ROD.get());
        simpleItem(ModItems.STEEL_ROD.get());


    }

    private ItemModelBuilder simpleItem(Item item) {
        return withExistingParent(item.getRegistryName().getPath(),
                new ResourceLocation("item/generated")).texture("layer0",
                new ResourceLocation(Ornithophobia.MOD_ID,"item/" + item.getRegistryName().getPath()));
    }

    private ItemModelBuilder handheldItem(Item item) {
        return withExistingParent(item.getRegistryName().getPath(),
                new ResourceLocation("item/handheld")).texture("layer0",
                new ResourceLocation(Ornithophobia.MOD_ID,"item/" + item.getRegistryName().getPath()));
    }
}