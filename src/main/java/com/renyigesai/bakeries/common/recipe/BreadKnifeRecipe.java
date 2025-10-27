package com.renyigesai.bakeries.common.recipe;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.HolderLookup;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;

public class BreadKnifeRecipe implements Recipe<RecipeInput> {


    private final Ingredient inputItems;
    private final ItemStack output;

    public static final String ID = "bread_knife";

    public BreadKnifeRecipe(Ingredient ingredient, ItemStack output) {
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

    public enum Type implements RecipeType<BreadKnifeRecipe> {
        INSTANCE
    }

    public static class Serializer implements RecipeSerializer<BreadKnifeRecipe> {
        public static final Serializer INSTANCE = new Serializer(BreadKnifeRecipe::new);
        private static BreadKnifeRecipe.Serializer.Factory<BreadKnifeRecipe> factory;

        Serializer(BreadKnifeRecipe.Serializer.Factory<BreadKnifeRecipe> pFactory) {
            factory = pFactory;
        }

        public static final MapCodec<BreadKnifeRecipe> CODEC = RecordCodecBuilder.mapCodec(instance ->
                instance.group(
                        Ingredient.CODEC_NONEMPTY.fieldOf("ingredient").forGetter(recipe -> recipe.inputItems),
                        ItemStack.CODEC.fieldOf("output").forGetter(recipe -> recipe.output)
                ).apply(instance, BreadKnifeRecipe::new)
        );

        public static final StreamCodec<RegistryFriendlyByteBuf, BreadKnifeRecipe> STREAM_CODEC = StreamCodec.of(
                BreadKnifeRecipe.Serializer::toNetwork, BreadKnifeRecipe.Serializer::fromNetwork
        );

        @Override
        public MapCodec<BreadKnifeRecipe> codec() {
            return CODEC;
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, BreadKnifeRecipe> streamCodec() {
            return STREAM_CODEC;
        }

        private static BreadKnifeRecipe fromNetwork(RegistryFriendlyByteBuf buffer) {
            ItemStack result = ItemStack.STREAM_CODEC.decode(buffer);
            Ingredient ingredient = Ingredient.CONTENTS_STREAM_CODEC.decode(buffer);
            return factory.create(ingredient, result);
        }
        private static void toNetwork(RegistryFriendlyByteBuf buffer, BreadKnifeRecipe recipe) {
            ItemStack.STREAM_CODEC.encode(buffer, recipe.output);
            Ingredient.CONTENTS_STREAM_CODEC.encode(buffer, recipe.inputItems);
        }

        interface Factory<T extends BreadKnifeRecipe> {
            T create(Ingredient inputItems,ItemStack output);
        }
    }

}
