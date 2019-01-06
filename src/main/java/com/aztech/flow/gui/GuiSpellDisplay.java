package com.aztech.flow.gui;

import com.aztech.flow.Flow;
import com.aztech.flow.core.spells.Spell;
import com.aztech.flow.core.api.spells.Vertex;
import net.minecraft.client.gui.GuiScreen;
import org.lwjgl.opengl.GL11;

public class GuiSpellDisplay extends GuiScreen {
    Spell system;

    public GuiSpellDisplay(Spell system) {
        this.system = system;
    }

    @Override
    public void initGui() {
        Flow.logger.info("initGui()");
    }

    @Override
    public void updateScreen() {

    }

    private static float usedPartOfScreen = 0.8f;

    private int getPosition(int dimension, float min, float max, float pos) {
        float normalized = (pos - min)/(max - min + 1.0e-5f); // pos in [0.0f, 1.0f]
        float pixelPosition = normalized*dimension*usedPartOfScreen + dimension*(1.0f - usedPartOfScreen)/2.0f;
        return (int)pixelPosition;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float p_73863_3_) {
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        fontRenderer.drawString(String.format("There are %d aspects in this spell", this.system.graph.length), 0, 0, 0);
        if(system.graph.length > 0) {
            float minX = 100, maxX = -100;
            float minY = 100, maxY = -100;
            for (Vertex v : system.graph) {
                minX = Math.min(minX, v.xPos);
                maxX = Math.max(maxX, v.xPos);
                minY = Math.min(minY, v.yPos);
                maxY = Math.max(maxY, v.yPos);
            }

            for (Vertex v : system.graph) {
                int xPos = getPosition(width, minX, maxX, v.xPos);
                int yPos = getPosition(width, minY, maxY, v.yPos);
                drawCenteredString(fontRenderer, v.rune.runeId, xPos, yPos, 0);
            }
        }
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
