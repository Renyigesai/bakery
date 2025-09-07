package com.renyigesai.bakeries.item;

import com.renyigesai.bakeries.api.item.FoodBlockItem;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.IntegerProperty;

public class PaperCupItem extends FoodBlockItem {
    public PaperCupItem(Block block, IntegerProperty integerProperty, Properties pProperties, boolean effectTooltip, boolean customField) {
        super(block, integerProperty, pProperties, effectTooltip, customField);
    }
}
