package com.renyigesai.bakeries.common.event;

import com.google.common.collect.ImmutableList;
import com.renyigesai.bakeries.common.client.LookBlockEntityRegistries;
import com.renyigesai.bakeries.common.client.model.FullbrightBakedModel;
import com.renyigesai.bakeries.common.init.BakeriesBlocks;
import com.renyigesai.bakeries.common.overlay.ILookOverlay;
import com.renyigesai.bakeries.integration.ponder.BakeriesPonderIntegration;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.block.BlockModelShaper;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.ModList;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.ClientPlayerNetworkEvent;
import net.neoforged.neoforge.client.event.ModelEvent;
import net.neoforged.neoforge.client.event.RenderGuiEvent;

@EventBusSubscriber({Dist.CLIENT})
public class BakeriesClientEvents {
    @SubscribeEvent
    public static void onPlayerLogout(ClientPlayerNetworkEvent.LoggingOut event) {
        LocalPlayer player = event.getPlayer();
        if (player != null) {
            LookBlockEntityRegistries.getBlocks().remove(player.getUUID());

        }
    }
    @SubscribeEvent(priority = EventPriority.NORMAL)
    public static void addOverlay(RenderGuiEvent.Pre event){
        Minecraft mc = Minecraft.getInstance();
        Player localPlayer = mc.player;
        if (localPlayer == null){
            return;
        }
        BlockEntity blockEntity = LookBlockEntityRegistries.getBlocks().get(localPlayer.getUUID());
        if (blockEntity != null){
            ILookOverlay iLookOverlay = LookBlockEntityRegistries.getRegister().get(blockEntity.getClass());
            if (iLookOverlay != null) {
                if (iLookOverlay.isOverlay(blockEntity,localPlayer,mc)) {
                    iLookOverlay.create(event, blockEntity, localPlayer, mc);
                }
            }
        }
    }

    @SubscribeEvent
    public static void onModel(ModelEvent.ModifyBakingResult event){
        ImmutableList<BlockState> possibleStates = BakeriesBlocks.LUMINOUS_LIGHT_SIGN.get().getStateDefinition().getPossibleStates();
        for (BlockState state : possibleStates) {
            ModelResourceLocation modelResourceLocation = BlockModelShaper.stateToModelLocation(state);
            BakedModel bakedModel = event.getModels().get(modelResourceLocation);
            FullbrightBakedModel fullbrightBakedModel = new FullbrightBakedModel(bakedModel);
            event.getModels().put(modelResourceLocation,fullbrightBakedModel);
        }
    }

    // PonderPlugin
    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        if (ModList.get().isLoaded("create") || ModList.get().isLoaded("ponder")) {
            event.enqueueWork(BakeriesPonderIntegration::register);
        }
    }
}
