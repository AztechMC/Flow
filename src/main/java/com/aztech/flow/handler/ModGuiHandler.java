package com.aztech.flow.handler;

import com.aztech.flow.blocks.altar.ContainerAltar;
import com.aztech.flow.blocks.altar.GuiAltar;
import com.aztech.flow.blocks.altar.TileEntityAltar;
import com.aztech.flow.blocks.magicfurnace.ContainerMagicFurnace;
import com.aztech.flow.blocks.magicfurnace.GuiMagicFurnace;
import com.aztech.flow.blocks.magicfurnace.TileEntityMagicFurnace;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class ModGuiHandler implements IGuiHandler {

	public static final int ALTAR = 0;
	public static final int MAGIC_FURNACE = 1;

	@Override
	public Container getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		switch (ID) {
		case ALTAR:
			return new ContainerAltar(player.inventory, (TileEntityAltar) world.getTileEntity(new BlockPos(x, y, z)));
		case MAGIC_FURNACE:
			return new ContainerMagicFurnace(player.inventory, (TileEntityMagicFurnace) world.getTileEntity(new BlockPos(x, y, z)));
		default:
			return null;
		}
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		switch (ID) {
		case ALTAR:
			return new GuiAltar(getServerGuiElement(ID, player, world, x, y, z), player.inventory,
					(TileEntityAltar) world.getTileEntity(new BlockPos(x, y, z)));
		case MAGIC_FURNACE:
			return new GuiMagicFurnace(getServerGuiElement(ID, player, world, x, y, z), player.inventory,
					(TileEntityMagicFurnace) world.getTileEntity(new BlockPos(x, y, z)));
			
		default:
			return null;
		}
	}

}
