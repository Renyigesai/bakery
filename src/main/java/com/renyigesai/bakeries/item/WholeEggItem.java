package com.renyigesai.bakeries.item;

import com.renyigesai.bakeries.init.BakeriesItems;
import com.renyigesai.bakeries.util.ItemUtil;
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
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        ItemStack hand = pPlayer.getItemInHand(pUsedHand);
        if (hand.is(BakeriesItems.WHOLE_EGG.get())){
            if (!pPlayer.getAbilities().instabuild){
                hand.shrink(1);
            }
            ItemUtil.givePlayerItem(pPlayer,new ItemStack(BakeriesItems.RAW_PROTEIN.get()));
            ItemUtil.givePlayerItem(pPlayer,new ItemStack(BakeriesItems.RAW_EGG_YOLK.get()));
            return InteractionResultHolder.success(hand);
        }
        return super.use(pLevel, pPlayer, pUsedHand);
    }
}
