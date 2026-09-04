package com.renyigesai.bakeries.integration.jei;

import com.renyigesai.bakeries.BakeriesMod;
import com.renyigesai.bakeries.common.init.BakeriesItems;
import com.renyigesai.bakeries.common.init.BakeriesRecipeTypes;
import com.renyigesai.bakeries.common.inventory.blender.BlenderScreen;
import com.renyigesai.bakeries.common.inventory.fermentation_box.FermentationBoxScreen;
import com.renyigesai.bakeries.common.inventory.oven.OvenScreen;
import com.renyigesai.bakeries.common.recipe.BlenderRecipe;
import com.renyigesai.bakeries.common.recipe.DrinkRecipe;
import com.renyigesai.bakeries.common.recipe.FlourSieveRecipe;
import com.renyigesai.bakeries.common.recipe.oven.OvenRecipe;
import com.renyigesai.bakeries.integration.jei.category.*;
import com.renyigesai.bakeries.integration.jei.recipe.DisengageRecipe;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.*;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeManager;

import javax.annotation.ParametersAreNonnullByDefault;

@JeiPlugin
@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
@SuppressWarnings("unused")
public class JEIPlugin implements IModPlugin {
    private static final ResourceLocation ID = ResourceLocation.fromNamespaceAndPath(BakeriesMod.MODID, "jei_plugin");

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        registration.addRecipeCategories(new OvenRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
        registration.addRecipeCategories(new BlenderRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
        registration.addRecipeCategories(new DoughCraftingRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
        registration.addRecipeCategories(new BreadKnifeRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
        registration.addRecipeCategories(new FlourSieveRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
        registration.addRecipeCategories(new DrinkRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
        registration.addRecipeCategories(new FermentationBoxCategory(registration.getJeiHelpers().getGuiHelper()));
        registration.addRecipeCategories(new DisengageCategory(registration.getJeiHelpers().getGuiHelper()));
    }


    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        RecipeManager recipeManager = Minecraft.getInstance().level.getRecipeManager();
        registration.addRecipes(BakeriesRecipeTypes.JEI.OVEN, recipeManager.getAllRecipesFor(OvenRecipe.Type.INSTANCE));
        registration.addRecipes(BakeriesRecipeTypes.JEI.BLENDER, recipeManager.getAllRecipesFor(BlenderRecipe.Type.INSTANCE));
        registration.addRecipes(BakeriesRecipeTypes.JEI.DOUGH_CRAFTING, recipeManager.getAllRecipesFor(BakeriesRecipeTypes.DOUGH_CRAFTING_TYPE.get()));
        registration.addRecipes(BakeriesRecipeTypes.JEI.BREAD_KNIFE, recipeManager.getAllRecipesFor(BakeriesRecipeTypes.BREAD_KNIFE_TYPE.get()));
        registration.addRecipes(BakeriesRecipeTypes.JEI.FLOUR_SIEVE, recipeManager.getAllRecipesFor(FlourSieveRecipe.Type.INSTANCE));
        registration.addRecipes(BakeriesRecipeTypes.JEI.DRINK, recipeManager.getAllRecipesFor(DrinkRecipe.Type.INSTANCE));
        registration.addRecipes(BakeriesRecipeTypes.JEI.FERMENTATION_BOX, recipeManager.getAllRecipesFor(BakeriesRecipeTypes.FERMENTATION_BOX_TYPE.get()));
        registration.addRecipes(BakeriesRecipeTypes.JEI.DISENGAGE, DisengageRecipe.getDisengageRecipes());
    }

    @Override
    public void registerIngredientAliases(IIngredientAliasRegistration registration) {
        registration.addAlias(BakeriesItems.MOULD_CAKE_BASE.asItem().getDefaultInstance(),"cake");
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        registration.addRecipeCatalyst(new ItemStack(BakeriesItems.OVEN.get()), BakeriesRecipeTypes.JEI.OVEN);
        registration.addRecipeCatalyst(new ItemStack(BakeriesItems.DOUGH_CRAFTING_TABLE.get()), BakeriesRecipeTypes.JEI.DOUGH_CRAFTING);
        registration.addRecipeCatalyst(new ItemStack(BakeriesItems.BLENDER.get()), BakeriesRecipeTypes.JEI.BLENDER);
        registration.addRecipeCatalyst(new ItemStack(BakeriesItems.BREAD_KNIFE.get()), BakeriesRecipeTypes.JEI.BREAD_KNIFE);
        registration.addRecipeCatalyst(new ItemStack(BakeriesItems.FLOUR_SIEVE.get()), BakeriesRecipeTypes.JEI.FLOUR_SIEVE);
        registration.addRecipeCatalyst(new ItemStack(BakeriesItems.DRINK_CUP.get()), BakeriesRecipeTypes.JEI.DRINK);
        registration.addRecipeCatalyst(new ItemStack(BakeriesItems.FERMENTATION_BOX.get()), BakeriesRecipeTypes.JEI.FERMENTATION_BOX);
    }

    @Override
    public void registerGuiHandlers(IGuiHandlerRegistration registration){
        registration.addRecipeClickArea(OvenScreen.class, 110, 16, 8, 54, BakeriesRecipeTypes.JEI.OVEN);
        registration.addRecipeClickArea(BlenderScreen.class, 136, 38, 20, 20, BakeriesRecipeTypes.JEI.BLENDER);
        registration.addRecipeClickArea(FermentationBoxScreen.class, 121, 49, 25, 16, BakeriesRecipeTypes.JEI.FERMENTATION_BOX);
    }


    @Override
    public ResourceLocation getPluginUid() {
        return ID;
    }
}
