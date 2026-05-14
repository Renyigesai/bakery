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

    /**
     * 获取配置映射的方法
     * 该方法重写了父类的 getConfigMapping 方法，返回一个包含所有配置项的映射表
     *
     * @return 返回一个Map集合，键为配置项的名称(String类型)，值为对应配置项的提供者(Supplier<?>类型)
     */


    @Override
    public MapCodec<? extends ICondition> codec() {
        return CODEC;
    }
}
