package com.aztech.flow.capability.mana.worldmanager;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.common.util.INBTSerializable;

/**
 * Allows a World to manage connected mana producers, consumers and storages.
 */
public interface IManaWorldManager extends INBTSerializable<NBTTagCompound> {
    /**
     * Ticks the mana system if it hasn't been ticked yet.
     * This should happen at the beginning of every tick for chunks containing mana tile entities.
     */
    void tick(Chunk chunk);
}
