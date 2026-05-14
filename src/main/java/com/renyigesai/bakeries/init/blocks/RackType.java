package com.renyigesai.bakeries.init.blocks;

import net.minecraft.util.StringRepresentable;

public enum RackType implements StringRepresentable {
    SINGLE("single"),
    LEFT("left"),
    RIGHT("right"),
    ALL("all");

    private final String name;

    RackType(String name) {
        this.name = name;
    }

    @Override
    public String getSerializedName() {
        return name;
    }
}
