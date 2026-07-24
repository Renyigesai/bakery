package com.renyigesai.bakeries.init;

import com.google.common.collect.ImmutableList;
import com.renyigesai.bakeries.BakeriesMod;
import com.renyigesai.bakeries.client.render.block.BlenderRenderer;
import com.renyigesai.bakeries.client.render.block.BreadRackRender;
import com.renyigesai.bakeries.block.cake.CakeRollProcessingBlockEntityRender;
import com.renyigesai.bakeries.client.render.block.CustomCakeRenderer;
import com.renyigesai.bakeries.client.render.block.CakeBoxBlockEntityRender;
import com.renyigesai.bakeries.client.render.block.FermentationBoxRender;
import com.renyigesai.bakeries.client.render.block.LuminousLightSignBlockEntityRender;
import com.renyigesai.bakeries.client.render.block.MagneticPlateRenderer;
import com.renyigesai.bakeries.client.render.block.MenuBlockEntityRender;
import com.renyigesai.bakeries.client.render.block.MixBlockEntityRender;
import com.renyigesai.bakeries.client.render.block.MokaPotRender;
import com.renyigesai.bakeries.client.render.block.OvenRender;
import com.renyigesai.bakeries.client.render.block.PizzaFlatbreadBlockEntityRender;
import com.renyigesai.bakeries.client.render.block.StoneKilnBlockEntityRender;
import com.renyigesai.bakeries.client.render.block.ToasterBlockEntityRender;
import com.renyigesai.bakeries.client.render.block.WoodTrayBlockEntityRender;
import com.renyigesai.bakeries.client.model.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.BlockModelShaper;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.ModelEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Map;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class BakeriesClientHandler {

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
        event.registerBlockEntityRenderer(BakeriesBlocks.MAGNETIC_PLATE_ENTITY.get(), MagneticPlateRenderer::new);
        event.registerBlockEntityRenderer(BakeriesBlocks.CUSTOM_CAKE_ENTITY.get(), CustomCakeRenderer::new);
    }

    @SubscribeEvent
    public static void registerLayer(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(BlenderModel.BLENDER, BlenderModel::createBodyLayer);
        event.registerLayerDefinition(OvenModel.OVEN, OvenModel::createBodyLayer);
        event.registerLayerDefinition(MokaPotModel.MOKA_POT, MokaPotModel::createBodyLayer);
        event.registerLayerDefinition(FermentationBoxModel.FERMENTATION_BOX, FermentationBoxModel::createBodyLayer);
        event.registerLayerDefinition(GlassBreadRackDoorModel.LAYER_LOCATION, GlassBreadRackDoorModel::createBodyLayer);
        event.registerLayerDefinition(MagneticPlateModel.MAGNETIC_PLATE, MagneticPlateModel::createBodyLayer);
    }

    @SubscribeEvent
    public static void onRegisterModifyModel(ModelEvent.ModifyBakingResult event) {

        registerItemModel(new ResourceLocation(BakeriesMod.MODID, "eternal_baguette"),event);
        renderBlockModel(BakeriesBlocks.LUMINOUS_LIGHT_SIGN.get(), event);

        Map<ResourceLocation, BakedModel> modelRegistry = event.getModels();
        ModelResourceLocation baseLocation = new ModelResourceLocation(new ResourceLocation(BakeriesMod.MODID,"custom_cake"), "inventory");
        BakedModel model = modelRegistry.get(baseLocation);
        if (model != null) {
            modelRegistry.put(baseLocation, new CustomCakeModel(model));
        }

    }

    @SubscribeEvent
    public static void onAdditionalModel(ModelEvent.RegisterAdditional event) {
        Minecraft minecraft = Minecraft.getInstance();
        ResourceManager manager = minecraft.getResourceManager();

        Map<ResourceLocation, Resource> resources = manager.listResources(
                "models/cake_part",
                rl -> rl.getPath().endsWith(".json")
        );

        for (Map.Entry<ResourceLocation, Resource> entry : resources.entrySet()) {
            ResourceLocation fullPath = entry.getKey();
            if (!fullPath.getNamespace().equals(BakeriesMod.MODID)){
                continue;
            }
            String path = fullPath.getPath();
            String prefix = "models/cake_part/";
            if (!path.startsWith(prefix) || !path.endsWith(".json")){
                continue;
            }

            String fileName = path.substring(prefix.length(), path.length() - 5);
            ResourceLocation modelId = new ResourceLocation(BakeriesMod.MODID, "cake_part/" + fileName);
            event.register(modelId);
        }
    }

    private static void registerItemModel(ResourceLocation itemId, ModelEvent.ModifyBakingResult event){
        ModelResourceLocation itemModelKey = new ModelResourceLocation(itemId, "inventory");
        BakedModel originalModel = event.getModels().get(itemModelKey);
        if (originalModel != null) {
            FullbrightBakedModel fullbrightModel = new FullbrightBakedModel(originalModel);
            event.getModels().put(itemModelKey, fullbrightModel);
        }

    }

    private static void renderBlockModel(Block block,ModelEvent.ModifyBakingResult event){
        ImmutableList<BlockState> possibleStates = block.getStateDefinition().getPossibleStates();
        for (BlockState state : possibleStates) {
            ModelResourceLocation modelResourceLocation = BlockModelShaper.stateToModelLocation(state);
            BakedModel bakedModel = event.getModels().get(modelResourceLocation);
            FullbrightBakedModel fullbrightBakedModel = new FullbrightBakedModel(bakedModel);
            event.getModels().put(modelResourceLocation,fullbrightBakedModel);
        }
    }
}
