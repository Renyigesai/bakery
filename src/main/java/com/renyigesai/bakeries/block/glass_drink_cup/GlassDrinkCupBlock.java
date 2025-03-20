package com.renyigesai.bakeries.block.glass_drink_cup;

import com.renyigesai.bakeries.init.BakeriesBlocks;
import com.renyigesai.bakeries.init.BakeriesSounds;
import com.renyigesai.bakeries.util.ItemUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.SoundType;
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
    public static final IntegerProperty STAGE = IntegerProperty.create("stage",0,4);

    private static final VoxelShape SHAPE = box(6.25, 0, 6.0, 9.75, 7.5, 9.5);

    public GlassDrinkCupBlock(Properties pProperties) {
        super(pProperties);
        this.registerDefaultState(defaultBlockState().setValue(STAGE,0).setValue(FACING, Direction.NORTH));
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
        return new GlassDrinkCupBlockEntity(pPos,pState);
    }

    @Override
    public boolean isRandomlyTicking(BlockState state) {
        return true;
    }

    public void spawnOrSetBlock(ItemStack stack, Level level, BlockPos pos){
        if (stack.getItem() instanceof BlockItem blockItem) {
            level.setBlock(pos,blockItem.getBlock().defaultBlockState(), 3);
            return;
        } else {
            ItemUtil.spawnItemEntity(level, stack, pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5, new Vec3(0.0, 0.0, 0.0));
        }
        level.removeBlock(pos,false);
    }

    @Override
    public InteractionResult use(BlockState pState, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult pHit) {
        BlockEntity blockEntity = level.getBlockEntity(pos);
        ItemStack stack = player.getItemInHand(hand);
        if (blockEntity instanceof GlassDrinkCupBlockEntity glassDrinkCupBlockEntity){
            if (!stack.isEmpty()) {
                glassDrinkCupBlockEntity.addItem(stack, player);
                spawnSound(level,pos,stack);
                return InteractionResult.SUCCESS;
            }else {
                ItemStack craftItem = glassDrinkCupBlockEntity.getCraftItem();
                if(!craftItem.isEmpty()) {
                    spawnOrSetBlock(craftItem,level,pos);
                    return InteractionResult.SUCCESS;
                }
            }
        }
        return super.use(pState, level, pos, player, hand, pHit);
    }

    public void spawnSound(Level level,BlockPos pos, ItemStack stack) {
        if (level instanceof ServerLevel serverLevel) {
            if (stack.getItem() instanceof BlockItem) {
                serverLevel.playSound(null,pos, BakeriesSounds.PUT_ON_ICE.get(), SoundSource.BLOCKS);
                return;
            }
            if (stack.hasCraftingRemainingItem()) {
                serverLevel.playSound(null,pos, SoundEvents.BOTTLE_EMPTY, SoundSource.BLOCKS);
                return;
            }
            return;
        }
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState, BlockEntityType<T> pBlockEntityType) {
        return pLevel.isClientSide ? null : createTickerHelper(pBlockEntityType, BakeriesBlocks.GLASS_DRINK_CUP_ENTITY.get(),
                GlassDrinkCupBlockEntity::tick);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        return this.defaultBlockState().setValue(FACING, pContext.getHorizontalDirection());
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(STAGE,FACING);
    }
}
