package com.renyigesai.bakeries.common.blocks;

import com.renyigesai.bakeries.init.blocks.StateBlocks;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class ReapCropBlock extends StateBlocks.CropLikeBlock {
    public ReapCropBlock(BlockBehaviour.Properties properties) {
        super(properties, 7);
    }
}
