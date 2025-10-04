package net.weibai.bakeries.data;

import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.damagesource.DamageType;
import net.weibai.bakeries.BakeriesMod;


public class MSModDamageTypes {
//    public static final ResourceKey<DamageType> IN_GOD_FIRE = createKey("in_god_fire");

    @SuppressWarnings("SameParameterValue")
    private static ResourceKey<DamageType> createKey(String pName) {
        return ResourceKey.create(Registries.DAMAGE_TYPE, BakeriesMod.rl(pName));
    }
    public static void bootstrap(BootstrapContext<DamageType> pContext) {
//        pContext.register(IN_GOD_FIRE, new DamageType("inGodFire", 0.1F, DamageEffects.BURNING));
    }
}
