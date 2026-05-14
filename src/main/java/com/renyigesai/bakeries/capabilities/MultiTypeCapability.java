package com.renyigesai.bakeries.capabilities;

public final class MultiTypeCapability implements IMultiTypeCapability {
    private int type;

    @Override
    public int getType() {
        return type;
    }

    @Override
    public void setType(int type) {
        this.type = type;
    }
}
