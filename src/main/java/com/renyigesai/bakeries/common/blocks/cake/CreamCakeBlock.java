package com.renyigesai.bakeries.common.blocks.cake;

import com.mojang.serialization.MapCodec;
import com.renyigesai.bakeries.api.LazyMobEffectInstance;
import com.renyigesai.bakeries.api.block.AbstractBCakeBlock;
import com.renyigesai.bakeries.common.init.BakeriesBlocks;
import com.renyigesai.bakeries.common.init.BakeriesItems;
import com.renyigesai.bakeries.common.utils.ItemUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

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
    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        ItemStack itemInHand = player.getItemInHand(hand);
        int bites = state.getValue(BITES);
        if (itemInHand.is(BakeriesItems.CREAM_CAKE.get()) && bites == 0){
            Direction facing = state.getValue(FACING);
            level.setBlockAndUpdate(pos, BakeriesBlocks.MULTI_LAYER_CREAM_CAKE.get().defaultBlockState().setValue(MultiLayerCreamCakeBlock.FACING,facing));
            if (!player.getAbilities().instabuild){
                itemInHand.shrink(1);
            }
            return ItemInteractionResult.SUCCESS;
        }
        return super.useItemOn(stack, state, level, pos, player, hand, hitResult);
    }

    @Override
    protected MapCodec<? extends HorizontalDirectionalBlock> codec() {
        return simpleCodec(CreamCakeBlock::new);
    }
}
