package com.aztech.flow.blocks;

import com.aztech.flow.Flow;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.Arrays;
import java.util.List;

@Mod.EventBusSubscriber(modid = Flow.MODID)
public class ModBlocks {

    public static Block ALTAR;
    public static Block[] blocks;
    public static Item[] itemBlocks;

    public static void preInit() {
        ALTAR = new BlockBasic(Material.ROCK, "altar").setHardness(20.0F).setResistance(1000F).setLightLevel(1f); ALTAR.setHarvestLevel("pickaxe", 3);

        blocks = new Block[]{ALTAR};
        itemBlocks = new ItemBlock[blocks.length];
        for(int i = 0; i < blocks.length; ++i) {
            itemBlocks[i] = new ItemBlock(blocks[i]).setRegistryName(blocks[i].getRegistryName());
        }
    }

    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> event) {
        event.getRegistry().registerAll(blocks);
    }
}
