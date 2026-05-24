package com.renyigesai.bakeries.items;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class FoodEffectItem extends Item implements FoodEffectTooltip.Appender {
    private final FoodEffectTooltip.Entry[] effects;

    public FoodEffectItem(Properties properties, FoodEffectTooltip.Entry... effects) {
        super(properties);
        this.effects = effects;
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        appendFoodEffectTooltip(stack, level, tooltipComponents, tooltipFlag, effects);
        super.appendHoverText(stack, level, tooltipComponents, tooltipFlag);
    }
}
