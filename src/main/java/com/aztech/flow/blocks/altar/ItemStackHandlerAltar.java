package com.aztech.flow.blocks.altar;

import net.minecraft.item.ItemStack;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;

public class ItemStackHandlerAltar extends ItemStackHandler {
    
    ItemStackHandlerAltar(int size) {
        super(size);
    }

    @Override
    @Nonnull
    public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate) {
        if (!BlockAltar.isItemAllowed(stack)) {
            return stack;
        }
        return super.insertItem(slot, stack, simulate);
    }
}
