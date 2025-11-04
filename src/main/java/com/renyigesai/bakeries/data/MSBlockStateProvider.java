package com.renyigesai.bakeries.data;


import com.renyigesai.bakeries.common.blocks.blander.BlenderBlock;
import com.renyigesai.bakeries.common.blocks.oven.OvenBlock;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import com.renyigesai.bakeries.BakeriesMod;
import com.renyigesai.bakeries.common.init.BakeriesBlocks;
import net.weibai.rcglib.blocks.BreadBlock;

import java.util.Objects;
import java.util.function.Supplier;

public class MSBlockStateProvider extends BlockStateProvider {
    /**模型切开*/
    public static String CUTOUT = "cutout";
    /**半透明*/
    public static String TRANSLUCENT = "translucent";
    /**实心*/
    public static String SOLID = "solid";
    public static String CUTOUT_MIPPED = "cutout_mipped";

    public MSBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, BakeriesMod.MODID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        breadBlock(BakeriesBlocks.BAGEL::get);
        breadBlock(BakeriesBlocks.WHOLE_WHEAT_BAGEL::get, BakeriesBlocks.BAGEL::get);
        breadBlock(BakeriesBlocks.ROUND_BREAD::get);
        breadBlock(BakeriesBlocks.BERRY_BREAD::get);
        breadBlock(BakeriesBlocks.CHEESE_CREAM_BREAD::get);
        breadBlock(BakeriesBlocks.BROWN_SUGAR_ROLL::get);
        breadBlock(BakeriesBlocks.PINEAPPLE_BUN::get);
        breadBlock(BakeriesBlocks.MEAT_FLOSS_BREAD_ROLL::get);
        breadBlock(BakeriesBlocks.CROISSANT::get);
        breadBlock(BakeriesBlocks.DIRTY_CHOCO_CROISSANT::get, BakeriesBlocks.CROISSANT::get);
        breadBlock(BakeriesBlocks.SALT_CROISSANT::get);
        breadBlock(BakeriesBlocks.CIABATTA::get);
        breadBlock(BakeriesBlocks.FOCACCIA::get);
        breadBlock(BakeriesBlocks.BERRY_BAGEL::get);
        bagelFilledSauce();
        breadBlock(BakeriesBlocks.BAGUETTE_WITH_FILLING::get);
        breadBlock(BakeriesBlocks.TOMATO_CHEESE_CROISSANT_SANDWICH::get);
        breadBlock(BakeriesBlocks.BAGUETTE::get);
        blenderBlock(BakeriesBlocks.BLENDER::get);
        ovenBlock(BakeriesBlocks.OVEN::get);
    }

    public void breadBlock(Supplier<Block> block){

        for (Direction direction : BlockStateProperties.HORIZONTAL_FACING.getPossibleValues()){
            for (int size : BreadBlock.PILE_4.getPossibleValues()){
                ModelFile modelFile = sizeModel(block, size);

                this.getVariantBuilder(block.get())
                        .partialState()
                        .with(BreadBlock.PILE_4, size)
                        .with(BlockStateProperties.HORIZONTAL_FACING, direction)
                        .modelForState()
                        .rotationY((int) direction.getOpposite().toYRot())
                        .modelFile(modelFile)
                        .addModel();
            }
        }
    }
    public void bagelFilledSauce(){
        for (Direction direction : BlockStateProperties.HORIZONTAL_FACING.getPossibleValues()){
            for (int size : BreadBlock.PILE_4.getPossibleValues()){
                ModelFile modelFile = bagelFilledSauceModel(BakeriesBlocks.BAGEL_FILLED_SAUCE::get, BakeriesBlocks.BAGEL::get, "sauce", size);

                this.getVariantBuilder(BakeriesBlocks.BAGEL_FILLED_SAUCE.get())
                        .partialState()
                        .with(BreadBlock.PILE_4, size)
                        .with(BlockStateProperties.HORIZONTAL_FACING, direction)
                        .modelForState()
                        .rotationY((int) direction.getOpposite().toYRot())
                        .modelFile(modelFile)
                        .addModel();
            }
        }
    }
    public ModelFile bagelFilledSauceModel(Supplier<Block> block, Supplier<Block> model, String model2, int size){
        return this.models().withExistingParent(
                        this.name(block.get()) + "_" + size,
                        this.modLoc("custom/" + this.name(block.get()) + "_" + size))
                .texture("0", this.modLoc("block/" + this.name(model.get())))
                .texture("1", this.modLoc("block/" + model2))
                .texture("particle", this.modLoc("block/" + model2))
                .renderType(CUTOUT);
    }
    public void breadBlock(Supplier<Block> block, Supplier<Block> model){

        for (Direction direction : BlockStateProperties.HORIZONTAL_FACING.getPossibleValues()){
            for (int size : BreadBlock.PILE_4.getPossibleValues()){
                ModelFile modelFile = sizeModel(block, model, size);

                this.getVariantBuilder(block.get())
                        .partialState()
                        .with(BreadBlock.PILE_4, size)
                        .with(BlockStateProperties.HORIZONTAL_FACING, direction)
                        .modelForState()
                        .rotationY((int) direction.getOpposite().toYRot())
                        .modelFile(modelFile)
                        .addModel();
            }
        }
    }
    public void ovenBlock(Supplier<Block> block){

        for (Direction direction : BlockStateProperties.HORIZONTAL_FACING.getPossibleValues()){
            for (boolean lit : OvenBlock.LIT.getPossibleValues()){
                ModelFile modelFile = lit ? this.models().withExistingParent(
                                this.name(block.get()) + "_fire",
                                this.modLoc("custom/" + this.name(block.get())))
                        .texture("0", this.modLoc("block/" + this.name(block.get()) + "_fire"))
                        .texture("particle", this.modLoc("block/" + this.name(block.get()) + "_fire"))
                        .renderType(CUTOUT) :
                        this.models().withExistingParent(
                                        this.name(block.get()),
                                        this.modLoc("custom/" + this.name(block.get())))
                                .texture("0", this.modLoc("block/" + this.name(block.get())))
                                .texture("particle", this.modLoc("block/" + this.name(block.get())))
                                .renderType(CUTOUT);

                this.getVariantBuilder(block.get())
                        .partialState()
                        .with(OvenBlock.LIT, lit)
                        .with(BlockStateProperties.HORIZONTAL_FACING, direction)
                        .modelForState()
                        .rotationY((int) direction.toYRot())
                        .modelFile(modelFile)
                        .addModel();
            }
        }
    }


    public void blenderBlock(Supplier<Block> block){

        for (Direction direction : BlockStateProperties.HORIZONTAL_FACING.getPossibleValues()){
            for (boolean powered : BlenderBlock.POWERED.getPossibleValues()){
                ModelFile modelFile = powered ? this.models().withExistingParent(
                                this.name(block.get()) + "_powered",
                                this.modLoc("custom/" + this.name(block.get())))
                        .texture("0", this.modLoc("block/" + this.name(block.get()) ))
                        .texture("1", this.modLoc("block/mixing_head_1"))
                        .texture("2", this.modLoc("block/mixing_head_2"))
                        .texture("3", this.modLoc("block/mixing_head_3"))
                        .texture("4", this.modLoc("block/mixing_head_4"))
                        .texture("particle", this.modLoc("block/" + this.name(block.get())))
                        .renderType(CUTOUT) :
                        this.models().withExistingParent(
                                        this.name(block.get()),
                                        this.modLoc("custom/" + this.name(block.get())))
                                .texture("0", this.modLoc("block/" + this.name(block.get())))
                                .texture("particle", this.modLoc("block/" + this.name(block.get())))
                                .renderType(CUTOUT);

                this.getVariantBuilder(block.get())
                        .partialState()
                        .with(BlenderBlock.POWERED, powered)
                        .with(BlockStateProperties.HORIZONTAL_FACING, direction)
                        .modelForState()
                        .rotationY((int) direction.toYRot())
                        .modelFile(modelFile)
                        .addModel();
            }
        }
    }

    public ModelFile sizeModel(Supplier<Block> block, Supplier<Block> model, int size){
        return this.models().withExistingParent(
                        this.name(block.get()) + "_" + size,
                        this.modLoc("custom/" + this.name(model.get()) + "_" + size))
                .texture("0", this.modLoc("block/" + this.name(block.get())))
                .texture("particle", this.modLoc("block/" + this.name(block.get())))
                .renderType(CUTOUT);
    }
    public ModelFile sizeModel(Supplier<Block> block, int size){
        return this.models().withExistingParent(
                        this.name(block.get()) + "_" + size,
                        this.modLoc("custom/" + this.name(block.get()) + "_" + size))
                .texture("0", this.modLoc("block/" + this.name(block.get())))
                .texture("particle", this.modLoc("block/" + this.name(block.get())))
                .renderType(CUTOUT);
    }
    public void blockSolid(Supplier<Block> block){
        ModelFile modelFile = this.models().withExistingParent(
                        this.name(block.get()),
                        this.mcLoc("block/cube_all"))
                .texture("all", this.modLoc("block/" + this.name(block.get())))
                .texture("particle", this.modLoc("block/" + this.name(block.get())));
        this.getVariantBuilder(block.get())
                .partialState().modelForState().modelFile(modelFile).addModel();
    }
    public void blockNull(Supplier<Block> block){
        ModelFile modelFile = this.models().withExistingParent(
                        this.name(block.get()),
                        this.mcLoc("block/air"))
                .texture("particle", this.modLoc("block/air"));
        this.getVariantBuilder(block.get())
                .partialState().modelForState().modelFile(modelFile).addModel();
    }
    public void crossBlock(Supplier<? extends Block> block, IntegerProperty propertys){

        for (int property : propertys.getPossibleValues()){
            this.getVariantBuilder(block.get())
                    .partialState()
                    .with(propertys, property)
                    .modelForState()
                    .modelFile(cross(block, property)).addModel();
        }
    }
    public void tintedCrossBlock(Supplier<? extends Block> block){
        this.getVariantBuilder(block.get())
                .partialState()
                .modelForState()
                .modelFile(tintedCross(block)).addModel();
    }
    public ModelFile cross(Supplier<? extends Block> block, int property){
        return switch (property) {
            case 1 -> this.models().withExistingParent(
                            this.name(block.get()) + "_stage1",
                            this.mcLoc("block/cross"))
                    .texture("cross", this.mcLoc("block/" + this.name(block.get()) + "_stage1")).renderType(CUTOUT);
            case 2 -> this.models().withExistingParent(
                            this.name(block.get()) + "_stage2",
                            this.mcLoc("block/cross"))
                    .texture("cross", this.mcLoc("block/" + this.name(block.get()) + "_stage2")).renderType(CUTOUT);
            case 3 -> this.models().withExistingParent(
                            this.name(block.get()) + "_stage3",
                            this.mcLoc("block/cross"))
                    .texture("cross", this.mcLoc("block/" + this.name(block.get()) + "_stage3")).renderType(CUTOUT);
            default -> this.models().withExistingParent(
                            this.name(block.get()) + "_stage0",
                            this.mcLoc("block/cross"))
                    .texture("cross", this.mcLoc("block/" + this.name(block.get()) + "_stage0")).renderType(CUTOUT);
        };
    }
    public ModelFile tintedCross(Supplier<? extends Block> block){
        return this.models().withExistingParent(
                        this.name(block.get()),
                        this.mcLoc("block/tinted_cross"))
                .texture("cross", this.modLoc("block/" + this.name(block.get()))).renderType(CUTOUT);
    }
    private String name(Block block) {
        return Objects.requireNonNull(BuiltInRegistries.BLOCK.getKey(block)).getPath();
    }
    private String name(Item item) {
        return Objects.requireNonNull(BuiltInRegistries.ITEM.getKey(item)).getPath();
    }
    public ResourceLocation blockLoc(Block block ,String name) {
        ResourceLocation  blockLoc = BuiltInRegistries.BLOCK.getKey(block);
        return ResourceLocation.fromNamespaceAndPath(blockLoc.getNamespace(), name);
    }
}
//    public void logCampfire(Supplier<Block> block, String fire, boolean soul){
//        Block log = ((LogCampfireBlock)block.get()).log;
//        for(int index : LogCampfireBlock.VALUE.getPossibleValues()){
//            for (Boolean lit : LogCampfireBlock.LIT.getPossibleValues()){
//                ModelFile modelFile = models().withExistingParent(
//                                soul ? "soul_" + name(log) + "_campfire_" + index: name(log) + "_campfire_" + index,
//                                modLoc("custom/campfire_" + index))
//                        .texture("stone", mcLoc("block/" + name(Blocks.STONE)))
//                        .texture("log", blockLoc(log ,"block/" + name(log)))
//                        .texture("log_top", blockLoc(log ,"block/" + name(log) + "_top"))
//                        .texture("particle", blockLoc(log ,"block/" + name(log))).renderType(CUTOUT);
//                if(lit && index == 3){
//                    modelFile = models().withExistingParent(
//                                    soul ? "soul_" + name(log) + "_campfire_fire" : name(log) + "_campfire_fire",
//                                    modLoc("custom/campfire_fire"))
//                            .texture("stone", mcLoc("block/" + name(Blocks.STONE)))
//                            .texture("fire", mcLoc("block/" + fire))
//                            .texture("log", blockLoc(log ,"block/" + name(log)))
//                            .texture("log_top", blockLoc(log ,"block/" + name(log) + "_top"))
//                            .texture("log_lit", soul ? modLoc("block/soul_campfire_log_lit") : modLoc("block/campfire_log_lit"))
//                            .texture("particle", blockLoc(log ,"block/" + name(log))).renderType(CUTOUT);
//                }
//                for(Direction facing : LogCampfireBlock.FACING.getPossibleValues()){
//                    getVariantBuilder(block.get())
//                            .partialState()
//                            .with(LogCampfireBlock.VALUE, index)
//                            .with(LogCampfireBlock.LIT, lit)
//                            .with(LogCampfireBlock.FACING, facing)
//                            .modelForState()
//                            .modelFile(modelFile)
//                            .rotationY((int) facing.getOpposite().toYRot())
//                            .addModel();
//                }
//            }
//        }
//    }
//    public void soilBurning(Supplier<Block> block, Supplier<Block> coal){
//        Block log = ((SoilBurningBlock) block.get()).log;
//        for(int index : SoilBurningBlock.VALUE.getPossibleValues()){
//            for (Boolean ash : SoilBurningBlock.ASHES.getPossibleValues()){
//                ModelFile modelFile = models().withExistingParent(
//                                name(log) + "_soil_burning_" + index,
//                                modLoc("custom/soil_burning_" + index))
//                        .texture("log", blockLoc(log ,"block/" + name(log)))
//                        .texture("log_top", blockLoc(log ,"block/" + name(log) + "_top"))
//                        .texture("particle", blockLoc(log ,"block/" + name(log))).renderType(CUTOUT);
//                if(ash){
//                    modelFile = models().withExistingParent(
//                                    name(log) + "_soil_burning_ashes",
//                                    modLoc("custom/soil_burning_ashes"))
//                            .texture("all", mcLoc("block/" + name(coal.get())))
//                            .texture("particle", mcLoc("block/" + name(coal.get()))).renderType(CUTOUT);
//                }
//
//                getVariantBuilder(block.get())
//                        .partialState()
//                        .with(SoilBurningBlock.VALUE, index)
//                        .with(SoilBurningBlock.ASHES, ash)
//                        .modelForState()
//                        .modelFile(modelFile)
//                        .addModel();
//            }
//        }
//    }


