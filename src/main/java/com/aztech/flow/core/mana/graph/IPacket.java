package com.aztech.flow.core.mana.graph;

public interface IPacket {
    public boolean hasComponent(ManaComponent<?> component);
    public <T> T getComponent(ManaComponent<T> component);
    public <T> void addComponent(ManaComponent<T> component, T content);
}
