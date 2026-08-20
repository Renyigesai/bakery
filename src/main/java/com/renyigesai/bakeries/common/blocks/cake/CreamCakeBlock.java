package com.renyigesai.bakeries.common.blocks.cake;

import com.mojang.serialization.MapCodec;
import com.renyigesai.bakeries.api.LazyMobEffectInstance;
import com.renyigesai.bakeries.api.block.AbstractBCakeBlock;
import com.renyigesai.bakeries.common.utils.ItemUtils;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;

import java.util.List;

public class CreamCakeBlock extends AbstractBCakeBlock {
    public CreamCakeBlock(int foodLevelModifier, float saturationLevelModifier) {
        super(CAKE.lightLevel((l) -> 1), foodLevelModifier, saturationLevelModifier);
    }

    public CreamCakeBlock(List<LazyMobEffectInstance> effects, int foodLevelModifier, float saturationLevelModifier) {
        super(CAKE.lightLevel((l) -> 1), effects, foodLevelModifier, saturationLevelModifier);
    }

    public CreamCakeBlock(Properties properties) {
        super(CAKE.lightLevel((l) -> 1), ItemUtils.ofEffects(), 0, 0);
    }

    @Override
    protected MapCodec<? extends HorizontalDirectionalBlock> codec() {
        return simpleCodec(CreamCakeBlock::new);
    }
}
