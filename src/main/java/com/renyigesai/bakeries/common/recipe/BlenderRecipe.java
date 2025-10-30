package com.renyigesai.bakeries.common.recipe;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.common.util.RecipeMatcher;
import net.neoforged.neoforge.items.wrapper.RecipeWrapper;

import java.util.ArrayList;
import java.util.List;

public class BlenderRecipe implements Recipe<RecipeWrapper> {

    private final NonNullList<Ingredient> inputItems;
    private final ItemStack output;
    private final ItemStack container;

    public static final String ID = "blender";

    public BlenderRecipe(NonNullList<Ingredient> ingredients, ItemStack output, ItemStack container) {
        this.inputItems = ingredients;
        this.output = output;
        if (!container.isEmpty()){
            this.container = container;
        }else {
            this.container = ItemStack.EMPTY;
        }

    }

    @Override
    public boolean matches(RecipeWrapper inv, Level level) {
        List<ItemStack> inputs = new ArrayList<>();
        int i = 0;

        for (int j = 0; j < 9; ++j) {
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

    public ItemStack getContainer() {
        return container.copy();
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return Serializer.INSTANCE;
    }

    @Override
    public RecipeType<?> getType() {
        return Type.INSTANCE;
    }


    public enum Type implements RecipeType<BlenderRecipe> {
        INSTANCE
    }

    public static class Serializer implements RecipeSerializer<BlenderRecipe> {
        public static final Serializer INSTANCE = new Serializer();
        private static final MapCodec<BlenderRecipe> CODEC = RecordCodecBuilder.mapCodec(inst -> inst.group(
                Ingredient.LIST_CODEC_NONEMPTY.fieldOf("ingredients").xmap(ingredients -> {
                    NonNullList<Ingredient> nonNullList = NonNullList.create();
                    nonNullList.addAll(ingredients);
                    return nonNullList;
                }, ingredients -> ingredients).forGetter(BlenderRecipe::getIngredients),
                ItemStack.STRICT_CODEC.fieldOf("output").forGetter(blenderRecipe -> blenderRecipe.output),
                ItemStack.STRICT_CODEC.optionalFieldOf("container", ItemStack.EMPTY).forGetter(BlenderRecipe::getContainer)
        ).apply(inst, BlenderRecipe::new));

        public static final StreamCodec<RegistryFriendlyByteBuf, BlenderRecipe> STREAM_CODEC =
                StreamCodec.of(Serializer::toNetwork, Serializer::fromNetwork);

        @Override
        public MapCodec<BlenderRecipe> codec() {
            return CODEC;
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, BlenderRecipe> streamCodec() {
            return STREAM_CODEC;
        }

        private static BlenderRecipe fromNetwork(RegistryFriendlyByteBuf buffer) {
            int i = buffer.readVarInt();
            NonNullList<Ingredient> inputItemsIn = NonNullList.withSize(i, Ingredient.EMPTY);
            inputItemsIn.replaceAll(ignored -> Ingredient.CONTENTS_STREAM_CODEC.decode(buffer));
            ItemStack outputIn = ItemStack.STREAM_CODEC.decode(buffer);
            ItemStack container = ItemStack.OPTIONAL_STREAM_CODEC.decode(buffer);
            return new BlenderRecipe(inputItemsIn, outputIn, container);
        }

        private static void toNetwork(RegistryFriendlyByteBuf buffer, BlenderRecipe recipe) {
            buffer.writeVarInt(recipe.inputItems.size());

            for (Ingredient ingredient : recipe.inputItems) {
                Ingredient.CONTENTS_STREAM_CODEC.encode(buffer, ingredient);
            }

            ItemStack.STREAM_CODEC.encode(buffer, recipe.output);
            ItemStack.OPTIONAL_STREAM_CODEC.encode(buffer, recipe.container);
        }
    }
}