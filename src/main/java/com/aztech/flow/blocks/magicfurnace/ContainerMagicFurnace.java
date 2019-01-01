package com.aztech.flow.blocks.magicfurnace;

import com.aztech.flow.container.BasicContainer;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class ContainerMagicFurnace extends BasicContainer {

	public ContainerMagicFurnace(InventoryPlayer playerInv, final TileEntityMagicFurnace magicFurnace) {
		IItemHandler inventory = magicFurnace.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.NORTH);
		
		addSlotToContainer(new SlotItemHandler(inventory, 0, 56, 35) {
			@Override
			public void onSlotChanged() {
				magicFurnace.markDirty();
			}
			
		});
		
		addSlotToContainer(new SlotItemHandler(inventory, 1, 115, 35) {
			
			@Override
			public void onSlotChanged() {
				magicFurnace.markDirty();
			}
			
		});
		
		this.createInvetoryForPlayer(playerInv);


	}

}
