package com.renyigesai.bakeries.client.render.block;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.renyigesai.bakeries.block.stone_kiln.StoneKilnBlockEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.Direction;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
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
            boolean block = stack.getItem() instanceof BlockItem;
            float f1 = -direction.toYRot() - 180f;
            float size = 0.5f;
            float addSize = entity.getSize();
            poseStack.pushPose();
            poseStack.translate(0.5,(0.6 + 0.03125) + (addSize / 4),0.5);
            poseStack.mulPose(Axis.YP.rotationDegrees(f1 + 180F * entity.getProgress(pPartialTick)));
            poseStack.mulPose(Axis.XP.rotationDegrees(0));
            poseStack.scale(size + addSize, size + addSize, size + addSize);
            if (entity.getLevel() != null) {
                if (block) {
                    BlockState state = ((BlockItem) stack.getItem()).getBlock().defaultBlockState();
                    BakedModel model = Minecraft.getInstance().getBlockRenderer().getBlockModel(state);
                    Minecraft.getInstance().getItemRenderer().render(stack, ItemDisplayContext.FIXED, false, poseStack, pBuffer, 15728880, pPackedOverlay, model);
                } else {
                    Minecraft.getInstance().getItemRenderer().renderStatic(stack, ItemDisplayContext.FIXED, 15728880, pPackedOverlay, poseStack, pBuffer, entity.getLevel(), (int) (posLong + 1));
                }
            }
            poseStack.popPose();
        }
    }
}
