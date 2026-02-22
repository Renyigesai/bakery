package com.renyigesai.bakeries.item;

import com.renyigesai.bakeries.block.ColdDrinkBlock;
import com.renyigesai.bakeries.init.BakeriesMobEffects;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ColdDrinkItem extends RepeatEatItem{
    private final boolean is_thirst;
    private final int thirst;
    private final int quenched;
    private final int upEffect;

    public ColdDrinkItem(Block block, Properties pProperties, boolean effectTooltip, boolean customField, int upEffect) {
        super(block, ColdDrinkBlock.integerProperty, pProperties, effectTooltip, customField);
        this.upEffect = upEffect;
        this.is_thirst = false;
        this.thirst = 0;
        this.quenched = 0;
    }

    public ColdDrinkItem(Block block, Properties pProperties, boolean is_thirst, int thirst, int quenched, boolean effectTooltip, boolean customField, int upEffect) {
        super(block, ColdDrinkBlock.integerProperty, pProperties, effectTooltip, customField);
        this.is_thirst = is_thirst;
        this.thirst = thirst;
        this.quenched = quenched;
        this.upEffect = upEffect;
    }

    public boolean isThirst() {
        return is_thirst;
    }

    public int getThirst() {
        return thirst;
    }

    public int getQuenched() {
        return quenched;
    }

    @Override
    public SoundEvent getPlaceSound() {
        return SoundEvents.GLASS_PLACE;
    }

    @Override
    public boolean canDrink() {
        return true;
    }

    public int getUpEffect() {
        return upEffect;
    }

    @Override
    public void addAllEffect(FoodProperties foodProperties, LivingEntity player, Level level) {
        super.addAllEffect(foodProperties, player, level);
        if (this.upEffect > 0){
            if (!level.isClientSide()) {
                if (player.hasEffect(BakeriesMobEffects.ENJOY.get()) && player.getEffect(BakeriesMobEffects.ENJOY.get()).getAmplifier() < this.upEffect) {
                    int amplifier = player.getEffect(BakeriesMobEffects.ENJOY.get()).getAmplifier();
                    player.addEffect(new MobEffectInstance(BakeriesMobEffects.ENJOY.get(), player.getEffect(BakeriesMobEffects.ENJOY.get()).getDuration() + 200, amplifier + 1), player);
                }
            }
        }
    }

    @Override
    public void repeatEat(Level level, ItemStack food, LivingEntity living) {
        if (this.isThirst()) {
            CompoundTag compoundTag = living.serializeNBT();
            if (compoundTag.getCompound("ForgeCaps").getCompound("thirst:thirst").contains("thirst") && compoundTag.getCompound("ForgeCaps").getCompound("thirst:thirst").contains("quenched")) {
                int nbtThirst = compoundTag.getCompound("ForgeCaps").getCompound("thirst:thirst").getInt("thirst");
                int thirst = getThirst() + nbtThirst > 20 ? 20 - nbtThirst : getThirst();
                int nbtQuenched = compoundTag.getCompound("ForgeCaps").getCompound("thirst:thirst").getInt("quenched");
                int quenched = getQuenched() + nbtQuenched > 20 ? 20 - nbtThirst : getQuenched();
                compoundTag.getCompound("ForgeCaps").getCompound("thirst:thirst").putInt("thirst", nbtThirst + thirst);
                compoundTag.getCompound("ForgeCaps").getCompound("thirst:thirst").putInt("quenched", nbtQuenched + quenched);
                living.deserializeNBT(compoundTag);
            }
        }
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, @NotNull List<Component> tooltip, @NotNull TooltipFlag isAdvanced) {
        tooltip.add(Component.translatable("item.bakeries.tips.cold_drink").withStyle(ChatFormatting.BLUE));
        if (this.upEffect > 0) {
            tooltip.add(Component.translatable("item.bakeries.tips.cold_drink_2", Component.translatable("potion.potency." + this.upEffect)).withStyle(ChatFormatting.DARK_GRAY));
        }
        super.appendHoverText(stack, level, tooltip, isAdvanced);
    }
}
