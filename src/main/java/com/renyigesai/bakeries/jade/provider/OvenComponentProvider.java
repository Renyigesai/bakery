package com.renyigesai.bakeries.jade.provider;

import com.renyigesai.bakeries.block.oven.OvenBlockEntity;
import com.renyigesai.bakeries.jade.Identifiers;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import snownee.jade.api.BlockAccessor;
import snownee.jade.api.IBlockComponentProvider;
import snownee.jade.api.IServerDataProvider;
import snownee.jade.api.ITooltip;
import snownee.jade.api.config.IPluginConfig;

public enum OvenComponentProvider implements IBlockComponentProvider, IServerDataProvider<BlockAccessor> {
    INSTANCE;

    @Override
    public void appendTooltip(ITooltip tooltip, BlockAccessor accessor, IPluginConfig config) {
        if (accessor.getServerData().contains("cooking_times") && accessor.getServerData().contains("max_cooking_times")) {
            int[] cooking_times = accessor.getServerData().getIntArray("cooking_times");
            int[] max_cooking_times = accessor.getServerData().getIntArray("max_cooking_times");

            int cooking_time_1 = cooking_times[0];
            int cooking_time_2 = cooking_times[1];
            int cooking_time_3 = cooking_times[2];
            int cooking_time_4 = cooking_times[3];
            int max_cooking_time_1 = max_cooking_times[0];
            int max_cooking_time_2 = max_cooking_times[1];
            int max_cooking_time_3 = max_cooking_times[2];
            int max_cooking_time_4 = max_cooking_times[3];
            int p_1 = max_cooking_time_1 == 0 ? 0 : (int) (cooking_time_1 * 100 / max_cooking_time_1);
            int p_2 = max_cooking_time_2 == 0 ? 0 : (int) (cooking_time_2 * 100 / max_cooking_time_2);
            int p_3 = max_cooking_time_3 == 0 ? 0 : (int) (cooking_time_3 * 100 / max_cooking_time_3);
            int p_4 = max_cooking_time_4 == 0 ? 0 : (int) (cooking_time_4 * 100 / max_cooking_time_4);
            tooltip.add(Component.literal(" "));
            tooltip.append(Component.translatable("bakeries.cooking_time").append("1: " + p_1 + "%"));
            tooltip.add(Component.literal(" "));
            tooltip.append(Component.translatable("bakeries.cooking_time").append("2: " + p_2 + "%"));
            tooltip.add(Component.literal(" "));
            tooltip.append(Component.translatable("bakeries.cooking_time").append("3: " + p_3 + "%"));
            tooltip.add(Component.literal(" "));
            tooltip.append(Component.translatable("bakeries.cooking_time").append("4: " + p_4 + "%"));

        }
    }

    @Override
    public void appendServerData(CompoundTag tag, BlockAccessor accessor) {
        OvenBlockEntity brewingStand = (OvenBlockEntity)accessor.getBlockEntity();
        tag.putIntArray("cooking_times", brewingStand.cooking_times);
        tag.putIntArray("max_cooking_times", brewingStand.max_cooking_times);
    }

    @Override
    public ResourceLocation getUid() {
        return Identifiers.OVEN;
    }
}
