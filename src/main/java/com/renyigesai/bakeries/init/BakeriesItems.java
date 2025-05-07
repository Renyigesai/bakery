package com.renyigesai.bakeries.init;

import com.renyigesai.bakeries.BakeriesMod;
import com.renyigesai.bakeries.api.block.PileBlock;
import com.renyigesai.bakeries.api.item.FoodBlockItem;
import com.renyigesai.bakeries.fluid.BakeriesFluids;
import com.renyigesai.bakeries.item.*;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.*;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.Nullable;


public class BakeriesItems {
    public static final DeferredRegister<Item> REGISTER = DeferredRegister.create(ForgeRegistries.ITEMS, BakeriesMod.MODID);
    /*
    一般物品/食物
    */
    public static final RegistryObject<Item> FLOUR;
    public static final RegistryObject<Item> WHOLE_WHEAT_FLOUR;
    public static final RegistryObject<Item> SALT;
    public static final RegistryObject<Item> BUTTER_CUBE;
    public static final RegistryObject<Item> BROWN_SUGAR_CUBE;
    public static final RegistryObject<Item> RAW_EGG_TART;
    public static final RegistryObject<Item> RAW_PUMPKIN_PIE;
    public static final RegistryObject<Item> RAW_TARE_CRUST;
    public static final RegistryObject<Item> TART_SHELL;
    public static final RegistryObject<Item> OLIVE;
    public static final RegistryObject<Item> OLIVE_OIL;
    public static final RegistryObject<Item> MEAT_FLOSS_BREAD_DOUGH;
    public static final RegistryObject<Item> MEAT_FLOSS;
    public static final RegistryObject<Item> COCOA_POWDER;
    public static final RegistryObject<Item> CHEESE_CUBE;
    public static final RegistryObject<Item> TOMATO;
    public static final RegistryObject<Item> GROUND_COFFEE;
    public static final RegistryObject<Item> RAW_COFFEE_BEAN;
    public static final RegistryObject<Item> COFFEE_BEAN;
    public static final RegistryObject<Item> PASTRY;
    public static final RegistryObject<Item> ROUND_DOUGH_DRAWING;
    public static final RegistryObject<Item> BAGEL_DOUGH_DRAWING;
    public static final RegistryObject<Item> WHOLE_WHEAT_BAGEL_DOUGH_DRAWING;
    public static final RegistryObject<Item> BAGUETTE_DOUGH_DRAWING;
    public static final RegistryObject<Item> CIABATTA_DOUGH_DRAWING;
    public static final RegistryObject<Item> SALT_WATER_BUCKET;
    public static final RegistryObject<Item> BOTTLE_MILK;
    public static final RegistryObject<Item> BOTTLE_CREAM;
    public static final RegistryObject<Item> BOTTLE_BUTTER;
    public static final RegistryObject<Item> HONEY_BUTTER;
    /*
    功能物品
    */
    public static final RegistryObject<Item> BREAD_KNIFE;
    public static final RegistryObject<Item> FLOUR_SIEVE;
    public static final RegistryObject<Item> BAYSALT_FRAME;
    public static final RegistryObject<Item> ETERNAL_BAGUETTE;
    /*
    生物品/面团
    */
    public static final RegistryObject<Item> SWEET_DOUGH;
    public static final RegistryObject<Item> WHOLE_WHEAT_DOUGH;
    public static final RegistryObject<Item> SALTED_DOUGH;
    public static final RegistryObject<Item> COCOA_DOUGH;
    public static final RegistryObject<Item> BAGEL_DOUGH;
    public static final RegistryObject<Item> BAGUETTE_DOUGH;
    public static final RegistryObject<Item> BROWN_SUGAR_ROLL_DOUGH;
    public static final RegistryObject<Item> COUNTRY_BREAD_DOUGH;
    public static final RegistryObject<Item> CROISSANT_DOUGH;
    public static final RegistryObject<Item> PINEAPPLE_BUN_DOUGH;
    public static final RegistryObject<Item> ROUND_BREAD_DOUGH;
    public static final RegistryObject<Item> BERRY_BREAD_DOUGH;
    public static final RegistryObject<Item> SALT_CROISSANT_DOUGH;
    public static final RegistryObject<Item> WHOLE_WHEAT_BAGEL_DOUGH;
    /*
    面包物品
    */
    public static final RegistryObject<Item> BAGEL;
    public static final RegistryObject<Item> WHOLE_WHEAT_BAGEL;
    public static final RegistryObject<Item> BAGUETTE;
    public static final RegistryObject<Item> BROWN_SUGAR_ROLL;
    public static final RegistryObject<Item> COUNTRY_BREAD;
    public static final RegistryObject<Item> COUNTRY_BREAD_SLICE;
    public static final RegistryObject<Item> CROISSANT;
    public static final RegistryObject<Item> CIABATTA;
    public static final RegistryObject<Item> PINEAPPLE_BUN;
    public static final RegistryObject<Item> ROUND_BREAD;
    public static final RegistryObject<Item> SALT_CROISSANT;
    public static final RegistryObject<Item> BERRY_BREAD;
    public static final RegistryObject<Item> FOCACCIA;
    public static final RegistryObject<Item> TOAST;
    public static final RegistryObject<Item> SLICED_TOAST;
    public static final RegistryObject<Item> CHEESE_COCOA_TOAST;
    public static final RegistryObject<Item> SLICED_CHEESE_COCOA_TOAST;
    public static final RegistryObject<Item> CIABATTA_DOUGH;
    public static final RegistryObject<Item> PIZZA;
    public static final RegistryObject<Item> SAUSAGE_PIZZA;
    public static final RegistryObject<Item> MEAT_PASTE_PIZZA;
    public static final RegistryObject<Item> BAGUETTE_WITH_FILLING;
    public static final RegistryObject<Item> MEAT_FLOSS_BREAD;
    public static final RegistryObject<Item> DIRTY_CHOCO_CROISSANT;
    public static final RegistryObject<Item> TOMATO_CHEESE_CROISSANT_SANDWICH;
    public static final RegistryObject<Item> BERRY_BAGEL;
    public static final RegistryObject<Item> HONEY_BUTTER_SPREAD_TOAST;
    /*
    方块物品/一般物品
    */
    public static final RegistryObject<Item> OVEN;
    public static final RegistryObject<Item> FERMENTATION_TANK;
    public static final RegistryObject<Item> YEAST_TANK;
    public static final RegistryObject<Item> CHEESE_TANK;
    public static final RegistryObject<Item> BOTTLE_YEAST;
    public static final RegistryObject<Item> GLASS_CABINET_DOOR;
    public static final RegistryObject<Item> GLASS_CABINET_DOOR_TWO;
    public static final RegistryObject<Item> COARSE_SALT;
    public static final RegistryObject<Item> SALT_ORE;
    public static final RegistryObject<Item> DEEPSLATE_SALT_ORE;
    public static final RegistryObject<Item> DOUGH_CRAFTING_TABLE;
    public static final RegistryObject<Item> MOULD;
    public static final RegistryObject<Item> MOULD_TOAST_DOUGH;
    public static final RegistryObject<Item> MOULD_TOAST;
    public static final RegistryObject<Item> MOULD_CHEESE_COCOA_TOAST;
    public static final RegistryObject<Item> RAW_SALT_BLOCK;
    public static final RegistryObject<Item> MILK_TANK;
    public static final RegistryObject<Item> WOOD_COUNTER;
    public static final RegistryObject<Item> BLACK_WHITE_CONCRETE;
    public static final RegistryObject<Item> BREAD_BASKET;
    public static final RegistryObject<Item> TOASTER;
    public static final RegistryObject<Item> CUPBOARD;
    public static final RegistryObject<Item> SALT_SCRAPER_RAKE;
    public static final RegistryObject<Item> MOULD_CHEESE_COCOA_TOAST_DOUGH;
    public static final RegistryObject<Item> BREAD_HOLDERS;
    public static final RegistryObject<Item> FLOUR_BAG;
    public static final RegistryObject<Item> WHOLE_WHEAT_FLOUR_BAG;
    public static final RegistryObject<Item> BLENDER;
    public static final RegistryObject<Item> MOKA_POT;
    public static final RegistryObject<Item> MOKA_POT_FILL;
    public static final RegistryObject<Item> FOCACCIA_DOUGH;
    public static final RegistryObject<Item> MENU;
    public static final RegistryObject<Item> WOOD_TRAY;
    public static final RegistryObject<Item> DRINK_CUP;
    /*
    饮料物品
    */
    public static final RegistryObject<Item> ICED_LATTE;
    public static final RegistryObject<Item> BROWN_SUGAR_LATTE;
    public static final RegistryObject<Item> ICED_AMERICAN;
    public static final RegistryObject<Item> CREAM_BINGLE_COFFEE;

