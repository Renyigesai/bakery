package com.renyigesai.bakeries.data.provider;


import com.renyigesai.bakeries.BakeriesMod;
import com.renyigesai.bakeries.api.block.PileBlock;
import com.renyigesai.bakeries.block.oven.OvenBlock;
import com.renyigesai.bakeries.init.BakeriesBlocks;
import net.minecraft.core.Direction;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Objects;
import java.util.function.Supplier;

public class BBlockStateProvider extends net.minecraftforge.client.model.generators.BlockStateProvider {
    public static final String CUTOUT = "cutout";

    public BBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, BakeriesMod.MODID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        pileBlock(BakeriesBlocks.BAGEL);
        pileBlock(BakeriesBlocks.BAGUETTE);
        pileBlock(BakeriesBlocks.BROWN_SUGAR_ROLL);
        pileBlock(BakeriesBlocks.CROISSANT);
        pileBlock(BakeriesBlocks.SALT_CROISSANT);
        pileBlock(BakeriesBlocks.PINEAPPLE_BUN);
        pileBlock(BakeriesBlocks.ROUND_BREAD);
        pileBlock(BakeriesBlocks.COUNTRY_BREAD);
        ovenBlock(BakeriesBlocks.OVEN);
//        fermentation_tank(BakeriesBlocks.FERMENTATION_TANK);
    }
//    public void fermentation_tank(Supplier<? extends Block> block) {
//        for(boolean water : FermentationTankBlock.WATER.getPossibleValues()) {
//            for(boolean fertilized : FermentationTankBlock.IS_FERTILIZED.getPossibleValues()) {
//                for (int flour : FermentationTankBlock.FLOUR.getPossibleValues()) {
//
//                    ModelFile modelFile;
//                    if (flour == 0) {
//                        modelFile = this.models().withExistingParent(
//                                        this.name(block.get()),
//                                        this.modLoc("custom/" + this.name(block.get())))
//                                .texture("0", this.modLoc("block/" + this.name(block.get())))
//                                .texture("particle", this.modLoc("block/" + this.name(block.get())))
//                                .renderType(CUTOUT);
//                    } else if (flour == 4) {
//                        modelFile = this.models().withExistingParent(
//                                        this.name(block.get()) + "_flour_" + flour,
//                                        this.modLoc("custom/" + this.name(block.get()) + "_flour_" + flour))
//                                .texture("0", this.modLoc("block/" + this.name(block.get())))
//                                .texture("1", this.modLoc("block/yeast"))
//                                .texture("particle", this.modLoc("block/" + this.name(block.get())))
//                                .renderType(CUTOUT);
//                    } else {
//                        modelFile = this.models().withExistingParent(
//                                        this.name(block.get()) + "_flour_" + flour,
//                                        this.modLoc("custom/" + this.name(block.get()) + "_flour_" + flour))
//                                .texture("0", this.modLoc("block/" + this.name(block.get())))
//                                .texture("1", this.modLoc("block/whole_wheat_flour"))
//                                .texture("particle", this.modLoc("block/" + this.name(block.get())))
//                                .renderType(CUTOUT);
//                    }
//                    this.getVariantBuilder(block.get())
//                            .partialState()
//                            .with(FermentationTankBlock.FLOUR, flour)
//                            .with(FermentationTankBlock.WATER, water)
//                            .with(FermentationTankBlock.IS_FERTILIZED, fertilized)
//                            .modelForState()
//                            .modelFile(modelFile)
//                            .addModel();
//                }
//            }
//        }
//    }
    public void ovenBlock(Supplier<? extends Block> block) {
        for(boolean lit : OvenBlock.LIT.getPossibleValues()) {
            ModelFile modelFile;
            if(lit){
                modelFile = this.models().withExistingParent(
                                this.name(block.get()) + "_fire",
                                this.modLoc("custom/"+ this.name(block.get())))
                        .texture("0", this.modLoc("block/" + this.name(block.get()) + "_fire"))
                        .texture("particle", this.modLoc("block/" + this.name(block.get()) + "_fire"))
                        .renderType(CUTOUT);
            }else {
                modelFile = this.models().withExistingParent(
                                this.name(block.get()),
                                this.modLoc("custom/"+ this.name(block.get())))
                        .texture("0", this.modLoc("block/" + this.name(block.get())))
                        .texture("particle", this.modLoc("block/" + this.name(block.get())))
                        .renderType(CUTOUT);
            }
            for (Direction facing :  Direction.Plane.HORIZONTAL) {
                this.getVariantBuilder(block.get())
                        .partialState()
                        .with(OvenBlock.FACING, facing)
                        .with(OvenBlock.LIT, lit)
                        .modelForState()
                        .modelFile(modelFile)
                        .rotationY((int) facing.toYRot())
                        .addModel();
            }
        }
    }
    public void pileBlock(Supplier<? extends Block> block) {
        for(int pile : PileBlock.integerProperty.getPossibleValues()) {
            ModelFile modelFile = this.models().withExistingParent(
                            this.name(block.get())+ "_" + pile,
                            this.modLoc("custom/"+ this.name(block.get())+ "_" + pile))
                    .texture("0", this.modLoc("block/" + this.name(block.get())))
                    .texture("particle", this.modLoc("block/" + this.name(block.get())))
                    .renderType(CUTOUT);
            for (Direction facing :  Direction.Plane.HORIZONTAL) {
                this.getVariantBuilder(block.get())
                        .partialState()
                        .with(PileBlock.FACING, facing)
                        .with(PileBlock.integerProperty, pile)
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