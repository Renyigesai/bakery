package com.renyigesai.bakeries.api.conditions;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.renyigesai.bakeries.BakeriesConfig;
import net.neoforged.neoforge.common.conditions.ICondition;

import java.util.Map;
import java.util.function.Supplier;

public record ConfigCondition(String configKey,String valueType,String expected) implements ICondition {

    public static final MapCodec<ConfigCondition> CODEC = RecordCodecBuilder.mapCodec(
            instance -> instance.group(
                    Codec.STRING.fieldOf("config_key").forGetter(ConfigCondition::configKey),
                    Codec.STRING.fieldOf("value_type").forGetter(ConfigCondition::valueType),
                    Codec.STRING.fieldOf("expected").forGetter(ConfigCondition::expected)

            ).apply(instance, ConfigCondition::new)
    );

    @Override
    public boolean test(IContext context) {
        Map<String, Supplier<?>> map = BakeriesConfig.ConfigMapping.map;
        Supplier<?> supplier = map.get(configKey);
        if (supplier == null){
            return false;
        }
        Object returnValue = supplier.get();
        switch (valueType) {
            case "boolean" -> {
                boolean aBoolean = expected.equals("true");
                return ((Boolean) returnValue) == aBoolean;
            }
            case "double" -> {
                Double aDouble = Double.valueOf(expected);
                return Math.abs((Double) returnValue - aDouble) < 1e-6;
            }
            case "string" -> {
                return ((String) returnValue).equals(expected);
            }
        }
        return false;
    }


    @Override
    public MapCodec<? extends ICondition> codec() {
        return CODEC;
    }
}
