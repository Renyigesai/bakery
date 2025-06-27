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
import net.minecraftforge.common.crafting.CraftingHelper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class FermentationRecipe implements Recipe<SimpleContainer> {
    private final NonNullList<Ingredient> inputItems;
    private final ItemStack output;
    private final ResourceLocation id;
    private final ItemStack container;

    public FermentationRecipe(NonNullList<Ingredient> ingredient, ItemStack output, ItemStack container/*, int time*/,ResourceLocation id) {
        this.inputItems = ingredient;
        this.output = output;
        this.id = id;
        if (container.isEmpty()){
            this.container = ItemStack.EMPTY;
        }else {
            this.container = container;
        }
    }

    @Override
    public boolean matches(SimpleContainer pContainer, Level pLevel) {
        if (pLevel.isClientSide) return false;

        // 转换为列表以便修改
        List<ItemStack> inputs = new ArrayList<>();
        for (int i = 0; i < pContainer.getContainerSize(); i++) {
            ItemStack item = pContainer.getItem(i);
            if (!item.isEmpty()) inputs.add(item);
        }

        // 检查原料数量是否匹配
        if (inputs.size() != this.inputItems.size()) return false;

        // 复制配方原料用于匹配消耗
        List<Ingredient> ingredientsToCheck = new ArrayList<>(this.inputItems);

        // 无序匹配逻辑
        outer:
        for (ItemStack input : inputs) {
            for (Ingredient ingredient : ingredientsToCheck) {
                if (ingredient.test(input)) {
                    ingredientsToCheck.remove(ingredient);
                    continue outer;
                }
            }
            return false;
        }
        return true;
    }

    @Override
    public ItemStack assemble(SimpleContainer pContainer, RegistryAccess pRegistryAccess) {
        return output.copy();
    }

    @Override
    public boolean canCraftInDimensions(int pWidth, int pHeight) {
        return true;
    }

    public ItemStack getContainer() {
        return container.copy();
    }

    @Override
    public ItemStack getResultItem(RegistryAccess pRegistryAccess) {
        return output.copy();
    }

    @Override
    public ResourceLocation getId() {
        return id;
    }

//    public int getTime() {
//        return time;
//    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return FermentationRecipe.Serializer.INSTANCE;
    }

    @Override
    public RecipeType<?> getType() {
        return FermentationRecipe.Type.INSTANCE;
    }

    public static class Type implements RecipeType<FermentationRecipe> {
        public static final FermentationRecipe.Type INSTANCE = new FermentationRecipe.Type();
        public static final String ID = "fermentation";
    }

    public static class Serializer implements RecipeSerializer<FermentationRecipe> {
        public static final FermentationRecipe.Serializer INSTANCE = new FermentationRecipe.Serializer();
        public static final ResourceLocation ID = new ResourceLocation(BakeriesMod.MODID, "fermentation");

        @Override
        public FermentationRecipe fromJson(ResourceLocation pRecipeId, JsonObject pSerializedRecipe) {
            ItemStack output = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(pSerializedRecipe, "output"));
            JsonArray ingredients = GsonHelper.getAsJsonArray(pSerializedRecipe, "ingredients");
            NonNullList<Ingredient> inputs = NonNullList.create();
            if (inputs.size() > 6){
                throw new JsonParseException("Too many ingredients for coffee recipe! The max is 4");
            }else {
                for (int i = 0; i < ingredients.size(); i++) {
                    inputs.add(Ingredient.fromJson(ingredients.get(i)));
                }
                ItemStack container = GsonHelper.isValidNode(pSerializedRecipe, "container") ? CraftingHelper.getItemStack(GsonHelper.getAsJsonObject(pSerializedRecipe, "container"), true) : ItemStack.EMPTY;
                return new FermentationRecipe(inputs,output,container/*,time*/,pRecipeId);
            }
        }

        @Override
        public @Nullable FermentationRecipe fromNetwork(ResourceLocation pRecipeId, FriendlyByteBuf pBuffer) {
            int ingredientCount = pBuffer.readInt();
            NonNullList<Ingredient> inputs = NonNullList.withSize(ingredientCount, Ingredient.EMPTY);

            for (int i = 0; i < ingredientCount; i++) {
                inputs.set(i, Ingredient.fromNetwork(pBuffer));
            }
            ItemStack container = pBuffer.readItem();
            ItemStack output = pBuffer.readItem();
            return new FermentationRecipe(inputs, output,container/*,time*/,pRecipeId);
        }

        @Override
        public void toNetwork(FriendlyByteBuf pBuffer, FermentationRecipe pRecipe) {
            pBuffer.writeInt(pRecipe.inputItems.size());

            for (Ingredient ingredient : pRecipe.getIngredients()) {
                ingredient.toNetwork(pBuffer);
            }
            pBuffer.writeItemStack(pRecipe.getResultItem(null), false);
            pBuffer.writeItem(pRecipe.container);
        }
    }

    @Override
    public @NotNull NonNullList<Ingredient> getIngredients() {
        return inputItems;
    }
}
