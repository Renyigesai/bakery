package com.renyigesai.bakeries.block.cake;

import com.renyigesai.bakeries.init.BakeriesMobEffects;
import net.minecraft.core.BlockPos;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class CreamCakeBlock extends BCakeBlock{

    public CreamCakeBlock(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return box(3, 0, 3, 13, 7, 13);
    }

    @Override
    public void addEffect(Player pPlayer) {
        pPlayer.addEffect(new MobEffectInstance(BakeriesMobEffects.SOFT.get(),1200));
        pPlayer.addEffect(new MobEffectInstance(MobEffects.REGENERATION,1200));
    }

    @Override
    public float getSaturationLevelModifier() {
        return 0.4f;
    }

    @Override
    public int getFoodLevelModifier() {
        return 5;
    }
}
