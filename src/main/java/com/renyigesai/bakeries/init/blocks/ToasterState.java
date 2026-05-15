package com.renyigesai.bakeries.init.blocks;

import net.minecraft.util.StringRepresentable;
import org.jetbrains.annotations.NotNull;

public enum ToasterState implements StringRepresentable {
    IDLE("idle"),
    LIT("lit"),
    FINISH("finish");

    private final String name;

    ToasterState(String name) {
        this.name = name;
    }

    @Override
    public @NotNull String getSerializedName() {
        return name;
    }
}
