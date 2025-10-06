package net.weibai.bakeries.api.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class BreadBlock extends AbstractPileBlock {
    public static final IntegerProperty PILE = IntegerProperty.create("pile", 1, 4);
    public BreadBlock() {
        super(BlockBehaviour.Properties.of()
                .strength(0.5F,0.5F)
                .sound(SoundType.WOOL)
                .noOcclusion()
                .isRedstoneConductor((bs, br, bp) -> false));
        this.registerDefaultState(this.stateDefinition.any().setValue(PILE, 1));
    }
    public BreadBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any() .setValue(PILE, 1));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(PILE);
    }

    @Override
    public @Nullable BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection());
    }

    @Override
    public List<VoxelShape> createShapes(@NotNull BlockState state, @NotNull BlockGetter level, @NotNull BlockPos pos, @NotNull CollisionContext context) {
        List<VoxelShape> shapes = new ArrayList<>();
        shapes.add(Block.box(1.0D, 0.0D, 1.0D, 15.0D, 4.0D, 15.0D));
        return shapes;
    }
    @Override
    public int getMaxPile() {
        return 4;
    }
    @Override
    public @Nullable SoundEvent getTakeSound() {
        return SoundEvents.ITEM_FRAME_REMOVE_ITEM;
    }
    @Override
    public @Nullable SoundEvent getPlaceSound(){
        return SoundEvents.WOOL_STEP;
    }
    @Override
    public @NotNull InteractionResult put(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        return InteractionResult.PASS;
    }
    @Override
    public @NotNull InteractionResult take(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        int i = state.getValue(PILE);
        if (i == 1){
            level.removeBlock(pos,false);
        }else {
            level.setBlock(pos,state.setValue(PILE,i-1),3);
        }
        player.getInventory().placeItemBackInInventory(new ItemStack(this.asItem()));
        return InteractionResult.SUCCESS;
    }
}
