package com.renyigesai.bakeries.common.blocks.bread_rack;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import com.renyigesai.bakeries.common.blocks.HorizontalConnectBlock;
import com.renyigesai.bakeries.common.client.model.GlassBreadRackDoorModel;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.Vec2;

import java.util.ArrayList;
import java.util.List;

public class BreadRackRender implements BlockEntityRenderer<BreadRackBlockEntity> {
    private final GlassBreadRackDoorModel<?> model;
    public static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath("bakeries","textures/entity/glass_bread_rack_door/glass_bread_rack_door.png");
    public static final ResourceLocation TEXTURE_LEFT = ResourceLocation.fromNamespaceAndPath("bakeries","textures/entity/glass_bread_rack_door/glass_bread_rack_door_left.png");
    public static final ResourceLocation TEXTURE_RIGHT = ResourceLocation.fromNamespaceAndPath("bakeries","textures/entity/glass_bread_rack_door/glass_bread_rack_door_right.png");
    public static final ResourceLocation TEXTURE_ALL = ResourceLocation.fromNamespaceAndPath("bakeries","textures/entity/glass_bread_rack_door/glass_bread_rack_door_all.png");
    public static final float ADD_SIZE = 0.25f;
    public static final Vec2[][] VEC2S = {
            new Vec2[]{},
            new Vec2[]{new Vec2(0.5f, 0.5f + 0.125f)},
            new Vec2[]{new Vec2(0.5f - ADD_SIZE, 0.5f + 0.125f), new Vec2(0.5f + ADD_SIZE, 0.5f + 0.125f)},
            new Vec2[]{new Vec2(0.5f - ADD_SIZE, 0.5f + 0.125f), new Vec2(0.5f + ADD_SIZE, 0.5f + 0.125f), new Vec2(0.5f, 0.5f - 0.125f)},
            new Vec2[]{new Vec2(0.5f - ADD_SIZE, 0.5f + 0.125f), new Vec2(0.5f + ADD_SIZE, 0.5f + 0.125f),new Vec2(0.5f - ADD_SIZE, 0.5f  - 0.125f), new Vec2(0.5f + ADD_SIZE, 0.5f  - 0.125f)}
    };

    public BreadRackRender(BlockEntityRendererProvider.Context context) {
        this.model = new GlassBreadRackDoorModel<>(context.bakeLayer(GlassBreadRackDoorModel.LAYER_LOCATION));
    }

    @Override
    public void render(BreadRackBlockEntity entity, float pPartialTick, PoseStack poseStack, MultiBufferSource pBuffer, int pPackedLight, int pPackedOverlay) {
        renderDoor(entity,pPartialTick,poseStack,pBuffer,pPackedLight,pPackedOverlay);
        if (entity.isEmpty()) {
            return;
        }
        Direction direction = entity.getBlockState().getValue(BlockStateProperties.HORIZONTAL_FACING).getOpposite();
        int count = entity.getItemsCount();
        if (count <= 0 || count >= VEC2S.length) {
            return;
        }
//        Vec2[] positions = VEC2S[count];
        List<ItemStack> itemsToRender = new ArrayList<>();
        for (int i = 0; i < entity.getItems().getSlots(); i++) {
            ItemStack stack = entity.getItems().getStackInSlot(i);
            if (!stack.isEmpty()) {
                itemsToRender.add(stack);
            }else {
                itemsToRender.add(ItemStack.EMPTY);
            }
        }
        Vec2[] positions = VEC2S[4];
        for (int i = 0; i < itemsToRender.size(); i++) {
//            if (i >= positions.length) {
//                break;
//            }
            ItemStack stack = itemsToRender.get(i);
            Vec2 position = positions[i];
            renderItem(stack, entity, direction, position, poseStack, pBuffer, pPackedLight, pPackedOverlay,i);
        }
    }


    private void renderItem(ItemStack stack, BreadRackBlockEntity entity, Direction direction, Vec2 position, PoseStack poseStack, MultiBufferSource pBuffer, int pPackedLight, int pPackedOverlay, int slot) {
        boolean isBlock = stack.getItem() instanceof BlockItem;
        int posLong = (int) entity.getBlockPos().asLong();
        float rotation = -direction.toYRot();
        poseStack.pushPose();
        Vec2 transformedPosition = transformPositionByDirection(position, direction);
        poseStack.translate(transformedPosition.x, 0.1875 + (slot > 1 ? 0.453125 : 0), transformedPosition.y);

        poseStack.mulPose(Axis.YP.rotationDegrees(rotation + 15));
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

    private void renderDoor(BreadRackBlockEntity rack, float pPartialTick, PoseStack poseStack, MultiBufferSource multiBufferSource, int packedLight, int packedOverlay){
        if (!rack.getBlockState().hasProperty(GlassBreadRackBlock.OPEN)){
            return;
        }
        Direction direction = rack.getBlockState().getValue(BlockStateProperties.HORIZONTAL_FACING).getOpposite();
        poseStack.pushPose();
        poseStack.translate(0.5F, 1.5F, 0.5F);
        poseStack.mulPose(Axis.YP.rotationDegrees(-direction.toYRot()));
        poseStack.mulPose(Axis.XP.rotationDegrees(180F));
        poseStack.scale(0.9995F, 0.9995F, 0.9995F);

        ResourceLocation location;
        HorizontalConnectBlock.Type type = rack.getBlockState().getValue(HorizontalConnectBlock.TYPE);
        switch (type){
            case ALL -> location = TEXTURE_ALL;
            case LEFT -> location = TEXTURE_LEFT;
            case RIGHT -> location = TEXTURE_RIGHT;
            default -> location = TEXTURE;
        }
        VertexConsumer vertexConsumer = multiBufferSource.getBuffer(RenderType.entityCutoutNoCull(location));
        float progress = rack.getProgress(pPartialTick);
        float doorX = getLeftByBlock(rack.getLevel(),rack.getBlockPos(),direction.getOpposite()).isAir() ? -12F : 12F;
        this.model.getAll().x = -progress * doorX;
        this.model.renderToBuffer(poseStack, vertexConsumer,packedLight,packedOverlay);
        poseStack.popPose();
    }

    private Vec2 transformPositionByDirection(Vec2 position, Direction direction) {
        float x = position.x;
        float y = position.y;
        return switch (direction) {
            case NORTH -> new Vec2(1 - x, 1 - y);
            case SOUTH -> new Vec2(x, y);
            case EAST -> new Vec2(y, 1 - x);
            case WEST -> new Vec2(1 - y, x);
            default -> position;
        };
    }

    protected BlockState getLeftByBlock(Level level, BlockPos pos, Direction direction){
        return level.getBlockState(pos.relative(direction.getCounterClockWise()));
    }
}
