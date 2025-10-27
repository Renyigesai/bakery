package com.renyigesai.bakeries.common.recipe;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.HolderLookup;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;

public class FlourSieveRecipe implements Recipe<RecipeInput> {


    private final Ingredient inputItems;
    private final ItemStack output;

    public static final String ID = "flour_sieve";

    public FlourSieveRecipe(Ingredient ingredient, ItemStack output) {
        this.inputItems = ingredient;
        this.output = output;
    }

    @Override
    public boolean matches(RecipeInput recipeInput, Level level) {
        return this.inputItems.test(recipeInput.getItem(0));
    }

    @Override
    public ItemStack assemble(RecipeInput recipeInput, HolderLookup.Provider provider) {
        return output.copy();
    }

    @Override
    public boolean canCraftInDimensions(int i, int i1) {
        return true;
    }

    @Override
    public ItemStack getResultItem(HolderLookup.Provider provider) {
        return output.copy();
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return Serializer.INSTANCE;
    }

    @Override
    public RecipeType<?> getType() {
        return Type.INSTANCE;
    }

    public enum Type implements RecipeType<FlourSieveRecipe> {
        INSTANCE
    }

    public static class Serializer implements RecipeSerializer<FlourSieveRecipe> {
        public static final Serializer INSTANCE = new Serializer(FlourSieveRecipe::new);
        private static FlourSieveRecipe.Serializer.Factory<FlourSieveRecipe> factory;

        Serializer(FlourSieveRecipe.Serializer.Factory<FlourSieveRecipe> pFactory) {
            factory = pFactory;
        }

        public static final MapCodec<FlourSieveRecipe> CODEC = RecordCodecBuilder.mapCodec(instance ->
                instance.group(
                        Ingredient.CODEC_NONEMPTY.fieldOf("ingredient").forGetter(recipe -> recipe.inputItems),
                        ItemStack.CODEC.fieldOf("output").forGetter(recipe -> recipe.output)
                ).apply(instance, FlourSieveRecipe::new)
        );

        public static final StreamCodec<RegistryFriendlyByteBuf, FlourSieveRecipe> STREAM_CODEC = StreamCodec.of(
                FlourSieveRecipe.Serializer::toNetwork, FlourSieveRecipe.Serializer::fromNetwork
        );

        @Override
        public MapCodec<FlourSieveRecipe> codec() {
            return CODEC;
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, FlourSieveRecipe> streamCodec() {
            return STREAM_CODEC;
        }

        private static FlourSieveRecipe fromNetwork(RegistryFriendlyByteBuf buffer) {
            ItemStack result = ItemStack.STREAM_CODEC.decode(buffer);
            Ingredient ingredient = Ingredient.CONTENTS_STREAM_CODEC.decode(buffer);
            return factory.create(ingredient, result);
        }
        private static void toNetwork(RegistryFriendlyByteBuf buffer, FlourSieveRecipe recipe) {
            ItemStack.STREAM_CODEC.encode(buffer, recipe.output);
            Ingredient.CONTENTS_STREAM_CODEC.encode(buffer, recipe.inputItems);
        }

        interface Factory<T extends FlourSieveRecipe> {
            T create(Ingredient inputItems,ItemStack output);
        }
    }

}
