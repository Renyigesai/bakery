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

public class StoneKilnRecipe implements Recipe<SimpleContainer> {

    private final NonNullList<Ingredient> inputItems;
    private final ItemStack output;
    private final int[] time;
    private final ResourceLocation id;

    public StoneKilnRecipe(NonNullList<Ingredient> ingredient, ItemStack output, int[] time, ResourceLocation id) {
        this.inputItems = ingredient;
        this.output = output;
        this.time = time;
        this.id = id;
    }

    @Override
    public boolean matches(SimpleContainer pContainer, Level pLevel) {
        if (pLevel.isClientSide) {
            return false;
        }
        return inputItems.get(0).test(pContainer.getItem(0));
    }

    public int[] getTime() {
        return time;
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
        return StoneKilnRecipe.Serializer.INSTANCE;
    }

    @Override
    public RecipeType<?> getType() {
        return StoneKilnRecipe.Type.INSTANCE;
    }

    public static class Type implements RecipeType<StoneKilnRecipe> {
        public static final StoneKilnRecipe.Type INSTANCE = new StoneKilnRecipe.Type();
        public static final String ID = "stone_kiln";
    }

    public static class Serializer implements RecipeSerializer<StoneKilnRecipe> {
        public static final StoneKilnRecipe.Serializer INSTANCE = new StoneKilnRecipe.Serializer();
        public static final ResourceLocation ID = new ResourceLocation(BakeriesMod.MODID, "stone_kiln");

        @Override
        public StoneKilnRecipe fromJson(ResourceLocation pRecipeId, JsonObject pSerializedRecipe) {
            ItemStack output = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(pSerializedRecipe, "output"));

            // 动态获取原料数量
            JsonArray ingredients = GsonHelper.getAsJsonArray(pSerializedRecipe, "ingredients");
            NonNullList<Ingredient> inputs = NonNullList.create();
            int[] time;
            if (pSerializedRecipe.has("cooking_time") && pSerializedRecipe.get("cooking_time").isJsonArray()) {
                JsonArray timeArray = GsonHelper.getAsJsonArray(pSerializedRecipe, "cooking_time");
                time = new int[timeArray.size()];
                for (int i = 0; i < timeArray.size(); i++) {
                    time[i] = timeArray.get(i).getAsInt();
                }
            } else if (pSerializedRecipe.has("cooking_time")) {
                time = new int[]{GsonHelper.getAsInt(pSerializedRecipe, "cooking_time")};
            } else {
                throw new JsonParseException("Missing cooking_time in recipe");
            }

            inputs.add(Ingredient.fromJson(ingredients.get(0)));
            return new StoneKilnRecipe(inputs,output,time,pRecipeId);
        }

        @Override
        public @Nullable StoneKilnRecipe fromNetwork(ResourceLocation pRecipeId, FriendlyByteBuf pBuffer) {
            int ingredientCount = pBuffer.readInt();
            int timeLength = pBuffer.readVarInt();
            int[] time = new int[timeLength];
            for (int i = 0; i < timeLength; i++) {
                time[i] = pBuffer.readVarInt();
            }
            NonNullList<Ingredient> inputs = NonNullList.withSize(ingredientCount, Ingredient.EMPTY);;

            if (inputs.size() > 1){
                throw new JsonParseException("Too many ingredients for stone kiln recipe! The max is 1");
            }else {
                inputs.set(0, Ingredient.fromNetwork(pBuffer));
                ItemStack output = pBuffer.readItem();
                return new StoneKilnRecipe(inputs, output, time, pRecipeId);
            }
        }

        @Override
        public void toNetwork(FriendlyByteBuf pBuffer, StoneKilnRecipe pRecipe) {
            pBuffer.writeInt(pRecipe.inputItems.size());
            pBuffer.writeVarInt(pRecipe.time.length);
            for (int t : pRecipe.time) {
                pBuffer.writeVarInt(t);
            }

            for (Ingredient ingredient : pRecipe.getIngredients()) {
                ingredient.toNetwork(pBuffer);
            }
            pBuffer.writeItemStack(pRecipe.getResultItem(null), false);
        }
    }
}
