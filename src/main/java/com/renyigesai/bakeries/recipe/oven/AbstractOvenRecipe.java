package com.renyigesai.bakeries.recipe.oven;

import lombok.Getter;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public abstract class AbstractOvenRecipe implements Recipe<Container> {
    private  final RecipeType<?> type;
    private final RecipeSerializer<?> serializer;
    protected final ResourceLocation id;
    protected final ItemStack output;
    @Getter
    protected final int time;
    @Getter
    protected final int min_temperature;
    @Getter
    protected final int max_temperature;
    @Getter
    protected final int perfect_temperature;
    protected final Ingredient recipeItems;
    public AbstractOvenRecipe(RecipeType<?> recipeType, RecipeSerializer<?> serializer, ResourceLocation id, ItemStack output, int time, int min_temperature, int max_temperature, int perfectTemperature, Ingredient recipeItems) {
        this.type = recipeType;
        this.serializer = serializer;
        this.id = id;
        this.output = output;
        this.time = time;
        this.min_temperature = min_temperature;
        this.max_temperature = max_temperature;
        this.perfect_temperature = perfectTemperature;
        this.recipeItems = recipeItems;
    }
    @Override
    public boolean matches(@NotNull Container pInv, Level pLevel) {
        if(pLevel.isClientSide()){
            return false;
        }
        return this.recipeItems.test(pInv.getItem(0))
                || this.recipeItems.test(pInv.getItem(1))
                || this.recipeItems.test(pInv.getItem(2))
                || this.recipeItems.test(pInv.getItem(3));
    }
    @Override
    public @NotNull ItemStack assemble(@NotNull Container pContainer, @NotNull RegistryAccess pRegistryAccess) {
        return this.output.copy();
    }
    @Override
    public @NotNull NonNullList<Ingredient> getIngredients() {
        NonNullList<Ingredient> nonnulllist = NonNullList.create();
        nonnulllist.add(this.recipeItems);
        return nonnulllist;
    }
    @Override
    public boolean canCraftInDimensions(int pWidth, int pHeight) {
        return true;
    }

    @Override
    public @NotNull ItemStack getResultItem(@NotNull RegistryAccess access) {
        return output.copy();
    }

    @Override
    public @NotNull ResourceLocation getId() {
        return id;
    }

    @Override
    public @NotNull RecipeType<?> getType() {
        return type;
    }

    @Override
    public @NotNull RecipeSerializer<?> getSerializer() {
        return serializer;
    }
}
