package com.renyigesai.bakeries.items;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public class BaguetteItem extends RepeatEatItem {
    public BaguetteItem(Properties properties, boolean canDrink) {
        super(properties, canDrink);
    }

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (stack.getMaxDamage() > 0) {
            stack.hurtAndBreak(1, attacker, e -> {
            });
        }
        return true;
    }
}
