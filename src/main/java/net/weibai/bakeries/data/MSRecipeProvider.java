package net.weibai.bakeries.data;


import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.weibai.bakeries.BakeriesMod;

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

    }
    public void minecraft(RecipeOutput recipeOutput){


    }

    private ResourceLocation name(Block block) {
        return BakeriesMod.rl(BuiltInRegistries.BLOCK.getKey(block).getPath());
    }
    private ResourceLocation name(Item item) {
        return BakeriesMod.rl(BuiltInRegistries.ITEM.getKey(item).getPath());
    }
    private ResourceLocation name(Block block, String name) {
        return BakeriesMod.rl(BuiltInRegistries.BLOCK.getKey(block).getPath()+"_"+name);
    }
    private ResourceLocation name(Item item, String name) {
        return BakeriesMod.rl(BuiltInRegistries.ITEM.getKey(item).getPath()+"_"+name);
    }
}