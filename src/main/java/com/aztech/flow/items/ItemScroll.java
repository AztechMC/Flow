package com.aztech.flow.items;

import com.aztech.flow.Flow;
import com.aztech.flow.capability.spellholder.ISpellHolder;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class ItemScroll extends ItemBasic {
    @CapabilityInject(ISpellHolder.class)
    public static final Capability<ISpellHolder> SPELL_HOLDER_CAPABILITY = null;

    public ItemScroll(String name) {
        super(name);
    }

    @Override
    @Nonnull
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, @Nonnull EnumHand handIn) {
        ItemStack itemStack = playerIn.getHeldItem(handIn);
        if (!itemStack.hasCapability(SPELL_HOLDER_CAPABILITY, null)) {
            return super.onItemRightClick(worldIn, playerIn, handIn);
        }
        ISpellHolder spellHolder = itemStack.getCapability(SPELL_HOLDER_CAPABILITY, null);
        if (playerIn.isSneaking()) {
            if (worldIn.isRemote) {
                Flow.proxy.openSpellGui(spellHolder.getSpell());
            }
        } else {
            if (!worldIn.isRemote) { // cast spell
                spellHolder.cast(worldIn, playerIn.getPosition());
            }
        }
        return super.onItemRightClick(worldIn, playerIn, handIn);
    }

    @Override
    public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
        if (!worldIn.isRemote) {
            if (stack.hasCapability(SPELL_HOLDER_CAPABILITY, null)) {
                stack.getCapability(SPELL_HOLDER_CAPABILITY, null).tick();
            }
        }
    }

    @Override
    @Nullable
    public NBTTagCompound getNBTShareTag(ItemStack stack) {
        if (stack.hasCapability(SPELL_HOLDER_CAPABILITY, null)) {
            ISpellHolder spellHolder = stack.getCapability(SPELL_HOLDER_CAPABILITY, null);
            return spellHolder.serializeNBT();
        }
        return null;
    }

    @Override
    public void readNBTShareTag(ItemStack stack, @Nullable NBTTagCompound nbt) {
        if (stack.hasCapability(SPELL_HOLDER_CAPABILITY, null)) {
            ISpellHolder spellHolder = stack.getCapability(SPELL_HOLDER_CAPABILITY, null);
            spellHolder.deserializeNBT(nbt);
        }
    }
}
