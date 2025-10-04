package net.weibai.bakeries.data;


import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.weibai.mechanical_soar.common.MechanicalSoarMod;
import net.weibai.mechanical_soar.common.init.MSBlocks;
import net.weibai.mechanical_soar.common.init.MSItems;
import net.weibai.mechanical_soar.common.init.MSTags;
import net.weibai.mechanical_soar.data.recipes.CollisionRecipeBuilder;

import java.util.concurrent.CompletableFuture;


public class MSRecipeProvider extends RecipeProvider {

    public MSRecipeProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries);
    }

    @Override
    protected void buildRecipes(RecipeOutput consumer) {
        minecraft(consumer);
        mod(consumer);
    }
    public void mod(RecipeOutput recipeOutput){
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, MSItems.PLANT_FIBERS.get(), 1)
                .define('#', MSItems.WEED)
                .pattern("##")
                .pattern("##")
                .unlockedBy("has_weed", has(MSItems.WEED))
                .save(recipeOutput, name(MSItems.PLANT_FIBERS.get()));
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, MSItems.PLANT_STRING.get(), 1)
                .define('#', MSItems.PLANT_FIBERS)
                .pattern("##")
                .pattern("##")
                .unlockedBy("has_plant_fibers", has(MSItems.PLANT_FIBERS))
                .save(recipeOutput, name(MSItems.PLANT_STRING.get()));
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, MSItems.WOODEN_BRACKET.get(), 1)
                .define('#', MSItems.PLANT_STRING)
                .define('Z', Items.STICK)
                .pattern("ZZ")
                .pattern("Z#")
                .unlockedBy("has_plant_string", has(MSItems.PLANT_STRING))
                .save(recipeOutput, name(MSItems.WOODEN_BRACKET.get()));
        CollisionRecipeBuilder.col(0.2f,
                        Ingredient.of(new ItemStack(MSItems.TINY_STONE.get())),
                        Ingredient.of(MSBlocks.TINY_STONE_BLOCK),
                        new ItemStack(MSItems.POINTED_TINY_STONE.get()))
                .save(recipeOutput, name(MSItems.POINTED_TINY_STONE.get()));
        CollisionRecipeBuilder.col(0.4f,
                        Ingredient.of(new ItemStack(MSItems.TINY_STONE.get())),
                        Ingredient.of(MSBlocks.TINY_ANDESITE_BLOCK),
                        new ItemStack(MSItems.POINTED_TINY_STONE.get()))
                .save(recipeOutput, name(MSItems.POINTED_TINY_STONE.get(),"1"));
        CollisionRecipeBuilder.col(0.6f,
                        Ingredient.of(new ItemStack(MSItems.TINY_STONE.get())),
                        Ingredient.of(MSBlocks.TINY_GRANITE_BLOCK),
                        new ItemStack(MSItems.POINTED_TINY_STONE.get()))
                .save(recipeOutput, name(MSItems.POINTED_TINY_STONE.get(),"2"));
        CollisionRecipeBuilder.col(0.6f,
                        Ingredient.of(new ItemStack(MSItems.TINY_STONE.get())),
                        Ingredient.of(MSBlocks.TINY_DIORITE_BLOCK),
                        new ItemStack(MSItems.POINTED_TINY_STONE.get()))
                .save(recipeOutput, name(MSItems.POINTED_TINY_STONE.get(),"3"));


    }
    public void minecraft(RecipeOutput recipeOutput){
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Blocks.CRAFTING_TABLE, 1)
                .define('#', ItemTags.LOGS)
                .define('S', MSTags.Items.TINY_STONES)
                .pattern("SS")
                .pattern("##")
                .unlockedBy("has_logs", has(ItemTags.LOGS))
                .save(recipeOutput, name(Blocks.CRAFTING_TABLE));
        //无序合成
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Blocks.OAK_PLANKS, 1)
                .define('#', ItemTags.OAK_LOGS)
                .pattern("#")
                .unlockedBy("has_planks", has(ItemTags.LOGS))
                .save(recipeOutput, name(Blocks.OAK_PLANKS));

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Blocks.SPRUCE_PLANKS, 1)
                .define('#', ItemTags.SPRUCE_LOGS)
                .pattern("#")
                .unlockedBy("has_planks", has(ItemTags.LOGS))
                .save(recipeOutput, name(Blocks.SPRUCE_PLANKS));

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Blocks.BIRCH_PLANKS, 1)
                .define('#', ItemTags.BIRCH_LOGS)
                .pattern("#")
                .unlockedBy("has_planks", has(ItemTags.LOGS))
                .save(recipeOutput, name(Blocks.BIRCH_PLANKS));

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Blocks.JUNGLE_PLANKS, 1)
                .define('#', ItemTags.JUNGLE_LOGS)
                .pattern("#")
                .unlockedBy("has_planks", has(ItemTags.LOGS))
                .save(recipeOutput, name(Blocks.JUNGLE_PLANKS));

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Blocks.ACACIA_PLANKS, 1)
                .define('#', ItemTags.ACACIA_LOGS)
                .pattern("#")
                .unlockedBy("has_planks", has(ItemTags.LOGS))
                .save(recipeOutput, name(Blocks.ACACIA_PLANKS));

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Blocks.DARK_OAK_PLANKS, 1)
                .define('#', ItemTags.DARK_OAK_LOGS)
                .pattern("#")
                .unlockedBy("has_planks", has(ItemTags.LOGS))
                .save(recipeOutput, name(Blocks.DARK_OAK_PLANKS));

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Blocks.MANGROVE_PLANKS, 1)
                .define('#', ItemTags.MANGROVE_LOGS)
                .pattern("#")
                .unlockedBy("has_planks", has(ItemTags.LOGS))
                .save(recipeOutput, name(Blocks.MANGROVE_PLANKS));

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Blocks.CHERRY_PLANKS, 1)
                .define('#', ItemTags.CHERRY_LOGS)
                .pattern("#")
                .unlockedBy("has_planks", has(ItemTags.LOGS))
                .save(recipeOutput, name(Blocks.CHERRY_PLANKS));

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Blocks.BAMBOO_PLANKS, 1)
                .define('#', ItemTags.BAMBOO_BLOCKS)
                .pattern("#")
                .unlockedBy("has_planks", has(ItemTags.LOGS))
                .save(recipeOutput, name(Blocks.BAMBOO_PLANKS));

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Blocks.CRIMSON_PLANKS, 1)
                .define('#', ItemTags.CRIMSON_STEMS)
                .pattern("#")
                .unlockedBy("has_planks", has(ItemTags.LOGS))
                .save(recipeOutput, name(Blocks.CRIMSON_PLANKS));

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Blocks.WARPED_PLANKS, 1)
                .define('#', ItemTags.WARPED_STEMS)
                .pattern("#")
                .unlockedBy("has_planks", has(ItemTags.LOGS))
                .save(recipeOutput, name(Blocks.WARPED_PLANKS));



    }

    private ResourceLocation name(Block block) {
        return MechanicalSoarMod.prefix(BuiltInRegistries.BLOCK.getKey(block).getPath());
    }
    private ResourceLocation name(Item item) {
        return MechanicalSoarMod.prefix(BuiltInRegistries.ITEM.getKey(item).getPath());
    }
    private ResourceLocation name(Block block, String name) {
        return MechanicalSoarMod.prefix(BuiltInRegistries.BLOCK.getKey(block).getPath()+"_"+name);
    }
    private ResourceLocation name(Item item, String name) {
        return MechanicalSoarMod.prefix(BuiltInRegistries.ITEM.getKey(item).getPath()+"_"+name);
    }
}