package com.renyigesai.bakeries.event;

import com.renyigesai.bakeries.init.BakeriesItems;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.enchanting.EnchantmentLevelSetEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class EnchantmentSetEvent {
    @SubscribeEvent
    public static void setEnchantment(EnchantmentLevelSetEvent event){
        if (event.getItem().equals(new ItemStack(BakeriesItems.BAGUETTE.get()))){
            event.setEnchantLevel(0);
        }

    }

}
