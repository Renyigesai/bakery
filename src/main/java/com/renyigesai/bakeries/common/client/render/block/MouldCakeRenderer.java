package com.renyigesai.bakeries.common.client.render.block;

import com.mojang.blaze3d.vertex.PoseStack;
import com.renyigesai.bakeries.common.blocks.mould_cake.MouldCakeBlockEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;

public class MouldCakeRenderer implements IBBlockEntityRenderer<MouldCakeBlockEntity> {

    public MouldCakeRenderer(BlockEntityRendererProvider.Context context) {

    }

    @Override
    public void startRender(@NotNull MouldCakeBlockEntity be, float v, @NotNull PoseStack poseStack, @NotNull MultiBufferSource multiBufferSource, int pPackedLight, int pPackedOverlay) {
        ItemStackHandler items = be.getItems();
        ItemStack cake = items.getStackInSlot(0);
        if (!cake.isEmpty()){
            Minecraft mc = Minecraft.getInstance();
            if (cake.getItem() instanceof BlockItem blockItem){
                poseStack.pushPose();
                poseStack.translate(-0.5,0.0625,-0.5);
                mc.getBlockRenderer().renderSingleBlock(blockItem.getBlock().defaultBlockState(),poseStack,multiBufferSource,pPackedLight,pPackedOverlay);
                poseStack.popPose();
            }
        }

    }
}
