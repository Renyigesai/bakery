package com.renyigesai.bakeries.api.items;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import com.renyigesai.bakeries.BakeriesMod;
import net.weibai.rcglib.items.TooltipItem;
import net.weibai.rcglib.utils.UtilTranslatable;

public class RawItem extends TooltipItem {
    public RawItem(Properties properties, int temperature) {
        super(properties,
                Component.translatable(UtilTranslatable.setTooltips(BakeriesMod.MODID, "row_item_temperature"), temperature)
                        .withStyle(ChatFormatting.BLUE)
        );
    }
}
