package com.renyigesai.bakeries.block.blender;

import com.renyigesai.bakeries.block.state.BakeriesEnumProperty;
import com.renyigesai.bakeries.init.BakeriesBlocks;
import com.renyigesai.bakeries.init.BakeriesSounds;
import net.minecraft.core.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.network.NetworkHooks;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.Nullable;

public class BlenderBlock extends BaseEntityBlock {
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
    public static final BooleanProperty POWERED = BlockStateProperties.POWERED;
    public static final EnumProperty<BakeriesEnumProperty> SHAPE =  EnumProperty.create("shape", BakeriesEnumProperty.class);
    public static final VoxelShape X_BOX = box(4.0,0.0,0.0,12.0,16.0,16.0);
    public static final VoxelShape Z_BOX = box(0.0,0.0,4.0,16.0,16.0,12.0);
    public static final VoxelShape BOX = box(0.0,0.0,0.0,16.0,16.0,16.);
    public BlenderBlock(Properties pProperties) {
        super(pProperties);
        this.registerDefaultState(this.stateDefinition.any().setValue(POWERED,false).setValue(SHAPE,BakeriesEnumProperty.NONE).setValue(FACING, Direction.NORTH));
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        if (pState.getValue(SHAPE) == BakeriesEnumProperty.NONE){
            Direction direction = pState.getValue(FACING);
            return direction.getAxis() == Direction.Axis.X ? Z_BOX : X_BOX;
        }
        return BOX;
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState, BlockEntityType<T> pBlockEntityType) {
        return pLevel.isClientSide ? null : createTickerHelper(pBlockEntityType, BakeriesBlocks.BLENDER_ENTITY.get(),
                BlenderBlockEntity::craftTick);
    }

    @Override
    public RenderShape getRenderShape(BlockState pState) {
        return RenderShape.MODEL;
    }

    @Override
    public InteractionResult use(BlockState blockstate, Level world, BlockPos pos, Player entity, InteractionHand hand, BlockHitResult hit) {
        super.use(blockstate, world, pos, entity, hand, hit);
        ItemStack handStack = entity.getItemInHand(hand);
        if(!world.isClientSide()) {
            BlockEntity blockEntity = world.getBlockEntity(pos);
            super.use(blockstate, world, pos, entity, hand, hit);
            if (blockEntity instanceof BlenderBlockEntity blenderBlockEntity) {
                if (handStack.is(Items.REDSTONE_TORCH)){
                    boolean temp = blenderBlockEntity.compatibility;
                    blenderBlockEntity.compatibility = !temp;
                    world.playSound(null, pos, SoundEvents.ITEM_FRAME_ADD_ITEM, SoundSource.BLOCKS);
                    return InteractionResult.CONSUME;
                }
                if (getItemRegistryName(handStack).equals("create:brass_casing")){
                    world.setBlock(pos,blockstate.setValue(SHAPE,BakeriesEnumProperty.BRASS),3);
                    world.playSound(null, pos, SoundEvents.STONE_PLACE, SoundSource.BLOCKS);
                    return InteractionResult.CONSUME;
                }
                NetworkHooks.openScreen(((ServerPlayer) entity), blenderBlockEntity, pos);
                return InteractionResult.CONSUME;
            }else {
                throw new IllegalStateException("Our Container provider is missing!");
            }
        }
        return InteractionResult.SUCCESS;
    }

    public static String getItemRegistryName(ItemStack stack) {
        Item item = stack.getItem();
        ResourceLocation resourceLocation = ForgeRegistries.ITEMS.getKey(item);
        return resourceLocation != null ? resourceLocation.toString() : "null";
    }

    @Override
    public void onRemove(BlockState state, Level world, BlockPos pos, BlockState newState, boolean isMoving) {
        if (state.getBlock() != newState.getBlock()) {
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if (blockEntity instanceof BlenderBlockEntity blenderBlockEntity) {
                blenderBlockEntity.drops(blenderBlockEntity);
                world.updateNeighbourForOutputSignal(pos, this);
            }
            super.onRemove(state, world, pos, newState, isMoving);
        }
    }

    @Override
    public void animateTick(BlockState pState, Level pLevel, BlockPos pPos, RandomSource pRandom) {
        if (pState.getValue(POWERED)){
            double d0 = (double)pPos.getX() + 0.5D;
            double d1 = (double)pPos.getY() + 0.5D;
            double d2 = (double)pPos.getZ() + 0.5D;
            pLevel.playLocalSound(d0, d1, d2, BakeriesSounds.BLENDER.get(), SoundSource.BLOCKS, 1.0F, 1.0F, false);
        }
    }

    @Override
    public MenuProvider getMenuProvider(BlockState state, Level worldIn, BlockPos pos) {
        BlockEntity tileEntity = worldIn.getBlockEntity(pos);
        return tileEntity instanceof MenuProvider menuProvider ? menuProvider : null;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(POWERED,SHAPE,FACING);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        return this.defaultBlockState().setValue(FACING, pContext.getHorizontalDirection());
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new BlenderBlockEntity(pPos,pState);
    }
}
