package com.renyigesai.bakeries.api.conditions;

import net.minecraftforge.common.crafting.conditions.ICondition;

import java.util.Map;
import java.util.function.Supplier;

public abstract class AbstractConfigCondition implements ICondition {
    public final String configKey;
    public final String valueType;
    public final Object expected;

    public AbstractConfigCondition(String configKey, String valueType, Object expectedValue) {
        this.configKey = configKey;
        this.valueType = valueType;
        this.expected = expectedValue;
    }

    /**
     * 获取配置映射的方法
     * 该方法重写了父类的 getConfigMapping 方法，返回一个包含所有配置项的映射表
     *
     * @return 返回一个Map集合，键为配置项的名称(String类型)，值为对应配置项的提供者(Supplier<?>类型)
     */
    public abstract Map<String,Supplier<?>> getConfigMapping();

    @Override
    public boolean test(IContext context) {
        Map<String, Supplier<?>> configMapping = getConfigMapping();
        if (configMapping == null){
            return false;
        }
        Supplier<?> supplier = configMapping.get(configKey);
        if (supplier == null){
            return false;
        }
        Object actual = supplier.get();
        if (actual == null) {
            return false;
        }
        switch (valueType) {
            case "boolean":
                if (expected instanceof Boolean && actual instanceof Boolean) {
                    return actual.equals(expected);
                }
                break;
            case "string":
                if (expected instanceof String && actual instanceof String) {
                    return actual.equals(expected);
                }
                break;
            case "double":
                if (expected instanceof Double && actual instanceof Number) {
                    double actualDouble = ((Number) actual).doubleValue();
                    double expectedDouble = (Double) expected;
                    return Math.abs(actualDouble - expectedDouble) < 1e-6;
                }
                break;
        }
        return false;
    }
}
