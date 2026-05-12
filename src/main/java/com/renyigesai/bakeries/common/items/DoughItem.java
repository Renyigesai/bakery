package com.renyigesai.bakeries.common.items;

import com.renyigesai.bakeries.api.items.IFermentationItem;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class DoughItem extends Item implements IFermentationItem {
    private final int craftingCount;
    public DoughItem(int craftingCount) {
        super(new Item.Properties());
        if (craftingCount > 0){
            this.craftingCount = craftingCount;
        }else {
            this.craftingCount = 1;
        }

    }

    public DoughItem() {
        super(new Item.Properties());
        this.craftingCount = 1;
    }

    @Override
    public int getCraftingCount() {
        return craftingCount;
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        if (isPerfectFermentation(stack)){
//            pTooltipComponents.add(Component.translatable("item.bakeries.tips.perfect_fermentation").withStyle(ChatFormatting.GOLD));
        }
    }
}
