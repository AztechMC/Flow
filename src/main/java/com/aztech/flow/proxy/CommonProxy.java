package com.aztech.flow.proxy;

import com.aztech.flow.Flow;
import com.aztech.flow.blocks.ModBlocks;
import com.aztech.flow.blocks.altar.TileEntityAltar;
import com.aztech.flow.blocks.magicfurnace.TileEntityMagicFurnace;
import com.aztech.flow.capability.CapabilityStorage;
import com.aztech.flow.capability.mana.*;
import com.aztech.flow.capability.mana.worldmanager.IManaWorldManager;
import com.aztech.flow.capability.mana.worldmanager.ManaWorldManager;
import com.aztech.flow.capability.spellholder.ISpellHolder;
import com.aztech.flow.capability.spellholder.SpellHolder;
import com.aztech.flow.core.spells.Spell;
import com.aztech.flow.items.ModItems;
import com.aztech.flow.spells.runes.ModRunes;
import com.aztech.flow.ore.Ore;

import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class CommonProxy {
    public void preInit() {
        CapabilityManager.INSTANCE.register(ISpellHolder.class, new CapabilityStorage<>(), SpellHolder::new);
        CapabilityManager.INSTANCE.register(IManaConsumer.class, new CapabilityStorage<>(), ManaConsumer::new);
        CapabilityManager.INSTANCE.register(IManaProducer.class, new CapabilityStorage<>(), ManaProducer::new);
        CapabilityManager.INSTANCE.register(IManaStorage.class, new CapabilityStorage<>(), ManaStorage::new);
        CapabilityManager.INSTANCE.register(IManaWorldManager.class, new CapabilityStorage<>(), ManaWorldManager::new);
      
        Ore.preInit();  
        ModBlocks.preInit();
        ModItems.preInit();
        ModRunes.register();
      
        GameRegistry.registerTileEntity(TileEntityAltar.class, ModBlocks.ALTAR.getRegistryName());
        GameRegistry.registerTileEntity(TileEntityMagicFurnace.class, ModBlocks.MAGIC_FURNACE.getRegistryName());
      
        Flow.CREATIVE_TAB.setIconItem(ModItems.SCROLL);
    }

    public void openSpellGui(Spell spell) {

    }
}
