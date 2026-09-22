package com.renyigesai.bakeries.client.model;

import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.ItemOverrides;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.client.ChunkRenderTypeSet;
import net.minecraftforge.client.model.data.ModelData;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * 把旧模型的几何体保留下来，将 UV 重映射到新的 sprite。
 * 如果 newSprite 为 null，则使用旧模型本身的纹理（等价于原样拷贝）。
 */
public class RemappedTextureBakedModel implements BakedModel {

    private final BakedModel original;
    private final TextureAtlasSprite newSprite; // 目标纹理；null 表示沿用旧纹理

    public RemappedTextureBakedModel(BakedModel original, @Nullable TextureAtlasSprite newSprite) {
        this.original = original;
        this.newSprite = newSprite;
    }

    /** 沿用旧模型的纹理，仅复制一份包装（等价于原样使用） */
    public static RemappedTextureBakedModel copyOf(BakedModel original) {
        return new RemappedTextureBakedModel(original, null);
    }

    /** 用新 sprite 替换旧模型的所有纹理 */
    public static RemappedTextureBakedModel withTexture(BakedModel original, TextureAtlasSprite sprite) {
        return new RemappedTextureBakedModel(original, sprite);
    }

    // ============ 核心：重映射 UV ============
    @Override
    public List<BakedQuad> getQuads(@Nullable BlockState state, @Nullable Direction side,
                                    RandomSource rand, ModelData data, @Nullable RenderType renderType) {
        List<BakedQuad> quads = original.getQuads(state, side, rand, data, renderType);
        return newSprite == null ? quads : remapQuads(quads);
    }

    @Override
    public List<BakedQuad> getQuads(@Nullable BlockState state, @Nullable Direction side, RandomSource rand) {
        List<BakedQuad> quads = original.getQuads(state, side, rand);
        return newSprite == null ? quads : remapQuads(quads);
    }

    private List<BakedQuad> remapQuads(List<BakedQuad> quads) {
        List<BakedQuad> result = new ArrayList<>(quads.size());
        for (BakedQuad quad : quads) {
            result.add(remapQuad(quad, quad.getSprite(), newSprite));
        }
        return result;
    }

    /**
     * 把 quad 的 UV 从 from sprite 映射到 to sprite。
     * Minecraft 顶点格式：每个顶点 8 个 int，UV 位于第 4、5 位。
     */
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

            // 归一化到 [0,1]
            float nu = (u - fromU0) / fromW;
            float nv = (v - fromV0) / fromH;

            // 映射到新 sprite 的图集范围
            vertices[offset]     = Float.floatToIntBits(toU0 + nu * toW);
            vertices[offset + 1] = Float.floatToIntBits(toV0 + nv * toH);
        }

        return new BakedQuad(vertices, quad.getTintIndex(), quad.getDirection(),
                to, quad.isShade());
    }

    // ============ 其余方法全部委托给 original ============
    @Override public boolean useAmbientOcclusion() { return original.useAmbientOcclusion(); }
    @Override public boolean isGui3d() { return original.isGui3d(); }
    @Override public boolean usesBlockLight() { return original.usesBlockLight(); }
    @Override public boolean isCustomRenderer() { return original.isCustomRenderer(); }
    @Override public TextureAtlasSprite getParticleIcon() { return original.getParticleIcon(); }
    @Override public ItemOverrides getOverrides() { return original.getOverrides(); }
    @Override public ItemTransforms getTransforms() { return original.getTransforms(); }
    @Override public ChunkRenderTypeSet getRenderTypes(BlockState state, RandomSource rand, ModelData data) {
        return original.getRenderTypes(state, rand, data);
    }
    @Override public BakedModel applyTransform(net.minecraft.world.item.ItemDisplayContext ctx,
                                               com.mojang.blaze3d.vertex.PoseStack poseStack,
                                               boolean leftHand) {
        return original.applyTransform(ctx, poseStack, leftHand);
    }
    @Override public List<RenderType> getRenderTypes(net.minecraft.world.item.ItemStack stack, boolean fabulous) {
        return original.getRenderTypes(stack, fabulous);
    }
}