package com.renyigesai.bakeries.block.bread_basket;

import net.minecraft.util.StringRepresentable;

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

    public String getSerializedName() {
        return this.name;
    }
}
