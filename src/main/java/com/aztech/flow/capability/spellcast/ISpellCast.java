package com.aztech.flow.capability.spellcast;

import com.aztech.flow.capability.INbtSerializable;
import com.aztech.flow.core.mana.graph.ManaSystem;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface ISpellCast extends INbtSerializable<ISpellCast> {
    void startSpellCast(World world, BlockPos blockPos);
    void tick();
    ManaSystem getUnderlyingSystem();
}
