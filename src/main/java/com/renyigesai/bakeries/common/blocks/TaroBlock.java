package com.renyigesai.bakeries.common.blocks;

import com.renyigesai.bakeries.common.init.BakeriesItems;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

public class TaroBlock extends ReapCropBlock{

    private static final VoxelShape[] SHAPE_BY_AGE = new VoxelShape[]{
            Block.box(0.0D, 0.0D, 0.0D, 16.0D, 2.0D, 16.0D),
            Block.box(0.0D, 0.0D, 0.0D, 16.0D, 4.0D, 16.0D),
            Block.box(0.0D, 0.0D, 0.0D, 16.0D, 6.0D, 16.0D),
            Block.box(0.0D, 0.0D, 0.0D, 16.0D, 8.0D, 16.0D)
    };

    public TaroBlock(Properties p_52247_) {
        super(p_52247_);
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        int age = pState.getValue(AGE);
        if (age <= 3){
            return SHAPE_BY_AGE[pState.getValue(AGE)];
        }
        return SHAPE_BY_AGE[3];
    }

    @Override
    public int getMaxAge() {
        return 3;
    }

    @Override
    protected @NotNull ItemLike getBaseSeedId() {
        return BakeriesItems.TARO.get();
    }

}
