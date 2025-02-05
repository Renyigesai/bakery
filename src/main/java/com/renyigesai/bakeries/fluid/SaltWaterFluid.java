
package com.renyigesai.bakeries.fluid;

import com.renyigesai.bakeries.init.BakeriesBlocks;
import com.renyigesai.bakeries.init.BakeriesItems;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;

import net.minecraftforge.fluids.ForgeFlowingFluid;
import org.jetbrains.annotations.NotNull;

public abstract class SaltWaterFluid extends ForgeFlowingFluid {
    public static final ForgeFlowingFluid.Properties PROPERTIES = new ForgeFlowingFluid.Properties(BakeriesFluidTypes.SALT_WATER_TYPE, BakeriesFluids.SALT_WATER,
            BakeriesFluids.FLOWING_SALT_WATER).explosionResistance(100f).bucket(BakeriesItems.SALT_WATER_BUCKET)
            .block(BakeriesBlocks.SALT_WATER_BLOCK);

    private SaltWaterFluid() {
        super(PROPERTIES);
    }

    public static class Source extends SaltWaterFluid {
        public int getAmount(@NotNull FluidState state) {
            return 8;
        }

        public boolean isSource(@NotNull FluidState state) {
            return true;
        }
    }

    public static class Flowing extends SaltWaterFluid {
        protected void createFluidStateDefinition(StateDefinition.@NotNull Builder<Fluid, FluidState> builder) {
            super.createFluidStateDefinition(builder);
            builder.add(LEVEL);
        }

        public int getAmount(FluidState state) {
            return state.getValue(LEVEL);
        }

        public boolean isSource(@NotNull FluidState state) {
            return false;
        }
    }

}
