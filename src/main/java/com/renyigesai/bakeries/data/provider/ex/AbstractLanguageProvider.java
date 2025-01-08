//package com.renyigesai.bakeries.data.provider.ex;
//
//import com.google.gson.JsonObject;
//import com.renyigesai.bakeries.BakeriesMod;
//import net.minecraft.core.registries.BuiltInRegistries;
//import net.minecraft.data.CachedOutput;
//import net.minecraft.data.DataProvider;
//import net.minecraft.data.PackOutput;
//import net.minecraft.resources.ResourceKey;
//import net.minecraft.world.damagesource.DamageType;
//import net.minecraft.world.effect.MobEffect;
//import net.minecraft.world.entity.EntityType;
//import net.minecraft.world.entity.ai.attributes.Attribute;
//import net.minecraft.world.item.CreativeModeTab;
//import net.minecraft.world.item.Item;
//import net.minecraft.world.level.biome.Biome;
//import net.minecraft.world.level.block.Block;
//import net.minecraftforge.registries.RegistryObject;
//import net.weibai.xuixian.HXMod;
//import org.jetbrains.annotations.NotNull;
//
//import java.nio.file.Path;
//import java.util.Map;
//import java.util.TreeMap;
//import java.util.concurrent.CompletableFuture;
//import java.util.function.Supplier;
//
//public abstract class AbstractLanguageProvider extends net.minecraftforge.common.data.LanguageProvider {
//    private final Map<String,String> enData = new TreeMap<>();
//    private final Map<String,String> cnData = new TreeMap<>();
//    private final PackOutput output;
//    private final String locale;
//    public AbstractLanguageProvider(PackOutput output, String locale) {
//        super(output, BakeriesMod.MODID, locale);
//        this.output = output;
//        this.locale = locale;
//    }
//    @Override
//    public @NotNull CompletableFuture<?> run(CachedOutput cache) {
//        this.addTranslations();
//        Path path = this.output.getOutputFolder(PackOutput.Target.RESOURCE_PACK)
//                .resolve(BakeriesMod.MODID).resolve("lang");
//        if (this.locale.equals("en_us") && !this.enData.isEmpty()) {
//            return this.save(this.enData, cache, path.resolve("en_us.json"));
//        }
//        if (this.locale.equals("zh_cn") && !this.cnData.isEmpty()) {
//            return this.save(this.cnData, cache, path.resolve("zh_cn.json"));
//        }
//        return CompletableFuture.allOf();
//    }
//    private CompletableFuture<?> save(Map<String, String> data, CachedOutput cache, Path target) {
//        JsonObject json = new JsonObject();
//        data.forEach(json::addProperty);
//        return DataProvider.saveStable(cache, json, target);
//    }
//    private String getEnglishName(String path) {
//        String[] words = path.split("_");
//        for (int i = 0; i < words.length; i++) {
//            String firstLetter = words[i].substring(0, 1);
//            String remainingLetters = words[i].substring((1));
//            words[i] = firstLetter.toUpperCase() + remainingLetters;
//        }
//
//        return String.join(" ", words);
//    }
//    protected void addBlock(RegistryObject<Block> key, String zh_cn) {
//        String path = BuiltInRegistries.BLOCK.getKey(key.get()).getPath();
//        this.add(key.get().getDescriptionId(), this.getEnglishName(path), zh_cn);
//    }
//    protected void addItem(RegistryObject<Item> key, String zh_cn) {
//        String path = BuiltInRegistries.ITEM.getKey(key.get()).getPath();
//        this.add(key.get().getDescriptionId(), this.getEnglishName(path), zh_cn);
//    }
//    protected void addEntityType(RegistryObject<EntityType> key, String zh_cn) {
//        String path = BuiltInRegistries.ENTITY_TYPE.getKey(key.get()).getPath();
//        this.add(key.get().getDescriptionId(), this.getEnglishName(path), zh_cn);
//    }
//    protected void addEffect(RegistryObject<MobEffect> key, String zh_cn) {
//        String path = BuiltInRegistries.MOB_EFFECT.getKey(key.get()).getPath();
//        this.add(key.get().getDescriptionId(), this.getEnglishName(path), zh_cn);
//    }
//    protected void addTab(RegistryObject<CreativeModeTab> key, String zh_cn) {
//        String path = BuiltInRegistries.CREATIVE_MODE_TAB.getKey(key.get()).getPath();
//        this.add(key.get().getDisplayName().getString(), this.getEnglishName(path), zh_cn);
//    }
//    protected void addAttribute(RegistryObject<Attribute> key, String zh_cn) {
//        String path = BuiltInRegistries.ATTRIBUTE.getKey(key.get()).getPath();
//        this.add(key.get().getDescriptionId(), this.getEnglishName(path), zh_cn);
//    }
//    protected void addBlock(Supplier<? extends Block> key, String en_us, String zh_cn) {
//        this.add(key.get().getDescriptionId(), en_us, zh_cn);
//    }
//    protected void addItem(Supplier<? extends Item> key, String en_us, String zh_cn) {
//        this.add(key.get().getDescriptionId(), en_us, zh_cn);
//    }
//    protected void addDamageType(DamageType key, String en_us, String zh_cn) {
//        this.add(key.deathMessageType().getSerializedName(), en_us, zh_cn);
//    }
//    protected void addEntityType(Supplier<? extends EntityType<?>> key, String en_us, String zh_cn) {
//        this.add(key.get().getDescriptionId(), en_us, zh_cn);
//    }
//
//    protected void addEffect(Supplier<? extends MobEffect> key, String en_us, String zh_cn) {
//        this.add(key.get().getDescriptionId(), en_us, zh_cn);
//    }
//    protected void addBiome(ResourceKey<Biome> biome, String en_us, String zh_cn) {
//        this.add("biome." + biome.location().toLanguageKey(), en_us, zh_cn);
//    }
//    protected void addTab(Supplier<CreativeModeTab> tab, String en_us, String zh_cn){
//        this.add(tab.get().getDisplayName().getString(), en_us, zh_cn);
//    }
//    protected void addTooltips(String key, String en_us, String zh_cn) {
//        this.add("tooltips." + HXMod.MODID + "." + key, en_us, zh_cn);
//    }
//    protected void addAttribute(Supplier<Attribute> attribute, String en_us, String zh_cn) {
//        this.add("attribute." + attribute.get().getDescriptionId(), en_us, zh_cn);
//    }
//    protected void addKeyMapping(String name, String en_us, String zh_cn){
//        this.add("key." + HXMod.MODID + "." + name, en_us, zh_cn);
//    }
//    protected void add(String key, String en_us, String zh_cn) {
//        if (this.locale.equals("en_us") && !this.enData.containsKey(key)) {
//            this.enData.put(key, en_us);
//        } else if (this.locale.equals("zh_cn") && !this.cnData.containsKey(key)) {
//            this.cnData.put(key, zh_cn);
//        }
//    }
//}
