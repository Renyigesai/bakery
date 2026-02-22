package com.renyigesai.bakeries.block.cake;

import com.renyigesai.bakeries.api.block.BCakeBlock;
import com.renyigesai.bakeries.init.BakeriesBlocks;
import com.renyigesai.bakeries.init.BakeriesItems;
import com.renyigesai.bakeries.util.ItemUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.*;

public class CakeProcessingInitialBlock extends Block {
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
    public CakeProcessingInitialBlock() {
        super(BCakeBlock.CAKE);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH));
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return box(3.0, 0.0, 3.0, 13.0, 3.0, 13.0);
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        ItemStack hand = pPlayer.getItemInHand(pHand);
        if (hand.isEmpty()){
            return super.use(pState, pLevel, pPos, pPlayer, pHand, pHit);
        }
        List<Item> keys = getProcessingKey();
        boolean flag = false;
        for (int i = 0; i < keys.size(); i++) {
            if (hand.is(keys.get(i)) || ItemUtils.isTag(hand,keys.get(i))) {
                Map<Item, Block> cake = getCakeProcessing();
                pLevel.setBlock(pPos, cake.get(keys.get(0)).defaultBlockState(), 3);
                flag = true;
            }
        }
        if (flag) {
            if (!pPlayer.getAbilities().instabuild) {
                if (hand.hasCraftingRemainingItem()){
                    ItemUtils.givePlayerItem(pPlayer,hand.getCraftingRemainingItem());
                }
                hand.shrink(1);
            }
        }
        return InteractionResult.SUCCESS;
    }

    @Override
    public RenderShape getRenderShape(BlockState pState) {
        return RenderShape.MODEL;
    }


    public static Map<Item, Block> getCakeProcessing(){
        Map<Item,Block> CAKE = new HashMap<>();
        CAKE.put(BakeriesItems.FOAMED_CREAM.get(), BakeriesBlocks.CREAM_CAKE_PROCESSING.get());
        return CAKE;
    }

    public List<Item> getProcessingKey(){
        Set<Item> tagKeys = getCakeProcessing().keySet();
        return new ArrayList<>(tagKeys.stream().toList());
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        return this.defaultBlockState().setValue(FACING, pContext.getHorizontalDirection());
    }

    public BlockState rotate(BlockState pState, Rotation pRot) {
        return pState.setValue(FACING, pRot.rotate(pState.getValue(FACING)));
    }

    public BlockState mirror(BlockState pState, Mirror pMirror) {
        return pState.rotate(pMirror.getRotation(pState.getValue(FACING)));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(FACING);
    }

}
