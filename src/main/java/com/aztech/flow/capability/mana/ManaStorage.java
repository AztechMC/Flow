package com.aztech.flow.capability.mana;

import net.minecraft.nbt.NBTTagCompound;

public class ManaStorage implements IManaStorage {
    long mana;
    long maxStorage;

    public ManaStorage() {
        this(0);
    }

    public ManaStorage(long maxStorage) {
        this.mana = 0;
        this.maxStorage = maxStorage;
    }

    @Override
    public long getMaxStorage() {
        return this.maxStorage;
    }

    @Override
    public long getCurrentStorage() {
        return this.mana;
    }

    @Override
    public void setCurrentStorage(long mana) {
        this.mana = mana;
    }

    @Override
    public NBTTagCompound serializeNBT() {
        NBTTagCompound nbt = new NBTTagCompound();
        nbt.setLong("mana", this.mana);
        nbt.setLong("maxStorage", this.maxStorage);
        return nbt;
    }

    @Override
    public void deserializeNBT(NBTTagCompound nbt) {
        this.mana = nbt.getLong("mana");
        this.maxStorage = nbt.getLong("maxStorage");
    }
}
