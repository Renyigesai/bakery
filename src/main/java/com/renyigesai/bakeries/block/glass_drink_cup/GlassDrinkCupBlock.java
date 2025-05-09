package com.renyigesai.bakeries.block.glass_drink_cup;

import com.renyigesai.bakeries.init.BakeriesBlocks;
import com.renyigesai.bakeries.init.BakeriesSounds;
import com.renyigesai.bakeries.util.ItemUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class GlassDrinkCupBlock extends BaseEntityBlock {
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;

    private static final VoxelShape SHAPE = box(6.0, 0, 6.0, 10, 7.5, 10);

    public GlassDrinkCupBlock(Properties pProperties) {
        super(pProperties);
        this.registerDefaultState(defaultBlockState().setValue(FACING, Direction.NORTH));
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return SHAPE;
    }

    @Override
    public RenderShape getRenderShape(BlockState pState) {
        return RenderShape.MODEL;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new GlassDrinkCupBlockEntity(pPos, pState);
    }

    @Override
    public InteractionResult use(BlockState pState, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult pHit) {
        BlockEntity blockEntity = level.getBlockEntity(pos);
        ItemStack stack = player.getItemInHand(hand);
        if (blockEntity instanceof GlassDrinkCupBlockEntity glassDrinkCupBlockEntity) {
            if (!stack.isEmpty() && glassDrinkCupBlockEntity.isInventoryFull()) {
                glassDrinkCupBlockEntity.addItem(stack.copy().split(1), player);
                if (level instanceof ServerLevel serverLevel) {
                    serverLevel.playSound(null, pos, getSound(stack), SoundSource.BLOCKS);
                }
                stack.shrink(1);
                return InteractionResult.SUCCESS;
            } else {
                ItemStack craftItem = glassDrinkCupBlockEntity.inventory.getStackInSlot(4);
                if (!craftItem.isEmpty()) {
                    spawnOrSetBlock(craftItem, level, pos);
                    if (level instanceof ServerLevel serverLevel) {
                        serverLevel.playSound(null, pos, BakeriesSounds.INSERT_STRAW.get(), SoundSource.BLOCKS);
                    }
                    return InteractionResult.SUCCESS;
                }
            }
        }
        return super.use(pState, level, pos, player, hand, pHit);
    }

    @Override
    public void onRemove(BlockState state, Level world, BlockPos pos, BlockState newState, boolean isMoving) {
        if (state.getBlock() != newState.getBlock()) {
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if (blockEntity instanceof GlassDrinkCupBlockEntity glassDrinkCupBlockEntity) {
                glassDrinkCupBlockEntity.drops(glassDrinkCupBlockEntity);
                world.updateNeighbourForOutputSignal(pos, this);
            }
            super.onRemove(state, world, pos, newState, isMoving);
        }
    }

    public void spawnOrSetBlock(ItemStack stack, Level level, BlockPos pos) {
        BlockEntity blockEntity = level.getBlockEntity(pos);
        if (blockEntity instanceof GlassDrinkCupBlockEntity glassDrinkCupBlockEntity) {
            glassDrinkCupBlockEntity.removeItems();
        }
        if (stack.getItem() instanceof BlockItem blockItem && !level.getBlockState(pos.below()).is(Blocks.HOPPER)) {
            level.setBlock(pos, blockItem.getBlock().defaultBlockState(), 3);
            return;
        } else {
            ItemUtil.spawnItemEntity(level, stack, pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5, new Vec3(0.0, 0.0, 0.0));
        }
        level.removeBlock(pos, false);
    }

    public SoundEvent getSound(ItemStack stack) {
        if (stack.getItem() instanceof BlockItem) {
            return BakeriesSounds.PUT_ON_ICE.get();
        } else {
            return SoundEvents.BOTTLE_EMPTY;
        }
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState, BlockEntityType<T> pBlockEntityType) {
        return pLevel.isClientSide ? null : createTickerHelper(pBlockEntityType, BakeriesBlocks.DRINK_CUP_ENTITY.get(),
                GlassDrinkCupBlockEntity::tick);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        return this.defaultBlockState().setValue(FACING, pContext.getHorizontalDirection());
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(FACING);
    }
}
