package com.renyigesai.bakeries.compat.jei.category;

import com.renyigesai.bakeries.BakeriesMod;
import com.renyigesai.bakeries.compat.jei.BakeryJeiPlugin;
import com.renyigesai.bakeries.init.BakeriesBlocks;
import com.renyigesai.bakeries.recipe.FermentationBoxRecipe;
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
import org.jetbrains.annotations.Nullable;

@SuppressWarnings("all")
public class FermentationBoxCategory implements IRecipeCategory<FermentationBoxRecipe> {
    public final static ResourceLocation UID = new ResourceLocation(BakeriesMod.MODID, "fermentation_box");
    public final static ResourceLocation TEXTURE = new ResourceLocation(BakeriesMod.MODID, "textures/gui/jei/jei_fermentation_box_gui.png");
    protected final IDrawable background;
    protected final IDrawable icon;

    public FermentationBoxCategory(IGuiHelper helper) {
        this.background = helper.createDrawable(TEXTURE, 0, 0, 62, 63);
        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(BakeriesBlocks.FERMENTATION_BOX.get()));;
    }

    @Override
    public RecipeType<FermentationBoxRecipe> getRecipeType() {
        return BakeryJeiPlugin.FERMENTATION_TYPE;
    }

    @Override
    public Component getTitle() {
        return Component.translatable("container.fermentation_box");
    }

    @Override
    public @Nullable IDrawable getIcon() {
        return icon;
    }

    @Nullable
    @Override
    public IDrawable getBackground() {
        return background;
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, FermentationBoxRecipe recipe, IFocusGroup iFocusGroup) {
        builder.addSlot(RecipeIngredientRole.INPUT,15, 8).addIngredients(recipe.getIngredients().get(0));
        builder.addSlot(RecipeIngredientRole.OUTPUT,15, 38).addItemStack(recipe.getResultItem(null));
    }
}
