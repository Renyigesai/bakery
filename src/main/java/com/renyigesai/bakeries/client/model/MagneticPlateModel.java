package com.renyigesai.bakeries.client.model;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.ItemOverrides;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.client.ChunkRenderTypeSet;
import net.minecraftforge.client.model.data.ModelData;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class MagneticPlateModel implements BakedModel {

    private final BakedModel shapeModel;
    private final BakedModel textureModel;
    private final BlockState textureState;

    public MagneticPlateModel(BakedModel shapeModel, BakedModel textureModel,
                                   BlockState textureState) {
        this.shapeModel = shapeModel;
        this.textureModel = textureModel;
        this.textureState = textureState;
    }

    @Override
    public List<BakedQuad> getQuads(@Nullable BlockState state, @Nullable Direction side,
                                    RandomSource rand, ModelData data, @Nullable RenderType renderType) {
        List<BakedQuad> shapeQuads = shapeModel.getQuads(state, side, rand, data, renderType);
        if (shapeQuads.isEmpty()) {
            return shapeQuads;
        }
        TextureAtlasSprite sprite = pickTextureSprite(side, rand);
        if (sprite == null) {
            return shapeQuads;
        }
        return remapQuads(shapeQuads, sprite);
    }

    @Override
    public List<BakedQuad> getQuads(@Nullable BlockState state, @Nullable Direction side, RandomSource rand) {
        List<BakedQuad> shapeQuads = shapeModel.getQuads(state, side, rand);
        if (shapeQuads.isEmpty()) {
            return shapeQuads;
        }
        TextureAtlasSprite sprite = pickTextureSprite(side, rand);
        if (sprite == null) {
            return shapeQuads;
        }
        return remapQuads(shapeQuads, sprite);
    }

    /** 用 textureState 查询 B 模型，而不是用 A 的 state */
    @Nullable
    private TextureAtlasSprite pickTextureSprite(@Nullable Direction side, RandomSource rand) {
        // 1. 优先按方向查 B 模型
        List<BakedQuad> quads = textureModel.getQuads(textureState, side, rand);
        if (!quads.isEmpty()) {
            return quads.get(0).getSprite();
        }

        // 2. 回退：null 方向
        quads = textureModel.getQuads(textureState, null, rand);
        if (!quads.isEmpty()) {
            return quads.get(0).getSprite();
        }

        // 3. 回退：null state + null 方向
        quads = textureModel.getQuads(null, null, rand);
        if (!quads.isEmpty()) {
            return quads.get(0).getSprite();
        }

        // 4. 最后回退：粒子图标
        TextureAtlasSprite particle = textureModel.getParticleIcon();
        if (particle != null && !particle.atlasLocation().getPath().contains("missingno")) {
            return particle;
        }
        return null;
    }

    private static List<BakedQuad> remapQuads(List<BakedQuad> quads, TextureAtlasSprite to) {
        List<BakedQuad> result = new ArrayList<>(quads.size());
        for (BakedQuad quad : quads) {
            result.add(remapQuad(quad, quad.getSprite(), to));
        }
        return result;
    }

    private static BakedQuad remapQuad(BakedQuad quad, TextureAtlasSprite from, TextureAtlasSprite to) {
        int[] vertices = quad.getVertices().clone();

        float fromU0 = from.getU0(), fromU1 = from.getU1();
        float fromV0 = from.getV0(), fromV1 = from.getV1();
        float fromW = fromU1 - fromU0;
        float fromH = fromV1 - fromV0;

        float toU0 = to.getU0(), toU1 = to.getU1();
        float toV0 = to.getV0(), toV1 = to.getV1();
        float toW = toU1 - toU0;
        float toH = toV1 - toV0;

        for (int i = 0; i < 4; i++) {
            int offset = i * 8 + 4;
            float u = Float.intBitsToFloat(vertices[offset]);
            float v = Float.intBitsToFloat(vertices[offset + 1]);

            float nu = (u - fromU0) / fromW;
            float nv = (v - fromV0) / fromH;

            vertices[offset]     = Float.floatToIntBits(toU0 + nu * toW);
            vertices[offset + 1] = Float.floatToIntBits(toV0 + nv * toH);
        }

        return new BakedQuad(vertices, quad.getTintIndex(), quad.getDirection(),
                to, quad.isShade());
    }

    // 其余委托给 shapeModel（A）
    @Override public boolean useAmbientOcclusion() { return shapeModel.useAmbientOcclusion(); }
    @Override public boolean isGui3d() { return shapeModel.isGui3d(); }
    @Override public boolean usesBlockLight() { return shapeModel.usesBlockLight(); }
    @Override public boolean isCustomRenderer() { return shapeModel.isCustomRenderer(); }
    @Override public TextureAtlasSprite getParticleIcon() { return textureModel.getParticleIcon(); }
    @Override public ItemOverrides getOverrides() { return shapeModel.getOverrides(); }
    @Override public ItemTransforms getTransforms() { return shapeModel.getTransforms(); }
    @Override public ChunkRenderTypeSet getRenderTypes(BlockState state, RandomSource rand, ModelData data) {
        return shapeModel.getRenderTypes(state, rand, data);
    }
    @Override public BakedModel applyTransform(ItemDisplayContext ctx, PoseStack poseStack, boolean leftHand) {
        return shapeModel.applyTransform(ctx, poseStack, leftHand);
    }
}