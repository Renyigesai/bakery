package com.renyigesai.bakeries.client.render.block;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.renyigesai.bakeries.block.pizza.PizzaFlatbreadBlockEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.Vec2;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(value = Dist.CLIENT)
public class PizzaFlatbreadBlockEntityRender implements BlockEntityRenderer<PizzaFlatbreadBlockEntity> {

    public static final float[] INVENTORY_YP = new float[]{15f,-20f,10f,-10f};
    public static final float[] CHEESE_YP = new float[]{-23f,12f,10f,-15f,5};
    public PizzaFlatbreadBlockEntityRender(BlockEntityRendererProvider.Context context) {
    }

    @Override
    public void render(PizzaFlatbreadBlockEntity entity, float pPartialTick, PoseStack poseStack, MultiBufferSource pBuffer, int pPackedLight, int pPackedOverlay) {
        Direction direction = entity.getBlockState().getValue(BlockStateProperties.HORIZONTAL_FACING).getOpposite();
        if (!entity.getCheeses().getStackInSlot(0).isEmpty()){
            for (int i = 0; i < 5; i++) {
                ItemStack cheese = entity.getCheeses().getStackInSlot(0);
                int posLong = (int) entity.getBlockPos().asLong();
                float f1 = -direction.toYRot() - 180f;
                Vec2[] vec2 = entity.getCheeseVec2();
                poseStack.pushPose();
                poseStack.translate(vec2[i].x,0.0625,vec2[i].y);
                poseStack.mulPose(Axis.YP.rotationDegrees(f1 + CHEESE_YP[i]));
                poseStack.mulPose(Axis.XP.rotationDegrees(90));
                poseStack.scale(0.25f, 0.25f, 0.25f);
                if (entity.getLevel() != null) {
                    Minecraft.getInstance().getItemRenderer().renderStatic(cheese, ItemDisplayContext.FIXED, 15728880, pPackedOverlay, poseStack, pBuffer, entity.getLevel(), (int) (posLong + 1));
                }
                poseStack.popPose();
            }
        }
        for (int i = 0; i < entity.getInventory().getSlots(); i++) {
            ItemStack stackInSlot = entity.getInventory().getStackInSlot(i);
            if (!stackInSlot.isEmpty()){
                int posLong = (int) entity.getBlockPos().asLong();
                float f1 = -direction.toYRot() - 180f;
                Vec2[] vec2 = entity.getVec2();
                poseStack.pushPose();
                poseStack.translate(vec2[i].x,0.0625,vec2[i].y);
                poseStack.mulPose(Axis.YP.rotationDegrees(f1 + INVENTORY_YP[i]));
                poseStack.mulPose(Axis.XP.rotationDegrees(90));
                poseStack.scale(0.2f, 0.2f, 0.2f);
                if (entity.getLevel() != null) {
                    Minecraft.getInstance().getItemRenderer().renderStatic(stackInSlot, ItemDisplayContext.FIXED, 15728880, pPackedOverlay, poseStack, pBuffer, entity.getLevel(), (int) (posLong + 1));
                }
                poseStack.popPose();
            }
        }
    }
}
