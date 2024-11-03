package com.renyigesai.bakery.init;

import com.renyigesai.bakery.BakeryMod;
import com.renyigesai.bakery.api.item.FoodBlockItem;
import com.renyigesai.bakery.item.BaguetteItem;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemNameBlockItem;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.Nullable;

public class BakeryItems {
    public static final DeferredRegister<Item> REGISTER = DeferredRegister.create(ForgeRegistries.ITEMS, BakeryMod.MODID);
    public static final RegistryObject<Item> FLOUR;
    public static final RegistryObject<Item> FLOUR_RYE;
    public static final RegistryObject<Item> SALT;
    public static final RegistryObject<Item> BUTTER_CUBE;
    public static final RegistryObject<Item> BROWN_SUGAR_CUBE;
    public static final RegistryObject<Item> BAGEL_DOUGH;
    public static final RegistryObject<Item> BAGUETTE_DOUGH;
    public static final RegistryObject<Item> CINNAMON_ROLL_DOUGH;
    public static final RegistryObject<Item> COUNTRY_BREAD_DOUGH;
    public static final RegistryObject<Item> CROISSANT_DOUGH;
    public static final RegistryObject<Item> PINEAPPLE_BUN_DOUGH;
    public static final RegistryObject<Item> RAW_EGG_TART;
    public static final RegistryObject<Item> RAW_PUMPKIN_PIE;
    public static final RegistryObject<Item> RAW_TARE_CRUST;
    public static final RegistryObject<Item> ROUND_BREAD_DOUGH;
    public static final RegistryObject<Item> SALT_CROISSANT_DOUGH;
    public static final RegistryObject<Item> TART_SHELL;
    public static final RegistryObject<Item> BAGEL;
    public static final RegistryObject<Item> BAGUETTE;
    public static final RegistryObject<Item> CINNAMON_ROLL;
    public static final RegistryObject<Item> COUNTRY_BREAD;
    public static final RegistryObject<Item> CROISSANT;
    public static final RegistryObject<Item> PINEAPPLE_BUN;
    public static final RegistryObject<Item> ROUND_BREAD;
    public static final RegistryObject<Item> SALT_CROISSANT;
    public static final RegistryObject<Item> OVEN;
    public static final RegistryObject<Item> FERMENTATION_TANK;
    public static final RegistryObject<Item> YEAST_TANK;
    public static final RegistryObject<Item> CHEESE_TANK;
    public static final RegistryObject<Item> BOTTLE_YEAST;
    public static final RegistryObject<Item> GLASS_CABINET_DOOR;
    public static final RegistryObject<Item> COARSE_SALT;
    public static final RegistryObject<Item> SALT_ORE;
    public static final RegistryObject<Item> SLICED_TOAST;
    public static final RegistryObject<Item> TOAST;
    public static final RegistryObject<Item> DOUGH_CRAFTING_TABLE;


    static {
        FLOUR = item("flour");
        FLOUR_RYE = item("flour_rye");
        SALT = item("salt");
        BUTTER_CUBE = item("butter_cube");
        BROWN_SUGAR_CUBE = item("brown_sugar_cube");
        BAGEL_DOUGH = item("bagel_dough");
        BAGUETTE_DOUGH = item("baguette_dough");
        CINNAMON_ROLL_DOUGH = item("cinnamon_roll_dough");
        COUNTRY_BREAD_DOUGH = item("country_bread_dough");
        CROISSANT_DOUGH = item("croissant_dough");
        PINEAPPLE_BUN_DOUGH = item("pineapple_bun_dough");
        RAW_EGG_TART = item("raw_egg_tart");
        RAW_PUMPKIN_PIE = item("raw_pumpkin_pie");
        RAW_TARE_CRUST = item("raw_tare_crust");
        ROUND_BREAD_DOUGH = item("round_bread_dough");
        SALT_CROISSANT_DOUGH = item("salt_croissant_dough");
        TART_SHELL = item("tart_shell");
        OVEN = block(BakeryBlocks.OVEN);
        FERMENTATION_TANK = block(BakeryBlocks.FERMENTATION_TANK);
        YEAST_TANK = block(BakeryBlocks.YEAST_TANK);
        CHEESE_TANK = block(BakeryBlocks.CHEESE_TANK);
        BOTTLE_YEAST = item("bottle_yeast");
        GLASS_CABINET_DOOR = block(BakeryBlocks.GLASS_CABINET_DOOR);
        SALT_ORE = block(BakeryBlocks.SALT_ORE);
        COARSE_SALT = item("coarse_salt");
        DOUGH_CRAFTING_TABLE = block(BakeryBlocks.DOUGH_CRAFTING_TABLE);

        //Bread Items
        BAGEL = foodBlockItem(BakeryBlocks.BAGEL, BakeryFoodProperties.BAGEL);
        BAGUETTE = REGISTER.register(BakeryBlocks.BAGUETTE.getId().getPath(),() -> new BaguetteItem(BakeryBlocks.BAGUETTE.get(),new Item.Properties().food(BakeryFoodProperties.BAGUETTE)));
        CINNAMON_ROLL = foodBlockItem(BakeryBlocks.CINNAMON_ROLL, BakeryFoodProperties.CINNAMON_ROLL);
        COUNTRY_BREAD = foodBlockItem(BakeryBlocks.COUNTRY_BREAD, BakeryFoodProperties.COUNTRY_BREAD);
        CROISSANT = foodBlockItem(BakeryBlocks.CROISSANT, BakeryFoodProperties.CROISSANT);
        PINEAPPLE_BUN = foodBlockItem(BakeryBlocks.PINEAPPLE_BUN,BakeryFoodProperties.PINEAPPLE_BUN);
        ROUND_BREAD = foodBlockItem(BakeryBlocks.ROUND_BREAD,BakeryFoodProperties.ROUND_BREAD);
        SALT_CROISSANT = foodBlockItem(BakeryBlocks.SALT_CROISSANT,BakeryFoodProperties.SALT_CROISSANT);
        TOAST = block(BakeryBlocks.TOAST);
        SLICED_TOAST = foodItem("sliced_toast",BakeryFoodProperties.SLICED_TOAST);
    }

