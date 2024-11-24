package com.renyigesai.bakeries.event;

import com.renyigesai.bakeries.BakeriesMod;
import com.renyigesai.bakeries.init.BakeriesItems;
import com.renyigesai.bakeries.villager.BakeriesVillagers;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraftforge.event.village.VillagerTradesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;

@Mod.EventBusSubscriber(modid = BakeriesMod.MODID)

public class BakeryEvent {
    @SubscribeEvent
    public static void addCustomTrades(VillagerTradesEvent event){
//
        if (event.getType() == BakeriesVillagers.PISTRINA_MASTER.get()){
            Int2ObjectMap<List<VillagerTrades.ItemListing>> trades = event.getTrades();
            int villagerLevel_1 = 1;
            int villagerLevel_2 = 2;
            int villagerLevel_3 = 3;
            int villagerLevel_4 = 4;
            int villagerLevel_5 = 5;
            //level 1
            trades.get(villagerLevel_1).add((traner,rand) -> new MerchantOffer(
                    new ItemStack(Items.EMERALD,8),new ItemStack(BakeriesItems.FLOUR.get(),2),16,1,0.05f));
            trades.get(villagerLevel_1).add((traner,rand) -> new MerchantOffer(
                    new ItemStack(Items.EMERALD,8),new ItemStack(BakeriesItems.WHOLE_WHEAT_FLOUR.get(),2),16,1,0.05f));
            trades.get(villagerLevel_1).add((traner,rand) -> new MerchantOffer(
                    new ItemStack(Items.EMERALD,4),new ItemStack(BakeriesItems.SALT.get(),2),16,2,0.05f));
            //level 2
            trades.get(villagerLevel_2).add((traner,rand) -> new MerchantOffer(
                    new ItemStack(Items.EMERALD,6),new ItemStack(BakeriesItems.BOTTLE_YEAST.get(),1),8,5,0.05f));
            //level 3
            trades.get(villagerLevel_3).add((traner,rand) -> new MerchantOffer(
                    new ItemStack(BakeriesItems.BUTTER_CUBE.get(),4),new ItemStack(Items.EMERALD,2),4,10,0.05f));
            trades.get(villagerLevel_3).add((traner,rand) -> new MerchantOffer(
                    new ItemStack(BakeriesItems.BROWN_SUGAR_CUBE.get(),4),new ItemStack(Items.EMERALD,1),8,5,0.05f));
            trades.get(villagerLevel_3).add((traner,rand) -> new MerchantOffer(
                    new ItemStack(BakeriesItems.BOTTLE_CREAM.get(),1),new ItemStack(Items.EMERALD,1),8,5,0.05f));
            trades.get(villagerLevel_3).add((traner,rand) -> new MerchantOffer(
                    new ItemStack(BakeriesItems.SWEET_DOUGH.get(),1),new ItemStack(Items.EMERALD,3),2,10,0.05f));
            //level 4
            trades.get(villagerLevel_4).add((traner,rand) -> new MerchantOffer(
                    new ItemStack(Items.EMERALD,6),new ItemStack(BakeriesItems.ROUND_BREAD.get(),4),4,15,0.0f));
            trades.get(villagerLevel_4).add((traner,rand) -> new MerchantOffer(
                    new ItemStack(Items.EMERALD,3),new ItemStack(BakeriesItems.BAGUETTE.get(),1),2,10,0.0f));
            trades.get(villagerLevel_4).add((traner,rand) -> new MerchantOffer(
                    new ItemStack(Items.EMERALD,5),new ItemStack(BakeriesItems.COUNTRY_BREAD.get(),1),2,10,0.0f));
            //level 5
            trades.get(villagerLevel_5).add((traner,rand) -> new MerchantOffer(
                    new ItemStack(Items.EMERALD,16),new ItemStack(BakeriesItems.CROISSANT.get(),3),2,30,0.0f));
            trades.get(villagerLevel_5).add((traner,rand) -> new MerchantOffer(
                    new ItemStack(Items.EMERALD,10),new ItemStack(BakeriesItems.CINNAMON_ROLL.get(),2),2,30,0.0f));
            trades.get(villagerLevel_5).add((traner,rand) -> new MerchantOffer(
                    new ItemStack(Items.EMERALD,12),new ItemStack(BakeriesItems.TOAST.get(),1),2,30,0.0f));
        }
    }

}
