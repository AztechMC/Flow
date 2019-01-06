package com.aztech.flow.core.spells;

import com.aztech.flow.core.api.spells.IRune;

public class RuneWrapper {
    public final IRune rune;

    public final String runeId;

    public RuneWrapper(IRune rune, String runeId) {
        this.rune = rune;
        this.runeId = runeId;
    }
}
