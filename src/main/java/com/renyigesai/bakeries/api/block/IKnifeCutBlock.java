package com.renyigesai.bakeries.api.block;

import com.renyigesai.bakeries.common.tag.CommonTags;
import com.renyigesai.bakeries.common.utils.ItemUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.Property;

public interface IKnifeCutBlock {
    IntegerProperty SLICE = IntegerProperty.create("slice",1,4);

    /*获取传入的物品是否为可用的刀*/
    default boolean isKnifeItem(ItemStack itemStack) {
        return itemStack.is(CommonTags.BREAD_KNIFE) || itemStack.is(ItemTags.create(ResourceLocation.fromNamespaceAndPath("c","tools/knife")));
    }

    /*返回切片方块状态,对于只切一刀的方块请返回null*/
    Property<Integer> getSliceProperty();

    /*返回最大切片数量*/
    int getMaxSlice();

    /*返回每一次切片的掉落物数量*/
    int getSliceItemCount();

    /*返回每一次切片的掉落物数量*/
    Item getSliceItem();

    boolean isCut(BlockState state);

    default void cut(Level level, BlockState state, BlockPos pos, LivingEntity entity, ItemStack hand, EquipmentSlot slot) {
        boolean b1 = getSliceProperty() != null;
        boolean b2 = false;
        if (b1) {
            int slice = state.getValue(getSliceProperty());
            if (slice < getMaxSlice()) {
                level.setBlock(pos, state.setValue(getSliceProperty(), slice + 1), 3);
            }else {
                b2 = true;
            }
        }
        if (!b1 || b2){
            level.removeBlock(pos,false);
        }
        ItemUtils.spawnItemEntity(level, new ItemStack(getSliceItem(), getSliceItemCount()),pos);
        hand.hurtAndBreak(1,entity,slot);
        level.playSound(null, pos, SoundEvents.WOOL_STEP, SoundSource.BLOCKS, 0.8F, 0.8F);
    }
}
