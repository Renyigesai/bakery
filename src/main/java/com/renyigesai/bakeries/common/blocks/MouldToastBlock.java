package com.renyigesai.bakeries.common.blocks;

import com.mojang.serialization.MapCodec;
import com.renyigesai.bakeries.common.init.BakeriesBlocks;
import com.renyigesai.bakeries.common.utils.ItemUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
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
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public class MouldToastBlock extends HorizontalDirectionalBlock {

    public final Supplier<Item> demouldItem;
    public static final IntegerProperty PILE = IntegerProperty.create("pile",1,2);
    protected static final VoxelShape X_BOX = Block.box(6.0D, 0.0D, 4.0D, 10.0D, 5.0D, 12.0D);
    protected static final VoxelShape Z_BOX = Block.box(4.0D, 0.0D, 6.0D, 12.0D, 5.0D, 10.0D);
    protected static final VoxelShape SHAPE = Block.box(2.0D, 0.0D, 2.0D, 14.0D, 5.0D, 14.0D);

    public MouldToastBlock(Properties pProperties, Supplier<Item> demouldItem) {
        super(pProperties);
        this.demouldItem = demouldItem;
        this.registerDefaultState(this.stateDefinition.any().setValue(PILE,1).setValue(FACING, Direction.NORTH));
    }

    private MouldToastBlock(Properties pProperties) {
        super(pProperties);
        this.demouldItem = ItemStack.EMPTY::getItem;
        this.registerDefaultState(this.stateDefinition.any().setValue(PILE,1).setValue(FACING, Direction.NORTH));
    }

    @Override
    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult hitResult) {
        ItemStack hand = pPlayer.getItemInHand(pHand);
        if (!pLevel.isClientSide) {
            if (!hand.is(asItem())) {
                return take(pLevel, pPos, pState, pPlayer);
            } else {
                return pileUp(pLevel, pPos, pState,pPlayer,pHand);
            }
        }
        if (!hand.is(asItem())) {
            return take(pLevel, pPos, pState, pPlayer);
        } else {
            return pileUp(pLevel, pPos, pState,pPlayer,pHand);
        }
    }

    protected ItemInteractionResult pileUp(Level level, BlockPos pos, BlockState state,Player player,InteractionHand hand){
        ItemStack handStack = player.getItemInHand(hand);
        int pile = state.getValue(PILE);
        if (pile < 2) {
            if (!player.getAbilities().instabuild) {
                handStack.shrink(1);
            }
            level.setBlock(pos,state.setValue(PILE,pile +1),3);
            level.playSound(null, pos, SoundEvents.METAL_PLACE, SoundSource.PLAYERS, 0.8F, 0.8F);
            return ItemInteractionResult.SUCCESS;
        }
        return ItemInteractionResult.FAIL;
    }

    protected ItemInteractionResult take(Level level, BlockPos pos, BlockState state, Player player){
        int pile = state.getValue(PILE);
        if (pile == 1){
            ItemUtil.givePlayerItem(player,new ItemStack(this.demouldItem.get()));
            level.playSound(null, pos, SoundEvents.WOOL_BREAK, SoundSource.PLAYERS, 0.8F, 0.8F);
            Direction facing = state.getValue(FACING);
            BlockState bBlockState = BakeriesBlocks.MOULD.get().defaultBlockState()
                    .setValue(FACING, facing);
            level.setBlockAndUpdate(pos,bBlockState);
        }
        return ItemInteractionResult.SUCCESS;
    }

    @Override
    public @NotNull VoxelShape getShape(BlockState state, @NotNull BlockGetter level, @NotNull BlockPos pos, @NotNull CollisionContext context) {
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
    public @NotNull BlockState updateShape(@NotNull BlockState stateIn, @NotNull Direction facing, @NotNull BlockState facingState, @NotNull LevelAccessor level, @NotNull BlockPos currentPos, @NotNull BlockPos facingPos) {
        return facing == Direction.DOWN && !stateIn.canSurvive(level, currentPos) ? Blocks.AIR.defaultBlockState() : super.updateShape(stateIn, facing, facingState, level, currentPos, facingPos);
    }

    @Override
    public boolean canSurvive(@NotNull BlockState state, LevelReader level, BlockPos pos) {
        return level.getBlockState(pos.below()).isSolid();
    }
    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING,PILE);
    }

    @Override
    protected MapCodec<MouldToastBlock> codec() {
        return simpleCodec(MouldToastBlock::new);
    }
}
