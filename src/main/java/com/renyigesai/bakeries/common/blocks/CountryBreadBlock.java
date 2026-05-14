package com.renyigesai.bakeries.common.blocks;

import com.renyigesai.bakeries.init.blocks.StateBlocks;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class CountryBreadBlock extends StateBlocks.FacingPileBlock {
    public CountryBreadBlock(BlockBehaviour.Properties properties) {
        super(properties, 2);
    }
}
