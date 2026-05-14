package com.renyigesai.bakeries.mixin;

import com.renyigesai.bakeries.event.BakeriesEventHooks;
import com.renyigesai.bakeries.event.PlayerLookBlockEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GameRenderer.class)
public abstract class GameRendererMixin {
    @Inject(method = "pick(F)V", at = @At("TAIL"))
    private void bakeries$pick(float partialTicks, CallbackInfo ci) {
        Minecraft mc = Minecraft.getInstance();
        HitResult hitResult = mc.hitResult;
        if (!(hitResult instanceof BlockHitResult blockHitResult)) {
            return;
        }
        Level level = mc.level;
        if (level == null || mc.player == null) {
            return;
        }
        BlockPos pos = blockHitResult.getBlockPos();
        BlockState state = level.getBlockState(pos);
        if (state.isAir()) {
            return;
        }
        BakeriesEventHooks.firePlayerLookBlock(new PlayerLookBlockEvent(mc.player, pos, state));
    }
}
