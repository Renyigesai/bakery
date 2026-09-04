package com.renyigesai.bakeries.common.blocks;

import com.renyigesai.bakeries.api.block.AbstractPileBlock;
import com.renyigesai.bakeries.common.init.BakeriesSoundType;
import com.renyigesai.bakeries.common.init.BakeriesSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class BreadBlock extends AbstractPileBlock {
    public static final IntegerProperty PILE = IntegerProperty.create("pile",1,4);
    public static final VoxelShape BOX = Block.box(1.0D, 0.0D, 1.0D, 15.0D, 4.0D, 15.0D);
    public BreadBlock() {
        super(Properties.of().strength(0.5F, 0.5F).sound(BakeriesSoundType.PASTRY).noOcclusion().isRedstoneConductor((bs, br, bp) -> false));
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING,Direction.NORTH).setValue(PILE,1));
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return BOX;
    }

    @Override
    public Property<Integer> getPileProperty() {
        return PILE;
    }

    @Override
    public int getMaxPile() {
        return 4;
    }

    @Override
    public SoundEvent getPlaceSound() {
        return BakeriesSounds.PASTRY_PLACE.get();
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING,PILE);
    }
}
