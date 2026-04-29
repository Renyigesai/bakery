package com.renyigesai.bakeries.block;

import com.renyigesai.bakeries.api.block.PileBlock;
import com.renyigesai.bakeries.init.BakeriesItems;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootParams;

import java.util.ArrayList;
import java.util.List;

public class OliveOilBlock extends PileBlock {
    public OliveOilBlock() {
    }

    public OliveOilBlock(Properties properties) {
        super(properties);
    }

    @Override
    public List<ItemStack> getDrops(BlockState pState, LootParams.Builder pParams) {
        int pile = pState.getValue(integerProperty);
//        List<ItemStack> stacks = new ArrayList<>();
//        for (int i = 0; i < pile; i++) {
//            stacks.add(new ItemStack(BakeriesItems.OLIVE_OIL.get()));
//        }
        return List.of(new ItemStack(BakeriesItems.OLIVE_OIL.get(),pile));
    }
}
