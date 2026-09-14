package com.renyigesai.bakeries.mixin;

import com.google.gson.JsonObject;
import net.minecraft.client.renderer.block.model.BlockElement;
import net.minecraft.util.GsonHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BlockElement.Deserializer.class)
public class BlockElementDeserializerMixin {

    /**去掉Json模型角度限制*/
//    @Inject(method = "getAngle",at = @At("HEAD"), cancellable = true)
//    private void onGetAngle(JsonObject pJson, CallbackInfoReturnable<Float> cir) {
//        float f = GsonHelper.getAsFloat(pJson, "angle");
//        cir.setReturnValue(f);
//    }
}
