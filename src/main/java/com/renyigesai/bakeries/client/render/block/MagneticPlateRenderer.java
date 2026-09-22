package com.renyigesai.bakeries.client.render.block;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.renyigesai.bakeries.block.magnetic_plate.MagneticPlateBlock;
import com.renyigesai.bakeries.block.magnetic_plate.MagneticPlateBlockEntity;
import com.renyigesai.bakeries.client.model.MagneticPlateModel;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class MagneticPlateRenderer implements IBBlockEntityRenderer<MagneticPlateBlockEntity> {

    public static final ResourceLocation DEFAULT_TEXTURE =
            new ResourceLocation("bakeries", "textures/block/magnetic_plate.png");

    public MagneticPlateRenderer(BlockEntityRendererProvider.Context pContext) {

    }

    @Override
    public void startRender(@NotNull MagneticPlateBlockEntity tile, float v, @NotNull PoseStack poseStack, @NotNull MultiBufferSource buffer, int packedLight, int packedOverlay) {
        renderBlock(tile, poseStack, buffer, packedLight, packedOverlay);

        for (int i = 0; i < tile.getItems().getSlots(); i++) {
            ItemStack stackInSlot = tile.getItems().getStackInSlot(i);
            if (!stackInSlot.isEmpty()) {
                renderTool(stackInSlot, tile, poseStack, v, buffer, packedLight, packedOverlay, i);
            }
        }
    }

    @Override
    public void actuaBasicsRotation(@NotNull MagneticPlateBlockEntity be, float pPartialTick, @NotNull PoseStack poseStack, @NotNull MultiBufferSource multiBufferSource, int pPackedLight, int pPackedOverlay) {

    }

    private void renderTool(ItemStack stack, MagneticPlateBlockEntity tile, PoseStack poseStack, float pPartialTick, MultiBufferSource buffer, int packedLight, int packedOverlay, int slot) {
        float u, v;
        if (slot == 0) {
            u = tile.getXyo()[0];
            v = tile.getXyo()[1];
        } else {
            u = tile.getXyo()[2];
            v = tile.getXyo()[3];
        }

        int posLong = (int) tile.getBlockPos().asLong();

        poseStack.pushPose();
        basicsRotation(tile, pPartialTick, poseStack, buffer, packedLight, packedOverlay);
        poseStack.translate(0.5f - u, v, -0.40625f);
        /**特殊渲染农夫乐事的煎锅*/
        if (BuiltInRegistries.ITEM.getKey(stack.getItem()).equals(MagneticPlateBlock.SKILLET)) {
            poseStack.scale(1f, 1f, 1f);
            poseStack.mulPose(Axis.ZP.rotationDegrees(-15));
        } else {
            poseStack.scale(0.5f, 0.5f, 0.5f);
        }
        oppositeY(tile, pPartialTick, poseStack, buffer, packedLight, packedOverlay);
        if (tile.getLevel() != null) {
            Minecraft.getInstance().getItemRenderer().renderStatic(
                    stack, ItemDisplayContext.FIXED,
                    LevelRenderer.getLightColor(tile.getLevel(), tile.getBlockPos()),
                    packedOverlay, poseStack, buffer, tile.getLevel(),
                    (int) (posLong + 1));
        }
        poseStack.popPose();

    }

    private void renderBlock(MagneticPlateBlockEntity tile, PoseStack poseStack,
                             MultiBufferSource buffer, int packedLight, int packedOverlay) {
        String blockId = tile.getBlockId();
        if (blockId == null) {
            return;
        }

        if (!tile.getBlockState().getValue(MagneticPlateBlock.CONTENT)) {
            return;
        }

        BakedModel blockModel = Minecraft.getInstance().getBlockRenderer().getBlockModel(tile.getBlock().defaultBlockState());
        BakedModel mpModel = Minecraft.getInstance().getBlockRenderer().getBlockModel(tile.getBlockState());

        MagneticPlateModel magneticPlateModel = new MagneticPlateModel(mpModel, blockModel,tile.getBlock().defaultBlockState());

        poseStack.pushPose();
        renderModel(tile.getLevel(),tile.getBlockState(),tile.getBlockPos(),magneticPlateModel, poseStack, buffer, packedLight, packedOverlay);
        poseStack.popPose();
    }
}