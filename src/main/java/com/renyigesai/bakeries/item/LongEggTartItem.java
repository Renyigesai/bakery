package com.renyigesai.bakeries.item;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.IntegerProperty;

public class LongEggTartItem extends RepeatEatItem{
    public LongEggTartItem(Block block, IntegerProperty integerProperty, Properties pProperties, int eatCountMax, boolean effectTooltip, boolean customField) {
        super(block, integerProperty, pProperties, eatCountMax, effectTooltip, customField);
    }
}
