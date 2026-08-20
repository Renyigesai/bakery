package com.renyigesai.bakeries.common.items;

import com.renyigesai.bakeries.api.items.RawItem;
import net.minecraft.world.item.Item;

public class MouldPasteItem extends RawItem {

    public final int color;

    public MouldPasteItem(int temperature, int color) {
        super(new Item.Properties(), temperature);
        this.color = color;
    }

    public int getColor() {
        return color;
    }

}
