package com.renyigesai.bakeries.event;

import com.renyigesai.bakeries.client.LookBlockEntityRegistries;
import com.renyigesai.bakeries.overlay.ILookOverlay;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ClientPlayerNetworkEvent;
import net.minecraftforge.client.event.RenderGuiEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber({Dist.CLIENT})
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
//    @SubscribeEvent
//    public static void onComputeCameraAngles(ViewportEvent.ComputeCameraAngles event) {
//    }
}
