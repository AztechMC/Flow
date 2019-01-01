package com.aztech.flow.capability;

import net.minecraft.nbt.NBTTagCompound;

public interface INbtSerializable<T> {
    NBTTagCompound writeNbt();
    T readNbt(NBTTagCompound nbt);
}
