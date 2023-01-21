package com.RuthlessNail.ornithophobia.item;

import net.minecraft.world.level.block.state.BlockState;

public interface IVaildBreakChecker {

    boolean canBreak(BlockState state);
}