package com.renyigesai.bakeries.item;

import com.renyigesai.bakeries.api.item.PileItem;
import com.renyigesai.bakeries.init.BakeriesBlocks;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;

public class OliveOilItem extends PileItem {
    public OliveOilItem() {
        super(BakeriesBlocks.OLIVE_OIL.get(), new PileProperties().itemProperties(new Item.Properties().durability(6)).placeSound(SoundEvents.GLASS_PLACE));
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
