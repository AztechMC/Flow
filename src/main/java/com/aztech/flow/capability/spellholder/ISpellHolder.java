package com.aztech.flow.capability.spellholder;

import com.aztech.flow.core.spells.Spell;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.util.INBTSerializable;

public interface ISpellHolder extends INBTSerializable<NBTTagCompound> {
    void cast(World world, BlockPos blockPos);

    void tick();

    Spell getSpell();
}
