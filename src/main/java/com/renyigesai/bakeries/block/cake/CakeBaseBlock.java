package com.renyigesai.bakeries.block.cake;

import com.renyigesai.bakeries.api.block.IKnifeCutBlock;
import com.renyigesai.bakeries.init.BakeriesBlocks;
import com.renyigesai.bakeries.init.BakeriesItems;
import com.renyigesai.bakeries.util.ItemUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class CakeBaseBlock extends InstanceCakeBlock implements IKnifeCutBlock {


    public CakeBaseBlock() {
        super(5,0.4F);
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return box(3.0, 0.0, 3.0, 13.0, 8.0, 13.0);
    }

    @Override
    public float getSaturationLevelModifier() {
        return 0.4f;
    }

    @Override
    public int getFoodLevelModifier() {
        return 5;
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        if (pLevel.isClientSide) {
            return cutOrEat(pState, pLevel, pPos, pPlayer, pHand);
        }

        return cutOrEat(pState, pLevel, pPos, pPlayer, pHand);
    }

    public InteractionResult cutOrEat(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand){
        int bites = pState.getValue(BITES);
        ItemStack hand = pPlayer.getItemInHand(pHand);
        if (this.isKnifeItem(hand)){
            if (bites != 0){
                return eat(pLevel,pPos,pState,pPlayer);
            }
            cut(pLevel,pState,pPos,pPlayer,hand,pHand);
            return InteractionResult.SUCCESS;
        }
        if (hand.is(BakeriesItems.MASHED_TARO.get()) && hand.getCount() >= 4){
            pLevel.setBlock(pPos, BakeriesBlocks.TARO_CAKE.get().defaultBlockState(),3);
            ItemUtils.shrink(hand,4,pPlayer);
            return InteractionResult.SUCCESS;
        }

        return eat(pLevel,pPos,pState,pPlayer);
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
}
