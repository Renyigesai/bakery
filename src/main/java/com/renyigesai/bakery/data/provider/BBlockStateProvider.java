package com.renyigesai.bakery.data.provider;


import com.renyigesai.bakery.BakeryMod;
import com.renyigesai.bakery.api.PileBlock;
import com.renyigesai.bakery.init.BakeryBlocks;
import net.minecraft.core.Direction;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.SlabType;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Objects;
import java.util.function.Supplier;

public class BBlockStateProvider extends net.minecraftforge.client.model.generators.BlockStateProvider {
    public static String CUTOUT = "cutout";

    public BBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, BakeryMod.MODID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        pileBlock(BakeryBlocks.BAGEL_BLOCK);
        pileBlock(BakeryBlocks.BAGUETTE_BLOCK);
        pileBlock(BakeryBlocks.CINNAMON_ROLL_BLOCK);
        pileBlock(BakeryBlocks.CROISSANT_BLOCK);
    }
    public void pileBlock(Supplier<? extends Block> block) {
        for(int pile : PileBlock.PILE.getPossibleValues()) {
            ModelFile modelFile = this.models().withExistingParent(
                            this.name(block.get())+ "_" + pile,
                            this.modLoc("custom/"+ this.name(block.get())+ "_" + pile))
                    .texture("0", this.modLoc("block/" + this.name(block.get())))
                    .renderType(CUTOUT);
            for (Direction facing :  Direction.Plane.HORIZONTAL) {
                this.getVariantBuilder(block.get())
                        .partialState()
                        .with(PileBlock.FACING, facing)
                        .with(PileBlock.PILE, pile)
                        .modelForState()
                        .modelFile(modelFile)
                        .rotationY((int) facing.toYRot())
                        .addModel();
            }
        }
    }

    private String name(Block block) {
        return Objects.requireNonNull(ForgeRegistries.BLOCKS.getKey(block)).getPath();
    }
    private String name(Item item) {
        return Objects.requireNonNull(ForgeRegistries.ITEMS.getKey(item)).getPath();
    }

}