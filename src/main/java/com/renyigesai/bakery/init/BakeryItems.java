package com.renyigesai.bakery.init;

import com.renyigesai.bakery.BakeryMod;
import com.renyigesai.bakery.api.PileBlock;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

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

    public static final RegistryObject<Item> BAGEL_BLOCK;
    public static final RegistryObject<Item> BAGUETTE_BLOCK;
    public static final RegistryObject<Item> CINNAMON_ROLL_BLOCK;
    public static final RegistryObject<Item> COUNTRY_BREAD_BLOCK;
    public static final RegistryObject<Item> CROISSANT_BLOCK;

    public static final RegistryObject<Item> PINEAPPLE_BUN;
    public static final RegistryObject<Item> ROUND_BREAD;
    public static final RegistryObject<Item> SALT_CROISSANT;

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
        PINEAPPLE_BUN = foodItem("pineapple_bun", BakeryFoodProperties.PINEAPPLE_BUN);
        ROUND_BREAD = foodItem("round_bread", BakeryFoodProperties.ROUND_BREAD);
        SALT_CROISSANT = foodItem("salt_croissant", BakeryFoodProperties.SALT_CROISSANT);
        //Bread Items
        BAGEL_BLOCK = foodBlockItem(BakeryBlocks.BAGEL_BLOCK, BakeryFoodProperties.BAGEL);
        BAGUETTE_BLOCK = foodBlockItem(BakeryBlocks.BAGUETTE_BLOCK, BakeryFoodProperties.BAGUETTE);
        CINNAMON_ROLL_BLOCK = foodBlockItem(BakeryBlocks.CINNAMON_ROLL_BLOCK, BakeryFoodProperties.CINNAMON_ROLL);
        COUNTRY_BREAD_BLOCK = foodBlockItem(BakeryBlocks.COUNTRY_BREAD_BLOCK, BakeryFoodProperties.COUNTRY_BREAD);
        CROISSANT_BLOCK = foodBlockItem(BakeryBlocks.CROISSANT_BLOCK, BakeryFoodProperties.CROISSANT);
    }

    private static RegistryObject<Item> item(String pName) {
        return REGISTER.register(pName, () -> new Item(new Item.Properties()));
    }
    private static RegistryObject<Item> block(RegistryObject<Block> block) {
        return REGISTER.register(block.getId().getPath(), () -> new BlockItem(block.get(), new Item.Properties()));
    }
    private static RegistryObject<Item> foodBlockItem(RegistryObject<Block> block, FoodProperties foodProperties) {
        return REGISTER.register(block.getId().getPath(), () -> new BlockItem(block.get(), new Item.Properties().food(foodProperties)){
            @Override
            protected boolean placeBlock(BlockPlaceContext pContext, BlockState pState) {
                int pile = pState.getValue(PileBlock.PILE);
                BlockPos pos = pContext.getClickedPos();
                Level level = pContext.getLevel();
                if(Screen.hasShiftDown()) {
                    return super.placeBlock(pContext, pState);
                }else {
                    return false;
                }
            }
            @Override
            public InteractionResult useOn(UseOnContext context) {
                super.useOn(context);
                Player player = context.getPlayer();
                InteractionHand hand = context.getHand();
                Level level = context.getLevel();
                BlockPos pos = context.getClickedPos();
                BlockState state = level.getBlockState(pos);
                Block block = state.getBlock();
                ItemStack handStack = player.getItemInHand(hand);
                boolean isPile = handStack.is(asItem());
                if(block instanceof PileBlock){
                    if (!level.isClientSide) {
                        if (isPile && Screen.hasShiftDown()) {
                            return pileUp(level, pos, state, handStack);

                        }
                    }
                    if (isPile && Screen.hasShiftDown()) {
                        return pileUp(level, pos, state, handStack);
                    }
                }
                return InteractionResult.FAIL;
            }
            public InteractionResult pileUp(Level level, BlockPos pos, BlockState state, ItemStack handStack){
                int pile = state.getValue(PileBlock.PILE);
                if (pile < 4) {
                    level.setBlock(pos,state.setValue(PileBlock.PILE, pile + 1),4);
                    handStack.shrink(1);
                    level.playSound(null, pos, SoundEvents.WOOL_STEP, SoundSource.PLAYERS, 0.8F, 0.8F);
                }else {
                    return InteractionResult.FAIL;
                }
                return InteractionResult.SUCCESS;

            }


        });
    }
    private static RegistryObject<Item> foodItem(String pName, FoodProperties foodProperties) {
        return REGISTER.register(pName, () -> new Item(new Item.Properties().food(foodProperties)));
    }




}
