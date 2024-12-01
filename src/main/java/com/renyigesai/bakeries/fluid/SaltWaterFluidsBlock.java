package com.renyigesai.bakeries.fluid;

import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;

public class SaltWaterFluidsBlock extends LiquidBlock {

    public SaltWaterFluidsBlock() {
        super(() -> BakeriesFluids.SALT_WATER.get(),
                BlockBehaviour.Properties.of().mapColor(MapColor.WATER).strength(100f).noCollission().noLootTable().liquid().pushReaction(PushReaction.DESTROY).sound(SoundType.EMPTY).replaceable());
    }


}
