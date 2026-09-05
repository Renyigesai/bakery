package com.renyigesai.bakeries.block.custom_cake;

import com.google.common.collect.ImmutableList;
import com.renyigesai.bakeries.init.BakeriesItems;
import com.renyigesai.bakeries.item.CustomCakeItem;
import com.renyigesai.bakeries.util.ItemUtils;
import com.renyigesai.bakeries.util.measurer.CakePartMeasurer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.FlintAndSteelItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.NameTagItem;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class CustomCakeBlock extends HorizontalDirectionalBlock implements EntityBlock {
    public static final BooleanProperty CANDLE_LIT;
    private static final Iterable<Vec3> PARTICLE_OFFSETS;
    public CustomCakeBlock(Properties pProperties) {
        super(pProperties);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(CANDLE_LIT,false));
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return box(3.0, 0.0, 3.0, 13.0, 6.0, 13.0);
    }

    @Override
    public RenderShape getRenderShape(BlockState pState) {
        return RenderShape.MODEL;
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        if (pLevel.isClientSide){
            return InteractionResult.SUCCESS;
        }
        BlockEntity blockEntity = pLevel.getBlockEntity(pPos);
        if (!(blockEntity instanceof CustomCakeBlockEntity cc)){
            return super.use(pState, pLevel, pPos, pPlayer, pHand, pHit);
        }
        ItemStack itemInHand = pPlayer.getItemInHand(pHand);

        if (itemInHand.getItem() instanceof NameTagItem){
            cc.setName(itemInHand.getHoverName().getString());
            return InteractionResult.SUCCESS;
        }

        /**1.3.1新增，可以添加蜡烛和点燃熄灭蜡烛*/
        if (itemInHand.getItem() instanceof BlockItem blockItem){
            Block block = blockItem.getBlock();
            if (block instanceof AbstractCandleBlock){
                cc.setCandleId(BuiltInRegistries.BLOCK.getKey(block).toString());
                ItemUtils.shrink(itemInHand,1,pPlayer);
                cc.update();
                return InteractionResult.SUCCESS;
            }
        }

        if (itemInHand.getItem() instanceof FlintAndSteelItem){
            if (cc.hasCandle()){
                pLevel.setBlock(pPos,pState.setValue(CANDLE_LIT,true),3);
                return InteractionResult.SUCCESS;
            }
        }

        if (itemInHand.isEmpty()){
            if (cc.hasCandle() && pState.getValue(CANDLE_LIT)){
                extinguish(pPlayer,pState,pLevel,pPos);
                return InteractionResult.SUCCESS;
            }
        }

        Optional<CakePartData> optional = CakePartMeasurer.isIngredient(itemInHand);
        if (optional.isPresent()){
            CakePartData cakePartData = optional.get();
            if (!cc.test(cakePartData)){
                return super.use(pState, pLevel, pPos, pPlayer, pHand, pHit);
            }
            cc.addCakePart(cakePartData);
            ItemUtils.shrinkAndReturn(itemInHand,1,pPlayer);
            return InteractionResult.SUCCESS;
        }
        return super.use(pState, pLevel, pPos, pPlayer, pHand, pHit);
    }

    @Override
    public void onRemove(BlockState state, Level world, BlockPos pos, BlockState newState, boolean isMoving) {
        if (state.getBlock() != newState.getBlock()) {
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if (blockEntity instanceof CustomCakeBlockEntity cc) {
                ItemStack itemStack = new ItemStack(BakeriesItems.CUSTOM_CAKE.get());
                CustomCakeItem.setCustomCakeNBT(cc,itemStack);
                Containers.dropItemStack(world,pos.getX(),pos.getY(),pos.getZ(),itemStack);
                if (cc.hasCandle()){
                    ItemStack candle = new ItemStack(BuiltInRegistries.BLOCK.get(ResourceLocation.tryParse(cc.getCandleId())).asItem());
                    Containers.dropItemStack(world,pos.getX(),pos.getY(),pos.getZ(),candle);
                }
                world.updateNeighbourForOutputSignal(pos, this);
            }
            super.onRemove(state, world, pos, newState, isMoving);
        }
    }

    public void animateTick(BlockState pState, Level pLevel, BlockPos pPos, RandomSource pRandom) {
        if (pState.getValue(CANDLE_LIT)) {
            this.getParticleOffsets(pState).forEach((p_220695_) -> addParticlesAndSound(pLevel, p_220695_.add(pPos.getX(), pPos.getY(), pPos.getZ()), pRandom));
        }
    }

    private void addParticlesAndSound(Level pLevel, Vec3 pOffset, RandomSource pRandom) {
        float $$3 = pRandom.nextFloat();
        if ($$3 < 0.3F) {
            pLevel.addParticle(ParticleTypes.SMOKE, pOffset.x, pOffset.y, pOffset.z, 0.0, 0.0, 0.0);
            if ($$3 < 0.17F) {
                pLevel.playLocalSound(pOffset.x + 0.5, pOffset.y + 0.5, pOffset.z + 0.5, SoundEvents.CANDLE_AMBIENT, SoundSource.BLOCKS, 1.0F + pRandom.nextFloat(), pRandom.nextFloat() * 0.7F + 0.3F, false);
            }
        }

        pLevel.addParticle(ParticleTypes.SMALL_FLAME, pOffset.x, pOffset.y, pOffset.z, 0.0, 0.0, 0.0);
    }

    private Iterable<Vec3> getParticleOffsets(BlockState pState) {
        return PARTICLE_OFFSETS;
    }

    private void extinguish(@javax.annotation.Nullable Player pPlayer, BlockState pState, LevelAccessor pLevel, BlockPos pPos) {
        pLevel.setBlock(pPos,pState.setValue(CANDLE_LIT,false),3);
            getParticleOffsets(pState).forEach((p_151926_) -> pLevel.addParticle(ParticleTypes.SMOKE, (double)pPos.getX() + p_151926_.x(), (double)pPos.getY() + p_151926_.y(), (double)pPos.getZ() + p_151926_.z(), 0.0, 0.10000000149011612, 0.0));
        pLevel.playSound(null, pPos, SoundEvents.CANDLE_EXTINGUISH, SoundSource.BLOCKS, 1.0F, 1.0F);
        pLevel.gameEvent(pPlayer, GameEvent.BLOCK_CHANGE, pPos);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(FACING,CANDLE_LIT);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        return this.defaultBlockState().setValue(FACING, pContext.getHorizontalDirection());
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new CustomCakeBlockEntity(blockPos,blockState);
    }

    static {
        CANDLE_LIT = BooleanProperty.create("candle_lit");
        PARTICLE_OFFSETS = ImmutableList.of(new Vec3(0.5, 0.8125, 0.5));
    }
}
