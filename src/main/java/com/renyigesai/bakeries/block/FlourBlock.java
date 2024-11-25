//package com.renyigesai.bakeries.block;
//
//import com.renyigesai.bakeries.init.BakeriesItems;
//import net.minecraft.core.BlockPos;
//import net.minecraft.core.Direction;
//import net.minecraft.world.InteractionHand;
//import net.minecraft.world.InteractionResult;
//import net.minecraft.world.entity.player.Player;
//import net.minecraft.world.item.ItemStack;
//import net.minecraft.world.item.Items;
//import net.minecraft.world.item.context.BlockPlaceContext;
//import net.minecraft.world.level.BlockGetter;
//import net.minecraft.world.level.Level;
//import net.minecraft.world.level.LevelReader;
//import net.minecraft.world.level.block.Block;
//import net.minecraft.world.level.block.HorizontalDirectionalBlock;
//import net.minecraft.world.level.block.state.BlockState;
//import net.minecraft.world.level.block.state.StateDefinition;
//import net.minecraft.world.level.block.state.properties.IntegerProperty;
//import net.minecraft.world.phys.BlockHitResult;
//import net.minecraft.world.phys.shapes.CollisionContext;
//import net.minecraft.world.phys.shapes.VoxelShape;
//
//public class FlourBlock extends HorizontalDirectionalBlock {
//
//    protected static final VoxelShape SHAPE = Block.box(4.0D, 0.0D, 4.0D, 12.0D, 2.0D, 12.0D);
//    public static final IntegerProperty MATERIALS = IntegerProperty.create("materials",1,5);
//
//    public FlourBlock(Properties pProperties) {
//        super(pProperties);
//        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH));
//    }
//
//    @Override
//    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
//        return super.use(pState, pLevel, pPos, pPlayer, pHand, pHit);
//    }
//
//    protected InteractionResult put(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit){
//        int materials = state.getValue(MATERIALS);
//        ItemStack phand = player.getItemInHand(hand);
//        if (materials == 1 && phand.is(BakeriesItems.SALT.get())){
//
//        }else if (materials == 2 && phand.is(Items.SUGAR) && phand.getCount()>=2){
//
//        } else if (materials == 3 && phand.is(Items.EGG)) {
//
//        } else if (materials == 4 && phand.is(Items.MILK_BUCKET)) {
//
//        }
//    }
//
//    @Override
//    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
//        return SHAPE;
//    }
//    @Override
//    public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
//        return level.getBlockState(pos.below()).isSolid();
//    }
//
//    @Override
//    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
//        builder.add(FACING);
//    }
//    @Override
//    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
//        return this.defaultBlockState().setValue(FACING, pContext.getHorizontalDirection());
//    }
//}
