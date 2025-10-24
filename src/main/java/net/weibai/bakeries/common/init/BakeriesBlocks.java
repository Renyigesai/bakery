package net.weibai.bakeries.common.init;

import lombok.Getter;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.weibai.bakeries.BakeriesMod;
import net.weibai.bakeries.common.blocks.bread.*;
import net.weibai.bakeries.common.blocks.oven.OvenBlock;
import net.weibai.bakeries.common.blocks.oven.OvenBlockEntity;

import java.util.function.Function;
import java.util.function.Supplier;
import java.util.function.ToIntFunction;

public class BakeriesBlocks {
    @Getter
    private static final DeferredRegister.Blocks REGISTER = DeferredRegister.createBlocks(BakeriesMod.MODID);

    /*面包方块*/

    /**贝果*/
    public static final DeferredBlock<Bagel> BAGEL;
    /**全麦贝果*/
    public static final DeferredBlock<Bagel> WHOLE_WHEAT_BAGEL;
    /**圆面包*/
    public static final DeferredBlock<RoundBread> ROUND_BREAD;
    /**莓果面包*/
    public static final DeferredBlock<RoundBread> BERRY_BREAD;
    /**乳酪面包*/
    public static final DeferredBlock<RoundBread> CHEESE_CREAM_BREAD;
    /**红糖卷*/
    public static final DeferredBlock<BrownSugarRoll> BROWN_SUGAR_ROLL;
    /**菠萝包*/
    public static final DeferredBlock<PineappleBun> PINEAPPLE_BUN;
    /**肉松面包卷*/
    public static final DeferredBlock<MeatFlossBreadRoll> MEAT_FLOSS_BREAD_ROLL;
    /**可颂*/
    public static final DeferredBlock<Croissant> CROISSANT;
    /**脏脏包*/
    public static final DeferredBlock<Croissant> DIRTY_CHOCO_CROISSANT;
    /**盐可颂*/
    public static final DeferredBlock<SaltCroissant> SALT_CROISSANT;
    /**恰巴塔面包*/
    public static final DeferredBlock<Ciabatta> CIABATTA;
    /**佛卡夏面包*/
    public static final DeferredBlock<Focaccia> FOCACCIA;
    /**浆果贝果*/
    public static final DeferredBlock<BerryBagel> BERRY_BAGEL;
    /**填酱贝果*/
    public static final DeferredBlock<BagelFilledSauce> BAGEL_FILLED_SAUCE;
    /**填馅法棍*/
    public static final DeferredBlock<BaguetteWithFilling> BAGUETTE_WITH_FILLING;
    /**番茄奶酪可颂三明治*/
    public static final DeferredBlock<TomatoCheeseCroissantSandwich> TOMATO_CHEESE_CROISSANT_SANDWICH;
    /**法棍*/
    public static final DeferredBlock<Baguette> BAGUETTE;
    /**吐司*/
    public static final DeferredBlock<Block> TOAST;



    public static final DeferredBlock<OvenBlock> OVEN;
    static {
        /*面包方块*/
        BAGEL = register("bagel", Bagel::new);
        WHOLE_WHEAT_BAGEL = register("whole_wheat_bagel", Bagel::new);
        ROUND_BREAD = register("round_bread", RoundBread::new);
        BERRY_BREAD = register("berry_bread", RoundBread::new);
        CHEESE_CREAM_BREAD = register("cheese_cream_bread", RoundBread::new);
        BROWN_SUGAR_ROLL = register("brown_sugar_roll", BrownSugarRoll::new);
        PINEAPPLE_BUN = register("pineapple_bun", PineappleBun::new);
        MEAT_FLOSS_BREAD_ROLL = register("meat_floss_bread_roll", MeatFlossBreadRoll::new);
        CROISSANT = register("croissant", Croissant::new);
        DIRTY_CHOCO_CROISSANT = register("dirty_choco_croissant", Croissant::new);
        SALT_CROISSANT = register("salt_croissant", SaltCroissant::new);
        CIABATTA = register("ciabatta",Ciabatta::new);
        FOCACCIA = register("focaccia",Focaccia::new);
        BERRY_BAGEL = register("berry_bagel",BerryBagel::new);
        BAGEL_FILLED_SAUCE = register("bagel_filled_sauce",BagelFilledSauce::new);
        BAGUETTE_WITH_FILLING = register("baguette_with_filling",BaguetteWithFilling::new);
        TOMATO_CHEESE_CROISSANT_SANDWICH = register("tomato_cheese_croissant_sandwich",TomatoCheeseCroissantSandwich::new);
        BAGUETTE = register("baguette", Baguette::new);
        TOAST = register("toast", () ->
                new Block(BlockBehaviour.Properties.of().sound(SoundType.WOOL).strength(0.5F,0.5F)));

        OVEN = registerBlock("oven", OvenBlock::new, BlockBehaviour.Properties.of().strength(3.5F));
    }


