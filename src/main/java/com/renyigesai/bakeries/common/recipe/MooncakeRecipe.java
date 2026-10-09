package com.renyigesai.bakeries.common.recipe;

import com.mojang.serialization.MapCodec;
import com.renyigesai.bakeries.common.init.BakeriesDataComponents;
import com.renyigesai.bakeries.common.init.BakeriesItems;
import com.renyigesai.bakeries.common.init.BakeriesRecipeTypes;
import com.renyigesai.bakeries.common.items.MooncakeItem;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class MooncakeRecipe implements CraftingRecipe {


    public MooncakeRecipe() {

    }

    @Override
    public CraftingBookCategory category() {
        return CraftingBookCategory.MISC;
    }

    @Override
    public boolean matches(CraftingInput craftingInput, Level level) {
        int mould = 0;
        int flour = 0;
        int sugar = 0;
        int food = 0;
        for (int slot = 0; slot < craftingInput.size(); slot++) {
            ItemStack input = craftingInput.getItem(slot);
            if (input.is(BakeriesItems.ROUND_MOULD.get())){
                mould ++;
            } else if (input.is(TagKey.create(Registries.ITEM,ResourceLocation.fromNamespaceAndPath("c","flours")))) {
                flour ++;
            } else if (input.is(Items.HONEY_BOTTLE)) {
                sugar ++;
            }else {
                if (input.getFoodProperties(null) != null && !input.is(BakeriesItems.MOONCAKE.get())){
                    if (input.getCount() == 1){
                        food ++;
                    }else {
                        return false;
                    }
                }
            }
        }
        return mould == 1 && flour == 1 && sugar == 1 && food <= 6;
    }

    @Override
    public ItemStack assemble(CraftingInput craftingInput, HolderLookup.Provider provider) {
        List<Item> inputs = new ArrayList<>();
        Ingredient flourTag = Ingredient.of(
                TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("c", "flours")));

        for (int slot = 0; slot < craftingInput.size(); slot++) {
            ItemStack input = craftingInput.getItem(slot);
            if (input.isEmpty()) {
                continue;
            }

            /**跳过固定原料*/
            if (input.is(BakeriesItems.ROUND_MOULD.get())){
                continue;
            }
            if (flourTag.test(input)){
                continue;
            }
            if (input.is(Items.HONEY_BOTTLE)){
                continue;
            }
            /**跳过月饼*/
            if (input.is(BakeriesItems.MOONCAKE.get())){
                continue;
            }
            if (input.getFoodProperties(null) != null) {
                inputs.add(input.getItem());
            }
        }
        ItemStack output = new ItemStack(BakeriesItems.RAW_MOONCAKE.get(), 1);
        if (output.getItem() instanceof MooncakeItem) {
            StringBuilder buffer = new StringBuilder();
            inputs.forEach(item -> {
                String id = BuiltInRegistries.ITEM.getKey(item).toString();
                buffer.append("&").append(id);
            });
            output.set(BakeriesDataComponents.CUSTOM_CAKE_PART_ID.get(),buffer.toString());
        }
        return output;
    }

    @Override
    public boolean canCraftInDimensions(int x, int y) {
        return x * y >= 2;
    }

    @Override
    public ItemStack getResultItem(HolderLookup.Provider provider) {
        return new ItemStack(BakeriesItems.RAW_MOONCAKE.get());
    }

    @Override
    public @NotNull NonNullList<Ingredient> getIngredients(){
        NonNullList<Ingredient> ingredients = NonNullList.create();
        ingredients.add(Ingredient.of(BakeriesItems.ROUND_MOULD.get()));
        ingredients.add(Ingredient.of(TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("c", "flours"))));
        ingredients.add(Ingredient.of(Items.HONEY_BOTTLE));
        return ingredients;
    }


    @Override
    public RecipeSerializer<?> getSerializer() {
        return BakeriesRecipeTypes.MOONCAKE_SERIALIZERS.get();
    }


    public static class Serializer implements RecipeSerializer<MooncakeRecipe> {
        public static final MapCodec<MooncakeRecipe> CODEC =
                MapCodec.unit(MooncakeRecipe::new);

        public static final StreamCodec<RegistryFriendlyByteBuf, MooncakeRecipe> STREAM_CODEC =
                StreamCodec.of(
                        (buf, recipe) -> {

                        },
                        buf -> new MooncakeRecipe()
                );

        @Override
        public MapCodec<MooncakeRecipe> codec() {
            return CODEC;
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, MooncakeRecipe> streamCodec() {
            return STREAM_CODEC;
        }
    }
}
