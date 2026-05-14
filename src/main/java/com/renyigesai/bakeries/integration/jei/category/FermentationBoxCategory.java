package com.renyigesai.bakeries.integration.jei.category;

import com.renyigesai.bakeries.BakeriesMod;
import com.renyigesai.bakeries.common.init.BakeriesBlocks;
import com.renyigesai.bakeries.common.init.BakeriesRecipeTypes;
import com.renyigesai.bakeries.common.recipe.FermentationBoxRecipe;
import mezz.jei.api.constants.RecipeTypes;
import mezz.jei.api.constants.VanillaTypes;
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
import net.minecraft.world.item.crafting.RecipeHolder;
import org.jetbrains.annotations.Nullable;

public class FermentationBoxCategory implements IRecipeCategory<RecipeHolder<FermentationBoxRecipe>> {
    public final static ResourceLocation UID = ResourceLocation.fromNamespaceAndPath(BakeriesMod.MODID, "fermentation_box");
    public final static ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(BakeriesMod.MODID, "textures/gui/jei_fermentation_box_gui.png");
    protected final IDrawable background;
    protected final IDrawable icon;

    public FermentationBoxCategory(IGuiHelper helper) {
        this.background = helper.createDrawable(TEXTURE, 0, 0, 62, 63);
        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(BakeriesBlocks.FERMENTATION_BOX.get()));;
    }


    @Override
    public RecipeType<RecipeHolder<FermentationBoxRecipe>> getRecipeType() {
        return BakeriesRecipeTypes.JEI.FERMENTATION_BOX;
    }

    @Override
    public Component getTitle() {
        return Component.translatable("container.bakeries.fermentation_box");
    }

    @Override
    public @Nullable IDrawable getIcon() {
        return icon;
    }

    @SuppressWarnings("removal")
    @Nullable
    @Override
    public IDrawable getBackground() {
        return background;
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, RecipeHolder<FermentationBoxRecipe> recipe, IFocusGroup iFocusGroup) {
        builder.addSlot(RecipeIngredientRole.INPUT,15, 8).addIngredients(recipe.value().getIngredients().getFirst());
        builder.addSlot(RecipeIngredientRole.OUTPUT,15, 38).addItemStack(recipe.value().getResultItem(null));
    }
}