    private static<B extends Block> DeferredBlock<B> registerBlock(String name, Function<BlockBehaviour.Properties, B> func, BlockBehaviour.Properties props) {
        return REGISTER.registerBlock(name, func, props);
    }
    private static<B extends Block> DeferredBlock<B> registerBlock(String name, Function<BlockBehaviour.Properties, B> func) {
        return REGISTER.registerBlock(name, func);
    }
    private static<B extends Block> DeferredBlock<B> register(String name, Supplier<B> block) {
        return REGISTER.register(name, block);
    }
    private static ToIntFunction<BlockState> litBlockEmission(int lightValue) {
        return state -> state.getValue(BlockStateProperties.LIT) ? lightValue : 0;
    }
    public static class MSBlockEntities {
        @Getter
        private static final DeferredRegister<BlockEntityType<?>> REGISTER = DeferredRegister.create(BuiltInRegistries.BLOCK_ENTITY_TYPE, BakeriesMod.MODID);
        public static final Supplier<BlockEntityType<OvenBlockEntity>> OVEN_BLOCK_ENTITY = REGISTER.register(
                "cauldron_block_entity",
                () -> BlockEntityType.Builder.of(
                        OvenBlockEntity::new,
                        OVEN.get()
                ).build(null));
//        public static final Supplier<BlockEntityType<MSFluidPipeBlockEntity>> FLUID_PIPE_BLOCK_ENTITY = REGISTER.register(
//                "fluid_pipe_block_entity",
//                () -> BlockEntityType.Builder.of(
//                        MSFluidPipeBlockEntity::new,
//                        FLUID_PIPE.get()
//                ).build(null));


//        public static final Supplier<BlockEntityType<ItemBlockEntity>> ITEM_BLOCK_ENTITY = REGISTER.register(
//                "item_block_entity",
//                () -> BlockEntityType.Builder.of(ItemBlockEntity::new, ITEM_BLOCK.get()).build(null));
//        public static final Supplier<BlockEntityType<SoilBurningBlockEntity>> SOIL_BURNING_BLOCK_ENTITY = REGISTER.register(
//                "soil_burning_block_entity",
//                () -> BlockEntityType.Builder.of(
//                        SoilBurningBlockEntity::new,
//                        OAK_SOIL_BURNING_BLOCK.get(),
//                        SPRUCE_SOIL_BURNING_BLOCK.get(),
//                        BIRCH_SOIL_BURNING_BLOCK.get(),
//                        DARK_OAK_SOIL_BURNING_BLOCK.get(),
//                        ACACIA_SOIL_BURNING_BLOCK.get(),
//                        JUNGLE_SOIL_BURNING_BLOCK.get(),
//                        MANGROVE_SOIL_BURNING_BLOCK.get(),
//                        CHERRY_SOIL_BURNING_BLOCK.get(),
//                        BAMBOO_SOIL_BURNING_BLOCK.get(),
//                        WARPED_STEM_SOIL_BURNING_BLOCK.get(),
//                        CRIMSON_STEM_SOIL_BURNING_BLOCK.get()).build(null));

    }
}
