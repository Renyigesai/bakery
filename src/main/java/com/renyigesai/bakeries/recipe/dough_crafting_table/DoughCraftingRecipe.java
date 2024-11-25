package com.renyigesai.bakeries.recipe.dough_crafting_table;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.renyigesai.bakeries.BakeriesMod;
import lombok.Getter;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;

public class DoughCraftingRecipe implements  Recipe<Container> {
    @Getter
    private final ResourceLocation id;
    /**主料*/
    final Ingredient mainIngredient;//主料
    /**调味料*/
    final ItemStack flavoring;//调味料
    /**添加剂*/
    final ItemStack additive;//添加剂
    /**副料*/
    final ItemStack additive_food;//副料
    /**输出*/
    final ItemStack result;//输出
    public DoughCraftingRecipe(ResourceLocation pId, Ingredient pMainIngredient, ItemStack pFlavoring, ItemStack pAdditive, ItemStack pAdditiveFood, ItemStack pResult) {
        this.id = pId;
        this.mainIngredient = pMainIngredient;
        this.flavoring = pFlavoring;
        this.additive = pAdditive;
        this.additive_food = pAdditiveFood;
        this.result = pResult;
    }
    @Override
    public NonNullList<Ingredient> getIngredients() {
        NonNullList<Ingredient> nonnulllist = NonNullList.create();
        nonnulllist.add(this.mainIngredient);
        return nonnulllist;
    }

    @Override
    public ResourceLocation getId() {
        return null;
    }

    @Override
    public boolean matches(Container pContainer, Level pLevel) {
        return pContainer.getItem(0).getItem().equals(mainIngredient.getItems()[0].getItem()) &&
                pContainer.getItem(1).getItem().equals(flavoring.getItem()) &&
                pContainer.getItem(2).getItem().equals(additive.getItem()) &&
                pContainer.getItem(3).getItem().equals(additive_food.getItem());

    }
    @Override
    public ItemStack assemble(Container pContainer, RegistryAccess pRegistryAccess) {
        return this.result.copy();
    }

    @Override
    public boolean canCraftInDimensions(int pWidth, int pHeight) {
        return false;
    }

    public ItemStack getFlavoringItem(RegistryAccess pRegistryAccess) {
        return flavoring.copy();
    }

    public ItemStack getAdditiveItem(RegistryAccess pRegistryAccess) {
        return additive.copy();
    }

    public ItemStack getAdditiveFoodItem(RegistryAccess pRegistryAccess) {
        return additive_food.copy();
    }

    @Override
    public ItemStack getResultItem(RegistryAccess pRegistryAccess) {
        return result.copy();
    }
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
        public static final DoughCraftingRecipe.Type INSTANCE = new DoughCraftingRecipe.Type();
    }


    public static class Serializer implements RecipeSerializer<DoughCraftingRecipe> {
        public static final Serializer INSTANCE = new Serializer();
        public Serializer(){}
        private static final ResourceLocation NAME = new ResourceLocation(BakeriesMod.MODID, "dough_crafting_table");

        @Override
        public DoughCraftingRecipe fromJson(ResourceLocation pRecipeId, JsonObject pJson) {
            JsonElement jsonelement = (JsonElement)(GsonHelper.isArrayNode(pJson, "ingredient") ? GsonHelper.getAsJsonArray(pJson, "ingredient") : GsonHelper.getAsJsonObject(pJson, "ingredient"));
            Ingredient mainIngredient = Ingredient.fromJson(jsonelement, false);
            ItemStack flavoring = ItemStack.EMPTY;
            if(pJson.has("flavoring")){
                flavoring = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(pJson, "flavoring"));
            }
            ItemStack additive = ItemStack.EMPTY;
            if(pJson.has("additive")){
                additive = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(pJson, "additive"));
            }
            ItemStack additiveFood = ItemStack.EMPTY;
            if(pJson.has("additive_food")){
                additiveFood = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(pJson, "additive_food"));
            }
            ItemStack result = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(pJson, "result"));

            return new DoughCraftingRecipe(pRecipeId, mainIngredient, flavoring, additive, additiveFood, result);
        }
        @Override
        public DoughCraftingRecipe fromNetwork(ResourceLocation recipeId, FriendlyByteBuf buffer) {
            Ingredient mainIngredient = Ingredient.fromNetwork(buffer);
            ItemStack flavoring = buffer.readItem();
            if (flavoring.isEmpty()) {
                flavoring = ItemStack.EMPTY;
            }
            ItemStack additive = buffer.readItem();
            if (additive.isEmpty()) {
                additive = ItemStack.EMPTY;
            }
            ItemStack additiveFood = buffer.readItem();
            if (additiveFood.isEmpty()) {
                additiveFood = ItemStack.EMPTY;
            }

            ItemStack result = buffer.readItem();

            return new DoughCraftingRecipe(recipeId, mainIngredient, flavoring, additive, additiveFood, result);
        }
        @Override
        public void toNetwork(FriendlyByteBuf buffer, DoughCraftingRecipe recipe) {
            buffer.writeInt(recipe.getIngredients().size());
            for (Ingredient ing : recipe.getIngredients()) {
                ing.toNetwork(buffer);
            }
            buffer.writeItem(recipe.flavoring);
            buffer.writeItem(recipe.additive);
            buffer.writeItem(recipe.additive_food);
            buffer.writeItem(recipe.result);
        }
    }
}
