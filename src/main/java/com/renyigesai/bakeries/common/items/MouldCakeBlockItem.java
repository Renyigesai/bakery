package com.renyigesai.bakeries.common.items;

import com.renyigesai.bakeries.common.blocks.mould_cake.MouldCakeBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;

import java.util.function.Supplier;

public class MouldCakeBlockItem extends BlockItem {

    public final Supplier<Item> mouldContent;

    public MouldCakeBlockItem(Block block, Properties pProperties, Supplier<Item> mouldContent) {
        super(block, pProperties);
        this.mouldContent = mouldContent;
    }

    @Override
    public InteractionResult place(BlockPlaceContext context) {
        super.place(context);
        BlockPos clickedPos = context.getClickedPos();
        Level level = context.getPlayer().level();
        BlockEntity blockEntity = level.getBlockEntity(clickedPos);
        if (blockEntity instanceof MouldCakeBlockEntity mcb){
            mcb.getItems().setStackInSlot(0,new ItemStack(mouldContent.get()));
            mcb.update();
        }
        return InteractionResult.SUCCESS;
    }
}
