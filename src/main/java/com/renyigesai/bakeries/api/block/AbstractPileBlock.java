package com.renyigesai.bakeries.api.block;

import com.renyigesai.bakeries.BakeriesMod;
import com.renyigesai.bakeries.common.utils.ItemUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public abstract class AbstractPileBlock extends Block {

    public static final DirectionProperty FACING;
    public static final VoxelShape BOX;
    public AbstractPileBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return BOX;
    }

    public abstract Property<Integer> getPileProperty();

    public abstract int getMaxPile();

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        if (level.isClientSide){
            return InteractionResult.SUCCESS;
        }
        if (!BakeriesMod.onAuxiliaryKey(player)){
            return take(state, level, pos, player);
        }
        return super.useWithoutItem(state, level, pos, player, hitResult);
    }

    public InteractionResult take(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer){
        int i =  pState.getValue(getPileProperty());
        if (i == 1){
            pLevel.removeBlock(pPos,false);
        }else {
            pLevel.setBlock(pPos,pState.setValue(getPileProperty(),i-1),3);
        }
        ItemUtils.givePlayerItem(pPlayer,new ItemStack(this.asItem()));
        pLevel.playSound(null,pPos,getTakeSound(), SoundSource.BLOCKS);
        return InteractionResult.SUCCESS;
    }

    protected BlockState rotate(BlockState state, Rotation rot) {
        return state.setValue(FACING, rot.rotate(state.getValue(FACING)));
    }

    protected BlockState mirror(BlockState state, Mirror mirror) {
        return state.rotate(mirror.getRotation(state.getValue(FACING)));
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        return this.defaultBlockState().setValue(FACING, pContext.getHorizontalDirection().getOpposite());
    }

    public abstract SoundEvent getPlaceSound();

    public SoundEvent getTakeSound(){
        return SoundEvents.ITEM_FRAME_REMOVE_ITEM;
    }

    static {
        FACING = BlockStateProperties.HORIZONTAL_FACING;
        BOX = Block.box(1.0D, 0.0D, 1.0D, 15.0D, 4.0D, 15.0D);
    }
}
