package com.renyigesai.bakeries.integration.jei;

import com.renyigesai.bakeries.BakeriesMod;
import com.renyigesai.bakeries.init.BakeriesBlocks;
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

public class FermentationBoxRecipeCategory implements IRecipeCategory<SimpleMachineRecipe> {
    public static final RecipeType<SimpleMachineRecipe> TYPE =
            RecipeType.create(BakeriesMod.MODID, "fermentation_box", SimpleMachineRecipe.class);

    private static final ResourceLocation TEXTURE = new ResourceLocation(BakeriesMod.MODID, "textures/gui/jei_fermentation_box_gui.png");
    private final IDrawable background;
    private final IDrawable icon;

    public FermentationBoxRecipeCategory(IGuiHelper guiHelper) {
        this.background = guiHelper.createDrawable(TEXTURE, 0, 0, 62, 63);
        this.icon = guiHelper.createDrawableItemStack(new ItemStack(BakeriesBlocks.FERMENTATION_BOX));
    }

    @Override
    public @NotNull RecipeType<SimpleMachineRecipe> getRecipeType() {
        return TYPE;
    }

    @Override
    public @NotNull Component getTitle() {
        return Component.translatable("container.bakeries.fermentation_box");
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
        guiGraphics.drawString(net.minecraft.client.Minecraft.getInstance().font, Component.literal("20-1200 tick"), 4, 52, 0xFFFFFF, false);
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, SimpleMachineRecipe recipe, IFocusGroup focuses) {
        builder.addSlot(RecipeIngredientRole.INPUT, 15, 8).addIngredients(recipe.getIngredient());
        builder.addSlot(RecipeIngredientRole.OUTPUT, 15, 38).addItemStack(recipe.getResultItem(net.minecraft.core.RegistryAccess.EMPTY));
    }
}
