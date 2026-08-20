package com.renyigesai.bakeries.common.items;

import com.renyigesai.bakeries.common.blocks.custom_cake.CustomCakeBlockEntity;
import com.renyigesai.bakeries.common.init.BakeriesBlocks;
import com.renyigesai.bakeries.common.init.BakeriesDataComponents;
import com.renyigesai.bakeries.common.init.BakeriesRarity;
import com.renyigesai.bakeries.common.utils.ItemUtils;
import com.renyigesai.bakeries.common.utils.measurer.CakeEffectRules;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;

import java.util.*;
import java.util.stream.Collectors;

public class CustomCakeItem extends BlockItem {

    private final int eatCountMax = 4;


    public CustomCakeItem() {
        super(BakeriesBlocks.CUSTOM_CAKE.get(), new Item.Properties()
                .stacksTo(1)
                .component(BakeriesDataComponents.EAT_COUNT.get(),4)
                .component(BakeriesDataComponents.EAT_COUNT_MAX.get(),4)
                .component(BakeriesDataComponents.CUSTOM_CAKE_PART_ID.get(),"")
                .component(BakeriesDataComponents.CUSTOM_CAKE_HUNGER.get(),0)
                .component(BakeriesDataComponents.CUSTOM_CAKE_SATURATION.get(),0f)
                .component(BakeriesDataComponents.CUSTOM_CAKE_NAME.get(),"")
                .component(BakeriesDataComponents.CUSTOM_CAKE_PART_USE.get(),new ArrayList<>())
                .rarity(BakeriesRarity.getAdvanced())
        );
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        ItemStack itemstack = pPlayer.getItemInHand(pUsedHand);
        pPlayer.startUsingItem(pUsedHand);
        return InteractionResultHolder.consume(itemstack);
    }

    /**1.3.1新增*/
    @Override
    public InteractionResult useOn(UseOnContext pContext) {
        ItemStack stack = pContext.getItemInHand();
        if (isRepeatEat(stack)) {
            int eatCount = stack.getOrDefault(BakeriesDataComponents.EAT_COUNT,0);
            int eatCountMax = stack.getOrDefault(BakeriesDataComponents.EAT_COUNT_MAX,0);
            if (eatCount < eatCountMax){
                return InteractionResult.FAIL;
            }
        }
        return super.useOn(pContext);
    }

    @Override
    public int getUseDuration(ItemStack stack, LivingEntity entity) {
        return 32;
    }

    @Override
    public UseAnim getUseAnimation(ItemStack pStack) {
        return UseAnim.EAT;
    }

    @Override
    public ItemStack finishUsingItem(ItemStack pStack, Level pLevel, LivingEntity pLivingEntity) {
        return onConsume(pLevel,pLivingEntity,pStack);
    }

    public ItemStack onConsume(Level level, LivingEntity living, ItemStack food){
        eat(level, living, food);
        return consume(food,living);
    }

    public ItemStack consume(ItemStack stack,LivingEntity living){
        if (living instanceof Player player && player.getAbilities().instabuild){
            return stack;
        }
        if (isRepeatEat(stack)){
            int eatCount = stack.getOrDefault(BakeriesDataComponents.EAT_COUNT,-1);
            ItemStack cache = ItemStack.EMPTY;
            if (eatCount - 1 == 0){
                cache = stack.copy();
                stack.consume(1,living);
            }else {
                if (!(living instanceof Player) || !(living instanceof Player player && player.getAbilities().instabuild)){
                    stack.set(BakeriesDataComponents.EAT_COUNT,eatCount - 1);
                }
            }
            return (eatCount - 1 == 0 && cache.hasCraftingRemainingItem()) ? cache.getCraftingRemainingItem() : stack;
        }
        return stack;
    }

    public void eat(Level level, LivingEntity living, ItemStack food){
        if (living instanceof Player player){
            player.getFoodData().eat(getHunger(food) / this.eatCountMax,getSaturation(food) / this.eatCountMax);
            player.awardStat(Stats.ITEM_USED.get(this));
            if (player instanceof ServerPlayer) {
                CriteriaTriggers.CONSUME_ITEM.trigger((ServerPlayer)player, food);
            }
        }
        List<MobEffectInstance> effects = getEffects(food);
        if (!effects.isEmpty()){
            if (!level.isClientSide){
                List<MobEffectInstance> mobEffectInstances = CakeEffectRules.effectIteration(effects);
                for (MobEffectInstance mobEffectInstance : mobEffectInstances) {
                    living.addEffect(mobEffectInstance);
                }
            }
        }
    }

