package com.renyigesai.bakeries.integration.jei.category;

import com.renyigesai.bakeries.BakeriesMod;
import com.renyigesai.bakeries.common.init.BakeriesItems;
import com.renyigesai.bakeries.common.init.BakeriesRecipeTypes;
import com.renyigesai.bakeries.common.recipe.DoughCraftingRecipe;
import com.renyigesai.bakeries.integration.jei.JEIPlugin;
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
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class DoughCraftingRecipeCategory implements IRecipeCategory<RecipeHolder<DoughCraftingRecipe>> {
    public final static ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(BakeriesMod.MODID, "textures/gui/jei_dough_crafting_table_gui.png");
    protected final IDrawable background;
    protected final IDrawable icon;

    public DoughCraftingRecipeCategory(IGuiHelper helper) {//96, 87
        this.background = helper.createDrawable(TEXTURE, 0, 0, 98, 46);
        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(BakeriesItems.DOUGH_CRAFTING_TABLE.get()));
    }

    @SuppressWarnings("removal")
    @Nullable
    @Override
    public IDrawable getBackground() {
        return background;
    }


    @Override
    public RecipeType<RecipeHolder<DoughCraftingRecipe>> getRecipeType() {
        return BakeriesRecipeTypes.JEI.DOUGH_CRAFTING;
    }

    @Override
    public @NotNull Component getTitle() {
        return Component.translatable("container.bakeries.dough_crafting_table");
    }

    @Override
    public @Nullable IDrawable getIcon() {
        return icon;
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, RecipeHolder<DoughCraftingRecipe> recipe, IFocusGroup focuses) {
        builder.addSlot(RecipeIngredientRole.INPUT, 12,15).addIngredients(recipe.value().getIngredients().getFirst());
        builder.addSlot(RecipeIngredientRole.OUTPUT, 66, 15).addItemStack(recipe.value().getResultItem(null));
    }
}
