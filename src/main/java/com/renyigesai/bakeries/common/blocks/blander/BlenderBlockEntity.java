package com.renyigesai.bakeries.common.blocks.blander;

import com.renyigesai.bakeries.common.init.BakeriesBlocks;
import com.renyigesai.bakeries.common.inventory.blender.BlenderMenu;
import com.renyigesai.bakeries.common.recipe.BlenderRecipe;
import com.renyigesai.bakeries.common.utils.ItemUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.Containers;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.items.ItemStackHandler;
import net.neoforged.neoforge.items.wrapper.RecipeWrapper;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Optional;

public class BlenderBlockEntity extends BaseContainerBlockEntity {

    private static final int CONTAINER_SLOT = 9;
    private static final int OUTPUT_SLOT = 10;
    private static final int[] SLOTS_FOR_DOWN = new int[]{10};

    protected final ItemStackHandler inventory = new ItemStackHandler(11) {
        // FIX: Limit input slots (0-8) to 1 item to prevent external insertion issues,
        // while allowing container (9) and output (10) slots to hold up to 64.
        @Override
        public int getSlotLimit(int slot) {
            return (slot < 9) ? 1 : 64;
        }

        @Override
        protected int getStackLimit(int slot, @NotNull ItemStack stack) {
            return (slot < 9) ? 1 : 64;
        }

        // FIX: Sync data and trigger updates when pipes insert/extract items.
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
            if (level != null && !level.isClientSide) {
                level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), 3);
            }
        }
    };

    protected final ItemStackHandler filtrationinventory = new ItemStackHandler(10){
        @Override
        public int getSlotLimit(int slot) {
            return 1;
        }

        @Override
        public void setStackInSlot(int slot, @NotNull ItemStack stack) {
            super.setStackInSlot(slot, stack);
        }
    };//9个过滤槽位
