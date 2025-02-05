package com.renyigesai.bakeries.fluid;

import com.renyigesai.bakeries.init.BakeriesBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import org.jetbrains.annotations.NotNull;

//待优化
public class SaltWaterFluidsBlock extends LiquidBlock {

    public SaltWaterFluidsBlock() {
        super(BakeriesFluids.SALT_WATER,
                Properties.of().mapColor(MapColor.WATER).strength(100f)
                        .noCollission().noLootTable().liquid().pushReaction(PushReaction.DESTROY).sound(SoundType.EMPTY).replaceable());
    }

    @Override
    public boolean isRandomlyTicking(@NotNull BlockState pState) {
        return true;
    }

    @Override
    public void randomTick(@NotNull BlockState pState, ServerLevel pLevel, BlockPos pPos, @NotNull RandomSource pRandom) {
        double x = pPos.getX();
        double y = pPos.getY();
        double z = pPos.getZ();
        if ((pLevel.getBlockState(BlockPos.containing(x, y-1, z)))
                .getBlock() == (Blocks.CAMPFIRE.getStateDefinition().getProperty("lit") instanceof BooleanProperty _withbp1 ? Blocks.CAMPFIRE.defaultBlockState().setValue(_withbp1, true) : Blocks.CAMPFIRE.defaultBlockState()).getBlock()){
            pLevel.setBlock(pPos, BakeriesBlocks.RAW_SALT_BLOCK.get().defaultBlockState(), 3);
        }
    }
}
