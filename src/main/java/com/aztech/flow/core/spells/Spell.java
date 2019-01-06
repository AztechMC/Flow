package com.aztech.flow.core.spells;

import com.aztech.flow.core.api.spells.IDrop;
import com.aztech.flow.core.api.spells.Link;
import com.aztech.flow.core.api.spells.Vertex;
import net.minecraft.nbt.NBTTagCompound;

import java.util.LinkedList;
import java.util.List;

public class Spell {
    private static class PendingDrop {
        public Link link;
        public IDrop drop;

        public PendingDrop(Link link, IDrop drop) {
            this.link = link;
            this.drop = drop;
        }
    }

    public List<PendingDrop> queue;
    public final Vertex[] graph;

    /**
     * Create a spell from a set of runes and (oriented) links.
     * <p>
     * TODO: Every link must only reference runes in the spell or an exception will be thrown.
     * TODO: A rune may only appear once in the list of runes.
     */
    public Spell(Vertex[] graph) {
        this.queue = new LinkedList<>();
        this.graph = graph;
    }

    /**
     * Create a spell from NBT data.
     */
    public Spell(NBTTagCompound nbt) {
        // TODO: VERY IMPORTANT: serialize `queue` too!
        this.queue = new LinkedList<>();
        this.graph = Vertex.getVerticesFromNbt(nbt.getCompoundTag("graph"), RuneRegistry.getInstance());
    }

    /**
     * Write a spell to NBT.
     */
    public NBTTagCompound writeNbt() {
        NBTTagCompound nbt = new NBTTagCompound();
        nbt.setTag("graph", Vertex.getNbtFromVertices(this.graph, RuneRegistry.getInstance()));
        return nbt;
    }

    /**
     * Tick the spell. This moves every queued drop forward in the spell.
     */
    public void tick() {
        List<PendingDrop> newQueue = new LinkedList<>();

        // Process every queued drop
        for (PendingDrop pendingDrop : this.queue) {
            Link inputLink = pendingDrop.link;
            Vertex sink = this.graph[inputLink.getSinkId()];
            IDrop[] outputDrops = sink.rune.rune.processDrop(pendingDrop.drop, inputLink.getSinkInputId());
            Link[] sinkAdj = sink.adjList;

            // Enqueue output packets
            for (int i = 0; i < outputDrops.length; ++i) {
                IDrop drop = outputDrops[i];
                if (drop != null) {
                    newQueue.add(new PendingDrop(sinkAdj[i], drop));
                }
            }
        }

        this.queue = newQueue;
    }

    /**
     * Insert a drop in the system
     */
    public void insertDrop(IDrop drop, Link link) {
        this.queue.add(new PendingDrop(link, drop));
    }
}
