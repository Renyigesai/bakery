 package com.renyigesai.bakeries.common.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

import java.util.List;

public class ReapCropBlock extends CropBlock {
    public ReapCropBlock(Properties p_52247_) {
        super(p_52247_);
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        int age = state.getValue(AGE);
        if (age == getMaxAge()) {
            boolean temp = false;
            if (level instanceof ServerLevel level1) {
                List<ItemStack> stacks = getDrops(state, level1, pos, null);
                if (!stacks.isEmpty()) {
                    for (ItemStack stack : stacks) {
                        popResource(level, pos, stack);
                    }
                    temp = true;
                }
            }
            if (temp) {
                level.setBlock(pos, state.setValue(AGE, 0), 3);
                level.playSound(null, pos, SoundEvents.PLAYER_HURT_SWEET_BERRY_BUSH, SoundSource.BLOCKS, 1.0F, 0.8F + level.random.nextFloat() * 0.4F);
                return InteractionResult.SUCCESS;
            }
        }
        return super.useWithoutItem(state, level, pos, player, hitResult);
    }

}
