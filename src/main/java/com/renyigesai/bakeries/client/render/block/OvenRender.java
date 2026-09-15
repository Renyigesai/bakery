package com.renyigesai.bakeries.client.render.block;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import com.renyigesai.bakeries.BakeriesMod;
import com.renyigesai.bakeries.block.oven.OvenBlock;
import com.renyigesai.bakeries.block.oven.OvenBlockEntity;
import com.renyigesai.bakeries.client.model.OvenModel;
import com.renyigesai.bakeries.init.BakeriesItemTag;
import com.renyigesai.bakeries.util.measurer.ClientUtilsMeasurer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.resources.model.BakedModel;
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

public class OvenRender implements IBBlockEntityRenderer<OvenBlockEntity> {
    private final OvenModel<?> model;
    private final OvenModel<?> glow;
    public static final ResourceLocation TEXTURE = new ResourceLocation("bakeries","textures/entity/oven/oven.png");
    public static final ResourceLocation TEXTURE_LIT = new ResourceLocation("bakeries","textures/entity/oven/oven_lit.png");
    public static final ResourceLocation TEXTURE_GLOW = new ResourceLocation("bakeries","textures/entity/oven/oven_glow.png");
    public static final Vec2[] VEC2S = new Vec2[]{
            new Vec2(-0.2875f,0f),
            new Vec2(-0.0625f,0f),
            new Vec2(0.1625f,0f),
            new Vec2(-0.2875f,0f),
            new Vec2(-0.0625f,0f),
            new Vec2(0.1625f,0f)
    };

    public OvenRender(BlockEntityRendererProvider.Context pContext) {
        this.model = new OvenModel<>(pContext.bakeLayer(OvenModel.OVEN));
        this.glow = model;
    }

    @Override
    public void startRender(@NotNull OvenBlockEntity oven, float pPartialTicks, @NotNull PoseStack poseStack, @NotNull MultiBufferSource multiBufferSource, int pPackedLight, int pPackedOverlay) {
        renderBlock(oven,pPartialTicks,poseStack,multiBufferSource,pPackedLight,pPackedOverlay);
        renderItem(oven,pPartialTicks,poseStack,multiBufferSource,pPackedOverlay);
    }


    private void renderItem(OvenBlockEntity oven, float pPartialTicks,PoseStack poseStack, MultiBufferSource multiBufferSource, int i1){
        int posLong = (int) oven.getBlockPos().asLong();
        for (int slot = 0; slot < oven.getItemHandler().getSlots(); slot++) {
            ItemStack item = oven.getItem(slot);
            if (!item.isEmpty()){
                poseStack.pushPose();
                float y = slot > 2 ? 0.3125f + 0.03125f : 0.5625f + 0.03125f;
                Vec2 vec2 = VEC2S[slot];
                float yp = item.is(BakeriesItemTag.UPRIGHT_ON_OVEN) ? 90f : 0f;
                poseStack.translate(vec2.x, y, vec2.y);
                poseStack.mulPose(Axis.YP.rotationDegrees(yp));
                poseStack.scale(0.35f,0.35f,0.35f);
                renderModel(item,oven,poseStack,multiBufferSource,i1,posLong);
                poseStack.popPose();
            }
        }
    }

    private void renderModel(ItemStack stack,OvenBlockEntity oven, PoseStack poseStack, MultiBufferSource pBuffer, int pPackedOverlay,int posLong){
        Level level = oven.getLevel();
        if (level != null){
            if (BakeriesMod.utilsMeasurer instanceof ClientUtilsMeasurer measurer){
                if (measurer.isItem3D(stack)){
                    BlockState state = ((BlockItem) stack.getItem()).getBlock().defaultBlockState();
                    BakedModel model = Minecraft.getInstance().getBlockRenderer().getBlockModel(state);
                    Minecraft.getInstance().getItemRenderer().render(stack, ItemDisplayContext.FIXED, false, poseStack, pBuffer, LevelRenderer.getLightColor(oven.getLevel(), oven.getBlockPos()), pPackedOverlay, model);
                }else {
                    poseStack.pushPose();
                    poseStack.translate(0f,-0.25,0f);
                    poseStack.mulPose(Axis.XP.rotationDegrees(-90));
                    Minecraft.getInstance().getItemRenderer().renderStatic(stack, ItemDisplayContext.FIXED, LevelRenderer.getLightColor(oven.getLevel(), oven.getBlockPos()), pPackedOverlay, poseStack, pBuffer, oven.getLevel(), (int) (posLong + 1));
                    poseStack.popPose();
                }
            }
        }
    }

    private void renderBlock(OvenBlockEntity oven, float pPartialTick, PoseStack poseStack, MultiBufferSource multiBufferSource, int i, int i1){
        poseStack.pushPose();
        poseStack.translate(0, 1.5F, 0);
        poseStack.mulPose(Axis.XP.rotationDegrees(180F));
        poseStack.scale(0.9995F, 0.9995F, 0.9995F);
        boolean lit = oven.getBlockState().getValue(OvenBlock.LIT);
        VertexConsumer vertexConsumer = multiBufferSource.getBuffer(RenderType.entityCutoutNoCull(lit ? TEXTURE_LIT : TEXTURE));
        this.model.getDoor().xRot = (float) Math.toRadians(oven.getProgress(pPartialTick) * 75);
        this.model.renderToBuffer(poseStack, vertexConsumer, i, i1, 1.0F, 1.0F, 1.0F, 1.0F);
        if (lit){
            VertexConsumer glow = multiBufferSource.getBuffer(RenderType.eyes(TEXTURE_GLOW));
            this.glow.renderToBuffer(poseStack,glow,i, i1, 1.0F, 1.0F, 1.0F, 1.0F);
        }
        poseStack.popPose();
    }

    private Vec2 getVec2(int slot){
        return VEC2S[slot];
    }
}
