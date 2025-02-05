
package com.renyigesai.bakeries.jei_recipes;

import com.renyigesai.bakeries.BakeriesMod;
import com.renyigesai.bakeries.init.BakeriesBlocks;
import com.renyigesai.bakeries.recipe.dough_crafting_table.DoughCraftingRecipe;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class DoughCraftingTableRecipeCategory implements IRecipeCategory<DoughCraftingRecipe> {
	public final static ResourceLocation UID = new ResourceLocation(BakeriesMod.MODID, "dough_crafting_table_recipe");
	public final static ResourceLocation TEXTURE = new ResourceLocation(BakeriesMod.MODID, "textures/gui/jei_dough_crafting_table_gui.png");
	protected final IDrawable background;
	protected final IDrawable icon;
	public DoughCraftingTableRecipeCategory(IGuiHelper helper) {//96, 87
		this.background = helper.createDrawable(TEXTURE, 0, 0, 98, 46);
		this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(BakeriesBlocks.DOUGH_CRAFTING_TABLE.get()));
	}
	@Override
	public mezz.jei.api.recipe.@NotNull RecipeType<DoughCraftingRecipe> getRecipeType() {
		return BakeryJeiPlugin.Dough_Crafting_Table_Type;
	}

	@Override
	public @NotNull Component getTitle() {
		return Component.translatable("container.dough_crafting_table");
	}



	@SuppressWarnings("removal")
	@Override
	public IDrawable getBackground() {
		return this.background;
	}

	@Override
	public IDrawable getIcon() {
		return this.icon;
	}

	@Override
	public void setRecipe(IRecipeLayoutBuilder builder, DoughCraftingRecipe recipe, @NotNull IFocusGroup focuses) {
		builder.addSlot(RecipeIngredientRole.INPUT, 12,15).addIngredients(recipe.getIngredients().get(0));
		builder.addSlot(RecipeIngredientRole.OUTPUT, 66, 15).addItemStack(recipe.getResultItem(null));
	}
}
