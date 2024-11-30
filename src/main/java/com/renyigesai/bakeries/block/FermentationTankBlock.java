package com.renyigesai.bakeries.block;

import com.renyigesai.bakeries.init.BakeriesBlocks;
import com.renyigesai.bakeries.init.BakeriesItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.BlockHitResult;

public class FermentationTankBlock extends TankBlock {
    public static final IntegerProperty FLOUR = IntegerProperty.create("flour", 0, 3);
    public static final BooleanProperty WATER = BooleanProperty.create("water");

    public FermentationTankBlock(Properties pProperties) {
        super(pProperties);
        this.registerDefaultState(this.stateDefinition.any().setValue(FLOUR, 0)
                .setValue(WATER, false));
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer,
                                 InteractionHand pHand, BlockHitResult pHit) {
        ItemStack handStack = pPlayer.getItemInHand(pHand);
        int flour = pState.getValue(FLOUR);
        if (flour < 3 && handStack.is(BakeriesItems.WHOLE_WHEAT_FLOUR.get())){
                return fillFlour(pLevel, pPos, pState, pPlayer, pHand);
        }else if ((PotionUtils.getPotion(handStack) == Potions.WATER && !pState.getValue(WATER))){
            return fillWater(pLevel, pPos, pState, pPlayer, pHand);
        }
        if (flour == 0 && handStack.is(Items.MILK_BUCKET)){
                return fillMilk(pLevel, pPos, pState, pPlayer, pHand);
        }
        return super.use(pState, pLevel, pPos, pPlayer, pHand, pHit);
    }

    public static InteractionResult fillFlour(Level level, BlockPos pos, BlockState state, Player playerIn, InteractionHand pHand){
        ItemStack handStack = playerIn.getItemInHand(pHand);
        int flour = state.getValue(FLOUR);
        level.setBlock(pos, state.setValue(FLOUR, Math.min(flour + 1, 3)), 3);
        handStack.shrink(1);
        level.playSound(null, pos, SoundEvents.WOOL_STEP, SoundSource.PLAYERS, 0.8F, 0.8F);
        return InteractionResult.SUCCESS;
    }

    public static InteractionResult fillWater(Level level, BlockPos pos, BlockState state, Player playerIn,InteractionHand pHand){
        ItemStack handStack = playerIn.getItemInHand(pHand);
        level.setBlock(pos, state.setValue(WATER,true),0);
        handStack.shrink(1);
        playerIn.getInventory().placeItemBackInInventory(new ItemStack(Items.GLASS_BOTTLE));
        level.playSound(null, pos, SoundEvents.BOTTLE_EMPTY, SoundSource.PLAYERS, 0.8F, 0.8F);
        return InteractionResult.SUCCESS;
    }

    public static InteractionResult fillMilk(Level level, BlockPos pos, BlockState state, Player playerIn,InteractionHand pHand){
        ItemStack handStack = playerIn.getItemInHand(pHand);
        handStack.shrink(1);
        playerIn.getInventory().placeItemBackInInventory(new ItemStack(Items.BUCKET));
        level.setBlock(pos, BakeriesBlocks.Milk_TANK.get().defaultBlockState(), 3);
        level.playSound(null, pos, SoundEvents.BUCKET_EMPTY, SoundSource.PLAYERS, 0.8F, 0.8F);
        return InteractionResult.SUCCESS;
    }

    @Override
    public void randomTick(BlockState pState, ServerLevel pLevel, BlockPos pPos, RandomSource pRandom) {
        int flour = pState.getValue(FLOUR);
        boolean water = pState.getValue(WATER);
        if (flour == 3 && water) {
            pLevel.setBlock(pPos, BakeriesBlocks.YEAST_TANK.get().defaultBlockState(), 3);
        }
    }

    @Override
    public void animateTick(BlockState pState, Level pLevel, BlockPos pPos, RandomSource pRandom) {
        int flour = pState.getValue(FLOUR);
        boolean water = pState.getValue(WATER);
        Direction direction = Direction.getRandom(pRandom);
        double d0 = direction.getStepX() == 0 ? pRandom.nextDouble() : 0.5D + (double) direction.getStepX() * 0.6D;
        double d1 = direction.getStepY() == 0 ? pRandom.nextDouble() : 0.5D + (double) direction.getStepY() * 0.6D;
        double d2 = direction.getStepZ() == 0 ? pRandom.nextDouble() : 0.5D + (double) direction.getStepZ() * 0.6D;
        if (flour == 3 && water){
            pLevel.addParticle(ParticleTypes.ENTITY_EFFECT, (double) pPos.getX() + d0, (double) pPos.getY() + d1, (double) pPos.getZ() + d2, 0.0D, 0.0D, 0.0D);
        }
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FLOUR,WATER);
    }

    @Override
    public void destroy(LevelAccessor pLevel, BlockPos pPos, BlockState pState) {
        int flour = pState.getValue(FLOUR);
        for (int i = 0; i < flour; i++) {
            double x = pPos.getX() + 0.5;
            double y = pPos.getY() + 0.15;
            double z = pPos.getZ() + 0.5;
            ItemEntity entity = new ItemEntity((Level) pLevel, x, y, z, new ItemStack(BakeriesItems.WHOLE_WHEAT_FLOUR.get()));
            pLevel.addFreshEntity(entity);
        }
    }
}
