package com.aztech.flow.blocks.altar;

import javax.annotation.Nullable;

import com.aztech.flow.capability.mana.IManaProducer;
import com.aztech.flow.capability.mana.ManaProducer;
import com.aztech.flow.capability.mana.worldmanager.IManaWorldManager;
import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.items.CapabilityItemHandler;

public class TileEntityAltar extends TileEntity implements ITickable {
    @CapabilityInject(IManaWorldManager.class)
    private static Capability<IManaWorldManager> WORLD_MANAGER_CAPABILITY = null;
    @CapabilityInject(IManaProducer.class)
    private static Capability<IManaProducer> MANA_PRODUCER_CAPABILITY = null;

    private int burningTime = 0;
    private int sTick = 0;
    private ItemStackHandlerAltar inventory = new ItemStackHandlerAltar(1);

    public int getBurningTime() {
        return burningTime;
    }

    @Override
    public void update() {
        if (!world.isRemote) {
            // Update mana in world
            assert world.hasCapability(WORLD_MANAGER_CAPABILITY, null);
            world.getCapability(WORLD_MANAGER_CAPABILITY, null).tick(world.getChunkFromBlockCoords(this.pos));

            sTick++;
            if (sTick % 5 == 0) {
                sTick = 0;

                boolean isBurning = false;

                if (burningTime > 0) { // Still burning
                    burningTime--;
                    isBurning = true;
                } else { // No item is burning
                    if (!inventory.getStackInSlot(0).isEmpty()) { // Is there a burnable item in the input slot?
                        // Make it burn
                        inventory.getStackInSlot(0).shrink(1);
                        isBurning = true;
                        burningTime = 128;
                    }
                }

                // Mana producer logic
                assert this.hasCapability(MANA_PRODUCER_CAPABILITY, null);
                ManaProducer producer = (ManaProducer)this.getCapability(MANA_PRODUCER_CAPABILITY, null);
                if(isBurning) {
                    producer.setCurrentRate(10);
                } else {
                    producer.setCurrentRate(0);
                }

                // Send update to client
                if (isBurning) {
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
