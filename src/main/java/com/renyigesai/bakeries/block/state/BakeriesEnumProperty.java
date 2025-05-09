package com.renyigesai.bakeries.block.state;

import net.minecraft.util.StringRepresentable;

public enum BakeriesEnumProperty implements StringRepresentable {
    NONE("none"),
    BRASS("brass"),
    ONE_SHAPE("one_shape"),
    TWO_SHAPE("two_shape");

    private final String name;

    private BakeriesEnumProperty(String pName) {
        this.name = pName;
    }

    @Override
    public String getSerializedName() {return this.name;}

    public String toString() {
        return this.name;
    }
}
