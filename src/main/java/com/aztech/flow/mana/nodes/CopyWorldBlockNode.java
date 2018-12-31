package com.aztech.flow.mana.nodes;

import com.aztech.flow.Flow;
import com.aztech.flow.core.mana.graph.IManaNode;
import com.aztech.flow.core.mana.graph.IPacket;
import com.aztech.flow.mana.components.Components;
import com.aztech.flow.mana.components.WorldPos;
import com.aztech.flow.util.ItemStackUtils;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class CopyWorldBlockNode implements IManaNode {
    @Override
    public IPacket[] processPacket(IPacket packet, int inputId) {
        Flow.logger.info("CopyWorldBlockNode::processPacket");
        // First, we check the packet has the required components
        if(packet.hasComponent(Components.WORLD_POS_MANA_COMPONENT) && !packet.hasComponent(Components.ITEM_STACK_MANA_COMPONENT)) {
            Flow.logger.info("Had the one necessary component");
            WorldPos worldPos = packet.getComponent(Components.WORLD_POS_MANA_COMPONENT);
            World world = worldPos.world;
            Flow.logger.info(String.format("Trying to copy from %d %d %d", worldPos.blockPos.getX(), worldPos.blockPos.getY(), worldPos.blockPos.getZ()));
            // We make sure the block position is loaded
            if(world.isBlockLoaded(worldPos.blockPos)) {
                Flow.logger.info("Block was loaded");
                IBlockState blockState = world.getBlockState(worldPos.blockPos);
                Flow.logger.info(String.format("isNormalCube(): %b", blockState.isNormalCube()));
                // We only allow copying of normal cubes
                if(blockState.isNormalCube()) {
                    ItemStack itemStack = ItemStackUtils.newItemStackFromBlockState(blockState);
                    packet.addComponent(Components.ITEM_STACK_MANA_COMPONENT, itemStack);
                    Flow.logger.info(String.format("Copied item: %s", itemStack.getItem().getUnlocalizedName()));
                } else {
                    // TODO: not a normal cube
                }
            } else {
                // TODO: position is not loaded
            }
        } else {
            // TODO: wrong components
        }
        return new IPacket[]{packet};
    }

    @Override
    public IManaNode readNbt(NBTTagCompound nbt) {
        return this;
    }

    @Override
    public NBTTagCompound writeNbt() {
        return new NBTTagCompound();
    }
}
