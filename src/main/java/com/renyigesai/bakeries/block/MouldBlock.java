package com.renyigesai.bakeries.block;

import com.renyigesai.bakeries.util.Shortcuts;
import com.renyigesai.bakeries.api.block.properties.ModIntegerProperty;
import com.renyigesai.bakeries.init.BakeriesItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
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
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public class MouldBlock extends HorizontalDirectionalBlock {

    public final Supplier<Item> demouldItem;
    public static final ModIntegerProperty PILE = ModIntegerProperty.create("pile",1,2);
    public static final ModIntegerProperty USE = ModIntegerProperty.create("use",1,2);
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
    public @NotNull InteractionResult use(@NotNull BlockState pState, Level pLevel, @NotNull BlockPos pPos, Player pPlayer, @NotNull InteractionHand pHand, @NotNull BlockHitResult pHit) {
        ItemStack hand = pPlayer.getItemInHand(pHand);
        if (!pLevel.isClientSide) {
            if (!hand.is(asItem())) {
                return take(pLevel, pPos, pState, pPlayer);
            } else {
                hand.shrink(1);
                return pileUp(pLevel, pPos, pState);
            }
        }
        if (!hand.is(asItem())) {
            return take(pLevel, pPos, pState, pPlayer);
        } else {
            hand.shrink(1);
            return pileUp(pLevel, pPos, pState);
        }
    }

    protected InteractionResult pileUp(Level level, BlockPos pos, BlockState state){
        int pile = state.getValue(PILE);
        int use = state.getValue(USE);
        if (pile < 2 && use == 2) {
            Shortcuts.setBlock(level,pos,state,PILE,1, true);
            level.playSound(null, pos, SoundEvents.METAL_PLACE, SoundSource.PLAYERS, 0.8F, 0.8F);
            return InteractionResult.SUCCESS;
        }return InteractionResult.FAIL;
    }

    protected InteractionResult take(Level level, BlockPos pos, BlockState state, Player player){
        int use = state.getValue(USE);
        int pile = state.getValue(PILE);
        if (use == 2 && pile == 1){
            Shortcuts.setBlock(level,pos,state,USE,1,false);
            Shortcuts.givePlayerItem(player,new ItemStack(this.demouldItem.get()));
            level.playSound(null, pos, SoundEvents.WOOL_BREAK, SoundSource.PLAYERS, 0.8F, 0.8F);
        }else if (use == 1 && pile == 1){
            level.removeBlock(pos,false);
            Shortcuts.givePlayerItem(player,new ItemStack(BakeriesItems.MOULD.get()));
            level.playSound(null, pos, SoundEvents.METAL_BREAK, SoundSource.PLAYERS, 0.8F, 0.8F);
        }return InteractionResult.SUCCESS;
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
        builder.add(FACING, PILE, USE);
    }
}
