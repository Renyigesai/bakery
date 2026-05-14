package com.renyigesai.bakeries.integration.jei;

import com.renyigesai.bakeries.BakeriesMod;
import com.renyigesai.bakeries.init.BakeriesBlocks;
import com.renyigesai.bakeries.init.BakeriesRecipeTypes;
import com.renyigesai.bakeries.recipe.SimpleMachineRecipe;
import com.renyigesai.bakeries.screen.BlenderScreen;
import com.renyigesai.bakeries.screen.DoughCraftingTableScreen;
import com.renyigesai.bakeries.screen.OvenScreen;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IGuiHandlerRegistration;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeManager;

import java.util.List;

@JeiPlugin
public class JEIPlugin implements IModPlugin {
    private static final ResourceLocation UID = new ResourceLocation(BakeriesMod.MODID, "jei_plugin");

    @Override
    public ResourceLocation getPluginUid() {
        return UID;
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        registration.addRecipeCategories(
                new OvenRecipeCategory(registration.getJeiHelpers().getGuiHelper()),
                new BlenderRecipeCategory(registration.getJeiHelpers().getGuiHelper()),
                new DoughCraftingRecipeCategory(registration.getJeiHelpers().getGuiHelper())
        );
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        if (Minecraft.getInstance().level == null) {
            return;
        }
        RecipeManager manager = Minecraft.getInstance().level.getRecipeManager();

        List<SimpleMachineRecipe> ovenRecipes = manager.getAllRecipesFor(BakeriesRecipeTypes.OVEN);
        List<SimpleMachineRecipe> blenderRecipes = manager.getAllRecipesFor(BakeriesRecipeTypes.BLENDER);
        List<SimpleMachineRecipe> doughRecipes = manager.getAllRecipesFor(BakeriesRecipeTypes.DOUGH_CRAFTING);
        registration.addRecipes(OvenRecipeCategory.TYPE, ovenRecipes);
        registration.addRecipes(BlenderRecipeCategory.TYPE, blenderRecipes);
        registration.addRecipes(DoughCraftingRecipeCategory.TYPE, doughRecipes);
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        registration.addRecipeCatalyst(new ItemStack(BakeriesBlocks.OVEN), OvenRecipeCategory.TYPE);
        registration.addRecipeCatalyst(new ItemStack(BakeriesBlocks.BLENDER), BlenderRecipeCategory.TYPE);
        registration.addRecipeCatalyst(new ItemStack(BakeriesBlocks.DOUGH_CRAFTING_TABLE), DoughCraftingRecipeCategory.TYPE);
    }

    @Override
    public void registerGuiHandlers(IGuiHandlerRegistration registration) {
        registration.addRecipeClickArea(OvenScreen.class, 110, 16, 8, 54, OvenRecipeCategory.TYPE);
        registration.addRecipeClickArea(BlenderScreen.class, 136, 38, 20, 20, BlenderRecipeCategory.TYPE);
        registration.addRecipeClickArea(DoughCraftingTableScreen.class, 118, 30, 22, 18, DoughCraftingRecipeCategory.TYPE);
    }
}
