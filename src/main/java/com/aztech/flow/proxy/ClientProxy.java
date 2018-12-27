package com.aztech.flow.proxy;

import com.aztech.flow.core.mana.graph.ManaSystem;
import com.aztech.flow.gui.GuiSpellDisplay;
import net.minecraft.client.Minecraft;

public class ClientProxy extends CommonProxy {
    public void openSpellGui(ManaSystem system) {
        Minecraft.getMinecraft().displayGuiScreen(new GuiSpellDisplay(system));
    }
}
