package com.renyigesai.bakeries.init;

import com.renyigesai.bakeries.BakeriesMod;
import com.renyigesai.bakeries.api.item.FoodBlockItem;
import com.renyigesai.bakeries.fluid.BakeriesFluids;
import com.renyigesai.bakeries.item.BaguetteItem;
import com.renyigesai.bakeries.item.FlourSieveItem;
import com.renyigesai.bakeries.item.RawItem;
import com.renyigesai.bakeries.item.ShakeItem;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.*;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.Nullable;

public class BakeriesItems {
    public static final DeferredRegister<Item> REGISTER = DeferredRegister.create(ForgeRegistries.ITEMS, BakeriesMod.MODID);
    public static final RegistryObject<Item> FLOUR;
    public static final RegistryObject<Item> WHOLE_WHEAT_FLOUR;
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
    public static final RegistryObject<Item> WHOLE_WHEAT_BAGEL;
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
    public static final RegistryObject<Item> SWEET_DOUGH_KNEAD;
    public static final RegistryObject<Item> SWEET_DOUGH;
    public static final RegistryObject<Item> WHOLE_WHEAT_DOUGH_KNEAD;
    public static final RegistryObject<Item> WHOLE_WHEAT_DOUGH;
    public static final RegistryObject<Item> SALTED_DOUGH_KNEAD;
    public static final RegistryObject<Item> SALTED_DOUGH;
    public static final RegistryObject<Item> WHOLE_WHEAT_BAGEL_DOUGH;
    public static final RegistryObject<Item> SALT_WATER_BUCKET;
    public static final RegistryObject<Item> BOTTLE_MILK;
    public static final RegistryObject<Item> BOTTLE_CREAM;
    public static final RegistryObject<Item> BOTTLE_BUTTER;
    public static final RegistryObject<Item> FLOUR_SIEVE;

    static {
        FLOUR = item("flour");
        WHOLE_WHEAT_FLOUR = item("whole_wheat_flour");
        SALT = item("salt");
        BUTTER_CUBE = item("butter_cube");
        BROWN_SUGAR_CUBE = item("brown_sugar_cube");
        BAGEL_DOUGH = rawItem("bagel_dough","200");
        BAGUETTE_DOUGH = rawItem("baguette_dough","215");
        CINNAMON_ROLL_DOUGH = rawItem("cinnamon_roll_dough","170");
        COUNTRY_BREAD_DOUGH = rawItem("country_bread_dough","225");
        CROISSANT_DOUGH = rawItem("croissant_dough","155");
        PINEAPPLE_BUN_DOUGH = rawItem("pineapple_bun_dough","155");
        RAW_EGG_TART = item("raw_egg_tart");
        RAW_PUMPKIN_PIE = item("raw_pumpkin_pie");
        RAW_TARE_CRUST = item("raw_tare_crust");
        ROUND_BREAD_DOUGH = rawItem("round_bread_dough","155");
        SALT_CROISSANT_DOUGH = rawItem("salt_croissant_dough","165");
        WHOLE_WHEAT_BAGEL_DOUGH = rawItem("whole_wheat_bagel_dough","190");
        TART_SHELL = item("tart_shell");
        OVEN = block(BakeriesBlocks.OVEN);
        FERMENTATION_TANK = block(BakeriesBlocks.FERMENTATION_TANK);
        YEAST_TANK = block(BakeriesBlocks.YEAST_TANK);
        CHEESE_TANK = block(BakeriesBlocks.CHEESE_TANK);
        BOTTLE_YEAST = item("bottle_yeast");
        GLASS_CABINET_DOOR = block(BakeriesBlocks.GLASS_CABINET_DOOR);
        SALT_ORE = block(BakeriesBlocks.SALT_ORE);
        COARSE_SALT = item("coarse_salt");
        DOUGH_CRAFTING_TABLE = block(BakeriesBlocks.DOUGH_CRAFTING_TABLE);
        SWEET_DOUGH_KNEAD = block(BakeriesBlocks.SWEET_DOUGH_KNEAD);
        SWEET_DOUGH = item("sweet_dough");
        WHOLE_WHEAT_DOUGH_KNEAD = block(BakeriesBlocks.WHOLE_WHEAT_DOUGH_KNEAD);
        WHOLE_WHEAT_DOUGH = item("whole_wheat_dough");
        SALTED_DOUGH_KNEAD = block(BakeriesBlocks.SALTED_DOUGH_KNEAD);
        SALTED_DOUGH = item("salted_dough");
        SALT_WATER_BUCKET = REGISTER.register("salt_water_bucket",()->new BucketItem(BakeriesFluids.SALT_WATER,new Item.Properties()));
        BOTTLE_MILK = REGISTER.register("bottle_milk",()->new ShakeItem(new Item.Properties().stacksTo(1), BakeriesItems.BOTTLE_CREAM));
        BOTTLE_CREAM = REGISTER.register("bottle_cream",()->new ShakeItem(new Item.Properties().stacksTo(1), BakeriesItems.BOTTLE_BUTTER));
        BOTTLE_BUTTER = REGISTER.register("bottle_butter",()->new Item(new Item.Properties().craftRemainder(Items.GLASS_BOTTLE).stacksTo(1)));
        FLOUR_SIEVE = REGISTER.register("flour_sieve",()->new FlourSieveItem(new Item.Properties().stacksTo(1).defaultDurability(250)));

        //Bread Items
        BAGEL = foodBlockItem(BakeriesBlocks.BAGEL, BakeriesFoodProperties.BAGEL);
        WHOLE_WHEAT_BAGEL = foodBlockItem(BakeriesBlocks.WHOLE_WHEAT_BAGEL, BakeriesFoodProperties.WHOLE_WHEAT_BAGEL,true);
        BAGUETTE = REGISTER.register(BakeriesBlocks.BAGUETTE.getId().getPath(),() -> new BaguetteItem(BakeriesBlocks.BAGUETTE.get(),new Item.Properties().durability(4)));
        CINNAMON_ROLL = foodBlockItem(BakeriesBlocks.CINNAMON_ROLL, BakeriesFoodProperties.CINNAMON_ROLL,true);
        COUNTRY_BREAD = foodBlockItem(BakeriesBlocks.COUNTRY_BREAD, BakeriesFoodProperties.COUNTRY_BREAD);
        CROISSANT = foodBlockItem(BakeriesBlocks.CROISSANT, BakeriesFoodProperties.CROISSANT);
        PINEAPPLE_BUN = foodBlockItem(BakeriesBlocks.PINEAPPLE_BUN,BakeriesFoodProperties.PINEAPPLE_BUN);
        ROUND_BREAD = foodBlockItem(BakeriesBlocks.ROUND_BREAD,BakeriesFoodProperties.ROUND_BREAD);
        SALT_CROISSANT = foodBlockItem(BakeriesBlocks.SALT_CROISSANT,BakeriesFoodProperties.SALT_CROISSANT,true);
        TOAST = block(BakeriesBlocks.TOAST);
        SLICED_TOAST = foodItem("sliced_toast",BakeriesFoodProperties.SLICED_TOAST);
    }

    private static RegistryObject<Item> rawItem(String pName,String tips) {
        return REGISTER.register(pName, () -> new RawItem(new Item.Properties(),tips));
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

    private static RegistryObject<Item> foodBlockItem(RegistryObject<Block> block, FoodProperties foodProperties,boolean effectTooltip) {
        return REGISTER.register(block.getId().getPath(), () -> new FoodBlockItem(block.get(), new Item.Properties().food(foodProperties),effectTooltip));
    }

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
