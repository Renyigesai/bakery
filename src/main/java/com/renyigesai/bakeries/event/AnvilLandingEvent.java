package com.renyigesai.bakeries.event;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;

public class AnvilLandingEvent {
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
