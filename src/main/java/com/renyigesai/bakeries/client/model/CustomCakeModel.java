package com.renyigesai.bakeries.client.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.renyigesai.bakeries.item.CustomCakeItem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.ItemOverrides;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.client.model.data.ModelData;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.*;

public class CustomCakeModel implements BakedModel {
    private final Map<Direction, List<BakedQuad>> faceQuads = new EnumMap<>(Direction.class);
    private final List<BakedQuad> genQuads = new ArrayList<>();
    private final BakedModel originalModel;
    private final ItemOverrides itemOverrides = new ItemOverrides() {
        @Nonnull
        @Override
        public BakedModel resolve(BakedModel model, ItemStack stack, @Nullable ClientLevel level, @Nullable LivingEntity entity, int seed) {
            CompoundTag nbt = stack.getOrCreateTag();
            if (nbt.contains("PartId")) {
                List<String> contents = CustomCakeItem.getPartIds(stack);
                if (!contents.isEmpty()) {
                    return new CompositeCakeModel(originalModel,contents);
                }
            }
            return originalModel;
        }
    };

    public CustomCakeModel(BakedModel originalModel) {
        this.originalModel = originalModel;
    }

    public static class CompositeCakeModel implements BakedModel{

        private final BakedModel model;
        private final List<BakedQuad> genQuads;
        private final Map<Direction, List<BakedQuad>> faceQuads;
        public CompositeCakeModel(BakedModel model, List<String> partIds) {
            this.model = model;
            this.genQuads = new ArrayList<>();
            this.faceQuads = new EnumMap<>(Direction.class);

            for (Direction dir : Direction.values()) faceQuads.put(dir, new ArrayList<>());
            for (String partId : partIds) {
                ResourceLocation resourceLocation = new ResourceLocation(partId);
                ResourceLocation modelId = new ResourceLocation(resourceLocation.getNamespace(), "cake_part/" + resourceLocation.getPath());// bakeries:cake_part/cake_base
                BakedModel partModel = Minecraft.getInstance().getModelManager().getModel(modelId);
                if (partModel == Minecraft.getInstance().getModelManager().getMissingModel()) continue;
                PoseStack pose = new PoseStack();
                List<BakedQuad> quads = new ArrayList<>();
                quads.addAll(partModel.getQuads(null, null, RandomSource.create(), ModelData.EMPTY, null));
                for (Direction dir : Direction.values())
                    quads.addAll(partModel.getQuads(null, dir, RandomSource.create(), ModelData.EMPTY, null));

                for (BakedQuad quad : quads) {
                    BakedQuad transformed = transformQuad(quad, pose, true);
                    if (quad.getDirection() == null)
                        genQuads.add(transformed);
                    else
                        faceQuads.get(quad.getDirection()).add(transformed);
                }
            }
        }

        @Override
        public List<BakedQuad> getQuads(@Nullable BlockState state, @Nullable Direction side, RandomSource rand) {
            return getQuads(state, side, rand, ModelData.EMPTY, null);
        }

        @Nonnull
        @Override
        public List<BakedQuad> getQuads(@Nullable BlockState state, @Nullable Direction side, @Nonnull RandomSource rand, ModelData data, @Nullable RenderType renderType) {
            return side == null ? genQuads : faceQuads.getOrDefault(side, Collections.emptyList());
        }

        @Override
        public boolean useAmbientOcclusion() {
            return model.useAmbientOcclusion();
        }

        @Override
        public boolean isGui3d() {
            return model.isGui3d();
        }

        @Override
        public boolean usesBlockLight() {
            return model.usesBlockLight();
        }

        @Override
        public boolean isCustomRenderer() {
            return model.isCustomRenderer();
        }

        @Override
        public TextureAtlasSprite getParticleIcon() {
            return model.getParticleIcon();
        }

        @Override
        public ItemOverrides getOverrides() {
            return ItemOverrides.EMPTY;
        }

        @Override
        public ItemTransforms getTransforms() {
            return model.getTransforms();
        }
    }



    private static BakedQuad transformQuad(BakedQuad quad, PoseStack pose, boolean keepLighting) {
        int[] vertexData = quad.getVertices().clone();
        var matrix = pose.last().pose();
        for (int i = 0; i < 4; i++) {
            int offset = i * 8;
            float x = Float.intBitsToFloat(vertexData[offset]);
            float y = Float.intBitsToFloat(vertexData[offset + 1]);
            float z = Float.intBitsToFloat(vertexData[offset + 2]);
            var vec = new org.joml.Vector4f(x, y, z, 1);
            vec.mul(matrix);
            vertexData[offset] = Float.floatToRawIntBits(vec.x());
            vertexData[offset + 1] = Float.floatToRawIntBits(vec.y());
            vertexData[offset + 2] = Float.floatToRawIntBits(vec.z());
        }
        return new BakedQuad(vertexData, quad.getTintIndex(), quad.getDirection(), quad.getSprite(), quad.isShade());
    }

    @Override
    public boolean useAmbientOcclusion() {
        return originalModel.useAmbientOcclusion();
    }

    @Override
    public boolean isGui3d() {
        return originalModel.isGui3d();
    }

    @Override
    public boolean usesBlockLight() {
        return originalModel.usesBlockLight();
    }

    @Override
    public boolean isCustomRenderer() {
        return originalModel.isCustomRenderer();
    }

    @Override
    public TextureAtlasSprite getParticleIcon() {
        return originalModel.getParticleIcon();
    }

    @Override
    public ItemOverrides getOverrides() {
        return itemOverrides;
    }

    @Override
    public List<BakedQuad> getQuads(@Nullable BlockState state, @Nullable Direction side, RandomSource rand) {
        return getQuads(state, side, rand, ModelData.EMPTY, null);
    }

    @Override
    public List<BakedQuad> getQuads(@Nullable BlockState state, @Nullable Direction side, RandomSource rand, ModelData data, @Nullable RenderType renderType) {
        return side == null ? genQuads : faceQuads.getOrDefault(side, Collections.emptyList());
    }

}