package com.aztech.flow.ore;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.DimensionType;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.fml.common.IWorldGenerator;

public class WorldGenCustomOre implements IWorldGenerator {

	private WorldGenerator ore_gen;
	private int minHeight, maxHeight;
	private int chance;

	public WorldGenCustomOre(Block ore, int block_count, int minHeight, int maxHeight, int chance) {
		ore_gen = new WorldGenMinable(ore.getDefaultState(), block_count);
		this.minHeight = minHeight;
		this.maxHeight = maxHeight;
		this.chance = chance;
		if (minHeight < 0 || maxHeight > 255 || maxHeight < minHeight) {
			throw new IllegalArgumentException("Ore generation altitue incorret");
		}
	}

	public WorldGenCustomOre(Block ore, int block_count, int maxHeight, int chance) {
		this(ore, block_count, 0, maxHeight, chance);
	}

	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator,
			IChunkProvider chunkProvider) {

		if (world.provider.getDimensionType() == DimensionType.OVERWORLD) {

			for (int i = 0; i < chance; i++) {
				int x = chunkX * 16 + random.nextInt(16);
				int y = minHeight + random.nextInt(maxHeight - minHeight);
				int z = chunkZ * 16 + random.nextInt(16);
				ore_gen.generate(world, random, new BlockPos(x, y, z));
			}

		}

	}

}
