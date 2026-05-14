package com.renyigesai.bakeries.overlay;

import com.renyigesai.bakeries.key.BakeriesKeyMapping;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;

public final class ToasterOverlay implements ILookOverlay {
    @Override
    public void render(GuiGraphics graphics, int width, int height) {
        if (!BakeriesKeyMapping.isAuxDown()) {
            return;
        }
        graphics.drawString(
                net.minecraft.client.Minecraft.getInstance().font,
                Component.literal("Bakeries AUX: ON"),
                8,
                height - 20,
                0xFFE8C15A,
                true
        );
    }
}
