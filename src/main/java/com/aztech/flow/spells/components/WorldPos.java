package com.aztech.flow.spells.components;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class WorldPos {
    public World world;
    public BlockPos blockPos;

    public WorldPos(World world, BlockPos blockPos) {
        this.world = world;
        this.blockPos = blockPos;
    }
}
