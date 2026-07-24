package com.renyigesai.bakeries.client.overlay;

import com.mojang.blaze3d.systems.RenderSystem;
import com.renyigesai.bakeries.block.glass_drink_cup.GlassDrinkCupBlockEntity;
import com.renyigesai.bakeries.client.LookBlockEntityRegistries;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RenderGuiEvent;

import java.util.Map;
import java.util.UUID;

@OnlyIn(Dist.CLIENT)
public class GlassDrinkCupOverlay implements ILookOverlay<GlassDrinkCupBlockEntity>{
    @Override
    public void create(RenderGuiEvent.Pre event, GlassDrinkCupBlockEntity entity, Player localPlayer, Minecraft mc) {
        int w = event.getWindow().getGuiScaledWidth() / 2 - 71;
        int h = event.getWindow().getGuiScaledHeight() / 2 + 50;
        GuiGraphics guiGraphics = event.getGuiGraphics();
        if (localPlayer == null) {
            return;
        }
        guiGraphics.blit(new ResourceLocation("bakeries","textures/gui/glass_drink_cup_overlay.png"), w, h, 0, 0, 142, 22, 142, 22);
        int x = w + 3;
        int y = h + 3;
        for (int i = 0; i < 4; ++i) {
            guiGraphics.renderItem(entity.getInventory().getStackInSlot(i),x + (i * 24),y, -1);
        }
        guiGraphics.renderItem(entity.getInventory().getStackInSlot(4),x + (5 * 24),y, -1);

        RenderSystem.depthMask(true);
        RenderSystem.defaultBlendFunc();
        RenderSystem.enableDepthTest();
        RenderSystem.disableBlend();
        RenderSystem.setShaderColor(1, 1, 1, 1);
    }

    @Override
    public boolean isOverlay(GlassDrinkCupBlockEntity entity, Player localPlayer, Minecraft mc) {
        return true;
    }
}
