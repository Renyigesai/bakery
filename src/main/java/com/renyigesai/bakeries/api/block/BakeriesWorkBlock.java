package com.renyigesai.bakeries.api.block;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.network.NetworkHooks;

public interface BakeriesWorkBlock {

    SoundEvent getOpenSound();
    SoundEvent getCloseSound();

    static void openScreen(ServerPlayer serverPlayer, MenuProvider container, BlockPos pos, Level level, BlockState state){
        NetworkHooks.openScreen(serverPlayer, container, (buf) -> {
            buf.writeBlockPos(pos);
            level.blockEvent(pos,state.getBlock(),0,0);
        });
    }

    static void openScreen(ServerPlayer serverPlayer, MenuProvider container, BlockPos pos, Level level, BlockState state, SoundEvent soundEvent){
        openScreen(serverPlayer, container, pos, level, state);
        if (level instanceof ServerLevel serverLevel) {
            serverLevel.playSound(null, pos, soundEvent, SoundSource.BLOCKS);
        }
    }
}
