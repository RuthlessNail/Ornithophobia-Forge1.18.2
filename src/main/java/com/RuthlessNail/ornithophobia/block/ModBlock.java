package com.RuthlessNail.ornithophobia.block;

import com.RuthlessNail.ornithophobia.Ornithophobia;
import com.RuthlessNail.ornithophobia.block.custom.BlockCopperLamp;
import com.RuthlessNail.ornithophobia.block.custom.BlockWalkway;
import com.RuthlessNail.ornithophobia.block.custom.ModFlammableRotatedPillarBlock;
import com.RuthlessNail.ornithophobia.item.ModCreativeModeTab;
import com.RuthlessNail.ornithophobia.item.ModItems;
import com.RuthlessNail.ornithophobia.world.feature.tree.FleshTreeGrower;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class ModBlock {
    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, Ornithophobia.MOD_ID);

    public static final  RegistryObject<Block> EYEBALL_BLOCK = registerBlock("eyeball_block",
            () -> new Block(BlockBehaviour.Properties.of(Material.NETHER_WOOD)
                    .strength(0.5f).sound(SoundType.NETHER_WART)), ModCreativeModeTab.ORNITHOPHOBIA_TAB);

    public static final  RegistryObject<Block> WALKWAY = registerBlock("walkway",
            () -> new BlockWalkway(BlockBehaviour.Properties.of(Material.METAL)
                    .strength(3f).sound(SoundType.METAL)), ModCreativeModeTab.ORNITHOPHOBIA_TAB);

    public static final  RegistryObject<Block> TIN_ORE = registerBlock("tin_ore",
            () -> new Block(BlockBehaviour.Properties.of(Material.STONE)
                    .strength(3f, 2.5f).sound(SoundType.STONE).requiresCorrectToolForDrops()), ModCreativeModeTab.ORNITHOPHOBIA_TAB);

    public static final  RegistryObject<Block> DEEPSLATE_TIN_ORE = registerBlock("deepslate_tin_ore",
            () -> new Block(BlockBehaviour.Properties.of(Material.STONE)
                    .strength(4.5f, 2.5f).sound(SoundType.DEEPSLATE).requiresCorrectToolForDrops()), ModCreativeModeTab.ORNITHOPHOBIA_TAB);

    public static final  RegistryObject<Block> TIN_BLOCK = registerBlock("tin_block",
            () -> new Block(BlockBehaviour.Properties.of(Material.METAL)
                    .strength(4f, 20f).sound(SoundType.METAL).requiresCorrectToolForDrops()), ModCreativeModeTab.ORNITHOPHOBIA_TAB);

    public static final  RegistryObject<Block> STEEL_BLOCK = registerBlock("steel_block",
            () -> new Block(BlockBehaviour.Properties.of(Material.METAL)
                    .strength(20f, 40f).sound(SoundType.METAL).requiresCorrectToolForDrops()), ModCreativeModeTab.ORNITHOPHOBIA_TAB);

    public static final  RegistryObject<Block> DEEPSLATE_TITANIUM_ORE = registerBlock("deepslate_titanium_ore",
            () -> new Block(BlockBehaviour.Properties.of(Material.STONE)
                    .strength(30f, 15f).sound(SoundType.DEEPSLATE).requiresCorrectToolForDrops()), ModCreativeModeTab.ORNITHOPHOBIA_TAB);

    public static final  RegistryObject<Block> TITANIUM_BLOCK = registerBlock("titanium_block",
            () -> new Block(BlockBehaviour.Properties.of(Material.METAL)
                    .strength(50f, 1200f).sound(SoundType.NETHERITE_BLOCK).requiresCorrectToolForDrops()), ModCreativeModeTab.ORNITHOPHOBIA_TAB);

    public static final  RegistryObject<Block> EYE_ROSE = registerBlock("eye_rose",
            () -> new FlowerBlock(MobEffects.BLINDNESS, 8, BlockBehaviour.Properties.copy(Blocks.DANDELION)
                    .noOcclusion()), ModCreativeModeTab.ORNITHOPHOBIA_TAB);

    public static final  RegistryObject<Block> POTTED_EYE_ROSE = registerBlockWithoutBlockItem("potted_eye_rose",
            () -> new FlowerPotBlock(null, ModBlock.EYE_ROSE, BlockBehaviour.Properties.copy(Blocks.POTTED_DANDELION)
                    .noOcclusion()));

    public static final  RegistryObject<Block> COPPER_LAMP = registerBlock("copper_lamp",
            () -> new BlockCopperLamp(BlockBehaviour.Properties.of(Material.METAL).strength(2f).requiresCorrectToolForDrops().
                    lightLevel((state) -> state.getValue(BlockCopperLamp.CLICKED) ? 15:0)), ModCreativeModeTab.ORNITHOPHOBIA_TAB);

    public static final  RegistryObject<Block> FLESH_LOG = registerBlock("flesh_log",
            () -> new ModFlammableRotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.OAK_LOG)),
            ModCreativeModeTab.ORNITHOPHOBIA_TAB);
    public static final  RegistryObject<Block> FLESH_WOOD = registerBlock("flesh_wood",
            () -> new ModFlammableRotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.OAK_WOOD)),
            ModCreativeModeTab.ORNITHOPHOBIA_TAB);
    public static final  RegistryObject<Block> STRIPPED_FLESH_WOOD = registerBlock("stripped_flesh_wood",
            () -> new ModFlammableRotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.STRIPPED_OAK_WOOD)),
            ModCreativeModeTab.ORNITHOPHOBIA_TAB);
    public static final  RegistryObject<Block> STRIPPED_FLESH_LOG = registerBlock("stripped_flesh_log",
            () -> new ModFlammableRotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.STRIPPED_OAK_LOG)),
            ModCreativeModeTab.ORNITHOPHOBIA_TAB);

    public static final  RegistryObject<Block> FLESH_LEAVES = registerBlock("flesh_leaves",
            () -> new LeavesBlock(BlockBehaviour.Properties.copy(Blocks.OAK_LEAVES)) {
                @Override
                public boolean isFlammable(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                    return true;
                }

                @Override
                public int getFlammability(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                    return 60;
                }

                @Override
                public int getFireSpreadSpeed(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                    return 30;
                }
            }, ModCreativeModeTab.ORNITHOPHOBIA_TAB);


    public static final  RegistryObject<Block> EYEBALL_LEAVES = registerBlock("eyeball_leaves",
            () -> new LeavesBlock(BlockBehaviour.Properties.copy(Blocks.OAK_LEAVES)) {
                @Override
                public boolean isFlammable(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                    return true;
                }

                @Override
                public int getFlammability(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                    return 60;
                }

                @Override
                public int getFireSpreadSpeed(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                    return 30;
                }
            }, ModCreativeModeTab.ORNITHOPHOBIA_TAB);

    public static final  RegistryObject<Block> FLESH_SAPLING = registerBlock("flesh_sapling",
            () -> new SaplingBlock(new FleshTreeGrower(),BlockBehaviour.Properties.copy(Blocks.OAK_SAPLING)),
            ModCreativeModeTab.ORNITHOPHOBIA_TAB);

    public static final  RegistryObject<Block> COMPRESSED_COBBLESTONE = registerBlock("compressed_cobblestone",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.COBBLESTONE)
                    .strength(4.0f, 12.0f)), ModCreativeModeTab.ORNITHOPHOBIA_TAB);


    private static <T extends Block> RegistryObject<T> registerBlockWithoutBlockItem(String name, Supplier<T> block) {
        return BLOCKS.register(name, block);
    }

    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block, CreativeModeTab tab) {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn, tab);
        return toReturn;
    }

    private static <T extends Block> RegistryObject<Item> registerBlockItem(String name, RegistryObject<T> block, CreativeModeTab tab) {
        return ModItems.ITEMS.register(name, () -> new BlockItem(block.get(),
                new Item.Properties().tab(tab)));
    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}
