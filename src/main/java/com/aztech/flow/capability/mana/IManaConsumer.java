package com.aztech.flow.capability.mana;

import com.aztech.flow.capability.INbtSerializable;

public interface IManaConsumer extends INbtSerializable<IManaConsumer> {
    /**
     * How much mana this consumer would like to receive per tick.
     */
    long getMaxRate();

    /**
     * Current rate that the consumer receives at.
     */
    void setCurrentRate(long rate);
}
