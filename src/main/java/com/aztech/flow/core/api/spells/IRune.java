package com.aztech.flow.core.api.spells;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.INBTSerializable;

/**
 * A rune in a spell.
 */
public interface IRune extends INBTSerializable<NBTTagCompound> {
    /**
     * Process one drop.
     *
     * @param drop    Received drop.
     * @param inputId Side from which the drop was received.
     * @return Output drops. A non-null drop at position i corresponds to a drop sent to output link number i.
     */
    IDrop[] processDrop(IDrop drop, int inputId);
}
