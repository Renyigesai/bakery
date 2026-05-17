package com.renyigesai.bakeries.integration.jei;

import com.renyigesai.bakeries.BakeriesMod;
import com.renyigesai.bakeries.init.BakeriesItems;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.drawable.IDrawableStatic;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("unused")
public class MokaPotBrewingRecipeCategory implements IRecipeCategory<MokaPotBrewingRecipeCategory.Recipe> {
    public static final RecipeType<Recipe> TYPE =
            RecipeType.create(BakeriesMod.MODID, "moka_pot_brewing", Recipe.class);
    public static final Recipe INSTANCE = new Recipe();

    private final IDrawableStatic background;
    private final IDrawableStatic slot;
    private final IDrawableStatic plus;
    private final IDrawableStatic arrow;
    private final IDrawable icon;

    public MokaPotBrewingRecipeCategory(IGuiHelper guiHelper) {
        this.background = guiHelper.createBlankDrawable(126, 36);
        this.slot = guiHelper.getSlotDrawable();
        this.plus = guiHelper.getRecipePlusSign();
        this.arrow = guiHelper.getRecipeArrow();
        this.icon = guiHelper.createDrawableItemStack(new ItemStack(BakeriesItems.MOKA_POT));
    }

    @Override
    public @NotNull RecipeType<Recipe> getRecipeType() {
        return TYPE;
    }

    @Override
    public @NotNull Component getTitle() {
        return Component.translatable("container.bakeries.moka_pot_brewing");
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
        slot.draw(guiGraphics, 3, 9);
        plus.draw(guiGraphics, 23, 12);
        slot.draw(guiGraphics, 39, 9);
        arrow.draw(guiGraphics, 63, 10);
        slot.draw(guiGraphics, 96, 9);
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, Recipe recipe, IFocusGroup focuses) {
        builder.addSlot(RecipeIngredientRole.INPUT, 4, 10)
                .addItemStack(new ItemStack(BakeriesItems.GROUND_COFFEE));
        builder.addSlot(RecipeIngredientRole.INPUT, 40, 10)
                .addItemStack(new ItemStack(BakeriesItems.MOKA_POT));
        builder.addSlot(RecipeIngredientRole.OUTPUT, 97, 10)
                .addItemStack(new ItemStack(BakeriesItems.MOKA_POT_FILL));
    }

    public static final class Recipe {
    }
}
