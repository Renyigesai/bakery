package com.renyigesai.bakery.data.provider;


import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.renyigesai.bakery.BakeryMod;
import com.renyigesai.bakery.recipe.oven.OvenRecipe;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.ItemLike;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

public class BRecipeProvider extends RecipeProvider {

    public BRecipeProvider(PackOutput output) {
        super(output);
    }

    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> consumer) {


//        planksFromLogs(consumer, UMCModBlocks.IRON_BIRCH_PLANKS.get(), ModItemTag.IRON_BIRCH_LOGS, 4);
//        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, UMCModItems.FROST_UP.get(), 1)
//                .define('#', Items.NETHERITE_UPGRADE_SMITHING_TEMPLATE)
//                .define('&', UMCModBlocks.BLUE_ICE_2.get())
//                .pattern("   ")
//                .pattern(" # ")
//                .pattern(" & ")
//                .unlockedBy("has_"+Items.NETHER_STAR.asItem().getDescriptionId(),has(Items.NETHER_STAR))
//                .save(consumer,new ResourceLocation(UMCMod.MODID,getItemName(UMCModItems.FROST_UP.get()) + "_craft"));
    }
    protected static void anvil(Consumer<FinishedRecipe> pFinishedRecipeConsumer , ItemLike pItem1 , ItemLike pItem2, ItemLike pResultItem){
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, pResultItem, 1)
                .define('A', pItem1)
                .define('B', pItem2)
                .pattern("AAA")
                .pattern(" B ")
                .pattern("BBB")
                .unlockedBy("has_"+pItem2.asItem().getDescriptionId(),has(pItem2))
                .save(pFinishedRecipeConsumer,new ResourceLocation(BakeryMod.MODID,getItemName(pResultItem) + "_anvil"));
    }
    protected static void upgradation(Consumer<FinishedRecipe> pFinishedRecipeConsumer , ItemLike pStar , ItemLike pDunKuo, ItemLike pDunKuo1, ItemLike pResultItem){
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, pResultItem, 1)
                .define('A', pDunKuo1)
                .define('B', pDunKuo)
                .define('C', pStar)
                .pattern("ACA")
                .pattern("BBB")
                .pattern("   ")
                .unlockedBy("has_"+pStar.asItem().getDescriptionId(),has(pStar))
                .save(pFinishedRecipeConsumer,new ResourceLocation(BakeryMod.MODID,getItemName(pResultItem) + "_upgradation"));
    }
     protected static void compress(Consumer<FinishedRecipe> pFinishedRecipeConsumer , ItemLike pIngredient , ItemLike pResultItem){
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, pResultItem, 1)
                .define('#', pIngredient)
                .pattern("###")
                .pattern("###")
                .pattern("###")
                .unlockedBy(getHasName(pIngredient),has(pIngredient))
                .save(pFinishedRecipeConsumer,new ResourceLocation(BakeryMod.MODID,getItemName(pResultItem) + "_0"));
    }
    protected static void compress1(Consumer<FinishedRecipe> pFinishedRecipeConsumer , ItemLike pIngredient , ItemLike pResultItem){
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, pResultItem, 1)
                .define('#', pIngredient)
                .pattern("###")
                .pattern("###")
                .pattern("###")
                .unlockedBy(getHasName(pIngredient),has(pIngredient))
                .save(pFinishedRecipeConsumer,new ResourceLocation(BakeryMod.MODID,getItemName(pResultItem) + "_1"));
    }
    protected static void decompression(Consumer<FinishedRecipe> pFinishedRecipeConsumer , ItemLike pIngredient , ItemLike pResultItem){
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, pResultItem, 9)
                .define('#', pIngredient)
                .pattern("   ")
                .pattern(" # ")
                .pattern("   ")
                .unlockedBy(getHasName(pIngredient),has(pIngredient))
                .save(pFinishedRecipeConsumer,new ResourceLocation(BakeryMod.MODID,getItemName(pResultItem) + "_0"));
    }
    protected static void umcSmithing(Consumer<FinishedRecipe> pFinishedRecipeConsumer, ItemLike pPgradeSmithingTemplate, ItemLike pzhuang_bei, ItemLike pIngotItems, ItemLike pResultItem) {
        SmithingTransformRecipeBuilder.smithing(
                        Ingredient.of(pPgradeSmithingTemplate),
                        Ingredient.of(pzhuang_bei),
                        Ingredient.of(pIngotItems),
                        RecipeCategory.MISC, pResultItem.asItem())
                .unlocks(getHasName(pIngotItems), has(pIngotItems))
                .save(pFinishedRecipeConsumer, new ResourceLocation(BakeryMod.MODID,getItemName(pResultItem) + "_smithing"));
    }
    protected static void umcSmithing1(Consumer<FinishedRecipe> pFinishedRecipeConsumer, ItemLike pPgradeSmithingTemplate, ItemLike pzhuang_bei, ItemLike pIngotItems, ItemLike pResultItem) {
        SmithingTransformRecipeBuilder.smithing(
                        Ingredient.of(pPgradeSmithingTemplate),
                        Ingredient.of(pzhuang_bei),
                        Ingredient.of(pIngotItems),
                        RecipeCategory.MISC, pResultItem.asItem())
                .unlocks(getHasName(pIngotItems), has(pIngotItems))
                .save(pFinishedRecipeConsumer, new ResourceLocation(BakeryMod.MODID,getItemName(pResultItem) + "_smithing_1"));
    }
    protected static void umcSimpleCooking(Consumer<FinishedRecipe> pFinishedRecipeConsumer, ItemLike pIngredient,float pExp,int pCookingTime, ItemLike pResultItem) {
        SimpleCookingRecipeBuilder.smelting(
                Ingredient.of(pIngredient),
                RecipeCategory.BUILDING_BLOCKS,
                        pResultItem, pExp, pCookingTime)
                .unlockedBy(getHasName(pIngredient), has(pIngredient))
                .save(pFinishedRecipeConsumer,new ResourceLocation(BakeryMod.MODID,getItemName(pResultItem) + "_simple_cooking"));
    }

    private void addOvenRecipe(Consumer<FinishedRecipe> consumer, String name, ItemLike ingredient, int count, int time, int minTemperature, int maxTemperature, ItemLike result) {
        FinishedRecipe finishedRecipe = new FinishedRecipe() {
            @Override
            public void serializeRecipeData(JsonObject json) {
                json.add("ingredient", Ingredient.of(ingredient).toJson());
                json.addProperty("count", count);
                json.addProperty("time", time);
                json.addProperty("min_temperature", minTemperature);
                json.addProperty("max_temperature", maxTemperature);
                // 将 CompoundTag 转换为 JsonElement
                CompoundTag resultTag = new ItemStack(result, count).serializeNBT();
                json.add("result", JsonParser.parseString(resultTag.toString()));
            }

            @Override
            public RecipeSerializer<?> getType() {
                return OvenRecipe.Serializer.INSTANCE;
            }

            @Nullable
            @Override
            public JsonObject serializeAdvancement() {
                return null;
            }

            @Nullable
            @Override
            public ResourceLocation getAdvancementId() {
                return null;
            }

            @Override
            public ResourceLocation getId() {
                return BakeryMod.prefix(name);
            }
        };

        consumer.accept(finishedRecipe);
    }
}