package com.renyigesai.bakeries.block;

import com.renyigesai.bakeries.init.BakeriesItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
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
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.function.Supplier;

public class MouldBlock extends HorizontalDirectionalBlock {

    public final Supplier<Item> demouldItem;
    public static final IntegerProperty PILE = IntegerProperty.create("pile",1,2);
    public static final IntegerProperty USE = IntegerProperty.create("use",1,2);
    protected static final VoxelShape X_BOX = Block.box(6.0D, 0.0D, 4.0D, 10.0D, 5.0D, 12.0D);
    protected static final VoxelShape Z_BOX = Block.box(4.0D, 0.0D, 6.0D, 12.0D, 5.0D, 10.0D);
    protected static final VoxelShape SHAPE = Block.box(2.0D, 0.0D, 2.0D, 14.0D, 5.0D, 14.0D);

    public MouldBlock(Properties pProperties, Supplier<Item> demouldItem) {
        super(pProperties);
        this.demouldItem = demouldItem;
        this.registerDefaultState(this.stateDefinition.any().setValue(PILE,1).setValue(USE,2).setValue(FACING, Direction.NORTH));
    }

    public ItemStack getDemouldItem(){return new ItemStack(this.demouldItem.get());}

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        ItemStack hand = pPlayer.getItemInHand(pHand);
        if (!hand.is(asItem())){
            return take(pLevel,pPos,pState,pPlayer);
        }else {
            return pileUp(pLevel,pPos,pState);
        }
    }

    protected InteractionResult pileUp(Level level, BlockPos pos, BlockState state){
        int pile = state.getValue(PILE);
        if (pile < 2) {
            level.setBlock(pos, state.setValue(PILE, pile + 1), 2);
            level.playSound(null, pos, SoundEvents.IRON_GOLEM_STEP, SoundSource.PLAYERS, 0.8F, 0.8F);
            return InteractionResult.SUCCESS;
        }return InteractionResult.FAIL;
    }

    protected InteractionResult take(Level level, BlockPos pos, BlockState state, Player player){
        int use = state.getValue(USE);
        int pile = state.getValue(PILE);
        if (use == 2 && pile == 1){
            level.setBlock(pos, state.setValue(USE, use - 1), 2);
            player.getInventory().placeItemBackInInventory(new ItemStack(this.demouldItem.get()));
        }else {
            level.removeBlock(pos,false);
            player.getInventory().placeItemBackInInventory(new ItemStack(BakeriesItems.MOULD.get()));
        }return InteractionResult.SUCCESS;
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
        builder.add(FACING, PILE, USE);
    }
}
