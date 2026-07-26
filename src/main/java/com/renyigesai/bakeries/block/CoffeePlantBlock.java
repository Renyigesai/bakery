package com.renyigesai.bakeries.block;

import com.renyigesai.bakeries.init.BakeriesItems;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

public class CoffeePlantBlock extends ReapCropBlock{

    private static final VoxelShape SHAPE = Shapes.or(Block.box(0.0D, 8.0D, 0.0D, 16.0D, 15.0D, 16.0D), Block.box(6.0D, 0.0D, 6.0D, 10.0D, 8.0D, 10.0D));
    public CoffeePlantBlock(Properties p_52247_) {
        super(p_52247_);
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return SHAPE;
    }

    @Override
    public int getMaxAge() {
        return 2;
    }

    @Override
    protected @NotNull ItemLike getBaseSeedId() {
        return BakeriesItems.RAW_COFFEE_BEAN.get();
    }

    @Override
    public void entityInside(BlockState state, Level level, BlockPos pos, Entity entity) {
        if (entity instanceof LivingEntity && entity.getType() != EntityType.FOX && entity.getType() != EntityType.BEE) {
            entity.makeStuckInBlock(state, new Vec3(0.800000011920929, 0.75, 0.800000011920929));
        }
    }
}
