
package com.renyigesai.bakery.jei_recipes;

import com.renyigesai.bakery.BakeryMod;
import com.renyigesai.bakery.init.BakeryBlocks;
import com.renyigesai.bakery.recipe.dough_crafting_table.DoughCraftingRecipe;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;


public class DoughCraftingTableRecipeCategory implements IRecipeCategory<DoughCraftingRecipe> {
	public final static ResourceLocation UID = new ResourceLocation(BakeryMod.MODID, "dough_crafting_table_recipe");
	public final static ResourceLocation TEXTURE = new ResourceLocation(BakeryMod.MODID, "textures/gui/dough_crafting_table_gui.png");
	protected final IDrawable background;
	protected final IDrawable icon;
	private final IDrawable cachedArrows;
	public DoughCraftingTableRecipeCategory(IGuiHelper helper) {//96, 87
		this.background = helper.createDrawable(TEXTURE, 0, 0, 66, 70);
		this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(BakeryBlocks.OVEN.get()));
		this.cachedArrows =  helper.createDrawable(TEXTURE, 0, 70, 20, 3);
	}
	@Override
	public void draw(DoughCraftingRecipe recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics guiGraphics, double mouseX, double mouseY) {
//		int min_temperature = recipe.getMin_temperature();
//		int max_temperature = recipe.getMax_temperature();
//		int zhen_min =  (500 - min_temperature)/(500/52) + 9;
//		int zhen_max =  (500 - max_temperature)/(500/52) + 9;
//		this.cachedArrows.draw(guiGraphics, 38,zhen_min);
//		this.cachedArrows.draw(guiGraphics, 38,zhen_max);
//		int cookTime = recipe.getTime();
//		if (cookTime > 0) {
//			int cookTimeSeconds = cookTime / 20;
//			Component timeString = Component.translatable("gui.jei.category.smelting.time.seconds", new Object[]{cookTimeSeconds});
//			Minecraft minecraft = Minecraft.getInstance();
//			Font fontRenderer = minecraft.font;
//			int stringWidth = fontRenderer.width(timeString);
//			guiGraphics.drawString(fontRenderer, timeString, 39 - stringWidth, 31, -8355712, false);
//		}

	}
	@Override
	public mezz.jei.api.recipe.RecipeType<DoughCraftingRecipe> getRecipeType() {
		return BakeryJeiPlugin.Dough_Crafting_Table_Type;
	}

	@Override
	public Component getTitle() {
		return Component.translatable("container.ovenoo");
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
		builder.addSlot(RecipeIngredientRole.INPUT, 13,17).addIngredients(recipe.getIngredients().get(0));
		builder.addSlot(RecipeIngredientRole.INPUT, 31,17).addIngredients(recipe.getIngredients().get(1));
		builder.addSlot(RecipeIngredientRole.INPUT, 13,35).addIngredients(recipe.getIngredients().get(2));
		builder.addSlot(RecipeIngredientRole.INPUT, 31,35).addIngredients(recipe.getIngredients().get(3));
//		builder.addSlot(RecipeIngredientRole.INPUT, 22,55).addItemStack(recipe.);
		builder.addSlot(RecipeIngredientRole.OUTPUT, 16, 45).addItemStack(recipe.getResultItem(null));
	}
}
