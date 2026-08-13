package com.renyigesai.bakeries.common.client.render.block;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
public interface IBBlockEntityRenderer<T extends BlockEntity> extends BlockEntityRenderer<T> {

    @Override
    default void render(@NotNull T be, float pPartialTick, @NotNull PoseStack poseStack, @NotNull MultiBufferSource multiBufferSource,  int pPackedLight, int pPackedOverlay){
        poseStack.pushPose();
        basicsRotation(be, pPartialTick, poseStack, multiBufferSource, pPackedLight, pPackedOverlay);
        startRender(be, pPartialTick, poseStack, multiBufferSource, pPackedLight, pPackedOverlay);
        poseStack.popPose();
    }

    default void basicsRotation(@NotNull T be, float pPartialTick, @NotNull PoseStack poseStack, @NotNull MultiBufferSource multiBufferSource,  int pPackedLight, int pPackedOverlay){
        poseStack.translate(0.5,0,0.5);
        BlockState blockState = be.getBlockState();
        if (blockState.hasProperty(HorizontalDirectionalBlock.FACING)){
            Direction facing = blockState.getValue(HorizontalDirectionalBlock.FACING);
            switch (facing) {
                case SOUTH -> poseStack.mulPose(Axis.YP.rotationDegrees(180.0F));
                case WEST -> poseStack.mulPose(Axis.YP.rotationDegrees(90.0F));
                case NORTH -> poseStack.mulPose(Axis.YP.rotationDegrees(0.0F));
                case EAST -> poseStack.mulPose(Axis.YP.rotationDegrees(270.0F));
                case UP -> poseStack.mulPose(Axis.XP.rotationDegrees(90.0F));
                case DOWN -> poseStack.mulPose(Axis.XN.rotationDegrees(90.0F));
            }
        }

    }

    default void oppositeY(@NotNull T be, float pPartialTick, @NotNull PoseStack poseStack, @NotNull MultiBufferSource multiBufferSource, int pPackedLight, int pPackedOverlay){
        poseStack.mulPose(Axis.YP.rotationDegrees(180));
    }

    void startRender(@NotNull T be, float v, @NotNull PoseStack poseStack, @NotNull MultiBufferSource multiBufferSource,  int pPackedLight, int pPackedOverlay);
}
