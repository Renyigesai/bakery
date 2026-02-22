package com.renyigesai.bakeries.item;

import com.renyigesai.bakeries.api.item.PileItem;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.IntegerProperty;

public class PaperCupItem extends PileItem {
    public PaperCupItem(Block block, IntegerProperty integerProperty, Properties pProperties, boolean effectTooltip, boolean customField) {
        super(block, integerProperty, pProperties, effectTooltip, customField);
    }
}
