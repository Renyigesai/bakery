package com.renyigesai.bakeries.common.init;

import com.renyigesai.bakeries.BakeriesMod;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.world.item.Item;

public class BUseItemAnimation {
    private BUseItemAnimation(){}
    public static void addCustomItemProperties(){
//        makeUsingAnimation(MSItems.WOOD_LIGHTERS.get());
//		makeStackSize(HXModItems.LING_QI_STONE.get());
//		makeStackSize(HXModItems.FULU.get());
    }
    //使用动画
    private static void makeBowAnimation(Item item) {
        ItemProperties.register(item, BakeriesMod.rl("pull"), (itemStack, level, livingEntity, pSeed) -> {
            if (livingEntity == null) {
                return 0.0F;
            } else {
                return livingEntity.getUseItem() != itemStack ? 0.0F : (float) (itemStack.getUseDuration(livingEntity) - livingEntity.getUseItemRemainingTicks()) / 20.0F;
            }
        });
        ItemProperties.register(item, BakeriesMod.rl("pulling"), (itemStack, level, livingEntity, pSeed) -> {
            return livingEntity != null && livingEntity.isUsingItem() && livingEntity.getUseItem() == itemStack ? 1.0F : 0.0F;
        });
    }
    private static void makeUsingAnimation(Item item) {
        ItemProperties.register(item, BakeriesMod.rl("using"), (itemStack, level, livingEntity, pSeed) -> {
            if (livingEntity == null) {
                return 0.0F;
            } else {
                if(livingEntity.getUseItem() == itemStack && livingEntity.isUsingItem()){
                    return 1.0F;
                }else {
                    return 0.0F;
                }
            }
        });
    }
    private static void makeStackSize(Item item) {
        ItemProperties.register(item, BakeriesMod.rl("stack_size"), (itemStack, level, livingEntity, pSeed) -> itemStack.getCount());
    }
}
