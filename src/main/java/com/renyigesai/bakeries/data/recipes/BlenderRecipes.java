package com.renyigesai.bakeries.data.recipes;

import com.renyigesai.bakeries.common.init.BakeriesItems;
import com.renyigesai.bakeries.common.tag.CommonTags;
import com.renyigesai.bakeries.data.builder.BlenderBuilder;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.world.item.Items;

public class BlenderRecipes extends Recipes{
    public static void register(RecipeOutput output) {
        onBlender(output);
    }

    private static void onBlender(RecipeOutput output){
        BlenderBuilder.blender(BakeriesItems.SWEET_DOUGH.get(),1)
                .addIngredient(BakeriesItems.FLOUR)
                .addIngredient(BakeriesItems.FLOUR)
                .addIngredient(BakeriesItems.FLOUR)
                .addIngredient(BakeriesItems.FLOUR)
                .addIngredient(Items.SUGAR)
                .addIngredient(CommonTags.MILK)
                .addIngredient(BakeriesItems.BOTTLE_YEAST)
                .addIngredient(CommonTags.SALT)
                .addIngredient(CommonTags.BUTTER)
                .build(output);

        BlenderBuilder.blender(BakeriesItems.COCOA_DOUGH.get(),1)
                .addIngredient(BakeriesItems.FLOUR)
                .addIngredient(BakeriesItems.FLOUR)
                .addIngredient(BakeriesItems.FLOUR)
                .addIngredient(BakeriesItems.COCOA_POWDER)
                .addIngredient(Items.SUGAR)
                .addIngredient(CommonTags.MILK)
                .addIngredient(BakeriesItems.BOTTLE_YEAST)
                .addIngredient(CommonTags.SALT)
                .addIngredient(CommonTags.BUTTER)
                .build(output);

        BlenderBuilder.blender(BakeriesItems.SALTED_DOUGH.get(),1)
                .addIngredient(BakeriesItems.FLOUR)
                .addIngredient(BakeriesItems.FLOUR)
                .addIngredient(BakeriesItems.FLOUR)
                .addIngredient(BakeriesItems.FLOUR)
                .addIngredient(Items.WATER_BUCKET)
                .addIngredient(BakeriesItems.BOTTLE_YEAST)
                .addIngredient(CommonTags.SALT)
                .build(output);

        BlenderBuilder.blender(BakeriesItems.WHOLE_WHEAT_DOUGH.get(),1)
                .addIngredient(CommonTags.WHOLE_WHEAT_FLOUR)
                .addIngredient(CommonTags.WHOLE_WHEAT_FLOUR)
                .addIngredient(CommonTags.WHOLE_WHEAT_FLOUR)
                .addIngredient(CommonTags.WHOLE_WHEAT_FLOUR)
                .addIngredient(BakeriesItems.BOTTLE_YEAST)
                .addIngredient(CommonTags.SALT)
                .addIngredient(Items.WATER_BUCKET)
                .build(output);

        BlenderBuilder.blender(BakeriesItems.BOTTLE_CREAM,Items.GLASS_BOTTLE,1)
                .addIngredient(CommonTags.MILK)
                .build(output);

        BlenderBuilder.blender(BakeriesItems.BUTTER_CUBE,1)
                .addIngredient(BakeriesItems.BOTTLE_CREAM)
                .build(output);

        BlenderBuilder.blender(BakeriesItems.CHEESE_CREAM,2)
                .addIngredient(BakeriesItems.FOAMED_CREAM)
                .addIngredient(BakeriesItems.FRESH_CHEESE_CUBE)
                .build(output);

        BlenderBuilder.blender(BakeriesItems.FOAMED_CREAM,4)
                .addIngredient(BakeriesItems.BOTTLE_CREAM)
                .addIngredient(BakeriesItems.BOTTLE_CREAM)
                .addIngredient(Items.SUGAR)
                .build(output);

        BlenderBuilder.blender(BakeriesItems.HONEY_BUTTER,4)
                .addIngredient(Items.HONEY_BOTTLE)
                .addIngredient(CommonTags.BUTTER)
                .addIngredient(CommonTags.BUTTER)
                .build(output);

        BlenderBuilder.blender(BakeriesItems.MEAT_FLOSS,4)
                .addIngredient(Items.COOKED_PORKCHOP)
                .build(output);

        BlenderBuilder.blender(BakeriesItems.OLIVE_OIL,Items.GLASS_BOTTLE,1)
                .addIngredient(BakeriesItems.OLIVE)
                .addIngredient(BakeriesItems.OLIVE)
                .addIngredient(BakeriesItems.OLIVE)
                .addIngredient(BakeriesItems.OLIVE)
                .build(output);

        BlenderBuilder.blender(BakeriesItems.MATCHA_PARFAIT,Items.GLASS_BOTTLE,1)
                .addIngredient(Items.ICE)
                .addIngredient(CommonTags.MILK)
                .addIngredient(BakeriesItems.BOTTLE_CREAM)
                .addIngredient(CommonTags.MATCHA)
                .build(output);

        BlenderBuilder.blender(BakeriesItems.MASHED_TARO,4)
                .addIngredient(BakeriesItems.COOKED_TARO)
                .addIngredient(Items.SUGAR)
                .addIngredient(BakeriesItems.BOTTLE_CREAM)
                .build(output);

        BlenderBuilder.blender(BakeriesItems.FRESH_CHEESE_CUBE,4)
                .addIngredient(BakeriesItems.CHEESE_CUBE)
                .addIngredient(BakeriesItems.CHEESE_CUBE)
                .addIngredient(BakeriesItems.CHEESE_CUBE)
                .addIngredient(BakeriesItems.CHEESE_CUBE)
                .addIngredient(BakeriesItems.BOTTLE_CREAM)
                .build(output);
    }
}
