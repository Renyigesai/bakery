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
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.ShapedRecipe;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("unused")
public class MultiOutputSingleItemRecipe extends SimpleMachineRecipe {
    private final NonNullList<ItemStack> allResults;

    public MultiOutputSingleItemRecipe(ResourceLocation id, Ingredient ingredient, NonNullList<ItemStack> results, ResourceLocation typeId, ResourceLocation serializerId) {
        super(id, ingredient, results.isEmpty() ? ItemStack.EMPTY : results.get(0), results.isEmpty() ? 1 : Math.max(1, results.get(0).getCount()), typeId, serializerId);
        this.allResults = results;
    }

    public NonNullList<ItemStack> getAllResults() {
        return allResults;
    }

    @Override
    public @NotNull ItemStack assemble(Container container, RegistryAccess registryAccess) {
        return allResults.isEmpty() ? ItemStack.EMPTY : allResults.get(0).copy();
    }

    public interface Factory<T extends MultiOutputSingleItemRecipe> {
        T create(ResourceLocation id, Ingredient ingredient, NonNullList<ItemStack> results);
    }

    public static class Serializer<T extends MultiOutputSingleItemRecipe> implements RecipeSerializer<T> {
        private final Factory<T> factory;

        public Serializer(Factory<T> factory) {
            this.factory = factory;
        }

        @Override
        public @NotNull T fromJson(ResourceLocation id, JsonObject json) {
            Ingredient ingredient = Ingredient.fromJson(GsonHelper.getAsJsonObject(json, "ingredient"));
            NonNullList<ItemStack> results = readResults(GsonHelper.getNonNull(json, "result"));
            return factory.create(id, ingredient, results);
        }

        @Override
        public @NotNull T fromNetwork(ResourceLocation id, FriendlyByteBuf buf) {
            Ingredient ingredient = Ingredient.fromNetwork(buf);
            int size = buf.readVarInt();
            NonNullList<ItemStack> results = NonNullList.create();
            for (int i = 0; i < size; i++) {
                results.add(buf.readItem());
            }
            return factory.create(id, ingredient, results);
        }

        @Override
        public void toNetwork(FriendlyByteBuf buf, T recipe) {
            recipe.getIngredient().toNetwork(buf);
            buf.writeVarInt(recipe.getAllResults().size());
            for (ItemStack stack : recipe.getAllResults()) {
                buf.writeItem(stack);
            }
        }

        private static NonNullList<ItemStack> readResults(JsonElement json) {
            NonNullList<ItemStack> results = NonNullList.create();
            if (json.isJsonArray()) {
                JsonArray array = json.getAsJsonArray();
                for (JsonElement element : array) {
                    results.add(ShapedRecipe.itemStackFromJson(element.getAsJsonObject()));
                }
            } else {
                results.add(ShapedRecipe.itemStackFromJson(json.getAsJsonObject()));
            }
            return results;
        }
    }
}
