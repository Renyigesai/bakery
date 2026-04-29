package com.renyigesai.bakeries.item;

import com.renyigesai.bakeries.api.item.IFermentationItem;
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
    public DoughItem(Properties pProperties,int craftingCount) {
        super(pProperties);
        if (craftingCount > 0){
            this.craftingCount = craftingCount;
        }else {
            this.craftingCount = 1;
        }

    }

    public DoughItem(Properties pProperties) {
        super(pProperties);
        this.craftingCount = 1;
    }

    @Override
    public int getCraftingCount() {
        return craftingCount;
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        if (isPerfectFermentation(pStack)){
            pTooltipComponents.add(Component.translatable("item.bakeries.tips.perfect_fermentation").withStyle(ChatFormatting.GOLD));
        }
    }
}
