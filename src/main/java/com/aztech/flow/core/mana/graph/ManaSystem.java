package com.aztech.flow.core.mana.graph;

import java.util.ArrayList;
import java.util.HashMap;
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

    private List<PendingPacket> queue;
    private HashMap<IManaNode, List<Edge>> graph;

    /**
     * Create a mana system from a set of nodes and oriented edges
     *
     * Every edge must only reference nodes in the graph or an exception will be thrown.
     * A node may only appear once in the list of nodes.
     * @param nodes Nodes
     * @param edges Edges
     */
    public ManaSystem(List<IManaNode> nodes, List<Edge> edges) {
        this.queue = new LinkedList<>();
        this.graph = new HashMap<>();
        for(IManaNode node : nodes) {
            if(this.graph.containsKey(node)) {
                // TODO: DuplicateNodeException
            } else {
                this.graph.put(node, new ArrayList<>());
            }
        }

        for(Edge e : edges) {
            IManaNode s = e.source, t = e.sink;
            if(!this.graph.containsKey(s) || !this.graph.containsKey(t)) {
                // TODO: NodeNotInSystemException
            } else {
                List<Edge> sourceAdj = this.graph.get(s);
                while(sourceAdj.size() <= e.sourceOutputId) {
                    sourceAdj.add(null); // TODO: maybe optimize
                }
                if(e.sourceOutputId < 0) {
                    // TODO: negative index
                }
                sourceAdj.set(e.sourceOutputId, e);
            }
        }
    }

    /**
     * Ticks the complete system
     */
    public void tick() {
        List<PendingPacket> newQueue = new LinkedList<>();

        // Process every queued packet
        for(PendingPacket pendingPacket : this.queue) {
            Edge inputEdge = pendingPacket.edge;
            IPacket[] outputPackets = inputEdge.sink.processPacket(pendingPacket.packet, inputEdge.sinkInputId);
            List<Edge> sinkAdj = this.graph.get(inputEdge.sink);

            // Enqueue output packets
            for(int i = 0; i < outputPackets.length; ++i) {
                IPacket packet = outputPackets[i];
                if(packet != null) {
                    newQueue.add(new PendingPacket(sinkAdj.get(i), packet));
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
