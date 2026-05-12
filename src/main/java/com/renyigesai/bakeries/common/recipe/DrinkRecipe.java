package com.renyigesai.bakeries.common.recipe;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.common.util.RecipeMatcher;
import net.neoforged.neoforge.items.wrapper.RecipeWrapper;

import java.util.ArrayList;
import java.util.List;

public class DrinkRecipe implements Recipe<RecipeWrapper> {

    private final NonNullList<Ingredient> inputItems;
    private final ItemStack output;

    public static final String ID = "drink";

    public DrinkRecipe(NonNullList<Ingredient> ingredients, ItemStack output) {
        this.inputItems = ingredients;
        this.output = output;
    }

    @Override
    public boolean matches(RecipeWrapper inv, Level level) {
        List<ItemStack> inputs = new ArrayList<>();
        int i = 0;

        for (int j = 0; j < 4; ++j) {
            ItemStack itemstack = inv.getItem(j);
            if (!itemstack.isEmpty()) {
                ++i;
                inputs.add(itemstack);
            }
        }
        return i == this.inputItems.size() && RecipeMatcher.findMatches(inputs, this.inputItems) != null;
    }

    @Override
    public ItemStack assemble(RecipeWrapper recipeInput, HolderLookup.Provider provider) {
        return output.copy();
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return width * height >= inputItems.size();
    }

    @Override
    public ItemStack getResultItem(HolderLookup.Provider provider) {
        return output.copy();
    }

    public NonNullList<Ingredient> getInputItems() {
        return inputItems;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return Serializer.INSTANCE;
    }

    @Override
    public RecipeType<?> getType() {
        return Type.INSTANCE;
    }


    public enum Type implements RecipeType<DrinkRecipe> {
        INSTANCE
    }

    public static class Serializer implements RecipeSerializer<DrinkRecipe> {
        public static final Serializer INSTANCE = new Serializer();
        private static final MapCodec<DrinkRecipe> CODEC = RecordCodecBuilder.mapCodec(inst -> inst.group(
                Ingredient.CODEC.listOf().fieldOf("ingredients").xmap(
                        ingredients -> {
                            NonNullList<Ingredient> nonNullList = NonNullList.create();
                            nonNullList.addAll(ingredients);
                            if (nonNullList.isEmpty()) {
                                throw new IllegalArgumentException("Ingredients list cannot be empty");
                            }
                            if (nonNullList.size() > 4) {
                                throw new IllegalArgumentException("Too many ingredients for drink recipe! The max is 4");
                            }
                            return nonNullList;
                        },
                        ArrayList::new
                ).forGetter(DrinkRecipe::getInputItems),
                ItemStack.STRICT_CODEC.fieldOf("output").forGetter(recipe -> recipe.output)).apply(inst, DrinkRecipe::new));

        public static final StreamCodec<RegistryFriendlyByteBuf, DrinkRecipe> STREAM_CODEC =
                StreamCodec.of(Serializer::toNetwork, Serializer::fromNetwork);

        @Override
        public MapCodec<DrinkRecipe> codec() {
            return CODEC;
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, DrinkRecipe> streamCodec() {
            return STREAM_CODEC;
        }

        private static DrinkRecipe fromNetwork(RegistryFriendlyByteBuf buffer) {
            int i = buffer.readVarInt();
            NonNullList<Ingredient> inputItemsIn = NonNullList.withSize(i, Ingredient.EMPTY);
            inputItemsIn.replaceAll(ignored -> Ingredient.CONTENTS_STREAM_CODEC.decode(buffer));
            ItemStack outputIn = ItemStack.STREAM_CODEC.decode(buffer);
            return new DrinkRecipe(inputItemsIn, outputIn);
        }

        private static void toNetwork(RegistryFriendlyByteBuf buffer, DrinkRecipe recipe) {
            buffer.writeVarInt(recipe.inputItems.size());

            for (Ingredient ingredient : recipe.inputItems) {
                Ingredient.CONTENTS_STREAM_CODEC.encode(buffer, ingredient);
            }

            ItemStack.STREAM_CODEC.encode(buffer, recipe.output);
        }
    }
}