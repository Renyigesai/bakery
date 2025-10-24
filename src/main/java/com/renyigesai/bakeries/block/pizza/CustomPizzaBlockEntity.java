package com.renyigesai.bakeries.block.pizza;

import com.mojang.datafixers.util.Pair;
import com.renyigesai.bakeries.init.BakeriesBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CustomPizzaBlockEntity extends BlockEntity {

    private final ItemStackHandler inventory = new ItemStackHandler(4){
        @Override
        protected int getStackLimit(int slot, @NotNull ItemStack stack) {
            return 1;
        }

        @Override
        public @NotNull ItemStack insertItem(int slot, @NotNull ItemStack stack, boolean simulate) {
            return super.insertItem(slot, stack, simulate);
        }

        @Override
        public @NotNull ItemStack extractItem(int slot, int amount, boolean simulate) {
            return super.extractItem(slot, amount, simulate);
        }
    };

    private int nutrition;
    private float saturationMod;

    public CustomPizzaBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(BakeriesBlocks.CUSTOM_PIZZA_ENTITY.get(), pPos, pBlockState);
    }

    public ItemStackHandler getInventory() {
        return inventory;
    }

    public int getNutrition() {
        return nutrition;
    }

    public float getSaturationMod() {
        return saturationMod;
    }

    public void setNutrition(int nutrition) {
        this.nutrition = nutrition;
    }

    public void setSaturationMod(float saturationMod) {
        this.saturationMod = saturationMod;
    }

    public void setFoodProperties(int nutrition,float saturationMod){
        this.nutrition = nutrition;
        this.saturationMod = saturationMod;
    }

    public  List<List<Pair<MobEffectInstance, Float>>> getEffects(LivingEntity livingEntity){
        List<List<Pair<MobEffectInstance, Float>>> lists = new ArrayList<>();
        for (int i = 0; i < inventory.getSlots(); i++) {
            ItemStack stackInSlot = inventory.getStackInSlot(i);
            if (!stackInSlot.isEmpty() && stackInSlot.getItem().isEdible()){
                List<Pair<MobEffectInstance, Float>> effects = Objects.requireNonNull(new ItemStack(stackInSlot.getItem()).getFoodProperties(livingEntity)).getEffects();
                lists.add(effects);
            }
        }
        return lists;
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        if (tag.contains("Inventory")) {
            inventory.deserializeNBT(tag.getCompound("Inventory"));
        }
        nutrition = tag.getInt("Nutrition");
        saturationMod = tag.getFloat("SaturationMod");
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        tag.put("Inventory", inventory.serializeNBT());
        tag.putInt("Nutrition",nutrition);
        tag.putFloat("SaturationMod",saturationMod);
    }
}
