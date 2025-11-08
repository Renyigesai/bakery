package com.renyigesai.bakeries.block.luminous_light_sign;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.font.glyphs.BakedGlyph;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.level.block.state.BlockState;

public class LuminousLightSignBlockEntityRender implements BlockEntityRenderer<LuminousLightSignBlockEntity> {
    private final Font font;

    public LuminousLightSignBlockEntityRender(BlockEntityRendererProvider.Context pContext) {
        this.font = pContext.getFont();
    }

    @Override
    public void render(LuminousLightSignBlockEntity pBlockEntity, float pPartialTick, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight, int pPackedOverlay) {
        pPoseStack.pushPose();
        renderText(pBlockEntity,pPoseStack,pBuffer,pPackedLight);
        pPoseStack.popPose();
    }

    private void renderText(LuminousLightSignBlockEntity entity,PoseStack poseStack,MultiBufferSource pBuffer,int pPackedLight){
        poseStack.translate(0.5, 0.625 + 0.25, 0.5);
        poseStack.scale(0.023F, -0.025F, 0.023F);
        BlockState state = entity.getBlockState();
        Direction direction = state.getValue(LuminousLightSignBlock.FACING);
        float yRot = 0;
        if (direction == Direction.NORTH){
            yRot = 180;
        }
        if (direction == Direction.SOUTH){
            yRot = -180;
        }
        poseStack.mulPose(Axis.YP.rotationDegrees(direction.toYRot() + yRot));
        String text = entity.getText();
        if (text == null){
            return;
        }
        int textWidth = font.width(text);
        int color = entity.getColor();
        this.font.drawInBatch(Component.nullToEmpty(text), (float) -textWidth / 2 + 1, 1, color, false, poseStack.last().pose(), pBuffer, Font.DisplayMode.NORMAL, 0, 15728880);
        if (pBuffer instanceof MultiBufferSource.BufferSource) {
            BakedGlyph texturedglyph = font.getFontSet(Style.DEFAULT_FONT).whiteGlyph();
            ((MultiBufferSource.BufferSource)pBuffer).endBatch(texturedglyph.renderType(Font.DisplayMode.NORMAL));
        }
    }
}
