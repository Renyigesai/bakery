package com.renyigesai.bakeries.block.mix_block;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.renyigesai.bakeries.init.BakeriesBlocks;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LevelRenderer;
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
import net.minecraft.world.phys.Vec2;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.ArrayList;
import java.util.List;
@OnlyIn(value = Dist.CLIENT)
public class MixBlockEntityRender implements BlockEntityRenderer<MixBlockEntity> {

    public static final float ADD_SIZE = 0.25f;
    public static final Vec2[][] VEC2S = {
            new Vec2[]{},
            new Vec2[]{new Vec2(0.5f, 0.5f)},
            new Vec2[]{new Vec2(0.5f - ADD_SIZE, 0.5f), new Vec2(0.5f + ADD_SIZE, 0.5f)},
            new Vec2[]{new Vec2(0.5f - ADD_SIZE, 0.5f - ADD_SIZE), new Vec2(0.5f + ADD_SIZE, 0.5f - ADD_SIZE), new Vec2(0.5f, 0.5f + ADD_SIZE)},
            new Vec2[]{new Vec2(0.5f - ADD_SIZE, 0.5f - ADD_SIZE), new Vec2(0.5f + ADD_SIZE, 0.5f - ADD_SIZE),new Vec2(0.5f - ADD_SIZE, 0.5f + ADD_SIZE), new Vec2(0.5f + ADD_SIZE, 0.5f + ADD_SIZE)}
    };

    public MixBlockEntityRender(BlockEntityRendererProvider.Context context) {
    }

    @Override
    public void render(MixBlockEntity entity, float pPartialTick, PoseStack poseStack, MultiBufferSource pBuffer, int pPackedLight, int pPackedOverlay) {
        if (entity.isEmpty()) {
            return;
        }
        Direction direction = entity.getBlockState().getValue(BlockStateProperties.HORIZONTAL_FACING).getOpposite();
        int count = entity.getInventoryCount();
        if (count <= 0 || count >= VEC2S.length) {
            return;
        }
        Vec2[] positions = VEC2S[count];
        List<ItemStack> itemsToRender = new ArrayList<>();
        for (int i = 0; i < entity.getInventory().getSlots(); i++) {
            ItemStack stack = entity.getInventory().getStackInSlot(i);
            if (!stack.isEmpty()) {
                itemsToRender.add(stack);
            }
        }
        boolean isTray = entity.getBlockState().getValue(MixBlock.TRAY);
        for (int i = 0; i < itemsToRender.size(); i++) {
            if (i >= positions.length) {
                break;
            }
            ItemStack stack = itemsToRender.get(i);
            Vec2 position = positions[i];
            renderItem(stack, entity, direction, position, poseStack, pBuffer, pPackedLight, pPackedOverlay,isTray);
            if (isTray){
                renderTray(entity,direction,poseStack,pBuffer,pPackedOverlay);
            }
        }
    }

    private void renderItem(ItemStack stack, MixBlockEntity entity, Direction direction, Vec2 position, PoseStack poseStack, MultiBufferSource pBuffer, int pPackedLight, int pPackedOverlay,boolean isTray) {
        boolean isBlock = stack.getItem() instanceof BlockItem;
        int posLong = (int) entity.getBlockPos().asLong();
        float rotation = -direction.toYRot();
        poseStack.pushPose();
        Vec2 transformedPosition = transformPositionByDirection(position, direction);
        poseStack.translate(transformedPosition.x, 0.125 + (isTray ? 0.0625 : 0), transformedPosition.y);
        poseStack.mulPose(Axis.YP.rotationDegrees(rotation - 15));
        poseStack.mulPose(Axis.XP.rotationDegrees(0));
        float size = 0.55f;
        poseStack.scale(size, size, size);
        if (entity.getLevel() != null) {
            if (isBlock) {
                BlockState state = ((BlockItem) stack.getItem()).getBlock().defaultBlockState();
                BakedModel model = Minecraft.getInstance().getBlockRenderer().getBlockModel(state);
                Minecraft.getInstance().getItemRenderer().render(stack, ItemDisplayContext.FIXED, false, poseStack, pBuffer, LevelRenderer.getLightColor(entity.getLevel(), entity.getBlockPos()), pPackedOverlay, model);
            } else {
                Minecraft.getInstance().getItemRenderer().renderStatic(stack, ItemDisplayContext.FIXED, LevelRenderer.getLightColor(entity.getLevel(), entity.getBlockPos()), pPackedOverlay, poseStack, pBuffer, entity.getLevel(), (int) (posLong + 1));
            }
        }
        poseStack.popPose();
    }

    private void renderTray(MixBlockEntity entity, Direction direction,PoseStack poseStack, MultiBufferSource pBuffer, int pPackedOverlay){
        BlockState state = BakeriesBlocks.WOOD_TRAY.get().defaultBlockState();
        poseStack.pushPose();
//        poseStack.translate(0f, 0.0625f, 0f);
//        poseStack.mulPose(Axis.YP.rotationDegrees(-direction.toYRot()));
        poseStack.scale(1f,1f,1f);
        Minecraft.getInstance().getBlockRenderer().renderSingleBlock(state,poseStack,pBuffer,LevelRenderer.getLightColor(entity.getLevel(), entity.getBlockPos()),pPackedOverlay);
        poseStack.popPose();
    }

    private Vec2 transformPositionByDirection(Vec2 position, Direction direction) {
        float x = position.x;
        float y = position.y;
        return switch (direction) {
            case NORTH -> new Vec2(x, y);
            case SOUTH -> new Vec2(1 - x, 1 - y);
            case EAST -> new Vec2(y, 1 - x);
            case WEST -> new Vec2(1 - y, x);
            default -> position;
        };
    }
}
