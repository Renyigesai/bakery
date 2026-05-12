package com.renyigesai.bakeries.common.event;

import com.renyigesai.bakeries.common.client.LookBlockEntityRegistries;
import com.renyigesai.bakeries.common.overlay.ILookOverlay;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientPlayerNetworkEvent;
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
}
