package com.renyigesai.bakeries.block;

import com.renyigesai.bakeries.block.custom_cake.CakePartData;
import com.renyigesai.bakeries.block.cake.CakeRollProcessingBlock;
import com.renyigesai.bakeries.block.custom_cake.CustomCakeBlock;
import com.renyigesai.bakeries.block.custom_cake.CustomCakeBlockEntity;
import com.renyigesai.bakeries.init.BakeriesBlocks;
import com.renyigesai.bakeries.init.BakeriesItems;
import com.renyigesai.bakeries.util.ItemUtils;
import com.renyigesai.bakeries.util.measurer.CakePartMeasurer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.Optional;

public class SiliconePaperBlock extends HorizontalDirectionalBlock {
    private static final VoxelShape BOX = box(1,0,1,15,1,15);
    public SiliconePaperBlock(Properties pProperties) {
        super(pProperties);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH));
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return BOX;
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        ItemStack hand = pPlayer.getItemInHand(pHand);
        if (hand.is(BakeriesItems.CUT_CAKE_BASE.get())){
            Direction facing = pState.getValue(FACING);
            pLevel.setBlock(pPos,BakeriesBlocks.CAKE_ROLL_PROCESSING.get().defaultBlockState().setValue(CakeRollProcessingBlock.FACING,facing),3);
            ItemUtils.shrink(hand,1,pPlayer);
            return InteractionResult.SUCCESS;
        }

        /*1.3.0新增，创建自定义蛋糕*/
        Optional<CakePartData> optional = CakePartMeasurer.isIngredient(hand);
        if (optional.isPresent()){
            CakePartData cakePartData = optional.get();
            if (!CakePartMeasurer.CAKE_BASE.equals(cakePartData.getType())){
                return super.use(pState, pLevel, pPos, pPlayer, pHand, pHit);
            }
            Direction direction = pState.getValue(FACING);
            pLevel.setBlock(pPos,BakeriesBlocks.CUSTOM_CAKE.get().defaultBlockState().setValue(CustomCakeBlock.FACING,direction),3);
            BlockEntity blockEntity = pLevel.getBlockEntity(pPos);
            if (blockEntity instanceof CustomCakeBlockEntity cc){
                cc.addCakePart(cakePartData);
            }
            return InteractionResult.SUCCESS;
        }

        return super.use(pState, pLevel, pPos, pPlayer, pHand, pHit);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        return this.defaultBlockState().setValue(FACING, pContext.getHorizontalDirection());
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(FACING);
    }
}
