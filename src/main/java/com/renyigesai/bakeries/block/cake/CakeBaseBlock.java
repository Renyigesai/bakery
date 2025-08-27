package com.renyigesai.bakeries.block.cake;

import com.renyigesai.bakeries.api.block.BCakeBlock;
import com.renyigesai.bakeries.api.block.IKnifeCutBlock;
import com.renyigesai.bakeries.init.BakeriesItems;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class CakeBaseBlock extends BCakeBlock implements IKnifeCutBlock {
    public CakeBaseBlock() {
        super(BlockBehaviour.Properties.copy(Blocks.CAKE).lightLevel((l) -> 1));
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return box(3.0, 0.0, 3.0, 13.0, 6.0, 13.0);
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
//            pLevel.removeBlock(pPos,false);
//            ItemUtil.spawnItemEntity(pLevel,new ItemStack(BakeriesItems.CUT_CAKE_BASE.get(),2),pPos);
//            pLevel.playSound(null, pPos, SoundEvents.WOOL_BREAK, SoundSource.PLAYERS, 0.8F, 0.8F);
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
