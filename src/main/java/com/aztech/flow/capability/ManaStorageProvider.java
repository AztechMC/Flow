
package com.aztech.flow.capability;

import com.aztech.flow.capability.mana.IManaStorage;
import net.minecraft.nbt.NBTBase;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class ManaStorageProvider implements ICapabilitySerializable<NBTBase> {
    @CapabilityInject(IManaStorage.class)
    public static final Capability<IManaStorage> CAPABILITY = null;

    private IManaStorage instance = CAPABILITY.getDefaultInstance();

    @Override
    public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing facing) {
        return capability == CAPABILITY;
    }

    @Nullable
    @Override
    public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing) {
        return capability == CAPABILITY ? CAPABILITY.<T> cast(this.instance) : null;
    }

    @Override
    public NBTBase serializeNBT() {
        return CAPABILITY.getStorage().writeNBT(CAPABILITY, this.instance, null);
    }

    @Override
    public void deserializeNBT(NBTBase nbt) {
        CAPABILITY.getStorage().readNBT(CAPABILITY, this.instance, null, nbt);
    }
}