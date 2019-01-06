package com.aztech.flow.core.spells;

import net.minecraft.nbt.NBTTagCompound;

import javax.annotation.Nullable;
import java.util.concurrent.Callable;

public interface IManaNodeRegistry {
    ManaNodeWrapper getNodeFromNbt(NBTTagCompound nbt);
    NBTTagCompound getNbtFromNode(ManaNodeWrapper node);
    void register(String modId, String nodeName, Callable<IManaNode> nodeFactory);
    ManaNodeWrapper constructNode(String modId, String nodeName, @Nullable NBTTagCompound parameters);
}
