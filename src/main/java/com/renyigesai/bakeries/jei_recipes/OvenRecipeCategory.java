
package com.renyigesai.bakeries.jei_recipes;

import com.renyigesai.bakeries.BakeriesMod;
import com.renyigesai.bakeries.block.oven.OvenBlockEntity;
import com.renyigesai.bakeries.init.BakeriesBlocks;
import com.renyigesai.bakeries.recipe.oven.OvenRecipe;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import org.checkerframework.checker.units.qual.C;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("all")
public class OvenRecipeCategory implements IRecipeCategory<OvenRecipe> {
	public final static ResourceLocation UID = new ResourceLocation(BakeriesMod.MODID, "oven_recipe");
	public final static ResourceLocation TEXTURE = new ResourceLocation(BakeriesMod.MODID, "textures/gui/jei_oven_gui.png");
	protected final IDrawable background;
	protected final IDrawable icon;
	private final IDrawable cachedArrows;
	public OvenRecipeCategory(IGuiHelper helper) {//96, 87
		this.background = helper.createDrawable(TEXTURE, 0, 0, 58, 63);
		this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(BakeriesBlocks.OVEN.get()));
		this.cachedArrows =  helper.createDrawable(TEXTURE, 0, 70, 20, 3);
	}
	@Override
	public void draw(OvenRecipe recipe, @NotNull IRecipeSlotsView recipeSlotsView, @NotNull GuiGraphics guiGraphics, double mouseX, double mouseY) {
		int cookTime = recipe.getTime();
		Minecraft minecraft = Minecraft.getInstance();
		if (cookTime > 0) {
			int cookTimeSeconds = cookTime / 20;
			Component timeString = Component.translatable("gui.jei.category.smelting.time.seconds", new Object[]{cookTimeSeconds});
			Font fontRenderer = minecraft.font;
			int stringWidth = fontRenderer.width(timeString);
			guiGraphics.drawString(fontRenderer, timeString, 39 - stringWidth, 27, -8355712, false);
		}
		if (mouseX >= 44 && mouseX <= 53 && mouseY >= 7 && mouseY <= 55){
			renderTemperatureTooltip(minecraft,guiGraphics,mouseX,mouseY,recipe);
		}
	}

	protected void renderTemperatureTooltip(Minecraft minecraft,GuiGraphics gui, double mouseX, double mouseY,OvenRecipe recipe) {
		if (minecraft != null && minecraft.player != null) {
			List<Component> tooltip = new ArrayList<>();
			tooltip.add(Component.literal("Min " + recipe.getMin_temperature() + "\u00b0C").withStyle(ChatFormatting.BLUE));
			tooltip.add(Component.literal("Max " + recipe.getMax_temperature() + "\u00b0C").withStyle(ChatFormatting.BLUE));
			gui.renderComponentTooltip(minecraft.font, tooltip, (int) mouseX,(int)mouseY);
		}
	}

	@Override
	public mezz.jei.api.recipe.@NotNull RecipeType<OvenRecipe> getRecipeType() {
		return BakeryJeiPlugin.Oven_Type;
	}

	@Override
	public @NotNull Component getTitle() {
		return Component.translatable("container.oven");
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
	public void setRecipe(IRecipeLayoutBuilder builder, OvenRecipe recipe, @NotNull IFocusGroup focuses) {
		builder.addSlot(RecipeIngredientRole.INPUT, 15, 8).addIngredients(recipe.getIngredients().get(0));
		builder.addSlot(RecipeIngredientRole.OUTPUT, 15, 38).addItemStack(recipe.getResultItem(null));
	}
}
