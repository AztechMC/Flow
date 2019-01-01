package com.aztech.flow.capability.spellcast;

import net.minecraft.nbt.NBTBase;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class SpellCastProvider implements ICapabilitySerializable<NBTBase> {
    @CapabilityInject(ISpellCast.class)
    public static final Capability<ISpellCast> SPELL_CAST_CAPABILITY = null;

    private ISpellCast instance = SPELL_CAST_CAPABILITY.getDefaultInstance();

    @Override
    public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing facing) {
        return capability == SPELL_CAST_CAPABILITY;
    }

    @Nullable
    @Override
    public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing) {
        return capability == SPELL_CAST_CAPABILITY ? SPELL_CAST_CAPABILITY.<T> cast(this.instance) : null;
    }

    @Override
    public NBTBase serializeNBT() {
        return SPELL_CAST_CAPABILITY.getStorage().writeNBT(SPELL_CAST_CAPABILITY, this.instance, null);
    }

    @Override
    public void deserializeNBT(NBTBase nbt) {
        SPELL_CAST_CAPABILITY.getStorage().readNBT(SPELL_CAST_CAPABILITY, this.instance, null, nbt);
    }
}
