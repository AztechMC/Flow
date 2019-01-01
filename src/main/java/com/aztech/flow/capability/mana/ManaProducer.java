package com.aztech.flow.capability.mana;

import net.minecraft.nbt.NBTTagCompound;

/**
 * Default ManaProducer. Produces 1 mana per tick.
 */
public class ManaProducer implements IManaProducer {
    @Override
    public long getCurrentRate() {
        return 1;
    }

    @Override
    public NBTTagCompound writeNbt() {
        return new NBTTagCompound();
    }

    @Override
    public IManaProducer readNbt(NBTTagCompound nbt) {
        return this;
    }
}
