package com.aztech.flow.proxy;

import com.aztech.flow.blocks.ModBlocks;
import com.aztech.flow.capability.ISpellCast;
import com.aztech.flow.capability.SpellCast;
import com.aztech.flow.capability.SpellCastStorage;
import com.aztech.flow.core.mana.graph.ManaSystem;
import com.aztech.flow.items.ModItems;
import com.aztech.flow.mana.nodes.ModNodes;
import net.minecraftforge.common.capabilities.CapabilityManager;

public class CommonProxy {
    public void preInit() {
        CapabilityManager.INSTANCE.register(ISpellCast.class, new SpellCastStorage(), SpellCast::new);
        ModBlocks.preInit();
        ModItems.preInit();
        ModNodes.register();
    }

    public void openSpellGui(ManaSystem system) {

    }
}
