package com.aztech.flow.capability;

import com.aztech.flow.Flow;
import com.aztech.flow.capability.spellcast.SpellCastProvider;
import com.aztech.flow.items.ModItems;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber(modid = Flow.MODID)
public class CapabilityHandler {
    public static final ResourceLocation SPELL_CAST_CAPABILITY = new ResourceLocation(Flow.MODID, "spellcast");

    @SubscribeEvent
    public static void attachCapability(AttachCapabilitiesEvent<ItemStack> event) {
        if(event.getObject().getItem() == ModItems.SCROLL) {
            event.addCapability(SPELL_CAST_CAPABILITY, new SpellCastProvider());
        }
    }
}
