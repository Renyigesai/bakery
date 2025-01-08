package com.renyigesai.bakeries.block;

import com.renyigesai.bakeries.api.Shortcuts;
import com.renyigesai.bakeries.init.BakeriesItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
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
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.common.Tags;
//未完成
public class CountryBreadBlock extends HorizontalDirectionalBlock {
    /*
     * 由于乡村面包只是一个单纯的方块，他的物品不需要可食用。
     * Since the Country Bread is just a simple Block, his Item does not need to be edible.
     * */
    public static final IntegerProperty PILE = IntegerProperty.create("pile",1,2);
    protected static final VoxelShape X_BOX = Block.box(5.0D, 0.0D, 4.0D, 11.0D, 4.0D, 12.0D);//待更改
    protected static final VoxelShape Z_BOX = Block.box(4.0D, 0.0D, 5.0D, 12.0D, 4.0D, 11.0D);//待更改
    protected static final VoxelShape SHAPE = Block.box(2.0D, 0.0D, 2.0D, 14.0D, 4.0D, 14.0D);

    public CountryBreadBlock(Properties pProperties) {
        super(pProperties);
        this.registerDefaultState(this.stateDefinition.any().setValue(PILE,1).setValue(FACING, Direction.NORTH));
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        Direction direction = state.getValue(FACING);
        int pile = state.getValue(PILE);
        if (pile > 1){
            return SHAPE;
        }
        return direction.getAxis() == Direction.Axis.X ? X_BOX : Z_BOX;
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        ItemStack hand = pPlayer.getItemInHand(pHand);
        int pile = pState.getValue(PILE);
        if ( hand.is(Tags.Items.TOOLS) && pile == 1){
            return cut(pLevel,pPos,pState,pPlayer);
        }

        if (hand.is(asItem()) && pile < 2){
            hand.shrink(1);
            return pileUp(pLevel,pPos,pState,pPlayer);
        }
        return InteractionResult.FAIL;
    }

    protected InteractionResult cut (Level level, BlockPos pos, BlockState state, Player playerIn){
        level.removeBlock(pos,false);
//        for (int i = 0; i <6 ; i++) {
//            ItemEntity entity = new ItemEntity(level, pos.getX(), pos.getY() + 0.15, pos.getZ(), new ItemStack(BakeriesItems.COUNTRY_BREAD_SLICE.get()));
//            level.addFreshEntity(entity);
//        }
//        Direction direction = playerIn.getDirection().getOpposite();
        Shortcuts.spawnItemEntity(level, new ItemStack(BakeriesItems.COUNTRY_BREAD_SLICE.get(),6), pos.getX() + 0.5, pos.getY() + 0.3, pos.getZ() + 0.5,
                new Vec3(0.0, 0.0, 0.0));
        level.playSound(null, pos, SoundEvents.WOOL_BREAK, SoundSource.PLAYERS, 0.8F, 0.8F);
        return InteractionResult.SUCCESS;
    }

    protected InteractionResult pileUp(Level level, BlockPos pos, BlockState state, Player playerIn){
        int pile = state.getValue(PILE);
        level.setBlock(pos, state.setValue(PILE, pile + 1), 3);
        level.playSound(null, pos, SoundEvents.WOOL_STEP, SoundSource.PLAYERS, 0.8F, 0.8F);
        return InteractionResult.SUCCESS;
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        return this.defaultBlockState().setValue(FACING, pContext.getHorizontalDirection());
    }

    @Override
    public BlockState updateShape(BlockState stateIn, Direction facing, BlockState facingState, LevelAccessor level, BlockPos currentPos, BlockPos facingPos) {
        return facing == Direction.DOWN && !stateIn.canSurvive(level, currentPos) ? Blocks.AIR.defaultBlockState() : super.updateShape(stateIn, facing, facingState, level, currentPos, facingPos);
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        return level.getBlockState(pos.below()).isSolid();
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, PILE);
    }

    public int getAnalogOutputSignal(BlockState pBlockState, Level pLevel, BlockPos pPos) {
        return getOutputSignal(pBlockState.getValue(PILE));
    }

    public static int getOutputSignal(int pPile) {
        return pPile * 2;
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
