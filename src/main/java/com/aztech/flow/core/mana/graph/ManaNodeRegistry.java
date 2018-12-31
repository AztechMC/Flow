package com.aztech.flow.core.mana.graph;

import com.aztech.flow.Flow;
import net.minecraft.nbt.NBTTagCompound;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.concurrent.Callable;

public class ManaNodeRegistry implements IManaNodeRegistry {
    private static ManaNodeRegistry INSTANCE = new ManaNodeRegistry();

    private HashMap<String, Callable<IManaNode>> nodes;

    private ManaNodeRegistry() {
        this.nodes = new HashMap<>();
    }

    private String getActualNodeName(String modId, String nodeName) {
        return modId + ":" + nodeName;
    }

    public static ManaNodeRegistry getInstance() {
        return INSTANCE;
    }

    @Override
    public ManaNodeWrapper constructNode(String modId, String nodeName, @Nullable NBTTagCompound parameters) {
        String actualNodeName = getActualNodeName(modId, nodeName);
        try {
            IManaNode node = nodes.get(actualNodeName).call().readNbt(parameters);
            return new ManaNodeWrapper(node, actualNodeName);
        } catch(Exception ex) {
            Flow.logger.warn("Exception caught in ManaNodeRegistry#constructNode(): " + ex.getMessage() + "\n" + ex.getStackTrace());
            // TODO: handle goddamn Exception?
        }
        Flow.logger.warn("ManaNodeRegistry#constructNode returned null and this is probably very bad!");
        return null;
    }

    @Override
    public ManaNodeWrapper getNodeFromNbt(NBTTagCompound nbt) {
        String nodeName = nbt.getString("nodeName");
        try {
            IManaNode node = nodes.get(nodeName).call().readNbt(nbt.getCompoundTag("node"));
            return new ManaNodeWrapper(node, nodeName);
        } catch(Exception ex) {
            Flow.logger.warn("Exception caught in ManaNodeRegistry#constructNode()", ex);
            // TODO: handle goddamn Exception?
        }
        Flow.logger.warn("ManaNodeRegistry#getNodeFromNbt returned null and this is probably very bad!");
        return null;
    }

    @Override
    public NBTTagCompound getNbtFromNode(ManaNodeWrapper node) {
        NBTTagCompound nbt = new NBTTagCompound();
        nbt.setString("nodeName", node.nodeName);
        nbt.setTag("node", node.node.writeNbt());
        return nbt;
    }

    @Override
    public void register(String modId, String nodeName, Callable<IManaNode> nodeFactory) {
        String key = this.getActualNodeName(modId, nodeName);
        if(nodes.containsKey(key)) {
            // TODO: already registered
        } else {
            nodes.put(key, nodeFactory);
        }
    }
}
