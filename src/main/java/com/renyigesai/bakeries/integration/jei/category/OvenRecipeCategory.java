package com.renyigesai.bakeries.integration.jei.category;

import com.renyigesai.bakeries.BakeriesMod;
import com.renyigesai.bakeries.common.init.BakeriesItems;
import com.renyigesai.bakeries.common.init.BakeriesRecipeTypes;
import com.renyigesai.bakeries.common.recipe.oven.OvenRecipe;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class OvenRecipeCategory implements IRecipeCategory<RecipeHolder<OvenRecipe>> {
    protected final IDrawable background;
    private final IDrawable cachedArrows;
    public final static ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(BakeriesMod.MODID, "textures/gui/jei_oven_gui.png");
    private final IDrawable icon;

    public OvenRecipeCategory(IGuiHelper helper) {
        this.background = helper.createDrawable(TEXTURE, 0, 0, 58, 63);
        this.cachedArrows = helper.createDrawable(TEXTURE, 0, 70, 20, 3);
        this.icon = helper.createDrawableItemStack(new ItemStack(BakeriesItems.OVEN.get()));
    }

    @Override
    public void draw(RecipeHolder<OvenRecipe> recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics guiGraphics, double mouseX, double mouseY) {
        int cookTime = recipe.value().getTime();
        Minecraft minecraft = Minecraft.getInstance();
        if (cookTime > 0) {
            int cookTimeSeconds = cookTime / 20;
            Component timeString = Component.translatable("gui.jei.category.smelting.time.seconds", new Object[]{cookTimeSeconds});
            Font fontRenderer = minecraft.font;
            int stringWidth = fontRenderer.width(timeString);
            guiGraphics.drawString(fontRenderer, timeString, 39 - stringWidth, 27, -8355712, false);
        }
        if (mouseX >= 44 && mouseX <= 53 && mouseY >= 7 && mouseY <= 55){
            renderTemperatureTooltip(minecraft,guiGraphics,mouseX,mouseY,recipe.value());
        }
    }

    protected void renderTemperatureTooltip(Minecraft minecraft,GuiGraphics gui, double mouseX, double mouseY,OvenRecipe recipe) {
        if (minecraft != null && minecraft.player != null) {
            List<Component> tooltip = new ArrayList<>();
            tooltip.add(Component.literal("Min " + recipe.getMinTemperature()+ "\u00b0C").withStyle(ChatFormatting.BLUE));
            tooltip.add(Component.literal("Max " + recipe.getMaxTemperature() + "\u00b0C").withStyle(ChatFormatting.BLUE));
            gui.renderComponentTooltip(minecraft.font, tooltip, (int) mouseX,(int)mouseY);
        }
    }

    @SuppressWarnings("removal")
    @Nullable
    @Override
    public IDrawable getBackground() {
        return background;
    }

    @Override
    public RecipeType<RecipeHolder<OvenRecipe>> getRecipeType() {
        return BakeriesRecipeTypes.JEI.OVEN;
    }

    @Override
    public Component getTitle() {
        return Component.translatable("container.bakeries.oven");
    }

    @Override
    public @Nullable IDrawable getIcon() {
        return icon;
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, RecipeHolder<OvenRecipe> recipe, IFocusGroup focuses) {
        builder.addSlot(RecipeIngredientRole.INPUT, 15, 8).addIngredients(recipe.value().getIngredients().getFirst());
        builder.addSlot(RecipeIngredientRole.OUTPUT, 15, 38).addItemStack(recipe.value().getResultItem(null));
    }
}
