package com.aztech.flow.blocks.magicfurnace;

import javax.annotation.Nullable;

import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

public class TileEntityMagicFurnace extends TileEntity implements ITickable{
	
	private int cookProgress = 0;
	private static int cookTime = 8*20;
	private ItemStackHandler inventory = new ItemStackHandler(2);

	@Override
	public void update() {
		
		if(!world.isRemote){
			if(!inventory.getStackInSlot(0).isEmpty()){
				
				ItemStack toCook = inventory.getStackInSlot(0);
				ItemStack product = FurnaceRecipes.instance().getSmeltingResult(toCook);
				ItemStack cooked = inventory.getStackInSlot(1);
				
				if(cooked.isEmpty() 
						|| (ItemStack.areItemsEqual(product, cooked) 
						&& cooked.getMaxStackSize() >= cooked.getCount()  + product.getCount())){
										
					cookProgress ++;
					if(cookProgress == cookTime){
						if(cooked.isEmpty()){
							inventory.setStackInSlot(1, product.copy());
						}else{
							inventory.insertItem(1, product, false);
						}
					}
					
					this.markDirty();
					IBlockState state = world.getBlockState(this.getPos());
					world.notifyBlockUpdate(this.getPos(), state, state, 2);
					
				}
				
			}
		}
		
	}
	

	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		compound.setTag("inventory", inventory.serializeNBT());
		compound.setInteger("cookProgress", cookProgress);
		return super.writeToNBT(compound);
	}

	@Override
	public void readFromNBT(NBTTagCompound compound) {
		inventory.deserializeNBT(compound.getCompoundTag("inventory"));
		cookProgress = compound.getInteger("cookProgress");
		super.readFromNBT(compound);
	}

	@Override
	public SPacketUpdateTileEntity getUpdatePacket() {
		NBTTagCompound compound = new NBTTagCompound();
		this.writeToNBT(compound);
		return new SPacketUpdateTileEntity(getPos(), 1, compound);
	}

	@Override
	public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
		NBTTagCompound compound = pkt.getNbtCompound();
		this.readFromNBT(compound);
	}

	@Override
	public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing) {
		return capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY || super.hasCapability(capability, facing);
	}
	
	@Nullable
	@Override
	public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing) {
		return capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY ? (T) inventory
				: super.getCapability(capability, facing);
	}

}
