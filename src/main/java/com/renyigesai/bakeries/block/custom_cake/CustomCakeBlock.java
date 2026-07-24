package com.renyigesai.bakeries.block.custom_cake;

import com.renyigesai.bakeries.block.cake_box.CakeBoxBlockEntity;
import com.renyigesai.bakeries.init.BakeriesItems;
import com.renyigesai.bakeries.item.CustomCakeItem;
import com.renyigesai.bakeries.util.ItemUtils;
import com.renyigesai.bakeries.util.measurer.CakePartMeasurer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.NameTagItem;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class CustomCakeBlock extends HorizontalDirectionalBlock implements EntityBlock {
    public CustomCakeBlock(Properties pProperties) {
        super(pProperties);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH));
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return box(3.0, 0.0, 3.0, 13.0, 6.0, 13.0);
    }

    @Override
    public RenderShape getRenderShape(BlockState pState) {
        return RenderShape.MODEL;
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        if (pLevel.isClientSide){
            return InteractionResult.SUCCESS;
        }
        BlockEntity blockEntity = pLevel.getBlockEntity(pPos);
        if (!(blockEntity instanceof CustomCakeBlockEntity cc)){
            return super.use(pState, pLevel, pPos, pPlayer, pHand, pHit);
        }
        ItemStack itemInHand = pPlayer.getItemInHand(pHand);

        if (itemInHand.getItem() instanceof NameTagItem){
            cc.setName(itemInHand.getHoverName().getString());
            return InteractionResult.SUCCESS;
        }

        Optional<CakePartData> optional = CakePartMeasurer.isIngredient(itemInHand);
        if (optional.isPresent()){
            CakePartData cakePartData = optional.get();
            if (!cc.test(cakePartData)){
                return super.use(pState, pLevel, pPos, pPlayer, pHand, pHit);
            }
            cc.addCakePart(cakePartData);
            ItemUtils.shrinkAndReturn(itemInHand,1,pPlayer);
            return InteractionResult.SUCCESS;
        }
        return super.use(pState, pLevel, pPos, pPlayer, pHand, pHit);
    }

    @Override
    public void onRemove(BlockState state, Level world, BlockPos pos, BlockState newState, boolean isMoving) {
        if (state.getBlock() != newState.getBlock()) {
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if (blockEntity instanceof CustomCakeBlockEntity cc) {
                ItemStack itemStack = new ItemStack(BakeriesItems.CUSTOM_CAKE.get());
                CustomCakeItem.setCustomCakeNBT(cc,itemStack);
                Containers.dropItemStack(world,pos.getX(),pos.getY(),pos.getZ(),itemStack);
                world.updateNeighbourForOutputSignal(pos, this);
            }
            super.onRemove(state, world, pos, newState, isMoving);
        }
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(FACING);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        return this.defaultBlockState().setValue(FACING, pContext.getHorizontalDirection());
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new CustomCakeBlockEntity(blockPos,blockState);
    }
}
