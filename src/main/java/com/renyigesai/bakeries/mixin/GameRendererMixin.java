package com.renyigesai.bakeries.mixin;

import com.renyigesai.bakeries.api.event.PlayerLookBlockEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.common.MinecraftForge;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GameRenderer.class)
public abstract class GameRendererMixin {
    @Inject(method = "pick", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/phys/HitResult;getType()Lnet/minecraft/world/phys/HitResult$Type;", shift = At.Shift.AFTER))
    private void pick(CallbackInfo ci){
        try {
            Minecraft mc = Minecraft.getInstance();
            if (mc.hitResult instanceof BlockHitResult blockHitResult) {
                BlockPos pos = blockHitResult.getBlockPos();
                Level level = mc.level;
                if (level != null) {
                    BlockState state = level.getBlockState(pos);
                    if (!state.isAir()){
                        PlayerLookBlockEvent playerLookBlockEvent = new PlayerLookBlockEvent(mc.player, pos,state);
                        MinecraftForge.EVENT_BUS.post(playerLookBlockEvent);
                    }
                }
            }
        }catch (Exception e){

        }

    }
}
