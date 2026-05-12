package com.renyigesai.bakeries.common.mixin;

import com.renyigesai.bakeries.api.event.PlayerLookBlockEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.neoforged.neoforge.common.NeoForge;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(GameRenderer.class)
public abstract class GameRendererMixin {

    @Inject(method = "pick(F)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/profiling/ProfilerFiller;pop()V",shift = At.Shift.AFTER),locals = LocalCapture.CAPTURE_FAILSOFT)
    private void pick(float partialTicks, CallbackInfo ci, Entity entity, double d0, double d1){
        Minecraft mc = Minecraft.getInstance();
        HitResult hitResult = mc.hitResult;
        if (hitResult instanceof BlockHitResult blockHitResult) {
            BlockPos pos = blockHitResult.getBlockPos();
            Level level = mc.level;
            if (level != null) {
                BlockState state = level.getBlockState(pos);
                if (!state.isAir()){
                    PlayerLookBlockEvent playerLookBlockEvent = new PlayerLookBlockEvent(mc.player, pos,state);
                    NeoForge.EVENT_BUS.post(playerLookBlockEvent);
                }
            }
        }
    }
}
