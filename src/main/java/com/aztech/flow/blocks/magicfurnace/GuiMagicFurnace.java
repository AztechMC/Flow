package com.aztech.flow.blocks.magicfurnace;

import com.aztech.flow.Flow;
import com.aztech.flow.blocks.ModBlocks;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;

public class GuiMagicFurnace extends GuiContainer{
	
	private InventoryPlayer playerInv;
	private static final ResourceLocation BG_TEXTURE = new ResourceLocation(Flow.MODID, "textures/gui/gui_magic_furnace.png");
	private TileEntityMagicFurnace magicFurnace;
	
	public GuiMagicFurnace(Container inventorySlotsIn,InventoryPlayer playerInv, TileEntityMagicFurnace magicFurnace) {
		super(inventorySlotsIn);
		this.playerInv = playerInv;
		this.magicFurnace = magicFurnace;
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		GlStateManager.color(1, 1, 1, 1);
		mc.getTextureManager().bindTexture(BG_TEXTURE);
		int x = (width - xSize) / 2;
		int y = (height - ySize) / 2;
		drawTexturedModalRect(x, y, 0, 0, xSize, ySize);
		
		int progress = (int) (24*((double) magicFurnace.getCookProgress())/TileEntityMagicFurnace.cookTime);
		
		drawTexturedModalRect(x+79, y+34, 176, 0, progress, 17);
		
		

	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		String name = I18n.format(ModBlocks.MAGIC_FURNACE.getUnlocalizedName() + ".name");
		
		fontRenderer.drawString(name, xSize / 2 - fontRenderer.getStringWidth(name ) / 2, 6, 0x404040);
		fontRenderer.drawString(playerInv.getDisplayName().getUnformattedText(), 8, ySize - 94, 0x404040);
	}

}

