package com.renyigesai.bakeries.overlay;

import com.mojang.blaze3d.systems.RenderSystem;
import com.renyigesai.bakeries.BakeriesMod;
import com.renyigesai.bakeries.block.toaster.ToasterBlock;
import com.renyigesai.bakeries.block.toaster.ToasterBlockEntity;
import com.renyigesai.bakeries.client.LookBlockEntityRegistries;
import com.renyigesai.bakeries.util.TextUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RenderGuiEvent;

import java.util.Map;
import java.util.UUID;

@OnlyIn(Dist.CLIENT)
public class ToasterOverlay implements ILookOverlay<ToasterBlockEntity>{

    @Override
    public void create(RenderGuiEvent.Pre event, ToasterBlockEntity entity, Player localPlayer, Minecraft mc) {
        int w = event.getWindow().getGuiScaledWidth() / 2;
        int h = event.getWindow().getGuiScaledHeight() / 2;
        GuiGraphics guiGraphics = event.getGuiGraphics();
        if (localPlayer == null) {
            return;
        }
        Map<UUID, BlockEntity> blocks = LookBlockEntityRegistries.getBlocks();
        BlockEntity blockEntity = blocks.get(localPlayer.getUUID());
        if (blockEntity instanceof ToasterBlockEntity toaster) {
            String text = "";
            ToasterBlock.State state = toaster.getBlockState().getValue(ToasterBlock.STATE);
            if (!toaster.getItems().getStackInSlot(0).isEmpty() && state == ToasterBlock.State.IDLE){
                text = Component.translatable("tip.bakeries.toaster_1").getString();
            }else if (state == ToasterBlock.State.FINISH){
                String keyName = BakeriesMod.getAuxiliaryKeyName();
                text = Component.translatable("tip.bakeries.toaster_2",keyName).getString();
            }

            if (!text.isEmpty()){
                int length = TextUtils.getPixelLength(text);
                System.out.println(length);
                guiGraphics.renderTooltip(mc.font,Component.literal(text),w - length / 2 - 8,h + 64);
            }
        }
    }

    @Override
    public boolean isOverlay(ToasterBlockEntity entity, Player localPlayer, Minecraft mc) {
        return true;
    }
}
