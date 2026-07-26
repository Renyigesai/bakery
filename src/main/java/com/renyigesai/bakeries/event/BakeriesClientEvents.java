package com.renyigesai.bakeries.event;

import com.renyigesai.bakeries.client.LookBlockEntityRegistries;
import com.renyigesai.bakeries.client.overlay.ILookOverlay;
import com.renyigesai.bakeries.util.measurer.CakePartMeasurer;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ClientPlayerNetworkEvent;
import net.minecraftforge.client.event.RenderGuiEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber({Dist.CLIENT})
public class BakeriesClientEvents {
    @SubscribeEvent
    public static void onPlayerLogout(ClientPlayerNetworkEvent.LoggingOut event) {
        LocalPlayer player = event.getPlayer();
        if (player != null) {
            LookBlockEntityRegistries.getBlocks().remove(player.getUUID());
        }
    }

    @SubscribeEvent(priority = EventPriority.NORMAL)
    public static void addOverlay(RenderGuiEvent.Pre event){
        Minecraft mc = Minecraft.getInstance();
        Player localPlayer = mc.player;
        if (localPlayer == null){
            return;
        }
        BlockPos blockPos = LookBlockEntityRegistries.getBlocks().get(localPlayer.getUUID());
        if (blockPos != null){
            BlockEntity blockEntity = localPlayer.level().getBlockEntity(blockPos);
            if (blockEntity != null){
                ILookOverlay iLookOverlay = LookBlockEntityRegistries.getRegister().get(blockEntity.getClass());
                if (iLookOverlay != null) {
                    if (iLookOverlay.isOverlay(blockEntity,localPlayer,mc)) {
                        iLookOverlay.create(event, blockEntity, localPlayer, mc);
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public static void onItemTooltip(ItemTooltipEvent event){
        CakePartMeasurer.getClientPartsType().forEach((key, value) -> {
            Item item = BuiltInRegistries.ITEM.get(key);
            if (event.getItemStack().is(item)){
                event.getToolTip().add(Component.translatable("tooltip.bakeries.cake_part",CakePartMeasurer.getPartTypeName(value)).withStyle(ChatFormatting.BLUE));
            }
        });
    }

}
