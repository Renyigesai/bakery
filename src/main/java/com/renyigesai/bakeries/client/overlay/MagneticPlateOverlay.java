package com.renyigesai.bakeries.client.overlay;

import com.renyigesai.bakeries.BakeriesMod;
import com.renyigesai.bakeries.block.magnetic_plate.MagneticPlateBlockEntity;
import com.renyigesai.bakeries.util.measurer.ClientUtilsMeasurer;
import com.renyigesai.bakeries.util.measurer.IUtilsMeasurer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RenderGuiEvent;

@OnlyIn(Dist.CLIENT)
public class MagneticPlateOverlay implements ILookOverlay<MagneticPlateBlockEntity>{
    @Override
    public void create(RenderGuiEvent.Pre event, MagneticPlateBlockEntity mp, Player localPlayer, Minecraft mc) {
        int w = event.getWindow().getGuiScaledWidth() / 2;
        int h = event.getWindow().getGuiScaledHeight() / 2;
        GuiGraphics guiGraphics = event.getGuiGraphics();
        if (localPlayer == null) {
            return;
        }
        String text;
        Block block = BuiltInRegistries.BLOCK.get(ResourceLocation.tryParse(mp.getBlockId()));
        if (block.defaultBlockState().isAir()){
            text = Component.translatable("tip.bakeries.magnetic_plate_1").getString();
        }else {
            text = Component.translatable("tip.bakeries.magnetic_plate_2",BakeriesMod.getAuxiliaryKeyName()).getString();
        }
        if (!text.isEmpty()){
            IUtilsMeasurer utilsMeasurer = BakeriesMod.utilsMeasurer;
            if (utilsMeasurer instanceof ClientUtilsMeasurer clientUtilsMeasurer){
                int length = clientUtilsMeasurer.getPixelLength(text);
                guiGraphics.renderTooltip(mc.font,Component.literal(text),w - length / 2 - 8,h + 64);
            }

        }
    }

    /**暂不启用*/
    @Override
    public boolean isOverlay(MagneticPlateBlockEntity entity, Player localPlayer, Minecraft mc) {
        return false;
    }
}
