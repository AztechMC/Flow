package com.aztech.flow.core.spells;

public interface IPacket {
    boolean hasComponent(ManaComponent<?> component);
    <T> T getComponent(ManaComponent<T> component);
    <T> void addComponent(ManaComponent<T> component, T content);
}
