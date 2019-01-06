package com.aztech.flow.spells.runes;

import com.aztech.flow.Flow;
import com.aztech.flow.core.spells.RuneRegistry;
import com.aztech.flow.core.api.spells.IRune;
import com.aztech.flow.core.api.spells.IRuneRegistry;

import java.util.concurrent.Callable;

public class ModRunes {
    public static final Callable<IRune> COPY_WORLD_BLOCK_RUNE_FACTORY = CopyWorldBlockRune::new;
    public static final Callable<IRune> PLACE_INTRO_WORLD_RUNE_FACTORY = PlaceIntoWorldRune::new;
    public static final Callable<IRune> TRANSLATION_RUNE_FACTORY = TranslationRune::new;

    public static void register() {
        IRuneRegistry reg = RuneRegistry.getInstance();
        reg.register(Flow.MODID, "copy_world_block", COPY_WORLD_BLOCK_RUNE_FACTORY);
        reg.register(Flow.MODID, "place_into_world", PLACE_INTRO_WORLD_RUNE_FACTORY);
        reg.register(Flow.MODID, "translation", TRANSLATION_RUNE_FACTORY);
    }
}
