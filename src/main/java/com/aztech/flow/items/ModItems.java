package com.aztech.flow.items;

import java.util.ArrayList;

import com.aztech.flow.Flow;
import com.aztech.flow.blocks.ModBlocks;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber(modid = Flow.MODID)
public class ModItems {

    public static ArrayList<Item> ITEMS = new ArrayList<Item>();
    public static Item SCROLL;
    public static Item MANA_SHARD;

    public static void preInit() {
        SCROLL = new ItemScroll("scroll");
        MANA_SHARD = new ItemBasic("mana_shard");
        
    }

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {
        event.getRegistry().registerAll(ITEMS.toArray(new Item[ITEMS.size()]));
        event.getRegistry().registerAll(ModBlocks.itemBlocks);
    }
}
