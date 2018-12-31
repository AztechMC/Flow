package com.aztech.flow.ore;

import java.util.Random;

import com.aztech.flow.blocks.BlockBasic;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;

public class CustomBlockOre extends BlockBasic {
	
	private Item drop;

	public CustomBlockOre(Material material, String name, Item drop) {
		super(material, name);
		this.drop = drop;
		
		this.setHardness(3f);
		this.setResistance(15f);
		this.setHarvestLevel("pickaxe", 1);	
	}

	@Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune)
    {
        return drop;
    }

}
