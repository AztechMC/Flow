package com.aztech.flow.spells.nodes;

import com.aztech.flow.Flow;
import com.aztech.flow.core.spells.IManaNode;
import com.aztech.flow.core.spells.IPacket;
import com.aztech.flow.spells.components.Components;
import com.aztech.flow.spells.components.WorldPos;
import net.minecraft.nbt.NBTTagCompound;

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

    @Override
    public IManaNode readNbt(NBTTagCompound nbt) {
        if(nbt != null) {
            this.dx = nbt.getInteger("dx");
            this.dy = nbt.getInteger("dy");
            this.dz = nbt.getInteger("dz");
        }
        return this;
    }

    @Override
    public NBTTagCompound writeNbt() {
        NBTTagCompound tag = new NBTTagCompound();
        tag.setInteger("dx", this.dx);
        tag.setInteger("dy", this.dy);
        tag.setInteger("dz", this.dz);
        return tag;
    }

    private int dx, dy, dz;

    public TranslationNode(int dx, int dy, int dz) {
        this.dx = dx;
        this.dy = dy;
        this.dz = dz;
    }

    public TranslationNode() {
        this.dx = this.dy = this.dz = 0;
    }
}
