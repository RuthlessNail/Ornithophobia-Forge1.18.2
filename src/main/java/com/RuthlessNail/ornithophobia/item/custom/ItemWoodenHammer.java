package com.RuthlessNail.ornithophobia.item.custom;

import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.ForgeHooks;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Random;

import static net.minecraft.sounds.SoundEvents.ITEM_BREAK;
import static net.minecraft.sounds.SoundEvents.SHIELD_BREAK;

public class ItemWoodenHammer extends Item {

    public ItemWoodenHammer(Properties pProperties) {
        super(pProperties.durability(60));

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
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        if(Screen.hasShiftDown()) {
            pTooltipComponents.add(new TranslatableComponent("tooltip.ornithophobia.wooden_hammer.tooltip"));
        }
        else {
            pTooltipComponents.add(new TranslatableComponent("tooltip.ornithophobia.shift.tooltip"));
        }
    }

    @Override
    public ItemStack getContainerItem(ItemStack itemStack) {
        // Copy the original
        var result = itemStack.copy();

        // Damage it
        result.hurtAndBreak(1, ForgeHooks.getCraftingPlayer(), player -> {
            if (player != null) {
                player.level.playSound(null, player.getX(), player.getY(), player.getZ(), ITEM_BREAK, player.getSoundSource(), 1.0f, 1.0f);
            }
        }
);
        return result;
    }

}