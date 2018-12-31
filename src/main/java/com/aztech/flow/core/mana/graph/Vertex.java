package com.aztech.flow.core.mana.graph;

import net.minecraft.nbt.NBTTagCompound;

/**
 * A vertex in a mana graph.
 */
public class Vertex {
    /**
     * Wrapper around the actual IManaNode represented by this vertex.
     */
    public final ManaNodeWrapper node;
    /**
     * Vertex (outgoing) adjacency list.
     */
    public final Edge[] adjList;
    /**
     * Horizontal position in the 2D representation of the mana graph.
     */
    public final float xPos;
    /**
     * Vertical position in the 2D representation of the mana graph.
     */
    public final float yPos;

    /**
     * Construct a Vertex from its fields. This is useful if you are building a graph for the first time.
     */
    public Vertex(ManaNodeWrapper node, Edge[] adjList, float xPos, float yPos) {
        this.node = node;
        this.adjList = adjList;
        this.xPos = xPos;
        this.yPos = yPos;
    }

    /**
     * Construct a Vertex from an NBTTagCompound. This is useful if you are rebuilding a saved graph.
     */
    public Vertex(NBTTagCompound nbt, IManaNodeRegistry reg) {
        this.node = reg.getNodeFromNbt(nbt.getCompoundTag("node"));
        this.adjList = Edge.getEdgesFromNbt(nbt.getCompoundTag("adjList"));
        this.xPos = nbt.getFloat("xPos");
        this.yPos = nbt.getFloat("yPos");
    }

    /**
     * Get a Vertex's representation as an NBTTagCompound.
     */
    public NBTTagCompound writeNbt(IManaNodeRegistry reg) {
        NBTTagCompound nbt = new NBTTagCompound();
        nbt.setTag("node", reg.getNbtFromNode(this.node));
        nbt.setTag("adjList", Edge.getNbtFromEdges(this.adjList));
        nbt.setFloat("xPos", this.xPos);
        nbt.setFloat("yPos", this.yPos);
        return nbt;
    }

    /**
     * Construct a Vertex array from an NBTTagCompound.
     */
    public static Vertex[] getVerticesFromNbt(NBTTagCompound nbt, IManaNodeRegistry reg) {
        int size = nbt.getInteger("size");
        Vertex[] vertices = new Vertex[size];
        for(int i = 0; i < size; ++i) {
            vertices[i] = new Vertex(nbt.getCompoundTag(String.format("vertex_%d", i)), reg);
        }
        return vertices;
    }

    /**
     * Construct an NBTTagCompound from a Vertex array.
     */
    public static NBTTagCompound getNbtFromVertices(Vertex[] vertices, IManaNodeRegistry reg) {
        int size = vertices.length;
        NBTTagCompound nbt = new NBTTagCompound();
        nbt.setInteger("size", size);
        for(int i = 0; i < size; ++i) {
            nbt.setTag(String.format("vertex_%d", i), vertices[i].writeNbt(reg));
        }
        return nbt;
    }
}
