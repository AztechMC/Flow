package com.aztech.flow.proxy;

import com.aztech.flow.Flow;
import com.aztech.flow.blocks.ModBlocks;
import com.aztech.flow.blocks.altar.TileEntityAltar;
import com.aztech.flow.blocks.magicfurnace.TileEntityMagicFurnace;
import com.aztech.flow.capability.CapabilityStorage;
import com.aztech.flow.capability.mana.*;
import com.aztech.flow.capability.spellcast.ISpellCast;
import com.aztech.flow.capability.spellcast.SpellCast;
import com.aztech.flow.core.mana.graph.ManaSystem;
import com.aztech.flow.items.ModItems;
import com.aztech.flow.mana.nodes.ModNodes;
import com.aztech.flow.ore.Ore;

import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class CommonProxy {
    public void preInit() {
        CapabilityManager.INSTANCE.register(ISpellCast.class, new CapabilityStorage<>(), SpellCast::new);
        CapabilityManager.INSTANCE.register(IManaConsumer.class, new CapabilityStorage<>(), ManaConsumer::new);
        CapabilityManager.INSTANCE.register(IManaProducer.class, new CapabilityStorage<>(), ManaProducer::new);
        CapabilityManager.INSTANCE.register(IManaStorage.class, new CapabilityStorage<>(), ManaStorage::new);
        CapabilityManager.INSTANCE.register(IManaChunkManager.class, new CapabilityStorage<>(), ManaChunkManager::new);
      
        Ore.preInit();  
        ModBlocks.preInit();
        ModItems.preInit();
        ModNodes.register();
      
        GameRegistry.registerTileEntity(TileEntityAltar.class, ModBlocks.ALTAR.getRegistryName());
        GameRegistry.registerTileEntity(TileEntityMagicFurnace.class, ModBlocks.MAGIC_FURNACE.getRegistryName());
      
        Flow.CREATIVE_TAB.setIconItem(ModItems.SCROLL);
    }

    public void openSpellGui(ManaSystem system) {

    }
}
