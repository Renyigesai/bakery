package com.renyigesai.bakeries.common.recipe;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;

public abstract class MultiOutputSingleItemRecipe implements Recipe<SingleRecipeInput> {
    protected final Ingredient ingredient;
    protected final NonNullList<ItemStack> results;  // 改为多个结果
    private final RecipeType<?> type;
    private final RecipeSerializer<?> serializer;
    protected final String group;

    public MultiOutputSingleItemRecipe(RecipeType<?> type, RecipeSerializer<?> serializer,
                                       String group, Ingredient ingredient,
                                       NonNullList<ItemStack> results) {
        this.type = type;
        this.serializer = serializer;
        this.group = group;
        this.ingredient = ingredient;
        this.results = results;
    }

    public RecipeType<?> getType() {
        return this.type;
    }

    public RecipeSerializer<?> getSerializer() {
        return this.serializer;
    }

    public String getGroup() {
        return this.group;
    }

    // 返回第一个结果以保持兼容性
    public ItemStack getResultItem(HolderLookup.Provider registries) {
        return this.results.isEmpty() ? ItemStack.EMPTY : this.results.getFirst();
    }

    // 新增：获取所有结果的方法
    public NonNullList<ItemStack> getAllResults() {
        return this.results;
    }

    public NonNullList<Ingredient> getIngredients() {
        NonNullList<Ingredient> nonnulllist = NonNullList.create();
        nonnulllist.add(this.ingredient);
        return nonnulllist;
    }

    public boolean canCraftInDimensions(int width, int height) {
        return true;
    }

    // 返回第一个结果的副本
    public ItemStack assemble(SingleRecipeInput input, HolderLookup.Provider registries) {
        return this.results.isEmpty() ? ItemStack.EMPTY : this.results.getFirst().copy();
    }

    // 新增：返回所有结果的方法
    public NonNullList<ItemStack> assembleAll(SingleRecipeInput input, HolderLookup.Provider registries) {
        NonNullList<ItemStack> resultCopies = NonNullList.create();
        for (ItemStack stack : this.results) {
            resultCopies.add(stack.copy());
        }
        return resultCopies;
    }

    // 序列化器类
    public static class Serializer<T extends MultiOutputSingleItemRecipe> implements RecipeSerializer<T> {
        private final Factory<T> factory;
        private final MapCodec<T> codec;
        private final StreamCodec<RegistryFriendlyByteBuf, T> streamCodec;

        public Serializer(Factory<T> factory) {
            this.factory = factory;

            // 创建 Codec
            this.codec = RecordCodecBuilder.mapCodec((instance) -> instance.group(
                    Codec.STRING.optionalFieldOf("group", "").forGetter(recipe -> recipe.group),
                    Ingredient.CODEC_NONEMPTY.fieldOf("ingredient").forGetter(recipe -> recipe.ingredient),
                    // 处理结果列表
                    ItemStack.STRICT_CODEC.listOf()
                            .fieldOf("results")
                            .xmap(
                                    list -> {
                                        NonNullList<ItemStack> nonNullList = NonNullList.create();
                                        nonNullList.addAll(list);
                                        return nonNullList;
                                    },
                                    NonNullList::copyOf
                            )
                            .forGetter(recipe -> recipe.results)
            ).apply(instance, factory::create));

            // 创建 StreamCodec
            this.streamCodec = StreamCodec.composite(
                    ByteBufCodecs.STRING_UTF8,
                    recipe -> recipe.group,
                    Ingredient.CONTENTS_STREAM_CODEC,
                    recipe -> recipe.ingredient,
                    ByteBufCodecs.collection(NonNullList::createWithCapacity, ItemStack.STREAM_CODEC),
                    recipe -> recipe.results,
                    factory::create
            );
        }

        public MapCodec<T> codec() {
            return this.codec;
        }

        public StreamCodec<RegistryFriendlyByteBuf, T> streamCodec() {
            return this.streamCodec;
        }
    }

    public interface Factory<T extends MultiOutputSingleItemRecipe> {
        T create(String group, Ingredient ingredient, NonNullList<ItemStack> results);
    }
}