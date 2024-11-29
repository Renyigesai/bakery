package com.renyigesai.bakeries.recipe.flour_sieve;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.renyigesai.bakeries.BakeriesMod;
import com.renyigesai.bakeries.recipe.dough_crafting_table.DoughCraftingRecipe;
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
import org.jetbrains.annotations.Nullable;

public class FlourSieveRecipe implements Recipe<Container> {
    @Getter
    private final ResourceLocation id;
    /**主料*/
    final NonNullList<Ingredient> ingredient;//主料
    final ItemStack result;
    public FlourSieveRecipe(ResourceLocation pId, NonNullList<Ingredient> pIngredient, ItemStack pResult){
        this.id = pId;
        this.ingredient = pIngredient;
        this.result = pResult;
    }
    @Override
    public boolean matches(Container pContainer, Level pLevel) {
        for (int i = 0; i < pContainer.getContainerSize(); i++) {
            ItemStack slotItem = pContainer.getItem(i);
            if (!slotItem.isEmpty() && slotItem.equals(this.ingredient)) {
                return true;
            }
        }
        return false;
    }
    @Override
    public ItemStack assemble(Container pContainer, RegistryAccess pRegistryAccess) {
        return this.result.copy();
    }
    @Override
    public NonNullList<Ingredient> getIngredients() {
        return ingredient;
    }
    @Override
    public boolean canCraftInDimensions(int pWidth, int pHeight) {
        return true;
    }
    @Override
    public ItemStack getResultItem(RegistryAccess pRegistryAccess) {
        return this.result.copy();
    }
    @Override
    public RecipeSerializer<?> getSerializer() {
        return Serializer.INSTANCE;
    }
    @Override
    public RecipeType<?> getType() {
        return Type.INSTANCE;
    }
    public static class Type implements RecipeType<FlourSieveRecipe> {
        private Type() {
        }
        public static final String ID = "flour_sieve";
        public static final FlourSieveRecipe.Type INSTANCE = new FlourSieveRecipe.Type();
    }
    public static class Serializer implements RecipeSerializer<FlourSieveRecipe> {
        public static final DoughCraftingRecipe.Serializer INSTANCE = new DoughCraftingRecipe.Serializer();
        public Serializer(){}
        private static final ResourceLocation NAME = new ResourceLocation(BakeriesMod.MODID, "flour_sieve");
        @Override
        public FlourSieveRecipe fromJson(ResourceLocation pRecipeId, JsonObject pSerializedRecipe) {
            JsonArray ingredients = GsonHelper.getAsJsonArray(pSerializedRecipe, "ingredient");
            NonNullList<Ingredient> ingredient = NonNullList.withSize(1, Ingredient.EMPTY);
            for (int i = 0; i < ingredients.size(); i++) {
                ingredient.add(i, Ingredient.fromJson(ingredients.get(i)));
            }
            ItemStack result = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(pSerializedRecipe, "result"));
            return new FlourSieveRecipe(pRecipeId, ingredient, result);
        }
        @Override
        public @Nullable FlourSieveRecipe fromNetwork(ResourceLocation pRecipeId, FriendlyByteBuf pBuffer) {
            NonNullList<Ingredient> ingredient = NonNullList.withSize(pBuffer.readInt(), Ingredient.EMPTY);
            for (int i = 0; i < ingredient.size(); i++) {
                ingredient.set(i, Ingredient.fromNetwork(pBuffer));
            }
            ItemStack result = pBuffer.readItem();
            return new FlourSieveRecipe(pRecipeId, ingredient, result);
        }
        @Override
        public void toNetwork(FriendlyByteBuf pBuffer, FlourSieveRecipe pRecipe) {
            pBuffer.writeInt(pRecipe.getIngredients().size());
            for (Ingredient ing : pRecipe.getIngredients()) {
                ing.toNetwork(pBuffer);
            }
            pBuffer.writeItem(pRecipe.result);
        }
    }
}
