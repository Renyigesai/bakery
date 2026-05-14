package com.renyigesai.bakeries.capabilities;

public final class FluidCapability {
    private int amount;

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = Math.max(0, amount);
    }
}
