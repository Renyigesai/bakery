package com.renyigesai.bakeries.item;

import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class HoneyFoodItem extends Item {
    public HoneyFoodItem(Properties pProperties) {
        super(pProperties);
    }
    /*只是像蜂蜜瓶一去除中毒效果*/
    public ItemStack finishUsingItem(ItemStack pStack, Level pLevel, LivingEntity pEntityLiving) {
        if (!pLevel.isClientSide) {
            pEntityLiving.removeEffect(MobEffects.POISON);
        }
        return super.finishUsingItem(pStack, pLevel, pEntityLiving);
    }
}
