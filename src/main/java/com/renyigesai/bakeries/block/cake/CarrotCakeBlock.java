package com.renyigesai.bakeries.block.cake;

import com.renyigesai.bakeries.api.block.BCakeBlock;
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

public class CarrotCakeBlock extends BCakeBlock {
    public CarrotCakeBlock() {
        super(BlockBehaviour.Properties.copy(Blocks.CAKE).lightLevel((l) -> 1));
    }
    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return box(3.0, 0.0, 3.0, 13.0, 7.0, 13.0);
    }

    @Override
    public float getSaturationLevelModifier() {
        return 0.25f;
    }

    @Override
    public int getFoodLevelModifier() {
        return 10;
    }

    @Override
    public void addEffect(Player pPlayer) {
        pPlayer.addEffect(new MobEffectInstance(MobEffects.NIGHT_VISION,1200));
    }
}
