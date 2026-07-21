package com.renyigesai.bakeries.client.render.block;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.renyigesai.bakeries.block.toaster.ToasterBlockEntity;
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
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.items.ItemStackHandler;

@OnlyIn(value = Dist.CLIENT)
public class ToasterBlockEntityRender implements BlockEntityRenderer<ToasterBlockEntity> {

    private static final float SIZE = 0.03125F;
    private static final Vec2[] north = new Vec2[]{new Vec2(0.5f,0.625f - SIZE),new Vec2(0.5f,0.4375f - SIZE)};
    private static final Vec2[] south = new Vec2[]{new Vec2(0.5f,0.4375f - SIZE),new Vec2(0.5f,0.625f - SIZE)};
    private static final Vec2[] east = new Vec2[]{new Vec2(0.4375f - SIZE,0.5f),new Vec2(0.625f - SIZE,0.5f)};
    private static final Vec2[] west = new Vec2[]{new Vec2(0.625f - SIZE,0.5f),new Vec2(0.4375f - SIZE,0.5f)};

    public ToasterBlockEntityRender(BlockEntityRendererProvider.Context context) {
    }

    @Override
    public void render(ToasterBlockEntity toaster, float pPartialTick, PoseStack poseStack, MultiBufferSource pBuffer, int pPackedOverlay, int  pPackedOverla) {
        Direction direction = toaster.getBlockState().getValue(BlockStateProperties.HORIZONTAL_FACING).getOpposite();
        for (int slot = 0; slot < 2; slot++) {
            ItemStackHandler items = toaster.getItems();
            ItemStack stackInSlot = items.getStackInSlot(slot);
            if (!stackInSlot.isEmpty()){
                int posLong = (int) toaster.getBlockPos().asLong();
                float f1 = -direction.toYRot() - 180f;
                Vec2[] vec2 = transformPositionByDirection(direction);
                poseStack.pushPose();
                poseStack.translate(vec2[slot].x,0.5 + (toaster.getProgress(pPartialTick) * 0.25),vec2[slot].y);
                poseStack.mulPose(Axis.YP.rotationDegrees(f1));
                poseStack.scale(0.5f, 0.5f, 0.5f);
                if (toaster.getLevel() != null) {
                    Minecraft.getInstance().getItemRenderer().renderStatic(stackInSlot, ItemDisplayContext.FIXED, LevelRenderer.getLightColor(toaster.getLevel(), toaster.getBlockPos()), pPackedOverlay, poseStack, pBuffer, toaster.getLevel(), (int) (posLong + 1));
                }
                poseStack.popPose();
            }
        }
    }

    private Vec2[] transformPositionByDirection(Direction direction) {
        return switch (direction){
            case NORTH -> north;
            case SOUTH -> south;
            case EAST -> east;
            case WEST -> west;
            default -> new Vec2[]{};
        };
    }

}