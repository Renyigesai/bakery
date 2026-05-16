package com.renyigesai.bakeries.init.blocks;

import com.renyigesai.bakeries.block.entity.MachineBlockEntity;
import com.renyigesai.bakeries.capabilities.PlayerKeyAuxiliary;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.item.crafting.CampfireCookingRecipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

@SuppressWarnings("unused")
public final class StateBlocks {
    private StateBlocks() {
    }

    public static class ToasterBlock extends MachineBlocks.FacingMachineBlock {
        public static final EnumProperty<ToasterState> STATE = EnumProperty.create("state", ToasterState.class);
        private static final VoxelShape SHAPE = Block.box(0, 0, 4, 16, 12, 16);

        public ToasterBlock(BlockBehaviour.Properties properties) {
            super(properties);
            this.registerDefaultState(this.defaultBlockState().setValue(STATE, ToasterState.IDLE));
        }

        @Override
        protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
            super.createBlockStateDefinition(builder);
            builder.add(STATE);
        }

        @Override
        public @NotNull InteractionResult use(BlockState state, Level level, net.minecraft.core.BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
            if (level.isClientSide) {
                return InteractionResult.SUCCESS;
            }
            if (!(level.getBlockEntity(pos) instanceof MachineBlockEntity machine)) {
                return InteractionResult.PASS;
            }
            net.minecraft.world.item.ItemStack itemInHand = player.getItemInHand(hand);
            if (!itemInHand.isEmpty()) {
                CampfireCookingRecipe recipe = level.getRecipeManager()
                        .getRecipeFor(RecipeType.CAMPFIRE_COOKING, new SimpleContainer(itemInHand), level)
                        .orElse(null);
                if (recipe == null || !machine.addToasterItem(itemInHand, recipe.getCookingTime())) {
                    return InteractionResult.FAIL;
                }
                itemInHand.shrink(1);
                return InteractionResult.CONSUME;
            }
            if (state.getValue(STATE) == ToasterState.FINISH) {
                machine.popToasterItems(player);
                machine.changeToasterState(ToasterState.IDLE);
                return InteractionResult.CONSUME;
            }
            if (PlayerKeyAuxiliary.isKeyDown(player.getUUID())) {
                machine.popToasterItems(player);
                machine.changeToasterState(ToasterState.IDLE);
                return InteractionResult.CONSUME;
            }
            if (state.getValue(STATE) == ToasterState.IDLE && !machine.isToasterIdle()) {
                machine.changeToasterState(ToasterState.LIT);
                return InteractionResult.CONSUME;
            }
            return InteractionResult.SUCCESS;
        }

