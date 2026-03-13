package com.renyigesai.bakeries.api.conditions;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraftforge.common.crafting.conditions.IConditionSerializer;

/**
 * 抽象配置条件序列化器类，实现了条件序列化器接口，用于处理抽象配置条件的序列化和反序列化操作
 */
public abstract class AbstractConfigConditionSerializer implements IConditionSerializer<AbstractConfigCondition> {

    @Override
    public void write(JsonObject json, AbstractConfigCondition value) {
        json.addProperty("config_key", value.configKey);
        json.addProperty("value_type", value.valueType);
        if ("boolean".equals(value.valueType)){
            json.addProperty("expected", (boolean)value.expected);
        }else if ("string".equals(value.valueType)){
            json.addProperty("expected", (String) value.expected);
        }else if ("double".equals(value.valueType)){
            json.addProperty("expected", (double)value.expected);
        }
    }

    @Override
    public AbstractConfigCondition read(JsonObject json) {
        String key = json.get("config_key").getAsString();
        String type = json.get("value_type").getAsString();
        JsonElement expectedElem = json.get("expected");
        Object expected;
        switch (type) {
            case "boolean":
                expected = expectedElem.getAsBoolean();
                break;
            case "string":
                expected = expectedElem.getAsString();
                break;
            case "double":
                expected = expectedElem.getAsDouble();
                break;
            default:
                throw new IllegalArgumentException("Bakeries ConfigCondition Unknown value type: " + type);
        }
        return create(key,type,expected);
    }

    /**
     * 创建一个抽象配置条件对象的工厂方法
     * @param key 配置项的键名
     * @param type 条件类型，用于指定判断条件的类型
     * @param expected 期望的值，用于与实际配置值进行比较
     * @return 返回一个AbstractConfigCondition类型的实例，该实例可以根据指定的键、类型和期望值进行配置条件的判断
     */
    public abstract AbstractConfigCondition create(String key,String type,Object expected);
}
