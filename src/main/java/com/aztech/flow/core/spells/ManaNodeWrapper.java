package com.aztech.flow.core.spells;

public class ManaNodeWrapper {
    public final IManaNode node;

    public final String nodeName;

    public ManaNodeWrapper(IManaNode node, String nodeName) {
        this.node = node;
        this.nodeName = nodeName;
    }
}
