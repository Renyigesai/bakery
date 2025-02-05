package com.renyigesai.bakeries.recipe.dough_crafting_table;

import com.google.gson.JsonObject;
import com.renyigesai.bakeries.BakeriesMod;
import com.renyigesai.bakeries.init.BakeriesBlocks;
import lombok.Getter;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.BuiltInRegistries;
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
import org.jetbrains.annotations.NotNull;

public class DoughCraftingRecipe implements  Recipe<Container> {
    @Getter
    private final ResourceLocation id;
    final Ingredient ingredient;
    final ItemStack result;
    public DoughCraftingRecipe(ResourceLocation pId, Ingredient pIngredient, ItemStack pResult) {
        this.id = pId;
        this.ingredient = pIngredient;
        this.result = pResult;
    }
    @Override
    public @NotNull NonNullList<Ingredient> getIngredients() {
        NonNullList<Ingredient> nonnulllist = NonNullList.create();
        nonnulllist.add(this.ingredient);
        return nonnulllist;
    }
    @Override
    public boolean matches(Container pContainer, @NotNull Level pLevel) {
        return this.ingredient.test(pContainer.getItem(0));

    }
    @Override
    public @NotNull ItemStack assemble(@NotNull Container pContainer, @NotNull RegistryAccess pRegistryAccess) {
        return this.result.copy();
    }

    @Override
    public boolean canCraftInDimensions(int pWidth, int pHeight) {
        return true;
    }

    @Override
    public @NotNull ItemStack getResultItem(@NotNull RegistryAccess pRegistryAccess) {
        return result.copy();
    }
    public @NotNull RecipeSerializer<?> getSerializer() {
        return Serializer.INSTANCE;
    }

    @Override
    public @NotNull RecipeType<?> getType() {
        return Type.INSTANCE;
    }
    public @NotNull ItemStack getToastSymbol() {
        return new ItemStack(BakeriesBlocks.DOUGH_CRAFTING_TABLE.get());
    }
    public static class Type implements RecipeType<DoughCraftingRecipe> {
        private Type() {
        }
        public static final String ID = "dough_crafting_table";
        public static final DoughCraftingRecipe.Type INSTANCE = new DoughCraftingRecipe.Type();
    }


    public static class Serializer implements RecipeSerializer<DoughCraftingRecipe> {
        public static final Serializer INSTANCE = new Serializer();
        public Serializer(){}
        public static final ResourceLocation ID = new ResourceLocation(BakeriesMod.MODID, "dough_crafting_table");

        @Override
        public @NotNull DoughCraftingRecipe fromJson(@NotNull ResourceLocation pRecipeId, @NotNull JsonObject pJson) {
            Ingredient ingredient;
            if (GsonHelper.isArrayNode(pJson, "ingredient")) {
                ingredient = Ingredient.fromJson(GsonHelper.getAsJsonArray(pJson, "ingredient"), false);
            } else {
                ingredient = Ingredient.fromJson(GsonHelper.getAsJsonObject(pJson, "ingredient"), false);
            }

            String s1 = GsonHelper.getAsString(pJson, "result");
            int i = GsonHelper.getAsInt(pJson, "count");
            ItemStack result = new ItemStack(BuiltInRegistries.ITEM.get(new ResourceLocation(s1)), i);

            return new DoughCraftingRecipe(pRecipeId, ingredient, result);
        }
        @Override
        public DoughCraftingRecipe fromNetwork(@NotNull ResourceLocation recipeId, @NotNull FriendlyByteBuf pBuffer) {
            Ingredient ingredient = Ingredient.fromNetwork(pBuffer);
            ItemStack result = pBuffer.readItem();

            return new DoughCraftingRecipe(recipeId, ingredient, result);
        }
        @Override
        public void toNetwork(@NotNull FriendlyByteBuf pBuffer, DoughCraftingRecipe pRecipe) {
            pRecipe.ingredient.toNetwork(pBuffer);
            pBuffer.writeItem(pRecipe.result);
        }
    }
}
