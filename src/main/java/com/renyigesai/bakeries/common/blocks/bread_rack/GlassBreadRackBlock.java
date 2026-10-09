package com.renyigesai.bakeries.common.blocks.bread_rack;

import com.renyigesai.bakeries.common.init.BakeriesBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
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
    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand hand, BlockHitResult hitResult) {
        if (pLevel.isClientSide) {
            return ItemInteractionResult.SUCCESS;
        }

        BlockEntity blockEntity = pLevel.getBlockEntity(pPos);
        if (!(blockEntity instanceof BreadRackBlockEntity rackBlock)) {
            return ItemInteractionResult.FAIL;
        }

        boolean open = pState.getValue(OPEN);

        int slot = getSlotFromHit(hitResult.getLocation(), pPos, pState.getValue(FACING), hitResult.getDirection().getOpposite());
        boolean clickedEmptySlot = slot != -1 && rackBlock.getItems().getStackInSlot(slot).isEmpty() && pPlayer.getItemInHand(hand).isEmpty();

        if (!open || pPlayer.isShiftKeyDown() || clickedEmptySlot) {
            boolean newOpen = !open;
            pLevel.blockEvent(pPos, pState.getBlock(), 0, newOpen ? 0 : 1);
            rackBlock.setOpen(newOpen);
            pLevel.playSound(null, pPos, newOpen ? SoundEvents.IRON_DOOR_OPEN : SoundEvents.IRON_DOOR_CLOSE, SoundSource.PLAYERS, 0.8F, 0.8F);
            return ItemInteractionResult.SUCCESS;
        }

        return super.useItemOn(stack, pState, pLevel, pPos, pPlayer, hand, hitResult);
    }



    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState, BlockEntityType<T> pBlockEntityType) {
        if (pLevel.isClientSide) {
            return createTickerHelper(pBlockEntityType, BakeriesBlocks.Entities.BREAD_RACK_ENTITY.get(), BreadRackBlockEntity::clientTick);
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
