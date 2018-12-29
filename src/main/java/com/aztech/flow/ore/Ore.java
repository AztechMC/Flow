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

    public Item itemTinyPileOfDust;
    public Item itemFragment;
    public Item itemDust;
    public Item itemIngot;
    public Block ore;

    private static List<Ore> ores = new ArrayList<>();

    private int minHeight = 0, maxHeight = 128, chance = 0;
    private WorldGenCustomOre worldGenOre;
    private int genCount;

    public Ore(String name, int tier) {
        this.name = name;
        this.tier = tier;

        itemFragment = new ItemBasic(name + "_fragment");
        itemTinyPileOfDust = new ItemBasic(name + "_tinydust");
        itemDust = new ItemBasic(name + "_dust");
        itemIngot = new ItemBasic(name + "_ingot");

        ore = new CustomBlockOre(Material.ROCK, name + "_ore", itemFragment);

        ores.add(this);


    }

    public String getName() {
        return name;
    }

    public int getTier() {
        return tier;
    }

    public static void preInit() {
        COPPER_ORE = new Ore("copper", 1).setGenCount(8).setChance(25).setMaxHeight(64);
        TIN_ORE = new Ore("tin", 1).setGenCount(8).setChance(10).setMaxHeight(64);
        SILVER_ORE = new Ore("silver", 1).setGenCount(6).setChance(3).setMaxHeight(32);
        PLATINIUM_ORE = new Ore("platinium", 2).setGenCount(6).setChance(1).setMaxHeight(20);
        LEAD_ORE = new Ore("lead", 1).setGenCount(6).setChance(5).setMaxHeight(64);
        MITRHIL_ORE = new Ore("mithril", 3).setGenCount(2).setChance(1).setMaxHeight(20);

        for (Ore ore : ores) {
            ore.register();
        }
    }

    public void register() {

        GameRegistry.addSmelting(itemDust, new ItemStack(itemIngot, 1), 1.5f * tier);
        worldGenOre = new WorldGenCustomOre(this.ore, genCount, minHeight, maxHeight, chance);
        GameRegistry.registerWorldGenerator(worldGenOre, 0);
    }

    public int getMinHeight() {
        return minHeight;
    }

    public Ore setMinHeight(int minHeight) {
        this.minHeight = minHeight;
        return this;
    }

    public int getMaxHeight() {
        return maxHeight;
    }

    public Ore setMaxHeight(int maxHeight) {
        this.maxHeight = maxHeight;
        return this;
    }

    public int getChance() {
        return chance;
    }

    public Ore setChance(int chance) {
        this.chance = chance;
        return this;
    }

    public int getGenCount() {
        return genCount;
    }

    public Ore setGenCount(int genCount) {
        this.genCount = genCount;
        return this;
    }

    public static Ore COPPER_ORE;
    public static Ore TIN_ORE;
    public static Ore SILVER_ORE;
    public static Ore PLATINIUM_ORE;
    public static Ore LEAD_ORE;
    public static Ore MITRHIL_ORE;

}
