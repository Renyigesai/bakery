
package com.renyigesai.bakeries.jei_recipes;

import com.renyigesai.bakeries.BakeriesMod;
import com.renyigesai.bakeries.init.BakeriesBlocks;
import com.renyigesai.bakeries.inventory.oven.OvenScreen;
import com.renyigesai.bakeries.recipe.dough_crafting_table.DoughCraftingRecipe;
import com.renyigesai.bakeries.recipe.oven.OvenRecipe;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.IGuiHandlerRegistration;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeManager;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;

@JeiPlugin
public class BakeryJeiPlugin implements IModPlugin {
	public static final mezz.jei.api.recipe.RecipeType<OvenRecipe> Oven_Type = new mezz.jei.api.recipe.RecipeType<>(OvenRecipeCategory.UID, OvenRecipe.class);
	public static final mezz.jei.api.recipe.RecipeType<DoughCraftingRecipe> Dough_Crafting_Table_Type = new mezz.jei.api.recipe.RecipeType<>(DoughCraftingTableRecipeCategory.UID, DoughCraftingRecipe.class);
	@Override
	public @NotNull ResourceLocation getPluginUid() {
		return new ResourceLocation(BakeriesMod.MODID,"jei_plugin");
	}

	@Override
	public void registerCategories(IRecipeCategoryRegistration registration) {
		registration.addRecipeCategories(new OvenRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
		registration.addRecipeCategories(new DoughCraftingTableRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
	}

	@Override
	public void registerRecipes(IRecipeRegistration registration) {
		RecipeManager recipeManager = Objects.requireNonNull(Minecraft.getInstance().level).getRecipeManager();
		List<OvenRecipe> SoundCollectorRecipes = recipeManager.getAllRecipesFor(OvenRecipe.Type.INSTANCE);
		registration.addRecipes(Oven_Type, SoundCollectorRecipes);
		List<DoughCraftingRecipe> doughCraftingRecipes = recipeManager.getAllRecipesFor(DoughCraftingRecipe.Type.INSTANCE);
		registration.addRecipes(Dough_Crafting_Table_Type, doughCraftingRecipes);

//		registration.addIngredientInfo(List.of(new ItemStack(BakeryBlocks.OVEN.get())), VanillaTypes.ITEM_STACK, Component.translatable("jei.defender.netheritr_blockxx_1"));
	}

	@Override
	public void registerGuiHandlers(IGuiHandlerRegistration registration){
		registration.addRecipeClickArea(OvenScreen.class,110,16,8,54,
				Oven_Type);

	}

	@Override
	public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
		registration.addRecipeCatalyst(new ItemStack(BakeriesBlocks.OVEN.get()), Oven_Type);
		registration.addRecipeCatalyst(new ItemStack(BakeriesBlocks.DOUGH_CRAFTING_TABLE.get()), Dough_Crafting_Table_Type);
	}
}
