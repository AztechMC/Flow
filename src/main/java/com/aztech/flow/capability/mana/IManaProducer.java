package com.aztech.flow.capability.mana;

import com.aztech.flow.capability.INbtSerializable;

public interface IManaProducer extends INbtSerializable<IManaProducer> {
    /**
     * How much mana this producer produce.
     */
    long getCurrentRate();
}
