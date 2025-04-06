package com.renyigesai.bakeries.block.menu;

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
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.items.ItemStackHandler;

public class MenuBlockEntityRender implements BlockEntityRenderer<MenuBlockEntity> {
    public MenuBlockEntityRender(BlockEntityRendererProvider.Context context){

    }
    @Override
    public void render(MenuBlockEntity entity, float partialTick, PoseStack poseStack, MultiBufferSource buffer, int packedLight, int packedOverlay) {
        Direction direction = entity.getBlockState().getValue(BlockStateProperties.HORIZONTAL_FACING).getOpposite();
        ItemStackHandler inventory = entity.getInventory();
        int posLong = (int) entity.getBlockPos().asLong();
        for (int i = 0; i < inventory.getSlots(); i++) {
            ItemStack stack1 = inventory.getStackInSlot(i);
            if (!stack1.isEmpty()){
                poseStack.pushPose();
                poseStack.translate(0.5,0.5,0.5);
                float f1 = -direction.toYRot();
                poseStack.mulPose(Axis.YP.rotationDegrees(f1));
                boolean vertical = entity.vertical;
                float f2 = vertical?90:0;
                poseStack.mulPose(Axis.XP.rotationDegrees(f2));
                Vec2 itemOff = entity.getItemOffest(i);
                float f3 = vertical?0.15f:-0.4f;
                poseStack.translate(itemOff.x,itemOff.y,f3);
                poseStack.scale(0.4f,0.4f,0.4f);
                if (entity.getLevel() != null){
                    Minecraft.getInstance().getItemRenderer().renderStatic(
                            stack1, ItemDisplayContext.FIXED, LevelRenderer.getLightColor(entity.getLevel(),entity.getBlockPos()),packedOverlay,poseStack,buffer,entity.getLevel(),posLong  + i);
                }
                poseStack.popPose();
            }

        }



    }
}
