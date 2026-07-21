package com.renyigesai.bakeries.block.custom_cake;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import com.renyigesai.bakeries.init.BakeriesBlocks;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraftforge.client.model.data.ModelData;

import java.util.List;

public class CustomCakeRenderer implements BlockEntityRenderer<CustomCakeBlockEntity> {

    public CustomCakeRenderer(BlockEntityRendererProvider.Context context) {

    }

    @Override
    public void render(CustomCakeBlockEntity cc, float v, PoseStack poseStack, MultiBufferSource multiBufferSource, int i, int i1) {
        List<String> partIds = cc.getPartIds();
        Direction direction = cc.getBlockState().getValue(CustomCakeBlock.FACING);

        poseStack.pushPose();
        Minecraft.getInstance().getBlockRenderer().renderSingleBlock(BakeriesBlocks.SILICONE_PAPER.get().defaultBlockState(),poseStack,multiBufferSource,i,i1);
        poseStack.popPose();

        for (String id : partIds) {
            ResourceLocation resourceLocation = new ResourceLocation(id);
            ResourceLocation modelId = new ResourceLocation(resourceLocation.getNamespace(), "cake_part/" + resourceLocation.getPath());
            BakedModel model = Minecraft.getInstance().getModelManager().getModel(modelId);
            poseStack.pushPose();
            poseStack.translate(0.5, 0.0, 0.5);
            poseStack.mulPose(Axis.YP.rotationDegrees(direction.toYRot()));
            poseStack.translate(-0.5, 0.0, -0.5);
            poseStack.scale(1, 1, 1);
            renderModel(model, poseStack, multiBufferSource, i, i1);
            poseStack.popPose();
        }

    }

    private void renderModel(BakedModel model, PoseStack poseStack, MultiBufferSource buffer, int light, int overlay) {
        RenderType renderType = RenderType.cutout();
        VertexConsumer consumer = buffer.getBuffer(renderType);

        for (Direction direction : Direction.values()) {
            List<BakedQuad> quads = model.getQuads(null, direction, RandomSource.create(), ModelData.EMPTY, renderType);
            for (BakedQuad quad : quads) {
                consumer.putBulkData(poseStack.last(), quad, 1.0f, 1.0f, 1.0f, light, overlay);
            }
        }
        List<BakedQuad> generalQuads = model.getQuads(null, null, RandomSource.create(), ModelData.EMPTY, renderType);
        for (BakedQuad quad : generalQuads) {
            consumer.putBulkData(poseStack.last(), quad, 1.0f, 1.0f, 1.0f, light, overlay);
        }
    }
}
