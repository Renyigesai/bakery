package com.renyigesai.bakeries.common.blocks.toaster;

import com.mojang.serialization.MapCodec;
import com.renyigesai.bakeries.BakeriesMod;
import com.renyigesai.bakeries.common.init.BakeriesBlocks;
import com.renyigesai.bakeries.common.init.BakeriesSounds;
import com.renyigesai.bakeries.common.utils.ItemUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.crafting.CampfireCookingRecipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nullable;
import java.util.Optional;

public class ToasterBlock extends BaseEntityBlock {

    public static EnumProperty<State> STATE = EnumProperty.create("state", State.class);
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
    protected static final VoxelShape BOX = Block.box(1.0D, 0.0D, 1.0D, 15.0D, 2.0D, 15.0D);
    protected static final VoxelShape X_BOX = Block.box(3, 0, 4, 13, 7, 12);
    protected static final VoxelShape Z_BOX = Block.box(4, 0, 3, 12, 7, 13);

    public ToasterBlock(Properties pProperties) {
        super(pProperties);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(STATE,State.IDLE));
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return simpleCodec(ToasterBlock::new);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        Direction direction = state.getValue(FACING);
        return direction.getAxis() == Direction.Axis.X ? Z_BOX : X_BOX;
    }


    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(FACING,STATE);
    }

    @Override
    public RenderShape getRenderShape(BlockState pState) {
        return RenderShape.MODEL;
    }


    @Override
    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        BlockEntity blockEntity = level.getBlockEntity(pos);
        if (blockEntity instanceof ToasterBlockEntity toaster){
            if (!player.getItemInHand(hand).isEmpty()){
                ItemStack itemInHand = player.getItemInHand(hand);
                Optional<RecipeHolder<CampfireCookingRecipe>> smokerRecipe = toaster.getSmokerRecipe(itemInHand);
                if (smokerRecipe.isPresent() && state.getValue(STATE) == State.IDLE){
                    toaster.addItem(itemInHand.copy(),smokerRecipe.get().value().getCookingTime());
                    ItemUtils.shrink(itemInHand,1,player);
                    playSound(level,pos, SoundEvents.ITEM_FRAME_ADD_ITEM);
                    return ItemInteractionResult.SUCCESS;
                }else {
                    return ItemInteractionResult.FAIL;
                }
            }else {
                if (BakeriesMod.onAuxiliaryKey(player)) {
                    toaster.getItem(player);
                    level.setBlock(pos, state.setValue(STATE, State.IDLE), 3);
                    playSound(level, pos, SoundEvents.ITEM_FRAME_REMOVE_ITEM);
                    return ItemInteractionResult.SUCCESS;
                }
                if (state.getValue(STATE) == State.IDLE) {
                    toaster.changeState(0);
                    level.setBlock(pos, state.setValue(STATE, State.LIT), 3);
                    playSound(level, pos, BakeriesSounds.TOASTER_IN.get());
                    return ItemInteractionResult.SUCCESS;
                }
            }
        }
        return super.useItemOn(stack, state, level, pos, player, hand, hitResult);
    }

    private void playSound(Level level, BlockPos pos, SoundEvent soundEvent){
        if (level instanceof ServerLevel serverLevel) {
            serverLevel.playSound(null, pos, soundEvent, SoundSource.BLOCKS);
        }
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        return this.defaultBlockState().setValue(FACING, pContext.getHorizontalDirection());
    }

    @Nullable
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState, BlockEntityType<T> pBlockEntityType) {
        return pLevel.isClientSide ? createTickerHelper(pBlockEntityType, BakeriesBlocks.Entities.TOASTER_ENTITY.get(), ToasterBlockEntity::clientTick) :
                                     createTickerHelper(pBlockEntityType, BakeriesBlocks.Entities.TOASTER_ENTITY.get(), ToasterBlockEntity::tick);
    }

    @Override
    public void animateTick(BlockState pState, Level pLevel, BlockPos pPos, RandomSource pRandom) {
        super.animateTick(pState, pLevel, pPos, pRandom);
        if (pState.getValue(STATE) == State.LIT){
            double d0 = (double)pPos.getX() + 0.4D + (double)pRandom.nextFloat() * 0.2D;
            double d1 = (double)pPos.getY() + 0.45D + (double)pRandom.nextFloat() * 0.3D;
            double d2 = (double)pPos.getZ() + 0.4D + (double)pRandom.nextFloat() * 0.2D;
            pLevel.addParticle(ParticleTypes.SMOKE, d0, d1, d2, 0.0D, 0.0D, 0.0D);
            if (pRandom.nextDouble() < 0.3D) {
                pLevel.playLocalSound(d0, d1, d2, SoundEvents.FURNACE_FIRE_CRACKLE, SoundSource.BLOCKS, 1.0F, 1.0F, false);
            }
        }
    }

    @Override
    public void onRemove(BlockState state, Level world, BlockPos pos, BlockState newState, boolean isMoving) {
        if (state.getBlock() != newState.getBlock()) {
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if (blockEntity instanceof ToasterBlockEntity toaster) {
                toaster.drops();
                world.updateNeighbourForOutputSignal(pos, this);
            }
            super.onRemove(state, world, pos, newState, isMoving);
        }
    }

    @Override
    public BlockState mirror(BlockState pState, Mirror pMirror) {
        return pState.rotate(pMirror.getRotation(pState.getValue(FACING)));
    }

    @Override
    public BlockState rotate(BlockState state, LevelAccessor level, BlockPos pos, Rotation direction) {
        return state.setValue(FACING,direction.rotate(state.getValue(FACING)));
    }

    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new ToasterBlockEntity(pPos, pState);
    }

    public enum State implements StringRepresentable {
        IDLE("idle"),
        LIT("lit"),
        FINISH("finish");

        private final String name;

        State(String name) {
            this.name = name;
        }

        @Override
        public String getSerializedName() {
            return this.name;
        }
    }

}
