package com.renyigesai.bakeries.data;

import com.renyigesai.bakeries.BakeriesMod;
import com.renyigesai.bakeries.common.init.BakeriesBlocks;
import com.renyigesai.bakeries.common.init.BakeriesCreativeModeTabs;
import com.renyigesai.bakeries.common.init.BakeriesItems;
import com.renyigesai.bakeries.common.init.BakeriesMobEffects;
import net.minecraft.data.PackOutput;
import net.weibai.rcglib.utils.UtilTranslatable;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public class MSLanguageProvider extends AbstractLanguageProvider {
    private final PackOutput output;
    private final String locale;
    public MSLanguageProvider(PackOutput output, String locale) {
        super(output, locale);
        this.output = output;
        this.locale = locale;
    }
    @Override
    protected void addTranslations() {
        addCreativeModeTabs();
        addItems();
        addBlocks();
        addElements();
        add();
        addEffects();
    }

    private void add() {
        add(UtilTranslatable.setTooltips(BakeriesMod.MODID, "row_item_temperature"), "Min %s °c", "最小 %s °c");
        add(UtilTranslatable.setContainer(BakeriesMod.MODID, "oven"),"Oven", "烤箱");
        add(UtilTranslatable.setContainer(BakeriesMod.MODID, "oven.temperature"),"Current temperature", "当前温度");
        add(UtilTranslatable.setContainer(BakeriesMod.MODID, "oven.rolling"),"Scroll the middle mouse to adjust the temperature.", "滚动鼠标中键调节温度");
        add(UtilTranslatable.setContainer(BakeriesMod.MODID, "blender"),"Blender", "搅拌机");
        add(UtilTranslatable.setTooltips(BakeriesMod.MODID, "bread_knife"),"When using  cut the object pointed by the target.", "使用时切开准星所指的物品");
    }
    private void addCreativeModeTabs() {
        addCreativeModeTab(BakeriesCreativeModeTabs.BAKERIES_TAB, "烘培坊");
    }

    private void addItems() {

        /**一般物品 食材 食物*/
        addItem(BakeriesItems.FLOUR,"面粉");
        addItem(BakeriesItems.WHOLE_WHEAT_FLOUR,"全麦面粉");
        addItem(BakeriesItems.SALT,"盐");
        addItem(BakeriesItems.COCOA_POWDER,"可可粉");
        addItem(BakeriesItems.MATCHA_POWDER,"抹茶粉");
        addItem(BakeriesItems.BUTTER_CUBE,"黄油块");
        addItem(BakeriesItems.FOAMED_CREAM,"打发奶油");
        addItem(BakeriesItems.CHEESE_CREAM,"奶酪奶油");
        addItem(BakeriesItems.WHOLE_EGG,"全蛋");
        addItem(BakeriesItems.RAW_PROTEIN,"生蛋白");
        addItem(BakeriesItems.RAW_EGG_YOLK,"生蛋黄");
        addItem(BakeriesItems.CHEESE_CUBE,"干酪块");
        addItem(BakeriesItems.FRESH_CHEESE_CUBE,"鲜奶酪块");
        addItem(BakeriesItems.BROWN_SUGAR_CUBE,"红糖块");

        addItem(BakeriesItems.BOTTLE_YEAST,"瓶装鲜酵母");
        addItem(BakeriesItems.BOTTLE_MILK,"瓶装奶");
        addItem(BakeriesItems.BOTTLE_CREAM,"瓶装淡奶油");
        addItem(BakeriesItems.BOTTLE_BUTTER,"瓶装黄油");

        /**面包*/
        addItem(BakeriesItems.BAGEL, "贝果");
        addItem(BakeriesItems.WHOLE_WHEAT_BAGEL, "全麦贝果");
        addItem(BakeriesItems.ROUND_BREAD, "圆面包");
        addItem(BakeriesItems.BERRY_BREAD, "莓果面包");
        addItem(BakeriesItems.CHEESE_CREAM_BREAD, "乳酪面包");
        addItem(BakeriesItems.BROWN_SUGAR_ROLL, "红糖卷");
        addItem(BakeriesItems.PINEAPPLE_BUN, "菠萝包");
        addItem(BakeriesItems.MEAT_FLOSS_BREAD_ROLL, "肉松面包卷");
        addItem(BakeriesItems.CROISSANT, "可颂");
        addItem(BakeriesItems.DIRTY_CHOCO_CROISSANT, "脏脏包");
        addItem(BakeriesItems.SALT_CROISSANT, "盐可颂");
        addItem(BakeriesItems.CIABATTA, "恰巴塔面包");
        addItem(BakeriesItems.FOCACCIA, "佛卡夏面包");
        addItem(BakeriesItems.BERRY_BAGEL, "浆果贝果");
        addItem(BakeriesItems.BAGEL_FILLED_SAUCE, "填酱贝果");
        addItem(BakeriesItems.BAGUETTE_WITH_FILLING, "填馅法棍");
        addItem(BakeriesItems.TOMATO_CHEESE_CROISSANT_SANDWICH, "番茄奶酪可颂三明治");
        addItem(BakeriesItems.BAGUETTE, "法棍");

        /**功能物品*/
        addItem(BakeriesItems.BREAD_KNIFE, "面包刀");
        addItem(BakeriesItems.FLOUR_SIEVE, "面粉筛");

        /**杂项*/
        addItem(BakeriesItems.ROUND_BREAD_DOUGH, "圆面包面胚");
        addItem(BakeriesItems.ICE_CUBES, "冰块");
    }
    private void addBlocks() {
        addBlock(BakeriesBlocks.OVEN, "烤箱");
        addBlock(BakeriesBlocks.BLENDER, "搅拌机");
    }

    private void addElements() {
;
    }

    private void addEffects(){
        addEffect(BakeriesMobEffects.CHEESE_POWER::value,"Cheese Power","芝士力");
        addEffect(BakeriesMobEffects.COCOA_MANIA::value,"Cocoa Mania","可可狂热");
        addEffect(BakeriesMobEffects.SOFT::value,"Soft","柔软");
        addEffect(BakeriesMobEffects.ENJOY::value,"Enjoy","享受");
    }
}