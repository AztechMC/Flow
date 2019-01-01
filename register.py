def register_item(id, name):
    file = open("src/main/resources/assets/flow/lang/en_us.lang", "a")
    file.write("\n")
    file.write("item." + id + ".name=" + name)
    file.close()

    file = open("src/main/resources/assets/flow/models/item/" + id + ".json",
                "w")
    file.write(
        "{ \n \"parent\": \"item/generated\", \n \"textures\": {  \n \"layer0\": \"flow:items/"
        + id + "\" \n } \n \n }")

    file.close()


def register_block(id, name):
    file = open("src/main/resources/assets/flow/lang/en_us.lang", "a")
    file.write("\n")
    file.write("tile." + id + ".name=" + name)
    file.close()

    file = open("src/main/resources/assets/flow/models/block/" + id + ".json",
                "w")
    file.write(
        "{ \n \"parent\": \"block/cube_all\",\n\"textures\": {\n\"all\": \"flow:blocks/"
        + id + "\"\n}\n }")
    file.close()

    file = open("src/main/resources/assets/flow/blockstates/" + id + ".json",
                "w")
    file.write(
        "{\n \"forge_marker\": 1,\n \"defaults\": { \n \"model\": \"flow:" +
        id + "\" \n},\n\"variants\": {\n \"normal\": [{" +
        "}],\n \"inventory\": [{" + "}]\n}\n}")
    file.close()


def register_ore(id, name):

    register_item(id + "_fragment", name + " Fragment")
    register_item(id + "_ingot", name + " Ingot")
    register_item(id + "_tinydust", "Tiny pile of " + name + " Dust")
    register_item(id + "_dust", name + " Dust")

    register_block(id + "_ore", name + " Ore")

    file = open(
        "src/main/resources/assets/flow/recipes/" + id + "_dust_recipe" +
        ".json", "w")
    file.write(
        "{\n \"type\" : \"minecraft:crafting_shaped\",\n \"pattern\":[\n\"ttt\",\n \"ttt\",\n\"ttt\"\n ],\n\"key\":{\n\"t\": {\n\"item\" : \"flow:"
        + id + "_tinydust\" \n }\n},\n\"result\":{\n\"item\": \"flow:" + id +
        "_dust\",\n \"count\": 1\n }\n}")
    file.close()

    file = open(
        "src/main/resources/assets/flow/recipes/" + id + "_tinydust_recipe" +
        ".json", "w")
    file.write(
        "{\n \"type\" : \"minecraft:crafting_shaped\",\n \"pattern\":[\n\"f\"\n ],\n\"key\":{\n\"f\": {\n\"item\" : \"flow:"
        + id + "_fragment\" \n }\n},\n\"result\":{\n\"item\": \"flow:" + id +
        "_tinydust\",\n \"count\": 1\n }\n}")
    file.close()


def create_capability_provider(name, subpackage):
    text = """
package com.aztech.flow.capability;

import com.aztech.flow.capability.%s.I%s;
import net.minecraft.nbt.NBTBase;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class %sProvider implements ICapabilitySerializable<NBTBase> {
    @CapabilityInject(I%s.class)
    public static final Capability<I%s> CAPABILITY = null;

    private I%s instance = CAPABILITY.getDefaultInstance();

    @Override
    public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing facing) {
        return capability == CAPABILITY;
    }

    @Nullable
    @Override
    public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing) {
        return capability == CAPABILITY ? CAPABILITY.<T> cast(this.instance) : null;
    }

    @Override
    public NBTBase serializeNBT() {
        return CAPABILITY.getStorage().writeNBT(CAPABILITY, this.instance, null);
    }

    @Override
    public void deserializeNBT(NBTBase nbt) {
        CAPABILITY.getStorage().readNBT(CAPABILITY, this.instance, null, nbt);
    }
}""" % tuple([subpackage] + [name] * 5)
    with open(
            "src/main/java/com/aztech/flow/capability/%sProvider.java" % name,
            "w") as file:
        file.write(text)