    static {
        /*
        一般物品/食物
        */
        FLOUR = item("flour");
        WHOLE_WHEAT_FLOUR = item("whole_wheat_flour");
        SALT = item("salt");
        BUTTER_CUBE = item("butter_cube");
        BROWN_SUGAR_CUBE = item("brown_sugar_cube");
        BAGEL_DOUGH = rawItem("bagel_dough","200");
        BAGUETTE_DOUGH = rawItem("baguette_dough","230");
        BROWN_SUGAR_ROLL_DOUGH = rawItem("brown_sugar_roll_dough","155");
        COUNTRY_BREAD_DOUGH = rawItem("country_bread_dough","225");
        CROISSANT_DOUGH = rawItem("croissant_dough","175");
        PINEAPPLE_BUN_DOUGH = rawItem("pineapple_bun_dough","170");
        RAW_EGG_TART = item("raw_egg_tart");
        RAW_PUMPKIN_PIE = item("raw_pumpkin_pie");
        RAW_TARE_CRUST = item("raw_tare_crust");
        ROUND_BREAD_DOUGH = rawItem("round_bread_dough","155");
        BERRY_BREAD_DOUGH = rawItem("berry_bread_dough","185");
        SALT_CROISSANT_DOUGH = rawItem("salt_croissant_dough","180");
        WHOLE_WHEAT_BAGEL_DOUGH = rawItem("whole_wheat_bagel_dough","200");
        TART_SHELL = item("tart_shell");
        COARSE_SALT = item("coarse_salt");
        SWEET_DOUGH = item("sweet_dough");
        WHOLE_WHEAT_DOUGH = item("whole_wheat_dough");
        SALTED_DOUGH = item("salted_dough");
        COCOA_DOUGH = item("cocoa_dough");
        SALT_WATER_BUCKET = REGISTER.register("salt_water_bucket",()->new BucketItem(BakeriesFluids.SALT_WATER,new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1)));
        BOTTLE_MILK = REGISTER.register("bottle_milk",()->new ShakeItem(new Item.Properties().craftRemainder(Items.GLASS_BOTTLE).stacksTo(16), BakeriesItems.BOTTLE_CREAM));
        BOTTLE_CREAM = REGISTER.register("bottle_cream",()->new ShakeItem(new Item.Properties().craftRemainder(Items.GLASS_BOTTLE).stacksTo(16), BakeriesItems.BOTTLE_BUTTER));
        BOTTLE_BUTTER = REGISTER.register("bottle_butter",()->new Item(new Item.Properties().craftRemainder(Items.GLASS_BOTTLE).stacksTo(16)));
        HONEY_BUTTER = item("honey_butter");
        /*
        功能物品
        */
        BREAD_KNIFE = REGISTER.register("bread_knife",()-> new BreadKnifeItem(0.5F,-0.2F,Tiers.IRON,new Item.Properties()));
        FLOUR_SIEVE = REGISTER.register("flour_sieve",()->new FlourSieveItem(new Item.Properties().stacksTo(1).defaultDurability(250)));
        ETERNAL_BAGUETTE = REGISTER.register("eternal_baguette",()-> new EternalBaguetteItem(new Item.Properties().stacksTo(1).rarity(Rarity.EPIC)));

