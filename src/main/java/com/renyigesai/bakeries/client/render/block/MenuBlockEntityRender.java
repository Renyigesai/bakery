package com.renyigesai.bakeries.client.render.block;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.renyigesai.bakeries.block.menu.MenuBlockEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.Vec2;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;

public class MenuBlockEntityRender implements IBBlockEntityRenderer<MenuBlockEntity> {

    public MenuBlockEntityRender(BlockEntityRendererProvider.Context context){

    }

    @Override
    public void startRender(@NotNull MenuBlockEntity be, float v, @NotNull PoseStack poseStack, @NotNull MultiBufferSource multiBufferSource, int pPackedLight, int pPackedOverlay) {
        ItemStackHandler inventory = be.getInventory();
        int posLong = (int) be.getBlockPos().asLong();
        ItemStack stackInSlot = inventory.getStackInSlot(0);
        if (!stackInSlot.isEmpty()){
            poseStack.pushPose();
            poseStack.translate(0,0.3125,-0.40625);
            oppositeY(be, v, poseStack, multiBufferSource, pPackedLight, pPackedOverlay);
            float scale = 0.55f;
            poseStack.scale(scale,scale,scale);
            if (be.getLevel() != null) {
                Minecraft.getInstance().getItemRenderer().renderStatic(stackInSlot, ItemDisplayContext.FIXED, LevelRenderer.getLightColor(be.getLevel(), be.getBlockPos()), pPackedOverlay, poseStack, multiBufferSource, be.getLevel(), (int) (posLong + 1));
            }
            poseStack.popPose();
        }
    }

}
