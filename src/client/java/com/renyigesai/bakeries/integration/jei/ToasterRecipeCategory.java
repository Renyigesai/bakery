package com.renyigesai.bakeries.integration.jei;

import com.renyigesai.bakeries.BakeriesMod;
import com.renyigesai.bakeries.init.BakeriesBlocks;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.drawable.IDrawableStatic;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CampfireCookingRecipe;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("unused")
public class ToasterRecipeCategory implements IRecipeCategory<ToasterRecipeCategory.Recipe> {
    public static final RecipeType<Recipe> TYPE =
            RecipeType.create(BakeriesMod.MODID, "toaster", Recipe.class);

    private final IDrawableStatic background;
    private final IDrawableStatic slot;
    private final IDrawableStatic arrow;
    private final IDrawable icon;

    public ToasterRecipeCategory(IGuiHelper guiHelper) {
        this.background = guiHelper.createBlankDrawable(82, 34);
        this.slot = guiHelper.getSlotDrawable();
        this.arrow = guiHelper.getRecipeArrow();
        this.icon = guiHelper.createDrawableItemStack(new ItemStack(BakeriesBlocks.TOASTER));
    }

    @Override
    public @NotNull RecipeType<Recipe> getRecipeType() {
        return TYPE;
    }

    @Override
    public @NotNull Component getTitle() {
        return Component.translatable("block.bakeries.toaster");
    }

    @Override
    public int getWidth() {
        return background.getWidth();
    }

    @Override
    public int getHeight() {
        return background.getHeight();
    }

    @Override
    public IDrawable getIcon() {
        return icon;
    }

    @Override
    public void draw(Recipe recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics guiGraphics, double mouseX, double mouseY) {
        background.draw(guiGraphics, 0, 0);
        slot.draw(guiGraphics, 3, 7);
        arrow.draw(guiGraphics, 28, 7);
        slot.draw(guiGraphics, 59, 7);
        guiGraphics.drawString(Minecraft.getInstance().font,
                Component.translatable("container.bakeries.toaster.time", recipe.toasterTime()),
                31, 24, 0x555555, false);
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, Recipe recipe, IFocusGroup focuses) {
        builder.addSlot(RecipeIngredientRole.INPUT, 4, 8).addIngredients(recipe.input());
        builder.addSlot(RecipeIngredientRole.OUTPUT, 60, 8).addItemStack(recipe.output());
    }

    public record Recipe(Ingredient input, ItemStack output, int toasterTime) {
        public static Recipe fromCampfireRecipe(CampfireCookingRecipe recipe) {
            return new Recipe(
                    recipe.getIngredients().get(0),
                    recipe.getResultItem(RegistryAccess.EMPTY),
                    Math.max(1, recipe.getCookingTime() / 3)
            );
        }
    }
}
