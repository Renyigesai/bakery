package com.renyigesai.bakeries.block.cake;

import com.renyigesai.bakeries.api.LazyMobEffectInstance;
import com.renyigesai.bakeries.api.block.BCakeBlock;
import com.renyigesai.bakeries.init.BakeriesBlocks;
import com.renyigesai.bakeries.init.BakeriesItems;
import com.renyigesai.bakeries.init.BakeriesMobEffects;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.List;

public class CreamCakeBlock extends InstanceCakeBlock {


    public CreamCakeBlock(List<LazyMobEffectInstance> effects, int foodLevelModifier, float saturationLevelModifier) {
        super(effects, foodLevelModifier, saturationLevelModifier);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return box(3, 0, 3, 13, 7, 13);
    }

    @Override
    public void addEffect(LivingEntity entity) {
        entity.addEffect(new MobEffectInstance(BakeriesMobEffects.SOFT.get(),1200));
        entity.addEffect(new MobEffectInstance(MobEffects.REGENERATION,1200));
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        ItemStack hand = pPlayer.getItemInHand(pHand);
        int bites = pState.getValue(BITES);
        if (hand.is(BakeriesItems.CREAM_CAKE.get()) && bites == 0){
            Direction facing = pState.getValue(FACING);
            pLevel.setBlockAndUpdate(pPos, BakeriesBlocks.MULTI_LAYER_CREAM_CAKE.get().defaultBlockState().setValue(MultiLayerCreamCakeBlock.FACING,facing));
            if (!pPlayer.getAbilities().instabuild){
                hand.shrink(1);
            }
            return InteractionResult.SUCCESS;
        }
        return super.use(pState, pLevel, pPos, pPlayer, pHand, pHit);
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
