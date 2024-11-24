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
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class FermentationTankBlock extends Block {
    public static final IntegerProperty FLOUR = IntegerProperty.create("flour", 0, 3);
    public static final BooleanProperty WATER = BooleanProperty.create("water");
    public static final BooleanProperty MILK = BooleanProperty.create("milk");
    public static final BooleanProperty SWEET_BERRIES = BooleanProperty.create("sweet_berries");
    protected static final VoxelShape SHAPE = Block.box(3.0D, 0.0D, 3.0D, 13.0D, 16.0D, 13.0D);

    public FermentationTankBlock(Properties pProperties) {
        super(pProperties);
        this.registerDefaultState(this.stateDefinition.any().setValue(FLOUR, 0)
                .setValue(WATER, false).setValue(MILK,false).setValue(SWEET_BERRIES,false));
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer,
                                 InteractionHand pHand, BlockHitResult pHit) {
        ItemStack handStack = pPlayer.getItemInHand(pHand);
        int flour = pState.getValue(FLOUR);
        boolean milk = pState.getValue(MILK);
        if (flour < 3 && !milk){
            if (handStack.is(BakeriesItems.WHOLE_WHEAT_FLOUR.get())){
                return fillFlour(pLevel, pPos, pState, pPlayer, pHand);
            }
        }else if ((PotionUtils.getPotion(handStack) == Potions.WATER && !pState.getValue(WATER))){
            return fillWater(pLevel, pPos, pState, pPlayer, pHand);
        }
        if (!pState.getValue(MILK) && flour == 0){
            if (handStack.is(Items.MILK_BUCKET)){
                return fillMilk(pLevel, pPos, pState, pPlayer, pHand);
            }
        }else if(handStack.is(Items.SWEET_BERRIES)){
            return fillSweetBerries(pLevel, pPos, pState, pPlayer, pHand);
        }
        return InteractionResult.FAIL;
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
//        ItemHandlerHelper.giveItemToPlayer(playerIn,new ItemStack(Items.GLASS_BOTTLE));
        playerIn.getInventory().placeItemBackInInventory(new ItemStack(Items.GLASS_BOTTLE));
        level.playSound(null, pos, SoundEvents.BOTTLE_EMPTY, SoundSource.PLAYERS, 0.8F, 0.8F);
        return InteractionResult.SUCCESS;
    }

    public static InteractionResult fillMilk(Level level, BlockPos pos, BlockState state, Player playerIn,InteractionHand pHand){
        ItemStack handStack = playerIn.getItemInHand(pHand);
        level.setBlock(pos, state.setValue(MILK,true),0);
        handStack.shrink(1);
//        ItemHandlerHelper.giveItemToPlayer(playerIn,new ItemStack(Items.BUCKET));
        playerIn.getInventory().placeItemBackInInventory(new ItemStack(Items.BUCKET));
        level.playSound(null, pos, SoundEvents.BUCKET_EMPTY, SoundSource.PLAYERS, 0.8F, 0.8F);
        return InteractionResult.SUCCESS;
    }

    public static InteractionResult fillSweetBerries(Level level, BlockPos pos, BlockState state, Player playerIn,InteractionHand pHand){
        ItemStack handStack = playerIn.getItemInHand(pHand);
        level.setBlock(pos, state.setValue(SWEET_BERRIES,true),0);
        handStack.shrink(1);
        level.playSound(null, pos, SoundEvents.BUCKET_EMPTY, SoundSource.PLAYERS, 0.8F, 0.8F);
        return InteractionResult.SUCCESS;
    }

    @Override
    public void randomTick(BlockState pState, ServerLevel pLevel, BlockPos pPos, RandomSource pRandom) {
        int flour = pState.getValue(FLOUR);
        boolean water = pState.getValue(WATER);
        boolean milk = pState.getValue(MILK);
        boolean sweet_berries = pState.getValue(SWEET_BERRIES);
        if (flour == 3 && water) {
            pLevel.setBlock(pPos, BakeriesBlocks.YEAST_TANK.get().defaultBlockState(), 3);
        }
        if (milk && sweet_berries) {
            if (pRandom.nextInt(3) == 0) {
                pLevel.setBlock(pPos, BakeriesBlocks.CHEESE_TANK.get().defaultBlockState(), 3);
            }
        }
    }

    @Override
    public void animateTick(BlockState pState, Level pLevel, BlockPos pPos, RandomSource pRandom) {
        int flour = pState.getValue(FLOUR);
        boolean water = pState.getValue(WATER);
        boolean milk = pState.getValue(MILK);
        boolean sweet_berries = pState.getValue(SWEET_BERRIES);
        Direction direction = Direction.getRandom(pRandom);
        double d0 = direction.getStepX() == 0 ? pRandom.nextDouble() : 0.5D + (double) direction.getStepX() * 0.6D;
        double d1 = direction.getStepY() == 0 ? pRandom.nextDouble() : 0.5D + (double) direction.getStepY() * 0.6D;
        double d2 = direction.getStepZ() == 0 ? pRandom.nextDouble() : 0.5D + (double) direction.getStepZ() * 0.6D;
        if (flour == 3 && water || milk && sweet_berries){
            pLevel.addParticle(ParticleTypes.ENTITY_EFFECT, (double) pPos.getX() + d0, (double) pPos.getY() + d1, (double) pPos.getZ() + d2, 0.0D, 0.0D, 0.0D);
        }
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        return level.getBlockState(pos.below()).isSolid();
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FLOUR,WATER,MILK,SWEET_BERRIES);
    }

    @Override
    public boolean hasAnalogOutputSignal(BlockState state) {
        return true;
    }

    @Override
    public boolean isPathfindable(BlockState state, BlockGetter level, BlockPos pos, PathComputationType type) {
        return false;
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
