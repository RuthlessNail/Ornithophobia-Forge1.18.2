package com.RuthlessNail.ornithophobia.datagen;

import com.RuthlessNail.ornithophobia.Ornithophobia;
import com.RuthlessNail.ornithophobia.block.ModBlock;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;

import java.util.function.Function;

public class ModBlockStateProvider extends BlockStateProvider {
    public ModBlockStateProvider(DataGenerator gen, ExistingFileHelper exFileHelper) {
        super(gen, Ornithophobia.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        simpleBlock(ModBlock.TIN_BLOCK.get());
        simpleBlock(ModBlock.TIN_ORE.get());
        simpleBlock(ModBlock.DEEPSLATE_TIN_ORE.get());
        simpleBlock(ModBlock.WALKWAY.get());
        simpleBlock(ModBlock.EYEBALL_BLOCK.get());
        simpleBlock(ModBlock.TITANIUM_BLOCK.get());
        simpleBlock(ModBlock.DEEPSLATE_TITANIUM_ORE.get());
        simpleBlock(ModBlock.COMPRESSED_COBBLESTONE.get());
        simpleBlock(ModBlock.STEEL_BLOCK.get());

        blockTexture(ModBlock.TIN_BLOCK.get());


        logBlock((RotatedPillarBlock) ModBlock.FLESH_LOG.get());
        axisBlock((RotatedPillarBlock) ModBlock.FLESH_WOOD.get(), blockTexture(ModBlock.FLESH_LOG.get()), blockTexture(ModBlock.FLESH_LOG.get()));
        axisBlock((RotatedPillarBlock) ModBlock.STRIPPED_FLESH_LOG.get(), new ResourceLocation(Ornithophobia.MOD_ID, "block/stripped_flesh_log"),
                new ResourceLocation(Ornithophobia.MOD_ID, "block/stripped_flesh_log_top"));
        axisBlock((RotatedPillarBlock) ModBlock.STRIPPED_FLESH_WOOD.get(), new ResourceLocation(Ornithophobia.MOD_ID, "block/stripped_flesh_log"),
                new ResourceLocation(Ornithophobia.MOD_ID, "block/stripped_flesh_log"));

        simpleBlock(ModBlock.FLESH_LEAVES.get());
        simpleBlock(ModBlock.EYEBALL_LEAVES.get());


        simpleBlock(ModBlock.EYE_ROSE.get(), models().cross(ModBlock.EYE_ROSE.get().getRegistryName().getPath(),
                blockTexture(ModBlock.EYE_ROSE.get())));
        simpleBlock(ModBlock.FLESH_SAPLING.get(), models().cross(ModBlock.FLESH_SAPLING.get().getRegistryName().getPath(),
                blockTexture(ModBlock.FLESH_SAPLING.get())));

        simpleBlock(ModBlock.POTTED_EYE_ROSE.get(), flowerPotCross(ModBlock.POTTED_EYE_ROSE.get().getRegistryName().getPath()));
    }

    public ModelFile flowerPotCross(String name) {
        return models().withExistingParent(name, "flower_pot_cross");
    }

    public void makeCrop(CropBlock block, String modelName, String textureName) {
        Function<BlockState, ConfiguredModel[]> function = state -> states(state, block, modelName, textureName);

        getVariantBuilder(block).forAllStates(function);
    }

    private ConfiguredModel[] states(BlockState state, CropBlock block, String modelName, String textureName) {
        ConfiguredModel[] models = new ConfiguredModel[1];
        models[0] = new ConfiguredModel(models().crop(modelName + state.getValue(block.getAgeProperty()),
                new ResourceLocation(Ornithophobia.MOD_ID, "block/" + textureName + state.getValue(block.getAgeProperty()))));

        return models;
    }
}