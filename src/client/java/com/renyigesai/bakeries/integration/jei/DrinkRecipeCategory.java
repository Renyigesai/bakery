package com.renyigesai.bakeries.integration.jei;

import com.renyigesai.bakeries.BakeriesMod;
import com.renyigesai.bakeries.init.BakeriesItems;
import com.renyigesai.bakeries.recipe.CoffeeRecipe;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("unused")
public class DrinkRecipeCategory implements IRecipeCategory<CoffeeRecipe> {
    public static final RecipeType<CoffeeRecipe> TYPE =
            RecipeType.create(BakeriesMod.MODID, "coffee", CoffeeRecipe.class);

    private static final ResourceLocation TEXTURE =
            new ResourceLocation(BakeriesMod.MODID, "textures/gui/jei_drink_gui.png");
    private final IDrawable background;
    private final IDrawable icon;

    public DrinkRecipeCategory(IGuiHelper guiHelper) {
        this.background = guiHelper.createDrawable(TEXTURE, 0, 0, 116, 18);
        this.icon = guiHelper.createDrawableItemStack(new ItemStack(BakeriesItems.DRINK_CUP));
    }

    @Override
    public @NotNull RecipeType<CoffeeRecipe> getRecipeType() {
        return TYPE;
    }

    @Override
    public @NotNull Component getTitle() {
        return Component.translatable("container.bakeries.drink");
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
    public void draw(CoffeeRecipe recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics guiGraphics, double mouseX, double mouseY) {
        background.draw(guiGraphics, 0, 0);
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, CoffeeRecipe recipe, IFocusGroup focuses) {
        int[][] positions = new int[][]{{3, 2}, {21, 2}, {39, 2}, {57, 2}};
        int idx = 0;

        for (var ingredient : recipe.getIngredients()) {
            if (idx >= positions.length) break;
            if (!ingredient.isEmpty()) {
                builder.addSlot(RecipeIngredientRole.INPUT, positions[idx][0], positions[idx][1])
                        .addIngredients(ingredient);
                idx++;
            }
        }

        builder.addSlot(RecipeIngredientRole.OUTPUT, 99, 1)
                .addItemStack(recipe.getResultItem(net.minecraft.core.RegistryAccess.EMPTY));
    }
}
