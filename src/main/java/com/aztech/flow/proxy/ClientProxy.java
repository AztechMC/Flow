package com.aztech.flow.proxy;

import com.aztech.flow.core.spells.Spell;
import com.aztech.flow.gui.GuiSpellDisplay;
import net.minecraft.client.Minecraft;

public class ClientProxy extends CommonProxy {
    public void openSpellGui(Spell spell) {
        Minecraft.getMinecraft().displayGuiScreen(new GuiSpellDisplay(spell));
    }
}
