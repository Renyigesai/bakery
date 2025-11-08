package com.renyigesai.bakeries.init;

import com.renyigesai.bakeries.block.cake.CakeRollProcessingBlockEntityRender;
import com.renyigesai.bakeries.block.cake_box.CakeBoxBlockEntityRender;
import com.renyigesai.bakeries.block.luminous_light_sign.LuminousLightSignBlockEntity;
import com.renyigesai.bakeries.block.luminous_light_sign.LuminousLightSignBlockEntityRender;
import com.renyigesai.bakeries.block.menu.MenuBlockEntityRender;
import com.renyigesai.bakeries.block.mix_block.MixBlockEntityRender;
import com.renyigesai.bakeries.block.pizza.PizzaFlatbreadBlockEntityRender;
import com.renyigesai.bakeries.block.stone_kiln.StoneKilnBlockEntityRender;
import com.renyigesai.bakeries.block.wooden_tray.WoodTrayBlockEntityRender;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class BakeriesClientHandler {
    @SubscribeEvent
    public static void onClientEvent(FMLClientSetupEvent event) {
        event.enqueueWork(() -> {

        });
    }
    @SubscribeEvent
    public static void onRenders(EntityRenderersEvent.RegisterRenderers event){
        event.registerBlockEntityRenderer(BakeriesBlocks.MENU_ENTITY.get(), MenuBlockEntityRender::new);
        event.registerBlockEntityRenderer(BakeriesBlocks.WOOD_TRAY_ENTITY.get(), WoodTrayBlockEntityRender::new);
        event.registerBlockEntityRenderer(BakeriesBlocks.CAKE_ROLL_PROCESSING_ENTITY.get(), CakeRollProcessingBlockEntityRender::new);
        event.registerBlockEntityRenderer(BakeriesBlocks.CAKE_BOX_ENTITY.get(), CakeBoxBlockEntityRender::new);
        event.registerBlockEntityRenderer(BakeriesBlocks.STONE_KILN_ENTITY.get(), StoneKilnBlockEntityRender::new);
        event.registerBlockEntityRenderer(BakeriesBlocks.PIZZA_FLATBREAD_ENTITY.get(), PizzaFlatbreadBlockEntityRender::new);
        event.registerBlockEntityRenderer(BakeriesBlocks.MIX_BLOCK_ENTITY.get(), MixBlockEntityRender::new);
        event.registerBlockEntityRenderer(BakeriesBlocks.LUMINOUS_LIGHT_SIGN_ENTITY.get(), LuminousLightSignBlockEntityRender::new);
    }
}
