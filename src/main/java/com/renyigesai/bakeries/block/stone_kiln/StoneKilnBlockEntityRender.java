package com.renyigesai.bakeries.block.stone_kiln;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.Vec2;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.items.ItemStackHandler;
@OnlyIn(value = Dist.CLIENT)
public class StoneKilnBlockEntityRender implements BlockEntityRenderer<StoneKilnBlockEntity> {

    public StoneKilnBlockEntityRender(BlockEntityRendererProvider.Context context) {
    }

    @Override
    public void render(StoneKilnBlockEntity entity, float pPartialTick, PoseStack poseStack, MultiBufferSource pBuffer, int pPackedLight, int pPackedOverlay) {
        Direction direction = entity.getBlockState().getValue(BlockStateProperties.HORIZONTAL_FACING).getOpposite();
        ItemStackHandler inventory = entity.getInventory();
        ItemStack stack = inventory.getStackInSlot(0);
        int posLong = (int) entity.getBlockPos().asLong();
        if (!entity.isEmpty() && !stack.isEmpty()) {
            float f1 = -direction.toYRot() - 180f;
            poseStack.pushPose();
            poseStack.translate(0.5,0.625,0.5);
            poseStack.mulPose(Axis.YP.rotationDegrees(f1));
            poseStack.mulPose(Axis.XP.rotationDegrees(0));
            float size = entity.getSize();
            System.out.println(size);
            poseStack.scale((float) (0.5 + size), (float) (0.5 + size), (float) (0.5 + size));
            if (entity.getLevel() != null) {

                Minecraft.getInstance().getItemRenderer().renderStatic(stack, ItemDisplayContext.FIXED, LevelRenderer.getLightColor(entity.getLevel(), entity.getBlockPos()), pPackedOverlay, poseStack, pBuffer, entity.getLevel(), (int) (posLong + 1));
            }
            poseStack.popPose();
        }
    }

    private void setRender(PoseStack poseStack, float f1,int time){
        poseStack.pushPose();
        poseStack.translate(0.5,0.625,0.5);
        poseStack.mulPose(Axis.YP.rotationDegrees(f1));
        poseStack.mulPose(Axis.XP.rotationDegrees(0));
        float size = (float) (0.5 + 1.5f/time);
        poseStack.scale(size,size,size);
    }
}
