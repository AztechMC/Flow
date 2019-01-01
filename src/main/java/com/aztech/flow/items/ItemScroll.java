package com.aztech.flow.items;

import com.aztech.flow.Flow;
import com.aztech.flow.capability.spellcast.ISpellCast;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;

import javax.annotation.Nullable;

public class ItemScroll extends ItemBasic {
    @CapabilityInject(ISpellCast.class)
    public static final Capability<ISpellCast> SPELL_CAST_CAPABILITY = null;

    public ItemScroll(String name) {
        super(name);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
        ItemStack itemStack = playerIn.getHeldItem(handIn);
        if(!itemStack.hasCapability(SPELL_CAST_CAPABILITY, null)) {
            return super.onItemRightClick(worldIn, playerIn, handIn);
        }
        ISpellCast spellCast = itemStack.getCapability(SPELL_CAST_CAPABILITY, null);
        if(playerIn.isSneaking()) {
            if(worldIn.isRemote) {
                Flow.proxy.openSpellGui(spellCast.getUnderlyingSystem());
            }
        } else {
            if (!worldIn.isRemote) { // cast spell
                spellCast.startSpellCast(worldIn, playerIn.getPosition());
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

    @Override
    @Nullable
    public NBTTagCompound getNBTShareTag(ItemStack stack) {
        if(stack.hasCapability(SPELL_CAST_CAPABILITY, null)) {
            ISpellCast spellCast = stack.getCapability(SPELL_CAST_CAPABILITY, null);
            return spellCast.writeNbt();
        }
        return null;
    }

    @Override
    public void readNBTShareTag(ItemStack stack, @Nullable NBTTagCompound nbt) {
        if(stack.hasCapability(SPELL_CAST_CAPABILITY, null)) {
            ISpellCast spellCast = stack.getCapability(SPELL_CAST_CAPABILITY, null);
            spellCast.readNbt(nbt);
        }
    }
}
