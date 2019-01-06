package com.aztech.flow.core.api.spells;

import com.aztech.flow.core.spells.RuneWrapper;
import net.minecraft.nbt.NBTTagCompound;

import javax.annotation.Nullable;
import java.util.concurrent.Callable;

/**
 * The registry for the runes.
 */
public interface IRuneRegistry {
    RuneWrapper getRuneFromNbt(NBTTagCompound nbt);

    NBTTagCompound getNbtFromRune(RuneWrapper rune);

    void register(String modId, String runeId, Callable<IRune> runeFactory);

    RuneWrapper constructRune(String modId, String partialRuneId, @Nullable NBTTagCompound parameters);
}
