package com.renyigesai.bakeries.block.cake;

import com.renyigesai.bakeries.api.block.BCakeBlock;
import com.renyigesai.bakeries.init.BakeriesMobEffects;
import net.minecraft.core.BlockPos;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class BasqueCakeBlock extends BCakeBlock {
    public BasqueCakeBlock() {
        super(BlockBehaviour.Properties.copy(Blocks.CAKE));
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return box(3.0, 0.0, 3.0, 13.0, 4.0, 13.0);
    }

    @Override
    public int getFoodLevelModifier() {
        return 6;
    }

    @Override
    public float getSaturationLevelModifier() {
        return 0.5f;
    }

    @Override
    public void addEffect(LivingEntity entity) {
        entity.addEffect(new MobEffectInstance(BakeriesMobEffects.CHEESE_POWER.get(),1200));
    }
}
