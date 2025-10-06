package net.weibai.bakeries.api.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Objects;

public abstract class AbstractPileBlock extends Block {
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;

    protected AbstractPileBlock(Properties properties) {
        super(properties);
    }

    /**
     * 创建方块的形状
     */
    public abstract List<VoxelShape> createShapes(@NotNull BlockState state, @NotNull BlockGetter level, @NotNull BlockPos pos, @NotNull CollisionContext context);
    /**
     * 获取方块的音效
     */
    @Nullable
    public abstract SoundEvent getTakeSound();
    @Nullable
    public abstract SoundEvent getPlaceSound();
    /**
     * 放置方块
     */
    public abstract @NotNull InteractionResult put(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult);
    /**
     * 拿取方块
     */
    public abstract @NotNull InteractionResult take(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult);
    public abstract int getMaxPile();
    @Override
    protected @NotNull InteractionResult useWithoutItem(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, Player player, @NotNull BlockHitResult hitResult) {
        if (!player.isShiftKeyDown()){
            InteractionResult take = take(state, level, pos, player, hitResult);
            if (take.consumesAction() && getTakeSound() != null){
                level.playSound(null,pos,getTakeSound(), SoundSource.BLOCKS);
            }
            return take;
        }else {
            InteractionResult put = put(state, level, pos, player, hitResult);
            if (put.consumesAction() && getPlaceSound() != null){
                level.playSound(null,pos,getPlaceSound(), SoundSource.BLOCKS);
            }
            return put;
        }
    }

    protected BlockState rotate(BlockState state, Rotation rot) {
        return state.setValue(FACING, rot.rotate(state.getValue(FACING)));
    }

    protected BlockState mirror(BlockState state, Mirror mirror) {
        return state.rotate(mirror.getRotation(state.getValue(FACING)));
    }
    @Override
    protected boolean hasAnalogOutputSignal(@NotNull BlockState state) {
        return true;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    @Override
    protected @NotNull VoxelShape getShape(@NotNull BlockState state, @NotNull BlockGetter level, @NotNull BlockPos pos, @NotNull CollisionContext context) {
        return createShapes(state,level,pos,context).stream().reduce(Shapes.empty(), Shapes::or);
    }

    @Override
    public void appendHoverText(@NotNull ItemStack stack, Item.@NotNull TooltipContext context, @NotNull List<Component> tooltipComponents, @NotNull TooltipFlag tooltipFlag) {
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);

        PotionContents potioncontents = stack.get(DataComponents.POTION_CONTENTS);
        if (potioncontents != null) {
            Objects.requireNonNull(tooltipComponents);
            potioncontents.addPotionTooltip(tooltipComponents::add, 1.0F, context.tickRate());
        }
    }
    public static VoxelShape box(double x1, double y1, double z1, double x2, double y2, double z2) {
        return Block.box(x1, y1, z1, x1+x2, y1+y2, z1+z2);
    }
    protected VoxelShape rotateBox(double x, double y, double z, double width, double height, double depth, Direction facing) {
        switch (facing) {
            case NORTH:
                return box(x, y, z, width, height, depth);
            case EAST:
                return box(16 - z - depth, y, x, depth, height, width);
            case SOUTH:
                return box(16 - x - width, y, 16 - z - depth, width, height, depth);
            case WEST:
                return box(z, y, 16 - x - width, depth, height, width);
            default:
                return box(x, y, z, width, height, depth);
        }
    }
}
