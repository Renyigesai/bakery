package com.renyigesai.bakeries.recipe;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.renyigesai.bakeries.init.BakeriesRecipeSerializers;
import com.renyigesai.bakeries.init.BakeriesRecipeTypes;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

public class FermentationBoxRecipe implements Recipe<SimpleContainer> {
    private final Ingredient input;
    private final ItemStack output;
    private final ResourceLocation id;
    private final ItemStack failItem;

    public FermentationBoxRecipe(Ingredient input, ItemStack output, ResourceLocation id, ItemStack failItem) {
        this.input = input;
        this.output = output;
        this.id = id;
        if (failItem.isEmpty()){
            this.failItem = ItemStack.EMPTY;
        }else {
            this.failItem = failItem;
        }
    }

    public FermentationBoxRecipe(Ingredient input, ItemStack output, ResourceLocation id) {
        this.input = input;
        this.output = output;
        this.id = id;
        this.failItem = ItemStack.EMPTY;
    }

    @Override
    public boolean matches(SimpleContainer container, Level level) {
        return this.input.test(container.getItem(0));
    }

    @Override
    public ItemStack assemble(SimpleContainer container, RegistryAccess registryAccess) {
        return this.output.copy();
    }

    @Override
    public boolean canCraftInDimensions(int i, int i1) {
        return true;
    }

    @Override
    public ItemStack getResultItem(RegistryAccess registryAccess) {
        return this.output.copy();
    }

    @Override
    public ResourceLocation getId() {
        return this.id;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return BakeriesRecipeSerializers.FERMENTATION_BOX.get();
    }

    @Override
    public RecipeType<?> getType() {
        return BakeriesRecipeTypes.FERMENTATION_BOX.get();
    }

    public static class Serializer implements RecipeSerializer<FermentationBoxRecipe> {

        @Override
        public FermentationBoxRecipe fromJson(ResourceLocation pRecipeId, JsonObject pSerializedRecipe) {
            ItemStack output = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(pSerializedRecipe, "output"));
            JsonArray ingredients = GsonHelper.getAsJsonArray(pSerializedRecipe, "ingredients");
            NonNullList<Ingredient> inputs = NonNullList.create();
            inputs.add(Ingredient.fromJson(ingredients.get(0)));

            return new FermentationBoxRecipe(inputs.get(0),output,pRecipeId);
        }

        @Override
        public @Nullable FermentationBoxRecipe fromNetwork(ResourceLocation pRecipeId, FriendlyByteBuf pBuffer) {
            Ingredient ingredient = Ingredient.fromNetwork(pBuffer);
            ItemStack output = pBuffer.readItem();
            return new FermentationBoxRecipe(ingredient, output, pRecipeId);
        }

        @Override
        public void toNetwork(FriendlyByteBuf pBuffer, FermentationBoxRecipe pRecipe) {
            pRecipe.input.toNetwork(pBuffer);
            pBuffer.writeItem(pRecipe.getResultItem(null));
        }
    }
}
