package com.renyigesai.bakeries.block.oven;

import com.renyigesai.bakeries.api.block.BakeriesWorkBlock;
import com.renyigesai.bakeries.block.blender.BlenderBlock;
import com.renyigesai.bakeries.block.blender.BlenderBlockEntity;
import com.renyigesai.bakeries.init.BakeriesBlocks;
import com.renyigesai.bakeries.init.BakeriesSounds;
import com.renyigesai.bakeries.inventory.oven.OvenMenu;
import com.renyigesai.bakeries.recipe.oven.OvenRecipe;
import io.netty.buffer.Unpooled;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.Container;
import net.minecraft.world.Containers;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class OvenBlockEntity extends BaseContainerBlockEntity implements WorldlyContainer, BakeriesWorkBlock {
    private final ItemStackHandler itemHandler = new ItemStackHandler(6){
        @Override
        public int getSlotLimit(int slot) {
            return 1;
        }
    };
    public final Component name = Component.translatable("container.oven");
    private LazyOptional<IItemHandler> lazyItemHandlers = LazyOptional.empty();
    public final int[] cooking_times = new int[6];
    public final int[] max_cooking_times = new int[6];
    private final int[] min_temperatures = new int[6];
    private final int[] max_temperatures = new int[6];
    public int temperature;
    public int loadVersion = 23;

    public final ContainerData dataAccess = new ContainerData() {
        @Override
        public int get(int pIndex) {
            return switch (pIndex) {
                case 0 -> OvenBlockEntity.this.temperature;
                case 1 -> calculateProgress(0);
                case 2 -> calculateProgress(1);
                case 3 -> calculateProgress(2);
                case 4 -> calculateProgress(3);
                case 5 -> calculateProgress(4);
                case 6 -> calculateProgress(5);
                default -> 0;
            };
        }

        private int calculateProgress(int index) {
            if (OvenBlockEntity.this.max_cooking_times[index] == 0) {
                return 0;
            }
            return Math.min((int) ((OvenBlockEntity.this.cooking_times[index] / (double) OvenBlockEntity.this.max_cooking_times[index]) * 14), 14);
        }

        @Override
        public void set(int pIndex, int pValue) {
            if (pIndex == 0) {
                OvenBlockEntity.this.temperature = pValue;
            }
        }

        @Override
        public int getCount() {
            return 7;
        }
    };

    public State state = State.CLOSE;
    public float progress;
    public float progressOld;

    public OvenBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(BakeriesBlocks.OVEN_BLOCK_ENTITY.get(), pPos, pBlockState);
    }
    public void drops() {
        SimpleContainer inventory = new SimpleContainer(itemHandler.getSlots());
        for (int i = 0; i < itemHandler.getSlots(); i++) {
            inventory.setItem(i, itemHandler.getStackInSlot(i));
        }
        if (this.level != null) {
            Containers.dropContents(this.level, this.worldPosition, inventory);
        }
    }
    @Override
    protected @NotNull Component getDefaultName() {
        return name;
    }
    @Override
    protected @NotNull AbstractContainerMenu createMenu(int pContainerId, @NotNull Inventory pInventory) {
        return new OvenMenu(pContainerId, pInventory,  new FriendlyByteBuf(Unpooled.buffer()).writeBlockPos(this.worldPosition),this, this.dataAccess);
    }

    @Override
    protected void saveAdditional(CompoundTag pTag) {
        pTag.put("Inventory", itemHandler.serializeNBT());
        pTag.putIntArray("cooking_times", this.cooking_times);
        pTag.putIntArray("max_cooking_times", this.max_cooking_times);
        pTag.putIntArray("min_temperatures", this.min_temperatures);
        pTag.putIntArray("max_temperatures", this.max_temperatures);
        pTag.putInt("temperature", this.temperature);
        pTag.putInt("LoadVersion", 23);
        super.saveAdditional(pTag);
    }

    @Override
    public void load(@NotNull CompoundTag pTag) {
        super.load(pTag);
        if (isMigration(pTag)){
            ItemStackHandler newInventory = new ItemStackHandler(6);
            ItemStackHandler oldInventory = new ItemStackHandler(4);
            if (pTag.contains("inventory")) {
                oldInventory.deserializeNBT(pTag.getCompound("inventory"));
                itemHandler.deserializeNBT(newInventory.serializeNBT());
            }
            for (int i = 0; i < oldInventory.getSlots(); i++) {
                if (i <= 4){
                    itemHandler.setStackInSlot(i,oldInventory.getStackInSlot(i));
                }else {
                    itemHandler.setStackInSlot(i,ItemStack.EMPTY);
                }
            }
        }else {
            if (pTag.contains("Inventory")) {
                itemHandler.deserializeNBT(pTag.getCompound("Inventory"));
            }
        }
        if (pTag.contains("CookingTimes", 11)) {
            int[] aint = pTag.getIntArray("CookingTimes");
            System.arraycopy(aint, 0, this.cooking_times, 0, Math.min(this.max_cooking_times.length, aint.length));
        }
        if (pTag.contains("MinTemperatures", 11)) {
            int[] aint = pTag.getIntArray("MinTemperatures");
            System.arraycopy(aint, 0, this.min_temperatures, 0, Math.min(this.min_temperatures.length, aint.length));
        }
        if (pTag.contains("MaxTemperatures", 11)) {
            int[] aint = pTag.getIntArray("MaxTemperatures");
            System.arraycopy(aint, 0, this.max_temperatures, 0, Math.min(this.max_temperatures.length, aint.length));
        }
        this.temperature = pTag.getInt("temperature");
        loadVersion = pTag.getInt("LoadVersion");
    }

    @Override
    public CompoundTag getUpdateTag() {
        CompoundTag pTag = new CompoundTag();
        pTag.put("Inventory", itemHandler.serializeNBT());
        pTag.putIntArray("cooking_times", this.cooking_times);
        pTag.putIntArray("max_cooking_times", this.max_cooking_times);
        pTag.putIntArray("min_temperatures", this.min_temperatures);
        pTag.putIntArray("max_temperatures", this.max_temperatures);
        pTag.putInt("temperature", this.temperature);
        pTag.putInt("LoadVersion", 23);
        return pTag;
    }

    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    private boolean isMigration(CompoundTag tag){
        if (!tag.contains("LoadVersion")){
            return true;
        }
        if (tag.getInt("LoadVersion") == loadVersion){
            return false;
        }
        return tag.getInt("LoadVersion") < loadVersion;
    }

    @Override
    public <T> @NotNull LazyOptional<T> getCapability(@NotNull Capability<T> capability, @Nullable Direction facing) {
        if (capability == ForgeCapabilities.ITEM_HANDLER)
            return lazyItemHandlers.cast();
        return super.getCapability(capability, facing);
    }

    @Override
    public void onLoad() {
        super.onLoad();
        lazyItemHandlers = LazyOptional.of(() -> itemHandler);
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        lazyItemHandlers.invalidate();
    }

    public float getProgress(float pPartialTicks) {
        return Mth.lerp(pPartialTicks, this.progressOld, this.progress);
    }

    @Override
    public boolean triggerEvent(int pId, int pType) {
        if (pId == 0) {
            if (pType == 0) {
                this.state = OvenBlockEntity.State.OPEN_PROCESS;
            }
            if (pType == 1) {
                this.state = OvenBlockEntity.State.CLOSE_PROCESS;
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

    public static void clientTick(Level level, BlockPos pos, BlockState state, OvenBlockEntity blockEntity){
        blockEntity.progressOld = blockEntity.progress;
        if (!state.getValue(OvenBlock.LIT)) {
            switch (blockEntity.state) {
                case OPEN_PROCESS:
                    blockEntity.progress += 0.25F;
                    if (blockEntity.progress >= 1.0F) {
                        blockEntity.progress = 1.0F;
                        blockEntity.state = OvenBlockEntity.State.OPEN;
                    }
                    break;
                case OPEN:
                    blockEntity.progress = 1.0F;
                    break;
                case CLOSE_PROCESS:
                    blockEntity.progress -= 0.25F;
                    if (blockEntity.progress <= 0F) {
                        blockEntity.progress = 0F;
                        blockEntity.state = OvenBlockEntity.State.CLOSE;
                    }
                    break;
                case CLOSE:
                    blockEntity.progress = 0.0F;
                    break;
            }
        }else {
            if (blockEntity.progress > 0F){
                blockEntity.progress -= 0.25F;
            }
            if (blockEntity.progress <= 0){
                blockEntity.progress = 0F;
            }
            blockEntity.state = OvenBlockEntity.State.CLOSE;
        }
    }
    private boolean hasInput() {
        for(int i = 0; i < itemHandler.getSlots(); ++i) {
            if (!this.itemHandler.getStackInSlot(i).isEmpty()) {
                return true;
            }
        }
        return false;
    }

    public static void serverTick(Level pLevel, BlockPos pPos, BlockState pState, OvenBlockEntity pOvenBlockEntity) {
        if (pOvenBlockEntity.hasInput()){
            for (int i = 0; i < pOvenBlockEntity.itemHandler.getSlots(); i++) {
                if (!pOvenBlockEntity.itemHandler.getStackInSlot(i).isEmpty()){
                    recipeItem(pLevel, pPos, pState, i, pOvenBlockEntity);
                }
            }
            setFire(pLevel, pPos, pOvenBlockEntity);
            setChanged(pLevel, pPos, pState);
            updateBlock(pOvenBlockEntity);
        }
    }

    public ItemStackHandler getItemHandler() {
        return this.itemHandler;
    }

    public static void setFire(Level world, BlockPos pos, OvenBlockEntity pOvenBlockEntity) {
        boolean isLit = pOvenBlockEntity.cooking_times[0] > 0 || pOvenBlockEntity.cooking_times[1] > 0 || pOvenBlockEntity.cooking_times[2] > 0 || pOvenBlockEntity.cooking_times[3] > 0 || pOvenBlockEntity.cooking_times[4] > 0 || pOvenBlockEntity.cooking_times[5] > 0;
        world.setBlock(pos, pOvenBlockEntity.getBlockState().setValue(OvenBlock.LIT, isLit), 3);
    }
    public int getTemperature(OvenBlockEntity ovenBlockEntity) {
        return ovenBlockEntity.temperature;
    }

    public void setTemperature(OvenBlockEntity ovenBlockEntity, int temperature) {
        updateBlock(ovenBlockEntity);
        ovenBlockEntity.temperature = Math.min(Math.max(temperature, 0), 500);
    }

    public void addTemperature(OvenBlockEntity ovenBlockEntity, int temperature) {
        updateBlock(ovenBlockEntity);
        ovenBlockEntity.temperature = Math.min(Math.max(this.getTemperature(ovenBlockEntity) + temperature, 0), 500);
    }

    public void subTemperature(OvenBlockEntity ovenBlockEntity, int temperature) {
        updateBlock(ovenBlockEntity);
        ovenBlockEntity.temperature = Math.min(Math.max(this.getTemperature(ovenBlockEntity) - temperature, 0), 500);
    }

    public static void updateBlock(OvenBlockEntity ovenBlockEntity) {
        Level world = ovenBlockEntity.getLevel();
        BlockPos pos = ovenBlockEntity.getBlockPos();
        BlockState state = world.getBlockState(pos);
        setChanged(world, pos, state);
        world.sendBlockUpdated(pos, state, state, 3);
    }

    private static void recipeItem(Level world, BlockPos pos, BlockState state, int slot, OvenBlockEntity ovenBlockEntity) {
        Optional<OvenRecipe> recipe = ovenBlockEntity.getCurrentRecipe(slot);
        int temperature = ovenBlockEntity.temperature;
        recipe.ifPresent(ovenRecipe -> {
            ovenBlockEntity.max_cooking_times[slot] = ovenRecipe.getTime();
            ovenBlockEntity.min_temperatures[slot] = Math.max(recipe.get().getMinTemperature(), 0);
            ovenBlockEntity.max_temperatures[slot] = Math.min(recipe.get().getMaxTemperature(), 500);
        });
        if (ovenBlockEntity.hasRecipe(slot) && recipe.isPresent() && Math.max(ovenBlockEntity.min_temperatures[slot], 0) <= temperature) {


            if (!world.isClientSide()) {
                int cookingTime = ovenBlockEntity.cooking_times[slot]++;
                int max_cooking_time = ovenBlockEntity.max_cooking_times[slot];

                int craft_temperature = Math.min(ovenBlockEntity.max_temperatures[slot], 500);

                if (cookingTime >= max_cooking_time) {
                    if (temperature <= craft_temperature) {
                        boolean perfect = true;
                        if (!recipe.get().isPresentPerfect() || temperature != recipe.get().getPerfectTemperature()){
                            perfect = false;
                        }
                        ovenBlockEntity.craftItem(ovenBlockEntity, slot, perfect);
                    } else {
                        ovenBlockEntity.itemHandler.setStackInSlot(slot, new ItemStack(Items.CHARCOAL, 1));
                    }
                    world.sendBlockUpdated(pos, state, state, 3);
                    resetProgress(ovenBlockEntity, slot);
                    boolean playSound = true;
                    for (int i = 0; i < ovenBlockEntity.cooking_times.length; i++) {
                        if (ovenBlockEntity.cooking_times[i] != 0) {
                            playSound = false;
                            break;
                        }
                    }
                    if (playSound){
                        world.playSound(null, pos, SoundEvents.NOTE_BLOCK_BELL.get(), SoundSource.BLOCKS, 1.0F, 1.0F);
                    }
                }
            }
        } else {
            if (!world.isClientSide()) {
                world.sendBlockUpdated(pos, state, state, 3);
                resetProgress(ovenBlockEntity, slot);
            }
        }
    }

    private static void resetProgress(OvenBlockEntity ovenBlockEntity, int slot) {
        ovenBlockEntity.max_cooking_times[slot] = 0;
        ovenBlockEntity.cooking_times[slot] = 0;
        ovenBlockEntity.min_temperatures[slot] = 0;
        ovenBlockEntity.max_temperatures[slot] = 0;
    }
    private void craftItem(OvenBlockEntity ovenBlockEntity, int slot, boolean perfect) {
        updateBlock(ovenBlockEntity);
        Optional<OvenRecipe> recipe = getCurrentRecipe(slot);
        if (recipe.isPresent()) {
            ItemStack result = recipe.get().getResultItem(null);
            ItemStack takeItem = new ItemStack(result.getItem(), result.getCount());
            if (perfect){
                takeItem.getOrCreateTag().putBoolean("perfect", true);
            }
            this.itemHandler.setStackInSlot(slot, takeItem);
            updateBlock(ovenBlockEntity);
        }
    }

    private boolean hasRecipe(int slot) {
        Optional<OvenRecipe> recipe = getCurrentRecipe(slot);
        return recipe.isPresent() && recipe.get().getIngredients().get(0).test(itemHandler.getStackInSlot(slot));
    }

    public Optional<OvenRecipe> getCurrentRecipe(int slot) {
        return this.level.getRecipeManager().getRecipeFor(OvenRecipe.Type.INSTANCE, new SimpleContainer(this.itemHandler.getStackInSlot(slot)), level);
    }



    @Override
    public int getContainerSize() {
        return 6;
    }

    @Override
    public boolean isEmpty() {
        for(int i = 0; i < this.itemHandler.getSlots(); i++) {
            if (!this.itemHandler.getStackInSlot(i).isEmpty()) {
                return false;
            }
        }
        return true;
    }

    @Override
    public @NotNull ItemStack getItem(int pSlot) {
        return this.itemHandler.getStackInSlot(pSlot);
    }

    @Override
    public @NotNull ItemStack removeItem(int pSlot, int pAmount) {
        return removeItem(this.itemHandler, pSlot, pAmount);
    }
    public static ItemStack removeItem(ItemStackHandler itemHandler, int pIndex, int pAmount) {
        return pIndex >= 0 && pIndex < itemHandler.getSlots() && !itemHandler.getStackInSlot(pIndex).isEmpty() && pAmount > 0 ? itemHandler.getStackInSlot(pIndex).split(pAmount) : ItemStack.EMPTY;
    }
    @Override
    public @NotNull ItemStack removeItemNoUpdate(int pSlot) {
        return takeItem(this.itemHandler, pSlot);
    }
    public static ItemStack takeItem(ItemStackHandler itemHandler, int pSlot) {
        return pSlot >= 0 && pSlot < itemHandler.getSlots() ? itemHandler.insertItem(pSlot, ItemStack.EMPTY, false) : ItemStack.EMPTY;
    }
    @Override
    public void setItem(int pSlot, ItemStack pStack) {
        ItemStack itemstack = this.itemHandler.getStackInSlot(pSlot);
        boolean flag = !pStack.isEmpty() && ItemStack.isSameItemSameTags(itemstack, pStack);
        this.itemHandler.insertItem(pSlot,pStack, false);
        if (pStack.getCount() > this.getMaxStackSize()) {
            pStack.setCount(this.getMaxStackSize());
        }
        if (pSlot == 0 && !flag) {
            this.setChanged();
        }
    }
    @Override
    public boolean stillValid(@NotNull Player pPlayer) {
        return Container.stillValidBlockEntity(this, pPlayer);
    }

    @Override
    public void clearContent() {
        for (int i = 0; i < this.itemHandler.getSlots(); i++) {
            this.itemHandler.setStackInSlot(i, ItemStack.EMPTY);
        }
    }


    @Override
    public int @NotNull [] getSlotsForFace(@NotNull Direction pSide) {return new int[]{0,1,2,3,4,5};}

    @Override
    public boolean canPlaceItemThroughFace(int pIndex, @NotNull ItemStack pItemStack, @Nullable Direction pDirection) {
        return this.canPlaceItem(pIndex, pItemStack);
    }

    @Override
    public boolean canTakeItemThroughFace(int pIndex, ItemStack pStack, Direction pDirection) {
        return false;
    }

    public boolean canPlaceItem(int pIndex, @NotNull ItemStack pStack) {
      return /*pStack.is(BakeriesItemTag.RAE_FOOD) &&*/ itemHandler.getStackInSlot(pIndex).isEmpty();
    }

    @Override
    public SoundEvent getOpenSound() {
        return BakeriesSounds.OVEN_OPEN.get();
    }

    @Override
    public SoundEvent getCloseSound() {
        return SoundEvents.IRON_TRAPDOOR_CLOSE;
    }

    public enum State {
        OPEN_PROCESS,
        OPEN,
        CLOSE_PROCESS,
        CLOSE,
    }
}
