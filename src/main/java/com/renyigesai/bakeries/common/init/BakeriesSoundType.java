package com.renyigesai.bakeries.common.init;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.block.SoundType;

public class BakeriesSoundType {
    public static final SoundType PASTRY = new SoundType(1.0F, 1.0F, SoundEvents.WOOL_BREAK, SoundEvents.WOOL_STEP, BakeriesSounds.PASTRY_PLACE.get(), SoundEvents.WOOL_HIT, SoundEvents.WOOL_FALL);
}
