package com.renyigesai.bakeries.common.blocks;

import com.renyigesai.bakeries.common.init.BakeriesBlocks;
import com.renyigesai.bakeries.common.init.BakeriesItems;
import com.renyigesai.bakeries.common.utils.ItemUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

public class CheeseTankBkock extends TankBlock {
    public CheeseTankBkock(Properties pProperties) {
        super(pProperties);
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        return ladleOut(level,pos,player);
    }

    public InteractionResult ladleOut(Level pLevel, BlockPos pPos, Player pPlayer){
        ItemUtil.givePlayerItem(pPlayer,new ItemStack(BakeriesItems.CHEESE_CUBE.get(),4));
        pLevel.setBlockAndUpdate(pPos, BakeriesBlocks.FERMENTATION_TANK.get().defaultBlockState());
        return InteractionResult.SUCCESS;
    }
}
