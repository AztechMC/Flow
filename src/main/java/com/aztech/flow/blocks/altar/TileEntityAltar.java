package com.aztech.flow.blocks.altar;

import javax.annotation.Nullable;

import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

public class TileEntityAltar extends TileEntity implements ITickable {

	private int burningTime = 0;
	private int sTick = 0;

	public int getBurningTime() {
		return burningTime;
	}

	@Override
	public void update() {
		if (!world.isRemote) {
			sTick++;
			if (sTick % 5 == 0) {
				sTick = 0;

				boolean flag = false;

				if (burningTime > 0) {
					burningTime--;
					flag = true;
				} else {
					if (!inventory.getStackInSlot(0).isEmpty()) {
						inventory.getStackInSlot(0).shrink(1);
						flag = true;
						burningTime = 128;
					}
				}

				if (flag) {
					this.markDirty();
					IBlockState state = world.getBlockState(this.getPos());
					world.notifyBlockUpdate(this.getPos(), state, state, 2);
				}
			}
		}
	}

	private ItemStackHandlerAltar inventory = new ItemStackHandlerAltar(1);

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		compound.setTag("inventory", inventory.serializeNBT());
		compound.setInteger("burning_time", burningTime);
		return super.writeToNBT(compound);
	}

	@Override
	public void readFromNBT(NBTTagCompound compound) {
		inventory.deserializeNBT(compound.getCompoundTag("inventory"));
		burningTime = compound.getInteger("burning_time");
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
