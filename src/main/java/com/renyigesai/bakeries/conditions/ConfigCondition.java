package com.renyigesai.bakeries.conditions;

import com.renyigesai.bakeries.BakeriesMod;
import com.renyigesai.bakeries.api.conditions.AbstractConfigCondition;
import com.renyigesai.bakeries.api.conditions.AbstractConfigConditionSerializer;
import com.renyigesai.bakeries.config.BakeriesConfig;
import net.minecraft.resources.ResourceLocation;

import java.util.Map;
import java.util.function.Supplier;
public class ConfigCondition extends AbstractConfigCondition {

    public static final ResourceLocation ID = new ResourceLocation(BakeriesMod.MODID,"config");

    public ConfigCondition(String configKey, String valueType, Object expectedValue) {
        super(configKey, valueType, expectedValue);
    }

    @Override
    public Map<String, Supplier<?>> getConfigMapping() {
        return BakeriesConfig.ConfigMapping.map;
    }

    @Override
    public ResourceLocation getID() {
        return ID;
    }

    public static class Serializer extends AbstractConfigConditionSerializer{

        public static final Serializer INSTANCE = new Serializer();

        @Override
        public AbstractConfigCondition create(String key, String type, Object expected) {
            return new ConfigCondition(key,type,expected);
        }

        @Override
        public ResourceLocation getID() {
            return ID;
        }
    }
}
