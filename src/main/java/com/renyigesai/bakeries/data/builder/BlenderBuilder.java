package com.renyigesai.bakeries.data.builder;

import com.renyigesai.bakeries.common.recipe.BlenderRecipe;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRequirements;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger;
import net.minecraft.core.NonNullList;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

public class BlenderBuilder implements RecipeBuilder {
    private final ItemStack resultStack;
    private final NonNullList<Ingredient> recipeItems;
    private final ItemStack container;
    private final Map<String, Criterion<?>> criteria = new LinkedHashMap<>();

    public BlenderBuilder(ItemStack container, ItemStack result) {
        this.recipeItems = NonNullList.create();
        this.resultStack = result;
        this.container = container;
    }

    public BlenderBuilder(ItemStack resultStack){
        this(ItemStack.EMPTY,resultStack);
    }

    public static BlenderBuilder blender(ItemLike result,ItemLike container, int count) {
        return new BlenderBuilder(new ItemStack(container),new ItemStack(result,count));
    }

    public static BlenderBuilder blender(ItemLike result, int count) {
        return new BlenderBuilder(new ItemStack(result,count));
    }

    public BlenderBuilder addIngredient(TagKey<Item> tagIn) {
        return this.addIngredient(Ingredient.of(tagIn));
    }

    public BlenderBuilder addIngredient(ItemLike itemIn) {
        return this.addIngredient(itemIn, 1);
    }

    public BlenderBuilder addIngredient(ItemLike itemIn, int quantity) {
        for(int i = 0; i < quantity; ++i) {
            this.addIngredient(Ingredient.of(itemIn));
        }

        return this;
    }

    public BlenderBuilder addIngredient(Ingredient ingredientIn) {
        return this.addIngredient(ingredientIn, 1);
    }

    public BlenderBuilder addIngredient(Ingredient ingredientIn, int quantity) {
        for(int i = 0; i < quantity; ++i) {
            this.recipeItems.add(ingredientIn);
        }

        return this;
    }


    public void build(RecipeOutput outputIn, String save) {
        ResourceLocation resourcelocation = BuiltInRegistries.ITEM.getKey(this.resultStack.getItem());
        if (ResourceLocation.parse(save).equals(resourcelocation)) {
            throw new IllegalStateException("Blender Recipe " + save + " should remove its 'save' argument");
        } else {
            this.save(outputIn, ResourceLocation.parse(save));
        }
    }

    public void build(RecipeOutput output) {
        ResourceLocation location = BuiltInRegistries.ITEM.getKey(this.resultStack.getItem());
        this.save(output, ResourceLocation.fromNamespaceAndPath("bakeries", location.getPath()));
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
        return this.resultStack.getItem();
    }

    @Override
    public void save(RecipeOutput recipeOutput, ResourceLocation id) {
        ResourceLocation recipeId = id.withPrefix("blender/");
        Advancement.Builder advancementBuilder = recipeOutput.advancement().addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(id)).rewards(AdvancementRewards.Builder.recipe(id)).requirements(AdvancementRequirements.Strategy.OR);
        Objects.requireNonNull(advancementBuilder);
        this.criteria.forEach(advancementBuilder::addCriterion);
        BlenderRecipe recipe = new BlenderRecipe(this.recipeItems,this.resultStack,this.container);
        recipeOutput.accept(recipeId, recipe, advancementBuilder.build(id.withPrefix("recipes/blender/")));
    }
}
