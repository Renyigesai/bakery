package com.renyigesai.bakeries.client.render.block;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import com.renyigesai.bakeries.BakeriesMod;
import com.renyigesai.bakeries.block.HorizontalConnectBlock;
import com.renyigesai.bakeries.block.bread_rack.BreadRackBlockEntity;
import com.renyigesai.bakeries.block.bread_rack.GlassBreadRackBlock;
import com.renyigesai.bakeries.client.model.GlassBreadRackDoorModel;
import com.renyigesai.bakeries.util.measurer.ClientUtilsMeasurer;
import com.renyigesai.bakeries.util.measurer.IUtilsMeasurer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
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
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class BreadRackRender implements IBBlockEntityRenderer<BreadRackBlockEntity> {
    private final GlassBreadRackDoorModel<?> model;
    public static final ResourceLocation TEXTURE = new ResourceLocation("bakeries", "textures/entity/glass_bread_rack_door/glass_bread_rack_door.png");
    public static final ResourceLocation TEXTURE_LEFT = new ResourceLocation("bakeries", "textures/entity/glass_bread_rack_door/glass_bread_rack_door_left.png");
    public static final ResourceLocation TEXTURE_RIGHT = new ResourceLocation("bakeries", "textures/entity/glass_bread_rack_door/glass_bread_rack_door_right.png");
    public static final ResourceLocation TEXTURE_ALL = new ResourceLocation("bakeries", "textures/entity/glass_bread_rack_door/glass_bread_rack_door_all.png");
    public static final float ADD_SIZE = 0.25f;
    public static final Vec2[][] VEC2S = {
            new Vec2[]{},
            new Vec2[]{new Vec2(0.5f, 0.5f + 0.125f)},
            new Vec2[]{new Vec2(0.5f - ADD_SIZE, 0.5f + 0.125f), new Vec2(0.5f + ADD_SIZE, 0.5f + 0.125f)},
            new Vec2[]{new Vec2(0.5f - ADD_SIZE, 0.5f + 0.125f), new Vec2(0.5f + ADD_SIZE, 0.5f + 0.125f), new Vec2(0.5f, 0.5f - 0.125f)},
            new Vec2[]{new Vec2(0.5f - ADD_SIZE, 0.5f + 0.125f), new Vec2(0.5f + ADD_SIZE, 0.5f + 0.125f), new Vec2(0.5f - ADD_SIZE, 0.5f - 0.125f), new Vec2(0.5f + ADD_SIZE, 0.5f - 0.125f)}
    };

    public BreadRackRender(BlockEntityRendererProvider.Context context) {
        this.model = new GlassBreadRackDoorModel<>(context.bakeLayer(GlassBreadRackDoorModel.LAYER_LOCATION));
    }

    @Override
    public void startRender(@NotNull BreadRackBlockEntity entity, float v, @NotNull PoseStack poseStack, @NotNull MultiBufferSource pBuffer, int pPackedLight, int pPackedOverlay) {
        renderDoor(entity, v, poseStack, pBuffer, pPackedLight, pPackedOverlay);

        if (entity.isEmpty()) {
            return;
        }

        int count = entity.getItemsCount();
        if (count <= 0 || count >= VEC2S.length) {
            return;
        }

        List<ItemStack> itemsToRender = new ArrayList<>();
        for (int i = 0; i < entity.getItems().getSlots(); i++) {
            itemsToRender.add(entity.getItems().getStackInSlot(i));
        }

        Vec2[] positions = VEC2S[4];
        for (int i = 0; i < itemsToRender.size(); i++) {
            renderItem(itemsToRender.get(i), entity, positions[i], poseStack, pBuffer, pPackedLight, pPackedOverlay, i);
        }
    }

    private void renderItem(ItemStack stack, BreadRackBlockEntity entity, Vec2 position, PoseStack poseStack, MultiBufferSource pBuffer, int pPackedLight, int pPackedOverlay, int slot) {
        if (stack.isEmpty()) {
            return;
        }

        poseStack.pushPose();
        poseStack.translate(position.x - 0.5f, 0.1875f + (slot > 1 ? 0.453125f : 0f), position.y - 0.5f);
        poseStack.mulPose(Axis.YP.rotationDegrees(15));
        float size = 0.55f;
        poseStack.scale(size, size, size);

        Level level = entity.getLevel();
        if (level != null) {
            IUtilsMeasurer utilsMeasurer = BakeriesMod.utilsMeasurer;
            if (utilsMeasurer instanceof ClientUtilsMeasurer measurer){
                if (measurer.isItem3D(stack)){
                    BlockState state = ((BlockItem) stack.getItem()).getBlock().defaultBlockState();
                    BakedModel model = Minecraft.getInstance().getBlockRenderer().getBlockModel(state);
                    Minecraft.getInstance().getItemRenderer().render(stack, ItemDisplayContext.FIXED, false, poseStack, pBuffer, LevelRenderer.getLightColor(level, entity.getBlockPos()), pPackedOverlay, model);
                }else {
                    poseStack.pushPose();
                    poseStack.translate(0f,-0.21875,0f);
                    poseStack.mulPose(Axis.XP.rotationDegrees(-90));
                    Minecraft.getInstance().getItemRenderer().renderStatic(stack, ItemDisplayContext.FIXED, LevelRenderer.getLightColor(level, entity.getBlockPos()), pPackedOverlay, poseStack, pBuffer, level, (int) entity.getBlockPos().asLong());
                    poseStack.popPose();
                }
            }
        }
        poseStack.popPose();
    }

    private void renderDoor(BreadRackBlockEntity rack, float pPartialTick, PoseStack poseStack, MultiBufferSource multiBufferSource, int i, int i1) {
        if (!rack.getBlockState().hasProperty(GlassBreadRackBlock.OPEN)) {
            return;
        }

        poseStack.pushPose();
        poseStack.translate(0f, 1.5f, 0f);
        poseStack.mulPose(Axis.XP.rotationDegrees(180F));
        poseStack.scale(0.9995F, 0.9995F, 0.9995F);

        ResourceLocation location;
        HorizontalConnectBlock.Type type = rack.getBlockState().getValue(HorizontalConnectBlock.TYPE);
        switch (type) {
            case ALL -> location = TEXTURE_ALL;
            case LEFT -> location = TEXTURE_LEFT;
            case RIGHT -> location = TEXTURE_RIGHT;
            default -> location = TEXTURE;
        }
        VertexConsumer vertexConsumer = multiBufferSource.getBuffer(RenderType.entityCutoutNoCull(location));

        float progress = rack.getProgress(pPartialTick);
        Direction direction = rack.getBlockState().getValue(BlockStateProperties.HORIZONTAL_FACING).getOpposite();
        float doorX = getLeftByBlock(rack.getLevel(), rack.getBlockPos(), direction.getOpposite()).isAir() ? -12F : 12F;
        this.model.getAll().x = -progress * doorX;

        this.model.renderToBuffer(poseStack, vertexConsumer, i, i1, 1.0F, 1.0F, 1.0F, 1.0F);
        poseStack.popPose();
    }

    protected BlockState getLeftByBlock(Level level, BlockPos pos, Direction direction) {
        return level.getBlockState(pos.relative(direction.getCounterClockWise()));
    }
}