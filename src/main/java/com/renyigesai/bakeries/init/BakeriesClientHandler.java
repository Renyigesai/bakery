package com.renyigesai.bakeries.init;

import com.renyigesai.bakeries.block.blender.BlenderRenderer;
import com.renyigesai.bakeries.block.bread_rack.BreadRackRender;
import com.renyigesai.bakeries.block.cake.CakeRollProcessingBlockEntityRender;
import com.renyigesai.bakeries.block.cake_box.CakeBoxBlockEntityRender;
import com.renyigesai.bakeries.block.fermentation_box.FermentationBoxRender;
import com.renyigesai.bakeries.block.luminous_light_sign.LuminousLightSignBlockEntityRender;
import com.renyigesai.bakeries.block.menu.MenuBlockEntityRender;
import com.renyigesai.bakeries.block.mix_block.MixBlockEntityRender;
import com.renyigesai.bakeries.block.moka_pot.MokaPotRender;
import com.renyigesai.bakeries.block.oven.OvenRender;
import com.renyigesai.bakeries.block.pizza.PizzaFlatbreadBlockEntityRender;
import com.renyigesai.bakeries.block.stone_kiln.StoneKilnBlockEntityRender;
import com.renyigesai.bakeries.block.toaster.ToasterBlockEntityRender;
import com.renyigesai.bakeries.block.wooden_tray.WoodTrayBlockEntityRender;
import com.renyigesai.bakeries.client.model.*;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class BakeriesClientHandler {

    @SubscribeEvent
    public static void onClientEvent(FMLClientSetupEvent event) {

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
        event.registerBlockEntityRenderer(BakeriesBlocks.TOASTER_ENTITY.get(), ToasterBlockEntityRender::new);
        event.registerBlockEntityRenderer(BakeriesBlocks.BLENDER_ENTITY.get(), BlenderRenderer::new);
        event.registerBlockEntityRenderer(BakeriesBlocks.OVEN_BLOCK_ENTITY.get(), OvenRender::new);
        event.registerBlockEntityRenderer(BakeriesBlocks.MOKA_POT_ENTITY.get(), MokaPotRender::new);
        event.registerBlockEntityRenderer(BakeriesBlocks.FERMENTATION_BOX_ENTITY.get(), FermentationBoxRender::new);
        event.registerBlockEntityRenderer(BakeriesBlocks.BREAD_RACK_ENTITY.get(), BreadRackRender::new);
    }

    @SubscribeEvent
    public static void registerLayer(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(BlenderModel.BLENDER, BlenderModel::createBodyLayer);
        event.registerLayerDefinition(OvenModel.OVEN, OvenModel::createBodyLayer);
        event.registerLayerDefinition(MokaPotModel.MOKA_POT, MokaPotModel::createBodyLayer);
        event.registerLayerDefinition(FermentationBoxModel.FERMENTATION_BOX, FermentationBoxModel::createBodyLayer);
        event.registerLayerDefinition(GlassBreadRackDoorModel.LAYER_LOCATION, GlassBreadRackDoorModel::createBodyLayer);
    }
}
