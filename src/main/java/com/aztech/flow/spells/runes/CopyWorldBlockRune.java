package com.aztech.flow.spells.runes;

import com.aztech.flow.Flow;
import com.aztech.flow.core.api.spells.IDrop;
import com.aztech.flow.core.api.spells.IRune;
import com.aztech.flow.spells.aspects.Aspects;
import com.aztech.flow.spells.aspects.WorldPos;
import com.aztech.flow.util.ItemStackUtils;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class CopyWorldBlockRune implements IRune {
    @Override
    public IDrop[] processDrop(IDrop packet, int inputId) {
        Flow.logger.info("CopyWorldBlockRune::processDrop");
        // First, we check the drop has the required aspects
        if (packet.hasAspect(Aspects.WORLD_POS_ASPECT) && !packet.hasAspect(Aspects.ITEM_STACK_ASPECT)) {
            Flow.logger.info("Had the one necessary component");
            WorldPos worldPos = packet.getAspect(Aspects.WORLD_POS_ASPECT);
            World world = worldPos.world;
            Flow.logger.info(String.format("Trying to copy from %d %d %d", worldPos.blockPos.getX(), worldPos.blockPos.getY(), worldPos.blockPos.getZ()));
            // We make sure the block position is loaded
            if (world.isBlockLoaded(worldPos.blockPos)) {
                Flow.logger.info("Block was loaded");
                IBlockState blockState = world.getBlockState(worldPos.blockPos);
                Flow.logger.info(String.format("isNormalCube(): %b", blockState.isNormalCube()));
                // We only allow copying of normal cubes
                if (blockState.isNormalCube()) {
                    ItemStack itemStack = ItemStackUtils.newItemStackFromBlockState(blockState);
                    packet.addAspect(Aspects.ITEM_STACK_ASPECT, itemStack);
                    Flow.logger.info(String.format("Copied item: %s", itemStack.getItem().getUnlocalizedName()));
                } else {
                    // TODO: not a normal cube
                }
            } else {
                // TODO: position is not loaded
            }
        } else {
            // TODO: wrong aspects
        }
        return new IDrop[]{packet};
    }

    @Override
    public void deserializeNBT(NBTTagCompound nbt) {

    }

    @Override
    public NBTTagCompound serializeNBT() {
        return new NBTTagCompound();
    }
}
