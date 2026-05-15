package com.renyigesai.bakeries.init.blocks;

import net.minecraft.util.StringRepresentable;
import org.jetbrains.annotations.NotNull;

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
    public @NotNull String getSerializedName() {
        return name;
    }
}
