package com.renyigesai.bakeries.recipe;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;

import net.minecraft.core.registries.BuiltInRegistries;
import com.google.gson.JsonObject;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

@SuppressWarnings("unused")
public class SimpleMachineRecipe implements Recipe<Container> {
    private final ResourceLocation id;
    private final Ingredient ingredient;
    private final ItemStack result;
    private final int count;
    private int minTemperature;
    private int maxTemperature;
    private int perfectTemperature = -1;
    private int craftTime = -1;
    private final ResourceLocation typeId;
    private final ResourceLocation serializerId;
    private boolean valid = true;

    public SimpleMachineRecipe(ResourceLocation id, Ingredient ingredient, ItemStack result, int count, ResourceLocation typeId, ResourceLocation serializerId) {
        this(id, ingredient, result, count, -1, -1, typeId, serializerId);
    }

    public SimpleMachineRecipe(ResourceLocation id, Ingredient ingredient, ItemStack result, int count, int minTemperature, int maxTemperature, ResourceLocation typeId, ResourceLocation serializerId) {
        this.id = id;
        this.ingredient = ingredient;
        this.result = result;
        this.count = count;
        this.minTemperature = minTemperature;
        this.maxTemperature = maxTemperature;
        this.typeId = typeId;
        this.serializerId = serializerId;
    }

    @Override
    public boolean matches(Container container, Level level) {
        if (!isValid()) {
            return false;
        }
        return ingredient.test(container.getItem(0));
    }

    @Override
    public @NotNull ItemStack assemble(Container container, RegistryAccess registryAccess) {
        ItemStack out = result.copy();
        out.setCount(count);
        return out;
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return true;
    }

    @Override
    public @NotNull ItemStack getResultItem(RegistryAccess registryAccess) {
        ItemStack out = result.copy();
        out.setCount(count);
        return out;
    }

    @Override
    public @NotNull ResourceLocation getId() {
        return id;
    }

    @Override
    public @NotNull RecipeSerializer<?> getSerializer() {
        return Objects.requireNonNull(BuiltInRegistries.RECIPE_SERIALIZER.get(serializerId));
    }

    @Override
    public @NotNull RecipeType<?> getType() {
        return Objects.requireNonNull(BuiltInRegistries.RECIPE_TYPE.get(typeId));
    }

    @Override
    public boolean isSpecial() {
        return false;
    }

    public Ingredient getIngredient() {
        return ingredient;
    }

    public boolean isValid() {
        return valid && !result.isEmpty();
    }

    public SimpleMachineRecipe markInvalid() {
        valid = false;
        return this;
    }

    public SimpleMachineRecipe setRecipeData(int minTemperature, int maxTemperature, int perfectTemperature, int craftTime) {
        this.minTemperature = minTemperature;
        this.maxTemperature = maxTemperature;
        this.perfectTemperature = perfectTemperature;
        this.craftTime = craftTime;
        return this;
    }

    public int getMinTemperature() {
        return minTemperature;
    }

    public int getMaxTemperature() {
        return maxTemperature;
    }

    public int getPerfectTemperature() {
        return perfectTemperature;
    }

    public int getCraftTime() {
        return craftTime;
    }

    public static class Serializer implements RecipeSerializer<SimpleMachineRecipe> {
        public interface Factory {
            SimpleMachineRecipe create(ResourceLocation id, Ingredient ingredient, ItemStack result, int count);

            default SimpleMachineRecipe create(ResourceLocation id, Ingredient ingredient, ItemStack result, int count, int minTemperature, int maxTemperature) {
                return create(id, ingredient, result, count);
            }
        }

        private final Factory factory;

        public Serializer(Factory factory) {
            this.factory = factory;
        }

        @Override
        public @NotNull SimpleMachineRecipe fromJson(ResourceLocation id, JsonObject json) {
            JsonObject ingredientJson = GsonHelper.getAsJsonObject(json, "ingredient");
            JsonObject resultJson = GsonHelper.getAsJsonObject(json, "result");
            if (!isValidIngredient(ingredientJson) || !isValidItemStack(resultJson)) {
                return factory.create(id, Ingredient.of(Items.BARRIER), new ItemStack(Items.BARRIER), 1).markInvalid();
            }
            Ingredient ingredient = Ingredient.fromJson(ingredientJson);
            ItemStack result = net.minecraft.world.item.crafting.ShapedRecipe.itemStackFromJson(resultJson);
            int count = GsonHelper.getAsInt(resultJson, "count", Math.max(1, result.getCount()));
            int min = GsonHelper.getAsInt(json, "min", -1);
            int max = GsonHelper.getAsInt(json, "max", -1);
            int perfect = GsonHelper.getAsInt(json, "perfect", -1);
            int time = GsonHelper.getAsInt(json, "time", -1);
            return factory.create(id, ingredient, result, count, min, max).setRecipeData(min, max, perfect, time);
        }

        @Override
        public @NotNull SimpleMachineRecipe fromNetwork(ResourceLocation id, FriendlyByteBuf buf) {
            Ingredient ingredient = Ingredient.fromNetwork(buf);
            ItemStack result = buf.readItem();
            int count = buf.readVarInt();
            int min = buf.readVarInt();
            int max = buf.readVarInt();
            int perfect = buf.readVarInt();
            int time = buf.readVarInt();
            boolean valid = buf.readBoolean();
            SimpleMachineRecipe recipe = factory.create(id, ingredient, result, count, min, max).setRecipeData(min, max, perfect, time);
            return valid ? recipe : recipe.markInvalid();
        }

        @Override
        public void toNetwork(FriendlyByteBuf buf, SimpleMachineRecipe recipe) {
            recipe.ingredient.toNetwork(buf);
            buf.writeItem(recipe.result);
            buf.writeVarInt(recipe.count);
            buf.writeVarInt(recipe.minTemperature);
            buf.writeVarInt(recipe.maxTemperature);
            buf.writeVarInt(recipe.perfectTemperature);
            buf.writeVarInt(recipe.craftTime);
            buf.writeBoolean(recipe.valid);
        }

        private static boolean isValidIngredient(JsonElement json) {
            if (json == null || json.isJsonNull()) {
                return false;
            }
            if (json.isJsonArray()) {
                JsonArray array = json.getAsJsonArray();
                if (array.isEmpty()) {
                    return false;
                }
                for (JsonElement element : array) {
                    if (!isValidIngredient(element)) {
                        return false;
                    }
                }
                return true;
            }
            if (!json.isJsonObject()) {
                return false;
            }
            JsonObject object = json.getAsJsonObject();
            if (object.has("item")) {
                return BuiltInRegistries.ITEM.containsKey(new ResourceLocation(GsonHelper.getAsString(object, "item")));
            }
            if (object.has("tag")) {
                return ResourceLocation.isValidResourceLocation(GsonHelper.getAsString(object, "tag"));
            }
            return false;
        }

        private static boolean isValidItemStack(JsonObject json) {
            return json.has("item")
                    && BuiltInRegistries.ITEM.containsKey(new ResourceLocation(GsonHelper.getAsString(json, "item")));
        }
    }
}
