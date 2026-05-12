package com.renyigesai.bakeries.data.builder;

import com.renyigesai.bakeries.common.recipe.BreadKnifeRecipe;
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
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import org.jetbrains.annotations.Nullable;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

public class BreadKnifeBuilder implements RecipeBuilder {
    private final NonNullList<ItemStack>  resultStacks;
    private final Item result;
    private final Ingredient recipeItems;
    private final Map<String, Criterion<?>> criteria = new LinkedHashMap<>();

    public BreadKnifeBuilder(NonNullList<ItemStack> result, Ingredient recipeItems) {
        this.resultStacks = result;
        this.result = result.getFirst().getItem();
        this.recipeItems = recipeItems;
    }

    public static BreadKnifeBuilder breadKnife(Ingredient recipeItems, NonNullList<ItemStack> resultStacks) {
        return new BreadKnifeBuilder(resultStacks,recipeItems);
    }

    public static BreadKnifeBuilder breadKnife(Ingredient recipeItems, ItemStack resultStacks) {
        NonNullList<ItemStack> stacks = NonNullList.create();
        stacks.add(resultStacks);
        return new BreadKnifeBuilder(stacks,recipeItems);
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

    public void build(RecipeOutput output) {
        ResourceLocation location = BuiltInRegistries.ITEM.getKey(this.getResult());
        this.save(output, ResourceLocation.fromNamespaceAndPath("bakeries", location.getPath()));
    }

    @Override
    public void save(RecipeOutput recipeOutput, ResourceLocation id) {
        ResourceLocation recipeId = id.withPrefix("bread_knife/");
        Advancement.Builder advancementBuilder = recipeOutput.advancement()
                .addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(id))
                .rewards(AdvancementRewards.Builder.recipe(id))
                .requirements(AdvancementRequirements.Strategy.OR);
        Objects.requireNonNull(advancementBuilder);
        this.criteria.forEach(advancementBuilder::addCriterion);

        BreadKnifeRecipe recipe = new BreadKnifeRecipe("",this.recipeItems,this.resultStacks);
        recipeOutput.accept(recipeId, recipe, advancementBuilder.build(id.withPrefix("recipes/bread_knife/")));
    }
}
