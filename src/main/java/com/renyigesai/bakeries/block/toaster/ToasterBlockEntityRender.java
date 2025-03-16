package com.renyigesai.bakeries.block.toaster;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;

public class ToasterBlockEntityRender implements BlockEntityRenderer<ToasterBlockEntity> {

    public ToasterBlockEntityRender(BlockEntityRendererProvider.Context context){
    }

    @Override
    public void render(ToasterBlockEntity pBlockEntity, float pPartialTick, PoseStack pPoseStack,
                      MultiBufferSource pBuffer, int pPackedLight, int pPackedOverlay) {
        // 获取方块朝向

    }
}
