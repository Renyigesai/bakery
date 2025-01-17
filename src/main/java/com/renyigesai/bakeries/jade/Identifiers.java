package com.renyigesai.bakeries.jade;

import com.renyigesai.bakeries.BakeriesMod;
import net.minecraft.resources.ResourceLocation;

public class Identifiers {
    public static final ResourceLocation BAYSALT_FRAME = BAKERIES("baysalt_frame");
    public static final ResourceLocation OVEN = BAKERIES("oven");
    public static ResourceLocation BAKERIES(String path) {
        return BakeriesMod.prefix(path);
    }
}
