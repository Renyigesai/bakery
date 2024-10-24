package com.renyigesai.bakery.item;

import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class RawItem extends Item {

    public static String TIPS_1;
    public static String TIPS_2;

    public RawItem(Properties pProperties,String tips_1,String tips_2) {
        super(pProperties);
        this.TIPS_1 = tips_1;
        this.TIPS_2 = tips_2;
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        if (Screen.hasShiftDown()){
            pTooltipComponents.add(Component.nullToEmpty(Component.translatable(TIPS_1).getString()));
            pTooltipComponents.add(Component.nullToEmpty(Component.translatable(TIPS_2).getString()));
        }else {
            pTooltipComponents.add(Component.nullToEmpty(Component.translatable("raw_item.tips.bakery.shift").getString()));
        }
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }
}
