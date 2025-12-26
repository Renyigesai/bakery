package com.renyigesai.bakeries.item;

import com.renyigesai.bakeries.block.ColdDrinkBlock;
import com.renyigesai.bakeries.init.BakeriesItems;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ColdDrinkItem extends RepeatEatItem{
    private final boolean is_thirst;
    private final int thirst;
    private final int quenched;

    public ColdDrinkItem(Block block, Properties pProperties, boolean effectTooltip, boolean customField) {
        super(block, ColdDrinkBlock.integerProperty, pProperties, effectTooltip, customField);
        this.is_thirst = false;
        this.thirst = 0;
        this.quenched = 0;
    }

    public ColdDrinkItem(Block block, Properties pProperties, boolean is_thirst,int thirst,int quenched,boolean effectTooltip, boolean customField) {
        super(block, ColdDrinkBlock.integerProperty, pProperties, effectTooltip, customField);
        this.is_thirst = is_thirst;
        this.thirst = thirst;
        this.quenched = quenched;
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

    @Override
    public void rEat(Level level, ItemStack food, LivingEntity living) {
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
        super.appendHoverText(stack, level, tooltip, isAdvanced);
    }
}
