package com.renyigesai.bakeries.client.render.block;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.renyigesai.bakeries.block.light_box.LightBoxBlockEntity;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.font.glyphs.BakedGlyph;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import org.jetbrains.annotations.NotNull;

public class LightBoxRender implements IBBlockEntityRenderer<LightBoxBlockEntity> {
    private final Font font;
    private static final float TEXT_HEIGHT = 0.625f + 0.25f;

    public LightBoxRender(BlockEntityRendererProvider.Context pContext) {
        this.font = pContext.getFont();
    }

    @Override
    public void startRender(@NotNull LightBoxBlockEntity be, float v, @NotNull PoseStack poseStack, @NotNull MultiBufferSource multiBufferSource, int pPackedLight, int pPackedOverlay) {
        String text = be.getText();
        if (text == null) {
            return;
        }

        if (!text.isEmpty()) {
            renderText(be, text, poseStack, multiBufferSource);
        }
    }

    private void renderText(LightBoxBlockEntity entity, String text, PoseStack poseStack, MultiBufferSource pBuffer) {
        int textWidth = font.width(text);
        startRender(text, textWidth, entity.getColor(), poseStack, pBuffer);
    }

    private void startRender(String text, int textWidth, int color, PoseStack poseStack, MultiBufferSource pBuffer) {
        poseStack.pushPose();
        poseStack.translate(0, 0.625, 0.015625);
        poseStack.mulPose(Axis.YP.rotationDegrees(-90));
        poseStack.scale(0.023F, -0.035F, 0.023F);
        this.font.drawInBatch(Component.literal(text), (float) -textWidth / 2 + 1, 0, color, true, poseStack.last().pose(), pBuffer, Font.DisplayMode.NORMAL, 15728880, 15728880);
        if (pBuffer instanceof MultiBufferSource.BufferSource) {
            BakedGlyph texturedglyph = font.getFontSet(Style.DEFAULT_FONT).whiteGlyph();
            ((MultiBufferSource.BufferSource) pBuffer).endBatch(texturedglyph.renderType(Font.DisplayMode.NORMAL));
        }
        poseStack.popPose();
    }
}
