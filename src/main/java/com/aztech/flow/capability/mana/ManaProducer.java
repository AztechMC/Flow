package com.aztech.flow.capability.mana;

import net.minecraft.nbt.NBTTagCompound;

/**
 * Default ManaProducer. Produces at a configurable rate.
 */
public class ManaProducer implements IManaProducer {
    private long currentRate = 0;

    public void setCurrentRate(long currentRate) {
        this.currentRate = currentRate;
    }

    @Override
    public long getCurrentRate() {
        return this.currentRate;
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
