package com.aztech.flow.core.api.spells;

/**
 * A drop of mana traveling through the spell.
 */
public interface IDrop {
    boolean hasAspect(Aspect<?> component);

    <T> T getAspect(Aspect<T> component);

    <T> void addAspect(Aspect<T> component, T content);
}
