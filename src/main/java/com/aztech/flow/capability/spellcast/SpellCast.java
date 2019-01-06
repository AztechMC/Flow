package com.aztech.flow.capability.spellcast;

import com.aztech.flow.Flow;
import com.aztech.flow.core.mana.graph.*;
import com.aztech.flow.core.spells.*;
import com.aztech.flow.spells.components.Components;
import com.aztech.flow.spells.components.WorldPos;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
/**
 * Default implementation of ISpellCast
 */
public class SpellCast implements ISpellCast {
    private ManaSystem system;
    private Edge inputEdge;

    public SpellCast() {
        ManaNodeRegistry reg = ManaNodeRegistry.getInstance();
        NBTTagCompound nbt1 = new NBTTagCompound(); nbt1.setInteger("dx", 0); nbt1.setInteger("dy", -5); nbt1.setInteger("dz", 0);
        NBTTagCompound nbt2 = new NBTTagCompound(); nbt2.setInteger("dx", 0); nbt2.setInteger("dy", 10); nbt2.setInteger("dz", 0);
        ManaNodeWrapper[] nodes = new ManaNodeWrapper[]{
                reg.constructNode(Flow.MODID, "translation", null),
                reg.constructNode(Flow.MODID, "translation", nbt1),
                reg.constructNode(Flow.MODID, "copy_world_block", null),
                reg.constructNode(Flow.MODID, "translation", nbt2),
                reg.constructNode(Flow.MODID, "place_into_world", null),
        };

        Vertex[] vertices = new Vertex[nodes.length];
        for(int i = 0; i < vertices.length; ++i) {
            vertices[i] = new Vertex(
                    nodes[i],
                    (i < vertices.length - 1 ? new Edge[]{new Edge(i+1, 0)} : new Edge[0]),
                    (float)i,
                    0.0f
            );
        }

        this.system = new ManaSystem(vertices);
        this.inputEdge = vertices[0].adjList[0];
    }

    @Override
    public void startSpellCast(World world, BlockPos blockPos) {
        IPacket packet = new Packet();
        packet.addComponent(Components.WORLD_POS_MANA_COMPONENT, new WorldPos(world, blockPos));
        this.system.insertPacket(packet, this.inputEdge);
    }

    @Override
    public void tick() {
        this.system.tick();
    }

    @Override
    public ManaSystem getUnderlyingSystem() {
        return system;
    }

    @Override
    public NBTTagCompound writeNbt() {
        NBTTagCompound nbt = new NBTTagCompound();
        nbt.setTag("system", this.system.writeNbt());
        nbt.setTag("inputEdge", this.inputEdge.writeNbt());
        return nbt;
    }

    @Override
    public ISpellCast readNbt(NBTTagCompound nbt) {
        this.system = new ManaSystem(nbt.getCompoundTag("system"));
        this.inputEdge = new Edge(nbt.getCompoundTag("inputEdge"));
        return this;
    }
}
