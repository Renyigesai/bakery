package com.renyigesai.bakeries.common.items;

import com.renyigesai.bakeries.api.items.PileItem;
import com.renyigesai.bakeries.common.init.BakeriesBlocks;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.Block;

public class OliveOilItem extends PileItem {

    public OliveOilItem() {
        super(BakeriesBlocks.OLIVE_OIL.get(), new Item.Properties().durability(6));
    }

    @Override
    public boolean hasCraftingRemainingItem(ItemStack stack) {
        return true;
    }

    @Override
    public ItemStack getCraftingRemainingItem(ItemStack itemstack) {
        ItemStack retval = new ItemStack(this);
        retval.setDamageValue(itemstack.getDamageValue() + 1);
        if (retval.getDamageValue() >= retval.getMaxDamage()) {
            return ItemStack.EMPTY;
        }
        return retval;
    }

    @Override
    public boolean isExtra(UseOnContext pContext) {
        return pContext.getItemInHand().getDamageValue() == 0;
    }

    @Override
    public boolean isRepairable(ItemStack itemstack) {
        return false;
    }
}
