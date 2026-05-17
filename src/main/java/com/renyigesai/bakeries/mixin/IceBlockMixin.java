package com.renyigesai.bakeries.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.IceBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(IceBlock.class)
public abstract class IceBlockMixin {
    @Redirect(
            method = "playerDestroy",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/level/Level;setBlockAndUpdate(Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;)Z"
            )
    )
    @SuppressWarnings("unused") // Called by Mixin redirect target.
    private boolean bakeries$preventIceWater(Level level, BlockPos pos, net.minecraft.world.level.block.state.BlockState state) {
        // Only prevent normal ice from turning into water when broken.
        if (state.is(Blocks.WATER)) {
            return level.getBlockState(pos).isAir();
        }
        return level.setBlockAndUpdate(pos, state);
    }
}
