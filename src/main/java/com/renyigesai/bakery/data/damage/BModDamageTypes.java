package com.renyigesai.bakery.data.damage;

import com.renyigesai.bakery.BakeryMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.damagesource.DamageEffects;
import net.minecraft.world.damagesource.DamageType;

public class BModDamageTypes {
    public static final ResourceKey<DamageType> IN_GOD_FIRE = createKey("in_god_fire");

    @SuppressWarnings("SameParameterValue")
    private static ResourceKey<DamageType> createKey(String pName) {
        return ResourceKey.create(Registries.DAMAGE_TYPE, BakeryMod.prefix(pName));
    }
    public static void bootstrap(BootstapContext<DamageType> pContext) {
        pContext.register(IN_GOD_FIRE, new DamageType("inGodFire", 0.1F, DamageEffects.BURNING));
    }
}
