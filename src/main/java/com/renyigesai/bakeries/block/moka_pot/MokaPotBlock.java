package com.renyigesai.bakeries.block.moka_pot;

import com.renyigesai.bakeries.init.BakeriesBlocks;
import com.renyigesai.bakeries.init.BakeriesItems;
import com.renyigesai.bakeries.util.ItemUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class MokaPotBlock extends BaseEntityBlock {
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
    private static final VoxelShape SHAPE = box(6.0, 0, 6.0, 10, 6.5, 10);

    public MokaPotBlock(Properties pProperties) {
        super(pProperties);
        this.registerDefaultState(defaultBlockState().setValue(FACING, Direction.NORTH));
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return SHAPE;
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState, BlockEntityType<T> pBlockEntityType) {
        return pLevel.isClientSide ? null : createTickerHelper(pBlockEntityType, BakeriesBlocks.MOKA_POT_ENTITY.get(),
                MokaPotBlockEntity::craftTick);
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        BlockEntity blockEntity = pLevel.getBlockEntity(pPos);
        ItemStack handStack = pPlayer.getItemInHand(pHand);
        if (blockEntity instanceof MokaPotBlockEntity mokaPotBlockEntity) {
            if (!handStack.isEmpty()) {
                if (mokaPotBlockEntity.isInventoryFull() && handStack.is(ItemTags.create(new ResourceLocation("forge:coffee_grounds")))) {
                    mokaPotBlockEntity.addGroundCoffee(handStack.copy().split(1));
                    handStack.shrink(1);
                    pLevel.playSound(null, pPos, SoundEvents.WOOL_STEP, SoundSource.PLAYERS, 0.8F, 0.8F);
                    return InteractionResult.SUCCESS;
                }
            } else {
                pLevel.removeBlock(pPos, false);
                ItemUtils.givePlayerItem(pPlayer, new ItemStack(getMokaPotItem(mokaPotBlockEntity).getItem()));
                pLevel.playSound(null,pPos, SoundEvents.ITEM_FRAME_REMOVE_ITEM, SoundSource.BLOCKS);
                return InteractionResult.SUCCESS;
            }
        }
        return super.use(pState, pLevel, pPos, pPlayer, pHand, pHit);
    }

    private ItemStack getMokaPotItem(MokaPotBlockEntity mokaPotBlockEntity){
        if (mokaPotBlockEntity.getFill()){
            return new ItemStack(BakeriesItems.MOKA_POT_FILL.get());
        }
        return new ItemStack(BakeriesItems.MOKA_POT.get());
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        return this.defaultBlockState().setValue(FACING, pContext.getHorizontalDirection());
    }

    @Override
    public void animateTick(BlockState pState, Level pLevel, BlockPos pPos, RandomSource pRandom) {
        BlockEntity blockEntity = pLevel.getBlockEntity(pPos);
        if (blockEntity instanceof MokaPotBlockEntity mokaPotBlockEntity && mokaPotBlockEntity.getCookingTotalTime() > 0){
            double d0 = (double)pPos.getX() + 0.4D + (double)pRandom.nextFloat() * 0.2D;
            double d1 = (double)pPos.getY() + 0.5D + (double)pRandom.nextFloat() * 0.3D;
            double d2 = (double)pPos.getZ() + 0.4D + (double)pRandom.nextFloat() * 0.2D;
            pLevel.addParticle(ParticleTypes.SMOKE, d0, d1, d2, 0.0D, 0.0D, 0.0D);
        }
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(FACING);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new MokaPotBlockEntity(pPos,pState);
    }
}
