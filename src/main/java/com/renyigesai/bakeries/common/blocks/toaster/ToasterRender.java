package com.renyigesai.bakeries.common.blocks.toaster;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.renyigesai.bakeries.common.client.render.block.IBBlockEntityRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.Vec2;
import net.neoforged.neoforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;

public class ToasterRender implements IBBlockEntityRenderer<ToasterBlockEntity> {

    public ToasterRender(BlockEntityRendererProvider.Context context) {
    }

    @Override
    public void startRender(@NotNull ToasterBlockEntity be, float v, @NotNull PoseStack poseStack, @NotNull MultiBufferSource multiBufferSource, int pPackedLight, int pPackedOverlay) {
        for (int slot = 0; slot < 2; slot++) {
            ItemStackHandler items = be.getItems();
            ItemStack stackInSlot = items.getStackInSlot(slot);
            if (!stackInSlot.isEmpty()) {
                int posLong = (int) be.getBlockPos().asLong();
                poseStack.pushPose();
                float z = slot == 0 ? -0.09375f : 0.09375f;
                poseStack.translate(0, 0.5 + (be.getProgress(v) * 0.25), z);
                oppositeY(be, v, poseStack, multiBufferSource, pPackedLight, pPackedOverlay);
                poseStack.scale(0.5f, 0.5f, 0.5f);
                if (be.getLevel() != null) {
                    Minecraft.getInstance().getItemRenderer().renderStatic(stackInSlot, ItemDisplayContext.FIXED, LevelRenderer.getLightColor(be.getLevel(), be.getBlockPos()), pPackedOverlay, poseStack, multiBufferSource, be.getLevel(), (int) (posLong + 1));
                }
                poseStack.popPose();
            }
        }
    }
}