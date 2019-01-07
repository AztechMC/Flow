package com.aztech.flow.capability;

import com.aztech.flow.Flow;
import com.aztech.flow.blocks.altar.TileEntityAltar;
import com.aztech.flow.blocks.magicfurnace.TileEntityMagicFurnace;
import com.aztech.flow.capability.spellholder.SpellHolderProvider;
import com.aztech.flow.items.ModItems;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber(modid = Flow.MODID)
public class CapabilityHandler {
    public static final ResourceLocation MANA_CONSUMER_CAPABILITY = new ResourceLocation(Flow.MODID, "manaconsumer");
    public static final ResourceLocation MANA_PROVIDER_CAPABILITY = new ResourceLocation(Flow.MODID, "manaprovider");
    public static final ResourceLocation MANA_WORLD_MANAGER_CAPABILITY = new ResourceLocation(Flow.MODID, "manaworldmanager");
    public static final ResourceLocation SPELL_HOLDER_CAPABILITY = new ResourceLocation(Flow.MODID, "spellholder");

    @SubscribeEvent
    public static void attachCapabilityItemStack(AttachCapabilitiesEvent<ItemStack> event) {
        if (event.getObject().getItem() == ModItems.SCROLL) {
            event.addCapability(SPELL_HOLDER_CAPABILITY, new SpellHolderProvider());
        }
    }

    @SubscribeEvent
    public static void attachCapabilityWorld(AttachCapabilitiesEvent<World> event) {
        event.addCapability(MANA_WORLD_MANAGER_CAPABILITY, new ManaWorldManagerProvider());
    }

    @SubscribeEvent
    public static void attachCapabilityTileEntity(AttachCapabilitiesEvent<TileEntity> event) {
        if (event.getObject() instanceof TileEntityAltar) {
            event.addCapability(MANA_PROVIDER_CAPABILITY, new ManaProducerProvider());
        } else if (event.getObject() instanceof TileEntityMagicFurnace) {
            event.addCapability(MANA_CONSUMER_CAPABILITY, new ManaConsumerProvider());
        }
    }
}
