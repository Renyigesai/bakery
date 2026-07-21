package com.renyigesai.bakeries.client.render.block;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.renyigesai.bakeries.block.cake_box.CakeBoxBlockEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.Vec2;
import net.minecraftforge.items.ItemStackHandler;

public class CakeBoxBlockEntityRender implements BlockEntityRenderer<CakeBoxBlockEntity> {

    public CakeBoxBlockEntityRender(BlockEntityRendererProvider.Context context) {
    }

    @Override
    public void render(CakeBoxBlockEntity entity, float pPartialTick, PoseStack poseStack, MultiBufferSource buffer, int pPackedLight, int packedOverlay) {
        Direction direction = entity.getBlockState().getValue(BlockStateProperties.HORIZONTAL_FACING).getOpposite();
        ItemStackHandler inventory = entity.getInventory();
        ItemStack stack1 = inventory.getStackInSlot(0);
        int posLong = (int) entity.getBlockPos().asLong();
        float f0 = 0f;

        if (!stack1.isEmpty()) {
            boolean isBlockItem = stack1.getItem() instanceof BlockItem;
            Vec2 itemOff = new Vec2(0f,-0.315f);
            float f1 = isBlockItem ? -direction.toYRot() - 0f + f0 : -direction.toYRot() - 180f + f0;
            float y = isBlockItem ? 0f : 0.125f;
            setRender(poseStack, itemOff, y, f1);
            if (entity.getLevel() != null) {
                if (isBlockItem) {
                    BlockState state = ((BlockItem) stack1.getItem()).getBlock().defaultBlockState();
                    BakedModel model = Minecraft.getInstance().getBlockRenderer().getBlockModel(state);
                    setRenderBlockModel(stack1, model, packedOverlay, poseStack, buffer, pPackedLight);
                } else {
                    setRenderItemModel(stack1, entity.getLevel(), entity.getBlockPos(), packedOverlay, poseStack, buffer, posLong);
                }
            }
            poseStack.popPose();
        }
    }

    private void setRender(PoseStack poseStack,Vec2 vec2,float y,float f1){
        poseStack.pushPose();
        poseStack.translate(0.5, 0.5, 0.5);
        poseStack.mulPose(Axis.YP.rotationDegrees(f1));
        poseStack.mulPose(Axis.XP.rotationDegrees(0));
        poseStack.translate(vec2.x, vec2.y+y, (float) 0);
        poseStack.scale(0.5f, 0.5f, 0.5f);
    }

    private void setRenderItemModel(ItemStack stack, Level level, BlockPos pos, int packedOverlay, PoseStack poseStack, MultiBufferSource buffer, int posLong){
        Minecraft.getInstance().getItemRenderer().renderStatic(
                stack, ItemDisplayContext.FIXED, LevelRenderer.getLightColor(level, pos), packedOverlay, poseStack, buffer, level, posLong + 1);
    }

    private void setRenderBlockModel(ItemStack stack, BakedModel model,int packedOverlay,PoseStack poseStack,MultiBufferSource buffer,int pPackedLight){
        Minecraft.getInstance().getItemRenderer().render(
                stack,ItemDisplayContext.FIXED,false,poseStack,buffer,pPackedLight,packedOverlay,model);
    }
}
