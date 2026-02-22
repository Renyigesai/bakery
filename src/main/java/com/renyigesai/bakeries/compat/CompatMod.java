package com.renyigesai.bakeries.compat;

import net.minecraftforge.fml.ModList;

public record CompatMod() {
    /*记录联动模组的ModId*/
    public static final boolean FARMER_S_DELIGHT = ModList.get().isLoaded("farmersdelight");//农夫乐事
    public static final boolean KALEIDOSCOPE_COOKERY = ModList.get().isLoaded("kaleidoscope_cookery");//森罗物语：厨房
}