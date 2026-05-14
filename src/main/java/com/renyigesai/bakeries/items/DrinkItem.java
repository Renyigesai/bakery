package com.renyigesai.bakeries.items;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class DrinkItem extends RepeatEatItem {
    private final int upEffect;

    public DrinkItem(Properties properties, int upEffect) {
        super(properties, true);
        this.upEffect = upEffect;
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag tooltipFlag) {
        if (upEffect > 0) {
            tooltip.add(Component.translatable("tooltips.bakeries.drink", Component.translatable("potion.potency." + upEffect)).withStyle(ChatFormatting.DARK_GRAY));
        }
        super.appendHoverText(stack, level, tooltip, tooltipFlag);
    }
}
