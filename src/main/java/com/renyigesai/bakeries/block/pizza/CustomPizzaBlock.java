package com.renyigesai.bakeries.block.pizza;

import com.mojang.datafixers.util.Pair;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class CustomPizzaBlock extends BaseEntityBlock {
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
    public static final IntegerProperty SLICE = IntegerProperty.create("slice",0,3);
    protected static final VoxelShape BOX = Block.box(1.0D, 0.0D, 1.0D, 15.0D, 2.0D, 15.0D);
    public CustomPizzaBlock() {
        super(BlockBehaviour.Properties.copy(Blocks.CAKE));
        this.registerDefaultState(this.stateDefinition.any().setValue(SLICE, 0).setValue(FACING, Direction.NORTH));
    }

    @Override
    public RenderShape getRenderShape(BlockState pState) {
        return RenderShape.MODEL;
    }

    @Override
    public @NotNull VoxelShape getShape(@NotNull BlockState pState, @NotNull BlockGetter pLevel, @NotNull BlockPos pPos, @NotNull CollisionContext pContext) {
        return BOX;
    }

    public int getSlice(){
        return 4;
    }

    @Override
    public @NotNull InteractionResult use(@NotNull BlockState pState, @NotNull Level pLevel, @NotNull BlockPos pPos, @NotNull Player pPlayer, @NotNull InteractionHand pHand, @NotNull BlockHitResult pHit) {
        return eat(pState, pLevel, pPos, pPlayer, pHand, pHit);
    }

    public InteractionResult eat(BlockState pState, Level pLevel, BlockPos pPos, LivingEntity livingEntity, InteractionHand pHand, BlockHitResult pHit){
        Player player = null;
        if (livingEntity instanceof Player){
            player = (Player)livingEntity;
        }
        BlockEntity blockEntity = pLevel.getBlockEntity(pPos);
        if (blockEntity instanceof CustomPizzaBlockEntity pizza){
            int nutrition = pizza.getNutrition();
            float saturationMod = pizza.getSaturationMod();
            if (player != null){
                player.getFoodData().eat(nutrition,saturationMod);
            }
            addEffects(pizza,livingEntity);
            pLevel.gameEvent(livingEntity, GameEvent.EAT, pPos);
            int slice = pState.getValue(SLICE);
            if (slice < getSlice() - 1){
                pLevel.setBlock(pPos, pState.setValue(SLICE, slice + 1), 3);
            }else {
                pLevel.removeBlock(pPos, false);
            }
            pLevel.playSound(null,pPos, SoundEvents.GENERIC_EAT, SoundSource.PLAYERS, 0.8F, 0.8F);
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.FAIL;

    }

    public void addEffects(CustomPizzaBlockEntity pizza, LivingEntity livingEntity){
        List<List<Pair<MobEffectInstance, Float>>> effects = pizza.getEffects(livingEntity);
        for (List<Pair<MobEffectInstance, Float>> pairs : effects) {
            for (Pair<MobEffectInstance, Float> mobEffectInstanceFloatPair : pairs) {
                livingEntity.addEffect(mobEffectInstanceFloatPair.getFirst());
            }
        }
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        return this.defaultBlockState().setValue(FACING, pContext.getHorizontalDirection());
    }

    @Override
    public boolean canSurvive(@NotNull BlockState state, LevelReader level, BlockPos pos) {
        return level.getBlockState(pos.below()).isSolid();
    }

    @Override
    public @NotNull BlockState updateShape(@NotNull BlockState stateIn, @NotNull Direction facing, @NotNull BlockState facingState, @NotNull LevelAccessor level, @NotNull BlockPos currentPos, @NotNull BlockPos facingPos) {
        return facing == Direction.DOWN && !stateIn.canSurvive(level, currentPos) ? Blocks.AIR.defaultBlockState() : super.updateShape(stateIn, facing, facingState, level, currentPos, facingPos);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new CustomPizzaBlockEntity(pPos,pState);
    }

    public BlockState rotate(BlockState pState, Rotation pRot) {
        return pState.setValue(FACING, pRot.rotate(pState.getValue(FACING)));
    }

    public BlockState mirror(BlockState pState, Mirror pMirror) {
        return pState.rotate(pMirror.getRotation(pState.getValue(FACING)));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(FACING,SLICE);
    }
}
