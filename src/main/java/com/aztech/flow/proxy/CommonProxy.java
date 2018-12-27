package com.aztech.flow.proxy;

import com.aztech.flow.Flow;
import com.aztech.flow.blocks.ModBlocks;
import com.aztech.flow.capability.ISpellCast;
import com.aztech.flow.capability.SpellCast;
import com.aztech.flow.capability.SpellCastStorage;
import com.aztech.flow.items.ModItems;
import com.aztech.flow.ore.Ore;

import net.minecraftforge.common.capabilities.CapabilityManager;

public class CommonProxy {
    public void preInit() {
        CapabilityManager.INSTANCE.register(ISpellCast.class, new SpellCastStorage(), SpellCast::new);
        
        Ore.preInit();
        ModBlocks.preInit();
        ModItems.preInit();
        
        Flow.CREATIVE_TAB.setIconItem(ModItems.SCROLL);
    }
}
