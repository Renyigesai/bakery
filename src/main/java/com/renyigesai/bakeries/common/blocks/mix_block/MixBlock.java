package com.renyigesai.bakeries.common.blocks.mix_block;

import com.mojang.serialization.MapCodec;
import com.renyigesai.bakeries.BakeriesMod;
import com.renyigesai.bakeries.api.block.AbstractPileBlock;
import com.renyigesai.bakeries.common.init.BakeriesItems;
import com.renyigesai.bakeries.common.utils.ItemUtils;
import com.renyigesai.bakeries.common.utils.TextUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.neoforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class MixBlock extends BaseEntityBlock {

    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
    public static final BooleanProperty TRAY = BooleanProperty.create("tray");

    public MixBlock() {
        super(Properties.of().strength(0.5F,0.5F).sound(SoundType.WOOL).noOcclusion().isRedstoneConductor((bs, br, bp) -> false));
        this.registerDefaultState(this.stateDefinition.any().setValue(TRAY,false).setValue(FACING, Direction.NORTH));
    }

    public MixBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return simpleCodec(MixBlock::new);
    }

    public RenderShape getRenderShape(BlockState pState) {
        return RenderShape.INVISIBLE;
    }

    @Override
    public boolean addLandingEffects(BlockState state1, ServerLevel level, BlockPos pos, BlockState state2, LivingEntity entity, int numberOfParticles) {
        return true;
    }

    @Override
    public boolean addRunningEffects(BlockState state, Level level, BlockPos pos, Entity entity) {
        return true;
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
    public boolean canSurvive(@NotNull BlockState state, LevelReader level, BlockPos pos) {
        return level.getBlockState(pos.below()).isSolid();
    }
    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(TRAY,FACING);
    }

    @Override
    public @NotNull VoxelShape getShape(@NotNull BlockState state, @NotNull BlockGetter level, @NotNull BlockPos pos, @NotNull CollisionContext context) {
        return Block.box(1.0D, 0.0D, 1.0D, 15.0D, 4.0D, 15.0D);
    }

    @Override
    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        if (level.isClientSide){
            return ItemInteractionResult.SUCCESS;
        }
        if (!BakeriesMod.onAuxiliaryKey(player)){
            ItemStack itemInHand = player.getItemInHand(hand);
            if (itemInHand.is(BakeriesItems.WOOD_TRAY.get())){
                level.setBlock(pos,state.setValue(TRAY,true),3);
                return ItemInteractionResult.SUCCESS;
            }
            if (addText(itemInHand,level,pos)){
                return ItemInteractionResult.SUCCESS;
            }else if (setColor(itemInHand,level,pos,player)){
                return ItemInteractionResult.SUCCESS;
            }
            return take(level, pos, player);
        }
        return super.useItemOn(stack, state, level, pos, player, hand, hitResult);
    }

    public ItemInteractionResult take(Level pLevel, BlockPos pPos, Player pPlayer){
        BlockEntity blockEntity = pLevel.getBlockEntity(pPos);
        MixBlockEntity mix;
        if (!(blockEntity instanceof MixBlockEntity)){
            return ItemInteractionResult.FAIL;
        }
        mix = (MixBlockEntity) blockEntity;
        ItemStackHandler inventory = mix.getInventory();
        ItemStack outStack;
        int inventoryCount = mix.getInventoryCount();
        ItemStack stackInSlot = inventory.getStackInSlot(inventoryCount - 1);
        outStack = stackInSlot.copy();
        inventory.setStackInSlot(inventoryCount - 1,ItemStack.EMPTY);
        mix.updateBlock();
        if (inventoryCount == 1){
            pLevel.removeBlock(pPos,false);
        }
        SoundEvent soundEvent;
        if (outStack.getItem() instanceof BlockItem blockItem && blockItem.getBlock() instanceof AbstractPileBlock pileBlock){
            soundEvent = pileBlock.getTakeSound();
        }else {
            soundEvent = SoundEvents.ITEM_FRAME_REMOVE_ITEM;
        }
        ItemUtils.givePlayerItem(pPlayer,outStack);
        pLevel.playSound(null,pPos,soundEvent, SoundSource.BLOCKS);
        return ItemInteractionResult.SUCCESS;
    }

    private boolean addText(ItemStack itemInHand,Level level,BlockPos pos) {
        if (itemInHand.getItem() instanceof NameTagItem) {
            BlockEntity blockEntity = level.getBlockEntity(pos);
            if (blockEntity instanceof MixBlockEntity mix) {
                Component hoverName = itemInHand.getHoverName();
                int length = TextUtils.getLength(hoverName.getString(),90);
                String string = hoverName.getString(length);
                mix.setText(string);
                return true;
            }
        }
        return false;
    }

    private boolean setColor(ItemStack itemInHand,Level level,BlockPos pos,Player player){
        if (itemInHand.getItem() instanceof DyeItem dye){
            BlockEntity blockEntity = level.getBlockEntity(pos);
            if (blockEntity instanceof MixBlockEntity mix){
                mix.setColor(dye.getDyeColor().getTextColor());
                ItemUtils.shrink(itemInHand,1,player);
                return true;
            }
        }
        return false;
    }

    @Override
    public void onRemove(BlockState state, Level world, BlockPos pos, BlockState newState, boolean isMoving) {
        if (state.getBlock() != newState.getBlock()) {
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if (blockEntity instanceof MixBlockEntity mix) {
                mix.drops(mix);
                world.updateNeighbourForOutputSignal(pos, this);
            }
            super.onRemove(state, world, pos, newState, isMoving);
        }
    }

    @Override
    public ItemStack getCloneItemStack(BlockState state, HitResult target, LevelReader level, BlockPos pos, Player player) {
        BlockEntity blockEntity = level.getBlockEntity(pos);
        if (blockEntity instanceof MixBlockEntity mix){
            return new ItemStack(mix.getInventory().getStackInSlot(0).getItem());
        }
        return super.getCloneItemStack(state, target, level, pos, player);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new MixBlockEntity(pPos,pState);
    }
}
