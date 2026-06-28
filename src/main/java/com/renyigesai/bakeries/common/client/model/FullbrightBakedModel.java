package com.renyigesai.bakeries.common.client.model;

import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.ItemOverrides;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.client.ChunkRenderTypeSet;
import net.neoforged.neoforge.client.model.data.ModelData;
// 注意这里导入的是 ChunkRenderTypeSet
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class FullbrightBakedModel implements BakedModel {
    private final BakedModel original;

    public FullbrightBakedModel(BakedModel original) {
        this.original = original;
    }

    /**
     * 修改单个 BakedQuad 的光照值为全亮
     * 对于1.21.1，发光效果的实现与旧版本一致，都需要将顶点数据中的光照值设为最大值
     */
    private BakedQuad modifyQuadLight(BakedQuad quad) {
        // 克隆原始顶点数据
        int[] vertexData = quad.getVertices().clone();

        // 将每个顶点的光照信息设置为全亮度
        int vertices = vertexData.length / 8; // 每个顶点有8个整数
        for (int i = 0; i < vertices; i++) {
            // 光照数据在每个顶点的第7个整数（索引从0开始，所以是6）
            // 对应旧版本中的 lightOffset 计算：i * 8 + 6
            int lightOffset = i * 8 + 6;
            vertexData[lightOffset] = LightTexture.FULL_BRIGHT;
        }

        // 在1.21.1中，构造 BakedQuad 可能需要6个参数
        // 确保传入原始的着色、环境光遮蔽等属性
        return new BakedQuad(vertexData, quad.getTintIndex(), quad.getDirection(),
                quad.getSprite(), quad.isShade(), quad.hasAmbientOcclusion());
    }

    @Override
    public List<BakedQuad> getQuads(@Nullable BlockState state, @Nullable Direction side, RandomSource rand, ModelData data, @Nullable RenderType renderType) {
        List<BakedQuad> originalQuads = this.original.getQuads(state, side, rand,data,renderType);
        if (originalQuads.isEmpty()) return originalQuads;

        List<BakedQuad> newQuads = new ArrayList<>(originalQuads.size());
        for (BakedQuad quad : originalQuads) {
            newQuads.add(modifyQuadLight(quad));
        }
        return newQuads;
    }

    // ==================== 确保透明效果的关键 ====================
    // 在1.21.1中，为了保持模型的透明属性，必须重写getRenderTypes并委托给原模型
    // 注意这里返回的是 ChunkRenderTypeSet，而不是 List<RenderType>

    @Override
    public ChunkRenderTypeSet getRenderTypes(BlockState state, RandomSource rand, ModelData data) {
        return this.original.getRenderTypes(state, rand, data);
    }

    @Override
    public List<BakedQuad> getQuads(@Nullable BlockState blockState, @Nullable Direction direction, RandomSource randomSource) {
        List<BakedQuad> originalQuads = this.original.getQuads(blockState, direction, randomSource);
        if (originalQuads.isEmpty()) return originalQuads;

        List<BakedQuad> newQuads = new ArrayList<>(originalQuads.size());
        for (BakedQuad quad : originalQuads) {
            newQuads.add(modifyQuadLight(quad));
        }
        return newQuads;
    }

    // ==================== 其他方法全部委托给原模型 ====================
    @Override public boolean useAmbientOcclusion() { return original.useAmbientOcclusion(); }
    @Override public boolean isGui3d() { return original.isGui3d(); }
    @Override public boolean usesBlockLight() { return original.usesBlockLight(); }
    @Override public boolean isCustomRenderer() { return original.isCustomRenderer(); }

    @Override
    public TextureAtlasSprite getParticleIcon() {
        return original.getParticleIcon();
    }

    @Override public ItemOverrides getOverrides() { return original.getOverrides(); }
}