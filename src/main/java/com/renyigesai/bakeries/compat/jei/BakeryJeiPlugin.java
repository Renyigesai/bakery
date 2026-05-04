
package com.renyigesai.bakeries.compat.jei;

import com.renyigesai.bakeries.BakeriesMod;
import com.renyigesai.bakeries.compat.jei.category.*;
import com.renyigesai.bakeries.init.BakeriesBlocks;
import com.renyigesai.bakeries.init.BakeriesItems;
import com.renyigesai.bakeries.init.BakeriesRecipeTypes;
import com.renyigesai.bakeries.inventory.blender.BlenderScreen;
import com.renyigesai.bakeries.inventory.fermentation_box.FermentationBoxScreen;
import com.renyigesai.bakeries.inventory.oven.OvenScreen;
import com.renyigesai.bakeries.recipe.*;
import com.renyigesai.bakeries.recipe.flour_sieve.FlourSieveRecipe;
import com.renyigesai.bakeries.recipe.oven.OvenRecipe;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.IGuiHandlerRegistration;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.crafting.RecipeType;
import org.jetbrains.annotations.NotNull;

import java.util.List;

@JeiPlugin
public class BakeryJeiPlugin implements IModPlugin {
	public static final mezz.jei.api.recipe.RecipeType<OvenRecipe> OVEN_TYPE = new mezz.jei.api.recipe.RecipeType<>(OvenRecipeCategory.UID, OvenRecipe.class);
	public static final mezz.jei.api.recipe.RecipeType<DoughCraftingRecipe> Dough_Crafting_Table_Type = new mezz.jei.api.recipe.RecipeType<>(DoughCraftingTableRecipeCategory.UID, DoughCraftingRecipe.class);
	public static final mezz.jei.api.recipe.RecipeType<BlenderRecipe> BLENDER_TYPE = new mezz.jei.api.recipe.RecipeType<>(BlenderCategory.UID, BlenderRecipe.class);
	public static final mezz.jei.api.recipe.RecipeType<BreadKnifeRecipe> BREAD_KNIFE_TYPE = new mezz.jei.api.recipe.RecipeType<>(BreadKnifeRecipeCategory.UID, BreadKnifeRecipe.class);
	public static final mezz.jei.api.recipe.RecipeType<FlourSieveRecipe> FLOUR_SIEVE_TYPE = new mezz.jei.api.recipe.RecipeType<>(FlourSieveRecipeCategory.UID, FlourSieveRecipe.class);
	public static final mezz.jei.api.recipe.RecipeType<CoffeeRecipe> DRINK_TYPE = new mezz.jei.api.recipe.RecipeType<>(DrinkRecipeCategory.UID, CoffeeRecipe.class);
	public static final mezz.jei.api.recipe.RecipeType<StoneKilnRecipe> STONE_KILN_TYPE = new mezz.jei.api.recipe.RecipeType<>(StoneKilnCategory.UID, StoneKilnRecipe.class);
	public static final mezz.jei.api.recipe.RecipeType<FermentationBoxRecipe> FERMENTATION_TYPE = new mezz.jei.api.recipe.RecipeType<>(FermentationBoxCategory.UID, FermentationBoxRecipe.class);
	@Override
	public @NotNull ResourceLocation getPluginUid() {
		return new ResourceLocation(BakeriesMod.MODID,"jei_plugin");
	}

	@Override
	public void registerCategories(IRecipeCategoryRegistration registration) {
		registration.addRecipeCategories(new OvenRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
		registration.addRecipeCategories(new DoughCraftingTableRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
		registration.addRecipeCategories(new BlenderCategory(registration.getJeiHelpers().getGuiHelper()));
		registration.addRecipeCategories(new BreadKnifeRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
		registration.addRecipeCategories(new FlourSieveRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
		registration.addRecipeCategories(new DrinkRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
		registration.addRecipeCategories(new StoneKilnCategory(registration.getJeiHelpers().getGuiHelper()));
		registration.addRecipeCategories(new FermentationBoxCategory(registration.getJeiHelpers().getGuiHelper()));
	}

	@SuppressWarnings("unchecked")
	@Override
	public void registerRecipes(IRecipeRegistration registration) {
		registration.addRecipes(OVEN_TYPE, (List<OvenRecipe>) getRecipesList(OvenRecipe.Type.INSTANCE));

		registration.addRecipes(Dough_Crafting_Table_Type, (List<DoughCraftingRecipe>) getRecipesList(DoughCraftingRecipe.Type.INSTANCE));

		registration.addRecipes(BLENDER_TYPE, (List<BlenderRecipe>) getRecipesList(BlenderRecipe.Type.INSTANCE));

		registration.addRecipes(BREAD_KNIFE_TYPE, (List<BreadKnifeRecipe>) getRecipesList(BreadKnifeRecipe.Type.INSTANCE));

		registration.addRecipes(FLOUR_SIEVE_TYPE, (List<FlourSieveRecipe>) getRecipesList(FlourSieveRecipe.Type.INSTANCE));

		registration.addRecipes(DRINK_TYPE, (List<CoffeeRecipe>) getRecipesList(CoffeeRecipe.Type.INSTANCE));

		registration.addRecipes(STONE_KILN_TYPE, (List<StoneKilnRecipe>) getRecipesList(StoneKilnRecipe.Type.INSTANCE));

		registration.addRecipes(FERMENTATION_TYPE, (List<FermentationBoxRecipe>) getRecipesList(BakeriesRecipeTypes.FERMENTATION_BOX.get()));
	}

	@Override
	public void registerGuiHandlers(IGuiHandlerRegistration registration){
		registration.addRecipeClickArea(OvenScreen.class,110,16,8,54,
				OVEN_TYPE);
		registration.addRecipeClickArea(BlenderScreen.class,136,38,13,10,
				BLENDER_TYPE);
		registration.addRecipeClickArea(FermentationBoxScreen.class,121,49,25,16,
				FERMENTATION_TYPE);
	}

	@Override
	public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
		registration.addRecipeCatalyst(new ItemStack(BakeriesBlocks.OVEN.get()), OVEN_TYPE);
		registration.addRecipeCatalyst(new ItemStack(BakeriesBlocks.DOUGH_CRAFTING_TABLE.get()), Dough_Crafting_Table_Type);
		registration.addRecipeCatalyst(new ItemStack(BakeriesBlocks.BLENDER.get()), BLENDER_TYPE);
		registration.addRecipeCatalyst(new ItemStack(BakeriesItems.BREAD_KNIFE.get()), BREAD_KNIFE_TYPE);
		registration.addRecipeCatalyst(new ItemStack(BakeriesItems.FLOUR_SIEVE.get()), FLOUR_SIEVE_TYPE);
		registration.addRecipeCatalyst(new ItemStack(BakeriesItems.DRINK_CUP.get()), DRINK_TYPE);
		registration.addRecipeCatalyst(new ItemStack(BakeriesBlocks.STONE_KILN.get()), STONE_KILN_TYPE);
		registration.addRecipeCatalyst(new ItemStack(BakeriesBlocks.FERMENTATION_BOX.get()), FERMENTATION_TYPE);
	}

	@SuppressWarnings("unchecked")
	private List<? extends Recipe<?>> getRecipesList(RecipeType<?> type){
		Minecraft minecraft = Minecraft.getInstance();
		ClientLevel level = minecraft.level;
		if (level != null){
			RecipeManager recipeManager = level.getRecipeManager();
			return List.copyOf(recipeManager.getAllRecipesFor((RecipeType) type));
		}
		return List.of();
	}
}
