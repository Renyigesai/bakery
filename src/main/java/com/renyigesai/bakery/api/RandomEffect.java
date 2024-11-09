package com.renyigesai.bakery.api;

import com.renyigesai.bakery.init.BakeryItems;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.ItemStack;

public class RandomEffect {

    public MobEffectInstance getRandomEffect(){
        if (Mth.nextInt(RandomSource.create(), 1, 2) == 1){
            return new MobEffectInstance(MobEffects.DAMAGE_BOOST, 600, 0);
        }else {
            return new MobEffectInstance(MobEffects.SATURATION, 600, 0);
        }
    }

}
