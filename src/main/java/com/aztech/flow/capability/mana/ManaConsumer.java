package com.aztech.flow.capability.mana;

import net.minecraft.nbt.NBTTagCompound;

/**
 * Default ManaConsumer. Consumes at a configurable rate.
 */
public class ManaConsumer implements IManaConsumer {
    private long maxRate = 0;
    private long currentRate = 0;

    public void setMaxRate(long maxRate) {
        this.maxRate = maxRate;
    }

    public long getCurrentRate() {
        return this.currentRate;
    }

    @Override
    public long getMaxRate() {
        return this.maxRate;
    }

    @Override
    public void setCurrentRate(long rate) {
        this.currentRate = rate;
    }

    @Override
    public NBTTagCompound writeNbt() {
        return new NBTTagCompound();
    }

    @Override
    public IManaConsumer readNbt(NBTTagCompound nbt) {
        return this;
    }
}
