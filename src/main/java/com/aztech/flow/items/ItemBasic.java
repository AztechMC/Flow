package com.aztech.flow.items;

import com.aztech.flow.Flow;

import net.minecraft.item.Item;

public class ItemBasic extends Item {
    public ItemBasic(String name) {
        this.setUnlocalizedName(name);
        this.setRegistryName(name);
        
        ModItems.ITEMS.add(this);
        
        this.setCreativeTab(Flow.CREATIVE_TAB);
    }
}
