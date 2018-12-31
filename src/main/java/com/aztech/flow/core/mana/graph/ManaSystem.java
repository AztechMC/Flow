package com.aztech.flow.core.mana.graph;

import net.minecraft.nbt.NBTTagCompound;

import java.util.LinkedList;
import java.util.List;

public class ManaSystem {
    private class PendingPacket {
        public Edge edge;
        public IPacket packet;
        public PendingPacket(Edge edge, IPacket packet) {
            this.edge = edge;
            this.packet = packet;
        }
    }

    public List<PendingPacket> queue;
    public final Vertex[] graph;

    /**
     * Create a mana system from a set of nodes and oriented edges.
     *
     * TODO: Every edge must only reference nodes in the graph or an exception will be thrown.
     * TODO: A node may only appear once in the list of nodes.
     */
    public ManaSystem(Vertex[] graph) {
        this.queue = new LinkedList<>();
        this.graph = graph;
    }

    /**
     * Create a mana system from NBT data.
     */
    public ManaSystem(NBTTagCompound nbt) {
        // TODO: VERY IMPORTANT: serialize `queue` too!
        this.queue = new LinkedList<>();
        this.graph = Vertex.getVerticesFromNbt(nbt.getCompoundTag("graph"), ManaNodeRegistry.getInstance());
    }

    /**
     * Write a mana system to NBT.
     */
    public NBTTagCompound writeNbt() {
        NBTTagCompound nbt = new NBTTagCompound();
        nbt.setTag("graph", Vertex.getNbtFromVertices(this.graph, ManaNodeRegistry.getInstance()));
        return nbt;
    }

    /**
     * Ticks the complete system
     */
    public void tick() {
        List<PendingPacket> newQueue = new LinkedList<>();

        // Process every queued packet
        for(PendingPacket pendingPacket : this.queue) {
            Edge inputEdge = pendingPacket.edge;
            Vertex sink = this.graph[inputEdge.sinkId];
            IPacket[] outputPackets = sink.node.node.processPacket(pendingPacket.packet, inputEdge.sinkInputId);
            Edge[] sinkAdj = sink.adjList;

            // Enqueue output packets
            for(int i = 0; i < outputPackets.length; ++i) {
                IPacket packet = outputPackets[i];
                if(packet != null) {
                    newQueue.add(new PendingPacket(sinkAdj[i], packet));
                }
            }
        }

        this.queue = newQueue;
    }

    /**
     * Insert a packet in the system
     */
    public void insertPacket(IPacket packet, Edge edge) {
        this.queue.add(new PendingPacket(edge, packet));
    }
}
