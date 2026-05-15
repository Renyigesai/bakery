package com.renyigesai.bakeries.event;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

@SuppressWarnings("unused")
public final class BakeriesEventHooks {
    private static final List<Consumer<AnvilLandingEvent>> ANVIL_LANDING_LISTENERS = new ArrayList<>();
    private static final List<Consumer<SnifferDropSeedEvent>> SNIFFER_DROP_SEED_LISTENERS = new ArrayList<>();
    private static final List<Consumer<PlayerLookBlockEvent>> PLAYER_LOOK_BLOCK_LISTENERS = new ArrayList<>();

    private BakeriesEventHooks() {
    }

    public static void onAnvilLanding(Consumer<AnvilLandingEvent> listener) {
        ANVIL_LANDING_LISTENERS.add(listener);
    }

    public static void onSnifferDropSeed(Consumer<SnifferDropSeedEvent> listener) {
        SNIFFER_DROP_SEED_LISTENERS.add(listener);
    }

    public static void onPlayerLookBlock(Consumer<PlayerLookBlockEvent> listener) {
        PLAYER_LOOK_BLOCK_LISTENERS.add(listener);
    }

    public static void fireAnvilLanding(AnvilLandingEvent event) {
        ANVIL_LANDING_LISTENERS.forEach(listener -> listener.accept(event));
    }

    public static void fireSnifferDropSeed(SnifferDropSeedEvent event) {
        SNIFFER_DROP_SEED_LISTENERS.forEach(listener -> listener.accept(event));
    }

    public static void firePlayerLookBlock(PlayerLookBlockEvent event) {
        PLAYER_LOOK_BLOCK_LISTENERS.forEach(listener -> listener.accept(event));
    }
}
