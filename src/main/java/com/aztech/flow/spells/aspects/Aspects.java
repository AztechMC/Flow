package com.aztech.flow.spells.aspects;

import com.aztech.flow.core.api.spells.Aspect;
import net.minecraft.item.ItemStack;

public class Aspects {
    public static final Aspect<ItemStack> ITEM_STACK_ASPECT = new Aspect<>();
    public static final Aspect<WorldPos> WORLD_POS_ASPECT = new Aspect<>();
}
