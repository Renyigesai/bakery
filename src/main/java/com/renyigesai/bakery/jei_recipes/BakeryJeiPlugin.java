
package com.renyigesai.bakery.jei_recipes;

import com.renyigesai.bakery.BakeryMod;
import com.renyigesai.bakery.init.BakeryBlocks;
import com.renyigesai.bakery.inventory.oven.OvenScreen;
import com.renyigesai.bakery.recipe.OvenRecipe;
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

import java.util.List;
import java.util.Objects;

@JeiPlugin
public class BakeryJeiPlugin implements IModPlugin {
	public static mezz.jei.api.recipe.RecipeType<OvenRecipe> Oven_Type = new mezz.jei.api.recipe.RecipeType<>(OvenRecipeCategory.UID, OvenRecipe.class);

	@Override
	public ResourceLocation getPluginUid() {
		return new ResourceLocation(BakeryMod.MODID,"jei_plugin");
	}

	@Override
	public void registerCategories(IRecipeCategoryRegistration registration) {
		registration.addRecipeCategories(new OvenRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
	}

	@Override
	public void registerRecipes(IRecipeRegistration registration) {
		RecipeManager recipeManager = Objects.requireNonNull(Minecraft.getInstance().level).getRecipeManager();
		List<OvenRecipe> SoundCollectorRecipes = recipeManager.getAllRecipesFor(OvenRecipe.Type.INSTANCE);
		registration.addRecipes(Oven_Type, SoundCollectorRecipes);

//		registration.addIngredientInfo(List.of(new ItemStack(BakeryBlocks.OVEN.get())), VanillaTypes.ITEM_STACK, Component.translatable("jei.defender.netheritr_blockxx_1"));
	}

	@Override
	public void registerGuiHandlers(IGuiHandlerRegistration registration){
		registration.addRecipeClickArea(OvenScreen.class,110,16,8,54,
				Oven_Type);
	}

	@Override
	public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
		registration.addRecipeCatalyst(new ItemStack(BakeryBlocks.OVEN.get()), Oven_Type);
	}
}
