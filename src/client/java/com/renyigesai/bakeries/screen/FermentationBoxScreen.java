package com.renyigesai.bakeries.screen;

import com.renyigesai.bakeries.BakeriesMod;
import com.renyigesai.bakeries.common.network.Messages;
import com.renyigesai.bakeries.menu.FermentationBoxMenu;
import io.netty.buffer.Unpooled;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class FermentationBoxScreen extends BaseMachineScreen<FermentationBoxMenu> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(BakeriesMod.MODID, "textures/gui/fermentation_box_gui.png");

    public FermentationBoxScreen(FermentationBoxMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title, TEXTURE);
    }

    @Override
    protected void init() {
        super.init();
        int leftPos = (this.width - this.imageWidth) / 2;
        int topPos = (this.height - this.imageHeight) / 2;

        this.addRenderableWidget(Button.builder(Component.literal("Reset"), btn -> sendFermentationAction(0))
                .pos(leftPos + 112, topPos + 12)
                .size(52, 16)
                .build());
    }

    private static void sendFermentationAction(int action) {
        FriendlyByteBuf buf = new FriendlyByteBuf(Unpooled.buffer());
        buf.writeInt(action);
        ClientPlayNetworking.send(Messages.FERMENTATION_BOX, buf);
    }
}
