package com.aztech.flow.blocks.altar;

import com.aztech.flow.Flow;
import com.aztech.flow.blocks.BlockBasic;
import com.aztech.flow.handler.ModGuiHandler;

import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

public class BlockAltar extends BlockBasic implements ITileEntityProvider{

	public BlockAltar() {
		super(Material.ROCK, "altar");
		this.setHardness(5.0F);
		this.setResistance(1000F);
		this.setLightLevel(1f); 
		this.setHarvestLevel("pickaxe", 3);
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileEntityAltar();
	}
	
	@Override
	public boolean hasTileEntity(IBlockState state) {
		return true;
	}
	
	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) {
		if (!world.isRemote) {
 			if (!player.isSneaking()) {
 				player.openGui(Flow.instance, ModGuiHandler.ALTAR, world, pos.getX(), pos.getY(), pos.getZ());
  			} else {  				
  			}
  		}
  		return true;

	}
	
	@Override
	public void breakBlock(World world, BlockPos pos, IBlockState state) {
		TileEntityAltar tile = (TileEntityAltar) world.getTileEntity(pos);
		IItemHandler itemHandler = tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.NORTH);
		ItemStack stack = itemHandler.getStackInSlot(0);
		if (!stack.isEmpty()) {
			EntityItem item = new EntityItem(world, pos.getX(), pos.getY(), pos.getZ(), stack);
			world.spawnEntity(item);
		}
		super.breakBlock(world, pos, state);
	}
	

}
