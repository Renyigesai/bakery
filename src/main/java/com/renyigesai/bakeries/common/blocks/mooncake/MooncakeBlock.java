package com.renyigesai.bakeries.common.blocks.mooncake;

import com.mojang.serialization.MapCodec;
import com.renyigesai.bakeries.BakeriesMod;
import com.renyigesai.bakeries.common.init.BakeriesSoundType;
import com.renyigesai.bakeries.common.utils.ItemUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class MooncakeBlock extends BaseEntityBlock {
    public static final IntegerProperty PILE;
    public static final DirectionProperty FACING;
    public static final VoxelShape BOX;
    public MooncakeBlock() {
        super(Properties.of().strength(0.5F,0.5F).sound(BakeriesSoundType.PASTRY).noOcclusion().isRedstoneConductor((bs, br, bp) -> false));
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING,Direction.NORTH).setValue(PILE, 1));
    }

    public MooncakeBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING,Direction.NORTH).setValue(PILE, 1));
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return simpleCodec(MooncakeBlock::new);
    }

    @Override
    public RenderShape getRenderShape(BlockState pState) {
        return RenderShape.MODEL;
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return BOX;
    }

    @Override
    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        if (level.isClientSide){
            return ItemInteractionResult.SUCCESS;
        }
        if (!BakeriesMod.onAuxiliaryKey(player)){
            return take(state, level, pos, player);
        }
        return super.useItemOn(stack, state, level, pos, player, hand, hitResult);
    }

    public ItemInteractionResult take(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer){
        BlockEntity blockEntity = pLevel.getBlockEntity(pPos);
        if (!(blockEntity instanceof MooncakeBlockEntity mc)){
            return ItemInteractionResult.CONSUME;
        }
        int i = pState.getValue(PILE);
        ItemUtils.givePlayerItem(pPlayer,mc.getItems().getStackInSlot(i - 1).copy());
        mc.getItems().setStackInSlot(i - 1,ItemStack.EMPTY);
        mc.update();
        if (i == 1){
            pLevel.removeBlock(pPos,false);
        }else {
            pLevel.setBlock(pPos,pState.setValue(PILE,i - 1),3);
        }
        pLevel.playSound(null,pPos, SoundEvents.ITEM_FRAME_REMOVE_ITEM, SoundSource.BLOCKS);
        return ItemInteractionResult.SUCCESS;
    }

    @Override
    public void onRemove(BlockState state, Level world, BlockPos pos, BlockState newState, boolean isMoving) {
        if (state.getBlock() != newState.getBlock()) {
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if (blockEntity instanceof MooncakeBlockEntity mc) {
                mc.drops(mc);
                world.updateNeighbourForOutputSignal(pos, this);
            }
            super.onRemove(state, world, pos, newState, isMoving);
        }
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
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(FACING,PILE);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new MooncakeBlockEntity(blockPos,blockState);
    }

    static {
        FACING = BlockStateProperties.HORIZONTAL_FACING;
        PILE = IntegerProperty.create("pile",1,4);
        BOX = box(2,0,2,14,5,14);
    }
}
