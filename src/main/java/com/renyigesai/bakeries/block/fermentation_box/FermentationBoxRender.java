package com.renyigesai.bakeries.block.fermentation_box;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import com.renyigesai.bakeries.block.oven.OvenBlock;
import com.renyigesai.bakeries.block.oven.OvenBlockEntity;
import com.renyigesai.bakeries.client.model.FermentationBoxModel;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.Vec2;

public class FermentationBoxRender implements BlockEntityRenderer<FermentationBoxBlockEntity> {
    private final FermentationBoxModel<?> model;
    public static final ResourceLocation TEXTURE = new ResourceLocation("bakeries","textures/entity/fermentation_box.png");
    private static final float ADD = 0.225f;
    public static final Vec2[] VEC2S_1 = new Vec2[]{
            new Vec2(0.5f - ADD,0.5f),
            new Vec2(0.5f,0.5f),
            new Vec2(0.5f + ADD,0.5f),
            new Vec2(0.5f - ADD,0.5f),
            new Vec2(0.5f,0.5f),
            new Vec2(0.5f + ADD,0.5f)
    };

    public static final Vec2[] VEC2S_2 = new Vec2[]{
            new Vec2(0.5f ,0.5f - ADD),
            new Vec2(0.5f,0.5f),
            new Vec2(0.5f,0.5f + ADD),
            new Vec2(0.5f ,0.5f - ADD),
            new Vec2(0.5f,0.5f),
            new Vec2(0.5f,0.5f + ADD)
    };

    public FermentationBoxRender(BlockEntityRendererProvider.Context pContext) {
        this.model = new FermentationBoxModel<>(pContext.bakeLayer(FermentationBoxModel.FERMENTATION_BOX));
    }

    @Override
    public void render(FermentationBoxBlockEntity box, float pPartialTicks, PoseStack poseStack, MultiBufferSource multiBufferSource, int i, int i1) {
        renderBlock(box, pPartialTicks, poseStack, multiBufferSource, i, i1);
        renderItem(box,pPartialTicks,poseStack,multiBufferSource,i1);
    }

    private void renderItem(FermentationBoxBlockEntity box, float pPartialTicks,PoseStack poseStack, MultiBufferSource multiBufferSource, int i1){
        Direction direction = box.getBlockState().getValue(OvenBlock.FACING);
        int posLong = (int) box.getBlockPos().asLong();
        for (int solt = 0; solt < box.getItems().getSlots(); solt++) {
            ItemStack item = box.getItem(solt);
            if (!item.isEmpty()){
                poseStack.pushPose();
                float y = solt > 2 ? 0.3125f + 0.03125f : 0.5625f + 0.03125f;
                Vec2[] vec2 = getVec2(direction);
                final float LEFT_MOVE;
                if (direction == Direction.SOUTH || direction == Direction.WEST){
                    LEFT_MOVE = 0.0625f;
                }else {
                   LEFT_MOVE = -0.0625f;
                }
                poseStack.translate(vec2[solt].x + LEFT_MOVE,y,vec2[solt].y + LEFT_MOVE);
                poseStack.mulPose(Axis.YP.rotationDegrees(-direction.toYRot() - 180));
                poseStack.scale(0.35f,0.35f,0.35f);
                renderModel(item,box,poseStack,multiBufferSource,i1,posLong);
                poseStack.popPose();
            }
        }
    }

    private void renderModel(ItemStack stack,FermentationBoxBlockEntity box, PoseStack poseStack, MultiBufferSource pBuffer, int pPackedOverlay,int posLong){
        Level level = box.getLevel();
        if (level != null){
            boolean isBlock = stack.getItem() instanceof BlockItem;
            if (isBlock) {
                BlockState state = ((BlockItem) stack.getItem()).getBlock().defaultBlockState();
                BakedModel model = Minecraft.getInstance().getBlockRenderer().getBlockModel(state);
                Minecraft.getInstance().getItemRenderer().render(stack, ItemDisplayContext.FIXED, false, poseStack, pBuffer, LevelRenderer.getLightColor(box.getLevel(), box.getBlockPos()), pPackedOverlay, model);
            } else {
                Minecraft.getInstance().getItemRenderer().renderStatic(stack, ItemDisplayContext.FIXED, LevelRenderer.getLightColor(box.getLevel(), box.getBlockPos()), pPackedOverlay, poseStack, pBuffer, box.getLevel(), (int) (posLong + 1));
            }
        }
    }

    private void renderBlock(FermentationBoxBlockEntity box, float pPartialTick, PoseStack poseStack, MultiBufferSource multiBufferSource, int i, int i1){
        Direction direction = box.getBlockState().getValue(BlockStateProperties.HORIZONTAL_FACING).getOpposite();
        poseStack.pushPose();
        poseStack.translate(0.5F, 1.5F, 0.5F);
        poseStack.mulPose(Axis.YP.rotationDegrees(-direction.toYRot()));
        poseStack.mulPose(Axis.XP.rotationDegrees(180F));
        poseStack.scale(0.9995F, 0.9995F, 0.9995F);
        VertexConsumer vertexConsumer = multiBufferSource.getBuffer(RenderType.entityCutoutNoCull(TEXTURE));
        this.model.getDoor().xRot = (float) Math.toRadians(box.getProgress(pPartialTick) * 75);
        this.model.renderToBuffer(poseStack, vertexConsumer, i, i1, 1.0F, 1.0F, 1.0F, 1.0F);
        poseStack.popPose();
    }

    private Vec2[] getVec2(Direction direction){
        switch (direction){
            case NORTH,SOUTH -> {
                return VEC2S_1;
            }
            case EAST,WEST -> {
                return VEC2S_2;
            }
        }
        return new Vec2[]{};
    }
}
