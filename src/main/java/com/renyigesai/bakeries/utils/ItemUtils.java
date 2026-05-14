package com.renyigesai.bakeries.utils;

import net.minecraft.world.item.ItemStack;

public final class ItemUtils {
    private ItemUtils() {
    }

    public static boolean isEmpty(ItemStack stack) {
        return stack == null || stack.isEmpty();
    }
}
