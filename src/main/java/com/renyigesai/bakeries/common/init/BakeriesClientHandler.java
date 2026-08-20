package com.renyigesai.bakeries.common.init;

import com.google.common.collect.ImmutableList;
import com.renyigesai.bakeries.BakeriesMod;
import com.renyigesai.bakeries.common.blocks.blander.BlenderRenderer;
import com.renyigesai.bakeries.common.blocks.bread_rack.BreadRackRender;
import com.renyigesai.bakeries.common.blocks.custom_cake.CustomCakeRenderer;
import com.renyigesai.bakeries.common.blocks.fermentation_box.FermentationBoxRender;
import com.renyigesai.bakeries.common.blocks.luminous_light_sign.LuminousLightSignBlockEntityRender;
import com.renyigesai.bakeries.common.blocks.menu.MenuRender;
import com.renyigesai.bakeries.common.blocks.mix_block.MixBlockRender;
import com.renyigesai.bakeries.common.blocks.moka_pot.MokaPotRender;
import com.renyigesai.bakeries.common.blocks.mould_cake.MouldCakeRenderer;
import com.renyigesai.bakeries.common.blocks.oven.OvenRender;
import com.renyigesai.bakeries.common.blocks.toaster.ToasterRender;
import com.renyigesai.bakeries.common.client.model.*;
import com.renyigesai.bakeries.common.items.MouldPasteItem;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.BlockModelShaper;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.ModelEvent;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;

import java.util.Map;

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
        event.registerBlockEntityRenderer(BakeriesBlocks.Entities.MOULD_CAKE_ENTITY.get(), MouldCakeRenderer::new);
        event.registerBlockEntityRenderer(BakeriesBlocks.Entities.CUSTOM_CAKE_ENTITY.get(), CustomCakeRenderer::new);
    }

    @SubscribeEvent
    public static void registerLayer(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(BlenderModel.BLENDER, BlenderModel::createBodyLayer);
        event.registerLayerDefinition(OvenModel.OVEN, OvenModel::createBodyLayer);
        event.registerLayerDefinition(MokaPotModel.LAYER_LOCATION, MokaPotModel::createBodyLayer);
        event.registerLayerDefinition(GlassBreadRackDoorModel.LAYER_LOCATION, GlassBreadRackDoorModel::createBodyLayer);
        event.registerLayerDefinition(FermentationBoxModel.FERMENTATION_BOX, FermentationBoxModel::createBodyLayer);
    }

    @SubscribeEvent
    public static void onRegisterModifyModel(ModelEvent.ModifyBakingResult event) {

//        registerItemModel(ResourceLocation.fromNamespaceAndPath(BakeriesMod.MODID, "eternal_baguette"),event);
//        renderBlockModel(BakeriesBlocks.LUMINOUS_LIGHT_SIGN.get(), event);

        Map<ModelResourceLocation, BakedModel> modelRegistry = event.getModels();
        ModelResourceLocation baseLocation0 = new ModelResourceLocation(ResourceLocation.fromNamespaceAndPath(BakeriesMod.MODID,"custom_cake"), "inventory");
        BakedModel model0 = modelRegistry.get(baseLocation0);
        if (model0 != null) {
            modelRegistry.put(baseLocation0, new CustomCakeModel(model0));
        }

        BakeriesItems.MOULD_CAKES.forEach(mcb -> {
            ModelResourceLocation baseLocation1 = new ModelResourceLocation(ResourceLocation.fromNamespaceAndPath(BakeriesMod.MODID,mcb), "inventory");
            BakedModel model1 = modelRegistry.get(baseLocation1);
            if (model1 != null) {
                modelRegistry.put(baseLocation1, new MouldCakeModel(model1));
            }
        });


    }

    @SubscribeEvent
    public static void onItemColors(RegisterColorHandlersEvent.Item event) {
        event.getItemColors().register((itemStack, tintIndex) -> {
            Item item = itemStack.getItem();
            if (item instanceof MouldPasteItem mp){
                int color = mp.getColor();
                if (tintIndex == 1){
                    return color;
                }
            }
            return -1;
        },BakeriesItems.MOULD_CAKE_PASTE.get(),
                BakeriesItems.MOULD_BASQUE_CAKE_PASTE.get(),
                BakeriesItems.MOULD_RED_VELVET_CAKE_PASTE.get(),
                BakeriesItems.MOULD_MATCHA_CAKE_PASTE.get(),
                BakeriesItems.MOULD_CARROT_CAKE_PASTE.get()
        );
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
            ResourceLocation modelId = ResourceLocation.fromNamespaceAndPath(BakeriesMod.MODID, "cake_part/" + fileName);
            event.register(ModelResourceLocation.standalone(modelId));
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

    private static void renderBlockModel(Block block, ModelEvent.ModifyBakingResult event){
        ImmutableList<BlockState> possibleStates = block.getStateDefinition().getPossibleStates();
        for (BlockState state : possibleStates) {
            ModelResourceLocation modelResourceLocation = BlockModelShaper.stateToModelLocation(state);
            BakedModel bakedModel = event.getModels().get(modelResourceLocation);
            FullbrightBakedModel fullbrightBakedModel = new FullbrightBakedModel(bakedModel);
            event.getModels().put(modelResourceLocation,fullbrightBakedModel);
        }
    }
}
