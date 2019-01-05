package com.aztech.flow.capability;

import com.aztech.flow.Flow;
import com.aztech.flow.blocks.altar.TileEntityAltar;
import com.aztech.flow.blocks.magicfurnace.TileEntityMagicFurnace;
import com.aztech.flow.capability.spellcast.SpellCastProvider;
import com.aztech.flow.items.ModItems;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber(modid = Flow.MODID)
public class CapabilityHandler {
    public static final ResourceLocation MANA_CONSUMER_CAPABILITY = new ResourceLocation(Flow.MODID, "manaconsumer");
    public static final ResourceLocation MANA_PROVIDER_CAPABILITY = new ResourceLocation(Flow.MODID, "manaprovider");
    public static final ResourceLocation MANA_CHUNK_MANAGER_CAPABILITY = new ResourceLocation(Flow.MODID, "manachunkmanager");
    public static final ResourceLocation SPELL_CAST_CAPABILITY = new ResourceLocation(Flow.MODID, "spellcast");

    @SubscribeEvent
    public static void attachCapabilityItemStack(AttachCapabilitiesEvent<ItemStack> event) {
        if(event.getObject().getItem() == ModItems.SCROLL) {
            event.addCapability(SPELL_CAST_CAPABILITY, new SpellCastProvider());
        }
    }

    @SubscribeEvent
    public static void attachCapabilityChunk(AttachCapabilitiesEvent<Chunk> event) {
        event.addCapability(MANA_CHUNK_MANAGER_CAPABILITY, new ManaChunkManagerProvider());
    }

    @SubscribeEvent
    public static void attachCapabilityTileEntity(AttachCapabilitiesEvent<TileEntity> event) {
        if(event.getObject() instanceof TileEntityAltar) {
            event.addCapability(MANA_PROVIDER_CAPABILITY, new ManaProducerProvider());
        } else if(event.getObject() instanceof TileEntityMagicFurnace) {
            event.addCapability(MANA_CONSUMER_CAPABILITY, new ManaConsumerProvider());
        }
    }
}
