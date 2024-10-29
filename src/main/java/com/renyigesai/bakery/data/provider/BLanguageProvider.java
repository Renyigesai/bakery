package com.renyigesai.bakery.data.provider;

import com.google.gson.JsonObject;
import com.renyigesai.bakery.BakeryMod;
import com.renyigesai.bakery.init.BakeryBlocks;
import com.renyigesai.bakery.init.BakeryGroup;
import com.renyigesai.bakery.init.BakeryItems;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;

import javax.annotation.ParametersAreNonnullByDefault;
import java.nio.file.Path;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

@ParametersAreNonnullByDefault
public class BLanguageProvider extends net.minecraftforge.common.data.LanguageProvider {
    private final Map<String,String> enData = new TreeMap<>();
    private final Map<String,String> cnData = new TreeMap<>();
    private final PackOutput output;
    private final String locale;
    public BLanguageProvider(PackOutput output, String locale) {
        super(output, BakeryMod.MODID, locale);
        this.output = output;
        this.locale = locale;
    }
    @Override
    protected void addTranslations() {
        add("container.oven", "Oven", "烤炉");
        addBlock(BakeryBlocks.FERMENTATION_TANK, "Fermentation Tank", "发酵罐");
        addItem(BakeryItems.BAGEL, "Bagel", "贝果");
        addItem(BakeryItems.BAGUETTE, "Baguette", "法棍");
        addItem(BakeryItems.CINNAMON_ROLL, "Cinnamon Roll", "肉桂卷");
        addItem(BakeryItems.COUNTRY_BREAD, "Country Bread", "乡村面包");
        addItem(BakeryItems.CROISSANT, "Croissant", "可颂");

        addItem(BakeryItems.BAGEL_DOUGH, "Bagel Dough", "贝果面胚");
        addItem(BakeryItems.BAGUETTE_DOUGH, "Baguette Dough", "法棍面胚");
        addItem(BakeryItems.BROWN_SUGAR_CUBE, "Brown Sugar Cube", "红糖块");
        addItem(BakeryItems.BUTTER_CUBE, "Butter Cube", "黄油块");
        addItem(BakeryItems.CINNAMON_ROLL_DOUGH, "Cinnamon Roll Dough", "肉桂卷面胚");
        addItem(BakeryItems.COUNTRY_BREAD_DOUGH, "Country Bread Dough", "乡村面包面胚");
        addItem(BakeryItems.CROISSANT_DOUGH, "Croissant Dough", "可颂面胚");
        addItem(BakeryItems.FLOUR, "Flour", "面粉");
        addItem(BakeryItems.FLOUR_RYE, "Rye Flour", "全麦面粉");
        addItem(BakeryItems.PINEAPPLE_BUN, "Pineapple Bun", "菠萝包");
        addItem(BakeryItems.PINEAPPLE_BUN_DOUGH, "Pineapple Bun", "菠萝包面胚");
        addItem(BakeryItems.RAW_EGG_TART, "Raw Egg Tart", "生蛋挞");
        addItem(BakeryItems.RAW_PUMPKIN_PIE, "Pumpkin Pie", "南瓜派");
        addItem(BakeryItems.RAW_TARE_CRUST, "Raw Tare Crust", "生派壳");
        addItem(BakeryItems.ROUND_BREAD_DOUGH, "Round Bread Dough", "圆面包面胚");
        addItem(BakeryItems.SALT, "Salt", "盐");
        addItem(BakeryItems.SALT_CROISSANT, "Salt Croissant", "盐可颂");
        addItem(BakeryItems.SALT_CROISSANT_DOUGH, "Salt Croissant Dough", "盐可颂面胚");
        addItem(BakeryItems.ROUND_BREAD, "Round Bread", "圆面包");
        addItem(BakeryItems.TART_SHELL, "Tart Shell", "蛋挞壳");

        addTab(BakeryGroup.BAKERY_TAB, "Bakery", "烘焙房");
        addTab(BakeryGroup.BAKERY_SEMI_MANUFACTURED_PRODUCT_TAB, "Bakery Semi-Manufactured Product", "半成品");

    }
    @Override
    public @NotNull CompletableFuture<?> run(CachedOutput cache) {
        this.addTranslations();
        Path path = this.output.getOutputFolder(PackOutput.Target.RESOURCE_PACK)
                .resolve(BakeryMod.MODID).resolve("lang");
        if (this.locale.equals("en_us") && !this.enData.isEmpty()) {
            return this.save(this.enData, cache, path.resolve("en_us.json"));
        }
        if (this.locale.equals("zh_cn") && !this.cnData.isEmpty()) {
            return this.save(this.cnData, cache, path.resolve("zh_cn.json"));
        }
        return CompletableFuture.allOf();
    }
    private CompletableFuture<?> save(Map<String, String> data, CachedOutput cache, Path target) {
        JsonObject json = new JsonObject();
        data.forEach(json::addProperty);
        return DataProvider.saveStable(cache, json, target);
    }
    private void addBlock(Supplier<? extends Block> key, String en_us, String zh_cn) {
        this.add(key.get().getDescriptionId(), en_us, zh_cn);
    }
    private void addItem(Supplier<? extends Item> key, String en_us, String zh_cn) {
        this.add(key.get().getDescriptionId(), en_us, zh_cn);
    }
    private void addDamageType(DamageType key, String en_us, String zh_cn) {
        this.add(key.deathMessageType().getSerializedName(), en_us, zh_cn);
    }
    private void addEntityType(Supplier<? extends EntityType<?>> key, String en_us, String zh_cn) {
        this.add(key.get().getDescriptionId(), en_us, zh_cn);
    }
    private void addEffect(Supplier<? extends MobEffect> key, String en_us, String zh_cn) {
        this.add(key.get().getDescriptionId(), en_us, zh_cn);
    }
    private void addBiome(ResourceKey<Biome> biome, String en_us, String zh_cn) {
        this.add("biome." + biome.location().toLanguageKey(), en_us, zh_cn);
    }
    private void addTab(Supplier<CreativeModeTab> tab, String en_us, String zh_cn){
        this.add(tab.get().getDisplayName().getString(), en_us, zh_cn);
    }
    private void addTooltips(String key, String en_us, String zh_cn) {
        this.add("tooltips." + BakeryMod.MODID + "." + key, en_us, zh_cn);
    }
    private void addAttribute(Supplier<Attribute> attribute, String en_us, String zh_cn) {
        this.add("attribute." + attribute.get().getDescriptionId(), en_us, zh_cn);
    }
    private void addKeyMapping(String name, String en_us, String zh_cn){
        this.add("key." + BakeryMod.MODID + "." + name, en_us, zh_cn);
    }
    private void add(String key, String en_us, String zh_cn) {
        if (this.locale.equals("en_us") && !this.enData.containsKey(key)) {
            this.enData.put(key, en_us);
        } else if (this.locale.equals("zh_cn") && !this.cnData.containsKey(key)) {
            this.cnData.put(key, zh_cn);
        }
    }
}
