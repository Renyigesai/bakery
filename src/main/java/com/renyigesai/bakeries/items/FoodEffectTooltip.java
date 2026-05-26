package com.renyigesai.bakeries.items;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public final class FoodEffectTooltip {
    private FoodEffectTooltip() {
    }

    public static void append(List<Component> tooltipComponents, Entry... entries) {
        for (Entry entry : entries) {
            tooltipComponents.add(entry.toComponent().copy().withStyle(ChatFormatting.BLUE));
        }
    }

    public record Entry(MobEffect effect, int durationTicks, int amplifier) {
        public MobEffectInstance createInstance() {
            return new MobEffectInstance(effect, durationTicks, amplifier);
        }

        private Component toComponent() {
            Component component = Component.translatable(effect.getDescriptionId());
            if (amplifier > 0) {
                component = component.copy().append(" ").append(Component.translatable("enchantment.level." + (amplifier + 1)));
            }
            return component.copy().append(" ").append(formatDuration(durationTicks));
        }
    }

    private static String formatDuration(int ticks) {
        int totalSeconds = ticks / 20;
        int minutes = totalSeconds / 60;
        int seconds = totalSeconds % 60;
        if (minutes > 0 && seconds > 0) {
            return minutes + "min" + seconds + "s";
        }
        if (minutes > 0) {
            return minutes + "min";
        }
        return seconds + "s";
    }

    public interface Appender {
        default void appendFoodEffectTooltip(ItemStack stack, @Nullable Level level, List<Component> tooltipComponents, TooltipFlag tooltipFlag, Entry... entries) {
            FoodEffectTooltip.append(tooltipComponents, entries);
        }
    }
}
