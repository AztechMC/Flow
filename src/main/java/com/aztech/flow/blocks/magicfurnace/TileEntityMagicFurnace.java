package com.aztech.flow.blocks.magicfurnace;

import javax.annotation.Nullable;

import com.aztech.flow.capability.mana.IManaChunkManager;
import com.aztech.flow.capability.mana.IManaConsumer;
import com.aztech.flow.capability.mana.ManaConsumer;
import com.aztech.flow.capability.mana.ManaProducer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
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
import net.minecraftforge.items.ItemStackHandler;

public class TileEntityMagicFurnace extends TileEntity implements ITickable {
    @CapabilityInject(IManaChunkManager.class)
    private static Capability<IManaChunkManager> CHUNK_MANAGER_CAPABILITY = null;
    @CapabilityInject(IManaConsumer.class)
    private static Capability<IManaConsumer> MANA_CONSUMER_CAPABILITY = null;

    private int cookProgress = 0;
    /**
     * A partial cook tick is 1/10 of a cookProgress tick. This is useful when mana flow is low.
     */
    private int partialCookTick = 0;
    public static final int cookTime = 8 * 20;
    private ItemStackHandler inventory = new ItemStackHandler(2);

    @Override
    public void update() {

        if (!world.isRemote) {
            boolean isBurning = false;

            // Update mana in chunk
            Chunk c = world.getChunkFromBlockCoords(this.pos);
            assert c.hasCapability(CHUNK_MANAGER_CAPABILITY, null);
            c.getCapability(CHUNK_MANAGER_CAPABILITY, null).tickIfNecessary(c);

            assert this.hasCapability(MANA_CONSUMER_CAPABILITY, null);
            ManaConsumer manaConsumer = (ManaConsumer) this.getCapability(MANA_CONSUMER_CAPABILITY, null);

            if (!inventory.getStackInSlot(0).isEmpty()) {

                ItemStack toCook = inventory.getStackInSlot(0);
                ItemStack product = FurnaceRecipes.instance().getSmeltingResult(toCook);
                ItemStack cooked = inventory.getStackInSlot(1);

                if (!product.isEmpty() && (cooked.isEmpty()
                        || (ItemStack.areItemsEqual(product, cooked)
                        && cooked.getMaxStackSize() >= cooked.getCount() + product.getCount()))) {

                    isBurning = true;

                    // Make sure there is enough mana available
                    this.partialCookTick += manaConsumer.getCurrentRate();

                    if (this.partialCookTick > 10) {
                        this.partialCookTick -= 10;

                        cookProgress++;
                        if (cookProgress == cookTime) {
                            cookProgress = 0;
                            toCook.shrink(1);
                            if (cooked.isEmpty()) {
                                inventory.setStackInSlot(1, product.copy());
                            } else {
                                inventory.insertItem(1, product, false);
                            }
                        }

                        this.markDirty();
                        IBlockState state = world.getBlockState(this.getPos());
                        world.notifyBlockUpdate(this.getPos(), state, state, 2);

                    }
                }

            }
            if (!isBurning && cookProgress != 0) {
                cookProgress = 0;
                this.markDirty();
                IBlockState state = world.getBlockState(this.getPos());
                world.notifyBlockUpdate(this.getPos(), state, state, 2);
            }

            if (isBurning) {
                manaConsumer.setMaxRate(10);
            } else {
                manaConsumer.setMaxRate(0);
            }
        }

    }

    public int getCookProgress() {
        return this.cookProgress;
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
