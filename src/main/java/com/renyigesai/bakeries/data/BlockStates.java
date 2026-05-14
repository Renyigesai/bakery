package com.renyigesai.bakeries.data;


import com.renyigesai.bakeries.api.block.AbstractPileBlock;
import com.renyigesai.bakeries.common.blocks.*;
import com.renyigesai.bakeries.common.blocks.blander.BlenderBlock;
import com.renyigesai.bakeries.common.blocks.bread_basket.BreadBasketBlock;
import com.renyigesai.bakeries.common.blocks.bread_rack.BreadRackBlock;
import com.renyigesai.bakeries.common.blocks.oven.OvenBlock;
import com.renyigesai.bakeries.common.blocks.sofa.SofaBlock;
import com.renyigesai.bakeries.common.blocks.toaster.ToasterBlock;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import com.renyigesai.bakeries.BakeriesMod;
import com.renyigesai.bakeries.common.init.BakeriesBlocks;
import net.weibai.rcglib.blocks.BreadBlock;

import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;

public class BlockStates extends BlockStateProvider {
    /**模型切开*/
    public static String CUTOUT = "cutout";
    /**半透明*/
    public static String TRANSLUCENT = "translucent";
    /**实心*/
    public static String SOLID = "solid";
    public static String CUTOUT_MIPPED = "cutout_mipped";