    private static RegistryObject<Item> item(String pName) {
        return REGISTER.register(pName, () -> new Item(new Item.Properties()));
    }
    private static RegistryObject<Item> block(RegistryObject<Block> block) {
        return REGISTER.register(block.getId().getPath(), () -> new BlockItem(block.get(), new Item.Properties()));
    }

    private static RegistryObject<Item> foodBlockItem(RegistryObject<Block> block, FoodProperties foodProperties) {
        return REGISTER.register(block.getId().getPath(), () -> new FoodBlockItem(block.get(), new Item.Properties().food(foodProperties)));
    }
//    private static RegistryObject<Item> foodBlockItemaa(RegistryObject<Block> block, FoodProperties foodProperties) {
//        return REGISTER.register(block.getId().getPath(), () -> new (block.get(), new Item.Properties().food(foodProperties)));
//    }
    private static RegistryObject<Item> foodItem(String pName, FoodProperties foodProperties) {
        return REGISTER.register(pName, () -> new Item(new Item.Properties().food(foodProperties)));
    }


//先留着
    private static<T extends Comparable<T>, V extends T> RegistryObject<Item>  blockState(String name, RegistryObject<Block> block
            , Property<T> integerProperty, V vaul) {
        return REGISTER.register(name, () -> new ItemNameBlockItem(block.get(), new Item.Properties())
                {
                    @Nullable
                    @Override
                    protected BlockState getPlacementState(BlockPlaceContext placeContext) {
                        return this.canPlace(placeContext, this.getBlock().defaultBlockState().setValue(integerProperty,vaul)) ?
                                this.getBlock().defaultBlockState().setValue(integerProperty,vaul) : null;
                    }
                    @Override
                    protected boolean placeBlock(BlockPlaceContext placeContext, BlockState state) {
                        return placeContext.getLevel().setBlock(placeContext.getClickedPos(),
                                this.getBlock().defaultBlockState().setValue(integerProperty,vaul), 11);
                    }
                }
        );
    }
    private static <T extends Comparable<T>, U extends Comparable<U>> RegistryObject<Item>  blockState(String name, RegistryObject<Block> block
            , Property<T> integerProperty, T vaul
            , Property<U> integerProperty1, U vaul1) {
        return REGISTER.register(name, () -> new ItemNameBlockItem(block.get(), new Item.Properties())
                {
                    @Nullable
                    @Override
                    protected BlockState getPlacementState(BlockPlaceContext placeContext) {
                        return this.canPlace(placeContext, this.getBlock().defaultBlockState().setValue(integerProperty,vaul).setValue(integerProperty1,vaul1)) ?
                                this.getBlock().defaultBlockState().setValue(integerProperty,vaul).setValue(integerProperty1,vaul1) : null;
                    }
                    @Override
                    protected boolean placeBlock(BlockPlaceContext placeContext, BlockState state) {
                        return placeContext.getLevel().setBlock(placeContext.getClickedPos(),
                                this.getBlock().defaultBlockState().setValue(integerProperty,vaul).setValue(integerProperty1,vaul1), 11);
                    }
                }
        );
    }
    private static<T extends Comparable<T>, U extends Comparable<U>,A extends Comparable<A>> RegistryObject<Item>  blockState(String name, RegistryObject<Block> block
            , Property<T> integerProperty, T vaul
            , Property<U> integerProperty1, U vaul1
            , Property<A> integerProperty2, A vaul2) {
        return REGISTER.register(name, () -> new ItemNameBlockItem(block.get(), new Item.Properties())
                {
                    @Nullable
                    @Override
                    protected BlockState getPlacementState(BlockPlaceContext placeContext) {
                        return this.canPlace(placeContext, this.getBlock().defaultBlockState().setValue(integerProperty,vaul).setValue(integerProperty1,vaul1).setValue(integerProperty2,vaul2)) ?
                                this.getBlock().defaultBlockState().setValue(integerProperty,vaul).setValue(integerProperty1,vaul1).setValue(integerProperty2,vaul2) : null;
                    }
                    @Override
                    protected boolean placeBlock(BlockPlaceContext placeContext, BlockState state) {
                        return placeContext.getLevel().setBlock(placeContext.getClickedPos(),
                                this.getBlock().defaultBlockState().setValue(integerProperty,vaul).setValue(integerProperty1,vaul1).setValue(integerProperty2,vaul2), 11);
                    }
                }
        );
    }
}