//    public void flaxPlant(){
//        Supplier<Block> block = MSBlocks.FLAX_BLOCK;
//
//        ModelFile modelFile = this.models().withExistingParent(
//                        this.name(block.get()),
//                        this.mcLoc("block/tinted_cross"))
//                .texture("cross", this.modLoc("block/flax_block/" + this.name(block.get()) + "_0")).renderType(CUTOUT);
//        for( int age : TallPlantBlock.AGE.getPossibleValues()){
//            for (DoubleBlockHalf half : TallPlantBlock.HALF.getPossibleValues()){
//                if(half == DoubleBlockHalf.LOWER ){
//                    modelFile = this.models().withExistingParent(
//                                    this.name(block.get()) + "_lower_" + age,
//                                    this.mcLoc("block/cross"))
//                            .texture("cross", this.modLoc("block/flax_block/" + this.name(block.get()) + "_" + age)).renderType(CUTOUT);
//                }else if(half == DoubleBlockHalf.UPPER ){
//                    if(age >= 2){
//                        modelFile = this.models().withExistingParent(
//                                        this.name(block.get()) + "_upper_" + age,
//                                        this.mcLoc("block/cross"))
//                                .texture("cross", this.modLoc("block/flax_block/" + this.name(block.get()) + "_" + age + "_top")).renderType(CUTOUT);
//                    }
//                }
//
//
//                this.getVariantBuilder(block.get())
//                        .partialState()
//                        .with(TallPlantBlock.HALF, half)
//                        .with(TallPlantBlock.AGE, age)
//                        .modelForState()
//                        .modelFile(modelFile).addModel();
//            }
//        }
//    }