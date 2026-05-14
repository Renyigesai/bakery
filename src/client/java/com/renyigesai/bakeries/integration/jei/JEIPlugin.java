package com.renyigesai.bakeries.integration.jei;

import com.renyigesai.bakeries.BakeriesMod;
import com.renyigesai.bakeries.init.BakeriesBlocks;
import com.renyigesai.bakeries.init.BakeriesItems;
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
                new DoughCraftingRecipeCategory(registration.getJeiHelpers().getGuiHelper()),
                new BreadKnifeRecipeCategory(registration.getJeiHelpers().getGuiHelper()),
                new FlourSieveRecipeCategory(registration.getJeiHelpers().getGuiHelper()),
                new DrinkRecipeCategory(registration.getJeiHelpers().getGuiHelper())
        );
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        Minecraft minecraft = Minecraft.getInstance();
        RecipeManager manager = null;
        if (minecraft.getConnection() != null) {
            manager = minecraft.getConnection().getRecipeManager();
        } else if (minecraft.level != null) {
            manager = minecraft.level.getRecipeManager();
        }

        if (manager == null) {
            BakeriesMod.LOGGER.warn("JEI recipe registration skipped: RecipeManager not available yet.");
            return;
        }

        List<SimpleMachineRecipe> ovenRecipes = manager.getAllRecipesFor(BakeriesRecipeTypes.OVEN);
        List<SimpleMachineRecipe> blenderRecipes = manager.getAllRecipesFor(BakeriesRecipeTypes.BLENDER);
        List<SimpleMachineRecipe> doughRecipes = manager.getAllRecipesFor(BakeriesRecipeTypes.DOUGH_CRAFTING);
        List<SimpleMachineRecipe> breadKnifeRecipes = manager.getAllRecipesFor(BakeriesRecipeTypes.BREAD_KNIFE);
        List<SimpleMachineRecipe> flourSieveRecipes = manager.getAllRecipesFor(BakeriesRecipeTypes.FLOUR_SIEVE);
        List<SimpleMachineRecipe> drinkRecipes = manager.getAllRecipesFor(BakeriesRecipeTypes.DRINK);
        registration.addRecipes(OvenRecipeCategory.TYPE, ovenRecipes);
        registration.addRecipes(BlenderRecipeCategory.TYPE, blenderRecipes);
        registration.addRecipes(DoughCraftingRecipeCategory.TYPE, doughRecipes);
        registration.addRecipes(BreadKnifeRecipeCategory.TYPE, breadKnifeRecipes);
        registration.addRecipes(FlourSieveRecipeCategory.TYPE, flourSieveRecipes);
        registration.addRecipes(DrinkRecipeCategory.TYPE, drinkRecipes);
        BakeriesMod.LOGGER.info("JEI recipes registered: oven={}, blender={}, dough={}, bread_knife={}, flour_sieve={}, drink={}",
                ovenRecipes.size(), blenderRecipes.size(), doughRecipes.size(), breadKnifeRecipes.size(), flourSieveRecipes.size(), drinkRecipes.size());
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        registration.addRecipeCatalyst(new ItemStack(BakeriesBlocks.OVEN), OvenRecipeCategory.TYPE);
        registration.addRecipeCatalyst(new ItemStack(BakeriesBlocks.BLENDER), BlenderRecipeCategory.TYPE);
        registration.addRecipeCatalyst(new ItemStack(BakeriesBlocks.DOUGH_CRAFTING_TABLE), DoughCraftingRecipeCategory.TYPE);
        registration.addRecipeCatalyst(new ItemStack(BakeriesItems.BREAD_KNIFE), BreadKnifeRecipeCategory.TYPE);
        registration.addRecipeCatalyst(new ItemStack(BakeriesItems.FLOUR_SIEVE), FlourSieveRecipeCategory.TYPE);
        registration.addRecipeCatalyst(new ItemStack(BakeriesBlocks.DRINK_CUP), DrinkRecipeCategory.TYPE);
    }

    @Override
    public void registerGuiHandlers(IGuiHandlerRegistration registration) {
        registration.addRecipeClickArea(OvenScreen.class, 110, 16, 8, 54, OvenRecipeCategory.TYPE);
        registration.addRecipeClickArea(BlenderScreen.class, 136, 38, 20, 20, BlenderRecipeCategory.TYPE);
        registration.addRecipeClickArea(DoughCraftingTableScreen.class, 118, 30, 22, 18, DoughCraftingRecipeCategory.TYPE);
    }
}
