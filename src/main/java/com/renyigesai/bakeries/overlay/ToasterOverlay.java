package com.renyigesai.bakeries.overlay;

import com.mojang.blaze3d.systems.RenderSystem;
import com.renyigesai.bakeries.block.stone_kiln.StoneKilnBlockEntity;
import com.renyigesai.bakeries.block.toaster.ToasterBlock;
import com.renyigesai.bakeries.block.toaster.ToasterBlockEntity;
import com.renyigesai.bakeries.client.LookBlockEntityMap;
import com.renyigesai.bakeries.util.TextUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderGuiEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Map;
import java.util.UUID;

@Mod.EventBusSubscriber({Dist.CLIENT})
public class ToasterOverlay {

    @SubscribeEvent(priority = EventPriority.NORMAL)
    public static void add(RenderGuiEvent.Pre event){
        int w = event.getWindow().getGuiScaledWidth() / 2;
        int h = event.getWindow().getGuiScaledHeight() / 2;
        GuiGraphics guiGraphics = event.getGuiGraphics();
        Minecraft mc = Minecraft.getInstance();
        Player localPlayer = mc.player;
        if (localPlayer == null) {
            return;
        }
        Map<UUID, BlockEntity> blocks = LookBlockEntityMap.getBlocks();
        BlockEntity blockEntity = blocks.get(localPlayer.getUUID());
        if (blockEntity instanceof ToasterBlockEntity toaster) {
            String text = "";
            ToasterBlock.State state = toaster.getBlockState().getValue(ToasterBlock.STATE);
            if (!toaster.getItems().getStackInSlot(0).isEmpty() && state == ToasterBlock.State.IDLE){
                text = Component.translatable("tip.bakeries.toaster_1").getString();
            }else if (state == ToasterBlock.State.FINISH){
                text = Component.translatable("tip.bakeries.toaster_2").getString();
            }

            if (!text.isEmpty()){
                int length = TextUtils.getLength(text);
                RenderSystem.enableBlend();
                RenderSystem.defaultBlendFunc();
                RenderSystem.setShaderColor(1, 1, 1, 0.75f);
                guiGraphics.renderTooltip(mc.font,Component.literal(text),w - length / 2 - 8,h + 64);
                RenderSystem.setShaderColor(1, 1, 1, 1f);
                RenderSystem.disableBlend();
            }
        }

    }
}