//    private Lazy<IItemHandler> lazyItemHandler = null;
//    private final Map<Direction, LazyOptional<WrappedHandler>> directionWrappedHandlerMap =
//            Map.of(
//                    Direction.DOWN, LazyOptional.of(
//                            () -> new WrappedHandler(inventory, (i) -> getIntList(i,SLOTS_FOR_DOWN), (i, s) -> false)
//                    )
//            );

    public int cookingTotalTime;
    public int filtrationIndex;

    public State state = State.CLOSE;
    public float progress;
    public float progressOld;
    public float rProgress;
    public float rProgressOld;

    private final RecipeManager.CachedCheck<RecipeWrapper, BlenderRecipe> CHECK = RecipeManager.createCheck(BlenderRecipe.Type.INSTANCE);
    public BlenderBlockEntity(BlockPos pos, BlockState blockState) {
        super(BakeriesBlocks.Entities.BLENDER_ENTITY.get(), pos, blockState);
    }

    public ItemStackHandler getInventory() {
        return this.inventory;
    }

    public ItemStackHandler getFiltrationinventory() {
        return this.filtrationinventory;
    }

    @Override
    protected Component getDefaultName() {
        return Component.translatable("container.bakeries.blender");
    }

    @Override
    protected NonNullList<ItemStack> getItems() {
        NonNullList<ItemStack> nonNullList = NonNullList.withSize(inventory.getSlots(),ItemStack.EMPTY);
        for (int i = 0; i < inventory.getSlots(); i++) {
            if (!inventory.getStackInSlot(i).isEmpty()) {
                nonNullList.add(inventory.getStackInSlot(i));
            }
        }
        return nonNullList;
    }

    @Override
    protected void setItems(NonNullList<ItemStack> nonNullList) {
        for (int i = 0; i < nonNullList.size(); i++) {
            this.inventory.setStackInSlot(i,nonNullList.get(i));
        }
    }

    @Override
    protected AbstractContainerMenu createMenu(int i, Inventory inventory) {
        return new BlenderMenu(i, inventory, this);
    }

    @Override
    public int getContainerSize() {
        return this.inventory.getSlots();
    }

    @Override
    public boolean isEmpty() {
        for (int i = 0; i < inventory.getSlots(); i++) {
            if (!inventory.getStackInSlot(i).isEmpty()) {
                return false;
            }
        }
        return true;
    }

    public boolean getIntList(int i,int[] intList){
        for (int j = 0; j < intList.length; j++) {
            if (intList[j] == i){
                return true;
            }
        }
        return false;
    }

    @Override
    public ItemStack getItem(int slot) {
        return inventory.getStackInSlot(slot);
    }

    @Override
    public ItemStack removeItem(int slot, int amount) {
        ItemStack stack = inventory.getStackInSlot(slot);
        return stack.isEmpty() ? ItemStack.EMPTY : stack.split(amount);
    }

    @Override
    public ItemStack removeItemNoUpdate(int slot) {
        ItemStack stack = inventory.getStackInSlot(slot);
        if (stack.isEmpty()) {
            return ItemStack.EMPTY;
        }
        inventory.setStackInSlot(slot, ItemStack.EMPTY);
        return stack;
    }

    @Override
    public void setItem(int slot, ItemStack stack) {
        for (int i = 0; i < filtrationinventory.getSlots(); ++i) {
            ItemStack filtrationStack = filtrationinventory.getStackInSlot(i);
            if (stack.is(filtrationStack.getItem()) && inventory.getStackInSlot(i).isEmpty()) {
                ItemStack singleStack = stack.copy();
                stack.shrink(1);
                singleStack.setCount(1);
                inventory.setStackInSlot(i, singleStack);
                break;
            }
        }
        setChanged();
    }

    private boolean hasInput() {
        for(int i = 0; i < 9; ++i) {
            if (!this.inventory.getStackInSlot(i).isEmpty()) {
                return true;
            }
        }
        return false;
    }

    public void setFiltrationIndex(int filtrationIndex) {
        this.filtrationIndex = filtrationIndex;
    }

    public int getFiltrationIndex() {
        return filtrationIndex;
    }


    public float getProgress(float pPartialTicks) {
        return Mth.lerp(pPartialTicks, this.progressOld, this.progress);
    }

    public float getRprogress(float pPartialTicks) {
        return Mth.lerp(pPartialTicks, this.rProgressOld, this.rProgress);
    }

    @Override
    public boolean triggerEvent(int pId, int pType) {
        if (pId == 0) {
            if (pType == 0) {
                this.state = State.OPEN_PROCESS;
            }
            if (pType == 1) {
                this.state = State.CLOSE_PROCESS;
            }
            doNeighborUpdates(this.getLevel(), this.worldPosition, this.getBlockState());
            return true;
        } else {
            return super.triggerEvent(pId, pType);
        }
    }

    private static void doNeighborUpdates(Level pLevel, BlockPos pPos, BlockState pState) {
        pState.updateNeighbourShapes(pLevel, pPos, 3);
    }

    @Override
    public void clearContent() {
        for (int i = 0; i < inventory.getSlots(); i++) {
            inventory.setStackInSlot(i, ItemStack.EMPTY);
        }
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        if (tag.contains("Inventory")) {
            inventory.deserializeNBT(registries,tag.getCompound("Inventory"));
        }
        if (tag.contains("FiltrationInventory")) {
            filtrationinventory.deserializeNBT(registries,tag.getCompound("FiltrationInventory"));
        }
        cookingTotalTime = tag.getInt("CookingTotalTime");
        filtrationIndex = tag.getInt("FiltrationIndex");
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        tag.put("Inventory", inventory.serializeNBT(registries));
        tag.put("FiltrationInventory", filtrationinventory.serializeNBT(registries));
        tag.putInt("CookingTotalTime", cookingTotalTime);
        tag.putInt("FiltrationIndex", filtrationIndex);
    }

    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public @NotNull CompoundTag getUpdateTag(HolderLookup.@NotNull Provider registries) {
        CompoundTag tag = new CompoundTag();
        tag.put("Inventory",inventory.serializeNBT(registries));
        tag.put("FiltrationInventory", filtrationinventory.serializeNBT(registries));
        tag.putInt("CookingTotalTime", cookingTotalTime);
        tag.putInt("FiltrationIndex", filtrationIndex);
        return tag;
    }

    public void drops(BlenderBlockEntity blockEntity) {
        SimpleContainer inventory = new SimpleContainer(blockEntity.inventory.getSlots());
        for (int i = 0; i < blockEntity.inventory.getSlots(); i++) {
            inventory.setItem(i, blockEntity.inventory.getStackInSlot(i));
        }
        if (this.level != null) {
            Containers.dropContents(this.level, this.worldPosition, inventory);
        }
    }

    public boolean stillValid(Player player) {
        if (this.level == null || this.level.getBlockEntity(this.worldPosition) != this) {
            return false;
        }
        return player.distanceToSqr((double) this.worldPosition.getX() + 0.5D,
                (double) this.worldPosition.getY() + 0.5D,
                (double) this.worldPosition.getZ() + 0.5D) <= 64.0D;
    }

    public static void clientTick(Level level, BlockPos pos, BlockState state, BlenderBlockEntity blockEntity){
        blockEntity.progressOld = blockEntity.progress;
        blockEntity.rProgressOld = blockEntity.rProgress;
        if (!state.getValue(BlenderBlock.POWERED)) {
            switch (blockEntity.state) {
                case OPEN_PROCESS:
                    blockEntity.progress += 0.5F;
                    if (blockEntity.progress >= 1.0F) {
                        blockEntity.progress = 1.0F;
                        blockEntity.state = State.OPEN;
                    }
                    break;
                case OPEN:
                    blockEntity.progress = 1.0F;
                    break;
                case CLOSE_PROCESS:
                    blockEntity.progress -= 0.5F;
                    if (blockEntity.progress <= 0F) {
                        blockEntity.progress = 0F;
                        blockEntity.state = State.CLOSE;
                    }
                    break;
                case CLOSE:
                    blockEntity.progress = 0.0F;
                    break;
            }
        }else {
            blockEntity.rProgress += 0.1F;
            if (blockEntity.rProgress >= 1.0F){
                blockEntity.rProgress = 0.0F;
            }
            if (blockEntity.progress > 0F){
                blockEntity.progress -= 0.25F;
            }
            if (blockEntity.progress <= 0){
                blockEntity.progress = 0F;
            }
            blockEntity.state = State.CLOSE;
        }
    }


    public static void craftTick(Level level, BlockPos pos, BlockState state, BlenderBlockEntity blockEntity) {
        if (blockEntity.hasInput()) {
            blockEntity.craftItem();
            boolean temp = blockEntity.cookingTotalTime > 0;
            level.setBlock(pos, state.setValue(BlenderBlock.POWERED, temp), 3);
            setChanged(level, pos, state);
            if (!level.isClientSide) {
                level.sendBlockUpdated(pos, state, state, 3);
            }
        }
    }

    private void craftItem() {
        Optional<RecipeHolder<BlenderRecipe>> recipe = getMatchingRecipe(new RecipeWrapper(this.inventory));
        if (recipe.isPresent()) {

        }else {
            cookingTotalTime = 0;
            return;
        }
        BlenderRecipe blenderRecipe = recipe.get().value();

        ItemStack resultItem = blenderRecipe.getResultItem(null);
        ItemStack outputStack = inventory.getStackInSlot(OUTPUT_SLOT);

        if (!canCraft(resultItem, outputStack) || !isContainer(recipe.get())) {
            cookingTotalTime = 0;
            return;
        }

        if (cookingTotalTime < 100) {
            cookingTotalTime++;
            spawnParticle();
        }else {
            for (int i = 0; i < 9; i++) {
                ItemStack stack = inventory.getStackInSlot(i);
                if (!stack.is(Items.WATER_BUCKET)){
                    if (stack.hasCraftingRemainingItem()) {
                        ejectIngredientRemainder(stack.getCraftingRemainingItem());
                    }
                    stack.shrink(1);
                }
            }

            if (!blenderRecipe.getContainer().isEmpty()) {
                ItemStack stackInSlot = inventory.getStackInSlot(CONTAINER_SLOT);
                stackInSlot.shrink(1);
            }

            if (outputStack.isEmpty()) {
                inventory.setStackInSlot(OUTPUT_SLOT, resultItem);
            } else {
                outputStack.grow(resultItem.getCount());
            }

            cookingTotalTime = 0;
            setChanged();
            level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), 3);

        }
    }

    protected void ejectIngredientRemainder(ItemStack remainderStack) {
        BlockPos pos = this.getBlockPos();
        Level level = this.level;
        Direction facing = level.getBlockState(pos).getValue(HorizontalDirectionalBlock.FACING).getClockWise();
        double x = pos.getX() + 0.5D;
        double z = pos.getZ() + 0.5D;
        double newX = x + (facing.getStepX()*1.0D);
        double newZ = z + (facing.getStepZ()*1.0D);
        ItemUtils.spawnItemEntity(this.level,remainderStack, newX, pos.getY(), newZ,new Vec3(0.0D,0.0D,0.0D));
    }

    private boolean isContainer(RecipeHolder<BlenderRecipe> recipeHolder){
        BlenderRecipe recipe = recipeHolder.value();
        ItemStack container = recipe.getContainer();
        return container.is(this.inventory.getStackInSlot(CONTAINER_SLOT).getItem());
    }

    private boolean canCraft(ItemStack resultItem,ItemStack outputStack){
        if (outputStack.isEmpty()){
            return true;
        }
        if (resultItem.is(outputStack.getItem()) && outputStack.getCount() != outputStack.getMaxStackSize()){
            return true;
        }
        return false;
    }

    private Optional<RecipeHolder<BlenderRecipe>> getMatchingRecipe(RecipeWrapper inventoryWrapper) {
        if (level == null) return Optional.empty();
        return CHECK.getRecipeFor(inventoryWrapper, this.level);
    }


    private void spawnParticle(){
        BlockPos pos = this.getBlockPos();
        Level pLevel = this.level;
        if (pLevel instanceof ServerLevel serverLevel){
            ArrayList<ItemStack> stacks = new ArrayList<>();
            for (int i = 0; i < inventory.getSlots(); i++) {
                ItemStack stackInSlot = inventory.getStackInSlot(i);
                if (!stackInSlot.isEmpty()){
                    stacks.add(stackInSlot);
                }
            }
            for (int i = 0; i < stacks.size(); i++) {
                serverLevel.sendParticles(new ItemParticleOption(ParticleTypes.ITEM, stacks.get(i)),
                        pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, 1, 0.0, 0.0, 0.0, 0.075);
            }
        }
    }

    @Override
    public boolean canPlaceItem(int pIndex, ItemStack stack) {
        for (int i = 0; i < filtrationinventory.getSlots(); ++i) {
            ItemStack filtrationStack = filtrationinventory.getStackInSlot(i);
            if (stack.is(filtrationStack.getItem()) && inventory.getStackInSlot(i).isEmpty()) {
                return true;
            }
        }
        return false;
    }

    public enum State {
        OPEN_PROCESS,
        OPEN,
        CLOSE_PROCESS,
        CLOSE,
    }

    // FIX: Restrict vanilla hoppers from inserting full stacks into the blender.
    @Override
    public int getMaxStackSize() {
        return 1;
    }

}
