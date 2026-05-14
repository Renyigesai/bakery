package com.renyigesai.bakeries.api.block;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;

public abstract class AbstractPileBlock extends Block {
    protected AbstractPileBlock(BlockBehaviour.Properties properties) {
        super(properties);
    }
}
