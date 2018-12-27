package com.aztech.flow.core.mana.graph;

import java.util.HashMap;

public class Packet implements IPacket {
    HashMap<ManaComponent<?>, Object> components;

    public Packet() {
        this.components = new HashMap<>();
    }

    @Override
    public <T> void addComponent(ManaComponent<T> component, T content) {
        this.components.put(component, content);
    }

    @Override
    public boolean hasComponent(ManaComponent<?> component) {
        return this.components.containsKey(component);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T getComponent(ManaComponent<T> component) {
        return (T)this.components.get(component);
    }
}
