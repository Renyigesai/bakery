package com.renyigesai.bakeries.common.blocks;

import com.renyigesai.bakeries.init.blocks.StateBlocks;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class CoffeePlantBlock extends StateBlocks.CropLikeBlock {
    public CoffeePlantBlock(BlockBehaviour.Properties properties) {
        super(properties, 2);
    }
}
