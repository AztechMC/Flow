package com.aztech.flow.tab;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class CustomCreativeTab extends CreativeTabs {
	private ItemStack iconItem;

	public CustomCreativeTab(String label) {
		super(label);
		
	}
	
	public void setIconItem(Item item){
		iconItem = new ItemStack(item);
	}

	@Override
	public ItemStack getTabIconItem() {
		return iconItem;
	}

}