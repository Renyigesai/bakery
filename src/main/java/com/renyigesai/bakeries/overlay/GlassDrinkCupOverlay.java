package com.renyigesai.bakeries.overlay;

import com.mojang.blaze3d.systems.RenderSystem;
import com.renyigesai.bakeries.block.glass_drink_cup.GlassDrinkCupBlockEntity;
import com.renyigesai.bakeries.client.LookBlockEntityMap;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ClientPlayerNetworkEvent;
import net.minecraftforge.client.event.RenderGuiEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Mod.EventBusSubscriber({Dist.CLIENT})
public class GlassDrinkCupOverlay {
    private static final Map<UUID, BlockEntity> playerViewMap = new HashMap<>();
    @SubscribeEvent(priority = EventPriority.NORMAL)
    public static void add(RenderGuiEvent.Pre event){
        int w = event.getWindow().getGuiScaledWidth() / 2 - 71;
        int h = event.getWindow().getGuiScaledHeight() / 2 + 50;
        GuiGraphics guiGraphics = event.getGuiGraphics();
        Minecraft mc = Minecraft.getInstance();
        Player localPlayer = mc.player;
        if (localPlayer == null) {
            return;
        }
        Map<UUID, BlockEntity> blocks = LookBlockEntityMap.getBlocks();
        BlockEntity blockEntity = blocks.get(localPlayer.getUUID());
        if (blockEntity instanceof GlassDrinkCupBlockEntity glassDrinkCupBlockEntity) {
            guiGraphics.blit(new ResourceLocation("bakeries:textures/gui/glass_drink_cup_overlay.png"), w, h, 0, 0, 142, 22, 142, 22);
            int x = w + 3;
            int y = h + 3;
            for (int i = 0; i < 4; ++i) {
                guiGraphics.renderItem(glassDrinkCupBlockEntity.getInventory().getStackInSlot(i),x + (i * 24),y, -1);
            }
            guiGraphics.renderItem(glassDrinkCupBlockEntity.getInventory().getStackInSlot(4),x + (5 * 24),y, -1);
        }
        RenderSystem.depthMask(true);
        RenderSystem.defaultBlendFunc();
        RenderSystem.enableDepthTest();
        RenderSystem.disableBlend();
        RenderSystem.setShaderColor(1, 1, 1, 1);
    }

    public static void setVisible(Player player, BlockEntity entity) {
        if (player.level().isClientSide) { // 确保只在客户端调用
            playerViewMap.put(player.getUUID(), entity);
        }
    }

//    @SubscribeEvent
//    public static void onPlayerLogout(ClientPlayerNetworkEvent.LoggingOut event) {
//        LocalPlayer player = event.getPlayer();
//        if (player != null) {
//            playerViewMap.remove(player.getUUID());
//        }
//    }
}
