package com.renyigesai.bakeries.api.items;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.block.Block;
import net.weibai.rcglib.items.BreadItem;
import vectorwing.farmersdelight.common.utility.TextUtils;

import java.util.List;

public abstract class BBreadItem extends BreadItem {
    public final boolean effectTooltip;


    protected BBreadItem(Block block, Properties properties, boolean effectTooltip) {
        super(block, properties);
        this.effectTooltip = effectTooltip;
    }

    protected BBreadItem(Block block, Properties properties) {
        super(block, properties);
        this.effectTooltip = false;
    }

    /**暂时添加药水效果显示*/
    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
        if (effectTooltip) {
            TextUtils.addFoodEffectTooltip(stack, tooltipComponents::add, 1.0F,context.tickRate());
        }
    }
}
