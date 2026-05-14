package com.renyigesai.bakeries.integration.jei;

import com.renyigesai.bakeries.BakeriesMod;
import com.renyigesai.bakeries.init.BakeriesBlocks;
import com.renyigesai.bakeries.recipe.SimpleMachineRecipe;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

public class DoughCraftingRecipeCategory implements IRecipeCategory<SimpleMachineRecipe> {
    public static final RecipeType<SimpleMachineRecipe> TYPE =
            RecipeType.create(BakeriesMod.MODID, "dough_crafting", SimpleMachineRecipe.class);

    private static final ResourceLocation TEXTURE = new ResourceLocation(BakeriesMod.MODID, "textures/gui/container/dough_crafting_table_gui.png");
    private final IDrawable background;
    private final IDrawable icon;

    public DoughCraftingRecipeCategory(IGuiHelper guiHelper) {
        this.background = guiHelper.createDrawable(TEXTURE, 10, 14, 156, 60);
        this.icon = guiHelper.createDrawableItemStack(new ItemStack(BakeriesBlocks.DOUGH_CRAFTING_TABLE));
    }

    @Override
    public RecipeType<SimpleMachineRecipe> getRecipeType() {
        return TYPE;
    }

    @Override
    public Component getTitle() {
        return Component.translatable("container.bakeries.dough_crafting_table");
    }

    @Override
    public IDrawable getBackground() {
        return background;
    }

    @Override
    public IDrawable getIcon() {
        return icon;
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, SimpleMachineRecipe recipe, IFocusGroup focuses) {
        builder.addSlot(RecipeIngredientRole.INPUT, 12, 22).addIngredients(recipe.getIngredient());
        builder.addSlot(RecipeIngredientRole.OUTPUT, 135, 22).addItemStack(recipe.getResultItem(net.minecraft.core.RegistryAccess.EMPTY));
    }
}
