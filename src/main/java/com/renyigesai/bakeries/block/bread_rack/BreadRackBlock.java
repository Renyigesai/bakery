package com.renyigesai.bakeries.block.bread_rack;

import com.renyigesai.bakeries.block.HorizontalConnectBlock;
import com.renyigesai.bakeries.util.ItemUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class BreadRackBlock extends HorizontalConnectBlock implements EntityBlock {

    private static final VoxelShape BOX = box(0.0D, 0.0D, 0.0D, 16.0D, 15.0D, 16.0D);

    public BreadRackBlock(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public @NotNull VoxelShape getShape(@NotNull BlockState state, @NotNull BlockGetter level, @NotNull BlockPos pos, @NotNull CollisionContext context) {
        return BOX;
    }

    @Override
    public RenderShape getRenderShape(BlockState pState) {
        return RenderShape.MODEL;
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        if (pLevel.isClientSide){
            return InteractionResult.SUCCESS;
        }
        ItemStack itemInHand = pPlayer.getItemInHand(pHand);
        BlockEntity blockEntity = pLevel.getBlockEntity(pPos);
        if (blockEntity instanceof BreadRackBlockEntity rackBlock){
            return putOrTake(rackBlock,pPlayer,pState,pLevel,pPos,itemInHand,pHit) ? InteractionResult.SUCCESS : InteractionResult.CONSUME;
        }
        return super.use(pState, pLevel, pPos, pPlayer, pHand, pHit);
    }

    public boolean putOrTake(BreadRackBlockEntity rackBlock,Player player, BlockState pState, Level pLevel, BlockPos pPos, ItemStack itemInHand, BlockHitResult hitResult){
        int slot = getSlotFromHit(hitResult.getLocation(), pPos, pState.getValue(FACING), hitResult.getDirection().getOpposite());
        if (slot == -1) {
            return false;
        }
        ItemStack stackInSlot = rackBlock.getItems().getStackInSlot(slot);
        if (stackInSlot.isEmpty()){
            if (!itemInHand.isEmpty()){
                ItemStack copy = itemInHand.copy();
                copy.setCount(1);
                if (rackBlock.putItem(slot,copy)){
                    ItemUtils.shrink(itemInHand,1,player);
                    pLevel.playSound(null,pPos, SoundEvents.ITEM_FRAME_ADD_ITEM, SoundSource.BLOCKS);
                    return true;
                }
            }
            return false;
        }
        ItemUtils.givePlayerItem(player,stackInSlot.copy());
        rackBlock.setItem(slot,ItemStack.EMPTY);
        pLevel.playSound(null,pPos, SoundEvents.ITEM_FRAME_REMOVE_ITEM, SoundSource.BLOCKS);
        return true;
    }

    @Override
    public void onRemove(BlockState state, Level world, BlockPos pos, BlockState newState, boolean isMoving) {
        if (state.getBlock() != newState.getBlock()) {
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if (blockEntity instanceof BreadRackBlockEntity rackBlock) {
                rackBlock.drops(rackBlock);
                world.updateNeighbourForOutputSignal(pos, this);
            }
            super.onRemove(state, world, pos, newState, isMoving);
        }
    }

    public int getSlotFromHit(Vec3 hitPos, BlockPos blockPos, Direction facing, Direction hitFace) {
        if (hitFace == Direction.UP || hitFace == Direction.DOWN || hitFace != facing) {
            return -1;
        }
        double relX = hitPos.x - blockPos.getX();
        double relY = hitPos.y - blockPos.getY();
        double relZ = hitPos.z - blockPos.getZ();
        float u, v;
        switch (facing) {
            case NORTH -> { u = 1f - (float) relX;  v = (float) relY; }
            case SOUTH -> { u = (float) relX;        v = (float) relY; }
            case WEST  -> { u = (float) relZ;        v = (float) relY; }
            case EAST  -> { u = 1f - (float) relZ;  v = (float) relY; }
            default    -> { return -1; }
        }
        if (u < 0.5f && v < 0.5f){
            return 1;
        }
        if (u >= 0.5f && v < 0.5f){
            return 0;
        }
        if (u < 0.5f && v >= 0.5f){
            return 3;
        }
        return 2;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new BreadRackBlockEntity(blockPos,blockState);
    }
}
