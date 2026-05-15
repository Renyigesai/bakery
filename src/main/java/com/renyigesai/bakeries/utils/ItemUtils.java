package com.renyigesai.bakeries.utils;

import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

@SuppressWarnings("unused")
public final class ItemUtils {
    private ItemUtils() {
    }

    public static boolean isEmpty(ItemStack stack) {
        return stack == null || stack.isEmpty();
    }

    public static void shrink(ItemStack stack, int amount, Player player) {
        if (stack == null || amount <= 0) {
            return;
        }
        if (!player.getAbilities().instabuild) {
            stack.shrink(amount);
        }
    }

    public static void givePlayerItem(Player player, ItemStack stack) {
        if (isEmpty(stack)) {
            return;
        }
        if (!player.getInventory().add(stack.copy())) {
            player.drop(stack.copy(), false);
        }
    }

    public static void spawnItemEntity(Level level, ItemStack stack, double x, double y, double z, Vec3 motion) {
        if (isEmpty(stack)) {
            return;
        }
        ItemEntity entity = new ItemEntity(level, x, y, z, stack.copy());
        entity.setDeltaMovement(motion);
        level.addFreshEntity(entity);
    }
}
