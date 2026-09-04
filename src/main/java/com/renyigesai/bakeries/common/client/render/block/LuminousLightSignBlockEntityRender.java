package com.renyigesai.bakeries.common.client.render.block;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.renyigesai.bakeries.common.blocks.luminous_light_sign.LuminousLightSignBlock;
import com.renyigesai.bakeries.common.blocks.luminous_light_sign.LuminousLightSignBlockEntity;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.font.glyphs.BakedGlyph;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientTickEvent;

import java.text.SimpleDateFormat;
import java.util.Date;

public class LuminousLightSignBlockEntityRender implements BlockEntityRenderer<LuminousLightSignBlockEntity> {
    private final Font font;
    private static boolean colonVisible = true;

    private static String lazyTime = "00 00";
    private static int count;
    private static final SimpleDateFormat HH = new SimpleDateFormat("HH");
    private static final SimpleDateFormat MM = new SimpleDateFormat("mm");

    public LuminousLightSignBlockEntityRender(BlockEntityRendererProvider.Context pContext) {
        this.font = pContext.getFont();
    }

    @Override
    public void render(LuminousLightSignBlockEntity pBlockEntity, float pPartialTick, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight, int pPackedOverlay) {
        pPoseStack.pushPose();
        renderText(pBlockEntity,pPoseStack,pBuffer,pPackedLight);
        pPoseStack.popPose();
        if (pBlockEntity.getText() == null){
            return;
        }
        if (pBlockEntity.getText().isEmpty()){
            pPoseStack.pushPose();
            renderTime(pBlockEntity,pPoseStack,pBuffer);
            pPoseStack.popPose();
            pPoseStack.pushPose();
            renderSecond(pBlockEntity,pPoseStack,pBuffer);
            pPoseStack.popPose();
        }
    }

    private void renderSecond(LuminousLightSignBlockEntity entity,PoseStack poseStack,MultiBufferSource pBuffer){
        if (colonVisible){
            int timeEextWidth = font.width(":");
            int color = entity.getColor();
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
            startRender(":",timeEextWidth,color,poseStack,pBuffer);
        }
    }

    private void renderTime(LuminousLightSignBlockEntity entity,PoseStack poseStack,MultiBufferSource pBuffer){
        int textWidth = font.width(lazyTime);
        int color = entity.getColor();
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
        startRender(lazyTime,textWidth,color,poseStack,pBuffer);
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
        startRender(text,textWidth,color,poseStack,pBuffer);
    }

    private void startRender(String text,int textWidth,int color,PoseStack poseStack,MultiBufferSource pBuffer){
        this.font.drawInBatch(Component.nullToEmpty(text), (float) -textWidth / 2 + 1, 1, color, false, poseStack.last().pose(), pBuffer, Font.DisplayMode.NORMAL, 0, 15728880);
        if (pBuffer instanceof MultiBufferSource.BufferSource) {
            BakedGlyph texturedglyph = font.getFontSet(Style.DEFAULT_FONT).whiteGlyph();
            ((MultiBufferSource.BufferSource)pBuffer).endBatch(texturedglyph.renderType(Font.DisplayMode.NORMAL));
        }
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
