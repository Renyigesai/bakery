package com.renyigesai.bakeries.client.render.block;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.renyigesai.bakeries.block.mix_block.MixBlock;
import com.renyigesai.bakeries.block.mix_block.MixBlockEntity;
import com.renyigesai.bakeries.init.BakeriesBlocks;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.font.glyphs.BakedGlyph;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec2;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

@OnlyIn(value = Dist.CLIENT)
public class MixBlockEntityRender implements IBBlockEntityRenderer<MixBlockEntity> {
    public static final float ADD_SIZE = 0.25f;
    public static final Vec2[][] VEC2S = {
            new Vec2[]{},
            new Vec2[]{new Vec2(0.5f, 0.5f)},
            new Vec2[]{new Vec2(0.5f - ADD_SIZE, 0.5f), new Vec2(0.5f + ADD_SIZE, 0.5f)},
            new Vec2[]{new Vec2(0.5f - ADD_SIZE, 0.5f - ADD_SIZE), new Vec2(0.5f + ADD_SIZE, 0.5f - ADD_SIZE), new Vec2(0.5f, 0.5f + ADD_SIZE)},
            new Vec2[]{new Vec2(0.5f - ADD_SIZE, 0.5f - ADD_SIZE), new Vec2(0.5f + ADD_SIZE, 0.5f - ADD_SIZE),new Vec2(0.5f - ADD_SIZE, 0.5f + ADD_SIZE), new Vec2(0.5f + ADD_SIZE, 0.5f + ADD_SIZE)}
    };
    public static final float textScale = 0.01f;
    private final Font font;

    public MixBlockEntityRender(BlockEntityRendererProvider.Context context) {
        this.font = context.getFont();
    }

    @Override
    public void startRender(@NotNull MixBlockEntity entity, float v, @NotNull PoseStack poseStack, @NotNull MultiBufferSource pBuffer, int pPackedLight, int pPackedOverlay) {
        if (entity.isEmpty()) {
            return;
        }

        int count = entity.getInventoryCount();
        if (count <= 0 || count >= VEC2S.length) {
            return;
        }

        Vec2[] positions = VEC2S[count];
        List<ItemStack> itemsToRender = new ArrayList<>();
        for (int i = 0; i < entity.getInventory().getSlots(); i++) {
            ItemStack stack = entity.getInventory().getStackInSlot(i);
            if (!stack.isEmpty()) {
                itemsToRender.add(stack);
            }
        }

        boolean isTray = entity.getBlockState().getValue(MixBlock.TRAY);

        for (int i = 0; i < itemsToRender.size() && i < positions.length; i++) {
            renderItem(itemsToRender.get(i), entity, positions[i], poseStack, pBuffer, pPackedLight, pPackedOverlay, isTray);
        }

        if (isTray) {
            renderTray(entity, poseStack, pBuffer, pPackedLight, pPackedOverlay);
        }

        if (entity.getText() != null && !entity.getText().isEmpty()) {
            renderText(entity, poseStack, pBuffer, pPackedLight,isTray);
        }
    }

    private void renderItem(ItemStack stack, MixBlockEntity entity, Vec2 position, PoseStack poseStack, MultiBufferSource pBuffer, int pPackedLight, int pPackedOverlay, boolean isTray) {
        poseStack.pushPose();
        float offsetX = position.x - 0.5f;
        float offsetZ = position.y - 0.5f;
        poseStack.translate(offsetX, (isTray ? 0.0625 : 0), offsetZ);
        poseStack.mulPose(Axis.YP.rotationDegrees( 15));
        if (entity.getLevel() != null) {
            boolean isBlock = stack.getItem() instanceof BlockItem;
            if (isBlock) {
                poseStack.pushPose();
                poseStack.translate(-0.5f, 0f, -0.5f);
                BlockState state = ((BlockItem) stack.getItem()).getBlock().defaultBlockState();
                Minecraft.getInstance().getBlockRenderer().renderSingleBlock(state, poseStack, pBuffer, pPackedLight, pPackedOverlay);
                poseStack.popPose();
            } else {
                Minecraft.getInstance().getItemRenderer().renderStatic(stack, ItemDisplayContext.FIXED, pPackedLight, pPackedOverlay, poseStack, pBuffer, entity.getLevel(), (int) entity.getBlockPos().asLong());
            }
        }
        poseStack.popPose();
    }

    private void renderTray(MixBlockEntity entity, PoseStack poseStack, MultiBufferSource pBuffer, int pPackedLight, int pPackedOverlay) {
        BlockState state = BakeriesBlocks.WOOD_TRAY.get().defaultBlockState();
        poseStack.pushPose();
        poseStack.translate(-0.5f, 0f, -0.5f);
        Minecraft.getInstance().getBlockRenderer().renderSingleBlock(state, poseStack, pBuffer, pPackedLight, pPackedOverlay);
        poseStack.popPose();
    }

    private void renderText(MixBlockEntity entity, PoseStack poseStack, MultiBufferSource pBuffer, int pPackedLight,boolean isTray) {
        String text = entity.getText();
        if (text == null || text.isEmpty()) {
            return;
        }

        int textWidth = font.width(text);
        int color = entity.getColor();

        poseStack.pushPose();
        poseStack.translate(0f, -0.25f + (isTray ? 0.0625f : 0f), 0f);
        poseStack.scale(textScale, -textScale, textScale);
        poseStack.mulPose(Axis.XP.rotationDegrees(45.0f));
        poseStack.translate(0f, 0f, 0.5f / textScale);
        startRender(text, textWidth, color, poseStack, pBuffer);
        poseStack.popPose();
    }

    private void startRender(String text, int textWidth, int color, PoseStack poseStack, MultiBufferSource pBuffer) {
        float x = 0.5f / textScale - textWidth;
        font.drawInBatch(Component.literal(text).withStyle(ChatFormatting.BOLD), x, 1, color, false,
                poseStack.last().pose(), pBuffer, Font.DisplayMode.NORMAL, 0, 15728880);
        if (pBuffer instanceof MultiBufferSource.BufferSource) {
            BakedGlyph texturedglyph = font.getFontSet(Style.DEFAULT_FONT).whiteGlyph();
            ((MultiBufferSource.BufferSource) pBuffer).endBatch(texturedglyph.renderType(Font.DisplayMode.NORMAL));
        }
    }
}
