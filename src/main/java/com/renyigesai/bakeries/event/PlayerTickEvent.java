package com.renyigesai.bakeries.event;

import com.renyigesai.bakeries.BakeriesMod;
import com.renyigesai.bakeries.api.event.PlayerLookBlockEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = BakeriesMod.MODID,value = Dist.CLIENT)
public class PlayerTickEvent {
    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent playerTickEvent){
        if (playerTickEvent.phase == TickEvent.Phase.START && playerTickEvent.player.level().isClientSide) {
            HitResult hitResult = Minecraft.getInstance().hitResult;
            Player player = playerTickEvent.player;
            Level level = player.level();
            if (hitResult instanceof BlockHitResult blockHit) {
                BlockPos pos = blockHit.getBlockPos();
                BlockState state = level.getBlockState(pos);
                PlayerLookBlockEvent playerLookBlockEvent = new PlayerLookBlockEvent(player, pos, state);
                MinecraftForge.EVENT_BUS.post(playerLookBlockEvent);
            }
        }
    }
}
