package com.aztech.flow.capability;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;

import javax.annotation.Nullable;

public class CapabilityStorage<T extends INbtSerializable<T>> implements Capability.IStorage<T> {
    @Nullable
    @Override
    public NBTBase writeNBT(Capability<T> capability, T instance, EnumFacing side) {
        return instance.writeNbt();
    }

    @Override
    public void readNBT(Capability<T> capability, T instance, EnumFacing side, NBTBase nbt) {
        instance.readNbt((NBTTagCompound)nbt);
    }
}
