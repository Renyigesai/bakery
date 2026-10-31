package com.renyigesai.bakeries.client.render.block;

import com.mojang.blaze3d.vertex.PoseStack;
import com.renyigesai.bakeries.block.luminous_light_sign.LuminousLightSignBlockEntity;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.font.glyphs.BakedGlyph;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.Date;

public class LuminousLightSignBlockEntityRender implements IBBlockEntityRenderer<LuminousLightSignBlockEntity> {
    private final Font font;
    private static boolean colonVisible = true;

    private static String lazyTime = "00 00";
    private static int count;
    private static final SimpleDateFormat HH = new SimpleDateFormat("HH");
    private static final SimpleDateFormat MM = new SimpleDateFormat("mm");

    private static final float TEXT_HEIGHT = 0.625f + 0.25f;
    private static final float LEFT_MARGIN = -20.5f;

    public LuminousLightSignBlockEntityRender(BlockEntityRendererProvider.Context pContext) {
        this.font = pContext.getFont();
    }

    @Override
    public void startRender(@NotNull LuminousLightSignBlockEntity entity, float v,
                            @NotNull PoseStack poseStack, @NotNull MultiBufferSource pBuffer,
                            int pPackedLight, int pPackedOverlay) {
        String text = entity.getText();
        if (text == null) {
            return;
        }

        if (!text.isEmpty()) {
            renderText(entity, text, poseStack, pBuffer);
            return;
        }

        renderTime(entity, poseStack, pBuffer);
        renderSecond(entity, poseStack, pBuffer);
    }

    private void renderSecond(LuminousLightSignBlockEntity entity, PoseStack poseStack, MultiBufferSource pBuffer) {
        if (!colonVisible) {
            return;
        }
        int textWidth = font.width(":");
        applyTextTransform(poseStack);
        startRender(":", textWidth, entity.getColor(), poseStack, pBuffer,true);
    }

    private void renderTime(LuminousLightSignBlockEntity entity, PoseStack poseStack, MultiBufferSource pBuffer) {
        int textWidth = font.width(lazyTime);
        applyTextTransform(poseStack);
        startRender(lazyTime, textWidth, entity.getColor(), poseStack, pBuffer,true);
    }

    private void renderText(LuminousLightSignBlockEntity entity, String text, PoseStack poseStack, MultiBufferSource pBuffer) {
        int textWidth = font.width(text);
        applyTextTransform(poseStack);
        startRender(text, textWidth, entity.getColor(), poseStack, pBuffer,entity.isCenter());
    }


    /** 统一的文字变换：抬到方块中心上方 + 缩放 */
    private void applyTextTransform(PoseStack poseStack) {
        poseStack.pushPose();
        poseStack.translate(0f, TEXT_HEIGHT, 0f);
        poseStack.scale(0.023F, -0.025F, 0.023F);
    }

    private void startRender(String text, int textWidth, int color,
                             PoseStack poseStack, MultiBufferSource pBuffer, boolean center) {
        float x = center ? (float) -textWidth / 2 + 1 : LEFT_MARGIN;

        this.font.drawInBatch(Component.literal(text),
                x, 1, color, false,
                poseStack.last().pose(), pBuffer, Font.DisplayMode.NORMAL, 0, 15728880);
        if (pBuffer instanceof MultiBufferSource.BufferSource) {
            BakedGlyph texturedGlyph = font.getFontSet(Style.DEFAULT_FONT).whiteGlyph();
            ((MultiBufferSource.BufferSource) pBuffer).endBatch(texturedGlyph.renderType(Font.DisplayMode.NORMAL));
        }
        poseStack.popPose();
    }

    @Mod.EventBusSubscriber({Dist.CLIENT})
    static class ClientTick {
        @SubscribeEvent
        public static void onClientTick(TickEvent.ClientTickEvent event) {
            if (event.phase == TickEvent.Phase.END) {
                count ++;

                if (count == 10) {
                    colonVisible = !colonVisible;
                }
                if (count >= 20) {
                    Date date = new Date();
                    lazyTime = HH.format(date) + " " + MM.format(date);
                    colonVisible = !colonVisible;
                    count = 0;
                }
            }
        }
    }
}