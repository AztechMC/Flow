package com.aztech.flow.capability.mana;

import com.aztech.flow.capability.INbtSerializable;

public interface IManaStorage extends INbtSerializable<IManaStorage> {
    /**
     * Maximum amount of mana that can be stored.
     */
    long getMaxStorage();

    /**
     * Get amount of mana currently in storage.
     */
    long getCurrentStorage();

    /**
     * Set amount of mana currently in storage.
     */
    void setCurrentStorage(long mana);
}
