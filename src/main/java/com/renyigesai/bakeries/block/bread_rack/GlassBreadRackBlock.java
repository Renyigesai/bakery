package com.renyigesai.bakeries.block.bread_rack;

import com.renyigesai.bakeries.init.BakeriesBlocks;
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
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

public class GlassBreadRackBlock extends BreadRackBlock {

    public static final BooleanProperty OPEN = BlockStateProperties.OPEN;

    public GlassBreadRackBlock(Properties pProperties) {
        super(pProperties);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(OPEN,false).setValue(TYPE,Type.SINGLE).setValue(SINGLE,false));
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player,
                                 InteractionHand hand, BlockHitResult hit) {
        if (level.isClientSide) {
            return InteractionResult.SUCCESS;
        }

        BlockEntity blockEntity = level.getBlockEntity(pos);
        if (!(blockEntity instanceof BreadRackBlockEntity rackBlock)) {
            return InteractionResult.FAIL;
        }

        boolean open = state.getValue(OPEN);

        int slot = getSlotFromHit(hit.getLocation(), pos, state.getValue(FACING), hit.getDirection().getOpposite());
        boolean clickedEmptySlot = slot != -1 && rackBlock.getItems().getStackInSlot(slot).isEmpty() && player.getItemInHand(hand).isEmpty();

        if (!open || player.isShiftKeyDown() || clickedEmptySlot) {
            boolean newOpen = !open;
            level.blockEvent(pos, state.getBlock(), 0, newOpen ? 0 : 1);
            rackBlock.setOpen(newOpen);
            level.playSound(null, pos, newOpen ? SoundEvents.IRON_DOOR_OPEN : SoundEvents.IRON_DOOR_CLOSE, SoundSource.PLAYERS, 0.8F, 0.8F);
            return InteractionResult.SUCCESS;
        }

        return super.use(state, level, pos, player, hand, hit);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState, BlockEntityType<T> pBlockEntityType) {
        if (pLevel.isClientSide) {
            return createTickerHelper(pBlockEntityType, BakeriesBlocks.BREAD_RACK_ENTITY.get(), BreadRackBlockEntity::clientTick);
        }
        return null;
    }

    private static <E extends BlockEntity, A extends BlockEntity> BlockEntityTicker<A> createTickerHelper(BlockEntityType<A> pType, BlockEntityType<E> pExpectedType, BlockEntityTicker<? super E> pTicker) {
        return pExpectedType == pType ? (BlockEntityTicker<A>) pTicker : null;
    }

    @Override
    public boolean triggerEvent(BlockState state, Level level, BlockPos pos, int id, int param) {
        BlockEntity blockEntity = level.getBlockEntity(pos);
        return blockEntity instanceof BreadRackBlockEntity && blockEntity.triggerEvent(id, param);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(FACING,TYPE,SINGLE,OPEN);
    }
}
