package net.weibai.bakeries.data;

import net.minecraft.data.PackOutput;
import net.weibai.bakeries.BakeriesMod;
import net.weibai.bakeries.common.init.BakeriesBlocks;
import net.weibai.bakeries.common.init.BakeriesCreativeModeTabs;
import net.weibai.bakeries.common.init.BakeriesItems;
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
    }

    private void add() {
        add(UtilTranslatable.setTooltips(BakeriesMod.MODID, "row_item_temperature"), "Min %s °c", "最小 %s °c");
        add(UtilTranslatable.setContainer(BakeriesMod.MODID, "oven"),"Oven", "烤箱");
        add(UtilTranslatable.setContainer(BakeriesMod.MODID, "oven.temperature"),"Current temperature", "当前温度");
        add(UtilTranslatable.setContainer(BakeriesMod.MODID, "oven.rolling"),"Scroll the middle mouse to adjust the temperature.", "滚动鼠标中键调节温度");
    }
    private void addCreativeModeTabs() {
        addCreativeModeTab(BakeriesCreativeModeTabs.BAKERIES_TAB, "烘培坊");
    }

    private void addItems() {
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




        /**杂项*/
        addItem(BakeriesItems.ROUND_BREAD_DOUGH, "圆面包面胚");
        addItem(BakeriesItems.ICE_CUBES, "冰块");
    }
    private void addBlocks() {
        addBlock(BakeriesBlocks.OVEN, "烤箱");
    }

    private void addElements() {
//        addElements(ElementCollections.H2_NI_O3_SI, "硅酸镍");
//        addElements(ElementCollections.C_H_FE_O4, "碳酸铁");
//        addElements(ElementCollections.CA_C_O3, "CaCO\\U+2083", "CaCO\\U+2083");
    }
}