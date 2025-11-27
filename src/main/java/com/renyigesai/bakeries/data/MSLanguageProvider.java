package com.renyigesai.bakeries.data;

import com.renyigesai.bakeries.BakeriesMod;
import com.renyigesai.bakeries.api.annotation.CustomData;
import com.renyigesai.bakeries.api.annotation.ItemData;
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
import java.util.ArrayList;
import java.util.List;

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
        try {
            addItems();
        } catch (IllegalAccessException e) {
            LOGGER.error("Failed to access item fields", e);
            throw new RuntimeException("Failed to access item fields during language generation", e);
        }
        addElements();
        add();
        addEffects();
        addEntity();
    }


    private void add() {
        add(UtilTranslatable.setTooltips(BakeriesMod.MODID, "row_item_temperature"), "Min %s °c", "Min %s °c");
        add(UtilTranslatable.setContainer(BakeriesMod.MODID, "oven"), "Oven", "烤箱");
        add(UtilTranslatable.setContainer(BakeriesMod.MODID, "oven.temperature"), "Current temperature", "当前温度");
        add(UtilTranslatable.setContainer(BakeriesMod.MODID, "oven.rolling"), "Scroll the middle mouse to adjust the temperature.", "滚动鼠标中键调节温度");
        add(UtilTranslatable.setContainer(BakeriesMod.MODID, "blender"), "Blender", "搅拌机");
        add(UtilTranslatable.setContainer(BakeriesMod.MODID, "dough_crafting_table"), "Dough Crafting Table", "面胚制作台");
        add(UtilTranslatable.setContainer(BakeriesMod.MODID, "cupboard"), "Cupboard", "厨台");
        add(UtilTranslatable.setTooltips(BakeriesMod.MODID, "bread_knife"), "When using  cut the object pointed by the target.", "使用时切开准星所指的物品");
        add(UtilTranslatable.setTooltips(BakeriesMod.MODID, "wood_counter"), "Use Bowl to Change State", "使用碗右键方块以改变状态");
        add(UtilTranslatable.setTooltips(BakeriesMod.MODID, "flour_sieve_0"), "Sift the item in the main hand while holding it off hand", "拿在副手时过筛主手的物品");
        add(UtilTranslatable.setTooltips(BakeriesMod.MODID, "flour_sieve_1"), "What are you doing?", "你在干什么?");
        add(UtilTranslatable.setTooltips(BakeriesMod.MODID, "flour_sieve_2"), "You can't sift it!", "筛不了的啦!");
        add(UtilTranslatable.setTooltips(BakeriesMod.MODID, "flour_sieve_3"), "I don't have the power, you know?", "我没这个能力知道吧?");
    }

    private void addCreativeModeTabs() {
        addCreativeModeTab(BakeriesCreativeModeTabs.BAKERIES_TAB, "烘培坊");
        addCreativeModeTab(BakeriesCreativeModeTabs.SFP_TAB, "烘培坊（半成品）");
    }


    private void addElements() {
        ;
    }

    private void addEffects() {
        addEffect(BakeriesMobEffects.CHEESE_POWER::value, "Cheese Power", "芝士力");
        addEffect(BakeriesMobEffects.COCOA_MANIA::value, "Cocoa Mania", "可可狂热");
        addEffect(BakeriesMobEffects.SOFT::value, "Soft", "柔软");
        addEffect(BakeriesMobEffects.ENJOY::value, "Enjoy", "享受");
    }

    private void addItems() throws IllegalAccessException {
        Class<BakeriesItems> _class = BakeriesItems.class;
        for (Field field : _class.getDeclaredFields()) {
            if (field.isAnnotationPresent(ItemData.class)) {
                Object object = field.get(null);
                DeferredItem<?> deferredItem = null;
                if (object instanceof DeferredItem<?>) {
                    deferredItem = (DeferredItem<?>) object;
                }
                if (deferredItem != null) {
                    ItemData itemData = field.getAnnotation(ItemData.class);
                    if (itemData != null) {
                        String zh = itemData.zhCn();
                        String en = itemData.enUs();
                        if (itemData.itemType() == ItemData.ItemType.ITEM) {
                            if (en.isEmpty()) {
                                addItem(deferredItem, zh);
                            } else {
                                addItem(deferredItem, en, zh);
                            }
                        }
                        if (itemData.itemType() == ItemData.ItemType.BLOCK) {
                            Item item = deferredItem.get();
                            if (item instanceof BlockItem blockItem) {
                                addBlock(blockItem::getBlock, en, zh);
                            } else {
                                throw new IllegalStateException("Field <<<" + field.getName() + ">>> is annotated as BLOCK but is not a BlockItem!");
                            }
                        }
                    }
                }
            }
        }
    }

    private void addEntity(){
        add("entity.minecraft.villager.bakeries.pistrinamaster","Pistrina Master","面包师");
    }

    private List<Class<?>> getClasses() {
        List<Class<?>> classes = new ArrayList<>();
        classes.add(BakeriesItems.class);
        return classes;
    }
}