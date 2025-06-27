package com.renyigesai.bakeries.item;

import com.renyigesai.bakeries.init.BakeriesSounds;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class MusicDiscBakingInProgress extends RecordItem {
    public MusicDiscBakingInProgress() {
        super(15, BakeriesSounds.MUSIC_DISC_BAKING_IN_PROGRESS.get(), new Item.Properties().stacksTo(1).rarity(Rarity.RARE), 168);
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltip, TooltipFlag pFlag) {
        pTooltip.add(Component.translatable("item.bakeries.music_disc_baking_in_progress.desc").withStyle(ChatFormatting.GRAY));
    }
}
