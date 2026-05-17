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
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.stats.Stats;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("unused")
public class OvenRecipeCategory implements IRecipeCategory<SimpleMachineRecipe> {
    public static final RecipeType<SimpleMachineRecipe> TYPE =
            RecipeType.create(BakeriesMod.MODID, "oven", SimpleMachineRecipe.class);

    private static final ResourceLocation TEXTURE = new ResourceLocation(BakeriesMod.MODID, "textures/gui/jei_oven_gui.png");
    private final IDrawable background;
    private final IDrawable icon;

    public OvenRecipeCategory(IGuiHelper guiHelper) {
        this.background = guiHelper.createDrawable(TEXTURE, 0, 0, 58, 63);
        this.icon = guiHelper.createDrawableItemStack(new ItemStack(BakeriesBlocks.OVEN));
    }

    @Override
    public @NotNull RecipeType<SimpleMachineRecipe> getRecipeType() {
        return TYPE;
    }

    @Override
    public @NotNull Component getTitle() {
        return Component.translatable("container.bakeries.oven");
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
        if (mouseX >= 44 && mouseX <= 53 && mouseY >= 7 && mouseY <= 55) {
            renderTemperatureTooltip(guiGraphics, mouseX, mouseY, recipe);
        }
    }

    private void renderTemperatureTooltip(GuiGraphics guiGraphics, double mouseX, double mouseY, SimpleMachineRecipe recipe) {
        Minecraft minecraft = Minecraft.getInstance();
        if (minecraft.player == null) {
            return;
        }
        String min = recipe.getMinTemperature() >= 0 ? String.valueOf(recipe.getMinTemperature()) : "--";
        String max = recipe.getMaxTemperature() >= 0 ? String.valueOf(recipe.getMaxTemperature()) : "--";
        java.util.List<Component> tooltip = new java.util.ArrayList<>();
        tooltip.add(Component.literal("Min " + min + "\u00B0C").withStyle(ChatFormatting.BLUE));
        tooltip.add(Component.literal("Max " + max + "\u00B0C").withStyle(ChatFormatting.BLUE));
        if (recipe.getPerfectTemperature() >= 0 && hasEatenResult(minecraft, recipe)) {
            tooltip.add(Component.translatable("tooltips.bakeries.pile_item_perfect")
                    .append(Component.literal(" " + recipe.getPerfectTemperature() + "\u00B0C"))
                    .withStyle(ChatFormatting.GOLD));
        }
        if (recipe.getCraftTime() > 0) {
            tooltip.add(Component.literal(recipe.getCraftTime() + " tick").withStyle(ChatFormatting.GRAY));
        }
        guiGraphics.renderComponentTooltip(minecraft.font, tooltip, (int) mouseX, (int) mouseY);
    }

    private static boolean hasEatenResult(Minecraft minecraft, SimpleMachineRecipe recipe) {
        if (minecraft.player == null || minecraft.level == null) {
            return false;
        }
        ItemStack result = recipe.getResultItem(minecraft.level.registryAccess());
        return !result.isEmpty() && minecraft.player.getStats().getValue(Stats.ITEM_USED.get(result.getItem())) > 0;
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, SimpleMachineRecipe recipe, IFocusGroup focuses) {
        builder.addSlot(RecipeIngredientRole.INPUT, 15, 8).addIngredients(recipe.getIngredient());
        builder.addSlot(RecipeIngredientRole.OUTPUT, 15, 38).addItemStack(recipe.getResultItem(net.minecraft.core.RegistryAccess.EMPTY));
    }
}
