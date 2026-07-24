package com.renyigesai.bakeries.block.custom_cake;

import com.renyigesai.bakeries.init.BakeriesBlocks;
import com.renyigesai.bakeries.util.measurer.CakePartMeasurer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class CustomCakeBlockEntity extends BlockEntity {
    public String partId = "";
    private float[] yRot = new float[]{0,0,0,0};
    private byte[] partUse = new byte[]{0,0,0,0};
    private int hunger;
    private float saturation;
    private List<MobEffectInstance> effects = new ArrayList<>();
    private String name = "";


    public CustomCakeBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(BakeriesBlocks.CUSTOM_CAKE_ENTITY.get(), pPos, pBlockState);
    }

    @Override
    protected void saveAdditional(CompoundTag pTag) {
        super.saveAdditional(pTag);
        pTag.putString("PartId",this.partId);

        int[] intArray0 = new int[yRot.length];
        for (int i = 0; i < yRot.length; i++) {
            intArray0[i] = Float.floatToRawIntBits(yRot[i]);
        }
        pTag.putIntArray("YRot",intArray0);
        pTag.putByteArray("PartUse",this.partUse);
        pTag.putInt("Hunger",this.hunger);
        pTag.putFloat("Saturation",this.saturation);

        ListTag list = new ListTag();
        for (MobEffectInstance effect : effects) {
            list.add(effect.save(new CompoundTag()));
        }
        pTag.put("Effects", list);

        pTag.putString("Name",this.name);
    }

    @Override
    public void load(CompoundTag pTag) {
        super.load(pTag);
        this.partId = pTag.getString("PartId");

        int[] intArray0 = pTag.getIntArray("YRot");
        this.yRot = new float[intArray0.length];
        for (int i = 0; i < intArray0.length; i++) {
            this.yRot[i] = Float.intBitsToFloat(intArray0[i]);
        }

        this.partUse = pTag.getByteArray("PartUse");

        this.hunger = pTag.getInt("Hunger");
        this.saturation = pTag.getInt("Saturation");

        effects.clear();
        if (pTag.contains("Effects", Tag.TAG_LIST)) {
            ListTag list = pTag.getList("Effects", Tag.TAG_COMPOUND);
            for (int i = 0; i < list.size(); i++) {
                MobEffectInstance loaded = MobEffectInstance.load(list.getCompound(i));
                if (loaded != null) {
                    effects.add(loaded);
                }
            }
        }

        this.name = pTag.getString("Name");
    }

    public int getHunger() {
        return hunger;
    }

    public float getSaturation() {
        return saturation;
    }

    public float[] getYRot() {
        return yRot;
    }

    public String getName() {
        return name;
    }

    @Override
    public CompoundTag getUpdateTag() {
        return this.saveWithoutMetadata();
    }

    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    public String getPartId() {
        return partId;
    }

    public byte[] getPartUse() {
        return partUse;
    }

    public List<String> getPartIds() {
        if (partId.isEmpty()) {
            return Collections.emptyList();
        }
        return Arrays.stream(partId.split("&"))
                .filter(s -> !s.isEmpty())
                .collect(Collectors.toList());
    }

    public List<MobEffectInstance> getEffects() {
        return effects;
    }

    public void setPartId(String partId) {
        this.partId = partId;
    }

    public void setHunger(int hunger) {
        this.hunger = hunger;
    }

    public void setSaturation(float saturation) {
        this.saturation = saturation;
    }

    public void setEffects(List<MobEffectInstance> effects) {
        this.effects = effects;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPartUse(byte[] partUse) {
        this.partUse = partUse;
    }

    public void addPartId(CakePartData cakePartData) {
        String newId = cakePartData.getId();
        if (partId.isEmpty()) {
            partId = newId;
        } else {
            partId += "&" + newId;
        }
        String type = cakePartData.getType();
        switch (type) {
            case CakePartMeasurer.CAKE_BASE -> partUse[0] = 1;
            case CakePartMeasurer.CAKE_FILLING -> partUse[1] = 1;
            case CakePartMeasurer.CAKE_CREAM -> partUse[2] = 1;
            case CakePartMeasurer.CAKE_TOPPING -> partUse[3] = 1;
        }
    }


    public boolean test(CakePartData cakePartData){
        String type = cakePartData.getType();
        boolean[] use = new boolean[] {false,false,false,false};
        for (int i = 0; i < partUse.length; i++) {
            use[i] = partUse[i] == 0;
        }
        switch (type) {
            case CakePartMeasurer.CAKE_BASE -> {
                return use[0];
            }
            case CakePartMeasurer.CAKE_FILLING -> {
                return use[1];
            }
            case CakePartMeasurer.CAKE_CREAM -> {
                return use[2];
            }
            case CakePartMeasurer.CAKE_TOPPING -> {
                return use[3];
            }
            default -> {
                return false;
            }
        }
    }

    public void addCakePart(CakePartData cakePartData){
        addPartId(cakePartData);
        this.hunger += cakePartData.getHunger();
        this.saturation += cakePartData.getSaturation();
        MobEffect effect = BuiltInRegistries.MOB_EFFECT.get(cakePartData.getPotionEffect());
        if (effect != null){
            int effectAmplifier = cakePartData.getEffectAmplifier();
            int effectDuration = cakePartData.getEffectDuration();
            this.effects.add(new MobEffectInstance(effect,effectDuration,effectAmplifier));
        }
        setChanged();
        if (this.level != null) {
            this.level.sendBlockUpdated(this.getBlockPos(),this.getBlockState(),this.getBlockState(),3);
        }
    }
}
