package com.renyigesai.bakeries.integration.jei;

import com.renyigesai.bakeries.BakeriesMod;
import com.renyigesai.bakeries.init.BakeriesBlocks;
import com.renyigesai.bakeries.recipe.BlenderRecipe;
import com.renyigesai.bakeries.recipe.SimpleMachineRecipe;
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
public class BlenderRecipeCategory implements IRecipeCategory<SimpleMachineRecipe> {
    public static final RecipeType<SimpleMachineRecipe> TYPE =
            RecipeType.create(BakeriesMod.MODID, "blender", SimpleMachineRecipe.class);

    private static final ResourceLocation TEXTURE = new ResourceLocation(BakeriesMod.MODID, "textures/gui/jei_blender_gui.png");
    private final IDrawable background;
    private final IDrawable icon;

    public BlenderRecipeCategory(IGuiHelper guiHelper) {
        this.background = guiHelper.createDrawable(TEXTURE, 0, 0, 90, 69);
        this.icon = guiHelper.createDrawableItemStack(new ItemStack(BakeriesBlocks.BLENDER));
    }

    @Override
    public @NotNull RecipeType<SimpleMachineRecipe> getRecipeType() {
        return TYPE;
    }

    @Override
    public @NotNull Component getTitle() {
        return Component.translatable("container.bakeries.blender");
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
    public void draw(SimpleMachineRecipe recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics guiGraphics, double mouseX, double mouseY) {
        background.draw(guiGraphics, 0, 0);
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, SimpleMachineRecipe recipe, IFocusGroup focuses) {
        int[][] positions = new int[][] {
                {5, 8}, {23, 8}, {41, 8},
                {5, 26}, {23, 26}, {41, 26},
                {5, 44}, {23, 44}, {41, 44}
        };
        if (recipe instanceof BlenderRecipe blenderRecipe) {
            int idx = 0;
            for (var ingredient : blenderRecipe.getInputIngredients()) {
                if (idx >= positions.length) {
                    break;
                }
                builder.addSlot(RecipeIngredientRole.INPUT, positions[idx][0], positions[idx][1]).addIngredients(ingredient);
                idx++;
            }
        } else {
            builder.addSlot(RecipeIngredientRole.INPUT, 5, 8).addIngredients(recipe.getIngredient());
        }
        builder.addSlot(RecipeIngredientRole.OUTPUT, 67, 43).addItemStack(recipe.getResultItem(net.minecraft.core.RegistryAccess.EMPTY));
        if (recipe instanceof BlenderRecipe blenderRecipe && blenderRecipe.hasContainer()) {
            builder.addSlot(RecipeIngredientRole.INPUT, 67, 8).addIngredients(blenderRecipe.getContainerIngredient());
        }
    }
}
