package com.renyigesai.bakery.api.block;

import com.renyigesai.bakery.init.BakeryItems;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.items.ItemHandlerHelper;

public class FermentationTankBlock extends Block {
//    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
//    public static final IntegerProperty FILL = IntegerProperty.create("fill", 0, 4);
    public static final IntegerProperty FLOUR = IntegerProperty.create("flour", 0, 3);
    public static final IntegerProperty WATER = IntegerProperty.create("water", 0, 1);
//    public static final IntegerProperty MILK = IntegerProperty.create("milk", 0, 1);
    protected static final VoxelShape SHAPE = Block.box(1.0D, 0.0D, 1.0D, 15.0D, 4.0D, 15.0D);

    public FermentationTankBlock(Properties pProperties) {
        super(pProperties);
        this.registerDefaultState(this.stateDefinition.any().setValue(FLOUR, 0)
                .setValue(WATER, 0));
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

//    @Override
//    public BlockState getStateForPlacement(BlockPlaceContext context) {
//        return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
//    }

    public static ItemStack isFlour(){
        return new ItemStack(BakeryItems.FLOUR_RYE.get());
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer,
                                 InteractionHand pHand, BlockHitResult pHit) {
        ItemStack handStack = pPlayer.getItemInHand(pHand);
        int flour = pState.getValue(FLOUR);
//        int milk = pState.getValue(MILK);
        if (handStack.is(BakeryItems.FLOUR_RYE.get()) && flour < 3){
            return fillFlour(pLevel,pPos,pState,pPlayer,pHand);
        }
//        if (handStack.is(Items.MILK_BUCKET) && milk == 0){
//            return fillMilk(pLevel,pPos,pState,pPlayer,pHand);
//        }
        if (PotionUtils.getPotion(handStack) == Potions.WATER && flour == 3){
            return fillWater(pLevel,pPos,pState,pPlayer,pHand);

        }
        return InteractionResult.FAIL;
    }

    public static InteractionResult fillFlour(Level level, BlockPos pos, BlockState state, Player playerIn, InteractionHand pHand){
        ItemStack handStack = playerIn.getItemInHand(pHand);
        int flour = state.getValue(FLOUR);
            level.setBlock(pos, state.setValue(FLOUR, flour + 1), 3);
            handStack.shrink(1);
            return InteractionResult.SUCCESS;
    }

//    public static InteractionResult fillMilk(Level level, BlockPos pos, BlockState state, Player playerIn, InteractionHand pHand){
//        ItemStack handStack = playerIn.getItemInHand(pHand);
//        int milk = state.getValue(MILK);
//        level.setBlock(pos, state.setValue(MILK, milk + 1), 1);
//        handStack.shrink(1);
//        ItemHandlerHelper.giveItemToPlayer(playerIn,new ItemStack(Items.BUCKET));
//        return InteractionResult.SUCCESS;
//    }

    public static InteractionResult fillWater(Level level, BlockPos pos, BlockState state, Player playerIn,InteractionHand pHand){
//
        ItemStack handStack = playerIn.getItemInHand(pHand);
        int flour = state.getValue(WATER);
        level.setBlock(pos, state.setValue(WATER, flour + 1), 1);
        handStack.shrink(1);
//        new ItemStack(Items.GLASS_BOTTLE).grow(1);
//        handStack.grow(1);
        ItemHandlerHelper.giveItemToPlayer(playerIn,new ItemStack(Items.GLASS_BOTTLE));
        return InteractionResult.SUCCESS;
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        return level.getBlockState(pos.below()).isSolid();
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FLOUR,WATER);
    }

    @Override
    public boolean hasAnalogOutputSignal(BlockState state) {
        return true;
    }

    @Override
    public boolean isPathfindable(BlockState state, BlockGetter level, BlockPos pos, PathComputationType type) {
        return false;
    }
}
