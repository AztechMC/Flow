package com.aztech.flow.blocks.altar;

import javax.annotation.Nullable;

import com.aztech.flow.Flow;

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

	private int burning_time = 0;
	private int s_tick = 0;

	public int getBurning_time() {
		return burning_time;
	}

	@Override
	public void update() {
		if (!world.isRemote) {
			s_tick++;
			if (s_tick % 5 == 0) {
				s_tick = 0;

				boolean flag = false;

				if (burning_time > 0) {
					burning_time--;
					flag = true;
				} else {
					if (!inventory.getStackInSlot(0).isEmpty()) {
						inventory.getStackInSlot(0).shrink(1);
						flag = true;
						burning_time = 128;
					}
				}

				if (flag) {
					this.markDirty();
					IBlockState state = world.getBlockState(this.getPos());
					world.notifyBlockUpdate(this.getPos(), state, state, 2);
					//Flow.logger.info("burning_time : " + this.burning_time);
				}
			}
		}
	}

	private ItemStackHandler inventory = new ItemStackHandler(1);

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		compound.setTag("inventory", inventory.serializeNBT());
		compound.setInteger("burning_time", burning_time);
		return super.writeToNBT(compound);
	}

	@Override
	public void readFromNBT(NBTTagCompound compound) {
		inventory.deserializeNBT(compound.getCompoundTag("inventory"));
		burning_time = compound.getInteger("burning_time");
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
