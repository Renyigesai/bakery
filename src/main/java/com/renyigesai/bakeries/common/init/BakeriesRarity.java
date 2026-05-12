package com.renyigesai.bakeries.common.init;

import net.minecraft.network.chat.Style;
import net.minecraft.world.item.Rarity;
import net.neoforged.fml.common.asm.enumextension.EnumProxy;

import java.util.function.UnaryOperator;

public class BakeriesRarity {
    public static final EnumProxy<Rarity> ADVANCED = new EnumProxy<>(
            Rarity.class, -1, "bakeries:advanced", (UnaryOperator<Style>) style -> style.withColor(15574564)
    );

    public static final EnumProxy<Rarity> TARO = new EnumProxy<>(
            Rarity.class, -2, "bakeries:taro", (UnaryOperator<Style>) style -> style.withColor(0XC889FF)
    );

    public static Rarity getAdvanced(){
        return ADVANCED.getValue();
    }
    public static Rarity getTaro(){
        return TARO.getValue();
    }
}
