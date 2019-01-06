package com.aztech.flow.core.spells;

import com.aztech.flow.Flow;
import com.aztech.flow.core.api.spells.IRune;
import com.aztech.flow.core.api.spells.IRuneRegistry;
import net.minecraft.nbt.NBTTagCompound;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.concurrent.Callable;

public class RuneRegistry implements IRuneRegistry {
    private static RuneRegistry INSTANCE = new RuneRegistry();

    private HashMap<String, Callable<IRune>> runes;

    private RuneRegistry() {
        this.runes = new HashMap<>();
    }

    private String getRuneId(String modId, String partialRuneId) {
        return modId + ":" + partialRuneId;
    }

    public static RuneRegistry getInstance() {
        return INSTANCE;
    }

    @Override
    public RuneWrapper constructRune(String modId, String partialRuneId, @Nullable NBTTagCompound parameters) {
        String runeId = getRuneId(modId, partialRuneId);
        try {
            IRune rune = runes.get(runeId).call();
            rune.deserializeNBT(parameters);
            return new RuneWrapper(rune, runeId);
        } catch (Exception ex) {
            Flow.logger.warn("Exception caught in RuneRegistry#constructRune(): " + ex.getMessage() + "\n" + ex.getStackTrace());
            // TODO: handle goddamn Exception?
        }
        Flow.logger.warn("RuneRegistry#constructRune returned null and this is probably very bad!");
        return null;
    }

    @Override
    public RuneWrapper getRuneFromNbt(NBTTagCompound nbt) {
        String runeId = nbt.getString("runeId");
        try {
            IRune rune = runes.get(runeId).call();
            rune.deserializeNBT(nbt.getCompoundTag("rune"));
            return new RuneWrapper(rune, runeId);
        } catch (Exception ex) {
            Flow.logger.warn("Exception caught in RuneRegistry#constructRune()", ex);
            // TODO: handle goddamn Exception?
        }
        Flow.logger.warn("RuneRegistry#getRuneFromNbt returned null and this is probably very bad!");
        return null;
    }

    @Override
    public NBTTagCompound getNbtFromRune(RuneWrapper rune) {
        NBTTagCompound nbt = new NBTTagCompound();
        nbt.setString("runeId", rune.runeId);
        nbt.setTag("rune", rune.rune.serializeNBT());
        return nbt;
    }

    @Override
    public void register(String modId, String partialRuneId, Callable<IRune> runeFactory) {
        String runeId = this.getRuneId(modId, partialRuneId);
        if (runes.containsKey(runeId)) {
            // TODO: already registered
        } else {
            runes.put(runeId, runeFactory);
        }
    }
}
