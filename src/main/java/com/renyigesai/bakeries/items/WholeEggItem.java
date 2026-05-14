package com.renyigesai.bakeries.items;

import com.renyigesai.bakeries.init.BakeriesItems;
import com.renyigesai.bakeries.utils.ItemUtils;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class WholeEggItem extends Item {
    public WholeEggItem() {
        super(new Item.Properties());
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        ItemStack hand = player.getItemInHand(usedHand);
        if (hand.is(BakeriesItems.WHOLE_EGG)) {
            if (!player.getAbilities().instabuild) {
                hand.shrink(1);
            }
            ItemUtils.givePlayerItem(player, new ItemStack(BakeriesItems.RAW_PROTEIN));
            ItemUtils.givePlayerItem(player, new ItemStack(BakeriesItems.RAW_EGG_YOLK));
            return InteractionResultHolder.success(hand);
        }
        return super.use(level, player, usedHand);
    }
}
