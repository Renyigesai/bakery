package net.weibai.bakeries.common.recipe.oven;

import lombok.Getter;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public abstract class AbstractOvenRecipe implements Recipe<OvenRecipeInput> {
    private  final RecipeType<?> type;
    private final RecipeSerializer<?> serializer;
    protected final ItemStack output;
    @Getter
    protected final int time;
    @Getter
    protected final int minTemperature;
    @Getter
    protected final int maxTemperature;
    @Getter
    protected final int perfectTemperature;
    protected final Ingredient recipeItems;
    protected AbstractOvenRecipe(RecipeType<?> recipeType, RecipeSerializer<?> serializer, ItemStack output, int time, int minTemperature, int maxTemperature, int perfectTemperature, Ingredient recipeItems) {
        this.type = recipeType;
        this.serializer = serializer;
        this.output = output;
        this.time = time;
        this.minTemperature = minTemperature;
        this.maxTemperature = maxTemperature;
        this.perfectTemperature = perfectTemperature;
        this.recipeItems = recipeItems;
    }

    @Override
    public boolean matches(@NotNull OvenRecipeInput ovenRecipeInput, Level level) {
        if(level.isClientSide()){
            return false;
        }
        return this.recipeItems.test(ovenRecipeInput.getItem(0));
    }

    @Override
    public @NotNull ItemStack assemble(@NotNull OvenRecipeInput ovenRecipeInput, HolderLookup.@NotNull Provider provider) {
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
    public @NotNull ItemStack getResultItem(HolderLookup.@NotNull Provider provider) {
        return output.copy();
    }
    public boolean isPresentPerfect(){
        return perfectTemperature != -1;
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
