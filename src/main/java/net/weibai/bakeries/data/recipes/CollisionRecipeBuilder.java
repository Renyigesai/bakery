//package net.weibai.bakeries.data.recipes;
//
//import net.minecraft.advancements.Advancement;
//import net.minecraft.advancements.AdvancementRequirements;
//import net.minecraft.advancements.AdvancementRewards;
//import net.minecraft.advancements.Criterion;
//import net.minecraft.advancements.critereon.RecipeUnlockedTrigger;
//import net.minecraft.data.recipes.RecipeBuilder;
//import net.minecraft.data.recipes.RecipeOutput;
//import net.minecraft.resources.ResourceLocation;
//import net.minecraft.world.item.Item;
//import net.minecraft.world.item.ItemStack;
//import net.minecraft.world.item.crafting.Ingredient;
//import net.minecraft.world.level.ItemLike;
//import net.weibai.mechanical_soar.common.recipe.collision.CollisionRecipe;
//import org.jetbrains.annotations.Nullable;
//
//import java.util.LinkedHashMap;
//import java.util.Map;
//import java.util.Objects;
//
//public class CollisionRecipeBuilder implements RecipeBuilder {//CollisionRecipe
//    private final ItemStack handItemStack;
//    private final Item handItem;
//    private final int handItemCount;
//    private final ItemStack blockItemStack;
//    private final Item blockItem;
//    private final int blockItemCount;
//    private final ItemStack resultStack;
//    private final Item result;
//    private final int resultCount;
//    private final float probability;
//    private final Map<String, Criterion<?>> criteria;
//
//    public CollisionRecipeBuilder(float probability, ItemLike handItem, int handItemCount, ItemLike blockItem, int blockItemCount, ItemLike result, int resultCount) {
//        this(probability, Ingredient.of(new ItemStack(handItem, handItemCount)), Ingredient.of(new ItemStack(blockItem, blockItemCount)), new ItemStack(result, resultCount));
//    }
//    public CollisionRecipeBuilder(float probability, Ingredient iHandItemStack, Ingredient iBlockItemStack, ItemStack result) {
//        this.criteria = new LinkedHashMap();
//        this.handItem = iHandItemStack.getItems()[0].getItem();
//        this.handItemCount = iHandItemStack.getItems()[0].getCount();
//        this.handItemStack = iHandItemStack.getItems()[0];
//        this.blockItem = iBlockItemStack.getItems()[0].getItem();
//        this.blockItemCount = iBlockItemStack.getItems()[0].getCount();
//        this.blockItemStack = iBlockItemStack.getItems()[0];
//        this.result = result.getItem();
//        this.resultCount = result.getCount();
//        this.resultStack = result;
//        this.probability = probability;
//    }
//    public static CollisionRecipeBuilder col(float probability, Ingredient handItemStack, Ingredient blockItemStack, ItemStack result) {
//        return new  CollisionRecipeBuilder(probability, handItemStack, blockItemStack, result);
//    }
//    public static CollisionRecipeBuilder col(float probability, ItemLike handItem, int handItemCount, ItemLike blockItem, int blockItemCount, ItemLike result, int resultCount) {
//        return new  CollisionRecipeBuilder(probability, handItem, handItemCount, blockItem, blockItemCount, result, resultCount);
//    }
//    @Override
//    public RecipeBuilder unlockedBy(String name, Criterion<?> criterion) {
//        this.criteria.put(name, criterion);
//        return this;
//    }
//
//    @Override
//    public RecipeBuilder group(@Nullable String groupName) {
//        return this;
//    }
//
//    @Override
//    public Item getResult() {
//        return this.result;
//    }
//
//    @Override
//    public void save(RecipeOutput recipeOutput, ResourceLocation id) {
//        Advancement.Builder advancement$builder = recipeOutput.advancement()
//                .addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(id))
//                .rewards(AdvancementRewards.Builder.recipe(id))
//                .requirements(AdvancementRequirements.Strategy.OR);
//        Objects.requireNonNull(advancement$builder);
//        this.criteria.forEach(advancement$builder::addCriterion);
//        CollisionRecipe recipe = new CollisionRecipe(Ingredient.of(this.handItem), Ingredient.of(this.blockItem), this.resultStack, this.probability);
//        recipeOutput.accept(id, recipe, advancement$builder.build(id.withPrefix("recipes/collision/")));
//
//    }
//}
