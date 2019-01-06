package com.aztech.flow.capability.spellholder;

import net.minecraft.nbt.NBTBase;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class SpellHolderProvider implements ICapabilitySerializable<NBTBase> {
    @CapabilityInject(ISpellHolder.class)
    private static final Capability<ISpellHolder> SPELL_HOLDER_CAPABILITY = null;

    private ISpellHolder instance = SPELL_HOLDER_CAPABILITY.getDefaultInstance();

    @Override
    public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing facing) {
        return capability == SPELL_HOLDER_CAPABILITY;
    }

    @Nullable
    @Override
    public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing) {
        return capability == SPELL_HOLDER_CAPABILITY ? SPELL_HOLDER_CAPABILITY.<T>cast(this.instance) : null;
    }

    @Override
    public NBTBase serializeNBT() {
        return SPELL_HOLDER_CAPABILITY.getStorage().writeNBT(SPELL_HOLDER_CAPABILITY, this.instance, null);
    }

    @Override
    public void deserializeNBT(NBTBase nbt) {
        SPELL_HOLDER_CAPABILITY.getStorage().readNBT(SPELL_HOLDER_CAPABILITY, this.instance, null, nbt);
    }
}
