package com.renyigesai.bakeries.data;

import com.renyigesai.bakeries.BakeriesMod;
import com.renyigesai.bakeries.api.annotation.ItemData;
import com.renyigesai.bakeries.api.annotation.ItemType;
import com.renyigesai.bakeries.common.init.BakeriesCreativeModeTabs;
import com.renyigesai.bakeries.common.init.BakeriesItems;
import com.renyigesai.bakeries.common.init.BakeriesMobEffects;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredItem;
import net.weibai.rcglib.utils.UtilTranslatable;

import javax.annotation.ParametersAreNonnullByDefault;
import java.lang.reflect.Field;

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
        try {
            addItems();
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        add();
        addEffects();
        addCreativeModeTabs();
    }

    private void addItems() throws IllegalAccessException {
        Class<BakeriesItems> _class = BakeriesItems.class;
        for (Field field : _class.getDeclaredFields()) {
            boolean isAnnotation = field.isAnnotationPresent(ItemData.class);
            if (isAnnotation){
                Object object = field.get(null);
                if (object instanceof DeferredItem<?> deferredItem){
                    ItemData annotation = field.getAnnotation(ItemData.class);
                    ItemType itemType = annotation.itemType();
                    String zh = annotation.zhCn();
                    String en = annotation.enUs();
                    if (itemType == ItemType.ITEM){
                        addItem(deferredItem,en,zh);
                    }
                    if (itemType == ItemType.BLOCK){
                        Item item = deferredItem.get();
                        BlockItem blockItem = (BlockItem) item;
                        addBlock(blockItem::getBlock,en,zh);
                    }
                }

            }
        }
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
        addCreativeModeTab(BakeriesCreativeModeTabs.SFP_TAB, "烘培坊 半成品");
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