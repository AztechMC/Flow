package com.aztech.flow.core.spells;

import net.minecraft.nbt.NBTTagCompound;

/**
 * An edge in a mana graph.
 */
public class Edge {
    /**
     * Sink node id.
     */
    public final int sinkId;
    /**
     * Incoming connection id to the sink. It can be any number. Refer to the corresponding IManaNode
     * to know what value it should be.
     */
    public final int sinkInputId;

    /**
     * Construct an Edge from its fields. This is useful if you are building a graph for the first time.
     */
    public Edge(int sinkId, int sinkInputId) {
        this.sinkId = sinkId;
        this.sinkInputId = sinkInputId;
    }

    /**
     * Construct an Edge from an NBTTagCompound. This is useful if you are rebuilding a saved graph.
     */
    public Edge(NBTTagCompound nbt) {
        this.sinkId = nbt.getInteger("sinkId");
        this.sinkInputId = nbt.getInteger("sinkInputId");
    }

    /**
     * Get an Edge's representation as an NBTTagCompound.
     */
    public NBTTagCompound writeNbt() {
        NBTTagCompound nbt = new NBTTagCompound();
        nbt.setInteger("sinkId", this.sinkId);
        nbt.setInteger("sinkInputId", this.sinkInputId);
        return nbt;
    }

    /**
     * Construct an Edge array from an NBTTagCompound.
     */
    public static Edge[] getEdgesFromNbt(NBTTagCompound nbt) {
        int size = nbt.getInteger("size");
        Edge[] edges = new Edge[size];
        for(int i = 0; i < size; ++i) {
            edges[i] = new Edge(nbt.getCompoundTag(String.format("edge_%d", i)));
        }
        return edges;
    }

    /**
     * Construct an NBTTagCompound from an Edge array.
     */
    public static NBTTagCompound getNbtFromEdges(Edge[] edges) {
        int size = edges.length;
        NBTTagCompound nbt = new NBTTagCompound();
        nbt.setInteger("size", size);
        for(int i = 0; i < size; ++i) {
            nbt.setTag(String.format("edge_%d", i), edges[i].writeNbt());
        }
        return nbt;
    }
}
