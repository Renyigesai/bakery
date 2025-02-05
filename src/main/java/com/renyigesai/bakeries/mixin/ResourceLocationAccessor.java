package com.renyigesai.bakeries.mixin;

import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(ResourceLocation.class)
public abstract class ResourceLocationAccessor {
//    @Inject(method = "decompose", at = @At("HEAD"))
//    private static void callDecompose(String pLocation, char pSeparator, CallbackInfoReturnable<String[]> cir) {
//        System.out.println(pLocation);
//    }
}
