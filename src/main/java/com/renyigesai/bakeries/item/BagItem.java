package com.renyigesai.bakeries.item;

import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class BagItem extends Item {

    protected final ItemStackHandler inventory = new ItemStackHandler(4);

    public BagItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
//        ItemStackHandler inventory = new ItemStackHandler(4);
        ItemStack stack = pPlayer.getItemInHand(pUsedHand);
        ItemStack offStack = pPlayer.getOffhandItem();
            saveOrCreateItems(stack,offStack);
        return super.use(pLevel, pPlayer, pUsedHand);
    }

    public void saveOrCreateItems(ItemStack stack,ItemStack storeStack){
        if (stack.getOrCreateTag().get("Inventory") == null){
            System.out.println("Inventory No");
            stack.getOrCreateTag().put("Inventory",inventory.serializeNBT());
        }
        inventory.setStackInSlot(0,new ItemStack(Items.APPLE));

    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        for (int i = 0; i < inventory.getSlots(); i++) {
            ItemStack stackInSlot = inventory.getStackInSlot(i);
            pTooltipComponents.add(Component.nullToEmpty(stackInSlot.getDisplayName().getString() + " x" + stackInSlot.getCount()));
        }
    }
}
