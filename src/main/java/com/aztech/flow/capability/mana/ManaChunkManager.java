package com.aztech.flow.capability.mana;

import com.aztech.flow.Flow;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class ManaChunkManager implements IManaChunkManager {
    @CapabilityInject(IManaConsumer.class)
    public static final Capability<IManaConsumer> C_CAP = null;
    @CapabilityInject(IManaProducer.class)
    public static final Capability<IManaProducer> P_CAP = null;
    @CapabilityInject(IManaStorage.class)
    public static final Capability<IManaStorage> S_CAP = null;

    private long lastTick = -1;

    public boolean shouldTick(Chunk c) {
        long currentTick = c.getWorld().getTotalWorldTime();
        if(currentTick > this.lastTick) {
            this.lastTick = currentTick;
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void tickIfNecessary(Chunk c) {
        if(!shouldTick(c)) return;
        List<IManaConsumer> consumers = new LinkedList<>();
        List<IManaProducer> producers = new LinkedList<>();
        List<IManaStorage> storages = new LinkedList<>();

        // Get relevant capabilities in chunk
        Map<BlockPos, TileEntity> tileEntities = c.getTileEntityMap();
        for(TileEntity te : tileEntities.values()) {
            if(te.hasCapability(C_CAP, null)) {
                consumers.add(te.getCapability(C_CAP, null));
            }
            if(te.hasCapability(P_CAP, null)) {
                producers.add(te.getCapability(P_CAP, null));
            }
            if(te.hasCapability(S_CAP, null)) {
                storages.add(te.getCapability(S_CAP, null));
            }
        }

        // Calculate total available mana
        long totalMana = 0;
        for(IManaStorage storage : storages) {
            totalMana += storage.getCurrentStorage();
        }
        for(IManaProducer producer : producers) {
            totalMana += producer.getCurrentRate();
        }

        //Flow.logger.info(String.format("Total mana available in chunk at (%d, %d) is currently %d mana.", c.x, c.z, totalMana));

        // Consume mana
        for(IManaConsumer consumer : consumers) {
            long maxRate = consumer.getMaxRate();
            if(totalMana > maxRate) {
                consumer.setCurrentRate(maxRate);
                totalMana -= maxRate;
            } else {
                consumer.setCurrentRate(totalMana);
                totalMana = 0;
            }
        }

        // Put mana back in the storages
        for(IManaStorage storage : storages) {
            long maxStorage = storage.getMaxStorage();
            if(totalMana > maxStorage) {
                storage.setCurrentStorage(maxStorage);
                totalMana -= maxStorage;
            } else {
                storage.setCurrentStorage(totalMana);
                totalMana = 0;
            }
        }
    }

    @Override
    public NBTTagCompound writeNbt() {
        return new NBTTagCompound();
    }

    @Override
    public IManaChunkManager readNbt(NBTTagCompound nbt) {
        return this;
    }
}