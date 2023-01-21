package com.RuthlessNail.ornithophobia.datagen.loot;

import com.RuthlessNail.ornithophobia.block.ModBlock;
import com.RuthlessNail.ornithophobia.item.ModItems;
import net.minecraft.data.loot.BlockLoot;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockLootTables extends BlockLoot {
    private static final float[] FLESH_LEAVES_SAPLING_CHANCES = new float[]{0.025F, 0.03125F, 0.04166666F, 0.05F};
    @Override
    protected void addTables() {
        this.dropSelf(ModBlock.EYEBALL_BLOCK.get());
        this.dropSelf(ModBlock.EYE_ROSE.get());
        this.add(ModBlock.TIN_ORE.get(), (block) ->
                createOreDrop(block, ModItems.RAW_TIN.get()));
        this.add(ModBlock.DEEPSLATE_TIN_ORE.get(), (block) ->
             createOreDrop(block, ModItems.RAW_TIN.get()));
        this.add(ModBlock.DEEPSLATE_TITANIUM_ORE.get(), (block) ->
                createOreDrop(block, ModItems.RAW_TITANIUM.get()));
        this.dropSelf(ModBlock.TIN_BLOCK.get());
        this.dropSelf(ModBlock.STEEL_BLOCK.get());
        this.dropSelf(ModBlock.TITANIUM_BLOCK.get());
        this.dropSelf(ModBlock.COMPRESSED_COBBLESTONE.get());
        this.dropSelf(ModBlock.COPPER_LAMP.get());
        this.dropPottedContents(ModBlock.POTTED_EYE_ROSE.get());
        this.dropSelf(ModBlock.FLESH_LOG.get());
        this.dropSelf(ModBlock.STRIPPED_FLESH_LOG.get());
        this.dropSelf(ModBlock.FLESH_WOOD.get());
        this.dropSelf(ModBlock.STRIPPED_FLESH_WOOD.get());
        this.dropSelf(ModBlock.WALKWAY.get());
        this.dropSelf(ModBlock.FLESH_SAPLING.get());
        this.add(ModBlock.FLESH_LEAVES.get(), (block ->
                createLeavesDrops(block, ModBlock.FLESH_SAPLING.get(), FLESH_LEAVES_SAPLING_CHANCES)));
        this.add(ModBlock.EYEBALL_LEAVES.get(), (block ->
                createLeavesDrops(block, ModBlock.FLESH_SAPLING.get(), FLESH_LEAVES_SAPLING_CHANCES)));


    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return ModBlock.BLOCKS.getEntries().stream().map(RegistryObject::get)::iterator;
    }
}
