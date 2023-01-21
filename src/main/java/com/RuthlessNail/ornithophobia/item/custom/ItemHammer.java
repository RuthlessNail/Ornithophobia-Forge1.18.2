package com.RuthlessNail.ornithophobia.item.custom;

import com.RuthlessNail.ornithophobia.item.IVaildBreakChecker;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Registry;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundBlockUpdatePacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.Nullable;


import javax.annotation.Nonnull;
import java.util.List;
import java.util.stream.Stream;

import static net.minecraft.sounds.SoundEvents.ITEM_BREAK;
import static net.minecraftforge.common.ForgeHooks.getCraftingPlayer;

public class ItemHammer extends DiggerItem {

    public ItemHammer(Tier pTier, int pAttackDamageModifier, float pAttackSpeedModifier, Item.Properties pProperties) {
        super((float)pAttackDamageModifier, pAttackSpeedModifier, pTier, BlockTags.MINEABLE_WITH_PICKAXE, pProperties);

    }


    /**
     * Breaks blocks within the given radius on the axis the player is facing.
     * If the player is facing the X axis and the radius is 1, a 3x3 area will be destroyed on the X axis.
     *
     * @param level          world the player is in
     * @param player         the player
     * @param radius         radius to break
     * @param vaildBreakChecker check to see if a block can be broken
     */
    public static void breakInRadius(Level level, Player player, int radius, BlockPos originPos, IVaildBreakChecker vaildBreakChecker) {
        if (!level.isClientSide) {
            Stream<BlockPos> brokenBlocks = getBreakBlocks(level, player, radius, originPos);
            ItemStack heldItem = player.getMainHandItem();
            brokenBlocks.forEach(pos -> {
                if (!pos.equals(originPos)) { // Vanilla handles this
                    BlockState state = level.getBlockState(pos);
                    if (vaildBreakChecker.canBreak(state)) {
                        ServerPlayer serverPlayer = (ServerPlayer) player;
                        if (player.getAbilities().instabuild) {
                            if (state.onDestroyedByPlayer(level, pos, player, true, state.getFluidState()))
                                state.getBlock().destroy(level, pos, state);
                        } else {
                            BlockEvent.BreakEvent event = new BlockEvent.BreakEvent(level, pos, state, player);
                            MinecraftForge.EVENT_BUS.post(event);

                            if (event.isCanceled()) {
                                // Forge copy
                                serverPlayer.connection.send(new ClientboundBlockUpdatePacket(level, pos));
                                BlockEntity tile = level.getBlockEntity(pos);
                                if (tile != null) {
                                    Packet<?> packet = tile.getUpdatePacket();
                                    if (packet != null) {
                                        serverPlayer.connection.send(packet);
                                    }
                                }
                            } else {
                                heldItem.getItem().mineBlock(heldItem, level, state, pos, player);
                                BlockEntity blockEntity = level.getBlockEntity(pos);
                                state.getBlock().destroy(level, pos, state);
                                state.getBlock().playerDestroy(level, player, pos, state, blockEntity, heldItem);
                                state.getBlock().popExperience((ServerLevel) level, pos, event.getExpToDrop());

                                level.removeBlock(pos, false);
                                level.levelEvent(2001, pos, Block.getId(state));
                                serverPlayer.connection.send(new ClientboundBlockUpdatePacket(level, pos));
                            }
                        }
                    }
                }
            });
        }
    }

    public static Stream<BlockPos> getBreakBlocks(Level level, Player player, int radius, BlockPos originPosition) {
        return getBreakBlocks(level, player, radius, 0, originPosition);
    }

    /**
     * Returns a list of the blocks that would be broken in breakInRadius, but doesn't break them.
     *
     * @param level  world of player
     * @param player player breaking
     * @param radius radius to break in
     * @param depth  depth to break in
     * @return a list of blocks that would be broken with the given radius and tool
     */
    public static Stream<BlockPos> getBreakBlocks(Level level, Player player, int radius, int depth, BlockPos originPosition) {
        Stream<BlockPos> potentialBrokenBlocks = Stream.of();

        Vec3 eyePosition = player.getEyePosition();
        Vec3 rotation = player.getViewVector(1);
        AttributeInstance attribute = player.getAttribute(ForgeMod.REACH_DISTANCE.get());
        //noinspection ConstantConditions
        double reach = attribute.getValue();
        Vec3 combined = eyePosition.add(rotation.x * reach, rotation.y * reach, rotation.z * reach);

        BlockHitResult rayTraceResult = level.clip(new ClipContext(player.getEyePosition(), combined, ClipContext.Block.OUTLINE, ClipContext.Fluid.NONE, player));

        if (rayTraceResult.getType() == HitResult.Type.BLOCK) {
            Direction side = rayTraceResult.getDirection();

            boolean doX = side.getStepX() == 0;
            boolean doY = side.getStepY() == 0;
            boolean doZ = side.getStepZ() == 0;

            BlockPos begin = new BlockPos(doX ? -radius : 0, doY ? -radius : 0, doZ ? -radius : 0);
            BlockPos end = new BlockPos(doX ? radius : depth * -side.getStepX(), doY ? radius : depth * -side.getStepY(), doZ ? radius : depth * -side.getStepZ());

            return BlockPos.betweenClosedStream(originPosition.offset(begin), originPosition.offset(end));
        }

        return potentialBrokenBlocks;
    }

    @Override
    public int getBurnTime(ItemStack itemStack, @Nullable RecipeType<?> recipeType) {
        return this.getTier() == Tiers.WOOD ? 400 : 0;
    }

    @Override
    public boolean isDamageable(ItemStack stack) {
        return true;
    }

    @Override
    public boolean hasContainerItem(ItemStack stack) {
        return true;
    }

    @Override
    public InteractionResult useOn(UseOnContext pContext) {
        return super.useOn(pContext);
    }

    @Override
    public boolean canAttackBlock(@Nonnull BlockState state, @Nonnull Level level, @Nonnull BlockPos pos, Player player) {
        int radius = 1;
        if (player.isShiftKeyDown()) {
            radius = 0;
        }

        float originHardness = level.getBlockState(pos).getDestroySpeed(level, pos);

        // only do a 3x3 break if the player's tool is effective on the block they are breaking
        // this makes it so breaking gravel doesn't break nearby stone
        if (player.getMainHandItem().isCorrectToolForDrops(level.getBlockState(pos))) {
            breakInRadius(level, player, radius, pos, (breakState) -> {
                double hardness = breakState.getDestroySpeed(level, pos);
                boolean isEffective = player.getMainHandItem().isCorrectToolForDrops(breakState);
                boolean verifyHardness = hardness < originHardness * 5 && hardness > 0;
                return isEffective && verifyHardness;
            });
        }

        return true;
    }

    @Override
    public ItemStack getContainerItem(ItemStack itemStack) {
        // Copy the original
        var result = itemStack.copy();
        System.out.println(result.getDamageValue());
        // Damage it
        result.hurtAndBreak(1, getCraftingPlayer(), player -> {
            if (player != null) {
                player.level.playSound(null, player.getX(), player.getY(), player.getZ(), ITEM_BREAK, player.getSoundSource(), 1.0f, 1.0f);
            }
        }
);
        System.out.println(result.getDamageValue());
        return result;
    }

    @Override
    public InteractionResult onItemUseFirst(ItemStack stack, UseOnContext context) {
        return super.onItemUseFirst(stack, context);
    }
}