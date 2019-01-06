package com.aztech.flow.core.spells;

import com.aztech.flow.core.api.spells.IDrop;
import com.aztech.flow.core.api.spells.Aspect;

import java.util.HashMap;

public class Drop implements IDrop {
    private HashMap<Aspect<?>, Object> aspects;

    public Drop() {
        this.aspects = new HashMap<>();
    }

    @Override
    public <T> void addAspect(Aspect<T> aspect, T content) {
        this.aspects.put(aspect, content);
    }

    @Override
    public boolean hasAspect(Aspect<?> aspect) {
        return this.aspects.containsKey(aspect);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T getAspect(Aspect<T> aspect) {
        return (T) this.aspects.get(aspect);
    }
}
