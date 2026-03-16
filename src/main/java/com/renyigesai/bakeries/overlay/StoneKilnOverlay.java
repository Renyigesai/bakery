package com.renyigesai.bakeries.overlay;

import com.mojang.blaze3d.systems.RenderSystem;
import com.renyigesai.bakeries.BakeriesMod;
import com.renyigesai.bakeries.block.stone_kiln.StoneKilnBlockEntity;
import com.renyigesai.bakeries.client.LookBlockEntityRegistries;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RenderGuiEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Map;
import java.util.UUID;

@OnlyIn(Dist.CLIENT)
public class  StoneKilnOverlay implements ILookOverlay<StoneKilnBlockEntity>{

    @Override
    public void create(RenderGuiEvent.Pre event, StoneKilnBlockEntity entity, Player localPlayer, Minecraft mc) {
        int w = event.getWindow().getGuiScaledWidth() / 2 - 56 - 23;
        int h = event.getWindow().getGuiScaledHeight() / 2 + 15;
        GuiGraphics guiGraphics = event.getGuiGraphics();
        if (localPlayer == null) {
            return;
        }
        Map<UUID, BlockEntity> blocks = LookBlockEntityRegistries.getBlocks();
        BlockEntity blockEntity = blocks.get(localPlayer.getUUID());
        if (blockEntity instanceof StoneKilnBlockEntity stoneKilnBlockEntity) {
            /*
             * pX 纹理显示的Y位置
             * pY 纹理显示的X位置
             * pUOffset 选定纹理X位置
             * pVOffset 选定纹理的Y位置
             * 组合起来为选定纹理的左上角位置
             * pWidth 选定纹理的裁剪显示宽度
             * pHeight 选定纹理的裁剪显示高度
             * 最后两个是纹理的宽和高
             * 主要是写给自己看的,老是记不住这个几个b参数
             * */
            float scale = 1.5f;
            guiGraphics.pose().pushPose();
            guiGraphics.pose().translate(w, h, 0); // 调整平移坐标
            guiGraphics.pose().scale(scale, scale, scale);
            guiGraphics.blit(new ResourceLocation("bakeries","textures/gui/stone_kiln_progress_bar.png"), 0, 0, 0, 0, 103, 40, 103, 46);
            int progressWidth = getProgressWidth(stoneKilnBlockEntity);
            guiGraphics.blit(new ResourceLocation("bakeries","textures/gui/stone_kiln_progress_bar.png"), 13, 19, 0, 40, progressWidth, 6, 103, 46);
            int maxTurnOver = stoneKilnBlockEntity.getMaxTurnOver();
            if (maxTurnOver != 0){
                String string = maxTurnOver + "/" + stoneKilnBlockEntity.getTurnOver();
                guiGraphics.drawString(mc.font, Component.nullToEmpty(string),44,9,16777215);
            }
            if (stoneKilnBlockEntity.isTurnOver()){
                guiGraphics.drawString(mc.font, Component.translatable("tip.bakeries.stone_kiln_stone_kiln_shovel"),25,35,16777215);
            }
            guiGraphics.pose().popPose();
        }
        RenderSystem.depthMask(true);
        RenderSystem.defaultBlendFunc();
        RenderSystem.enableDepthTest();
        RenderSystem.disableBlend();
        RenderSystem.setShaderColor(1, 1, 1, 1);
    }

    private static int getProgressWidth(StoneKilnBlockEntity stoneKilnBlockEntity) {
        if (stoneKilnBlockEntity.getMaxStageCookingTime() != 0) {
            return  80 * stoneKilnBlockEntity.getStageCookingTime() / stoneKilnBlockEntity.getMaxStageCookingTime();
        }else if (stoneKilnBlockEntity.getMaxCookingTime() != 0){
            return  80 * stoneKilnBlockEntity.getCookingTime() / stoneKilnBlockEntity.getMaxCookingTime();
        }else {
            return  0;
        }
    }

    @Override
    public boolean isOverlay(StoneKilnBlockEntity entity, Player localPlayer, Minecraft mc) {
        return true;
    }
}