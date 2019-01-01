package com.aztech.flow.blocks.magicfurnace;

import com.aztech.flow.blocks.BlockBasic;

import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockMagicFurnace extends BlockBasic implements ITileEntityProvider{

	public BlockMagicFurnace() {
		super(Material.ROCK, "magic_furnace");
		this.setHardness(20.0F);
		this.setResistance(25F);
		this.setLightLevel(0f); 
		this.setHarvestLevel("pickaxe", 1);
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {

		return new TileEntityMagicFurnace();
	}

}
