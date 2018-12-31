package com.aztech.flow.mana.nodes;

import com.aztech.flow.Flow;
import com.aztech.flow.core.mana.graph.IManaNode;
import com.aztech.flow.core.mana.graph.IManaNodeRegistry;
import com.aztech.flow.core.mana.graph.ManaNodeRegistry;

import java.util.concurrent.Callable;

public class ModNodes {
    public static final Callable<IManaNode> COPY_WORLD_BLOCK_NODE_FACTORY = CopyWorldBlockNode::new;
    public static final Callable<IManaNode> PLACE_INTRO_WORLD_NODE_FACTORY = PlaceIntoWorldNode::new;
    public static final Callable<IManaNode> TRANSLATION_NODE_FACTORY = TranslationNode::new;

    public static void register() {
        IManaNodeRegistry reg = ManaNodeRegistry.getInstance();
        reg.register(Flow.MODID, "copy_world_block", COPY_WORLD_BLOCK_NODE_FACTORY);
        reg.register(Flow.MODID, "place_into_world", PLACE_INTRO_WORLD_NODE_FACTORY);
        reg.register(Flow.MODID, "translation", TRANSLATION_NODE_FACTORY);
    }
}
