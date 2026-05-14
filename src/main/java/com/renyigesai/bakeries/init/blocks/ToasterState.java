package com.renyigesai.bakeries.init.blocks;

import net.minecraft.util.StringRepresentable;

public enum ToasterState implements StringRepresentable {
    IDLE("idle"),
    LIT("lit"),
    FINISH("finish");

    private final String name;

    ToasterState(String name) {
        this.name = name;
    }

    @Override
    public String getSerializedName() {
        return name;
    }
}
