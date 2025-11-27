package com.renyigesai.bakeries.data.builder;

import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRequirements;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import com.renyigesai.bakeries.common.recipe.oven.OvenRecipe;
import org.jetbrains.annotations.Nullable;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

public class OvenRecipeBuilder implements RecipeBuilder {
    private final ItemStack resultStack;
    private final Item result;
    private final int time;
    private final int minTemperature;
    private final int maxTemperature;
    private final int perfectTemperature;
    private final Ingredient recipeItems;
    private final Map<String, Criterion<?>> criteria = new LinkedHashMap<>();

    public OvenRecipeBuilder(ItemStack result, int time, int minTemperature, int maxTemperature, int perfectTemperature, Ingredient recipeItems) {
        this.resultStack = result;
        this.result = result.getItem();
        this.time = time;
        this.minTemperature = minTemperature;
        this.maxTemperature = maxTemperature;
        this.perfectTemperature = perfectTemperature;
        this.recipeItems = recipeItems;
    }

    public static OvenRecipeBuilder oven(ItemLike result, int count, int time, int minTemperature, int maxTemperature, int perfectTemperature, Ingredient recipeItems) {
        return new OvenRecipeBuilder(new ItemStack(result, count), time, minTemperature, maxTemperature, perfectTemperature, recipeItems);
    }

    @Override
    public RecipeBuilder unlockedBy(String name, Criterion<?> criterion) {
        this.criteria.put(name, criterion);
        return this;
    }

    @Override
    public RecipeBuilder group(@Nullable String groupName) {
        return this;
    }

    @Override
    public Item getResult() {
        return this.result;
    }

    @Override
    public void save(RecipeOutput recipeOutput, ResourceLocation id) {
        ResourceLocation recipeId = id.withPrefix("oven/");
        Advancement.Builder advancementBuilder = recipeOutput.advancement()
                .addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(id))
                .rewards(AdvancementRewards.Builder.recipe(id))
                .requirements(AdvancementRequirements.Strategy.OR);
        Objects.requireNonNull(advancementBuilder);
        this.criteria.forEach(advancementBuilder::addCriterion);

        OvenRecipe recipe = new OvenRecipe(this.resultStack, this.time, this.minTemperature, this.maxTemperature, this.perfectTemperature, this.recipeItems);
        recipeOutput.accept(recipeId, recipe, advancementBuilder.build(id.withPrefix("recipes/oven/")));
    }
}
