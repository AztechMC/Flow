package com.aztech.flow.capability.spellholder;

import com.aztech.flow.Flow;
import com.aztech.flow.core.spells.*;
import com.aztech.flow.core.api.spells.IDrop;
import com.aztech.flow.core.api.spells.Link;
import com.aztech.flow.core.spells.Drop;
import com.aztech.flow.core.api.spells.Vertex;
import com.aztech.flow.spells.aspects.Aspects;
import com.aztech.flow.spells.aspects.WorldPos;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/**
 * Default implementation of ISpellHolder
 */
public class SpellHolder implements ISpellHolder {
    private Spell system;
    private Link inputLink;

    public SpellHolder() {
        RuneRegistry reg = RuneRegistry.getInstance();
        NBTTagCompound nbt1 = new NBTTagCompound();
        nbt1.setInteger("dx", 0);
        nbt1.setInteger("dy", -5);
        nbt1.setInteger("dz", 0);
        NBTTagCompound nbt2 = new NBTTagCompound();
        nbt2.setInteger("dx", 0);
        nbt2.setInteger("dy", 10);
        nbt2.setInteger("dz", 0);
        RuneWrapper[] nodes = new RuneWrapper[]{
                reg.constructRune(Flow.MODID, "translation", null),
                reg.constructRune(Flow.MODID, "translation", nbt1),
                reg.constructRune(Flow.MODID, "copy_world_block", null),
                reg.constructRune(Flow.MODID, "translation", nbt2),
                reg.constructRune(Flow.MODID, "place_into_world", null),
        };

        Vertex[] vertices = new Vertex[nodes.length];
        for (int i = 0; i < vertices.length; ++i) {
            vertices[i] = new Vertex(
                    nodes[i],
                    (i < vertices.length - 1 ? new Link[]{new Link(i + 1, 0)} : new Link[0]),
                    (float) i,
                    0.0f
            );
        }

        this.system = new Spell(vertices);
        this.inputLink = vertices[0].adjList[0];
    }

    @Override
    public void cast(World world, BlockPos blockPos) {
        IDrop packet = new Drop();
        packet.addAspect(Aspects.WORLD_POS_ASPECT, new WorldPos(world, blockPos));
        this.system.insertDrop(packet, this.inputLink);
    }

    @Override
    public void tick() {
        this.system.tick();
    }

    @Override
    public Spell getSpell() {
        return system;
    }

    @Override
    public NBTTagCompound serializeNBT() {
        NBTTagCompound nbt = new NBTTagCompound();
        nbt.setTag("system", this.system.writeNbt());
        nbt.setTag("inputLink", this.inputLink.writeNbt());
        return nbt;
    }

    @Override
    public void deserializeNBT(NBTTagCompound nbt) {
        this.system = new Spell(nbt.getCompoundTag("system"));
        this.inputLink = new Link(nbt.getCompoundTag("inputLink"));
    }
}
