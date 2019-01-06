package com.aztech.flow.capability.mana;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.INBTSerializable;

public interface IManaProducer extends INBTSerializable<NBTTagCompound> {
    /**
     * How much mana this producer produce.
     */
    long getCurrentRate();
}
