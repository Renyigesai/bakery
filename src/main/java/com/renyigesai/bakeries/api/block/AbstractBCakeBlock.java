package com.renyigesai.bakeries.api.block;

import com.renyigesai.bakeries.api.LazyMobEffectInstance;
import com.renyigesai.bakeries.common.init.BakeriesSoundType;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractBCakeBlock extends HorizontalDirectionalBlock {
    public static final IntegerProperty BITES = IntegerProperty.create("bites",0,3);
    public List<LazyMobEffectInstance> effects = new ArrayList<>();
    public final int foodLevelModifier;
    public final float saturationLevelModifier;
    public static final Properties CAKE = Properties.of().forceSolidOn().strength(0.5F).sound(BakeriesSoundType.PASTRY).pushReaction(PushReaction.DESTROY);
    public AbstractBCakeBlock(Properties pProperties, int foodLevelModifier, float saturationLevelModifier) {
        super(pProperties);
        this.foodLevelModifier = foodLevelModifier;
        this.saturationLevelModifier = saturationLevelModifier;
        this.registerDefaultState(this.stateDefinition.any().setValue(BITES,0).setValue(FACING, Direction.NORTH));
    }

    public AbstractBCakeBlock(Properties pProperties, List<LazyMobEffectInstance> effects, int foodLevelModifier, float saturationLevelModifier) {
        super(pProperties);
        this.foodLevelModifier = foodLevelModifier;
        this.saturationLevelModifier = saturationLevelModifier;
        this.registerDefaultState(this.stateDefinition.any().setValue(BITES,0).setValue(FACING, Direction.NORTH));
        this.effects = effects;
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return box(3.0, 0.0, 3.0, 13.0, 8.0, 13.0);
    }


    @Override
    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        ItemStack itemstack = player.getItemInHand(hand);
        if (level.isClientSide) {
            if (eat(level, pos, state, player).consumesAction()) {
                return ItemInteractionResult.SUCCESS;
            }

            if (itemstack.isEmpty()) {
                return ItemInteractionResult.CONSUME;
            }
        }

        return eat(level, pos, state, player);
    }


    protected ItemInteractionResult eat(LevelAccessor pLevel, BlockPos pPos, BlockState pState, Player pPlayer) {
        pPlayer.awardStat(Stats.EAT_CAKE_SLICE);
        pPlayer.getFoodData().eat(getFoodLevelModifier(), getSaturationLevelModifier());
        this.addEffect(pPlayer);
        int i = pState.getValue(BITES);
        pLevel.gameEvent(pPlayer, GameEvent.EAT, pPos);
        if (i < 3) {
            pLevel.setBlock(pPos, pState.setValue(BITES, i + 1), 3);
        } else {
            pLevel.removeBlock(pPos, false);
            pLevel.gameEvent(pPlayer, GameEvent.BLOCK_DESTROY, pPos);
        }
        pLevel.playSound(null,pPos, SoundEvents.GENERIC_EAT, SoundSource.PLAYERS, 0.8F, 0.8F);
        return ItemInteractionResult.SUCCESS;
    }

    public void addEffect(LivingEntity entity){
        if (!effects.isEmpty()){
            for (LazyMobEffectInstance lazyInstance : effects) {
                entity.addEffect(new MobEffectInstance(lazyInstance.getEffect(), lazyInstance.getDuration(), lazyInstance.getAmplifier()));
            }
        }
    }

    public int getFoodLevelModifier(){
        return this.foodLevelModifier;
    };

    public float getSaturationLevelModifier(){
        return this.saturationLevelModifier;
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        return this.defaultBlockState().setValue(FACING, pContext.getHorizontalDirection());
    }

    public BlockState updateShape(BlockState pState, Direction pFacing, BlockState pFacingState, LevelAccessor pLevel, BlockPos pCurrentPos, BlockPos pFacingPos) {
        return pFacing == Direction.DOWN && !pState.canSurvive(pLevel, pCurrentPos) ? Blocks.AIR.defaultBlockState() : super.updateShape(pState, pFacing, pFacingState, pLevel, pCurrentPos, pFacingPos);
    }

    public boolean canSurvive(BlockState pState, LevelReader pLevel, BlockPos pPos) {
        return pLevel.getBlockState(pPos.below()).isSolid();
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(BITES,FACING);
    }

    @Override
    public int getAnalogOutputSignal(BlockState pState, @NotNull Level pLevel, @NotNull BlockPos pPos) {
        return pState.getValue(BITES) + 1;
    }

    public boolean hasAnalogOutputSignal(@NotNull BlockState state) {
        return true;
    }

}
