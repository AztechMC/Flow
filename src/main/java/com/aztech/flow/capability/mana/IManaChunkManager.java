package com.aztech.flow.capability.mana;

import com.aztech.flow.capability.INbtSerializable;
import net.minecraft.world.chunk.Chunk;

/**
 * Allows a chunk to manage its mana consumers, producers and storages.
 */
public interface IManaChunkManager extends INbtSerializable<IManaChunkManager> {
    /**
     * Ticks the mana system if it hasn't been ticked yet.
     */
    void tickIfNecessary(Chunk c);

    /**
     * Prepare to tick again in `tickIfNecessary`.
     */
    void nextTick();
}
