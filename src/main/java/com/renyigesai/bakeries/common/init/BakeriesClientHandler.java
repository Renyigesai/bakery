package com.renyigesai.bakeries.common.init;

import com.renyigesai.bakeries.common.blocks.blander.BlenderRenderer;
import com.renyigesai.bakeries.common.blocks.bread_rack.BreadRackRender;
import com.renyigesai.bakeries.common.blocks.fermentation_box.FermentationBoxRender;
import com.renyigesai.bakeries.common.blocks.luminous_light_sign.LuminousLightSignBlockEntityRender;
import com.renyigesai.bakeries.common.blocks.menu.MenuRender;
import com.renyigesai.bakeries.common.blocks.mix_block.MixBlockRender;
import com.renyigesai.bakeries.common.blocks.moka_pot.MokaPotRender;
import com.renyigesai.bakeries.common.blocks.oven.OvenRender;
import com.renyigesai.bakeries.common.blocks.toaster.ToasterRender;
import com.renyigesai.bakeries.common.client.model.*;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;

@SuppressWarnings("removal")
@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD,value = Dist.CLIENT)
public class BakeriesClientHandler {
    @SubscribeEvent
    public static void onRenders(EntityRenderersEvent.RegisterRenderers event){
        event.registerBlockEntityRenderer(BakeriesBlocks.Entities.MIX_BLOCK_ENTITY.get(), MixBlockRender::new);
        event.registerBlockEntityRenderer(BakeriesBlocks.Entities.BLENDER_ENTITY.get(), BlenderRenderer::new);
        event.registerBlockEntityRenderer(BakeriesBlocks.Entities.OVEN_BLOCK_ENTITY.get(), OvenRender::new);
        event.registerBlockEntityRenderer(BakeriesBlocks.Entities.TOASTER_ENTITY.get(), ToasterRender::new);
        event.registerBlockEntityRenderer(BakeriesBlocks.Entities.MOKA_POT_ENTITY.get(), MokaPotRender::new);
        event.registerBlockEntityRenderer(BakeriesBlocks.Entities.MENU_ENTITY.get(), MenuRender::new);
        event.registerBlockEntityRenderer(BakeriesBlocks.Entities.BREAD_RACK_ENTITY.get(), BreadRackRender::new);
        event.registerBlockEntityRenderer(BakeriesBlocks.Entities.FERMENTATION_BOX_ENTITY.get(), FermentationBoxRender::new);
        event.registerBlockEntityRenderer(BakeriesBlocks.Entities.LUMINOUS_LIGHT_SIGN_ENTITY.get(), LuminousLightSignBlockEntityRender::new);
    }

    @SubscribeEvent
    public static void registerLayer(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(BlenderModel.BLENDER, BlenderModel::createBodyLayer);
        event.registerLayerDefinition(OvenModel.OVEN, OvenModel::createBodyLayer);
        event.registerLayerDefinition(MokaPotModel.LAYER_LOCATION, MokaPotModel::createBodyLayer);
        event.registerLayerDefinition(GlassBreadRackDoorModel.LAYER_LOCATION, GlassBreadRackDoorModel::createBodyLayer);
        event.registerLayerDefinition(FermentationBoxModel.FERMENTATION_BOX, FermentationBoxModel::createBodyLayer);
    }
}
