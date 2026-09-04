package com.renyigesai.bakeries.common.client.render.block;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import com.renyigesai.bakeries.common.blocks.moka_pot.MokaPotBlockEntity;
import com.renyigesai.bakeries.common.client.model.MokaPotModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(value = Dist.CLIENT)
public class MokaPotRender implements BlockEntityRenderer<MokaPotBlockEntity> {
    private final MokaPotModel<?> model;
    public static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath("bakeries","textures/entity/moka_pot.png");

    public MokaPotRender(BlockEntityRendererProvider.Context context) {
        model = new MokaPotModel<>(context.bakeLayer(MokaPotModel.LAYER_LOCATION));
    }

    @Override
    public void render(MokaPotBlockEntity entity, float pPartialTick, PoseStack poseStack, MultiBufferSource pBuffer, int pPackedLight, int pPackedOverlay) {
        Direction direction = entity.getBlockState().getValue(BlockStateProperties.HORIZONTAL_FACING).getOpposite();
        poseStack.pushPose();
        if (entity.getLevel() != null) {
            long l = entity.getLevel().getGameTime() - entity.wobbleStartedAtTick;
            if (l < 10) {
                float decay = 1.0f;
                float enhancedDecay = (float)Math.pow(decay, 1.2f);
                float rotTime = (l + pPartialTick) * 0.45f;
                float shakeAngle = enhancedDecay * (float)Math.sin(rotTime * Math.PI * 2) * 8.0f;
                float xTime = (l + pPartialTick) * 0.38f;
                float xOffset = enhancedDecay * (float)Math.cos(xTime * Math.PI * 2 + 0.7f) * 0.03125f;
                float zTime = (l + pPartialTick) * 0.42f;
                float zOffset = enhancedDecay * (float)Math.sin(zTime * Math.PI * 2 + 1.5f) * 0.03125f;
                poseStack.translate(0.5F + xOffset, 1.5F, 0.5F + zOffset);
                poseStack.mulPose(Axis.YP.rotationDegrees(-direction.toYRot()));
                poseStack.mulPose(Axis.XP.rotationDegrees(180F));
                poseStack.scale(0.9995F, 0.9995F, 0.9995F);
                this.model.getAll().yRot = (float) Math.toRadians(shakeAngle);
            } else {
                poseStack.translate(0.5F, 1.5F, 0.5F);
                poseStack.mulPose(Axis.YP.rotationDegrees(-direction.toYRot()));
                poseStack.mulPose(Axis.XP.rotationDegrees(180F));
                poseStack.scale(0.9995F, 0.9995F, 0.9995F);
                this.model.getAll().yRot = 0.0f;
            }
        }
        VertexConsumer vertexConsumer = pBuffer.getBuffer(RenderType.entityCutoutNoCull(TEXTURE));
        this.model.renderToBuffer(poseStack, vertexConsumer, pPackedLight, pPackedOverlay);
        poseStack.popPose();
    }
}
