package com.aztech.flow.capability.mana;

import net.minecraft.nbt.NBTTagCompound;

/**
 * Default ManaConsumer. Deletes 1 mana per tick.
 */
public class ManaConsumer implements IManaConsumer {
    @Override
    public long getMaxRate() {
        return 1;
    }

    @Override
    public void setCurrentRate(long rate) {

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
