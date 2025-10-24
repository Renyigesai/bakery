package com.renyigesai.bakeries.block.pizza;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public class PizzaBlock extends HorizontalDirectionalBlock {
    public static final IntegerProperty SLICE = IntegerProperty.create("slice",0,3);
    public final int nutrition;
    public final float saturationModifier;
    public Supplier<Item> sliceItem;
    protected static final VoxelShape BOX = Block.box(1.0D, 0.0D, 1.0D, 15.0D, 2.0D, 15.0D);
    public PizzaBlock(int nutrition, float saturationModifier) {
        super(BlockBehaviour.Properties.copy(Blocks.CAKE));
        this.nutrition = nutrition;
        this.saturationModifier = saturationModifier;
        this.registerDefaultState(this.stateDefinition.any().setValue(SLICE, 0).setValue(FACING,Direction.NORTH));
    }

    @Override
    public @NotNull VoxelShape getShape(@NotNull BlockState pState, @NotNull BlockGetter pLevel, @NotNull BlockPos pPos, @NotNull CollisionContext pContext) {
        return BOX;
    }

    @Override
    public @NotNull InteractionResult use(@NotNull BlockState pState, @NotNull Level pLevel, @NotNull BlockPos pPos, @NotNull Player pPlayer, @NotNull InteractionHand pHand, @NotNull BlockHitResult pHit) {
            return eat(pState, pLevel, pPos, pPlayer, pHand, pHit);
    }

    public InteractionResult eat(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit){
        pPlayer.getFoodData().eat(this.nutrition, this.saturationModifier);
        pLevel.gameEvent(pPlayer, GameEvent.EAT, pPos);
        int slice = pState.getValue(SLICE);
        if (slice < getSlice() - 1){
            pLevel.setBlock(pPos, pState.setValue(SLICE, slice + 1), 3);
        }else {
            pLevel.removeBlock(pPos, false);
        }
        pLevel.playSound(null,pPos, SoundEvents.GENERIC_EAT, SoundSource.PLAYERS, 0.8F, 0.8F);
        return InteractionResult.SUCCESS;
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        return this.defaultBlockState().setValue(FACING, pContext.getHorizontalDirection());
    }

    public int getSlice(){
        return 4;
    }

    @Override
    public boolean canSurvive(@NotNull BlockState state, LevelReader level, BlockPos pos) {
        return level.getBlockState(pos.below()).isSolid();
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(FACING,SLICE);
    }

    @Override
    public @NotNull BlockState updateShape(@NotNull BlockState stateIn, @NotNull Direction facing, @NotNull BlockState facingState, @NotNull LevelAccessor level, @NotNull BlockPos currentPos, @NotNull BlockPos facingPos) {
        return facing == Direction.DOWN && !stateIn.canSurvive(level, currentPos) ? Blocks.AIR.defaultBlockState() : super.updateShape(stateIn, facing, facingState, level, currentPos, facingPos);
    }
}
