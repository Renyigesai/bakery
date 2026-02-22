package com.renyigesai.bakeries.api.block;

import net.minecraft.world.item.Item;

import java.util.function.Supplier;

public interface IMouldBlock {
    Supplier<Item> getMouldContentItem();

    Supplier<Item> getMouldItem();
}
