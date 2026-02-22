package com.renyigesai.bakeries.key;

import com.renyigesai.bakeries.network.KeyAuxiliaryMessage;
import com.renyigesai.bakeries.network.Messages;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.lwjgl.glfw.GLFW;


@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, value = {Dist.CLIENT})
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
                Messages.sendToServer(new KeyAuxiliaryMessage(type));
                isDownOld = isDown;
            }
        }
    };
    @SubscribeEvent
    public static void registerKeyMappings(RegisterKeyMappingsEvent event) {
        event.register(AUXILIARY);
    }
}
