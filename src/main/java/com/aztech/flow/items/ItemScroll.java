package com.aztech.flow.items;

import com.aztech.flow.Flow;
import com.aztech.flow.capability.ISpellCast;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;

public class ItemScroll extends ItemBasic {
    @CapabilityInject(ISpellCast.class)
    public static final Capability<ISpellCast> SPELL_CAST_CAPABILITY = null;

    public ItemScroll(String name) {
        super(name);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
        if(!worldIn.isRemote) {
            ItemStack itemStack = playerIn.getHeldItem(handIn);
            if(itemStack.hasCapability(SPELL_CAST_CAPABILITY, null)) {
                itemStack.getCapability(SPELL_CAST_CAPABILITY, null).startSpellCast(worldIn, playerIn.getPosition());
            }
        }
        return super.onItemRightClick(worldIn, playerIn, handIn);
    }

    @Override
    public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
        if(!worldIn.isRemote) {
            if(stack.hasCapability(SPELL_CAST_CAPABILITY, null)) {
                stack.getCapability(SPELL_CAST_CAPABILITY, null).tick();
            }
        }
    }

}
