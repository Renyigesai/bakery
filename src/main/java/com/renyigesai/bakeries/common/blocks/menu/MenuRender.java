package com.renyigesai.bakeries.common.blocks.menu;

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
import net.neoforged.neoforge.items.ItemStackHandler;

public class MenuRender implements BlockEntityRenderer<MenuBlockEntity> {

    private static final float SIZE = 0.0f;
    private static final Vec2 V_NORTH = new Vec2(0.5f,0.9375f - SIZE);
    private static final Vec2 V_SOUTH = new Vec2(0.5f,0.0625f - SIZE);
    private static final Vec2 V_EAST = new Vec2(0.0625f - SIZE,0.5f);
    private static final Vec2 V_WEST = new Vec2(0.9375f - SIZE,0.5f);
    public MenuRender(BlockEntityRendererProvider.Context context){

    }
    @Override
    public void render(MenuBlockEntity entity, float partialTick, PoseStack poseStack, MultiBufferSource buffer, int packedLight, int packedOverlay) {
        Direction direction = entity.getBlockState().getValue(BlockStateProperties.HORIZONTAL_FACING).getOpposite();
        ItemStackHandler inventory = entity.getInventory();
        int posLong = (int) entity.getBlockPos().asLong();
        ItemStack stackInSlot = inventory.getStackInSlot(0);
        if (!stackInSlot.isEmpty()){
            Vec2 vec2 = transformPositionByDirection(direction);
            float f1 = -direction.toYRot() - 180f;
            poseStack.pushPose();
            poseStack.translate(vec2.x,0.3125,vec2.y);
            float scale = 0.55f;
            poseStack.scale(scale,scale,scale);
            poseStack.mulPose(Axis.YP.rotationDegrees(f1));
            if (entity.getLevel() != null) {
                Minecraft.getInstance().getItemRenderer().renderStatic(stackInSlot, ItemDisplayContext.FIXED, LevelRenderer.getLightColor(entity.getLevel(), entity.getBlockPos()), packedOverlay, poseStack, buffer, entity.getLevel(), (int) (posLong + 1));
            }
            poseStack.popPose();
        }
    }

    private Vec2 transformPositionByDirection(Direction direction) {
        return switch (direction){
            case NORTH -> V_NORTH;
            case SOUTH -> V_SOUTH;
            case EAST -> V_EAST;
            case WEST -> V_WEST;
            default -> new Vec2(0.0f,0.0f);
        };
    }

}
