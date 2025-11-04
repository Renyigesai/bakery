package com.renyigesai.bakeries.common.utils;

import net.neoforged.fml.ModList;

public class ModIsLoaded {
    private static Boolean isFarmerSDelight;
    public static boolean isFarmerSDelight() {
        if (isFarmerSDelight == null) {
            isFarmerSDelight = ModList.get().isLoaded("farmersdelight");
        }
        return isFarmerSDelight;
    }
}
