package com.renyigesai.bakeries.event;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;

public record AnvilLandingEvent(Entity entity, Level level) {
}
