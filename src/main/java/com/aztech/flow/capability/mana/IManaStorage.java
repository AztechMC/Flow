package com.aztech.flow.capability.mana;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.INBTSerializable;

public interface IManaStorage extends INBTSerializable<NBTTagCompound> {
    /**
     * Maximum amount of mana that can be stored.
     */
    long getMaxStorage();

    /**
     * Get amount of mana currently in storage.
     */
    long getCurrentStorage();

    /**
     * Set amount of mana currently in storage.
     */
    void setCurrentStorage(long mana);
}
