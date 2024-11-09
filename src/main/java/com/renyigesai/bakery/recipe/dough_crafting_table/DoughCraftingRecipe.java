package com.renyigesai.bakery.recipe.dough_crafting_table;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;

public class DoughCraftingRecipe implements Recipe<Container> {
    private final ResourceLocation id;
    final ItemStack mainItem;
    final ItemStack result;
    final NonNullList<Ingredient> ingredients;
    private final boolean isSimple;

    public DoughCraftingRecipe(ResourceLocation pId, ItemStack pMainItem, ItemStack pResult, NonNullList<Ingredient> pIngredients) {
        this.id = pId;
        this.mainItem = pMainItem;
        this.result = pResult;
        this.ingredients = pIngredients;
        this.isSimple = pIngredients.stream().allMatch(Ingredient::isSimple);
    }
    public boolean hasMainIngredient(ItemStack mainItem) {
        return this.mainItem.is(mainItem.getItem());
    }


    public ItemStack getRequiredMainIngredient() {
        return this.mainItem.copy();
    }

    public boolean matches(ItemStack mainItem, NonNullList<ItemStack> secondaryItems) {
        if (this.mainItem == null || mainItem.isEmpty() || !this.mainItem.is(mainItem.getItem())) {
            return false;
        }
        if (this.ingredients.size() != secondaryItems.size()) {
            return false;
        }
        for (Ingredient ingredient : this.ingredients) {
            boolean found = false;
            for (ItemStack item : secondaryItems) {
                if (ingredient.test(item)) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean matches(Container pContainer, Level pLevel) {
        return false;
    }

    @Override
    public ItemStack assemble(Container pContainer, RegistryAccess pRegistryAccess) {
        return this.result.copy();
    }

    @Override
    public boolean canCraftInDimensions(int pWidth, int pHeight) {
        return true;
    }

    @Override
    public ItemStack getResultItem(RegistryAccess pRegistryAccess) {
        return this.result;
    }

    @Override
    public ResourceLocation getId() {
        return this.id;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return Serializer.INSTANCE;
    }

    @Override
    public RecipeType<?> getType() {
        return Type.INSTANCE;
    }

    public static class Type implements RecipeType<DoughCraftingRecipe> {
        private Type() {
        }

        public static final Type INSTANCE = new Type();
    }

    public static class Serializer implements RecipeSerializer<DoughCraftingRecipe> {
        public static final Serializer INSTANCE = new Serializer();

        public Serializer() {
        }

        public DoughCraftingRecipe fromJson(ResourceLocation pRecipeId, JsonObject pJson) {
            NonNullList<Ingredient> nonnulllist = itemsFromJson(GsonHelper.getAsJsonArray(pJson, "ingredients"));
            if (nonnulllist.isEmpty()) {
                throw new JsonParseException("No ingredients for shapeless recipe");
            } else if (nonnulllist.size() > 4) {
                throw new JsonParseException("Too many ingredients for shapeless recipe. The maximum is 4.");
            } else {
                ItemStack main = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(pJson, "main_item"));
                ItemStack output = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(pJson, "output"));
                return new DoughCraftingRecipe(pRecipeId, main, output, nonnulllist);
            }
        }

        private static NonNullList<Ingredient> itemsFromJson(JsonArray pIngredientArray) {
            NonNullList<Ingredient> nonnulllist = NonNullList.create();
            for (int i = 0; i < pIngredientArray.size(); ++i) {
                Ingredient ingredient = Ingredient.fromJson(pIngredientArray.get(i), false);
                nonnulllist.add(ingredient);
            }
            return nonnulllist;
        }

        public DoughCraftingRecipe fromNetwork(ResourceLocation pRecipeId, FriendlyByteBuf pBuffer) {
            int i = pBuffer.readVarInt();
            NonNullList<Ingredient> nonnulllist = NonNullList.withSize(i, Ingredient.EMPTY);
            for (int j = 0; j < nonnulllist.size(); ++j) {
                nonnulllist.set(j, Ingredient.fromNetwork(pBuffer));
            }
            ItemStack main = pBuffer.readItem();
            ItemStack output = pBuffer.readItem();
            return new DoughCraftingRecipe(pRecipeId, main, output, nonnulllist);
        }

        public void toNetwork(FriendlyByteBuf pBuffer, DoughCraftingRecipe pRecipe) {
            pBuffer.writeVarInt(pRecipe.ingredients.size());
            for (Ingredient ingredient : pRecipe.ingredients) {
                ingredient.toNetwork(pBuffer);
            }
            pBuffer.writeItem(pRecipe.mainItem);
            pBuffer.writeItem(pRecipe.result);
        }
    }
}
