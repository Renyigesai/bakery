package com.renyigesai.bakeries.block.cake;

import com.renyigesai.bakeries.util.ItemUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class CakeProcessingBlock extends Block {
    public Supplier<Item> mItem_0 = ItemStack.EMPTY.getItemHolder();
    public Supplier<Item> mItem_1 = ItemStack.EMPTY.getItemHolder();
    public Supplier<Item> mItem_2 = ItemStack.EMPTY.getItemHolder();
    public Supplier<Item> mItem_3 = ItemStack.EMPTY.getItemHolder();
    public final Supplier<Block> rBlock;
    public static final IntegerProperty STAGE = IntegerProperty.create("stage",0,3);
    public final int maxStage;

    public CakeProcessingBlock(Supplier<Item> mItem_0, Supplier<Item> mItem_1, Supplier<Item> mItem_2, Supplier<Item> mItem_3,Supplier<Block> rBlock, int maxStage) {
        super(BlockBehaviour.Properties.copy(Blocks.CAKE));
        this.registerDefaultState(this.stateDefinition.any().setValue(STAGE,0));
        this.mItem_0 = mItem_0;
        this.mItem_1 = mItem_1;
        this.mItem_2 = mItem_2;
        this.mItem_3 = mItem_3;
        this.rBlock = rBlock;
        this.maxStage = maxStage;
    }

    public CakeProcessingBlock(Supplier<Item> mItem_0, Supplier<Item> mItem_1, Supplier<Item> mItem_2, Supplier<Item> mItem_3, Supplier<Block> rBlock) {
        super(BlockBehaviour.Properties.copy(Blocks.CAKE));
        this.registerDefaultState(this.stateDefinition.any().setValue(STAGE,0));
        this.mItem_0 = mItem_0;
        this.mItem_1 = mItem_1;
        this.mItem_2 = mItem_2;
        this.mItem_3 = mItem_3;
        this.rBlock = rBlock;
        this.maxStage = 3;
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        int value = pState.getValue(STAGE);
        double h = value ==0?2:2*(value +1);
        return box(3.0, 0.0, 3.0, 13.0, h, 13.0);
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        if (pLevel.isClientSide){
            if (processing(pState, pLevel, pPos, pPlayer, pHand, pHit).consumesAction()){
                return InteractionResult.SUCCESS;
            }
        }
        return processing(pState, pLevel, pPos, pPlayer, pHand, pHit);
    }

    public InteractionResult processing(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit){
        int stage = pState.getValue(STAGE);
        ItemStack hand = pPlayer.getItemInHand(pHand);
        List<ItemStack> itemList = getProcessingItemList();
        if (hand.is(itemList.get(stage).getItem()) || ItemUtil.isTag(hand,itemList.get(stage).getItem())){
            if (stage < maxStage){
                pLevel.setBlock(pPos, pState.setValue(STAGE, stage + 1), 3);
            }else {
                pLevel.removeBlock(pPos,false);
                pLevel.setBlock(pPos,rBlock.get().defaultBlockState(), 3);
            }
            if (!pPlayer.getAbilities().instabuild) {
                if (hand.hasCraftingRemainingItem()){
                    ItemUtil.givePlayerItem(pPlayer,hand.getCraftingRemainingItem());
                }
                hand.shrink(1);
            }
            return InteractionResult.SUCCESS;
        }else {
            return InteractionResult.PASS;
        }
    }

    private List<ItemStack> getProcessingItemList(){
        List<ItemStack> stacks = new ArrayList<>();
        stacks.add(mItem_0.get().getDefaultInstance());
        stacks.add(mItem_1.get().getDefaultInstance());
        stacks.add(mItem_2.get().getDefaultInstance());
        stacks.add(mItem_3.get().getDefaultInstance());
        return stacks;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(STAGE);
    }
}

