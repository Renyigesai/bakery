package com.renyigesai.bakeries.block.cake;

import com.renyigesai.bakeries.init.BakeriesBlocks;
import com.renyigesai.bakeries.init.BakeriesItems;
import com.renyigesai.bakeries.item.CakeRollItem;
import com.renyigesai.bakeries.util.ItemUtil;
import com.renyigesai.bakeries.util.TextUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class CakeProcessingInitialBlock extends BaseEntityBlock {
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
    public static final BooleanProperty ROLL = BooleanProperty.create("roll");
    public CakeProcessingInitialBlock() {
        super(BlockBehaviour.Properties.copy(Blocks.CAKE));
        this.registerDefaultState(this.stateDefinition.any().setValue(ROLL,false).setValue(FACING, Direction.NORTH));
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return box(3.0, 0.0, 3.0, 13.0, 3.0, 13.0);
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        ItemStack hand = pPlayer.getItemInHand(pHand);
        if (hand.isEmpty()){
            if (pState.getValue(ROLL)){
                BlockEntity blockEntity = pLevel.getBlockEntity(pPos);
                if (blockEntity instanceof CakeProcessingInitialBlockEntity cake){
                    summonCakeRollItem(cake,pLevel,pPos.getX() + 0.5,pPos.getY() + 0.5,pPos.getZ() + 0.5,new Vec3(0.0,0.0,0.0));
                }
                pLevel.removeBlock(pPos,false);
                return InteractionResult.SUCCESS;
            }
            pLevel.setBlock(pPos,pState.setValue(ROLL,true),3);
            return InteractionResult.SUCCESS;
        }
        List<Item> keys = getProcessingKey();
        boolean flag = false;
        for (int i = 0; i < keys.size(); i++) {
            if (hand.is(keys.get(i)) || ItemUtil.isTag(hand,keys.get(i))) {
                Map<Item, Block> cake = getCakeProcessing();
                pLevel.setBlock(pPos, cake.get(keys.get(0)).defaultBlockState(), 3);
                flag = true;
            }
        }
        if (flag) {
            if (!pPlayer.getAbilities().instabuild) {
                if (hand.hasCraftingRemainingItem()){
                    ItemUtil.givePlayerItem(pPlayer,hand.getCraftingRemainingItem());
                }
                hand.shrink(1);
            }
        }
        return InteractionResult.SUCCESS;
    }

    @Override
    public RenderShape getRenderShape(BlockState pState) {
        return RenderShape.MODEL;
    }


    private void summonCakeRollItem(CakeProcessingInitialBlockEntity blockEntity, Level level, double x, double y, double z, Vec3 vec3){
        ItemStack roll = new ItemStack(BakeriesItems.CAKE_ROLL.get(),2);
        if (blockEntity.noEmpty()){
            CompoundTag compoundTag = new CompoundTag();
            ItemStackHandler handler = new ItemStackHandler(4);
            for (int i = 0; i < blockEntity.inventory.getSlots(); i++) {
                handler.setStackInSlot(i,blockEntity.inventory.getStackInSlot(i));
            }
            compoundTag.put("Inventory",handler.serializeNBT());
            roll.setTag(compoundTag);
            CakeRollItem.setName(roll);
        }
        ItemUtil.spawnItemEntity(level,roll,x,y,z,vec3);
    }

    public static Map<Item, Block> getCakeProcessing(){
        Map<Item,Block> CAKE = new HashMap<>();
        CAKE.put(BakeriesItems.FOAMED_CREAM.get(), BakeriesBlocks.CREAM_CAKE_PROCESSING.get());
        return CAKE;
    }

    public List<Item> getProcessingKey(){
        Set<Item> tagKeys = getCakeProcessing().keySet();
        return new ArrayList<>(tagKeys.stream().toList());
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new CakeProcessingInitialBlockEntity(pPos,pState);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        return this.defaultBlockState().setValue(FACING, pContext.getHorizontalDirection());
    }

    public BlockState rotate(BlockState pState, Rotation pRot) {
        return pState.setValue(FACING, pRot.rotate(pState.getValue(FACING)));
    }

    public BlockState mirror(BlockState pState, Mirror pMirror) {
        return pState.rotate(pMirror.getRotation(pState.getValue(FACING)));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(FACING,ROLL);
    }

}
