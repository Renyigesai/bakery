package com.renyigesai.bakeries.common.blocks.cake;

import com.mojang.serialization.MapCodec;
import com.renyigesai.bakeries.api.LazyMobEffectInstance;
import com.renyigesai.bakeries.api.block.AbstractBCakeBlock;
import com.renyigesai.bakeries.api.block.IKnifeCutBlock;
import com.renyigesai.bakeries.common.init.BakeriesBlocks;
import com.renyigesai.bakeries.common.init.BakeriesItems;
import com.renyigesai.bakeries.common.utils.ItemUtils;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;

import java.util.List;

public class CakeBaseBlock extends AbstractBCakeBlock implements IKnifeCutBlock {
    public CakeBaseBlock(int foodLevelModifier, float saturationLevelModifier) {
        super(CAKE.lightLevel((l) -> 1), foodLevelModifier, saturationLevelModifier);
    }

    public CakeBaseBlock(List<LazyMobEffectInstance> effects, int foodLevelModifier, float saturationLevelModifier) {
        super(CAKE.lightLevel((l) -> 1), effects,foodLevelModifier, saturationLevelModifier);
    }

    public CakeBaseBlock(Properties properties) {
        super(CAKE.lightLevel((l) -> 1), ItemUtils.ofEffects(), 0, 0);
    }

    @Override
    protected MapCodec<? extends HorizontalDirectionalBlock> codec() {
        return simpleCodec(CakeBaseBlock::new);
    }

    @Override
    public Property<Integer> getSliceProperty() {
        return null;
    }

    @Override
    public int getMaxSlice() {
        return 4;
    }

    @Override
    public int getSliceItemCount() {
        return 2;
    }

    @Override
    public Item getSliceItem() {
        return BakeriesItems.CUT_CAKE_BASE.get();
    }

    @Override
    public boolean isCut(BlockState state) {
        return state.getValue(BITES) == 0 && state.is(BakeriesBlocks.CAKE_BASE);
    }

}
