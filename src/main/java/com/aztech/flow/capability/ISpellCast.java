package com.aztech.flow.capability;

import com.aztech.flow.core.mana.graph.ManaSystem;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface ISpellCast {
    void startSpellCast(World world, BlockPos blockPos);
    void tick();
    ManaSystem getUnderlyingSystem();
    NBTTagCompound writeNbt();
    ISpellCast readNbt(NBTTagCompound nbt);
}
