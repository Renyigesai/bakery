package com.renyigesai.bakeries.event;

import com.renyigesai.bakeries.api.event.PlayerLookBlockEvent;
import com.renyigesai.bakeries.block.glass_drink_cup.GlassDrinkCupBlockEntity;
import com.renyigesai.bakeries.init.BakeriesBlocks;
import com.renyigesai.bakeries.overlay.GlassDrinkCupOverlay;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class LookBlockEvent {
    @SubscribeEvent
    public static void onLookBlock(PlayerLookBlockEvent event){
        Level level = event.getPlayer().level();
        Player player = event.getPlayer();
        BlockState state = event.getBlockState();
        BlockPos blockPos = event.getBlockPos();
        if (state.getBlock() == BakeriesBlocks.DRINK_CUP.get()){
            GlassDrinkCupBlockEntity blockEntity = (GlassDrinkCupBlockEntity) level.getBlockEntity(blockPos);
            GlassDrinkCupOverlay.setVisible(player,blockEntity);
        }else {
            GlassDrinkCupOverlay.setVisible(player,null);
        }
    }
}
