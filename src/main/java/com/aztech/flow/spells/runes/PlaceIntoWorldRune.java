package com.aztech.flow.spells.runes;

import com.aztech.flow.Flow;
import com.aztech.flow.core.api.spells.IDrop;
import com.aztech.flow.core.api.spells.IRune;
import com.aztech.flow.spells.aspects.Aspects;
import com.aztech.flow.spells.aspects.WorldPos;
import net.minecraft.block.Block;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class PlaceIntoWorldRune implements IRune {

    @Override
    public IDrop[] processDrop(IDrop packet, int inputId) {
        Flow.logger.info("PlaceIntoWorldRune::processDrop");
        // Check that both aspects are in the drop
        if (packet.hasAspect(Aspects.ITEM_STACK_ASPECT) && packet.hasAspect(Aspects.WORLD_POS_ASPECT)) {
            Flow.logger.info("Had the two necessary component");
            ItemStack itemStack = packet.getAspect(Aspects.ITEM_STACK_ASPECT);
            WorldPos worldPos = packet.getAspect(Aspects.WORLD_POS_ASPECT);
            BlockPos blockPos = worldPos.blockPos;

            Item item = itemStack.getItem();
            World world = worldPos.world;
            // Make sure that the Item is actually a Block
            // TODO: check that there is at least 1 of the block
            Flow.logger.info(String.format("The item is %s", item.getUnlocalizedName()));
            if (item instanceof ItemBlock) {
                Flow.logger.info("Item was a block");
                Block block = ((ItemBlock) item).getBlock();
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
                        (double) blockPos.getX() + 0.5,
                        (double) blockPos.getY() + 0.5,
                        (double) blockPos.getZ() + 0.5,
                        itemStack);
                world.spawnEntity(entityItem);
            }

        } else {
            // TODO: invalid aspects
        }
        return new IDrop[0];
    }

    @Override
    public void deserializeNBT(NBTTagCompound nbt) {

    }

    @Override
    public NBTTagCompound serializeNBT() {
        return new NBTTagCompound();
    }
}