    @Override
    public InteractionResult place(BlockPlaceContext pContext) {
        ItemStack itemInHand = pContext.getItemInHand();
        if (!itemInHand.has(BakeriesDataComponents.CUSTOM_CAKE_PART_ID) || getPartIds(itemInHand).isEmpty()){
            return InteractionResult.FAIL;
        }
        super.place(pContext);
        Level level = pContext.getLevel();
        BlockPos pos = pContext.getClickedPos();
        BlockEntity blockEntity = level.getBlockEntity(pos);
        if (blockEntity instanceof CustomCakeBlockEntity cc) {
            cc.setPartId(getPartId(itemInHand));
            cc.setPartUse(getPartUse(itemInHand));
            cc.setHunger(getHunger(itemInHand));
            cc.setSaturation(getSaturation(itemInHand));
            cc.setEffects(getEffects(itemInHand));

            cc.setName(getName(itemInHand).getString());
        }
        return InteractionResult.SUCCESS;
    }

    public static void setCustomCakeNBT(CustomCakeBlockEntity cc, ItemStack stack){
        stack.set(BakeriesDataComponents.CUSTOM_CAKE_PART_ID,cc.getPartId());

        int[] partUse = cc.getPartUse();
        ArrayList<Integer> list = new ArrayList<>();
        for (int i = 0; i < partUse.length; i++) {
            list.add(i,partUse[i]);
        }
        stack.set(BakeriesDataComponents.CUSTOM_CAKE_PART_USE,list);

        stack.set(BakeriesDataComponents.CUSTOM_CAKE_HUNGER, cc.getHunger());
        stack.set(BakeriesDataComponents.CUSTOM_CAKE_SATURATION, cc.getSaturation());
        PotionContents potionContents = new PotionContents(Optional.empty(), Optional.empty(), cc.getEffects().stream().toList());
        stack.set(DataComponents.POTION_CONTENTS,potionContents);
        stack.set(BakeriesDataComponents.CUSTOM_CAKE_NAME, cc.getName());
    }

    public static List<String> getPartIds(ItemStack stack) {
        if (stack.get(BakeriesDataComponents.CUSTOM_CAKE_PART_ID) == null){
            return Collections.emptyList();
        }
        return Arrays.stream(stack.getOrDefault(BakeriesDataComponents.CUSTOM_CAKE_PART_ID,"").split("&"))
                .filter(s -> !s.isEmpty())
                .collect(Collectors.toList());
    }

    public static String getPartId(ItemStack stack) {
        return stack.getOrDefault(BakeriesDataComponents.CUSTOM_CAKE_PART_ID,"");
    }

    public static int getHunger(ItemStack stack){
        return stack.getOrDefault(BakeriesDataComponents.CUSTOM_CAKE_HUNGER,0);
    }

    public static float getSaturation(ItemStack stack){
        return stack.getOrDefault(BakeriesDataComponents.CUSTOM_CAKE_SATURATION,0f);
    }

    @Override
    public Component getName(ItemStack pStack) {
        String name = pStack.getOrDefault(BakeriesDataComponents.CUSTOM_CAKE_NAME, "");
        if (!name.equals("")){
            return Component.literal(name);
        }
        return super.getName(pStack);
    }

    public List<MobEffectInstance> getEffects(ItemStack stack){
        PotionContents potionContents = stack.getOrDefault(DataComponents.POTION_CONTENTS, PotionContents.EMPTY);
        return potionContents.customEffects();
    }

    public int[] getPartUse(ItemStack stack){
        if (stack.has(BakeriesDataComponents.CUSTOM_CAKE_PART_USE.get())){
            List<Integer> list = stack.get(BakeriesDataComponents.CUSTOM_CAKE_PART_USE.get());
            if (list != null){
                return list.stream().mapToInt(Integer::intValue).toArray();
            }
        }
        return new int[]{};
    }

    @Override
    public int getBarColor(ItemStack stack) {
        return 15574564;
    }

    public int getBarWidth(ItemStack stack) {

        if (isRepeatEat(stack)){
            Integer eatCount = stack.get(BakeriesDataComponents.EAT_COUNT);
            Integer eatCountMax = stack.get(BakeriesDataComponents.EAT_COUNT_MAX);
            if (eatCount != null && eatCountMax != null && eatCountMax > 0) {
                return Mth.clamp(Math.round(13.0F * eatCount / eatCountMax), 0, 13);
            }
        }
        return super.getBarWidth(stack);
    }

    @Override
    public boolean isBarVisible(ItemStack stack) {
        if (!isRepeatEat(stack)){
            return false;
        }
        Integer eatCount = stack.get(BakeriesDataComponents.EAT_COUNT);
        Integer eatCountMax = stack.get(BakeriesDataComponents.EAT_COUNT_MAX);
        if (eatCount != null &&  eatCountMax != null){
            return eatCount < eatCountMax;
        }
        return false;
    }

    public static boolean isRepeatEat(ItemStack stack){
        return stack.has(BakeriesDataComponents.EAT_COUNT_MAX) && stack.has(BakeriesDataComponents.EAT_COUNT);
    }
}
