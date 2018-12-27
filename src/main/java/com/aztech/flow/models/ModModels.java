package com.aztech.flow.models;

import com.aztech.flow.blocks.ModBlocks;
import com.aztech.flow.items.ModItems;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

@Mod.EventBusSubscriber(Side.CLIENT)
public class ModModels {

    @SubscribeEvent
    public static void registerModels(ModelRegistryEvent event) {
    	for(Item item : ModItems.ITEMS){
    		registerModel(item);
    	}
    	for(Block block : ModBlocks.blocks){
    		registerModel(Item.getItemFromBlock(block));
    	}
    }

    private static void registerModel(Item item) {
        ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(item.getRegistryName(), "inventory"));
    }
}
