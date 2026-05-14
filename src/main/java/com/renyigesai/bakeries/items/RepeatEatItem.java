package com.renyigesai.bakeries.items;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;

public class RepeatEatItem extends Item {
    private final boolean canDrink;

    public RepeatEatItem(Properties properties, boolean canDrink) {
        super(properties);
        this.canDrink = canDrink;
    }

    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return canDrink ? UseAnim.DRINK : UseAnim.EAT;
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity livingEntity) {
        return super.finishUsingItem(stack, level, livingEntity);
    }
}
