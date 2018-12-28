package com.aztech.flow.ore;

import java.util.ArrayList;
import java.util.List;

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
	
	private static List<Ore> ores = new ArrayList<Ore>();
	
	private int min_height = 0, max_height = 128, chance = 0;
	private WorldGenCustomOre worldGenOre;
	private int gen_count;
	
	public Ore(String name, int tier){
		this.name = name;
		this.tier = tier;

		item_fragment = new ItemBasic(name+"_fragment");
		item_tinyPileOfDust = new ItemBasic(name+"_tinydust");
		item_dust = new ItemBasic(name+"_dust");
		item_ingot = new ItemBasic(name+"_ingot");
		
		ore = new CustomBlockOre(Material.ROCK, name+"_ore", item_fragment);
		
		ores.add(this);
		
		
	}
	
	public String getName(){
		return name;
	}
	
	public int getTier(){
		return tier;
	}
	
	public static void preInit(){
		COPPER_ORE = new Ore("copper", 1).setGen_count(8).setChance(25).setMax_height(64);
		TIN_ORE = new Ore("tin", 1).setGen_count(8).setChance(10).setMax_height(64);
		SILVER_ORE = new Ore("silver", 1).setGen_count(6).setChance(3).setMax_height(32);;
		PLATINIUM_ORE = new Ore("platinium", 2).setGen_count(6).setChance(1).setMax_height(20);
		LEAD_ORE = new Ore("lead", 1).setGen_count(6).setChance(5).setMax_height(64);
		MITRHIL_ORE = new Ore("mithril", 3).setGen_count(2).setChance(1).setMax_height(20);
		
		for(Ore ore : ores){
			ore.register();
		}
	}
	
	public void register(){
		
		GameRegistry.addSmelting(item_dust, new ItemStack(item_ingot, 1), 1.5f * tier);
		worldGenOre = new WorldGenCustomOre(this.ore, gen_count, min_height, max_height, chance);
		GameRegistry.registerWorldGenerator(worldGenOre, 0);
	}
	
	public int getMin_height() {
		return min_height;
	}

	public Ore setMin_height(int min_height) {
		this.min_height = min_height;
		return this;
	}

	public int getMax_height() {
		return max_height;
	}

	public Ore setMax_height(int max_height) {
		this.max_height = max_height;
		return this;
	}

	public int getChance() {
		return chance;
	}

	public Ore setChance(int chance) {
		this.chance = chance;
		return this;
	}

	public int getGen_count() {
		return gen_count;
	}

	public Ore setGen_count(int gen_count) {
		this.gen_count = gen_count;
		return this;
	}

	public static Ore COPPER_ORE;
	public static Ore TIN_ORE;
	public static Ore SILVER_ORE;
	public static Ore PLATINIUM_ORE;
	public static Ore LEAD_ORE;
	public static Ore MITRHIL_ORE;
	
}
