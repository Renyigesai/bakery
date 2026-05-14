package com.renyigesai.bakeries.items;

import com.renyigesai.bakeries.init.BakeriesItems;
import com.renyigesai.bakeries.utils.ItemUtils;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;

public class BottleButterItem extends Item {
    public BottleButterItem() {
        super(new Properties().craftRemainder(Items.GLASS_BOTTLE).stacksTo(16));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        ItemStack itemInHand = player.getItemInHand(usedHand);
        if (!level.isClientSide()) {
            ItemUtils.shrink(itemInHand, 1, player);
            ItemUtils.givePlayerItem(player, new ItemStack(BakeriesItems.BUTTER_CUBE));
            return InteractionResultHolder.success(itemInHand);
        }
        return super.use(level, player, usedHand);
    }
}
