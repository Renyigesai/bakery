package com.renyigesai.bakeries.capabilities;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@SuppressWarnings("unused")
public final class PlayerKeyAuxiliary {
    private static final Map<UUID, PlayerKeyAuxiliary> STATES = new ConcurrentHashMap<>();
    private boolean keyDown;

    public boolean isKeyDown() {
        return keyDown;
    }

    public void setKeyDown(boolean keyDown) {
        this.keyDown = keyDown;
    }

    public static PlayerKeyAuxiliary of(UUID playerId) {
        return STATES.computeIfAbsent(playerId, id -> new PlayerKeyAuxiliary());
    }

    public static boolean isKeyDown(UUID playerId) {
        return of(playerId).isKeyDown();
    }

    public static void clear(UUID playerId) {
        STATES.remove(playerId);
    }
}
