package com.aztech.flow.blocks.altar;

import com.aztech.flow.Flow;
import com.aztech.flow.blocks.ModBlocks;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;

public class GuiAltar extends GuiContainer{
	
	private InventoryPlayer playerInv;
	private static final ResourceLocation BG_TEXTURE = new ResourceLocation(Flow.MODID, "textures/gui/gui_altar.png");
	private TileEntityAltar altar;
	
	public GuiAltar(Container inventorySlotsIn,InventoryPlayer playerInv, TileEntityAltar altar) {
		super(inventorySlotsIn);
		this.playerInv = playerInv;
		this.altar = altar;
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		GlStateManager.color(1, 1, 1, 1);
		mc.getTextureManager().bindTexture(BG_TEXTURE);
		int x = (width - xSize) / 2;
		int y = (height - ySize) / 2;
		drawTexturedModalRect(x, y, 0, 0, xSize, ySize);
		
		int j = (int)(67f - 67f*(float) altar.getBurningTime()/128f);
		
		drawTexturedModalRect(x+54, y+9+j, 177, 7 + j, 67, 67 -j);
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		String name = I18n.format(ModBlocks.ALTAR.getUnlocalizedName() + ".name");
		
		fontRenderer.drawString(name, xSize / 2 - fontRenderer.getStringWidth(name ) / 2, 6, 0x404040);
		fontRenderer.drawString(playerInv.getDisplayName().getUnformattedText(), 8, ySize - 94, 0x404040);
	}

}
