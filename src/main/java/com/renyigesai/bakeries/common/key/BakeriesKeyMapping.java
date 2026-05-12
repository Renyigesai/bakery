package com.renyigesai.bakeries.common.key;

import com.renyigesai.bakeries.BakeriesMod;
import com.renyigesai.bakeries.common.network.to_server.KeyDownMessage;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import net.neoforged.neoforge.network.PacketDistributor;
import org.lwjgl.glfw.GLFW;


@EventBusSubscriber(value = Dist.CLIENT)
public class BakeriesKeyMapping {
    public static final KeyMapping AUXILIARY = new KeyMapping("key.bakeries.auxiliary",GLFW.GLFW_KEY_LEFT_SHIFT,"key.bakeries.bakeries"){
        private boolean isDownOld = false;
        @Override
        public void setDown(boolean isDown) {
            if (this.isDownOld == isDown){
                super.setDown(isDown);
                return;
            }
            super.setDown(isDown);
            if (Minecraft.getInstance().getConnection() != null) {
                int type = 1;
                if (isDownOld != isDown && isDown) {
                    type = 0;
                }
                PacketDistributor.sendToServer(new KeyDownMessage(type));
                isDownOld = isDown;
            }
        }
    };
    @SubscribeEvent
    public static void registerKeyMappings(RegisterKeyMappingsEvent event) {
        event.register(AUXILIARY);
    }
}
