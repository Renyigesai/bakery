package com.renyigesai.bakeries.block.custom_cake;

import com.google.gson.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Ingredient;

import java.lang.reflect.Type;

public class CakePartData {
    private String id;
    private final String type;
    private final Ingredient ingredient;
    private final ResourceLocation potionEffect; // 可为空
    private final int hunger;
    private final float saturation;
    private final float modelY;
    private final String loadId;

    public CakePartData(String type, Ingredient ingredient,
                        ResourceLocation potionEffect, int hunger, float saturation, float modelY, String loadId) {
        this.type = type;
        this.ingredient = ingredient;
        this.potionEffect = potionEffect;
        this.hunger = hunger;
        this.saturation = saturation;
        this.modelY = modelY;
        this.loadId = loadId;
    }

    public String getType() { return type; }
    public Ingredient getIngredient() { return ingredient; }
    public ResourceLocation getPotionEffect() { return potionEffect; }
    public int getHunger() { return hunger; }
    public float getSaturation() { return saturation; }

    public float getModelY() {
        return modelY;
    }

    public String getLoadId() {
        return loadId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }



    public static class Deserializer implements JsonDeserializer<CakePartData> {
        @Override
        public CakePartData deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext ctx) throws JsonParseException {
            JsonObject obj = json.getAsJsonObject();
            String type = obj.get("type").getAsString();

            JsonElement ingElem = obj.get("ingredient");
            if (ingElem == null || !ingElem.isJsonArray()) {
                throw new JsonParseException("Ingredient must be a JSON array");
            }
            JsonArray ingArray = ingElem.getAsJsonArray();
            if (ingArray.size() != 1) {
                throw new JsonParseException("Ingredient must contain exactly one element, but found " + ingArray.size());
            }
            Ingredient ingredient = Ingredient.fromJson(ingArray);

            ResourceLocation potionEffect = null;
            if (obj.has("potion_effect") && !obj.get("potion_effect").isJsonNull()) {
                String effectStr = obj.get("potion_effect").getAsString();
                if (!effectStr.isEmpty()) {
                    potionEffect = new ResourceLocation(effectStr);
                }
            }

            int hunger = obj.get("hunger").getAsInt();
            float saturation = obj.get("saturation").getAsFloat();

            float modelY = 0.0f;
            if (obj.has("model_y") && !obj.get("model_y").isJsonNull()) {
                modelY = obj.get("model_y").getAsFloat();
            }

            String loadId = "";
            if (obj.has("load_id") && !obj.get("load_id").isJsonNull()) {
                loadId = obj.get("load_id").getAsString();
            }

            return new CakePartData(type, ingredient, potionEffect, hunger, saturation,modelY,loadId);
        }
    }
}