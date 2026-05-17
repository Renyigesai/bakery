package com.renyigesai.bakeries;

import com.mojang.brigadier.CommandDispatcher;
import com.renyigesai.bakeries.block.entity.MachineBlockEntity;
import com.renyigesai.bakeries.menu.FermentationBoxMenu;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.core.BlockPos;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;

public final class BakeriesCommands {
    private BakeriesCommands() {
    }

    public static void init() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> register(dispatcher));
    }

    private static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal(BakeriesMod.MODID)
                .then(Commands.literal("refresh_temperature")
                        .requires(source -> source.hasPermission(2))
                        .executes(context -> refreshTemperature(context.getSource()))));
    }

    private static int refreshTemperature(CommandSourceStack source) {
        long day = source.getLevel().getDayTime() / 24000L;
        BakeriesMod.forceRefreshFloatingTemperature(day);
        forceRefreshOpenFermentationBox(source);
        int todayTemperature = MachineBlockEntity.calculateFermentationTemperature(
                source.getLevel(),
                BlockPos.containing(source.getPosition())
        );
        source.sendSuccess(() -> Component.literal("Bakeries today temperature refreshed. Local today temperature: "
                + todayTemperature + "\u00B0C (floating: " + BakeriesMod.floatingTemperature + "\u00B0C)"), true);
        return todayTemperature;
    }

    private static void forceRefreshOpenFermentationBox(CommandSourceStack source) {
        if (!(source.getEntity() instanceof ServerPlayer player)) {
            return;
        }
        if (player.containerMenu instanceof FermentationBoxMenu menu
                && menu.getContainer() instanceof MachineBlockEntity machine) {
            machine.forceRefreshFermentationTemperature();
            player.containerMenu.broadcastChanges();
        }
    }
}
