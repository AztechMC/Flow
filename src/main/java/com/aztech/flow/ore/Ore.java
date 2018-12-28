package com.aztech.flow.ore;

import com.aztech.flow.items.ItemBasic;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class Ore {
	
	private String name;
	private int tier;
	
	public Item item_tinyPileOfDust;
	public Item item_fragment;
	public Item item_dust;
	public Item item_ingot;
	public Block ore;
	
	public Ore(String name, int tier){
		this.name = name;
		this.tier = tier;

		item_fragment = new ItemBasic(name+"_fragment");
		item_tinyPileOfDust = new ItemBasic(name+"_tinydust");
		item_dust = new ItemBasic(name+"_dust");
		item_ingot = new ItemBasic(name+"_ingot");
		
		ore = new CustomBlockOre(Material.ROCK, name+"_ore", item_fragment);
		
		GameRegistry.addSmelting(item_dust, new ItemStack(item_ingot, 1), 1.5f * tier);
	}
	
	public String getName(){
		return name;
	}
	
	public int getTier(){
		return tier;
	}
	
	public static void preInit(){
		COPPER_ORE = new Ore("copper", 1);
		TIN_ORE = new Ore("tin", 1);
		SILVER_ORE = new Ore("silver", 1);
		PLATINIUM_ORE = new Ore("platinium", 2);
		LEAD_ORE = new Ore("lead", 1);
		MITRHIL_ORE = new Ore("mithril", 3);
	}
	
	public static Ore COPPER_ORE;
	public static Ore TIN_ORE;
	public static Ore SILVER_ORE;
	public static Ore PLATINIUM_ORE;
	public static Ore LEAD_ORE;
	public static Ore MITRHIL_ORE;
	
}
