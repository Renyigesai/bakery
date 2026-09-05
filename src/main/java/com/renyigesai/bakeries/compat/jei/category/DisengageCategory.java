package com.renyigesai.bakeries.compat.jei.category;

import com.renyigesai.bakeries.BakeriesMod;
import com.renyigesai.bakeries.compat.jei.BakeryJeiPlugin;
import com.renyigesai.bakeries.compat.jei.recipe.IListRecipe;
import com.renyigesai.bakeries.init.BakeriesItems;
import com.renyigesai.bakeries.init.BakeriesRecipeTypes;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.category.AbstractRecipeCategory;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class DisengageCategory extends AbstractRecipeCategory<IListRecipe> {

    public static final ResourceLocation TEXTURE = new ResourceLocation(BakeriesMod.MODID, "textures/gui/jei/jei_single_recipe.png");
    public final IDrawable back;

    public DisengageCategory(IGuiHelper helper) {
        super(BakeryJeiPlugin.DISENGAGE,Component.translatable("gui.jei.category.bakeries_disengage"), helper.createDrawableIngredient(VanillaTypes.ITEM_STACK,new ItemStack(BakeriesItems.MOULD.get())), 109, 21);
        this.back = helper.createDrawable(TEXTURE,0, 0, 109, 21);
    }


    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, IListRecipe recipe, IFocusGroup focuses) {
        builder.addInputSlot(26, 2).setStandardSlotBackground().addItemStacks(List.of(recipe.inputs()));
        builder.addOutputSlot(74, 2).setStandardSlotBackground().addItemStacks(List.of(recipe.outputs()));
        builder.addSlot(RecipeIngredientRole.OUTPUT,3,3).addItemStack(new ItemStack(BakeriesItems.BREAD_KNIFE.get()));
    }

    @SuppressWarnings("removal")
    @Override
    public @Nullable IDrawable getBackground() {
        return back;
    }
}
