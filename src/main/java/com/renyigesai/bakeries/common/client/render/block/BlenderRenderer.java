package com.renyigesai.bakeries.common.client.render.block;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import com.renyigesai.bakeries.BakeriesMod;
import com.renyigesai.bakeries.common.blocks.blander.BlenderBlockEntity;
import com.renyigesai.bakeries.common.client.model.BlenderModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

public class BlenderRenderer implements BlockEntityRenderer<BlenderBlockEntity> {
    private final BlenderModel<?> model;
    public static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath("bakeries","textures/entity/blender.png");

    public BlenderRenderer(BlockEntityRendererProvider.Context pContext) {
        this.model = new BlenderModel<>(pContext.bakeLayer(BlenderModel.BLENDER));
    }

    @Override
    public void render(BlenderBlockEntity blender, float pPartialTick, PoseStack pPoseStack, MultiBufferSource multiBufferSource, int i, int i1) {
        Direction direction = blender.getBlockState().getValue(BlockStateProperties.HORIZONTAL_FACING).getOpposite();
        pPoseStack.pushPose();
        pPoseStack.translate(0.5F, 1.5F, 0.5F);
        pPoseStack.mulPose(Axis.YP.rotationDegrees(-direction.toYRot()));
        pPoseStack.mulPose(Axis.XP.rotationDegrees(180F));
        pPoseStack.scale(0.9995F, 0.9995F, 0.9995F);
        VertexConsumer vertexConsumer = multiBufferSource.getBuffer(RenderType.entityCutoutNoCull(TEXTURE));
        this.model.getUp().xRot = (float) Math.toRadians(blender.getProgress(pPartialTick) * -25);
        if (!BakeriesMod.aprilFoolsDay){
            this.model.getHead().yRot = (float) Math.toRadians(blender.getRprogress(pPartialTick) * 360);
        }else {
            this.model.getAll().yRot = (float) Math.toRadians(blender.getRprogress(pPartialTick) * 360);
        }
        this.model.renderToBuffer(pPoseStack, vertexConsumer, i, i1);
        pPoseStack.popPose();
    }
}
