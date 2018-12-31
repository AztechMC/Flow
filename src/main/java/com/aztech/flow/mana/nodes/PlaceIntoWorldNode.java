package com.aztech.flow.mana.nodes;

import com.aztech.flow.Flow;
import com.aztech.flow.core.mana.graph.IManaNode;
import com.aztech.flow.core.mana.graph.IPacket;
import com.aztech.flow.mana.components.Components;
import com.aztech.flow.mana.components.WorldPos;
import net.minecraft.block.Block;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class PlaceIntoWorldNode implements IManaNode {

    @Override
    public IPacket[] processPacket(IPacket packet, int inputId) {
        Flow.logger.info("PlaceIntoWorldNode::processPacket");
        // Check that both components are in the packet
        if(packet.hasComponent(Components.ITEM_STACK_MANA_COMPONENT) && packet.hasComponent(Components.WORLD_POS_MANA_COMPONENT)) {
            Flow.logger.info("Had the two necessary component");
            ItemStack itemStack = packet.getComponent(Components.ITEM_STACK_MANA_COMPONENT);
            WorldPos worldPos = packet.getComponent(Components.WORLD_POS_MANA_COMPONENT);
            BlockPos blockPos = worldPos.blockPos;

            Item item = itemStack.getItem();
            World world = worldPos.world;
            // Make sure that the Item is actually a Block
            // TODO: check that there is at least 1 of the block
            Flow.logger.info(String.format("The item is %s", item.getUnlocalizedName()));
            if(item instanceof ItemBlock) {
                Flow.logger.info("Item was a block");
                Block block = ((ItemBlock)item).getBlock();
                // Make sure that the block is loaded and that it is an air block
                if (world.isBlockLoaded(blockPos) && world.isAirBlock(blockPos)) {
                    Flow.logger.info("Set BlockState!");
                    world.setBlockState(blockPos, block.getStateFromMeta(itemStack.getMetadata()));
                } else {
                    // TODO: block not loaded
                }
            } else {
                // Drop item in the world
                Flow.logger.info("Dropped item in the world");
                EntityItem entityItem = new EntityItem(
                        world,
                        (double)blockPos.getX()+0.5,
                        (double)blockPos.getY()+0.5,
                        (double)blockPos.getZ()+0.5,
                        itemStack);
                world.spawnEntity(entityItem);
            }

        } else {
            // TODO: invalid components
        }
        return new IPacket[0];
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
