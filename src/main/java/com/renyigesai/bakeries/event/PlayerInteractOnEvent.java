package com.renyigesai.bakeries.event;

import com.renyigesai.bakeries.accessor.VillagerAccessor;
import com.renyigesai.bakeries.init.BakeriesItems;
import com.renyigesai.bakeries.item.RepeatEatItem;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class PlayerInteractOnEvent {
    @SubscribeEvent
    public static void on(PlayerInteractEvent.EntityInteract event) {
        Player entity = event.getEntity();
        if (event.getHand() != entity.getUsedItemHand())
            return;
        ItemStack hand = entity.getItemInHand(InteractionHand.MAIN_HAND);
        Entity target = event.getTarget();
        if (hand.is(BakeriesItems.ICED_LATTE.get()) && target instanceof Villager villager){
            if (event.isCancelable()) {
                event.setCanceled(true);
            }
            villager.restock();
            ((VillagerAccessor)villager).bakery$setNumberOfRestocksToday(0);
//            if (hand.getDamageValue() == hand.getMaxDamage()-1) {
//                hand.shrink(1);
//                ItemUtil.givePlayerItem(entity,new ItemStack(BakeriesItems.DRINK_CUP.get()));
//            }else {
//                hand.hurt(1, RandomSource.create(), null);
//            }
            RepeatEatItem.rHurt(entity,hand,new ItemStack(BakeriesItems.DRINK_CUP.get()));
            villager.level().playSound(null, BlockPos.containing(villager.getX(),villager.getY(),villager.getZ()), SoundEvents.GENERIC_DRINK, SoundSource.BLOCKS);
        }
    }
}
