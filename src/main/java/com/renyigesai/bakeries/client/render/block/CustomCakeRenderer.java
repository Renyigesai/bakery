package com.renyigesai.bakeries.client.render.block;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import com.renyigesai.bakeries.block.custom_cake.CustomCakeBlock;
import com.renyigesai.bakeries.block.custom_cake.CustomCakeBlockEntity;
import com.renyigesai.bakeries.init.BakeriesBlocks;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.client.model.data.ModelData;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class CustomCakeRenderer implements BlockEntityRenderer<CustomCakeBlockEntity> {

    public CustomCakeRenderer(BlockEntityRendererProvider.Context context) {

    }

    @Override
    public void render(CustomCakeBlockEntity cc, float v, PoseStack poseStack, MultiBufferSource multiBufferSource, int i, int i1) {
        List<String> partIds = cc.getPartIds();
        String candleId = cc.getCandleId();
        Direction direction = cc.getBlockState().getValue(CustomCakeBlock.FACING);

        renderCake(partIds,direction,v,poseStack,multiBufferSource,i,i1,true,candleId);

    }

    private static void renderModel(BakedModel model, PoseStack poseStack, MultiBufferSource buffer, int light, int overlay) {
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

    /**1.3.1新增，可直接在调用自定义蛋糕渲染*/
    public static void renderCake(List<String> partIds,Direction direction,float v, PoseStack poseStack, MultiBufferSource multiBufferSource, int i, int i1,boolean renderParer,@Nullable String candleId){

        if (renderParer){
            poseStack.pushPose();
            Minecraft.getInstance().getBlockRenderer().renderSingleBlock(BakeriesBlocks.SILICONE_PAPER.get().defaultBlockState(),poseStack,multiBufferSource,i,i1);
            poseStack.popPose();
        }

        for (String id : partIds) {
            ResourceLocation resourceLocation = new ResourceLocation(id);
            ResourceLocation modelId = new ResourceLocation(resourceLocation.getNamespace(), "cake_part/" + resourceLocation.getPath());
            BakedModel model = Minecraft.getInstance().getModelManager().getModel(modelId);
            poseStack.pushPose();
            poseStack.translate(0.5, 0.0, 0.5);
            poseStack.mulPose(Axis.YP.rotationDegrees(direction.toYRot()));
            if (direction == Direction.WEST || direction == Direction.EAST){
                poseStack.mulPose(Axis.YP.rotationDegrees(180));
            }
            poseStack.translate(-0.5, 0.0, -0.5);
            poseStack.scale(1, 1, 1);
            renderModel(model, poseStack, multiBufferSource, i, i1);
            poseStack.popPose();
        }

        if (candleId != null) {
            Block block = BuiltInRegistries.BLOCK.get(ResourceLocation.tryParse(candleId));
            if (block != Blocks.AIR) {
                poseStack.pushPose();
                poseStack.translate(0.5, 0.5, 0.5);
                poseStack.mulPose(Axis.YP.rotationDegrees(direction.toYRot()));
                if (direction == Direction.WEST || direction == Direction.EAST) {
                    poseStack.mulPose(Axis.YP.rotationDegrees(180));
                }
                poseStack.scale(0.495f, 0.495f, 0.495f);
                poseStack.translate(-0.5, 0, -0.5);
                Minecraft.getInstance().getBlockRenderer().renderSingleBlock(block.defaultBlockState(), poseStack, multiBufferSource, i, i1);
                poseStack.popPose();
            }
        }
    }
}
