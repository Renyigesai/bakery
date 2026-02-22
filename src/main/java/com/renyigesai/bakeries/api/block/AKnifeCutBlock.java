package com.renyigesai.bakeries.api.block;

import com.renyigesai.bakeries.init.BakeriesItemTag;
import com.renyigesai.bakeries.util.ItemUtils;
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
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.Property;

public abstract class AKnifeCutBlock extends HorizontalDirectionalBlock {
    public static final IntegerProperty SLICE = IntegerProperty.create("slice",1,4);
    protected AKnifeCutBlock(Properties pProperties) {
        super(pProperties);
    }

    /*获取传入的物品是否为可用的刀*/
    public boolean isKnifeItem(ItemStack itemStack) {
        return itemStack.is(BakeriesItemTag.BREAD_KNIFE) || itemStack.is(ItemTags.create(new ResourceLocation("forge:tools/knives")));
    }

    /*返回切片方块状态,对于只切一刀的方块请返回null*/
    public abstract Property<Integer> getSliceProperty();

    /*返回最大切片数量*/
    public abstract int getMaxSlice();

   /*返回每一次切片的掉落物数量*/
    public abstract int getSliceItemCount();

    /*返回每一次切片的掉落物数量*/
    public abstract Item getSliceItem();

    /*切片逻辑的具体实现,切片时调用这个方法*/
    public void cut(Level level, BlockState state, BlockPos pos, Player entity, ItemStack hand, InteractionHand pUsedHand) {
        boolean b1 = getSliceProperty() != null;
        boolean b2 = false;
        if (b1) {
            int slice = state.getValue(getSliceProperty());
            if (slice > getMaxSlice()) {
                level.setBlock(pos, state.setValue(getSliceProperty(), slice - 1), 3);
            }else {
                b2 = true;
            }
        }
        if (!b1 || b2){
            level.removeBlock(pos,false);
        }
        ItemUtils.spawnItemEntity(level, new ItemStack(getSliceItem(), getSliceItemCount()),pos);
        if (!entity.getAbilities().instabuild){
                hand.hurtAndBreak(1,entity, (p_41300_) -> p_41300_.broadcastBreakEvent(pUsedHand));
        }
        level.playSound(null, pos, SoundEvents.WOOL_STEP, SoundSource.BLOCKS, 0.8F, 0.8F);
    }

    public void cut(Level level, BlockState state, BlockPos pos, LivingEntity entity, ItemStack hand, EquipmentSlot slot) {
        boolean b1 = getSliceProperty() != null;
        boolean b2 = false;
        if (b1) {
            int slice = state.getValue(getSliceProperty());
            if (slice > getMaxSlice()) {
                level.setBlock(pos, state.setValue(getSliceProperty(), slice - 1), 3);
            }else {
                b2 = true;
            }
        }
        if (!b1 || b2){
            level.removeBlock(pos,false);
        }
        ItemUtils.spawnItemEntity(level, new ItemStack(getSliceItem(), getSliceItemCount()),pos);
        hand.hurtAndBreak(1,entity, (p_41300_) -> p_41300_.broadcastBreakEvent(slot));
        level.playSound(null, pos, SoundEvents.WOOL_STEP, SoundSource.BLOCKS, 0.8F, 0.8F);
    }

    /*简单处理放置方向*/
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        return this.defaultBlockState().setValue(FACING, pContext.getHorizontalDirection());
    }
}
