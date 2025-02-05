package com.renyigesai.bakeries.block.bread_basket;

import net.minecraft.util.StringRepresentable;
import org.jetbrains.annotations.NotNull;

public enum BreadBasketShape implements StringRepresentable {

    ONE_SHAPE("one_shape"),
    TWO_SHAPE("two_shape"),;

    private final String name;

    private BreadBasketShape(String pName) {
        this.name = pName;
    }

    public String toString() {
        return this.name;
    }

    public @NotNull String getSerializedName() {
        return this.name;
    }
}
