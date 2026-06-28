package com.renyigesai.bakeries.common.overlay;

import com.mojang.blaze3d.systems.RenderSystem;
import com.renyigesai.bakeries.BakeriesMod;
import com.renyigesai.bakeries.common.blocks.toaster.ToasterBlock;
import com.renyigesai.bakeries.common.blocks.toaster.ToasterBlockEntity;
import com.renyigesai.bakeries.common.utils.TextUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.neoforge.client.event.RenderGuiEvent;

public class ToasterOverlay implements ILookOverlay<ToasterBlockEntity>{

    @Override
    public void create(RenderGuiEvent.Pre event, ToasterBlockEntity entity, Player localPlayer, Minecraft mc) {
        GuiGraphics guiGraphics = event.getGuiGraphics();
        int w = mc.getWindow().getGuiScaledWidth() / 2;
        int h = mc.getWindow().getGuiScaledHeight() / 2;
        if (localPlayer == null) {
            return;
        }
        String text = "";
        ToasterBlock.State state = entity.getBlockState().getValue(ToasterBlock.STATE);
        if (!entity.getItems().getStackInSlot(0).isEmpty() && state == ToasterBlock.State.IDLE){
            text = Component.translatable("tooltips.bakeries.toaster_0").getString();
        }else if (state == ToasterBlock.State.FINISH){
            text = Component.translatable("tooltips.bakeries.toaster_1", BakeriesMod.getAuxiliaryKeyName()).getString();
        }
        if (!text.isEmpty()){
            int length = BakeriesMod.clientUtilsMeasurer.getLength(text);
            guiGraphics.renderTooltip(mc.font,Component.literal(text),w - length / 2 - 8,h + 64);
        }
    }

    @Override
    public boolean isOverlay(ToasterBlockEntity entity, Player localPlayer, Minecraft mc) {
        return true;
    }
}
