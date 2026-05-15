package com.renyigesai.bakeries.event;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;

@SuppressWarnings("unused")
public record AnvilLandingEvent(Entity entity, Level level) {
}
