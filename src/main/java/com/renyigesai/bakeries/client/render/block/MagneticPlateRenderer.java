package com.renyigesai.bakeries.client.render.block;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import com.renyigesai.bakeries.block.magnetic_plate.MagneticPlateBlock;
import com.renyigesai.bakeries.block.magnetic_plate.MagneticPlateBlockEntity;
import com.renyigesai.bakeries.client.model.MagneticPlateModel;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import org.jetbrains.annotations.NotNull;

public class MagneticPlateRenderer implements IBBlockEntityRenderer<MagneticPlateBlockEntity> {

    private final MagneticPlateModel<?> model;
    public static final ResourceLocation DEFAULT_TEXTURE =
            new ResourceLocation("bakeries", "textures/block/magnetic_plate.png");

    public MagneticPlateRenderer(BlockEntityRendererProvider.Context pContext) {
        this.model = new MagneticPlateModel<>(pContext.bakeLayer(MagneticPlateModel.MAGNETIC_PLATE));
    }

    @Override
    public void startRender(@NotNull MagneticPlateBlockEntity tile, float v, @NotNull PoseStack poseStack, @NotNull MultiBufferSource buffer, int packedLight, int packedOverlay) {
        renderBlock(tile, poseStack, buffer, packedLight, packedOverlay);

        for (int i = 0; i < tile.getItems().getSlots(); i++) {
            ItemStack stackInSlot = tile.getItems().getStackInSlot(i);
            if (!stackInSlot.isEmpty()) {
                renderTool(stackInSlot, tile, poseStack, v, buffer, packedLight, packedOverlay, i);
            }
        }
    }

    private void renderTool(ItemStack stack, MagneticPlateBlockEntity tile, PoseStack poseStack, float pPartialTick,MultiBufferSource buffer, int packedLight, int packedOverlay, int slot) {
        float u, v;
        if (slot == 0) {
            u = tile.getXyo()[0];
            v = tile.getXyo()[1];
        } else {
            u = tile.getXyo()[2];
            v = tile.getXyo()[3];
        }

        int posLong = (int) tile.getBlockPos().asLong();

        poseStack.pushPose();
        poseStack.translate(0.5f - u, v, -0.40625f);
        /**特殊渲染农夫乐事的煎锅*/
        if (BuiltInRegistries.ITEM.getKey(stack.getItem()).equals(MagneticPlateBlock.SKILLET)){
            poseStack.scale(1f, 1f, 1f);
            poseStack.mulPose(Axis.ZP.rotationDegrees(-15));
        }else {
            poseStack.scale(0.5f, 0.5f, 0.5f);
        }
        oppositeY(tile,pPartialTick,poseStack,buffer,packedLight,packedOverlay);
        if (tile.getLevel() != null) {
            Minecraft.getInstance().getItemRenderer().renderStatic(
                    stack, ItemDisplayContext.FIXED,
                    LevelRenderer.getLightColor(tile.getLevel(), tile.getBlockPos()),
                    packedOverlay, poseStack, buffer, tile.getLevel(),
                    (int) (posLong + 1));
        }
        poseStack.popPose();
    }

    private void renderBlock(MagneticPlateBlockEntity tile, PoseStack poseStack,
                             MultiBufferSource buffer, int packedLight, int packedOverlay) {
        String blockId = tile.getBlockId();
        if (blockId == null) {
            return;
        }

        Block block = BuiltInRegistries.BLOCK.get(ResourceLocation.tryParse(blockId));
        ResourceLocation textureName;
        if (block == Blocks.AIR) {
            textureName = DEFAULT_TEXTURE;
        } else {
            ResourceLocation resourceLocation = Minecraft.getInstance().getBlockRenderer()
                    .getBlockModel(block.defaultBlockState()).getParticleIcon().contents().name();
            textureName = new ResourceLocation(resourceLocation.getNamespace(),
                    "textures/" + resourceLocation.getPath() + ".png");
        }

        poseStack.pushPose();
        // 基础变换后，原点在方块底部中心；原代码从 (0.5, 0.5, 0.5) 出发再偏移 (0, 0.5, -0.5)
        // 相对中心的等效偏移为 (0, 1.0, -0.5)
        poseStack.translate(0, 1.0f, -0.5f);

        ResourceManager rm = Minecraft.getInstance().getResourceManager();
        if (rm.getResource(textureName).isEmpty()) {
            textureName = DEFAULT_TEXTURE;
        }
        VertexConsumer original = buffer.getBuffer(RenderType.entityCutoutNoCull(textureName));
        VertexConsumer flipped = getVertexConsumer(original, tile.getRotationFlag());
        model.renderToBuffer(poseStack, flipped, packedLight, packedOverlay, 1.0F, 1.0F, 1.0F, 1.0F);

        poseStack.popPose();
    }

    private @NotNull VertexConsumer getVertexConsumer(VertexConsumer original, int rotation) {
        return new VertexConsumer() {
            @Override
            public VertexConsumer vertex(double x, double y, double z) { original.vertex(x, y, z); return this; }
            @Override
            public VertexConsumer color(int r, int g, int b, int a) { original.color(r, g, b, a); return this; }
            @Override
            public VertexConsumer uv(float u, float v) {
                return switch (rotation & 3) {
                    case 0 -> original.uv(u, v);
                    case 1 -> original.uv(1.0f - v, u);
                    case 2 -> original.uv(1.0f - u, 1.0f - v);
                    case 3 -> original.uv(v, 1.0f - u);
                    default -> original.uv(u, v);
                };
            }
            @Override
            public VertexConsumer overlayCoords(int u, int v) { original.overlayCoords(u, v); return this; }
            @Override
            public VertexConsumer uv2(int u, int v) { original.uv2(u, v); return this; }
            @Override
            public VertexConsumer normal(float x, float y, float z) { original.normal(x, y, z); return this; }
            @Override
            public void endVertex() { original.endVertex(); }
            @Override
            public void defaultColor(int r, int g, int b, int a) { original.defaultColor(r, g, b, a); }
            @Override
            public void unsetDefaultColor() { original.unsetDefaultColor(); }
        };
    }
}