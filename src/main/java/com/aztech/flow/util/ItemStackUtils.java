package com.aztech.flow.util;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;

public class ItemStackUtils {
    public static ItemStack newItemStackFromBlockState(IBlockState state) {
        Block block = state.getBlock();
        int meta = block.getMetaFromState(state);
        return new ItemStack(block, 1, meta);
    }
}
