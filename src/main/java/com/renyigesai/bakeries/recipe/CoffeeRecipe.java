package com.renyigesai.bakeries.recipe;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.core.NonNullList;
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
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import net.minecraft.core.registries.BuiltInRegistries;

public class CoffeeRecipe implements Recipe<Container> {
    private final ResourceLocation id;
    private final NonNullList<Ingredient> ingredients;
    private final ItemStack output;
    private final ResourceLocation serializerId;
    private final ResourceLocation typeId;
    private boolean valid = true;

    public CoffeeRecipe(ResourceLocation id, NonNullList<Ingredient> ingredients, ItemStack output, ResourceLocation serializerId, ResourceLocation typeId) {
        this.id = id;
        this.ingredients = ingredients;
        this.output = output;
        this.serializerId = serializerId;
        this.typeId = typeId;
    }

    @Override
    public boolean matches(Container container, Level level) {
        if (!isValid()) {
            return false;
        }
        List<ItemStack> available = new ArrayList<>();
        for (int i = 0; i < Math.min(4, container.getContainerSize()); i++) {
            ItemStack stack = container.getItem(i);
            if (!stack.isEmpty()) {
                available.add(stack);
            }
        }
        if (available.size() != ingredients.size()) {
            return false;
        }
        boolean[] used = new boolean[available.size()];
        for (Ingredient ingredient : ingredients) {
            boolean matched = false;
            for (int i = 0; i < available.size(); i++) {
                if (used[i]) {
                    continue;
                }
                if (ingredient.test(available.get(i))) {
                    used[i] = true;
                    matched = true;
                    break;
                }
            }
            if (!matched) {
                return false;
            }
        }
        return true;
    }

    @Override
    public @NotNull ItemStack assemble(Container container, RegistryAccess registryAccess) {
        return output.copy();
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return width * height >= ingredients.size();
    }

    @Override
    public @NotNull ItemStack getResultItem(RegistryAccess registryAccess) {
        return output.copy();
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
        return true;
    }

    public NonNullList<Ingredient> getIngredientsList() {
        return ingredients;
    }

    public boolean isValid() {
        if (!valid || output.isEmpty() || ingredients.isEmpty()) {
            return false;
        }
        return true;
    }

    public CoffeeRecipe markInvalid() {
        valid = false;
        return this;
    }

    @Override
    public @NotNull NonNullList<Ingredient> getIngredients() {
        return ingredients;
    }

    public static class Serializer implements RecipeSerializer<CoffeeRecipe> {
        private final ResourceLocation serializerId;
        private final ResourceLocation typeId;

        public Serializer(ResourceLocation serializerId, ResourceLocation typeId) {
            this.serializerId = serializerId;
            this.typeId = typeId;
        }

        @Override
        public @NotNull CoffeeRecipe fromJson(ResourceLocation id, JsonObject json) {
            JsonArray ingredientsJson = GsonHelper.getAsJsonArray(json, "ingredients");
            JsonObject outputJson = GsonHelper.getAsJsonObject(json, "result");
            if (ingredientsJson.isEmpty() || !isValidItemStack(outputJson)) {
                return createInvalid(id, serializerId, typeId);
            }
            NonNullList<Ingredient> ingredients = NonNullList.create();
            for (int i = 0; i < ingredientsJson.size(); i++) {
                JsonElement element = ingredientsJson.get(i);
                if (isEmptyIngredient(element)) {
                    continue;
                }
                if (!isValidIngredient(element)) {
                    return createInvalid(id, serializerId, typeId);
                }
                ingredients.add(Ingredient.fromJson(element));
            }
            if (ingredients.isEmpty()) {
                return createInvalid(id, serializerId, typeId);
            }
            ItemStack output = net.minecraft.world.item.crafting.ShapedRecipe.itemStackFromJson(outputJson);
            return new CoffeeRecipe(id, ingredients, output, serializerId, typeId);
        }

        @Override
        public @NotNull CoffeeRecipe fromNetwork(ResourceLocation id, FriendlyByteBuf buf) {
            int size = buf.readVarInt();
            NonNullList<Ingredient> ingredients = NonNullList.withSize(size, Ingredient.EMPTY);
            for (int i = 0; i < size; i++) {
                ingredients.set(i, Ingredient.fromNetwork(buf));
            }
            ItemStack output = buf.readItem();
            boolean valid = buf.readBoolean();
            CoffeeRecipe recipe = new CoffeeRecipe(id, ingredients, output, serializerId, typeId);
            return valid ? recipe : recipe.markInvalid();
        }

        @Override
        public void toNetwork(FriendlyByteBuf buf, CoffeeRecipe recipe) {
            buf.writeVarInt(recipe.ingredients.size());
            for (Ingredient ingredient : recipe.ingredients) {
                ingredient.toNetwork(buf);
            }
            buf.writeItem(recipe.output);
            buf.writeBoolean(recipe.valid);
        }

        private static CoffeeRecipe createInvalid(ResourceLocation id, ResourceLocation serializerId, ResourceLocation typeId) {
            NonNullList<Ingredient> ingredients = NonNullList.create();
            ingredients.add(Ingredient.of(Items.BARRIER));
            return new CoffeeRecipe(id, ingredients, new ItemStack(Items.BARRIER), serializerId, typeId).markInvalid();
        }

        private static boolean isEmptyIngredient(JsonElement json) {
            if (json == null || json.isJsonNull()) {
                return true;
            }
            if (json.isJsonArray()) {
                return json.getAsJsonArray().isEmpty();
            }
            return json.isJsonObject() && json.getAsJsonObject().entrySet().isEmpty();
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
