package com.aztech.flow.gui;

import com.aztech.flow.Flow;
import com.aztech.flow.core.mana.graph.ManaSystem;
import net.minecraft.client.gui.GuiScreen;
import org.lwjgl.opengl.GL11;

public class GuiSpellDisplay extends GuiScreen {
    ManaSystem system;

    public GuiSpellDisplay(ManaSystem system) {
        this.system = system;
    }

    @Override
    public void initGui() {
        Flow.logger.info("initGui()");
    }

    @Override
    public void updateScreen() {

    }

    @Override
    public void drawScreen(int width, int height, float p_73863_3_) {
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        fontRenderer.drawString(String.format("There are %d components in this spell", this.system.graph.size()), 0, 0, 0);
        super.drawScreen(width, height, p_73863_3_);
    }

    @Override
    public void onGuiClosed() {

    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }
}
