package com.renyigesai.bakeries.block.magnetic_plate;

import com.renyigesai.bakeries.block.blender.BlenderBlockEntity;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class MagneticPlateBlock extends HorizontalDirectionalBlock implements EntityBlock {
    public MagneticPlateBlock(Properties pProperties) {
        super(pProperties);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH));
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return switch (state.getValue(FACING)) {
            default -> box(0, 0, 15, 16, 16, 16);
            case NORTH -> box(0, 0, 0, 16, 16, 1);
            case EAST -> box(15, 0, 0, 16, 16, 16);
            case WEST -> box(0, 0, 0, 1, 16, 16);
        };
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        if (pLevel.isClientSide){
            return InteractionResult.SUCCESS;
        }
        ItemStack itemInHand = pPlayer.getItemInHand(pHand);
        BlockEntity blockEntity = pLevel.getBlockEntity(pPos);

        if (!(blockEntity instanceof MagneticPlateBlockEntity mp)){
            return super.use(pState, pLevel, pPos, pPlayer, pHand, pHit);
        }

        if (itemInHand.isEmpty()) {
            if (pPlayer.isShiftKeyDown()){
                return onRotation(mp,pState,pLevel,pPos,pPlayer,pHand,pHit);
            }
            return onOutput(mp,pState,pLevel,pPos,pPlayer,pHand,pHit);
        }

        if (itemInHand.is(ItemTags.TOOLS)) {
            return onInput(mp,itemInHand,pState,pLevel,pPos,pPlayer,pHand,pHit);
        }
        return onSetBlock(mp,itemInHand,pState,pLevel,pPos,pPlayer,pHand,pHit);
    }

    public InteractionResult onRotation(MagneticPlateBlockEntity mp,BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit){
        mp.addRotationFlag();
        mp.setChanged();
        pLevel.sendBlockUpdated(pPos, pState, pState, Block.UPDATE_ALL);
        return InteractionResult.SUCCESS;
    }

    public InteractionResult onOutput(MagneticPlateBlockEntity mp,BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit){
        float[] hitUV = getSlotFromHit(pHit.getLocation(), pPos, pState.getValue(FACING),
                pHit.getDirection().getOpposite());
        float u = hitUV[0];
        float v = hitUV[1];
        int slot = getClosestSlot(mp, u, v);
        if (slot != -1) {
            ItemStack taken = mp.getItems().getStackInSlot(slot);
            if (!taken.isEmpty()) {
                if (!pPlayer.getInventory().add(taken.copy())) {
                    pPlayer.drop(taken.copy(), false);
                }
                mp.getItems().setStackInSlot(slot, ItemStack.EMPTY);
                mp.setChanged();
                pLevel.sendBlockUpdated(pPos, pState, pState, Block.UPDATE_ALL);
                return InteractionResult.sidedSuccess(pLevel.isClientSide);
            }
        }
        return InteractionResult.PASS;
    }

    public InteractionResult onInput(MagneticPlateBlockEntity mp,ItemStack itemInHand,BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand hand,BlockHitResult pHit){
        for (int i = 0; i < mp.getItems().getSlots(); i++) {
            ItemStack stackInSlot = mp.getItems().getStackInSlot(i);
            if (stackInSlot.isEmpty()) {
                float[] slotFromHit = getSlotFromHit(pHit.getLocation(), pPos,
                        pState.getValue(FACING), pHit.getDirection().getOpposite());
                if (i > 0) {
                    mp.setXyo1(slotFromHit);
                } else {
                    mp.setXyo0(slotFromHit);
                }
                mp.getItems().setStackInSlot(i, itemInHand.copy());
                itemInHand.shrink(1);
                mp.setChanged();
                pLevel.sendBlockUpdated(pPos, pState, pState, Block.UPDATE_ALL);
                pLevel.playSound(null,pPos, SoundEvents.METAL_PLACE, SoundSource.BLOCKS);
                return InteractionResult.SUCCESS;
            }
        }
        return InteractionResult.FAIL;
    }

    public InteractionResult onSetBlock(MagneticPlateBlockEntity mp,ItemStack itemInHand,BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand hand,BlockHitResult pHit){
        if (itemInHand.getItem() instanceof BlockItem blockItem){
            BlockState blockState = blockItem.getBlock().defaultBlockState();
            if (blockState.isCollisionShapeFullBlock(pLevel,pPos)){
                mp.setBlockId(BuiltInRegistries.BLOCK.getKey(blockItem.getBlock()).toString());
                mp.setChanged();
                pLevel.sendBlockUpdated(pPos, pState, pState, Block.UPDATE_ALL);
                pLevel.playSound(null,pPos,blockItem.getBlock().getSoundType(blockState).getPlaceSound(),SoundSource.BLOCKS);
            }
        }
        return InteractionResult.SUCCESS;
    }

    public float[]  getSlotFromHit(Vec3 hitPos, BlockPos blockPos, Direction facing, Direction hitFace) {
        if (hitFace == Direction.UP || hitFace == Direction.DOWN || hitFace != facing) {
            return new float[]{0,0};
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
            default    -> { return new float[]{0,0}; }

        }
        return new float[]{u,v};
    }

    private int getClosestSlot(MagneticPlateBlockEntity mp, float u, float v) {
        float[] xyo = mp.getXyo();
        int bestSlot = -1;
        double bestDist = 0.2 * 0.2;
        for (int slot = 0; slot < 2; slot++) {
            ItemStack stack = mp.getItems().getStackInSlot(slot);
            if (stack.isEmpty()){
                continue;
            }
            int idx = slot * 2;
            if (idx + 1 >= xyo.length){
                break;
            }
            float su = xyo[idx];
            float sv = xyo[idx + 1];
            double dist = (u - su) * (u - su) + (v - sv) * (v - sv);
            if (dist < bestDist) {
                bestDist = dist;
                bestSlot = slot;
            }
        }
        return bestSlot;
    }

    @Override
    public void onRemove(BlockState state, Level world, BlockPos pos, BlockState newState, boolean isMoving) {
        if (state.getBlock() != newState.getBlock()) {
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if (blockEntity instanceof MagneticPlateBlockEntity mp) {
                mp.drops(mp);
                world.updateNeighbourForOutputSignal(pos, this);
            }
            super.onRemove(state, world, pos, newState, isMoving);
        }
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(FACING);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        return this.defaultBlockState().setValue(FACING, pContext.getHorizontalDirection());
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new MagneticPlateBlockEntity(blockPos,blockState);
    }
}