        MOULD = block(BakeriesBlocks.MOULD);
        MOULD_TOAST_DOUGH = rawItem("mould_toast_dough","135");
        RAW_SALT_BLOCK = block(BakeriesBlocks.RAW_SALT_BLOCK);
        WOOD_COUNTER = block(BakeriesBlocks.WOOD_COUNTER);
        PASTRY = item("pastry");
        CIABATTA_DOUGH = rawItem("ciabatta_dough","210");
        BLACK_WHITE_CONCRETE = block(BakeriesBlocks.BLACK_WHITE_CONCRETE);
        OLIVE = foodItem("olive",BakeriesFoodProperties.OLIVE);
        OLIVE_OIL = REGISTER.register("olive_oil",()-> new OliveOilItem(new Item.Properties().durability(6)));
        CUPBOARD = block(BakeriesBlocks.CUPBOARD);
        SALT_SCRAPER_RAKE = item("salt_scraper_rake");
        COCOA_POWDER = item("cocoa_powder");
        CHEESE_CUBE = foodItem("cheese_cube",BakeriesFoodProperties.CHEESE_CUBE);
        MOULD_CHEESE_COCOA_TOAST_DOUGH = rawItem("mould_cheese_cocoa_toast_dough","135");
        MEAT_FLOSS_BREAD_DOUGH = rawItem("meat_floss_bread_dough","155");
        FOCACCIA_DOUGH = rawItem("focaccia_dough","230");
        MEAT_FLOSS = foodItem("meat_floss",BakeriesFoodProperties.MEAT_FLOSS);
        ROUND_DOUGH_DRAWING = drawingItem("round_dough_drawing");
        BAGEL_DOUGH_DRAWING = drawingItem("bagel_dough_drawing");
        WHOLE_WHEAT_BAGEL_DOUGH_DRAWING = drawingItem("whole_wheat_bagel_dough_drawing");
        BAGUETTE_DOUGH_DRAWING = drawingItem("baguette_dough_drawing");
        CIABATTA_DOUGH_DRAWING = drawingItem("ciabatta_dough_drawing");
        DRINK_CUP = block(BakeriesBlocks.DRINK_CUP);
        ICED_LATTE = coldDrinkItem(BakeriesBlocks.ICED_LATTE,BakeriesFoodProperties.ICED_LATTE,true);
        BROWN_SUGAR_LATTE = coldDrinkItem(BakeriesBlocks.BROWN_SUGAR_LATTE,BakeriesFoodProperties.BROWN_SUGAR_LATTE,true);
        ICED_AMERICAN = coldDrinkItem(BakeriesBlocks.ICED_AMERICAN,BakeriesFoodProperties.ICED_AMERICAN,true);
        CREAM_BINGLE_COFFEE = coldDrinkItem(BakeriesBlocks.CREAM_BINGLE_COFFEE,BakeriesFoodProperties.CREAM_BINGLE_COFFEE,true);
        GROUND_COFFEE = item("ground_coffee");
        MOKA_POT = REGISTER.register("moka_pot",()-> new BlockItem(BakeriesBlocks.MOKA_POT.get(),new Item.Properties().stacksTo(1)));
        MOKA_POT_FILL = REGISTER.register("moka_pot_fill",()-> new Item(new Item.Properties().craftRemainder(BakeriesItems.MOKA_POT.get()).stacksTo(1)));
        RAW_COFFEE_BEAN = REGISTER.register("raw_coffee_bean",()-> new ItemNameBlockItem(BakeriesBlocks.COFFEE_PLANT.get(),new Item.Properties()));
        COFFEE_BEAN = item("coffee_bean");
        /*
        方块物品/一般物品
        */
        OVEN = block(BakeriesBlocks.OVEN);
        BLENDER = block(BakeriesBlocks.BLENDER);
        FERMENTATION_TANK = block(BakeriesBlocks.FERMENTATION_TANK);
        YEAST_TANK = block(BakeriesBlocks.YEAST_TANK);
        CHEESE_TANK = block(BakeriesBlocks.CHEESE_TANK);
        MILK_TANK = block(BakeriesBlocks.Milk_TANK);
        BOTTLE_YEAST = REGISTER.register("bottle_yeast",()-> new Item(new Item.Properties().stacksTo(16).craftRemainder(Items.GLASS_BOTTLE)));
        GLASS_CABINET_DOOR = block(BakeriesBlocks.GLASS_CABINET_DOOR);
        GLASS_CABINET_DOOR_TWO = block(BakeriesBlocks.GLASS_CABINET_DOOR_TWO);
        SALT_ORE = block(BakeriesBlocks.SALT_ORE);
        DEEPSLATE_SALT_ORE = block(BakeriesBlocks.DEEPSLATE_SALT_ORE);
        DOUGH_CRAFTING_TABLE = block(BakeriesBlocks.DOUGH_CRAFTING_TABLE);
        BREAD_BASKET = block(BakeriesBlocks.BREAD_BASKET);
        TOASTER = block(BakeriesBlocks.TOASTER);
        TOMATO = REGISTER.register("tomato",()->new ItemNameBlockItem(BakeriesBlocks.TOMATO.get(),new Item.Properties().food(BakeriesFoodProperties.TOMATO)));
        MENU = block(BakeriesBlocks.MENU_BLOCK);
        MOULD_TOAST = block(BakeriesBlocks.MOULD_TOAST);
        MOULD_CHEESE_COCOA_TOAST = block(BakeriesBlocks.MOULD_CHEESE_COCOA_TOAST);
        BAYSALT_FRAME = block(BakeriesBlocks.BAYSALT_FRAME);
        BREAD_HOLDERS = block(BakeriesBlocks.BREAD_HOLDERS);
        FLOUR_BAG = block(BakeriesBlocks.FLOUR_BAG);
        WHOLE_WHEAT_FLOUR_BAG = block(BakeriesBlocks.WHOLE_WHEAT_FLOUR_BAG);
        WOOD_TRAY = block(BakeriesBlocks.WOOD_TRAY);
        /*
        面包物品
        */
        BAGEL = foodBlockItem(BakeriesBlocks.BAGEL, BakeriesFoodProperties.BAGEL);
        WHOLE_WHEAT_BAGEL = foodBlockItem(BakeriesBlocks.WHOLE_WHEAT_BAGEL, BakeriesFoodProperties.WHOLE_WHEAT_BAGEL,true,false);
        BAGUETTE = REGISTER.register("baguette",()->new BaguetteItem(BakeriesBlocks.BAGUETTE.get(),new Item.Properties().durability(4).food(BakeriesFoodProperties.BAGUETTE)));
        BROWN_SUGAR_ROLL = foodBlockItem(BakeriesBlocks.BROWN_SUGAR_ROLL, BakeriesFoodProperties.BROWN_SUGAR_ROLL,true,false);
        COUNTRY_BREAD = block(BakeriesBlocks.COUNTRY_BREAD);
        COUNTRY_BREAD_SLICE = foodItem("country_bread_slice",BakeriesFoodProperties.COUNTRY_BREAD_SLICE);
        CROISSANT = foodBlockItem(BakeriesBlocks.CROISSANT, BakeriesFoodProperties.CROISSANT,true,false);
        CIABATTA = foodBlockItem(BakeriesBlocks.CIABATTA, BakeriesFoodProperties.CIABATTA);
        PINEAPPLE_BUN = foodBlockItem(BakeriesBlocks.PINEAPPLE_BUN,BakeriesFoodProperties.PINEAPPLE_BUN,true,false);
        ROUND_BREAD = foodBlockItem(BakeriesBlocks.ROUND_BREAD,BakeriesFoodProperties.ROUND_BREAD);
        SALT_CROISSANT = foodBlockItem(BakeriesBlocks.SALT_CROISSANT,BakeriesFoodProperties.SALT_CROISSANT,true,false);
        TOAST = block(BakeriesBlocks.TOAST);
        CHEESE_COCOA_TOAST = block(BakeriesBlocks.CHEESE_COCOA_TOAST);
        SLICED_TOAST = foodItem("sliced_toast",BakeriesFoodProperties.SLICED_TOAST);
        SLICED_CHEESE_COCOA_TOAST = foodItem("sliced_cheese_cocoa_toast",BakeriesFoodProperties.SLICED_CHEESE_COCOA_TOAST,true);
        BERRY_BREAD = foodBlockItem(BakeriesBlocks.BERRY_BREAD,BakeriesFoodProperties.BERRY_BREAD);
        FOCACCIA = foodBlockItem(BakeriesBlocks.FOCACCIA,BakeriesFoodProperties.FOCACCIA,true,false);
        MEAT_FLOSS_BREAD = foodBlockItem(BakeriesBlocks.MEAT_FLOSS_BREAD,BakeriesFoodProperties.MEAT_FLOSS_BREAD);
        PIZZA = block(BakeriesBlocks.PIZZA);
        SAUSAGE_PIZZA = block(BakeriesBlocks.SAUSAGE_PIZZA);
        MEAT_PASTE_PIZZA = block(BakeriesBlocks.MEAT_PASTE_PIZZA);
        DIRTY_CHOCO_CROISSANT = foodBlockItem(BakeriesBlocks.DIRTY_CHOCO_CROISSANT,BakeriesFoodProperties.DIRTY_CHOCO_CROISSANT,true,false);
        BAGUETTE_WITH_FILLING = foodBlockItem(BakeriesBlocks.BAGUETTE_WITH_FILLING,BakeriesFoodProperties.BAGUETTE_WITH_FILLING);
        TOMATO_CHEESE_CROISSANT_SANDWICH = foodBlockItem(BakeriesBlocks.TOMATO_CHEESE_CROISSANT_SANDWICH,BakeriesFoodProperties.TOMATO_CHEESE_CROISSANT_SANDWICH,true,false);
        BERRY_BAGEL = foodBlockItem(BakeriesBlocks.BERRY_BAGEL,BakeriesFoodProperties.BERRY_BAGEL,true,false);
        HONEY_BUTTER_SPREAD_TOAST = foodItem("honey_butter_spread_toast",BakeriesFoodProperties.HONEY_BUTTER_SPREAD_TOAST);
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
        return REGISTER.register(block.getId().getPath(), () -> new FoodBlockItem(block.get(), PileBlock.integerProperty,new Item.Properties().food(foodProperties)));
    }

    private static RegistryObject<Item> foodBlockItem(RegistryObject<Block> block, FoodProperties foodProperties,boolean effectTooltip,boolean customField) {
        return REGISTER.register(block.getId().getPath(), () -> new FoodBlockItem(block.get(), PileBlock.integerProperty, new Item.Properties().food(foodProperties),effectTooltip,customField));
    }

    private static RegistryObject<Item> foodItem(String pName, FoodProperties foodProperties) {
        return REGISTER.register(pName, () -> new Item(new Item.Properties().food(foodProperties)));
    }

    private static RegistryObject<Item> foodItem(String pName, FoodProperties foodProperties,boolean effectTooltip) {
        return REGISTER.register(pName, () -> new EffectTooltipItem(new Item.Properties().food(foodProperties),effectTooltip));
    }

    private static RegistryObject<Item> drawingItem(String pName) {
        return REGISTER.register(pName, () -> new DrawingItem(new Item.Properties().stacksTo(16)));
    }

    private static RegistryObject<Item> coldDrinkItem(RegistryObject<Block> block,FoodProperties foodProperties,boolean toolTips) {
        return REGISTER.register(block.getId().getPath(), () -> new ColdDrinkItem(block.get(),new Item.Properties().durability(6).craftRemainder(BakeriesItems.DRINK_CUP.get()).food(foodProperties),toolTips,false));
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
