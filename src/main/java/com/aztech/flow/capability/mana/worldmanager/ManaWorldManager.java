package com.aztech.flow.capability.mana.worldmanager;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

import com.aztech.flow.capability.mana.IManaConsumer;
import com.aztech.flow.capability.mana.IManaProducer;
import com.aztech.flow.capability.mana.IManaStorage;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;

public class ManaWorldManager implements IManaWorldManager {
    @CapabilityInject(IManaConsumer.class)
    private static final Capability<IManaConsumer> C_CAP = null;
    @CapabilityInject(IManaProducer.class)
    private static final Capability<IManaProducer> P_CAP = null;
    @CapabilityInject(IManaStorage.class)
    private static final Capability<IManaStorage> S_CAP = null;

    private TreeSet<ChunkPosition> visitedChunks;
    private List<IManaConsumer> consumers;
    private List<IManaProducer> producers;
    private List<IManaStorage> storages;

    private static final int[] DX = new int[]{+1, +1, 0, -1, -1, -1, 0, +1};
    private static final int[] DZ = new int[]{0, +1, +1, +1, 0, -1, -1, -1};

    private long lastTick = -1;

    private void prepareNewTick(Chunk c) {
        long currentTick = c.getWorld().getTotalWorldTime();
        if (currentTick > this.lastTick) {
            this.lastTick = currentTick;
            this.visitedChunks = new TreeSet<>();
        }
    }

    private boolean wasVisited(int x, int z) {
        return this.visitedChunks.contains(new ChunkPosition(x,z));
    }

    private void makeVisited(int x, int z) {
        this.visitedChunks.add(new ChunkPosition(x,z));
    }

    private void collectDevicesDfs(IChunkProvider provider, Chunk currentChunk) {
        int x = currentChunk.x;
        int z = currentChunk.z;
        if (!this.wasVisited(x, z)) {
            this.makeVisited(x, z);

            // collect relevant tile entities
            int collectedCount = 0;
            Map<BlockPos, TileEntity> tileEntities = currentChunk.getTileEntityMap();
            for (TileEntity te : tileEntities.values()) {
                if (te.hasCapability(C_CAP, null)) {
                    this.consumers.add(te.getCapability(C_CAP, null));
                    ++collectedCount;
                }
                if (te.hasCapability(P_CAP, null)) {
                    this.producers.add(te.getCapability(P_CAP, null));
                    ++collectedCount;
                }
                if (te.hasCapability(S_CAP, null)) {
                    this.storages.add(te.getCapability(S_CAP, null));
                    ++collectedCount;
                }
            }

            // only visit adjacent chunks if there was some mana tile entity in this chunk
            if(collectedCount != 0) {
                for (int k = 0; k < 8; ++k) {
                    int nx = x + DX[k];
                    int nz = z + DZ[k];
                    Chunk adjChunk = provider.getLoadedChunk(nx, nz);
                    if (adjChunk != null) {
                        this.collectDevicesDfs(provider, adjChunk);
                    }
                }
            }
        }
    }

    private void moveMana() {
        // Calculate total available mana
        long totalMana = 0;
        for (IManaStorage storage : this.storages) {
            totalMana += storage.getCurrentStorage();
        }
        for (IManaProducer producer : this.producers) {
            totalMana += producer.getCurrentRate();
        }

        //Flow.logger.info(String.format("Total mana available in chunk at (%d, %d) is currently %d mana.", c.x, c.z, totalMana));

        // Consume mana
        for (IManaConsumer consumer : this.consumers) {
            long maxRate = consumer.getMaxRate();
            if (totalMana > maxRate) {
                consumer.setCurrentRate(maxRate);
                totalMana -= maxRate;
            } else {
                consumer.setCurrentRate(totalMana);
                totalMana = 0;
            }
        }

        // Put mana back in the storages
        for (IManaStorage storage : this.storages) {
            long maxStorage = storage.getMaxStorage();
            if (totalMana > maxStorage) {
                storage.setCurrentStorage(maxStorage);
                totalMana -= maxStorage;
            } else {
                storage.setCurrentStorage(totalMana);
                totalMana = 0;
            }
        }
    }

    @Override
    public void tick(Chunk chunk) {
        this.prepareNewTick(chunk);
        if(!this.wasVisited(chunk.x, chunk.z)) {
            this.consumers = new LinkedList<>();
            this.producers = new LinkedList<>();
            this.storages = new LinkedList<>();

            collectDevicesDfs(chunk.getWorld().getChunkProvider(), chunk);
            moveMana();
        }
    }

    @Override
    public NBTTagCompound serializeNBT() {
        // TODO
        return new NBTTagCompound();
    }

    @Override
    public void deserializeNBT(NBTTagCompound nbt) {
        // TODO
    }
    
    class ChunkPosition implements Comparable<ChunkPosition>{
    	
    	public int x, z;
    	
    	public ChunkPosition(int x, int z){
    		this.x=x;
    		this.z=z;
    	}

		@Override
		public int compareTo(ChunkPosition arg0) {
			if(x == arg0.x){
				return Integer.compare(z, arg0.z);
			}
			return Integer.compare(x, arg0.x);
		}
    	
    }
}

