package com.renyigesai.bakeries.recipe;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
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
import vectorwing.farmersdelight.common.crafting.ingredient.ChanceResult;

import java.util.Iterator;

public class BreadKnifeRecipe implements Recipe<SimpleContainer> {

    private final Ingredient inputItems;
    private final NonNullList<ItemStack> output;
    private final ResourceLocation id;

    public BreadKnifeRecipe(Ingredient ingredient, NonNullList<ItemStack> output, ResourceLocation id) {
        this.inputItems = ingredient;
        this.output = output;
        this.id = id;
    }

    @Override
    public boolean matches(SimpleContainer pContainer, Level pLevel) {
        return this.inputItems.test(pContainer.getItem(0));
    }

    @Override
    public ItemStack assemble(SimpleContainer pContainer, RegistryAccess pRegistryAccess) {
        return output.get(0).copy();
    }

    @Override
    public @NotNull NonNullList<Ingredient> getIngredients() {
        NonNullList<Ingredient> nonnulllist = NonNullList.create();
        nonnulllist.add(this.inputItems);
        return nonnulllist;
    }

    public NonNullList<ItemStack> getOutput() {
        return output;
    }

    @Override
    public boolean canCraftInDimensions(int pWidth, int pHeight) {
        return true;
    }

    @Override
    public ItemStack getResultItem(RegistryAccess pRegistryAccess) {
        return output.get(0);
    }

    @Override
    public ResourceLocation getId() {
        return id;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return BreadKnifeRecipe.Serializer.INSTANCE;
    }

    @Override
    public RecipeType<?> getType() {
        return BreadKnifeRecipe.Type.INSTANCE;
    }

    public static class Type implements RecipeType<BreadKnifeRecipe> {
        public static final BreadKnifeRecipe.Type INSTANCE = new BreadKnifeRecipe.Type();
        public static final String ID = "bread_knife";
    }

    public static class Serializer implements RecipeSerializer<BreadKnifeRecipe> {
        public static final BreadKnifeRecipe.Serializer INSTANCE = new BreadKnifeRecipe.Serializer();
        public static final ResourceLocation ID = new ResourceLocation(BakeriesMod.MODID, "bread_knife");

        @Override
        public BreadKnifeRecipe fromJson(ResourceLocation pRecipeId, JsonObject pSerializedRecipe) {
            JsonArray output = GsonHelper.getAsJsonArray(pSerializedRecipe, "output");
            NonNullList<ItemStack> outputs = readResults(output);
            JsonArray ingredients = GsonHelper.getAsJsonArray(pSerializedRecipe, "ingredients");
            NonNullList<Ingredient> inputs = NonNullList.create();
            inputs.add(Ingredient.fromJson(ingredients.get(0)));

            return new BreadKnifeRecipe(inputs.get(0),outputs,pRecipeId);
        }

        private static NonNullList<ItemStack> readResults(JsonArray resultArray) {
            NonNullList<ItemStack> results = NonNullList.create();
            Iterator var2 = resultArray.iterator();
            while(var2.hasNext()) {
                JsonObject result = (JsonObject)var2.next();
                results.add(ShapedRecipe.itemStackFromJson(result));
            }

            return results;
        }

        @Override
        public @Nullable BreadKnifeRecipe fromNetwork(ResourceLocation pRecipeId, FriendlyByteBuf pBuffer) {
            Ingredient ingredient = Ingredient.fromNetwork(pBuffer);
            int outputCount = pBuffer.readVarInt();
            NonNullList<ItemStack> outputs = NonNullList.withSize(outputCount, ItemStack.EMPTY);
            for (int i = 0; i < outputCount; ++i) {
                outputs.set(i, pBuffer.readItem());
            }
            return new BreadKnifeRecipe(ingredient, outputs, pRecipeId);
        }

        @Override
        public void toNetwork(FriendlyByteBuf pBuffer, BreadKnifeRecipe pRecipe) {
            pRecipe.inputItems.toNetwork(pBuffer);
            pBuffer.writeVarInt(pRecipe.output.size());
            for (ItemStack stack : pRecipe.output) {
                pBuffer.writeItem(stack);
            }
        }
    }
}
