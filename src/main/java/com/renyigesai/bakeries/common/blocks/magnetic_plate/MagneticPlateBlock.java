package com.renyigesai.bakeries.common.blocks.magnetic_plate;

import com.mojang.serialization.MapCodec;
import com.renyigesai.bakeries.common.utils.ItemUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.TagKey;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class MagneticPlateBlock extends HorizontalDirectionalBlock implements EntityBlock {

    public static BooleanProperty CONTENT;
    public static final ResourceLocation SKILLET;

    public MagneticPlateBlock(Properties pProperties) {
        super(pProperties);
        this.registerDefaultState(this.stateDefinition.any().setValue(CONTENT,false).setValue(FACING, Direction.NORTH));
    }

    @Override
    protected MapCodec<? extends HorizontalDirectionalBlock> codec() {
        return null;
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
    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        if (pLevel.isClientSide){
            return ItemInteractionResult.SUCCESS;
        }
        ItemStack itemInHand = pPlayer.getItemInHand(pHand);
        BlockEntity blockEntity = pLevel.getBlockEntity(pPos);

        if (!(blockEntity instanceof MagneticPlateBlockEntity mp)){
            return super.useItemOn(stack, pState, pLevel, pPos, pPlayer, pHand, pHit);
        }

        float[] hitUV = getSlotFromHit(pHit.getLocation(), pPos, pState.getValue(FACING), pHit.getDirection().getOpposite());

        int slot = getClosestSlot(mp, hitUV[0], hitUV[1]);
        if (slot != -1) {
            return onOutput(mp, pState, pLevel, pPos, pPlayer, pHand, pHit, hitUV);
        }

        if (itemInHand.getItem() instanceof BlockItem blockItem) {
            BlockState blockState = blockItem.getBlock().defaultBlockState();
            if (blockState.isCollisionShapeFullBlock(pLevel, pPos)) {
                return onSetBlock(mp, itemInHand, pState, pLevel, pPos, pPlayer, pHand, pHit,pLevel);
            }
        }

        if (!itemInHand.isEmpty() && (itemInHand.is(TagKey.create(Registries.ITEM,ResourceLocation.fromNamespaceAndPath("c","tools")))) || BuiltInRegistries.ITEM.getKey(itemInHand.getItem()).equals(SKILLET)) {
            return onInput(mp, itemInHand, pState, pLevel, pPos, pPlayer, pHand, pHit, hitUV);
        }

        return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
    }

    public ItemInteractionResult onOutput(MagneticPlateBlockEntity mp, BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit, float[] uv){
        float u = uv[0];
        float v = uv[1];
        int slot = getClosestSlot(mp, u, v);
        if (slot != -1) {
            ItemStack taken = mp.getItems().getStackInSlot(slot);
            if (!taken.isEmpty()) {
                ItemUtils.givePlayerItem(pPlayer,taken.copy());
                mp.getItems().setStackInSlot(slot, ItemStack.EMPTY);
                mp.update();
                return ItemInteractionResult.sidedSuccess(pLevel.isClientSide);
            }
        }
        return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
    }

    public ItemInteractionResult onInput(MagneticPlateBlockEntity mp,ItemStack itemInHand,BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand hand,BlockHitResult pHit,float[] uv){
        for (int i = 0; i < mp.getItems().getSlots(); i++) {
            ItemStack stackInSlot = mp.getItems().getStackInSlot(i);
            if (stackInSlot.isEmpty()) {
                if (i > 0) {
                    mp.setXyo1(uv);
                } else {
                    mp.setXyo0(uv);
                }
                mp.getItems().setStackInSlot(i, itemInHand.copy());
                itemInHand.shrink(1);
                mp.update();
                pLevel.playSound(null,pPos, SoundEvents.METAL_PLACE, SoundSource.BLOCKS);
                return ItemInteractionResult.SUCCESS;
            }
        }
        return ItemInteractionResult.CONSUME;
    }

    public ItemInteractionResult onSetBlock(MagneticPlateBlockEntity mp,ItemStack itemInHand,BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand hand,BlockHitResult pHit,Level level){
        if (itemInHand.getItem() instanceof BlockItem blockItem){
            BlockState blockState = blockItem.getBlock().defaultBlockState();
            if (blockState.isCollisionShapeFullBlock(pLevel,pPos)){
                mp.setBlockId(BuiltInRegistries.BLOCK.getKey(blockItem.getBlock()).toString());
                pLevel.setBlock(pPos,pState.setValue(CONTENT,true),3);
                mp.setChanged();
                pLevel.sendBlockUpdated(pPos, pState, pState, Block.UPDATE_ALL);
                pLevel.playSound(null,pPos,blockItem.getBlock().getSoundType(blockState,level,pPos,null).getPlaceSound(),SoundSource.BLOCKS);
            }
        }
        return ItemInteractionResult.SUCCESS;
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
        pBuilder.add(CONTENT,FACING);
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

    @Override
    public RenderShape getRenderShape(BlockState pState) {
        return pState.getValue(CONTENT) ? RenderShape.ENTITYBLOCK_ANIMATED : super.getRenderShape(pState);
    }

    @Override
    public SoundType getSoundType(BlockState state, LevelReader level, BlockPos pos, @Nullable Entity entity) {
        if (!state.getValue(CONTENT)){
            return super.getSoundType(state, level, pos, entity);
        }
        BlockEntity blockEntity = level.getBlockEntity(pos);
        if (blockEntity instanceof MagneticPlateBlockEntity mp){
            BlockState blockState = mp.getBlock().defaultBlockState();
            return mp.getBlock().getSoundType(blockState,level,pos,null);
        }
        return super.getSoundType(state, level, pos, entity);
    }

    static {
        CONTENT = BooleanProperty.create("content");
        SKILLET = ResourceLocation.fromNamespaceAndPath("farmersdelight","skillet");
    }
}
