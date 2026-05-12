package com.renyigesai.bakeries.api.event;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.Event;

public class AnvilLandingEvent extends Event {
    private final Entity entity;
    private final Level level;

    public AnvilLandingEvent(Entity entity, Level level) {
        this.entity = entity;
        this.level = level;
    }

    public Entity getEntity() {
        return entity;
    }

    public Level getLevel() {
        return level;
    }
}
