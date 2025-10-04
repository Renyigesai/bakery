package com.renyigesai.bakeries.block.cake;

import com.renyigesai.bakeries.init.BakeriesBlocks;
import com.renyigesai.bakeries.init.BakeriesItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

public class RedVelvetCakeBaseBlock extends CakeBaseBlock{

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        ItemStack itemInHand = pPlayer.getItemInHand(pHand);
        if (itemInHand.is(BakeriesItems.CHEESE_CREAM.get()) && pState.getValue(BITES) == 0){
            Direction value = pState.getValue(FACING);
            pLevel.setBlock(pPos, BakeriesBlocks.RED_VELVET_CAKE.get().defaultBlockState().setValue(FACING,value), 3);
            return InteractionResult.SUCCESS;
        }else {
            return cutOrEat(pState, pLevel, pPos, pPlayer, pHand);
        }
    }
}
