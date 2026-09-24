package com.renyigesai.bakeries.recipe;

import com.google.gson.JsonObject;
import com.renyigesai.bakeries.init.BakeriesItems;
import com.renyigesai.bakeries.init.BakeriesRecipeSerializers;
import com.renyigesai.bakeries.item.MooncakeItem;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class MooncakeRecipe implements CraftingRecipe {

    private final ResourceLocation id;

    public MooncakeRecipe(ResourceLocation id) {
        this.id = id;
    }

    @Override
    public CraftingBookCategory category() {
        return CraftingBookCategory.MISC;
    }

    @Override
    public boolean matches(CraftingContainer craftingContainer, Level level) {
        int mould = 0;
        int flour = 0;
        int sugar = 0;
        int food = 0;
        for (int slot = 0; slot < craftingContainer.getContainerSize(); slot++) {
            ItemStack input = craftingContainer.getItem(slot);
            if (input.is(BakeriesItems.ROUND_MOULD.get())){
                mould ++;
            } else if (input.is(TagKey.create(Registries.ITEM,new ResourceLocation("forge","flour")))) {
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
    public ItemStack assemble(CraftingContainer container, RegistryAccess registryAccess) {
        List<Item> inputs = new ArrayList<>();
        Ingredient flourTag = Ingredient.of(
                TagKey.create(Registries.ITEM, new ResourceLocation("forge", "flour")));

        for (int slot = 0; slot < container.getContainerSize(); slot++) {
            ItemStack input = container.getItem(slot);
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
            CompoundTag tag = output.getOrCreateTag();
            StringBuilder buffer = new StringBuilder();
            inputs.forEach(item -> {
                String id = BuiltInRegistries.ITEM.getKey(item).toString();
                buffer.append("&").append(id);
            });
            tag.putString("ItemId", buffer.toString());
        }
        return output;
    }

    @Override
    public boolean canCraftInDimensions(int x, int y) {
        return x * y >= 2;
    }

    @Override
    public ItemStack getResultItem(RegistryAccess registryAccess) {
        return new ItemStack(BakeriesItems.RAW_MOONCAKE.get());
    }

    @Override
    public @NotNull NonNullList<Ingredient> getIngredients(){
        NonNullList<Ingredient> ingredients = NonNullList.create();
        ingredients.add(Ingredient.of(BakeriesItems.ROUND_MOULD.get()));
        ingredients.add(Ingredient.of(TagKey.create(Registries.ITEM, new ResourceLocation("forge", "flour"))));
        ingredients.add(Ingredient.of(Items.HONEY_BOTTLE));
        return ingredients;
    }

    @Override
    public ResourceLocation getId() {
        return this.id;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return BakeriesRecipeSerializers.MOONCAKE.get();
    }


    public static class Serializer implements RecipeSerializer<MooncakeRecipe> {
        @Override
        public MooncakeRecipe fromJson(ResourceLocation recipeId, JsonObject json) {
            return new MooncakeRecipe(recipeId);
        }

        @Override
        public MooncakeRecipe fromNetwork(ResourceLocation recipeId, FriendlyByteBuf buffer) {
            return new MooncakeRecipe(recipeId);
        }

        @Override
        public void toNetwork(FriendlyByteBuf buffer, MooncakeRecipe recipe) {
        }
    }
}
