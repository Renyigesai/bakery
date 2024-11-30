
package com.renyigesai.bakeries.jei_recipes;

import com.renyigesai.bakeries.BakeriesMod;
import com.renyigesai.bakeries.init.BakeriesBlocks;
import com.renyigesai.bakeries.recipe.dough_crafting_table.DoughCraftingRecipe;

import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;


public class DoughCraftingTableRecipeCategory implements IRecipeCategory<DoughCraftingRecipe> {
	public final static ResourceLocation UID = new ResourceLocation(BakeriesMod.MODID, "dough_crafting_table_recipe");
	public final static ResourceLocation TEXTURE = new ResourceLocation(BakeriesMod.MODID, "textures/gui/jei_dough_crafting_table_gui.png");
	protected final IDrawable background;
	protected final IDrawable icon;
	public DoughCraftingTableRecipeCategory(IGuiHelper helper) {//96, 87
		this.background = helper.createDrawable(TEXTURE, 0, 0, 106, 82);
		this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(BakeriesBlocks.DOUGH_CRAFTING_TABLE.get()));
	}
	@Override
	public mezz.jei.api.recipe.RecipeType<DoughCraftingRecipe> getRecipeType() {
		return BakeryJeiPlugin.Dough_Crafting_Table_Type;
	}

	@Override
	public Component getTitle() {
		return Component.translatable("container.dough_crafting_table");
	}



	@Override
	public IDrawable getBackground() {
		return this.background;
	}

	@Override
	public IDrawable getIcon() {
		return this.icon;
	}

	@Override
	public void setRecipe(IRecipeLayoutBuilder builder, DoughCraftingRecipe recipe, IFocusGroup focuses) {
//		builder.addSlot(RecipeIngredientRole.INPUT, 20,51).addIngredients(recipe.getIngredients().get(0));
//		builder.addSlot(RecipeIngredientRole.INPUT, 11,33).addItemStack(recipe.getFlavoringItem(null));
//		builder.addSlot(RecipeIngredientRole.INPUT, 20,15).addItemStack(recipe.getAdditiveItem(null));
//		builder.addSlot(RecipeIngredientRole.INPUT, 29,33).addItemStack(recipe.getAdditiveFoodItem(null));
//
//		builder.addSlot(RecipeIngredientRole.OUTPUT, 79, 33).addItemStack(recipe.getResultItem(null));
	}
}
