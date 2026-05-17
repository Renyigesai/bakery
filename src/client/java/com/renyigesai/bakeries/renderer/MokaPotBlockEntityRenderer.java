package com.renyigesai.bakeries.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.renyigesai.bakeries.block.entity.MachineBlockEntity;
import com.renyigesai.bakeries.init.BakeriesBlocks;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.level.block.state.BlockState;

public class MokaPotBlockEntityRenderer implements BlockEntityRenderer<MachineBlockEntity> {
    public MokaPotBlockEntityRenderer(BlockEntityRendererProvider.Context context) {
    }

    @Override
    public void render(MachineBlockEntity blockEntity, float partialTick, PoseStack poseStack,
                       MultiBufferSource bufferSource, int packedLight, int packedOverlay) {
        BlockState state = blockEntity.getBlockState();
        if (!state.is(BakeriesBlocks.MOKA_POT)) {
            return;
        }
        poseStack.pushPose();
        if (blockEntity.isMokaPotBrewing()) {
            long time = blockEntity.getLevel() == null ? 0L : blockEntity.getLevel().getGameTime();
            double xOffset = Math.sin((time + partialTick) * 1.7D) * 0.018D;
            double zOffset = Math.cos((time + partialTick) * 1.3D) * 0.018D;
            poseStack.translate(xOffset, 0.0D, zOffset);
        }
        Minecraft.getInstance().getBlockRenderer().renderSingleBlock(
                state,
                poseStack,
                bufferSource,
                packedLight,
                OverlayTexture.NO_OVERLAY
        );
        poseStack.popPose();
    }
}
