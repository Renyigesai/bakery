package com.renyigesai.bakeries.common.client.render.block;

import com.mojang.blaze3d.vertex.PoseStack;
import com.renyigesai.bakeries.common.blocks.luminous_light_sign.LuminousLightSignBlockEntity;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.font.glyphs.BakedGlyph;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientTickEvent;
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

    // 文字在方块空间中的高度（底部中心上方 0.875）
    private static final float TEXT_HEIGHT = 0.625f + 0.25f;

    public LuminousLightSignBlockEntityRender(BlockEntityRendererProvider.Context pContext) {
        this.font = pContext.getFont();
    }

    @Override
    public void startRender(@NotNull LuminousLightSignBlockEntity entity, float v, @NotNull PoseStack poseStack, @NotNull MultiBufferSource pBuffer, int pPackedLight, int pPackedOverlay) {
        String text = entity.getText();
        if (text == null) {
            return;
        }

        // 文字非空 → 渲染文字
        if (!text.isEmpty()) {
            renderText(entity, text, poseStack, pBuffer);
            return;
        }

        // 文字为空 → 渲染时间 + 冒号
        renderTime(entity, poseStack, pBuffer);
        renderSecond(entity, poseStack, pBuffer);
    }

    private void renderSecond(LuminousLightSignBlockEntity entity, PoseStack poseStack, MultiBufferSource pBuffer) {
        if (!colonVisible) {
            return;
        }
        int textWidth = font.width(":");
        applyTextTransform(poseStack);
        startRender(":", textWidth, entity.getColor(), poseStack, pBuffer);
    }

    private void renderTime(LuminousLightSignBlockEntity entity, PoseStack poseStack, MultiBufferSource pBuffer) {
        int textWidth = font.width(lazyTime);
        applyTextTransform(poseStack);
        startRender(lazyTime, textWidth, entity.getColor(), poseStack, pBuffer);
    }

    private void renderText(LuminousLightSignBlockEntity entity, String text, PoseStack poseStack, MultiBufferSource pBuffer) {
        int textWidth = font.width(text);
        applyTextTransform(poseStack);
        startRender(text, textWidth, entity.getColor(), poseStack, pBuffer);
    }

    /** 统一的文字变换：抬到方块中心上方 + 缩放 */
    private void applyTextTransform(PoseStack poseStack) {
        poseStack.pushPose();
        // 基础变换后原点在方块底部中心，只需向上抬
        poseStack.translate(0f, TEXT_HEIGHT, 0f);
        poseStack.scale(0.023F, -0.025F, 0.023F);
    }

    private void startRender(String text, int textWidth, int color, PoseStack poseStack, MultiBufferSource pBuffer) {
        this.font.drawInBatch(
                Component.nullToEmpty(text),
                (float) -textWidth / 2 + 1, 1, color, false,
                poseStack.last().pose(), pBuffer, Font.DisplayMode.NORMAL, 0, 15728880);
        if (pBuffer instanceof MultiBufferSource.BufferSource) {
            BakedGlyph texturedglyph = font.getFontSet(Style.DEFAULT_FONT).whiteGlyph();
            ((MultiBufferSource.BufferSource) pBuffer).endBatch(texturedglyph.renderType(Font.DisplayMode.NORMAL));
        }
        poseStack.popPose();
    }


    @EventBusSubscriber(value = Dist.CLIENT)
    static class ClientTick{
        @SubscribeEvent
        public static void onClientTick(ClientTickEvent.Pre event) {
            count++;
            if (count == 10) {
                colonVisible = !colonVisible;

            }
            if (count >= 20){
                Date date = new Date();
                lazyTime = HH.format(date) + " " + MM.format(date);
                colonVisible = !colonVisible;
                count = 0;
            }
        }
    }
}
