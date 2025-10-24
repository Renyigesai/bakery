package com.renyigesai.bakeries.recipe;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.renyigesai.bakeries.BakeriesMod;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class PizzaRecipe implements Recipe<SimpleContainer> {

    private final NonNullList<Ingredient> inputItems;
    private final ItemStack output;
    private final ResourceLocation id;

    public PizzaRecipe(NonNullList<Ingredient> ingredient, ItemStack output, ResourceLocation id) {
        this.inputItems = ingredient;
        this.output = output;
        this.id = id;
    }

    @Override
    public boolean matches(SimpleContainer pContainer, Level pLevel) {
        if (pLevel.isClientSide) {
            return false;
        }
        for (int i = 0; i < inputItems.size(); i++) {
            if (!inputItems.get(i).test(pContainer.getItem(i))) {
                return false;
            }
        }
        return true;
    }

    @Override
    public ItemStack assemble(SimpleContainer pContainer, RegistryAccess pRegistryAccess) {
        return output.copy();
    }

    @Override
    public @NotNull NonNullList<Ingredient> getIngredients() {
        return inputItems;
    }

    @Override
    public boolean canCraftInDimensions(int pWidth, int pHeight) {
        return true;
    }

    @Override
    public ItemStack getResultItem(RegistryAccess pRegistryAccess) {
        return output.copy();
    }

    @Override
    public ResourceLocation getId() {
        return id;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return PizzaRecipe.Serializer.INSTANCE;
    }

    @Override
    public RecipeType<?> getType() {
        return PizzaRecipe.Type.INSTANCE;
    }

    public static class Type implements RecipeType<PizzaRecipe> {
        public static final PizzaRecipe.Type INSTANCE = new PizzaRecipe.Type();
        public static final String ID = "pizza";
    }

    public static class Serializer implements RecipeSerializer<PizzaRecipe> {
        public static final PizzaRecipe.Serializer INSTANCE = new PizzaRecipe.Serializer();
        public static final ResourceLocation ID = new ResourceLocation(BakeriesMod.MODID, "pizza");

        @Override
        public PizzaRecipe fromJson(ResourceLocation pRecipeId, JsonObject pSerializedRecipe) {
            ItemStack output = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(pSerializedRecipe, "output"));

            // 动态获取原料数量
            JsonArray ingredients = GsonHelper.getAsJsonArray(pSerializedRecipe, "ingredients");
            NonNullList<Ingredient> inputs = NonNullList.create();

            for (int i = 0; i < ingredients.size(); i++) {
                inputs.add(Ingredient.fromJson(ingredients.get(i)));
            }

            return new PizzaRecipe(inputs,output,pRecipeId);
        }

        @Override
        public @Nullable PizzaRecipe fromNetwork(ResourceLocation pRecipeId, FriendlyByteBuf pBuffer) {
            int ingredientCount = pBuffer.readInt();
            NonNullList<Ingredient> inputs = NonNullList.withSize(ingredientCount, Ingredient.EMPTY);

            if (inputs.size() > 4){
                throw new JsonParseException("Too many ingredients for coffee recipe! The max is 4");
            }else {
                for (int i = 0; i < ingredientCount; i++) {
                    inputs.set(i, Ingredient.fromNetwork(pBuffer));
                }
                ItemStack output = pBuffer.readItem();
                return new PizzaRecipe(inputs, output,pRecipeId);
            }
        }

        @Override
        public void toNetwork(FriendlyByteBuf pBuffer, PizzaRecipe pRecipe) {
            pBuffer.writeInt(pRecipe.inputItems.size());

            for (Ingredient ingredient : pRecipe.getIngredients()) {
                ingredient.toNetwork(pBuffer);
            }
            pBuffer.writeItemStack(pRecipe.getResultItem(null), false);
        }
    }
}
