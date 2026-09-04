package com.renyigesai.bakeries.common.mixin;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.advancements.AdvancementTab;
import net.minecraft.client.gui.screens.advancements.AdvancementWidget;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AdvancementTab.class)
public class AdvancementTabMixin {


    @Shadow private boolean centered;

    @Shadow private double scrollX;

    @Shadow private double scrollY;

    @Shadow private int maxX;

    @Shadow private int minX;

    @Shadow private int maxY;

    @Shadow private int minY;

    @Shadow @Final private AdvancementWidget root;

    @Inject(method = "drawContents",at = @At(value = "HEAD"), cancellable = true)
    private void onContents(GuiGraphics guiGraphics, int x, int y, CallbackInfo ci){
//        if (!this.centered) {
//            this.scrollX = (double)(117 - (this.maxX + this.minX) / 2);
//            this.scrollY = (double)(56 - (this.maxY + this.minY) / 2);
//            this.centered = true;
//        }
//        guiGraphics.enableScissor(x, y, x + 234, y + 113);
//        guiGraphics.pose().pushPose();
//        guiGraphics.pose().translate((float)x, (float)y, 0.0F);
//        int i = Mth.floor(this.scrollX);
//        int j = Mth.floor(this.scrollY);
//
//        /*这里可以插入自定义背景图*/
//
//        this.root.drawConnectivity(guiGraphics, i, j, true);
//        this.root.drawConnectivity(guiGraphics, i, j, false);
//        this.root.draw(guiGraphics, i, j);
//        guiGraphics.pose().popPose();
//        guiGraphics.disableScissor();
//        ci.cancel();
    }
}
