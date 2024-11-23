package com.renyigesai.bakery.event;

import com.renyigesai.bakery.BakeryMod;
import com.renyigesai.bakery.init.BakeryItems;
import com.renyigesai.bakery.villager.BakeryVillagers;
import it.unimi.dsi.fastutil.ints.Int2ObjectArrayMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.minecraft.data.worldgen.placement.VillagePlacements;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraftforge.event.village.VillagerTradesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;

@Mod.EventBusSubscriber(modid = BakeryMod.MODID)

public class BakeryEvent {
    @SubscribeEvent
    public static void addCustomTrades(VillagerTradesEvent event){
//        if (event.getType() == VillagerProfession.ARMORER){
//            Int2ObjectMap<List<VillagerTrades.ItemListing>> trades = event.getTrades();
//            ItemStack stack = new ItemStack(BakeryItems.BAGEL.get(),1);
//            int villagerLevel = 1;
//            trades.get(villagerLevel).add((traner,rand) -> new MerchantOffer(
//                    new ItemStack(Items.EMERALD,2),stack,10,8,0.2f));
//        }

        if (event.getType() == BakeryVillagers.PISTRINA_MASTER.get()){
            Int2ObjectMap<List<VillagerTrades.ItemListing>> trades = event.getTrades();
            ItemStack stack = new ItemStack(BakeryItems.BAGEL.get(),1);
            int villagerLevel_1 = 1;
            int villagerLevel_2 = 2;
            int villagerLevel_3 = 3;
            int villagerLevel_4 = 4;
            int villagerLevel_5 = 5;
            //level 1
            trades.get(villagerLevel_1).add((traner,rand) -> new MerchantOffer(
                    new ItemStack(Items.EMERALD,2),new ItemStack(BakeryItems.FLOUR.get(),2),16,2,0.05f));
            trades.get(villagerLevel_1).add((traner,rand) -> new MerchantOffer(
                    new ItemStack(Items.EMERALD,2),new ItemStack(BakeryItems.WHOLE_WHEAT_FLOUR.get(),2),16,2,0.05f));
            trades.get(villagerLevel_1).add((traner,rand) -> new MerchantOffer(
                    new ItemStack(Items.EMERALD,3),new ItemStack(BakeryItems.SALT.get(),2),16,2,0.05f));
            trades.get(villagerLevel_1).add((traner,rand) -> new MerchantOffer(
                    new ItemStack(Items.EMERALD,2),new ItemStack(Items.WHEAT,20),16,2,0.05f));
            //level 2
            trades.get(villagerLevel_2).add((traner,rand) -> new MerchantOffer(
                    new ItemStack(Items.EMERALD,6),new ItemStack(BakeryItems.BOTTLE_YEAST.get(),1),16,2,0.05f));
            //level 3
            trades.get(villagerLevel_3).add((traner,rand) -> new MerchantOffer(
                    new ItemStack(Items.EMERALD,6),new ItemStack(BakeryItems.BUTTER_CUBE.get(),1),16,2,0.05f));
            trades.get(villagerLevel_3).add((traner,rand) -> new MerchantOffer(
                    new ItemStack(Items.EMERALD,3),new ItemStack(BakeryItems.BROWN_SUGAR_CUBE.get(),4),16,2,0.05f));



        }
    }

}
