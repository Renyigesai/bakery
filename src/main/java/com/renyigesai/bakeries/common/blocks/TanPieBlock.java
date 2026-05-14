package com.renyigesai.bakeries.common.blocks;

import com.renyigesai.bakeries.api.block.AbstractPileBlock;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.Property;

public class TanPieBlock extends AbstractPileBlock {
    public static final IntegerProperty PILE = IntegerProperty.create("pile",1,4);
    public TanPieBlock() {
        super(BlockBehaviour.Properties.ofFullCopy(Blocks.GLASS).strength(0.1F,0.1F));
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(PILE,1));
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
        return SoundEvents.GLASS_PLACE;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING,PILE);
    }
}