    public BlockStates(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, BakeriesMod.MODID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {

        /*普通方块*/
        simpleBlock(BakeriesBlocks.SALT_ORE.get());
        simpleBlock(BakeriesBlocks.DEEPSLATE_SALT_ORE.get());
        simpleBlock(BakeriesBlocks.RAW_SALT_BLOCK.get());

        /*面包方块*/
        pileBlock(BakeriesBlocks.BAGEL);
        pileBlock(BakeriesBlocks.WHOLE_WHEAT_BAGEL,"bagel","whole_wheat_bagel");
        pileBlock(BakeriesBlocks.ROUND_BREAD);
        pileBlock(BakeriesBlocks.BERRY_BREAD);
        pileBlock(BakeriesBlocks.CHEESE_CREAM_BREAD);
        pileBlock(BakeriesBlocks.BROWN_SUGAR_ROLL);
        pileBlock(BakeriesBlocks.PINEAPPLE_BUN);
        pileBlock(BakeriesBlocks.PINEAPPLE_OIL);
        pileBlock(BakeriesBlocks.MEAT_FLOSS_BREAD_ROLL);
        pileBlock(BakeriesBlocks.CROISSANT);
        pileBlock(BakeriesBlocks.DIRTY_CHOCO_CROISSANT,"croissant","dirty_choco_croissant");
        pileBlock(BakeriesBlocks.SALT_CROISSANT);
        pileBlock(BakeriesBlocks.CIABATTA);
        pileBlock(BakeriesBlocks.FOCACCIA);
        pileBlock(BakeriesBlocks.BAGUETTE_WITH_FILLING);
        pileBlock(BakeriesBlocks.TOMATO_CHEESE_CROISSANT_SANDWICH);
        pileBlock(BakeriesBlocks.BAGUETTE);
        pileBlock(BakeriesBlocks.RICE_BREAD);
        pileBlock(BakeriesBlocks.BERRY_BAGEL);
        pileBlock(BakeriesBlocks.EGG_TART);
        pileBlock(BakeriesBlocks.FLAT_CROISSANT);
        pileBlock(BakeriesBlocks.TARO_SALT_YOLK_BREAD);
        pileBlock(BakeriesBlocks.BEARNAISE);
        pileBlock(BakeriesBlocks.OLIVE_OIL);
        pileBlock(BakeriesBlocks.SALMON_SANDWICH);
        bagelFilledSauce();

        drinkBlock(BakeriesBlocks.ICED_AMERICAN);
        drinkBlock(BakeriesBlocks.ICED_LATTE);
        drinkBlock(BakeriesBlocks.BROWN_SUGAR_LATTE);
        drinkBlock(BakeriesBlocks.MATCHA_LATTE);
        drinkBlock(BakeriesBlocks.TARO_MILK);

        /*吐司*/
        toastBlock(BakeriesBlocks.TOAST);
        toastBlock(BakeriesBlocks.CHEESE_COCOA_TOAST);
        mouldToastBlock(BakeriesBlocks.MOULD_TOAST,"toast");
        mouldToastBlock(BakeriesBlocks.MOULD_CHEESE_COCOA_TOAST,"cheese_cocoa_toast");
        directionBlock(BakeriesBlocks.MOULD);

        bagBlock(BakeriesBlocks.WHOLE_WHEAT_FLOUR_BAG.get(),"whole_wheat_");
        bagBlock(BakeriesBlocks.FLOUR_BAG.get(),"");

        coffeeTableBlock(BakeriesBlocks.COFFEE_TABLE);

        breadRackBlock(BakeriesBlocks.BREAD_RACK);
        glassBreadRackBlock(BakeriesBlocks.GLASS_BREAD_RACK);

        sofaBlock(BakeriesBlocks.SOFA_WHITE);
        sofaBlock(BakeriesBlocks.SOFA_RED);
        sofaBlock(BakeriesBlocks.SOFA_LIGHT_GRAY);
        cashRegisterComputerBlock(BakeriesBlocks.CASH_REGISTER_COMPUTER);
        directionBlock(BakeriesBlocks.DRINK_CUP);
        directionBlock(BakeriesBlocks.MENU);
        directionBlock(BakeriesBlocks.WOOD_TRAY);

        breadBasketBlock(BakeriesBlocks.BREAD_BASKET::get);
    }

    public ResourceLocation resourceBlock(String path) {
        return ResourceLocation.fromNamespaceAndPath("bakeries", "block/" + path);
    }

    public void bagBlock(Block block, String bagName) {
        this.simpleBlock(block, this.models().cubeBottomTop(this.name(block), this.resourceBlock("flour_bag_side"), this.resourceBlock("flour_bag_bottom"), this.resourceBlock(bagName + "flour_bag_top")));
    }

    public void pileBlock(Supplier<Block> block,String modelName,String textureName){
        if (block.get() instanceof AbstractPileBlock pile) {
            for (Direction direction : BlockStateProperties.HORIZONTAL_FACING.getPossibleValues()) {
                for (int size : pile.getPileProperty().getPossibleValues()) {
                    ModelFile modelFile = this.models().withExistingParent(this.name(block.get()) + "_" + size, this.modLoc("custom/" + modelName + "_" + size)).texture("0", this.modLoc("block/" + textureName)).texture("particle", this.modLoc("block/" + textureName)).renderType(CUTOUT);
                    this.getVariantBuilder(block.get())
                            .partialState()
                            .with(pile.getPileProperty(), size)
                            .with(BlockStateProperties.HORIZONTAL_FACING, direction)
                            .modelForState()
                            .rotationY((int) direction.getOpposite().toYRot())
                            .modelFile(modelFile)
                            .addModel();
                }
            }
        }
    }

    public void pileBlock(Supplier<Block> block){
        String name = this.name(block.get());
        pileBlock(block,name,name);
    }

    public void drinkBlock(Supplier<Block> block){
        String name = name(block.get());
        if (block.get() instanceof AbstractPileBlock pile) {
            for (Direction direction : BlockStateProperties.HORIZONTAL_FACING.getPossibleValues()) {
                for (int size : pile.getPileProperty().getPossibleValues()) {
                    ModelFile modelFile = this.models().withExistingParent(this.name(block.get()) + "_" + size, this.modLoc("custom/drink_" + size)).texture("0", this.modLoc("block/drink_cup")).texture("1",this.modLoc("block/" + name)).texture("particle", this.modLoc("block/drink_cup")).renderType(CUTOUT);
                    this.getVariantBuilder(block.get())
                            .partialState()
                            .with(pile.getPileProperty(), size)
                            .with(BlockStateProperties.HORIZONTAL_FACING, direction)
                            .modelForState()
                            .rotationY((int) direction.getOpposite().toYRot())
                            .modelFile(modelFile)
                            .addModel();
                }
            }
        }
    }

    public void breadBlock(Supplier<Block> block){
        for (Direction direction : BlockStateProperties.HORIZONTAL_FACING.getPossibleValues()){
            for (int size : com.renyigesai.bakeries.common.blocks.BreadBlock.PILE.getPossibleValues()){
                ModelFile modelFile = sizeModel(block, size);

                this.getVariantBuilder(block.get())
                        .partialState()
                        .with(com.renyigesai.bakeries.common.blocks.BreadBlock.PILE, size)
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
            for (int size : com.renyigesai.bakeries.common.blocks.BreadBlock.PILE.getPossibleValues()){
                ModelFile modelFile = bagelFilledSauceModel(BakeriesBlocks.BAGEL_FILLED_SAUCE::get, BakeriesBlocks.BAGEL::get, "sauce", size);

                this.getVariantBuilder(BakeriesBlocks.BAGEL_FILLED_SAUCE.get())
                        .partialState()
                        .with(com.renyigesai.bakeries.common.blocks.BreadBlock.PILE, size)
                        .with(BlockStateProperties.HORIZONTAL_FACING, direction)
                        .modelForState()
                        .rotationY((int) direction.getOpposite().toYRot())
                        .modelFile(modelFile)
                        .addModel();
            }
        }
    }
    public ModelFile bagelFilledSauceModel(Supplier<Block> block, Supplier<Block> model, String model2, int size){
        return this.models().withExistingParent(this.name(block.get()) + "_" + size, this.modLoc("custom/" + this.name(block.get()) + "_" + size)).texture("0", this.modLoc("block/" + this.name(model.get()))).texture("1", this.modLoc("block/" + model2)).texture("particle", this.modLoc("block/" + model2)).renderType(CUTOUT);
    }
    public void toastBlock(Supplier<Block> block){
        for (Direction direction : BlockStateProperties.HORIZONTAL_FACING.getPossibleValues()){
            for (int pile : ToastBlock.PILE.getPossibleValues()){
                for (int slice : ToastBlock.SLICE.getPossibleValues()) {
                    ModelFile modelFile = pile == 2 ?
                            this.models().withExistingParent(this.name(block.get()) + "_pile", this.modLoc("custom/toast" + "_pile")).texture("0", this.modLoc("block/" + this.name(block.get()))).texture("particle", this.modLoc("block/" + this.name(block.get()))).renderType(CUTOUT):
                            this.models().withExistingParent(this.name(block.get()) + "_" + slice, this.modLoc("custom/toast_" + slice)).texture("0", this.modLoc("block/" + this.name(block.get()))).texture("particle", this.modLoc("block/" + this.name(block.get()))).renderType(CUTOUT);
                    this.getVariantBuilder(block.get())
                            .partialState()
                            .with(ToastBlock.PILE, pile)
                            .with(ToastBlock.SLICE,slice)
                            .with(BlockStateProperties.HORIZONTAL_FACING, direction)
                            .modelForState()
                            .rotationY((int) direction.getOpposite().toYRot())
                            .modelFile(modelFile)
                            .addModel();
                }
            }
        }
    }

    public void mouldToastBlock(Supplier<Block> block,String toast){
        for (Direction direction : BlockStateProperties.HORIZONTAL_FACING.getPossibleValues()){
            for (int pile : MouldToastBlock.PILE.getPossibleValues()){
                ModelFile modelFile = pile == 2 ?
                        this.models().withExistingParent(this.name(block.get()) + "_pile", this.modLoc("custom/mould_toast_pile")).texture("0", "block/mould").texture("1","block/" + toast).texture("particle", this.modLoc("block/mould")).renderType(CUTOUT):
                        this.models().withExistingParent(this.name(block.get()), this.modLoc("custom/mould_toast")).texture("0", "block/mould").texture("1","block/" + toast).texture("particle", this.modLoc("block/mould")).renderType(CUTOUT);
                this.getVariantBuilder(block.get())
                        .partialState()
                        .with(MouldToastBlock.PILE, pile)
                        .with(BlockStateProperties.HORIZONTAL_FACING, direction)
                        .modelForState()
                        .rotationY((int) direction.getOpposite().toYRot())
                        .modelFile(modelFile)
                        .addModel();

            }
        }
    }

    public void breadBasketBlock(Supplier<Block> block){
        for (Direction direction : BlockStateProperties.HORIZONTAL_FACING.getPossibleValues()){
            for (Boolean fill : BreadBasketBlock.FILL.getPossibleValues()){
                    ModelFile modelFile = fill?
                            this.models().withExistingParent(this.name(block.get()) + "_fill", this.modLoc("custom/bread_basket_fill")).texture("0", "block/bread_basket").texture("1","block/baguette_16x").texture("particle", "block/bread_basket").renderType(CUTOUT):
                            this.models().withExistingParent("bread_basket", this.modLoc("custom/bread_basket")).texture("0", "block/bread_basket").texture("1","block/baguette_16x").texture("particle", "block/bread_basket").renderType(CUTOUT);
                    this.getVariantBuilder(block.get())
                            .partialState()
                            .with(BreadBasketBlock.FILL, fill)
                            .with(BlockStateProperties.HORIZONTAL_FACING, direction)
                            .modelForState()
                            .rotationY((int) direction.getOpposite().toYRot())
                            .modelFile(modelFile)
                            .addModel();
            }
        }
    }

    public void breadBlock(Supplier<Block> block, Supplier<Block> model){

        for (Direction direction : BlockStateProperties.HORIZONTAL_FACING.getPossibleValues()){
            for (int size : com.renyigesai.bakeries.common.blocks.BreadBlock.PILE.getPossibleValues()){
                ModelFile modelFile = sizeModel(block, model, size);
                this.getVariantBuilder(block.get())
                        .partialState()
                        .with(com.renyigesai.bakeries.common.blocks.BreadBlock.PILE, size)
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

//    public void sofaBlock(Supplier<SofaBlock> sofa){
//        for (Direction direction : BlockStateProperties.HORIZONTAL_FACING.getPossibleValues()) {
//            for (Boolean left : SofaBlock.LEFT.getPossibleValues()) {
//                for (Boolean right : SofaBlock.RIGHT.getPossibleValues()) {
//                    String state = "";
//                    if (left && !right){
//                        state = "_left";
//                    }
//                    if (right && !left){
//                        state = "_right";
//                    }
//                    if (right && left){
//                        state = "_all";
//                    }
//                    String color = sofa.get().getColor().getColorKey();
//                    String texture = "block/sofa_" + color;
//                    ModelFile modelFile = this.models().withExistingParent("sofa_" + color + state , this.modLoc("custom/sofa" + state)).texture("0", texture).texture("particle", texture).renderType(CUTOUT);
//                    this.getVariantBuilder(sofa.get())
//                            .partialState()
//                            .with(SofaBlock.LEFT, left)
//                            .with(SofaBlock.RIGHT, right)
//                            .with(BlockStateProperties.HORIZONTAL_FACING, direction)
//                            .modelForState()
//                            .rotationY((int) direction.toYRot())
//                            .modelFile(modelFile)
//                            .addModel();
//                }
//            }
//        }
//    }

    public void sofaBlock(Supplier<SofaBlock> sofa){
        for (Direction direction : BlockStateProperties.HORIZONTAL_FACING.getPossibleValues()) {
            for (HorizontalConnectBlock.Type type : SofaBlock.TYPE.getPossibleValues()) {
                String state = "_" + type.getSerializedName();
                if ("_single".equals(state)){
                    state = "";
                }
                String color = sofa.get().getColor().getColorKey();
                String texture = "block/sofa_" + color;
                ModelFile modelFile = this.models().withExistingParent("sofa_" + color + state , this.modLoc("custom/sofa" + state)).texture("0", texture).texture("particle", texture).renderType(CUTOUT);
                this.getVariantBuilder(sofa.get())
                        .partialState()
                        .with(BlockStateProperties.HORIZONTAL_FACING, direction)
                        .with(SofaBlock.TYPE, type)
                        .modelForState()
                        .rotationY((int) direction.toYRot())
                        .modelFile(modelFile)
                        .addModel();
            }
        }
    }

    public void coffeeTableBlock(Supplier<Block> block){
        for (Direction direction : BlockStateProperties.HORIZONTAL_FACING.getPossibleValues()) {
            for (HorizontalConnectBlock.Type type : CoffeeTableBlock.TYPE.getPossibleValues()) {
                String state = "_" + type.getSerializedName();
                if ("_single".equals(state)){
                    state = "";
                }
                ModelFile modelFile = this.models().withExistingParent("coffee_table" + state, this.modLoc("custom/coffee_table" + state)).texture("0", "minecraft:block/stripped_oak_log").texture("1", "minecraft:block/quartz_block_top").texture("particle", "minecraft:block/quartz_block_top").renderType(CUTOUT);
                this.getVariantBuilder(block.get())
                        .partialState()
                        .with(BlockStateProperties.HORIZONTAL_FACING, direction)
                        .with(CoffeeTableBlock.TYPE, type)
                        .modelForState()
                        .rotationY((int) direction.toYRot())
                        .modelFile(modelFile)
                        .addModel();
            }
        }
    }

    public void breadRackBlock(Supplier<Block> block){
        for (Direction direction : BlockStateProperties.HORIZONTAL_FACING.getPossibleValues()) {
            for (HorizontalConnectBlock.Type type : BreadRackBlock.TYPE.getPossibleValues()) {
                String state = "_" + type.getSerializedName();
                if ("_single".equals(state)){
                    state = "";
                }
                ModelFile modelFile = this.models().withExistingParent("bread_rack" + state, this.modLoc("custom/bread_rack" + state)).texture("0", "bakeries:block/bread_rack").texture("particle", "bakeries:block/bread_rack").renderType(CUTOUT);
                this.getVariantBuilder(block.get())
                        .partialState()
                        .with(BlockStateProperties.HORIZONTAL_FACING, direction)
                        .with(BreadRackBlock.TYPE, type)
                        .modelForState()
                        .rotationY((int) direction.toYRot())
                        .modelFile(modelFile)
                        .addModel();
            }
        }
    }

    public void glassBreadRackBlock(Supplier<Block> block){
        for (Direction direction : BlockStateProperties.HORIZONTAL_FACING.getPossibleValues()) {
            for (HorizontalConnectBlock.Type type : BreadRackBlock.TYPE.getPossibleValues()) {
                String state = "_" + type.getSerializedName();
                if ("_single".equals(state)){
                    state = "";
                }
                ModelFile modelFile = this.models().withExistingParent("glass_bread_rack" + state, this.modLoc("custom/glass_bread_rack" + state)).texture("0", "bakeries:block/glass_bread_rack").texture("particle", "bakeries:block/glass_bread_rack").renderType(CUTOUT);
                this.getVariantBuilder(block.get())
                        .partialState()
                        .with(BlockStateProperties.HORIZONTAL_FACING, direction)
                        .with(BreadRackBlock.TYPE, type)
                        .modelForState()
                        .rotationY((int) direction.toYRot())
                        .modelFile(modelFile)
                        .addModel();
            }
        }
    }

    public void cashRegisterComputerBlock(Supplier<Block> block){
        for (Direction direction : BlockStateProperties.HORIZONTAL_FACING.getPossibleValues()) {
            for (Boolean lit : CashRegisterComputerBlock.LIT.getPossibleValues()) {
                String texture = "block/cash_register_computer";
                String texture_lit = "block/cash_register_computer_lit";
                ModelFile modelFile = lit ?
                        this.models().withExistingParent("cash_register_computer_lit", this.modLoc("custom/cash_register_computer")).texture("0", texture_lit).texture("particle", texture_lit).renderType(CUTOUT):
                        this.models().withExistingParent("cash_register_computer" , this.modLoc("custom/cash_register_computer")).texture("0", texture).texture("particle", texture).renderType(CUTOUT);
                        this.getVariantBuilder(block.get())
                            .partialState()
                            .with(CashRegisterComputerBlock.LIT,lit)
                            .with(BlockStateProperties.HORIZONTAL_FACING, direction)
                            .modelForState()
                            .rotationY((int) direction.toYRot())
                            .modelFile(modelFile)
                            .addModel();
            }
        }
    }

    public void mokaPotBlock(Supplier<? extends Block> block){
        for (Direction direction : BlockStateProperties.HORIZONTAL_FACING.getPossibleValues()) {
                String texture = "block/moka_pot";
                ModelFile modelFile = this.models().withExistingParent("moka_pot", this.modLoc("custom/moka_pot")).texture("0", texture).texture("particle", texture).renderType(CUTOUT);
                this.getVariantBuilder(block.get())
                        .partialState()
                        .with(BlockStateProperties.HORIZONTAL_FACING, direction)
                        .modelForState()
                        .rotationY((int) direction.toYRot())
                        .modelFile(modelFile)
                        .addModel();
        }
    }

    public void directionBlock(Supplier<? extends Block> block){
        for (Direction direction : BlockStateProperties.HORIZONTAL_FACING.getPossibleValues()) {
            String name = name(block.get());
            String texture = "block/" + name;
            ModelFile modelFile = this.models().withExistingParent(name, this.modLoc("custom/" + name)).texture("0", texture).texture("particle", texture).renderType(CUTOUT);
            this.getVariantBuilder(block.get())
                    .partialState()
                    .with(BlockStateProperties.HORIZONTAL_FACING, direction)
                    .modelForState()
                    .rotationY((int) direction.toYRot())
                    .modelFile(modelFile)
                    .addModel();
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