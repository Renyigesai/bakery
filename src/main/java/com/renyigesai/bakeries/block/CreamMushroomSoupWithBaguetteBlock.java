package com.renyigesai.bakeries.block;

import com.github.ysbbbbbb.kaleidoscopecookery.init.ModEffects;
import com.renyigesai.bakeries.compat.CompatMod;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class CreamMushroomSoupWithBaguetteBlock extends HorizontalDirectionalBlock {
    public static final IntegerProperty BITES;
    public static final VoxelShape BOX_1;
    public static final VoxelShape BOX_2;
    public static final VoxelShape BOX;
    public CreamMushroomSoupWithBaguetteBlock(Properties pProperties) {
        super(pProperties);
        this.registerDefaultState(defaultBlockState().setValue(BITES,0).setValue(FACING, Direction.NORTH));
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return BOX;
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        if (pLevel.isClientSide){
            return InteractionResult.SUCCESS;
        }
        return bites(pState, pLevel, pPos, pPlayer);
    }

    public InteractionResult bites(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer){
        int bites = pState.getValue(BITES);
        if (bites >= 2){
            pLevel.destroyBlock(pPos,true);
        }else {
            pPlayer.getFoodData().eat(9,0.5F);
            pLevel.setBlock(pPos,pState.setValue(BITES,bites + 1),3);
            if (CompatMod.KALEIDOSCOPE_COOKERY){
                pPlayer.addEffect(new MobEffectInstance(ModEffects.WARMTH.get(),6000));
            }
            pLevel.playSound(null,pPos, SoundEvents.GENERIC_EAT, SoundSource.BLOCKS);
        }
        return InteractionResult.SUCCESS;
    }

    @Override
    public void animateTick(BlockState pState, Level pLevel, BlockPos pPos, RandomSource pRandom) {
        if (pRandom.nextDouble() < 0.25){
            pLevel.addParticle(ParticleTypes.POOF,pPos.getX() + 0.5,pPos.getY() + 0.5,pPos.getZ() + 0.5,0,0,0);
        }
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        return this.defaultBlockState().setValue(FACING, pContext.getHorizontalDirection());
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(FACING,BITES);
    }

    static {
        BITES = IntegerProperty.create("bites",0,2);
        BOX_1 = box(1,0,1,15,1,15);
        BOX_2 = box(4,1,4,12,7,12);
        BOX = Shapes.or(BOX_1,BOX_2);
    }
}
