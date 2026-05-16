package com.renyigesai.bakeries.recipe;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.renyigesai.bakeries.BakeriesMod;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("unused")
public class BlenderRecipe extends SimpleMachineRecipe {
    private final Ingredient containerIngredient;
    private final boolean hasContainer;

    public BlenderRecipe(ResourceLocation id, Ingredient ingredient, ItemStack result, int count) {
        this(id, ingredient, result, count, Ingredient.EMPTY, false);
    }

    public BlenderRecipe(ResourceLocation id, Ingredient ingredient, ItemStack result, int count, Ingredient containerIngredient, boolean hasContainer) {
        super(id, ingredient, result, count,
                new ResourceLocation(BakeriesMod.MODID, "blender"),
                new ResourceLocation(BakeriesMod.MODID, "blender"));
        this.containerIngredient = containerIngredient;
        this.hasContainer = hasContainer;
    }

    public Ingredient getContainerIngredient() {
        return containerIngredient;
    }

    public boolean hasContainer() {
        return hasContainer;
    }

    public static class Serializer implements net.minecraft.world.item.crafting.RecipeSerializer<SimpleMachineRecipe> {
        @Override
        public @NotNull SimpleMachineRecipe fromJson(ResourceLocation id, JsonObject json) {
            JsonElement ingredientJson = json.has("ingredient") ? json.get("ingredient") : json.get("ingredients");
            JsonObject resultJson = GsonHelper.getAsJsonObject(json, "result");
            JsonElement containerJson = json.get("container");
            boolean hasContainer = containerJson != null && !containerJson.isJsonNull();
            if (!isValidIngredient(ingredientJson)
                    || !isValidItemStack(resultJson)
                    || (hasContainer && !isValidIngredient(containerJson))) {
                return new BlenderRecipe(id, Ingredient.of(Items.BARRIER), new ItemStack(Items.BARRIER), 1).markInvalid();
            }
            Ingredient ingredient = Ingredient.fromJson(ingredientJson);
            ItemStack result = net.minecraft.world.item.crafting.ShapedRecipe.itemStackFromJson(resultJson);
            int count = GsonHelper.getAsInt(resultJson, "count", Math.max(1, result.getCount()));
            Ingredient container = hasContainer ? Ingredient.fromJson(containerJson) : Ingredient.EMPTY;
            int min = GsonHelper.getAsInt(json, "min", -1);
            int max = GsonHelper.getAsInt(json, "max", -1);
            int perfect = GsonHelper.getAsInt(json, "perfect", -1);
            int time = GsonHelper.getAsInt(json, "time", -1);
            return new BlenderRecipe(id, ingredient, result, count, container, hasContainer)
                    .setRecipeData(min, max, perfect, time);
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
            boolean hasContainer = buf.readBoolean();
            Ingredient container = hasContainer ? Ingredient.fromNetwork(buf) : Ingredient.EMPTY;
            BlenderRecipe recipe = (BlenderRecipe) new BlenderRecipe(id, ingredient, result, count, container, hasContainer)
                    .setRecipeData(min, max, perfect, time);
            return valid ? recipe : recipe.markInvalid();
        }

        @Override
        public void toNetwork(FriendlyByteBuf buf, SimpleMachineRecipe recipe) {
            recipe.getIngredient().toNetwork(buf);
            ItemStack result = recipe.getResultItem(net.minecraft.core.RegistryAccess.EMPTY);
            buf.writeItem(result);
            buf.writeVarInt(Math.max(1, result.getCount()));
            buf.writeVarInt(recipe.getMinTemperature());
            buf.writeVarInt(recipe.getMaxTemperature());
            buf.writeVarInt(recipe.getPerfectTemperature());
            buf.writeVarInt(recipe.getCraftTime());
            buf.writeBoolean(recipe.isValid());
            boolean hasContainer = recipe instanceof BlenderRecipe blenderRecipe && blenderRecipe.hasContainer();
            buf.writeBoolean(hasContainer);
            if (hasContainer) {
                ((BlenderRecipe) recipe).getContainerIngredient().toNetwork(buf);
            }
        }

        private static boolean isValidIngredient(JsonElement json) {
            if (json == null || json.isJsonNull()) {
                return false;
            }
            if (json.isJsonArray()) {
                var array = json.getAsJsonArray();
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
