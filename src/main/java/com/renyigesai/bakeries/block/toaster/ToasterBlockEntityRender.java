package com.renyigesai.bakeries.block.toaster;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
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
import net.minecraftforge.items.ItemStackHandler;

public class ToasterBlockEntityRender implements BlockEntityRenderer<ToasterBlockEntity> {

    public ToasterBlockEntityRender(BlockEntityRendererProvider.Context context){

    }

    @Override
    public void render(ToasterBlockEntity pBlockEntity, float pPartialTick, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight, int pPackedOverlay) {
        Direction opposite = pBlockEntity.getBlockState().getValue(BlockStateProperties.HORIZONTAL_FACING).getOpposite();
        ItemStackHandler inventory = pBlockEntity.getItemHandler();
        int posLong = (int) pBlockEntity.getBlockPos().asLong();
        for (int i = 0; i < inventory.getSlots(); i++) {
            ItemStack stack1 = inventory.getStackInSlot(i);
            if (!stack1.isEmpty()){
                pPoseStack.pushPose();
                pPoseStack.translate(0.5,0.5625,0.5);
                float f = -opposite.toYRot();
                pPoseStack.mulPose(Axis.YP.rotationDegrees(f));
                pPoseStack.mulPose(Axis.XP.rotationDegrees(90F));
                Vec2 itemOffset = pBlockEntity.getItemOffset(i);
                pPoseStack.translate(itemOffset.x,itemOffset.y,0.0);
                pPoseStack.scale(0.285F,0.285F,0.285F);
                if (pBlockEntity.getLevel() != null){
                    Minecraft.getInstance().getItemRenderer().renderStatic(stack1,
                            ItemDisplayContext.FIXED, LevelRenderer.getLightColor(pBlockEntity.getLevel(),
                                    pBlockEntity.getBlockPos()),pPackedOverlay,pPoseStack,pBuffer,pBlockEntity.getLevel(),posLong + i);
                }
                pPoseStack.pushPose();
            }
        }
    }

}
