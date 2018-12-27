package com.aztech.flow.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

public class BlockBasic extends Block {
    public BlockBasic(Material material, String name) {
        this(material, SoundType.STONE, name, name);
    }

    public BlockBasic(Material material, SoundType sound, String unlocalizedName, String registryName) {
        super(material);
        this.setUnlocalizedName(unlocalizedName);
        this.setRegistryName(registryName);
        this.setSoundType(sound);
    }
}
