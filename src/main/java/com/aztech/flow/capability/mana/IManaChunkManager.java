package com.aztech.flow.capability.mana;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.common.util.INBTSerializable;

/**
 * Allows a chunk to manage its mana consumers, producers and storages.
 */
public interface IManaChunkManager extends INBTSerializable<NBTTagCompound> {
    /**
     * Ticks the mana system if it hasn't been ticked yet.
     */
    void tickIfNecessary(Chunk c);
}
