package com.renyigesai.bakeries.items;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class FoodEffectBlockItem extends BlockItem implements FoodEffectTooltip.Appender {
    private final FoodEffectTooltip.Entry[] effects;

    public FoodEffectBlockItem(Block block, Properties properties, FoodEffectTooltip.Entry... effects) {
        super(block, properties);
        this.effects = effects;
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        appendFoodEffectTooltip(stack, level, tooltipComponents, tooltipFlag, effects);
        super.appendHoverText(stack, level, tooltipComponents, tooltipFlag);
    }
}
