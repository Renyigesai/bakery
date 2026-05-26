package com.renyigesai.bakeries.common.potion;

import net.minecraft.world.effect.MobEffectCategory;

public class CocoaManiaMobEffect extends BakeriesMobEffect {
    public CocoaManiaMobEffect() {
        super(MobEffectCategory.BENEFICIAL, -3972305);
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return amplifier >= 0;
    }
}
