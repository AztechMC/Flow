package com.aztech.flow.mana.components;

import com.aztech.flow.core.mana.graph.ManaComponent;
import net.minecraft.item.ItemStack;

public class Components {
    public static final ManaComponent<ItemStack> ITEM_STACK_MANA_COMPONENT = new ManaComponent<>();
    public static final ManaComponent<WorldPos> WORLD_POS_MANA_COMPONENT = new ManaComponent<>();
}
