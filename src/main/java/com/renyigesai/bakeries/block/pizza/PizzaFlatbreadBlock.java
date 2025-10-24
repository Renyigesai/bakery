package com.renyigesai.bakeries.block.pizza;

import com.renyigesai.bakeries.block.blender.BlenderBlockEntity;
import com.renyigesai.bakeries.init.BakeriesBlocks;
import com.renyigesai.bakeries.init.BakeriesParticleTypes;
import com.renyigesai.bakeries.item.StoneKilnShovelItem;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.ParticleUtils;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class PizzaFlatbreadBlock extends BaseEntityBlock {
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
    public static final BooleanProperty SAUCE = BooleanProperty.create("sauce");
    public static final VoxelShape BOX = box(2.0D, 0.0D, 2.0D, 14.0D, 2.0D, 14.0D);
    public PizzaFlatbreadBlock() {
        super(BlockBehaviour.Properties.copy(Blocks.CAKE));
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(SAUCE, false));
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return BOX;
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        BlockEntity blockEntity = pLevel.getBlockEntity(pPos);
        if (!(blockEntity instanceof PizzaFlatbreadBlockEntity)) {
            return super.use(pState, pLevel, pPos, pPlayer, pHand, pHit);
        }
        ItemStack itemInHand = pPlayer.getItemInHand(pHand);
        PizzaFlatbreadBlockEntity pizza = (PizzaFlatbreadBlockEntity) blockEntity;
        if (pizza.isSynthesis()){
            if (itemInHand.getItem() instanceof StoneKilnShovelItem shovelItem){
                shovelItem.addItem(itemInHand,pizza.getSynthesisItem());
                pizza.removeItems();
                pLevel.removeBlock(pPos,false);
                useEvent(2,pLevel,pPos);
                return InteractionResult.SUCCESS;
            }
            if (pizza.startSynthesis()){
                useEvent(2,pLevel,pPos);
                return InteractionResult.SUCCESS;
            }
            pPlayer.displayClientMessage(Component.translatable("tip.bakeries.pizza_stone_kiln_shovel"), true);
            return InteractionResult.SUCCESS;
        }

        if (!pState.getValue(SAUCE)){
            if (itemInHand.is(ItemTags.create(new ResourceLocation("forge", "crops/tomato")))){
                pLevel.scheduleTick(pPos, this, 8);
                if (!pPlayer.getAbilities().instabuild){
                    itemInHand.shrink(1);
                }
//                pLevel.addParticle(BakeriesParticleTypes.TOMATO_SAUCE.get(), pPos.getX() + 0.5, pPos.getY() + 0.063, pPos.getZ() + 0.5, 0, 0, 0);
                useEvent(4,pLevel,pPos);
                return InteractionResult.SUCCESS;
            }else {
                pPlayer.displayClientMessage(Component.translatable("tip.bakeries.pizza_tomato"), true);
                return super.use(pState, pLevel, pPos, pPlayer, pHand, pHit);
            }
        }
        boolean flag = false;
        boolean isCheese = false;
            if (itemInHand.is(ItemTags.create(new ResourceLocation("forge", "cheese")))){
                if (pizza.getCheeses().getStackInSlot(0).isEmpty()){
                    pizza.getCheeses().setStackInSlot(0,itemInHand.copy());
                    pizza.updateBlock();
                    flag = true;
                    isCheese = true;
                }
            }else {
                if (pizza.getCheeses().getStackInSlot(0).isEmpty()) {
                    flag = pizza.addItem(itemInHand.copy(),pPlayer);
                }
            }
            if (flag) {
                if (!pPlayer.getAbilities().instabuild){
                    itemInHand.shrink(1);
                }
                if (isCheese){
                    useEvent(3,pLevel,pPos);
                }else {
                    useEvent(1,pLevel,pPos);
                }
                return InteractionResult.SUCCESS;
            }else {
                pPlayer.displayClientMessage(Component.translatable("tip.bakeries.pizza_cheese"), true);
            }
            return super.use(pState, pLevel, pPos, pPlayer, pHand, pHit);
    }

    private void useEvent(int state,Level level,BlockPos pos) {
        switch (state){
            case 0:
                level.playSound(null, pos, SoundEvents.COMPOSTER_READY, SoundSource.BLOCKS, 0.8F, 0.8F);
                break;
            case 1:
                level.playSound(null, pos, SoundEvents.COMPOSTER_FILL, SoundSource.BLOCKS, 0.8F, 0.8F);
                break;
            case 2:
                level.playSound(null, pos, SoundEvents.COMPOSTER_EMPTY, SoundSource.BLOCKS, 0.8F, 0.8F);
                break;
            case 3:
                level.playSound(null, pos, SoundEvents.COMPOSTER_EMPTY, SoundSource.BLOCKS, 0.8F, 0.8F);
                if (level instanceof ServerLevel serverLevel){
                    serverLevel.sendParticles(ParticleTypes.WAX_ON,pos.getX() + 0.5,pos.getY(),pos.getZ() + 0.5,32,0.5,0.5,0.5,0.0025);
                }
                break;
            case 4:
                level.playSound(null, pos, SoundEvents.COMPOSTER_READY, SoundSource.BLOCKS, 0.8F, 0.8F);
                if (level instanceof ServerLevel serverLevel){
                    serverLevel.sendParticles(BakeriesParticleTypes.TOMATO_SAUCE.get(), pos.getX() + 0.5, pos.getY() + 0.063, pos.getZ() + 0.5,1,0,0,0,0);
                }
        }
    }

    @Override
    public void onRemove(BlockState state, Level world, BlockPos pos, BlockState newState, boolean isMoving) {
        if (state.getBlock() != newState.getBlock()) {
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if (blockEntity instanceof PizzaFlatbreadBlockEntity pizza) {
                pizza.drops(pizza);
                world.updateNeighbourForOutputSignal(pos, this);
            }
            super.onRemove(state, world, pos, newState, isMoving);
        }
    }

    @Override
    public void tick(BlockState pState, ServerLevel pLevel, BlockPos pPos, RandomSource pRandom) {
        super.tick(pState, pLevel, pPos, pRandom);
        if (!pState.getValue(SAUCE)){
            pLevel.setBlock(pPos, pState.setValue(SAUCE, true), 3);
        }
    }

    @Override
    public RenderShape getRenderShape(BlockState pState) {
        return RenderShape.MODEL;
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        return this.defaultBlockState().setValue(FACING, pContext.getHorizontalDirection());
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new PizzaFlatbreadBlockEntity(pPos, pState);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(FACING,SAUCE);
    }

    @Override
    public BlockState mirror(BlockState pState, Mirror pMirror) {
        return pState.rotate(pMirror.getRotation(pState.getValue(FACING)));
    }

    @Override
    public BlockState rotate(BlockState state, LevelAccessor level, BlockPos pos, Rotation direction) {
        return state.setValue(FACING,direction.rotate(state.getValue(FACING)));
    }
}
