package com.renyigesai.bakeries.init;

import com.renyigesai.bakeries.block.toaster.ToasterBlockEntityRender;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class BakeriesClientHandler {
    public static void onClientEvent(FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            BlockEntityRenderers.register(BakeriesBlocks.TOASTER_ENTITY.get(), ToasterBlockEntityRender::new);
        });
    }
}
