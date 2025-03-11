package com.renyigesai.bakeries.block.inventory;

import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;

public class SideInputItemStackHandler extends ItemStackHandler {

    @Override
    public @NotNull ItemStack insertItem(int slot, @NotNull ItemStack stack, boolean simulate) {
        if (!Direction.UP.equals(Direction.UP)){
            return super.insertItem(slot, stack, simulate);
        }
        return stack;
    }
}
