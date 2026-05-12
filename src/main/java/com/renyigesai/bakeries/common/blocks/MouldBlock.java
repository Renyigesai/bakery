package com.renyigesai.bakeries.common.blocks;

import com.mojang.serialization.MapCodec;
import com.renyigesai.bakeries.common.utils.ItemUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.function.Supplier;

public class MouldBlock extends HorizontalDirectionalBlock {
    public final Supplier<Item> mould;
    public MouldBlock(Supplier<Item> mould) {
        super(Properties.of().sound(SoundType.METAL).strength(0.5F,0.5F));
        this.mould = mould;
    }

    private MouldBlock(Properties properties) {
        super(properties);
        this.mould = ItemStack.EMPTY::getItem;
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return box(2.0D, 0.0D, 2.0D, 14.0D, 7.0D, 14.0D);
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        ItemUtils.givePlayerItem(player,new ItemStack(this.mould.get()));
        level.playSound(null, pos, SoundEvents.METAL_BREAK, SoundSource.PLAYERS, 0.8F, 0.8F);
        level.removeBlock(pos,false);
        return InteractionResult.SUCCESS;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(FACING);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        return this.defaultBlockState().setValue(FACING, pContext.getHorizontalDirection());
    }

    @Override
    protected MapCodec<MouldBlock> codec() {
        return simpleCodec(MouldBlock::new);
    }
}
