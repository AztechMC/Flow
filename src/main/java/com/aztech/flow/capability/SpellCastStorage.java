package com.aztech.flow.capability;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;

import javax.annotation.Nullable;

public class SpellCastStorage implements Capability.IStorage<ISpellCast> {
    @Nullable
    @Override
    public NBTBase writeNBT(Capability<ISpellCast> capability, ISpellCast instance, EnumFacing side) {
        return new NBTTagCompound();
    }

    @Override
    public void readNBT(Capability<ISpellCast> capability, ISpellCast instance, EnumFacing side, NBTBase nbt) {

    }
}
