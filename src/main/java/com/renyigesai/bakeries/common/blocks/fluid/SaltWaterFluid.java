package com.renyigesai.bakeries.common.blocks.fluid;

import com.renyigesai.bakeries.common.init.BakeriesBlocks;
import com.renyigesai.bakeries.common.init.BakeriesItems;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.neoforged.neoforge.fluids.BaseFlowingFluid;

public abstract class SaltWaterFluid extends BaseFlowingFluid {
    public static final Properties PROPERTIES = new Properties(BakeriesFluidTypes.SALT_WATER_TYPE, BakeriesFluids.SALT_WATER,
            BakeriesFluids.FLOWING_SALT_WATER).explosionResistance(100f).bucket(BakeriesItems.SALT_WATER_BUCKET)
            .block(BakeriesBlocks.SALT_WATER_BLOCK);

    private SaltWaterFluid() {
        super(PROPERTIES);
    }

    public static class Source extends SaltWaterFluid {
        public int getAmount(FluidState state) {
            return 8;
        }

        public boolean isSource(FluidState state) {
            return true;
        }
    }

    public static class Flowing extends SaltWaterFluid {
        protected void createFluidStateDefinition(StateDefinition.Builder<Fluid, FluidState> builder) {
            super.createFluidStateDefinition(builder);
            builder.add(LEVEL);
        }

        public int getAmount(FluidState state) {
            return state.getValue(LEVEL);
        }

        public boolean isSource(FluidState state) {
            return false;
        }
    }
}