        @Override
        @SuppressWarnings("deprecation")
        public @NotNull VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
            return SHAPE;
        }

        @Override
        @SuppressWarnings("deprecation")
        public @NotNull VoxelShape getCollisionShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
            return SHAPE;
        }
    }

    public static class WoodTrayBlock extends FacingBlock {
        private static final VoxelShape SHAPE = Block.box(1, 0, 1, 15, 1, 15);

        public WoodTrayBlock(BlockBehaviour.Properties properties) {
            super(properties);
        }

        @Override
        @SuppressWarnings("deprecation")
        public @NotNull VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
            return SHAPE;
        }

        @Override
        @SuppressWarnings("deprecation")
        public @NotNull VoxelShape getCollisionShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
            return SHAPE;
        }
    }

    public static class FermentationTankBlock extends Block {
        public static final IntegerProperty FLOUR = IntegerProperty.create("flour", 0, 3);
        public static final BooleanProperty WATER = BooleanProperty.create("water");

        public FermentationTankBlock(BlockBehaviour.Properties properties) {
            super(properties);
            this.registerDefaultState(this.stateDefinition.any().setValue(FLOUR, 0).setValue(WATER, false));
        }

        @Override
        protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
            builder.add(FLOUR, WATER);
        }
    }

    public static class WoodCounterBlock extends FacingBlock {
        public static final BooleanProperty PLATE = BooleanProperty.create("plate");

        public WoodCounterBlock(BlockBehaviour.Properties properties) {
            super(properties);
            this.registerDefaultState(this.defaultBlockState().setValue(PLATE, false));
        }

        @Override
        protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
            super.createBlockStateDefinition(builder);
            builder.add(PLATE);
        }
    }

    public static class MilkTankBlock extends Block {
        public static final IntegerProperty MILK = IntegerProperty.create("milk", 1, 3);
        public static final BooleanProperty SALT = BooleanProperty.create("salt");

        public MilkTankBlock(BlockBehaviour.Properties properties) {
            super(properties);
            this.registerDefaultState(this.stateDefinition.any().setValue(MILK, 1).setValue(SALT, false));
        }

        @Override
        protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
            builder.add(MILK, SALT);
        }
    }

    public static class YeastTankBlock extends Block {
        public static final IntegerProperty YEAST = IntegerProperty.create("yeast", 1, 3);

        public YeastTankBlock(BlockBehaviour.Properties properties) {
            super(properties);
            this.registerDefaultState(this.stateDefinition.any().setValue(YEAST, 1));
        }

        @Override
        protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
            builder.add(YEAST);
        }
    }

    public static class RackBlock extends FacingBlock {
        public static final EnumProperty<RackType> TYPE = EnumProperty.create("type", RackType.class);

        public RackBlock(BlockBehaviour.Properties properties) {
            super(properties);
            this.registerDefaultState(this.defaultBlockState().setValue(TYPE, RackType.SINGLE));
        }

        @Override
        protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
            super.createBlockStateDefinition(builder);
            builder.add(TYPE);
        }
    }

    public static class BreadBasketBlock extends FacingBlock {
        public static final BooleanProperty FILL = BooleanProperty.create("fill");

        public BreadBasketBlock(BlockBehaviour.Properties properties) {
            super(properties);
            this.registerDefaultState(this.defaultBlockState().setValue(FILL, false));
        }

        @Override
        protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
            super.createBlockStateDefinition(builder);
            builder.add(FILL);
        }
    }

    public static class GlassCabinetDoorBlock extends FacingBlock {
        private static final VoxelShape SHAPE_CLOSED = Block.box(0, 0, 15, 16, 16, 16);
        private static final VoxelShape SHAPE_OPEN = Block.box(0, 0, 13, 16, 16, 16);

        public GlassCabinetDoorBlock(BlockBehaviour.Properties properties) {
            super(properties);
            this.registerDefaultState(this.defaultBlockState()
                    .setValue(BlockStateProperties.DOUBLE_BLOCK_HALF, DoubleBlockHalf.LOWER)
                    .setValue(BlockStateProperties.OPEN, false));
        }

        @Override
        public BlockState getStateForPlacement(BlockPlaceContext context) {
            BlockPos pos = context.getClickedPos();
            Level level = context.getLevel();
            if (pos.getY() >= level.getMaxBuildHeight() - 1 || !level.getBlockState(pos.above()).canBeReplaced(context)) {
                return null;
            }
            return Objects.requireNonNull(super.getStateForPlacement(context))
                    .setValue(BlockStateProperties.DOUBLE_BLOCK_HALF, DoubleBlockHalf.LOWER)
                    .setValue(BlockStateProperties.OPEN, false);
        }

        @Override
        public void setPlacedBy(Level level, BlockPos pos, BlockState state, net.minecraft.world.entity.LivingEntity placer, net.minecraft.world.item.ItemStack stack) {
            super.setPlacedBy(level, pos, state, placer, stack);
            level.setBlock(pos.above(),
                    state.setValue(BlockStateProperties.DOUBLE_BLOCK_HALF, DoubleBlockHalf.UPPER),
                    3);
        }

        @Override
        @SuppressWarnings("deprecation")
        public @NotNull InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
            if (level.isClientSide) {
                return InteractionResult.SUCCESS;
            }
            boolean open = !state.getValue(BlockStateProperties.OPEN);
            DoubleBlockHalf half = state.getValue(BlockStateProperties.DOUBLE_BLOCK_HALF);
            BlockPos lowerPos = half == DoubleBlockHalf.LOWER ? pos : pos.below();
            BlockPos upperPos = lowerPos.above();
            BlockState lower = level.getBlockState(lowerPos);
            BlockState upper = level.getBlockState(upperPos);
            if (lower.is(this)) {
                level.setBlock(lowerPos, lower.setValue(BlockStateProperties.OPEN, open), 10);
            }
            if (upper.is(this)) {
                level.setBlock(upperPos, upper.setValue(BlockStateProperties.OPEN, open), 10);
            }
            level.levelEvent(player, open ? 1005 : 1011, pos, 0);
            return InteractionResult.CONSUME;
        }

        @Override
        @SuppressWarnings("deprecation")
        public @NotNull VoxelShape getCollisionShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
            return state.getValue(BlockStateProperties.OPEN) ? SHAPE_OPEN : SHAPE_CLOSED;
        }

        @Override
        @SuppressWarnings("deprecation")
        public @NotNull BlockState updateShape(BlockState state, Direction facing, BlockState facingState, LevelAccessor level, BlockPos pos, BlockPos facingPos) {
            DoubleBlockHalf half = state.getValue(BlockStateProperties.DOUBLE_BLOCK_HALF);
            if (facing.getAxis() == Direction.Axis.Y) {
                if (half == DoubleBlockHalf.LOWER && facing == Direction.UP && !facingState.is(this)) {
                    return net.minecraft.world.level.block.Blocks.AIR.defaultBlockState();
                }
                if (half == DoubleBlockHalf.UPPER && facing == Direction.DOWN && !facingState.is(this)) {
                    return net.minecraft.world.level.block.Blocks.AIR.defaultBlockState();
                }
            }
            return super.updateShape(state, facing, facingState, level, pos, facingPos);
        }

        @Override
        protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
            super.createBlockStateDefinition(builder);
            builder.add(BlockStateProperties.DOUBLE_BLOCK_HALF, BlockStateProperties.OPEN);
        }
    }

    public static class CheeseTankBlock extends Block {
        public static final IntegerProperty CHEESE = IntegerProperty.create("cheese", 1, 3);

        public CheeseTankBlock(BlockBehaviour.Properties properties) {
            super(properties);
            this.registerDefaultState(this.stateDefinition.any().setValue(CHEESE, 1));
        }

        @Override
        protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
            builder.add(CHEESE);
        }
    }

    public static class DrinkCupBlock extends MachineBlocks.FacingMachineBlock {
        private static final VoxelShape SHAPE = Block.box(5, 0, 5, 11, 9, 11);

        public DrinkCupBlock(BlockBehaviour.Properties properties) {
            super(properties);
        }

        @Override
        @SuppressWarnings("deprecation")
        public @NotNull VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
            return SHAPE;
        }

        @Override
        @SuppressWarnings("deprecation")
        public @NotNull VoxelShape getCollisionShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
            return SHAPE;
        }
    }

    public static class MokaPotBlock extends FacingBlock {
        private static final VoxelShape SHAPE = Block.box(6.0, 0.0, 6.0, 10.0, 6.5, 10.0);

        public MokaPotBlock(BlockBehaviour.Properties properties) {
            super(properties);
        }

        @Override
        @SuppressWarnings("deprecation")
        public @NotNull VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
            return SHAPE;
        }

        @Override
        @SuppressWarnings("deprecation")
        public @NotNull VoxelShape getCollisionShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
            return SHAPE;
        }
    }

    public static class LitFacingBlock extends FacingBlock {
        public static final BooleanProperty LIT = BlockStateProperties.LIT;

        public LitFacingBlock(BlockBehaviour.Properties properties) {
            super(properties);
            this.registerDefaultState(this.defaultBlockState().setValue(LIT, false));
        }

        @Override
        protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
            super.createBlockStateDefinition(builder);
            builder.add(LIT);
        }
    }

    public static class ToastBlock extends FacingBlock {
        public static final IntegerProperty PILE = IntegerProperty.create("pile", 1, 2);
        public static final IntegerProperty SLICE = IntegerProperty.create("slice", 1, 4);
        private static final VoxelShape SHAPE_SINGLE = Block.box(1, 0, 1, 15, 3, 15);
        private static final VoxelShape SHAPE_PILE = Block.box(1, 0, 1, 15, 6, 15);

        public ToastBlock(BlockBehaviour.Properties properties) {
            super(properties);
            this.registerDefaultState(this.defaultBlockState().setValue(PILE, 1).setValue(SLICE, 1));
        }

        @Override
        protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
            super.createBlockStateDefinition(builder);
            builder.add(PILE, SLICE);
        }

        @Override
        @SuppressWarnings("deprecation")
        public @NotNull VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
            return state.getValue(PILE) >= 2 ? SHAPE_PILE : SHAPE_SINGLE;
        }

        @Override
        @SuppressWarnings("deprecation")
        public @NotNull VoxelShape getCollisionShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
            return state.getValue(PILE) >= 2 ? SHAPE_PILE : SHAPE_SINGLE;
        }
    }

    public static class MouldBlock extends FacingBlock {
        private static final VoxelShape SHAPE_NORTH = Block.box(1, 0, 1, 15, 3, 15);
        private static final VoxelShape SHAPE_SOUTH = Block.box(1, 0, 1, 15, 3, 15);
        private static final VoxelShape SHAPE_EAST = Block.box(1, 0, 1, 15, 3, 15);
        private static final VoxelShape SHAPE_WEST = Block.box(1, 0, 1, 15, 3, 15);

        public MouldBlock(BlockBehaviour.Properties properties) {
            super(properties.sound(SoundType.METAL));
        }

        @Override
        @SuppressWarnings("deprecation")
        public @NotNull VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
            return switch (state.getValue(FACING)) {
                case SOUTH -> SHAPE_SOUTH;
                case EAST -> SHAPE_EAST;
                case WEST -> SHAPE_WEST;
                default -> SHAPE_NORTH;
            };
        }

        @Override
        @SuppressWarnings("deprecation")
        public @NotNull VoxelShape getCollisionShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
            return switch (state.getValue(FACING)) {
                case SOUTH -> SHAPE_SOUTH;
                case EAST -> SHAPE_EAST;
                case WEST -> SHAPE_WEST;
                default -> SHAPE_NORTH;
            };
        }
    }

    public static class FacingPileBlock extends FacingBlock {
        public static final IntegerProperty PILE = IntegerProperty.create("pile", 1, 4);
        private final int maxPile;
        private static final VoxelShape PILE_1 = Block.box(1, 0, 1, 15, 4, 15);
        private static final VoxelShape PILE_2 = Block.box(1, 0, 1, 15, 6, 15);
        private static final VoxelShape PILE_3 = Block.box(1, 0, 1, 15, 8, 15);
        private static final VoxelShape PILE_4 = Block.box(1, 0, 1, 15, 10, 15);

        public FacingPileBlock(BlockBehaviour.Properties properties, int maxPile) {
            super(properties);
            this.maxPile = Math.max(1, Math.min(4, maxPile));
            this.registerDefaultState(this.defaultBlockState().setValue(PILE, 1));
        }

        @Override
        protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
            super.createBlockStateDefinition(builder);
            builder.add(PILE);
        }

        @Override
        public @NotNull BlockState getStateForPlacement(BlockPlaceContext context) {
            BlockState state = super.getStateForPlacement(context);
            return (state == null ? this.defaultBlockState() : state).setValue(PILE, 1);
        }

        @Override
        @SuppressWarnings("deprecation")
        public @NotNull VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
            return switch (state.getValue(PILE)) {
                case 2 -> PILE_2;
                case 3 -> PILE_3;
                case 4 -> PILE_4;
                default -> PILE_1;
            };
        }

        @Override
        @SuppressWarnings("deprecation")
        public @NotNull VoxelShape getCollisionShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
            return switch (state.getValue(PILE)) {
                case 2 -> PILE_2;
                case 3 -> PILE_3;
                case 4 -> PILE_4;
                default -> PILE_1;
            };
        }

        @Override
        @SuppressWarnings("deprecation")
        public @NotNull BlockState updateShape(BlockState state, Direction facing, BlockState facingState, net.minecraft.world.level.LevelAccessor level, net.minecraft.core.BlockPos currentPos, net.minecraft.core.BlockPos facingPos) {
            int pile = state.getValue(PILE);
            if (pile > maxPile) {
                return state.setValue(PILE, maxPile);
            }
            return super.updateShape(state, facing, facingState, level, currentPos, facingPos);
        }
    }

    public static class CropLikeBlock extends Block {
        public static final IntegerProperty AGE = BlockStateProperties.AGE_7;
        private final int maxAge;

        public CropLikeBlock(BlockBehaviour.Properties properties, int maxAge) {
            super(properties);
            this.maxAge = maxAge;
            this.registerDefaultState(this.stateDefinition.any().setValue(AGE, 0));
        }

        @Override
        public @NotNull BlockState getStateForPlacement(BlockPlaceContext context) {
            return this.defaultBlockState();
        }

        @Override
        protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
            builder.add(AGE);
        }

        @Override
        @SuppressWarnings("deprecation")
        public @NotNull BlockState updateShape(BlockState state, Direction facing, BlockState facingState, net.minecraft.world.level.LevelAccessor level, net.minecraft.core.BlockPos currentPos, net.minecraft.core.BlockPos facingPos) {
            int age = state.getValue(AGE);
            if (age > maxAge) {
                return state.setValue(AGE, maxAge);
            }
            return super.updateShape(state, facing, facingState, level, currentPos, facingPos);
        }
    }
}
