package com.renyigesai.bakeries.common.blocks.custom_cake;

import com.google.gson.*;
import com.llamalad7.mixinextras.lib.antlr.runtime.misc.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.Decoder;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Ingredient;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Optional;

public class CakePartData {
    private String id;
    private final String type;
    private final Ingredient ingredient;
    private final ResourceLocation potionEffect;
    private final int effectAmplifier;
    private final int effectDuration;
    private final int hunger;
    private final float saturation;
    private final float modelY;
    private final String loadId;

    public CakePartData(String type, Ingredient ingredient,
                        ResourceLocation potionEffect, int effectAmplifier, int effectDuration,
                        int hunger, float saturation, float modelY, String loadId) {
        this.type = type;
        this.ingredient = ingredient;
        this.potionEffect = potionEffect;
        this.effectAmplifier = effectAmplifier;
        this.effectDuration = effectDuration;
        this.hunger = hunger;
        this.saturation = saturation;
        this.modelY = modelY;
        this.loadId = loadId;
    }

    public String getType() { return type; }
    public Ingredient getIngredient() { return ingredient; }
    public ResourceLocation getPotionEffect() { return potionEffect; }
    public int getEffectAmplifier() { return effectAmplifier; }
    public int getEffectDuration() { return effectDuration; }
    public int getHunger() { return hunger; }
    public float getSaturation() { return saturation; }
    public float getModelY() { return modelY; }
    public String getLoadId() { return loadId; }
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    private static final Codec<Ingredient> SINGLE_INGREDIENT_CODEC =
            Ingredient.CODEC.listOf()                    // Codec<List<Ingredient>>
                    .flatXmap(
                            list -> {
                                if (list.size() != 1) {
                                    return DataResult.error(() -> "Ingredient must be a JSON array with exactly one element");
                                }
                                return DataResult.success(list.getFirst());
                            },
                            single -> DataResult.success(List.of(single))
                    );

    public static final Codec<CakePartData> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    Codec.STRING.fieldOf("type").forGetter(CakePartData::getType),
                    SINGLE_INGREDIENT_CODEC.fieldOf("ingredient").forGetter(CakePartData::getIngredient),
                    ResourceLocation.CODEC.optionalFieldOf("potion_effect").forGetter(data -> Optional.ofNullable(data.potionEffect)),
                    Codec.INT.optionalFieldOf("effect_amplifier", 0).forGetter(CakePartData::getEffectAmplifier),
                    Codec.INT.optionalFieldOf("effect_duration", 0).forGetter(CakePartData::getEffectDuration),
                    Codec.INT.fieldOf("hunger").forGetter(CakePartData::getHunger),
                    Codec.FLOAT.fieldOf("saturation").forGetter(CakePartData::getSaturation),
                    Codec.FLOAT.optionalFieldOf("model_y", 0.0f).forGetter(CakePartData::getModelY),
                    Codec.STRING.optionalFieldOf("load_id", "").forGetter(CakePartData::getLoadId)
            ).apply(instance, (type, ingredient, potionEffectOpt, effectAmplifier, effectDuration, hunger, saturation, modelY, loadId) -> {
                ResourceLocation potionEffect = potionEffectOpt.orElse(null);
                // 校验药水效果与时长
                if (potionEffect != null && effectDuration == 0) {
                    throw new IllegalStateException("When potion_effect is specified, effect_duration must also be provided");
                }
                return new CakePartData(type, ingredient, potionEffect, effectAmplifier, effectDuration, hunger, saturation, modelY, loadId);
            })
    );
}