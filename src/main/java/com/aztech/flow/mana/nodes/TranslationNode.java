package com.aztech.flow.mana.nodes;

import com.aztech.flow.Flow;
import com.aztech.flow.core.mana.graph.IManaNode;
import com.aztech.flow.core.mana.graph.IPacket;
import com.aztech.flow.mana.components.Components;
import com.aztech.flow.mana.components.WorldPos;

public class TranslationNode implements IManaNode {
    @Override
    public IPacket[] processPacket(IPacket packet, int inputId) {
        Flow.logger.info("TranslationNode::processPacket");
        if(packet.hasComponent(Components.WORLD_POS_MANA_COMPONENT)) {
            Flow.logger.info("Had WORLD_POS_MANA_COMPONENT");
            WorldPos worldPos = packet.getComponent(Components.WORLD_POS_MANA_COMPONENT);
            Flow.logger.info(String.format("Old pos: %d %d %d", worldPos.blockPos.getX(), worldPos.blockPos.getY(), worldPos.blockPos.getZ()));
            worldPos.blockPos = worldPos.blockPos.add(this.dx, this.dy, this.dz);
            Flow.logger.info(String.format("New pos: %d %d %d", worldPos.blockPos.getX(), worldPos.blockPos.getY(), worldPos.blockPos.getZ()));
        }
        return new IPacket[]{packet};
    }

    private int dx, dy, dz;

    public TranslationNode(int dx, int dy, int dz) {
        this.dx = dx;
        this.dy = dy;
        this.dz = dz;
    }
}
