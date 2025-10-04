package net.weibai.bakeries.common.init;

import lombok.Getter;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.weibai.bakeries.BakeriesMod;

import java.util.function.Function;
import java.util.function.Supplier;
import java.util.function.ToIntFunction;

public class BakeriesBlocks {
    @Getter
    private static final DeferredRegister.Blocks REGISTER = DeferredRegister.createBlocks(BakeriesMod.MODID);


    private static DeferredBlock<Block> registerSimpleBlock(String name, BlockBehaviour.Properties props) {
        return REGISTER.registerSimpleBlock(name, props);
    }
    private static DeferredBlock<Block> registerSimpleBlock(String name) {
        return REGISTER.registerSimpleBlock(name);
    }
    private static DeferredBlock<Block> registerBlock(String name, Function<BlockBehaviour.Properties, ? extends Block> func, BlockBehaviour.Properties props) {
        return REGISTER.registerBlock(name, func, props);
    }

    private static DeferredBlock<Block> registerBlock(String name, Function<BlockBehaviour.Properties, ? extends Block> func) {
        return REGISTER.registerBlock(name, func);
    }
    private static DeferredBlock<Block> register(String name, Supplier<? extends Block> block) {
        return REGISTER.register(name, block);
    }
    private static ToIntFunction<BlockState> litBlockEmission(int lightValue) {
        return state -> state.getValue(BlockStateProperties.LIT) ? lightValue : 0;
    }
    public static class MSBlockEntities {
        @Getter
        private static final DeferredRegister<BlockEntityType<?>> REGISTER = DeferredRegister.create(BuiltInRegistries.BLOCK_ENTITY_TYPE, BakeriesMod.MODID);
//        public static final Supplier<BlockEntityType<MSCauldronBlockEntity>> CAULDRON_BLOCK_ENTITY = REGISTER.register(
//                "cauldron_block_entity",
//                () -> BlockEntityType.Builder.of(
//                        MSCauldronBlockEntity::new,
//                        OAK_LOG_CAULDRON.get(),
//                        DARK_OAK_LOG_CAULDRON.get(),
//                        BIRCH_LOG_CAULDRON.get(),
//                        JUNGLE_LOG_CAULDRON.get(),
//                        SPRUCE_LOG_CAULDRON.get(),
//                        ACACIA_LOG_CAULDRON.get(),
//                        MANGROVE_LOG_CAULDRON.get(),
//                        CHERRY_LOG_CAULDRON.get(),
//                        CRIMSON_STEM_CAULDRON.get(),
//                        WARPED_STEM_CAULDRON.get()
//                        ).build(null));
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
