package com.renyigesai.bakeries.api.items;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import com.renyigesai.bakeries.BakeriesMod;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

import java.util.List;

public class RawItem extends Item {

    private final int temperature;

    public RawItem(Properties properties, int temperature) {
        super(properties);
        this.temperature = temperature;
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        tooltipComponents.add(Component.translatable("tooltips.bakeries.row_item_temperature",this.temperature).withStyle(ChatFormatting.BLUE));
    }
}
