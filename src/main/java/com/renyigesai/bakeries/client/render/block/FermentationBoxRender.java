package com.renyigesai.bakeries.client.render.block;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import com.renyigesai.bakeries.BakeriesMod;
import com.renyigesai.bakeries.block.fermentation_box.FermentationBoxBlockEntity;
import com.renyigesai.bakeries.client.model.FermentationBoxModel;
import com.renyigesai.bakeries.util.measurer.ClientUtilsMeasurer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec2;
import org.jetbrains.annotations.NotNull;

public class FermentationBoxRender implements IBBlockEntityRenderer<FermentationBoxBlockEntity> {
    private final FermentationBoxModel<?> model;
    public static final ResourceLocation TEXTURE = new ResourceLocation("bakeries", "textures/entity/fermentation_box.png");
    private static final float ADD = 0.225f;

    // 相对中心坐标（已减去 0.5），沿本地 X 轴排列
    public static final Vec2[] VEC2S = new Vec2[]{
            new Vec2(-ADD, 0f),
            new Vec2(0f, 0f),
            new Vec2(ADD, 0f),
            new Vec2(-ADD, 0f),
            new Vec2(0f, 0f),
            new Vec2(ADD, 0f)
    };

    public static final float[] YS = new float[]{
            0.005f,
            -0.005f,
            0.005f,
            -0.005f,
            0.005f,
            -0.005f,
    };

    public FermentationBoxRender(BlockEntityRendererProvider.Context pContext) {
        this.model = new FermentationBoxModel<>(pContext.bakeLayer(FermentationBoxModel.FERMENTATION_BOX));
    }

    @Override
    public void startRender(@NotNull FermentationBoxBlockEntity box, float pPartialTicks, @NotNull PoseStack poseStack,
                            @NotNull MultiBufferSource multiBufferSource, int pPackedLight, int pPackedOverlay) {
        renderBlock(box, pPartialTicks, poseStack, multiBufferSource, pPackedLight, pPackedOverlay);
        renderItem(box, pPartialTicks, poseStack, multiBufferSource, pPackedOverlay);
    }

    private void renderItem(FermentationBoxBlockEntity box, float pPartialTicks, PoseStack poseStack,
                            MultiBufferSource multiBufferSource, int i1) {
        int posLong = (int) box.getBlockPos().asLong();
        for (int slot = 0; slot < box.getItems().getSlots(); slot++) {
            ItemStack item = box.getItem(slot);
            if (item.isEmpty()) {
                continue;
            }
            poseStack.pushPose();
            float y = slot > 2 ? 0.3125f + 0.03125f - 0.0625f : 0.5625f + 0.03125f;
            Vec2 vec2 = VEC2S[slot];
            poseStack.translate(vec2.x, y + YS[slot], vec2.y);
            poseStack.scale(0.35f, 0.35f, 0.35f);
            renderModel(item, box, poseStack, multiBufferSource, i1, posLong);
            poseStack.popPose();
        }
    }

    private void renderModel(ItemStack stack, FermentationBoxBlockEntity box, PoseStack poseStack, MultiBufferSource pBuffer, int pPackedOverlay, int posLong) {
        Level level = box.getLevel();
        if (level == null) {
            return;
        }
        if (BakeriesMod.utilsMeasurer instanceof ClientUtilsMeasurer measurer){
            if (measurer.isItem3D(stack)){
                BlockState state = ((BlockItem) stack.getItem()).getBlock().defaultBlockState();
                BakedModel model = Minecraft.getInstance().getBlockRenderer().getBlockModel(state);
                Minecraft.getInstance().getItemRenderer().render(stack, ItemDisplayContext.FIXED, false, poseStack, pBuffer, LevelRenderer.getLightColor(box.getLevel(), box.getBlockPos()), pPackedOverlay, model);
            }else {
                poseStack.pushPose();
                poseStack.translate(0f,-0.25,0f);
                poseStack.mulPose(Axis.XP.rotationDegrees(-90));
                Minecraft.getInstance().getItemRenderer().renderStatic(stack, ItemDisplayContext.FIXED, LevelRenderer.getLightColor(box.getLevel(), box.getBlockPos()), pPackedOverlay, poseStack, pBuffer, box.getLevel(), (int) (posLong + 1));
                poseStack.popPose();
            }
        }
    }

    private void renderBlock(FermentationBoxBlockEntity box, float pPartialTick, PoseStack poseStack,
                             MultiBufferSource multiBufferSource, int i, int i1) {
        poseStack.pushPose();
        poseStack.translate(0, 1.5F, 0);
        poseStack.mulPose(Axis.XP.rotationDegrees(180F));
        poseStack.scale(0.9995F, 0.9995F, 0.9995F);

        VertexConsumer vertexConsumer = multiBufferSource.getBuffer(RenderType.entityCutoutNoCull(TEXTURE));
        this.model.getDoor().xRot = (float) Math.toRadians(box.getProgress(pPartialTick) * 75);
        this.model.renderToBuffer(poseStack, vertexConsumer, i, i1, 1.0F, 1.0F, 1.0F, 1.0F);
        poseStack.popPose();
    }
}