package com.renyigesai.bakeries.util;

import com.google.common.collect.Lists;
import com.mojang.datafixers.util.Pair;
import com.renyigesai.bakeries.BakeriesMod;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.font.FontSet;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffectUtil;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.ItemStack;

import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class TextUtils {
    private static final MutableComponent NO_EFFECTS = Component.translatable("effect.none").withStyle(ChatFormatting.GRAY);
    private static final int MAX_LENGTH = 1024;

    public static MutableComponent getTranslation(String key, Object... args) {
        return Component.translatable(BakeriesMod.MODID + "." + key, args);
    }

    /**By Farmer's Delight*/
    public static void addFoodEffectTooltip(ItemStack itemIn, List<Component> lores, float durationFactor) {
        FoodProperties foodStats = itemIn.getItem().getFoodProperties();
        if (foodStats == null) {
            return;
        }
        try {
            List<Pair<MobEffectInstance, Float>> effectList = foodStats.getEffects();
            List<Pair<Attribute, AttributeModifier>> attributeList = Lists.newArrayList();
            if (effectList.isEmpty()) {
                lores.add(NO_EFFECTS);
            } else {
                for (Pair<MobEffectInstance, Float> effectPair : effectList) {
                    MobEffectInstance instance = effectPair.getFirst();
                    MutableComponent iformattabletextcomponent = Component.translatable(instance.getDescriptionId());
                    MobEffect effect = instance.getEffect();
                    Map<Attribute, AttributeModifier> attributeMap = effect.getAttributeModifiers();
                    if (!attributeMap.isEmpty()) {
                        for (Map.Entry<Attribute, AttributeModifier> entry : attributeMap.entrySet()) {
                            AttributeModifier rawModifier = entry.getValue();
                            AttributeModifier modifier = new AttributeModifier(rawModifier.getName(), effect.getAttributeModifierValue(instance.getAmplifier(), rawModifier), rawModifier.getOperation());
                            attributeList.add(new Pair<>(entry.getKey(), modifier));
                        }
                    }

                    if (instance.getAmplifier() > 0) {
                        iformattabletextcomponent = Component.translatable("potion.withAmplifier", iformattabletextcomponent, Component.translatable("potion.potency." + instance.getAmplifier()));
                    }

                    if (instance.getDuration() > 20) {
                        iformattabletextcomponent = Component.translatable("potion.withDuration", iformattabletextcomponent, MobEffectUtil.formatDuration(instance, durationFactor));
                    }

                    lores.add(iformattabletextcomponent.withStyle(effect.getCategory().getTooltipFormatting()));
                }
            }

            if (!attributeList.isEmpty()) {
                lores.add(CommonComponents.EMPTY);
                lores.add((Component.translatable("potion.whenDrank")).withStyle(ChatFormatting.DARK_PURPLE));

                for (Pair<Attribute, AttributeModifier> pair : attributeList) {
                    AttributeModifier modifier = pair.getSecond();
                    double amount = modifier.getAmount();
                    double formattedAmount;
                    if (modifier.getOperation() != AttributeModifier.Operation.MULTIPLY_BASE && modifier.getOperation() != AttributeModifier.Operation.MULTIPLY_TOTAL) {
                        formattedAmount = modifier.getAmount();
                    } else {
                        formattedAmount = modifier.getAmount() * 100.0D;
                    }

                    if (amount > 0.0D) {
                        lores.add((Component.translatable("attribute.modifier.plus." + modifier.getOperation().toValue(), ItemStack.ATTRIBUTE_MODIFIER_FORMAT.format(formattedAmount), Component.translatable(pair.getFirst().getDescriptionId()))).withStyle(ChatFormatting.BLUE));
                    } else if (amount < 0.0D) {
                        formattedAmount = formattedAmount * -1.0D;
                        lores.add((Component.translatable("attribute.modifier.take." + modifier.getOperation().toValue(), ItemStack.ATTRIBUTE_MODIFIER_FORMAT.format(formattedAmount), Component.translatable(pair.getFirst().getDescriptionId()))).withStyle(ChatFormatting.RED));
                    }
                }
            }
        }catch (NoSuchFieldError error){
            BakeriesMod.LOGGER.debug(error);
        }

    }

    /**获取当前字符串的长度,可指定最大值*/
    public static int getLength(String string,int maxLength){
        if (string == null || maxLength == 0 || maxLength > MAX_LENGTH){
            throw new IllegalArgumentException("The text cannot be empty and its length must be greater than 0 and less than 1024.");
        }
        int width = 0;
        int length = 0;
        Minecraft mc = Minecraft.getInstance();
        for (int i = 0; i < string.length(); i++) {
            char _char = string.charAt(i);
            width += mc.font.width(String.valueOf(_char));
            length ++;
            if (width > maxLength){
                return length - 1;
            }
        }
        return maxLength;
    }

    /**获取当前字符串的像素长度*/
    public static int getPixelLength(String string){
        if (string == null){
            throw new IllegalArgumentException("The text cannot be empty and its length must be greater than 0 and less than 1024.");
        }
        int width = 0;
        Minecraft mc = Minecraft.getInstance();
        for (int i = 0; i < string.length(); i++) {
            char _char = string.charAt(i);
            width += mc.font.width(String.valueOf(_char));
        }
        return width;
    }

    /**输入资源地址返回一个自定义字体*/
    public static Font getCustomFont(ResourceLocation fontLocation,boolean filterFishyGlyphs){
        FontSet fontSet = Minecraft.getInstance().font.getFontSet(fontLocation);
        Function<ResourceLocation, FontSet> function = location -> fontSet;
        return new Font(function,filterFishyGlyphs);
    }

}
