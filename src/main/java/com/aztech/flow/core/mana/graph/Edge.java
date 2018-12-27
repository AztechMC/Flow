package com.aztech.flow.core.mana.graph;

/**
 * An edge in the mana graph.
 */
public class Edge {
    public final IManaNode source;
    // Actual output id. Cannot be negative and cannot be too large.
    public final int sourceOutputId;
    public final IManaNode sink;
    // Symbolic input id. It can be negative or large, it doesn't matter.
    public final int sinkInputId;

    public Edge(IManaNode source, int sourceOutputId, IManaNode sink, int sinkInputId) {
        this.source = source;
        this.sourceOutputId = sourceOutputId;
        this.sink = sink;
        this.sinkInputId = sinkInputId;
    }
}
