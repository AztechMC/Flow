package com.aztech.flow.capability.mana;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.INBTSerializable;

public interface IManaConsumer extends INBTSerializable<NBTTagCompound> {
    /**
     * How much mana this consumer would like to receive per tick.
     */
    long getMaxRate();

    /**
     * Current rate that the consumer receives at.
     */
    void setCurrentRate(long rate);
}
