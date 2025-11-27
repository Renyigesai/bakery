package com.renyigesai.bakeries.data.builder;

import com.renyigesai.bakeries.common.recipe.DoughCraftingRecipe;
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
import org.jetbrains.annotations.Nullable;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

public class DoughCraftingBuilder implements RecipeBuilder {
    private final ItemStack resultStack;
    private final Item result;
    private final Ingredient recipeItems;
    private final Map<String, Criterion<?>> criteria = new LinkedHashMap<>();

    public DoughCraftingBuilder(ItemStack result, Ingredient recipeItems) {
        this.resultStack = result;
        this.result = result.getItem();
        this.recipeItems = recipeItems;
    }

    public static DoughCraftingBuilder doughCrafting(Ingredient recipeItems,ItemLike result, int count) {
        return new DoughCraftingBuilder(new ItemStack(result,count),recipeItems);
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
        ResourceLocation recipeId = id.withPrefix("dough_crafting_table/");
        Advancement.Builder advancementBuilder = recipeOutput.advancement()
                .addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(id))
                .rewards(AdvancementRewards.Builder.recipe(id))
                .requirements(AdvancementRequirements.Strategy.OR);
        Objects.requireNonNull(advancementBuilder);
        this.criteria.forEach(advancementBuilder::addCriterion);

        DoughCraftingRecipe recipe = new DoughCraftingRecipe("",this.recipeItems,this.resultStack);
        recipeOutput.accept(recipeId, recipe, advancementBuilder.build(id.withPrefix("recipes/dough_crafting_table/")));
    }
}
