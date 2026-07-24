package com.renyigesai.bakeries.client.render.block;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import com.renyigesai.bakeries.block.magnetic_plate.MagneticPlateBlockEntity;
import com.renyigesai.bakeries.client.model.MagneticPlateModel;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import org.jetbrains.annotations.NotNull;

public class MagneticPlateRenderer implements BlockEntityRenderer<MagneticPlateBlockEntity> {

    private final MagneticPlateModel<?> model;
    public static final ResourceLocation DEFAULT_TEXTURE = new ResourceLocation("bakeries","textures/block/magnetic_plate.png");

    public MagneticPlateRenderer(BlockEntityRendererProvider.Context pContext) {
        this.model = new MagneticPlateModel<>(pContext.bakeLayer(MagneticPlateModel.MAGNETIC_PLATE));
    }

    @Override
    public void render(MagneticPlateBlockEntity tile, float v, PoseStack poseStack, MultiBufferSource buffer, int packedLight, int packedOverlay) {
        renderBlock(tile, poseStack, buffer, packedLight, packedOverlay);
        for (int i = 0; i < tile.getItems().getSlots(); i++) {
            ItemStack stackInSlot = tile.getItems().getStackInSlot(i);
            if (!stackInSlot.isEmpty()){
                renderTool(stackInSlot,tile,poseStack,buffer,packedLight,packedOverlay,i);
            }
        }
    }

    private void renderTool(ItemStack stack,MagneticPlateBlockEntity tile, PoseStack poseStack, MultiBufferSource buffer, int packedLight, int packedOverlay,int slot) {
        float u,v;
        if (slot == 0){
            u = tile.getXyo()[0];
            v = tile.getXyo()[1];
        }else {
            u = tile.getXyo()[2];
            v = tile.getXyo()[3];
        }
        Direction facing = tile.getBlockState().getValue(BlockStateProperties.HORIZONTAL_FACING);
        int posLong = (int) tile.getBlockPos().asLong();
        poseStack.pushPose();
        switch (facing){
            case SOUTH -> {
                poseStack.mulPose(Axis.YP.rotationDegrees(180));
                poseStack.translate(-u,v,-0.9375);
            }
            case NORTH -> {
                poseStack.translate(1 - u,v,0.0625);
            }
            case WEST -> {
                poseStack.mulPose(Axis.YP.rotationDegrees(90));
                poseStack.translate(-u,v,0.0625);
            }
            case EAST -> {
                poseStack.mulPose(Axis.YP.rotationDegrees(-90));
                poseStack.translate(1 - u,v,-0.9375);
            }
        }
        poseStack.scale(0.5f,0.5f,0.5f);
        if (tile.getLevel() != null) {
            Minecraft.getInstance().getItemRenderer().renderStatic(
                    stack,
                    ItemDisplayContext.NONE,
                    LevelRenderer.getLightColor(tile.getLevel(), tile.getBlockPos()),
                    packedOverlay,
                    poseStack,
                    buffer,
                    tile.getLevel(),
                    (int) (posLong + 1)
            );
        }
        poseStack.popPose();
    }

    private void renderBlock(MagneticPlateBlockEntity tile, PoseStack poseStack, MultiBufferSource buffer, int packedLight, int packedOverlay){
        String blockId = tile.getBlockId();

        if (blockId == null) {
            return;
        }

        Block block = BuiltInRegistries.BLOCK.get(ResourceLocation.tryParse(blockId));
        ResourceLocation textureName;
        if (block == Blocks.AIR) {
            textureName = DEFAULT_TEXTURE;
        }else {
            ResourceLocation resourceLocation = Minecraft.getInstance().getBlockRenderer().getBlockModel(block.defaultBlockState()).getParticleIcon().contents().name();
            textureName = new ResourceLocation(resourceLocation.getNamespace(), "textures/" + resourceLocation.getPath() + ".png");
        }
        Direction direction = tile.getBlockState().getValue(BlockStateProperties.HORIZONTAL_FACING).getOpposite();
        poseStack.pushPose();


        // 1. 移动到方块中心
        poseStack.translate(0.5, 0.5, 0.5);
        poseStack.mulPose(Axis.YP.rotationDegrees(-direction.toYRot()));
        poseStack.translate(0, 0.5, -0.5);

        poseStack.scale(1, 1, 1);

        if (textureName != null){
            ResourceManager rm = Minecraft.getInstance().getResourceManager();
            if (rm.getResource(textureName).isEmpty()){
                textureName = DEFAULT_TEXTURE;
            }
            VertexConsumer original = buffer.getBuffer(RenderType.entityCutoutNoCull(textureName));;
            VertexConsumer flipped = getVertexConsumer(original);
            model.renderToBuffer(poseStack,flipped,packedLight,packedOverlay,1.0F, 1.0F, 1.0F, 1.0F);
        }

        poseStack.popPose();
    }

    private @NotNull VertexConsumer getVertexConsumer(VertexConsumer original) {
        return new VertexConsumer() {
            @Override
            public VertexConsumer vertex(double x, double y, double z) { original.vertex(x,y,z); return this; }
            @Override
            public VertexConsumer color(int r, int g, int b, int a) { original.color(r,g,b,a); return this; }
            @Override
            public VertexConsumer uv(float u, float v) {
                return original.uv(1.0f - v, u);
            }
            @Override
            public VertexConsumer overlayCoords(int u, int v) { original.overlayCoords(u,v); return this; }
            @Override
            public VertexConsumer uv2(int u, int v) { original.uv2(u,v); return this; }
            @Override
            public VertexConsumer normal(float x, float y, float z) { original.normal(x,y,z); return this; }
            @Override
            public void endVertex() { original.endVertex(); }
            @Override
            public void defaultColor(int r, int g, int b, int a) { original.defaultColor(r,g,b,a); }
            @Override
            public void unsetDefaultColor() { original.unsetDefaultColor(); }
        };
    }



}
