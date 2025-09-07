package com.renyigesai.bakeries.block;

import com.renyigesai.bakeries.api.block.PileBlock;
import com.renyigesai.bakeries.init.BakeriesItems;
import com.renyigesai.bakeries.util.ItemUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.BlockHitResult;

public class PaperCupBlock extends PileBlock {
    public static final BooleanProperty FILL = BooleanProperty.create("fill");
    public PaperCupBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(FILL,false));
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        if (pLevel.isClientSide){
            return InteractionResult.SUCCESS;
        }
        ItemStack hand = pPlayer.getItemInHand(pHand);
        int pile = pState.getValue(integerProperty);
        if (!hand.is(BakeriesItems.CAKE_PASTE_BUCKET.get())) {
            if (pState.getValue(FILL)) {
                return super.use(pState, pLevel, pPos, pPlayer, pHand, pHit);
            }
        }else {
            if (pile == this.getMaxPile() && !pState.getValue(FILL)){
                if (!pPlayer.getAbilities().instabuild){
                    hand.shrink(1);
                }
                ItemUtil.givePlayerItem(pPlayer,new ItemStack(Items.BUCKET));
                pLevel.setBlock(pPos,pState.setValue(FILL,true),3);
            }
        }
        return InteractionResult.SUCCESS;
    }

    @Override
    public InteractionResult take(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer){
        ItemStack stack = pState.getValue(FILL)?new ItemStack(BakeriesItems.PAPER_CUP_CAKE_PASTE.get()):new ItemStack(BakeriesItems.PAPER_CUP.get());
        int i =  pState.getValue(integerProperty);
        if (i == 1){
            pLevel.removeBlock(pPos,false);
        }
        ItemUtil.givePlayerItem(pPlayer,new ItemStack(stack.getItem()));
        pLevel.setBlock(pPos,pState.setValue(integerProperty,i-1),3);
        pLevel.playSound(null,pPos,getTakeSound(),SoundSource.BLOCKS);
        return InteractionResult.SUCCESS;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(FILL);
    }
}
