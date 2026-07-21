package com.renyigesai.bakeries.block.mix_block;

import com.renyigesai.bakeries.BakeriesMod;
import com.renyigesai.bakeries.api.block.PileBlock;
import com.renyigesai.bakeries.block.luminous_light_sign.LuminousLightSignBlockEntity;
import com.renyigesai.bakeries.block.toaster.ToasterBlockEntity;
import com.renyigesai.bakeries.init.BakeriesItems;
import com.renyigesai.bakeries.util.ItemUtils;
import com.renyigesai.bakeries.util.TextUtils;
import com.renyigesai.bakeries.util.measurer.ClientUtilsMeasurer;
import com.renyigesai.bakeries.util.measurer.IUtilsMeasurer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.DyeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.NameTagItem;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class MixBlock extends BaseEntityBlock {

    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
    public static final BooleanProperty TRAY = BooleanProperty.create("tray");

    public MixBlock() {
        super(BlockBehaviour.Properties.of().strength(0.5F,0.5F).sound(SoundType.WOOL).noOcclusion().isRedstoneConductor((bs, br, bp) -> false));
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(TRAY,false));
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
        builder.add(FACING,TRAY);
    }

    @Override
    public @NotNull VoxelShape getShape(@NotNull BlockState state, @NotNull BlockGetter level, @NotNull BlockPos pos, @NotNull CollisionContext context) {
        return Block.box(1.0D, 0.0D, 1.0D, 15.0D, 4.0D, 15.0D);
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        if (pLevel.isClientSide){
            return InteractionResult.SUCCESS;
        }
        if (!BakeriesMod.onAuxiliaryKey(pPlayer)){
            ItemStack itemInHand = pPlayer.getItemInHand(pHand);
            if (itemInHand.is(BakeriesItems.WOOD_TRAY.get())){
                pLevel.setBlock(pPos,pState.setValue(TRAY,true),3);
                return InteractionResult.SUCCESS;
            }
            if (addText(itemInHand,pLevel,pPos)){
                return InteractionResult.SUCCESS;
            }else if (setColor(itemInHand,pLevel,pPos,pPlayer)){
                return InteractionResult.SUCCESS;
            }
            return take(pLevel, pPos, pPlayer);
        }
        return super.use(pState, pLevel, pPos, pPlayer, pHand, pHit);
    }

    public InteractionResult take(Level pLevel, BlockPos pPos, Player pPlayer){
        BlockEntity blockEntity = pLevel.getBlockEntity(pPos);
        MixBlockEntity mix;
        if (!(blockEntity instanceof MixBlockEntity)){
            return InteractionResult.FAIL;
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
        if (outStack.getItem() instanceof BlockItem blockItem && blockItem.getBlock() instanceof PileBlock pileBlock){
            soundEvent = pileBlock.getTakeSound();
        }else {
            soundEvent = SoundEvents.WOOL_BREAK;
        }
        ItemUtils.givePlayerItem(pPlayer,outStack);
        pLevel.playSound(null,pPos,soundEvent, SoundSource.BLOCKS);
        return InteractionResult.SUCCESS;
    }

    private boolean addText(ItemStack itemInHand,Level level,BlockPos pos) {
        if (itemInHand.getItem() instanceof NameTagItem) {
            BlockEntity blockEntity = level.getBlockEntity(pos);
            if (blockEntity instanceof MixBlockEntity mix) {
                Component hoverName = itemInHand.getHoverName();
                IUtilsMeasurer utilsMeasurer = BakeriesMod.utilsMeasurer;
                if (utilsMeasurer instanceof ClientUtilsMeasurer clientUtilsMeasurer){
                    int length = clientUtilsMeasurer.getLength(hoverName.getString(),90);
                    String string = hoverName.getString(length);
                    mix.setText(string);
                    return true;
                }
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
    public ItemStack getCloneItemStack(BlockState state, HitResult target, BlockGetter level, BlockPos pos, Player player) {
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

    @Override
    public int getAnalogOutputSignal(BlockState pBlockState, Level pLevel, BlockPos pPos) {
        if (pLevel.getBlockEntity(pPos) instanceof MixBlockEntity mixBlock){
            int size = 0;
            for (int i = 0; i < mixBlock.getInventory().getSlots(); i++) {
                if (!mixBlock.getInventory().getStackInSlot(i).isEmpty()){
                    size ++;
                }
            }
            return size;
        }
        return 0;
    }

    @Override
    public boolean hasAnalogOutputSignal(BlockState pState) {
        return true;
    }
}
