package com.renyigesai.bakeries.overlay;

import com.renyigesai.bakeries.init.BakeriesBlocks;
import com.renyigesai.bakeries.key.BakeriesKeyMapping;
import com.renyigesai.bakeries.block.entity.MachineBlockEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;

@SuppressWarnings("unused")
public final class ToasterOverlay implements ILookOverlay {
    @Override
    public void render(GuiGraphics graphics, int width, int height) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null || mc.level == null || mc.hitResult == null || mc.hitResult.getType() != HitResult.Type.BLOCK) {
            return;
        }

        BlockHitResult hit = (BlockHitResult) mc.hitResult;
        var state = mc.level.getBlockState(hit.getBlockPos());

        Component line1 = null;
        Component line2 = null;

        if (state.is(BakeriesBlocks.OVEN)) {
            line1 = Component.translatable("container.bakeries.oven");
            line2 = Component.translatable("overlay.bakeries.oven_hint");
        } else if (state.is(BakeriesBlocks.BLENDER)) {
            line1 = Component.translatable("container.bakeries.blender");
            line2 = Component.translatable("overlay.bakeries.blender_hint");
        } else if (state.is(BakeriesBlocks.FERMENTATION_BOX)) {
            line1 = Component.translatable("container.bakeries.fermentation_box");
            line2 = Component.translatable("overlay.bakeries.fermentation_hint");
        } else if (state.is(BakeriesBlocks.DOUGH_CRAFTING_TABLE)) {
            line1 = Component.translatable("container.bakeries.dough_crafting_table");
            line2 = Component.translatable("overlay.bakeries.dough_hint");
        } else if (state.is(BakeriesBlocks.CUPBOARD)) {
            line1 = Component.translatable("container.bakeries.cupboard");
            line2 = Component.translatable("overlay.bakeries.cupboard_hint");
        } else if (state.is(BakeriesBlocks.MIX_BLOCK)) {
            line1 = Component.translatable("container.bakeries.flour_sieve");
            line2 = Component.translatable("overlay.bakeries.flour_sieve_hint");
        } else if (state.is(BakeriesBlocks.MOKA_POT)) {
            line1 = Component.translatable("container.bakeries.drink");
            line2 = Component.translatable("overlay.bakeries.drink_hint");
        }

        if (line1 == null) {
            return;
        }

        int x = 8;
        int y = height - 34;
        graphics.drawString(mc.font, line1, x, y, 0xFFF0E6C8, true);
        graphics.drawString(mc.font, line2, x, y + 11, 0xFFB8D9FF, true);

        var be = mc.level.getBlockEntity(hit.getBlockPos());
        if (be instanceof MachineBlockEntity machine) {
            int max = machine.getOverlayMaxProgress();
            int cur = machine.getOverlayProgress();
            if (max > 0 && cur >= 0) {
                int percent = Math.min(100, Math.max(0, cur * 100 / max));
                Component progress = Component.translatable("overlay.bakeries.progress", percent);
                graphics.drawString(mc.font, progress, x, y + 22, 0xFF9BE29B, true);
            }
        }

        if (BakeriesKeyMapping.isAuxDown()) {
            graphics.drawString(mc.font, Component.translatable("overlay.bakeries.aux_on"), x, y - 11, 0xFFE8C15A, true);
        }
    }
}
