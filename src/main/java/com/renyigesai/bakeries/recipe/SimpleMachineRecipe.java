package com.renyigesai.bakeries.recipe;

import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;

import com.google.gson.JsonObject;
import net.minecraft.core.registries.BuiltInRegistries;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

@SuppressWarnings("unused")
public class SimpleMachineRecipe implements Recipe<Container> {
    private final ResourceLocation id;
    private final Ingredient ingredient;
    private final ItemStack result;
    private final int count;
    private final ResourceLocation typeId;
    private final ResourceLocation serializerId;

    public SimpleMachineRecipe(ResourceLocation id, Ingredient ingredient, ItemStack result, int count, ResourceLocation typeId, ResourceLocation serializerId) {
        this.id = id;
        this.ingredient = ingredient;
        this.result = result;
        this.count = count;
        this.typeId = typeId;
        this.serializerId = serializerId;
    }

    @Override
    public boolean matches(Container container, Level level) {
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

    public static class Serializer implements RecipeSerializer<SimpleMachineRecipe> {
        public interface Factory {
            SimpleMachineRecipe create(ResourceLocation id, Ingredient ingredient, ItemStack result, int count);
        }

        private final Factory factory;

        public Serializer(Factory factory) {
            this.factory = factory;
        }

        @Override
        public @NotNull SimpleMachineRecipe fromJson(ResourceLocation id, JsonObject json) {
            Ingredient ingredient = Ingredient.fromJson(GsonHelper.getAsJsonObject(json, "ingredient"));
            JsonObject resultJson = GsonHelper.getAsJsonObject(json, "result");
            ItemStack result = net.minecraft.world.item.crafting.ShapedRecipe.itemStackFromJson(resultJson);
            int count = GsonHelper.getAsInt(resultJson, "count", Math.max(1, result.getCount()));
            return factory.create(id, ingredient, result, count);
        }

        @Override
        public @NotNull SimpleMachineRecipe fromNetwork(ResourceLocation id, FriendlyByteBuf buf) {
            Ingredient ingredient = Ingredient.fromNetwork(buf);
            ItemStack result = buf.readItem();
            int count = buf.readVarInt();
            return factory.create(id, ingredient, result, count);
        }

        @Override
        public void toNetwork(FriendlyByteBuf buf, SimpleMachineRecipe recipe) {
            recipe.ingredient.toNetwork(buf);
            buf.writeItem(recipe.result);
            buf.writeVarInt(recipe.count);
        }
    }
}
