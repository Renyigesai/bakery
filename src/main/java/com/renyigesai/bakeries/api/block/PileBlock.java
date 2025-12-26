package com.renyigesai.bakeries.api.block;

import com.renyigesai.bakeries.init.BakeriesSounds;
import com.renyigesai.bakeries.util.ItemUtil;
import lombok.Getter;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

@Getter
public class PileBlock extends HorizontalDirectionalBlock {
    public static final SoundType PASTRY = new SoundType(1.0F, 1.0F, SoundEvents.WOOL_BREAK, SoundEvents.WOOL_STEP, BakeriesSounds.PASTRY_PLACE.get(), SoundEvents.WOOL_HIT, SoundEvents.WOOL_FALL);
    public static final IntegerProperty integerProperty = IntegerProperty.create("pile", 1, 4);

    public PileBlock() {
        super(BlockBehaviour.Properties.of().strength(0.5F,0.5F).sound(PASTRY).noOcclusion().isRedstoneConductor((bs, br, bp) -> false));
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(integerProperty, 1));
    }

    public PileBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(integerProperty, 1));
    }

    @Override
    public boolean skipRendering(@NotNull BlockState state, BlockState adjacentBlockState, @NotNull Direction side) {
        return adjacentBlockState.getBlock() == this || super.skipRendering(state, adjacentBlockState, side);
    }

    @Override
    public @NotNull VoxelShape getShape(@NotNull BlockState state, @NotNull BlockGetter level, @NotNull BlockPos pos, @NotNull CollisionContext context) {
        return Block.box(1.0D, 0.0D, 1.0D, 15.0D, 4.0D, 15.0D);
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
        builder.add(FACING, integerProperty);
    }
    @Override
    public boolean hasAnalogOutputSignal(@NotNull BlockState state) {
        return true;
    }

    public int getMaxPile(){
        return 4;
    }

    public SoundEvent getTakeSound(){
        return SoundEvents.ITEM_FRAME_REMOVE_ITEM;
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        if (pLevel.isClientSide){
            return InteractionResult.SUCCESS;
        }
        if (!pPlayer.isShiftKeyDown()){
            return take(pState, pLevel, pPos, pPlayer);
        }
        return super.use(pState, pLevel, pPos, pPlayer, pHand, pHit);
    }

    public InteractionResult take(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer){
        int i =  pState.getValue(integerProperty);
        if (i == 1){
            pLevel.removeBlock(pPos,false);
        }else {
            pLevel.setBlock(pPos,pState.setValue(integerProperty,i-1),3);
        }
        ItemUtil.givePlayerItem(pPlayer,new ItemStack(this.asItem()));
        pLevel.playSound(null,pPos,getTakeSound(),SoundSource.BLOCKS);
        return InteractionResult.SUCCESS;
    }

    private static void listPotionEffects(ItemStack pStack, Consumer<MobEffectInstance> pOutput) {
        CompoundTag compoundtag = pStack.getTag();
        if (compoundtag != null && compoundtag.contains("Effects", 9)) {
            ListTag listtag = compoundtag.getList("Effects", 10);

            for(int i = 0; i < listtag.size(); ++i) {
                CompoundTag compoundtag1 = listtag.getCompound(i);
                int j;
                if (compoundtag1.contains("EffectDuration", 99)) {
                    j = compoundtag1.getInt("EffectDuration");
                } else {
                    j = 160;
                }

                MobEffect mobeffect = MobEffect.byId(compoundtag1.getInt("EffectId"));
                mobeffect = net.minecraftforge.common.ForgeHooks.loadMobEffect(compoundtag1, "forge:effect_id", mobeffect);
                if (mobeffect != null) {
                    pOutput.accept(new MobEffectInstance(mobeffect, j));
                }
            }
        }
    }


    @Override
    public void appendHoverText(@NotNull ItemStack pStack, @Nullable BlockGetter pLevel, @NotNull List<Component> pTooltip, @NotNull TooltipFlag pFlag) {
        super.appendHoverText(pStack, pLevel, pTooltip, pFlag);
        List<MobEffectInstance> list = new ArrayList<>();
        listPotionEffects(pStack, list::add);
        PotionUtils.addPotionTooltip(list, pTooltip, 1.0F);
    }
}