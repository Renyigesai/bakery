package com.renyigesai.bakeries.api.block;

import com.renyigesai.bakeries.api.Shortcuts;
import lombok.Getter;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.chat.Component;
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
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

@Getter
public class PileBlock extends HorizontalDirectionalBlock {
    public static final IntegerProperty PILE = IntegerProperty.create("pile", 1, 4);
    protected static final VoxelShape SHAPE = Block.box(1.0D, 0.0D, 1.0D, 15.0D, 4.0D, 15.0D);
    public PileBlock() {
        super(BlockBehaviour.Properties.of().strength(0.5F,0.5F).sound(SoundType.WOOL).noOcclusion().isRedstoneConductor((bs, br, bp) -> false));
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(PILE, 1));
    }
    @Override
    public boolean skipRendering(BlockState state, BlockState adjacentBlockState, Direction side) {
        return adjacentBlockState.getBlock() == this ? true : super.skipRendering(state, adjacentBlockState, side);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    public int getMaxPile(){
        return 4;
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        ItemStack handItem = pPlayer.getItemInHand(pHand);
        Block block = pState.getBlock();
        if (pLevel.isClientSide) {
            if (!Screen.hasShiftDown()) {
                if (handItem.getItem() == block.asItem()) {return pileUp(pLevel, pPos, pState, pPlayer, pHand);}
            }else {return take(pLevel, pPos, pState, pPlayer);}
        }

        if (!Screen.hasShiftDown()) {
            if (handItem.getItem() == block.asItem()) {return pileUp(pLevel, pPos, pState, pPlayer, pHand);}
        }else {return take(pLevel, pPos, pState, pPlayer);}

        return super.use(pState, pLevel, pPos, pPlayer, pHand, pHit);
    }

    public InteractionResult pileUp(Level level, BlockPos pos, BlockState state,Player player,InteractionHand hand){
        int pile = state.getValue(PileBlock.PILE);
        ItemStack handItem = player.getItemInHand(hand);
        if (pile < getMaxPile()) {
            Shortcuts.setBlock(level,pos,state,PILE,pile,1);
            handItem.shrink(1);
            level.playSound(null, pos, SoundEvents.WOOL_STEP, SoundSource.PLAYERS, 0.8F, 0.8F);
        }else {
            return InteractionResult.FAIL;
        }
        return InteractionResult.SUCCESS;
    }

    public InteractionResult take(Level level, BlockPos pos, BlockState state,Player player){
        int pile = state.getValue(PileBlock.PILE);
        Block block = state.getBlock();
        player.getInventory().placeItemBackInInventory(new ItemStack(block.asItem()));
        if (pile > 1) {
            Shortcuts.setBlock(level,pos,state,PILE,pile,1,true);
        }else {
            level.removeBlock(pos, false);
        }
        level.playSound(null, pos, SoundEvents.WOOL_STEP, SoundSource.PLAYERS, 0.8F, 0.8F);
        return InteractionResult.SUCCESS;
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        return this.defaultBlockState().setValue(FACING, pContext.getHorizontalDirection());
    }

    @Override
    public BlockState updateShape(BlockState stateIn, Direction facing, BlockState facingState, LevelAccessor level, BlockPos currentPos, BlockPos facingPos) {
        return facing == Direction.DOWN && !stateIn.canSurvive(level, currentPos) ? Blocks.AIR.defaultBlockState() : super.updateShape(stateIn, facing, facingState, level, currentPos, facingPos);
    }
    @Override
    public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        return level.getBlockState(pos.below()).isSolid();
    }
    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, PILE);
    }
    @Override
    public boolean hasAnalogOutputSignal(BlockState state) {
        return true;
    }
    @Override
    public boolean isPathfindable(BlockState state, BlockGetter level, BlockPos pos, PathComputationType type) {
        return false;
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
    public void appendHoverText(ItemStack pStack, @Nullable BlockGetter pLevel, List<Component> pTooltip, TooltipFlag pFlag) {
        super.appendHoverText(pStack, pLevel, pTooltip, pFlag);
        List<MobEffectInstance> list = new ArrayList<>();
        listPotionEffects(pStack, list::add);
        PotionUtils.addPotionTooltip(list, pTooltip, 1.0F);
    }
}