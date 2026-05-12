package com.renyigesai.bakeries.common.items;

import com.renyigesai.bakeries.common.init.BakeriesItems;
import com.renyigesai.bakeries.common.utils.ItemUtils;
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
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        ItemStack itemInHand = pPlayer.getItemInHand(pUsedHand);
        if (!pLevel.isClientSide()){
            ItemUtils.shrink(itemInHand,1,pPlayer);
            ItemUtils.givePlayerItem(pPlayer,new ItemStack(BakeriesItems.BUTTER_CUBE.get()));
            return InteractionResultHolder.success(itemInHand);
        }
        return super.use(pLevel, pPlayer, pUsedHand);
    }
}
