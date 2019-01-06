package com.aztech.flow.core.api.spells;

import net.minecraft.nbt.NBTTagCompound;

/**
 * A link in a spell.
 */
public class Link {

    public int getSinkId() {
        return sinkId;
    }

    public int getSinkInputId() {
        return sinkInputId;
    }

    /**
     * Sink id.
     */
    private int sinkId;

    /**
     * Incoming link id to the sink. It can be any number. Refer to the sink IRune
     * to know what value it should be.
     */
    private int sinkInputId;

    /**
     * Construct a Link from its fields.
     */
    public Link(int sinkId, int sinkInputId) {
        this.sinkId = sinkId;
        this.sinkInputId = sinkInputId;
    }

    /**
     * Construct a Link from NBT data.
     */
    public Link(NBTTagCompound nbt) {
        this.sinkId = nbt.getInteger("sinkId");
        this.sinkInputId = nbt.getInteger("sinkInputId");
    }

    /**
     * Serialize to NBT.
     */
    public NBTTagCompound writeNbt() {
        NBTTagCompound nbt = new NBTTagCompound();
        nbt.setInteger("sinkId", this.sinkId);
        nbt.setInteger("sinkInputId", this.sinkInputId);
        return nbt;
    }

    /**
     * Construct a Link array from an NBTTagCompound.
     */
    public static Link[] getLinksFromNbt(NBTTagCompound nbt) {
        int size = nbt.getInteger("size");
        Link[] links = new Link[size];
        for (int i = 0; i < size; ++i) {
            links[i] = new Link(nbt.getCompoundTag(String.format("link_%d", i)));
        }
        return links;
    }

    /**
     * Construct an NBTTagCompound from a Link array.
     */
    public static NBTTagCompound getNbtFromLinks(Link[] links) {
        int size = links.length;
        NBTTagCompound nbt = new NBTTagCompound();
        nbt.setInteger("size", size);
        for (int i = 0; i < size; ++i) {
            nbt.setTag(String.format("link_%d", i), links[i].writeNbt());
        }
        return nbt;
    }
}
