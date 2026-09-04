package com.renyigesai.bakeries.common.utils;

import net.neoforged.fml.ModList;

public class ModIsLoaded {
    private static Boolean isFarmerSDelight;
    public static Boolean isKaleidoscopeCookery = ModList.get().isLoaded("kaleidoscope_cookery");
    public static boolean isFarmerSDelight() {
        if (isFarmerSDelight == null) {
            isFarmerSDelight = ModList.get().isLoaded("farmersdelight");
        }
        return isFarmerSDelight;
    }

    public static boolean isKaleidoscopeCookery() {
        if (isKaleidoscopeCookery == null) {
            isKaleidoscopeCookery = ModList.get().isLoaded("kaleidoscope_cookery");
        }
        return isKaleidoscopeCookery;
    }
}
