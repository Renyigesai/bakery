package com.renyigesai.bakeries.block.cake;

import com.renyigesai.bakeries.init.BakeriesMobEffects;
import net.minecraft.core.BlockPos;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class TiramisuBlock extends BCakeBlock{
    public TiramisuBlock() {
        super(BlockBehaviour.Properties.copy(Blocks.CAKE).lightLevel((l) -> 1));
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return box(3.0, 0.0, 3.0, 13.0, 7.0, 13.0);
    }

    @Override
    public void addEffect(Player pPlayer) {
        pPlayer.addEffect(new MobEffectInstance(BakeriesMobEffects.COCOA_MANIA.get(),1200));
        pPlayer.addEffect(new MobEffectInstance(BakeriesMobEffects.CHEESE_POWER.get(),1200));
        pPlayer.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED,1200));
    }

    @Override
    public int getFoodLevelModifier() {
        return 4;
    }

    @Override
    public float getSaturationLevelModifier() {
        return 0.5f;
    }
}
