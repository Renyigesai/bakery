package com.renyigesai.bakeries.block.cake;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.Vec2;
import net.minecraftforge.items.ItemStackHandler;

public class CakeRollProcessingBlockEntityRender implements BlockEntityRenderer<CakeRollProcessingBlockEntity> {

    public CakeRollProcessingBlockEntityRender(BlockEntityRendererProvider.Context context) {
    }

    @Override
    public void render(CakeRollProcessingBlockEntity entity, float pPartialTick, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight, int pPackedOverlay) {
        BlockState blockState = entity.getBlockState();
        if (!blockState.getValue(CakeRollProcessingBlock.ROLL)){
            Direction direction = entity.getBlockState().getValue(BlockStateProperties.HORIZONTAL_FACING).getOpposite();
            ItemStackHandler inventory = entity.getInventory();
            int posLong = (int) entity.getBlockPos().asLong();
            for (int i = 0; i < inventory.getSlots() ; i++) {
                ItemStack stackInSlot = inventory.getStackInSlot(i);
                if (!stackInSlot.isEmpty()){
                    pPoseStack.pushPose();
                    Vec2 itemOff = entity.getItemOffest(i);
                    pPoseStack.translate(0.5,0.1865, 0.5);
                    pPoseStack.mulPose(Axis.YP.rotationDegrees(-direction.toYRot()));
                    pPoseStack.mulPose(Axis.XP.rotationDegrees(-90));
                    pPoseStack.translate(itemOff.x, itemOff.y, 0.0);
                    pPoseStack.scale(0.285f, 0.285f, 0.285f);
                    setRenderItemModel(stackInSlot,entity.getLevel(),entity.getBlockPos(),pPackedOverlay,pPoseStack,pBuffer,posLong);
                    pPoseStack.popPose();
                }
            }
        }
    }

    private void setRenderItemModel(ItemStack stack, Level level, BlockPos pos, int packedOverlay, PoseStack poseStack, MultiBufferSource buffer, int posLong){
        Minecraft.getInstance().getItemRenderer().renderStatic(
                stack, ItemDisplayContext.FIXED, LevelRenderer.getLightColor(level, pos), packedOverlay, poseStack, buffer, level, posLong + 1);
    }
}
