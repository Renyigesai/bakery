package com.renyigesai.bakeries.common.blocks;

import com.mojang.serialization.MapCodec;
import com.renyigesai.bakeries.api.block.AKnifeCutBlock;
import com.renyigesai.bakeries.common.init.BakeriesItems;
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
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

public class CountryBreadBlock extends AKnifeCutBlock {
    /*由于乡村面包只是一个单纯的方块，他的物品不需要可食用*/
    public static final IntegerProperty PILE = IntegerProperty.create("pile",1,2);
    protected static final VoxelShape X_BOX = Block.box(5.0D, 0.0D, 4.0D, 11.0D, 4.0D, 12.0D);//待更改
    protected static final VoxelShape Z_BOX = Block.box(4.0D, 0.0D, 5.0D, 12.0D, 4.0D, 11.0D);//待更改
    protected static final VoxelShape SHAPE = Block.box(2.0D, 0.0D, 2.0D, 14.0D, 4.0D, 14.0D);

    public CountryBreadBlock(Properties pProperties) {
        super(pProperties);
        this.registerDefaultState(this.stateDefinition.any().setValue(PILE,1).setValue(FACING, Direction.NORTH));
    }

    @Override
    protected MapCodec<? extends HorizontalDirectionalBlock> codec() {
        return null;
    }

    @Override
    public Property<Integer> getSliceProperty() {
        return null;
    }

    @Override
    public int getMaxSlice() {
        return 4;
    }

    @Override
    public int getSliceItemCount() {
        return 4;
    }

    @Override
    public Item getSliceItem() {
        return BakeriesItems.COUNTRY_BREAD_SLICE.get();
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
    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        ItemStack handItem = player.getItemInHand(hand);
        int pile = state.getValue(PILE);
        if (isKnifeItem(handItem) && pile == 1){
            cut(level,state,pos,player,handItem,hand);
            return ItemInteractionResult.SUCCESS;
        }

        if (handItem.is(asItem()) && pile < 2){
            ItemUtil.shrink(handItem,1,player);
            return pileUp(level,pos,state,player);
        }
        return super.useItemOn(stack, state, level, pos, player, hand, hitResult);
    }

    protected ItemInteractionResult pileUp(Level level, BlockPos pos, BlockState state, Player playerIn){
        int pile = state.getValue(PILE);
        level.setBlock(pos, state.setValue(PILE, pile + 1), 3);
        level.playSound(null, pos, SoundEvents.WOOL_STEP, SoundSource.PLAYERS, 0.8F, 0.8F);
        return ItemInteractionResult.SUCCESS;
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
        super.createBlockStateDefinition(builder);
        builder.add(FACING, PILE);
    }

    public int getAnalogOutputSignal(BlockState pBlockState, @NotNull Level pLevel, @NotNull BlockPos pPos) {
        return getOutputSignal(pBlockState.getValue(PILE));
    }

    public static int getOutputSignal(int pPile) {
        return pPile * 2;
    }

    @Override
    public boolean hasAnalogOutputSignal(@NotNull BlockState state) {
        return true;
    }

}
