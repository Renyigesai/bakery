package com.renyigesai.bakery.item;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class RawItem extends Item {

    public final String TIPS;

    public RawItem(Properties pProperties,String tips) {
        super(pProperties);
        TIPS = tips;
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
            pTooltipComponents.add(Component.translatable("Min" + TIPS + "°C").withStyle(ChatFormatting.BLUE));
    }
}
