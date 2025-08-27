package com.renyigesai.bakeries.mixin;

import com.renyigesai.bakeries.accessor.VillagerAccessor;
import net.minecraft.world.entity.npc.Villager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

@Mixin(Villager.class)
public class VillagerMixin implements VillagerAccessor {
    @Shadow private int numberOfRestocksToday;

    @Unique
    @Override
    public void bakery$setNumberOfRestocksToday(int value) {
        numberOfRestocksToday = Math.max(value, 0);
    }
}
