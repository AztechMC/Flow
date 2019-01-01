package com.aztech.flow.proxy;

import com.aztech.flow.Flow;
import com.aztech.flow.blocks.ModBlocks;
import com.aztech.flow.blocks.altar.TileEntityAltar;
import com.aztech.flow.blocks.magicfurnace.TileEntityMagicFurnace;
import com.aztech.flow.capability.ISpellCast;
import com.aztech.flow.capability.SpellCast;
import com.aztech.flow.capability.SpellCastStorage;
import com.aztech.flow.core.mana.graph.ManaSystem;
import com.aztech.flow.items.ModItems;
import com.aztech.flow.mana.nodes.ModNodes;
import com.aztech.flow.ore.Ore;

import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class CommonProxy {
    public void preInit() {
        CapabilityManager.INSTANCE.register(ISpellCast.class, new SpellCastStorage(), SpellCast::new);
      
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
