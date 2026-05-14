package com.renyigesai.bakeries.items;

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
        this.craftingCount = Math.max(1, craftingCount);
    }

    public DoughItem() {
        this(1);
    }

    public int getCraftingCount() {
        return craftingCount;
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        tooltipComponents.add(Component.translatable("tooltips.bakeries.perfect_fermentation").withStyle(ChatFormatting.GOLD));
    }
}
