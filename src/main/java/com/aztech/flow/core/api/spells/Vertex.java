package com.aztech.flow.core.api.spells;

import com.aztech.flow.core.spells.RuneWrapper;
import net.minecraft.nbt.NBTTagCompound;

/**
 * A vertex in a spell.
 */
public class Vertex {
    /**
     * Wrapper around the actual IRune represented by this vertex.
     */
    public final RuneWrapper rune;

    /**
     * Vertex (outgoing) adjacency list.
     */
    public final Link[] adjList;

    /**
     * Horizontal position in the 2D representation of the mana graph.
     */
    public final float xPos;

    /**
     * Vertical position in the 2D representation of the mana graph.
     */
    public final float yPos;

    /**
     * Construct a Vertex from its fields.
     */
    public Vertex(RuneWrapper rune, Link[] adjList, float xPos, float yPos) {
        this.rune = rune;
        this.adjList = adjList;
        this.xPos = xPos;
        this.yPos = yPos;
    }

    /**
     * Construct a Vertex from an NBTTagCompound.
     */
    public Vertex(NBTTagCompound nbt, IRuneRegistry reg) {
        this.rune = reg.getRuneFromNbt(nbt.getCompoundTag("rune"));
        this.adjList = Link.getLinksFromNbt(nbt.getCompoundTag("adjList"));
        this.xPos = nbt.getFloat("xPos");
        this.yPos = nbt.getFloat("yPos");
    }

    /**
     * Get a Vertex's representation as an NBTTagCompound.
     */
    public NBTTagCompound writeNbt(IRuneRegistry reg) {
        NBTTagCompound nbt = new NBTTagCompound();
        nbt.setTag("rune", reg.getNbtFromRune(this.rune));
        nbt.setTag("adjList", Link.getNbtFromLinks(this.adjList));
        nbt.setFloat("xPos", this.xPos);
        nbt.setFloat("yPos", this.yPos);
        return nbt;
    }

    /**
     * Construct a Vertex array from an NBTTagCompound.
     */
    public static Vertex[] getVerticesFromNbt(NBTTagCompound nbt, IRuneRegistry reg) {
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
    public static NBTTagCompound getNbtFromVertices(Vertex[] vertices, IRuneRegistry reg) {
        int size = vertices.length;
        NBTTagCompound nbt = new NBTTagCompound();
        nbt.setInteger("size", size);
        for(int i = 0; i < size; ++i) {
            nbt.setTag(String.format("vertex_%d", i), vertices[i].writeNbt(reg));
        }
        return nbt;
    }
}
