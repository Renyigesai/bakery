package com.renyigesai.bakeries.block.cake;

import com.renyigesai.bakeries.api.LazyMobEffectInstance;
import com.renyigesai.bakeries.api.block.BCakeBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;

import java.util.List;

public class InstanceCakeBlock extends BCakeBlock {
    public InstanceCakeBlock(int foodLevelModifier, float saturationLevelModifier) {
        super(BlockBehaviour.Properties.copy(Blocks.CAKE).lightLevel((l) -> 1), foodLevelModifier, saturationLevelModifier);
    }

    public InstanceCakeBlock(List<LazyMobEffectInstance> effects, int foodLevelModifier, float saturationLevelModifier) {
        super(BlockBehaviour.Properties.copy(Blocks.CAKE).lightLevel((l) -> 1), effects, foodLevelModifier, saturationLevelModifier);
    }
}
