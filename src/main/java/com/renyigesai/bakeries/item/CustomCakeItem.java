package com.renyigesai.bakeries.item;

import com.renyigesai.bakeries.block.custom_cake.CustomCakeBlockEntity;
import com.renyigesai.bakeries.util.measurer.CakeEffectRules;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class CustomCakeItem extends BlockItem {


    public CustomCakeItem(Block pBlock, Properties pProperties) {
        super(pBlock, pProperties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        ItemStack itemstack = pPlayer.getItemInHand(pUsedHand);
        pPlayer.startUsingItem(pUsedHand);
        return InteractionResultHolder.consume(itemstack);
    }

    @Override
    public int getUseDuration(ItemStack pStack) {
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
        if (living instanceof Player player){
            player.getFoodData().eat(getHunger(food),getSaturation(food));
            player.awardStat(Stats.ITEM_USED.get(this));
            if (player instanceof ServerPlayer) {
                CriteriaTriggers.CONSUME_ITEM.trigger((ServerPlayer)player, food);
            }
        }
        List<MobEffectInstance> effects = getEffects(food);
        if (!effects.isEmpty()){
            List<MobEffectInstance> mobEffectInstances = CakeEffectRules.effectIteration(effects);
            for (MobEffectInstance mobEffectInstance : mobEffectInstances) {
                living.addEffect(mobEffectInstance);
            }
        }
        return food;
    }

    @Override
    public InteractionResult place(BlockPlaceContext pContext) {
        ItemStack itemInHand = pContext.getItemInHand();
        CompoundTag tag = itemInHand.getOrCreateTag();
        if (!tag.contains("PartId") || getPartIds(itemInHand).isEmpty()){
            return InteractionResult.FAIL;
        }
        super.place(pContext);
        Level level = pContext.getLevel();
        BlockPos pos = pContext.getClickedPos();
        BlockEntity blockEntity = level.getBlockEntity(pos);
        if (blockEntity instanceof CustomCakeBlockEntity cc) {
            if (tag.contains("PartId")){
                cc.setPartId(tag.getString("PartId"));
            }
            if (tag.contains("Hunger")){
                cc.setHunger(tag.getInt("Hunger"));
            }
            if (tag.contains("Saturation")){
                cc.setSaturation(tag.getFloat("Saturation"));
            }

            List<MobEffectInstance> list = new ArrayList<>();
            if (tag.contains("Effects", Tag.TAG_LIST)){
                ListTag effects = tag.getList("Effects", Tag.TAG_COMPOUND);
                for (int i = 0; i < effects.size(); i++) {
                    MobEffectInstance loaded = MobEffectInstance.load(effects.getCompound(i));
                    if (loaded != null) {
                        list.add(loaded);
                    }
                }
                cc.setEffects(list);
            }

            if (tag.contains("Name")){
                cc.setName(tag.getString("Name"));
            }
        }
        return InteractionResult.SUCCESS;
    }

    public static void setCustomCakeNBT(CustomCakeBlockEntity cc, ItemStack stack){
        CompoundTag tag = stack.getOrCreateTag();

        tag.putString("PartId",cc.getPartId());
        tag.putInt("Hunger",cc.getHunger());
        tag.putFloat("Saturation",cc.getSaturation());

        ListTag list = new ListTag();
        for (MobEffectInstance effect : cc.getEffects()) {
            list.add(effect.save(new CompoundTag()));
        }
        tag.put("Effects",list);

        tag.putString("Name",cc.getName());
    }

    public static List<String> getPartIds(ItemStack stack) {
        if (!stack.getOrCreateTag().contains("PartId")){
            return Collections.emptyList();
        }
        return Arrays.stream(stack.getOrCreateTag().getString("PartId").split("&"))
                .filter(s -> !s.isEmpty())
                .collect(Collectors.toList());
    }

    public static List<MobEffectInstance> getEffects(ItemStack stack){
        List<MobEffectInstance> list = new ArrayList<>();
        CompoundTag tag = stack.getOrCreateTag();
        if (tag.contains("Effects", Tag.TAG_LIST)){
            ListTag effects = tag.getList("Effects", Tag.TAG_COMPOUND);
            for (int i = 0; i < effects.size(); i++) {
                MobEffectInstance loaded = MobEffectInstance.load(effects.getCompound(i));
                if (loaded != null) {
                    list.add(loaded);
                }
            }
        }
        return list;
    }

    public static int getHunger(ItemStack stack){
        CompoundTag tag = stack.getOrCreateTag();
        return tag.contains("Hunger") ? tag.getInt("Hunger") : 0;
    }

    public static float getSaturation(ItemStack stack){
        CompoundTag tag = stack.getOrCreateTag();
        return tag.contains("Saturation") ? tag.getFloat("Saturation") : 0f;
    }

    @Override
    public Component getName(ItemStack pStack) {
        if (pStack.getOrCreateTag().contains("Name")){
            String name = pStack.getOrCreateTag().getString("Name");
            if (!name.equals("")){
                return Component.literal(name);
            }
        }
        return super.getName(pStack);
    }
}
