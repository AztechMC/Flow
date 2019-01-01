package com.aztech.flow.blocks.altar;

import javax.annotation.Nonnull;

import com.aztech.flow.container.BasicContainer;
import com.aztech.flow.items.ModItems;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class ContainerAltar extends BasicContainer {

	public ContainerAltar(InventoryPlayer playerInv, final TileEntityAltar altar) {
		IItemHandler inventory = altar.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.NORTH);
		addSlotToContainer(new SlotItemHandler(inventory, 0, 80, 35) {
			@Override
			public void onSlotChanged() {
				altar.markDirty();
			}

			@Override
			public boolean isItemValid(@Nonnull ItemStack stack) {

				return super.isItemValid(stack) && stack.getItem() == ModItems.MANA_SHARD;
			}
		});

		this.createInvetoryForPlayer(playerInv);
	}




}
