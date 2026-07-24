package com.renyigesai.bakeries.block.fermentation_box;

import com.renyigesai.bakeries.BakeriesMod;
import com.renyigesai.bakeries.api.block.WrappedHandler;
import com.renyigesai.bakeries.api.item.IFermentationItem;
import com.renyigesai.bakeries.block.blender.BlenderBlockEntity;
import com.renyigesai.bakeries.init.BakeriesBlocks;
import com.renyigesai.bakeries.init.BakeriesRecipeTypes;
import com.renyigesai.bakeries.inventory.fermentation_box.FermentationBoxMenu;
import com.renyigesai.bakeries.recipe.FermentationBoxRecipe;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.util.Mth;
import net.minecraft.world.Containers;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.Optional;

public class FermentationBoxBlockEntity extends BaseContainerBlockEntity {

    private final ItemStackHandler items = new ItemStackHandler(6){
        @Override
        protected int getStackLimit(int slot, @NotNull ItemStack stack) {
            return 1;
        }
    };

    private static final int[] SLOTS_FOR_DOWN = new int[]{0,1,2,3,4,5};
    private LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();
    private final Map<Direction, LazyOptional<WrappedHandler>> directionWrappedHandlerMap =
            Map.of(
                    Direction.DOWN, LazyOptional.of(() -> new WrappedHandler(
                            items,
                            (i) -> getIntList(i, SLOTS_FOR_DOWN),
                            (i, s) -> false
                    )),
                    Direction.UP, LazyOptional.of(() -> new WrappedHandler(
                            items,
                            (i) -> false,
                            (i, s) -> true
                    ))
            );

    private final int[] fermentationTime;
    private int fermentationMaxTime;
    private int temperature;
    private int perfectTime;
    public float progress;
    public float progressOld;
    public FermentationBoxBlockEntity.State state = FermentationBoxBlockEntity.State.CLOSE;

    public FermentationBoxBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(BakeriesBlocks.FERMENTATION_BOX_ENTITY.get(), pPos, pBlockState);
        this.fermentationTime = new int[6];
    }

    @Override
    public int getContainerSize() {
        return 6;
    }

    public boolean isEmpty(){
        for (int i = 0; i < this.items.getSlots(); i++) {
            if (!this.items.getStackInSlot(i).isEmpty()){
                return false;
            }
        }
        return true;
    }

    public ItemStackHandler getItems() {
        return items;
    }

    public ItemStack getItem(int slot){
        return items.getStackInSlot(slot);
    }

    @Override
    public ItemStack removeItem(int slot, int amount) {
        ItemStack stack = items.getStackInSlot(slot);
        return stack.isEmpty() ? ItemStack.EMPTY : stack.split(amount);
    }

    @Override
    public ItemStack removeItemNoUpdate(int slot) {
        ItemStack stack = items.getStackInSlot(slot);
        if (stack.isEmpty()) {
            return ItemStack.EMPTY;
        }
        items.setStackInSlot(slot, ItemStack.EMPTY);
        return stack;
    }

    @Override
    public void setItem(int pSlot, ItemStack pStack) {
        this.items.setStackInSlot(pSlot,pStack);
        this.fermentationTime[pSlot] = 0;
        setChanged();
    }

    public int getTemperature() {
        return temperature;
    }

    public void setTemperature(int temperature) {
        this.temperature = temperature;
    }

    public void addFermentationMaxTime(FermentationBoxBlockEntity box, int amount) {
        updateBlock();
        box.fermentationMaxTime = Math.min(Math.max(fermentationMaxTime + amount, 0), 1200);
    }

    public void subFermentationMaxTime(FermentationBoxBlockEntity box,int amount) {
        updateBlock();
        box.fermentationMaxTime = Math.min(Math.max(fermentationMaxTime - amount, 0),1200);
    }

    public void setFermentationMaxTime(FermentationBoxBlockEntity box,int fermentationMaxTime) {
        updateBlock();
        box.fermentationMaxTime = fermentationMaxTime;
    }

    public int getPerfectTime() {
        return perfectTime;
    }

    public void setPerfectTime(int perfectTime) {
        this.perfectTime = perfectTime;
    }

    public int getFermentationMaxTime() {
        return fermentationMaxTime;
    }

    public int[] getFermentationTime() {
        return fermentationTime;
    }

    public boolean getIntList(int i, int[] intList){
        for (int j = 0; j < intList.length; j++) {
            if (intList[j] == i){
                return true;
            }
        }
        return false;
    }

    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> cap, @Nullable Direction side) {
        if (cap == ForgeCapabilities.ITEM_HANDLER && side != null && directionWrappedHandlerMap.containsKey(side)) {
            return directionWrappedHandlerMap.get(side).cast();
        }
        return super.getCapability(cap, side);
    }

    public void updateBlock() {
        if (level == null){
            return;
        }
        BlockState state = level.getBlockState(worldPosition);
        setChanged(level, worldPosition, state);
        level.sendBlockUpdated(worldPosition, state, state, 3);
    }

    public boolean stillValid(Player player) {
        if (this.level == null || this.level.getBlockEntity(this.worldPosition) != this) {
            return false;
        }
        return player.distanceToSqr((double) this.worldPosition.getX() + 0.5D,
                (double) this.worldPosition.getY() + 0.5D,
                (double) this.worldPosition.getZ() + 0.5D) <= 64.0D;
    }

    public void drops(FermentationBoxBlockEntity blockEntity) {
        SimpleContainer inventory = new SimpleContainer(blockEntity.items.getSlots());
        for (int i = 0; i < blockEntity.items.getSlots(); i++) {
            inventory.setItem(i, blockEntity.items.getStackInSlot(i));
        }
        if (this.level != null) {
            Containers.dropContents(this.level, this.worldPosition, inventory);
        }
    }

    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt) {
        load(pkt.getTag());
    }

    @Override
    public CompoundTag getUpdateTag() {
        CompoundTag tag = new CompoundTag();
        tag.put("Items", items.serializeNBT());
        tag.putInt("Temperature",temperature);
        tag.putInt("PerfectTime",perfectTime);
        tag.putIntArray("FermentationTime", fermentationTime);
        tag.putInt("FermentationMaxTime", fermentationMaxTime);
        return tag;
    }

    public void load(CompoundTag pTag) {
        super.load(pTag);
        if (pTag.contains("Items")) {
            items.deserializeNBT(pTag.getCompound("Items"));
        }
        temperature = pTag.getInt("Temperature");
        perfectTime = pTag.getInt("PerfectTime");
        int[] $$2;
        if (pTag.contains("FermentationTime", 11)) {
            $$2 = pTag.getIntArray("FermentationTime");
            System.arraycopy($$2, 0, fermentationTime, 0, Math.min(fermentationTime.length, $$2.length));
        }
        fermentationMaxTime = pTag.getInt("FermentationMaxTime");
    }

    protected void saveAdditional(CompoundTag pTag) {
        super.saveAdditional(pTag);
        pTag.put("Items", items.serializeNBT());
        pTag.putInt("Temperature",temperature);
        pTag.putInt("PerfectTime",perfectTime);
        pTag.putIntArray("FermentationTime", fermentationTime);
        pTag.putInt("FermentationMaxTime", fermentationMaxTime);
    }

    @Override
    protected Component getDefaultName() {
        return Component.translatable(  "container.fermentation_box");
    }

    @Override
    protected AbstractContainerMenu createMenu(int containerId, Inventory inventory) {
        return new FermentationBoxMenu(containerId,inventory,this.worldPosition,this);
    }

    public float getProgress(float pPartialTicks) {
        return Mth.lerp(pPartialTicks, this.progressOld, this.progress);
    }

    @Override
    public boolean triggerEvent(int pId, int pType) {
        if (pId == 0) {
            if (pType == 0) {
                this.state = FermentationBoxBlockEntity.State.OPEN_PROCESS;
            }
            if (pType == 1) {
                this.state = FermentationBoxBlockEntity.State.CLOSE_PROCESS;
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

    public static void clientTick(Level level, BlockPos pos, BlockState state, FermentationBoxBlockEntity blockEntity){
        blockEntity.progressOld = blockEntity.progress;
        if (!state.getValue(FermentationBoxBlock.POWERED)) {
            switch (blockEntity.state) {
                case OPEN_PROCESS:
                    blockEntity.progress += 0.25F;
                    if (blockEntity.progress >= 1.0F) {
                        blockEntity.progress = 1.0F;
                        blockEntity.state = FermentationBoxBlockEntity.State.OPEN;
                    }
                    break;
                case OPEN:
                    blockEntity.progress = 1.0F;
                    break;
                case CLOSE_PROCESS:
                    blockEntity.progress -= 0.25F;
                    if (blockEntity.progress <= 0F) {
                        blockEntity.progress = 0F;
                        blockEntity.state = FermentationBoxBlockEntity.State.CLOSE;
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
            blockEntity.state = FermentationBoxBlockEntity.State.CLOSE;
        }
    }

    public static void tick(Level pLevel, BlockPos pPos, BlockState pState, FermentationBoxBlockEntity box) {
        if (!box.isEmpty() && box.fermentationMaxTime >= 430) {
            cookTick(pLevel, pPos, pState, box);
        }
        box.refreshTemperature(pLevel, pPos, box);
    }

    public static void cookTick(Level pLevel, BlockPos pPos, BlockState pState, FermentationBoxBlockEntity fermentation) {
        boolean update = false;
        for (int i = 0; i < fermentation.items.getSlots(); i++) {
            ItemStack stackInSlot = fermentation.items.getStackInSlot(i);
            if (stackInSlot.isEmpty()) {
                continue;
            }
            Optional<FermentationBoxRecipe> currentRecipe = fermentation.getCurrentRecipe(stackInSlot);
            if (currentRecipe.isPresent()){
                int time = fermentation.fermentationTime[i];
                if (time < fermentation.fermentationMaxTime){
                    fermentation.fermentationTime[i] ++;
                }else {
                    ItemStack resultItem = currentRecipe.get().getResultItem(null);
                    boolean isPerfect = fermentation.isPerfectFermentation(fermentation);
                    boolean isFermentationItem = resultItem.getItem() instanceof IFermentationItem;
                    if (isPerfect && isFermentationItem){
                        //设置完美发酵nbt
                        resultItem.getOrCreateTag().putBoolean("PerfectFermentation",true);
                    }
                    fermentation.setItem(i,resultItem);
                }
                update = true;
            }
        }
        if (update){
            fermentation.updateBlock();
        }
    }

    private void refreshTemperature(Level pLevel, BlockPos pPos,FermentationBoxBlockEntity fermentation){
        if (level != null && level.getDayTime() == 23999) {
            BakeriesMod.refreshFloatingTemperature();
            fermentation.setNowPerfectTime(pLevel, pPos, fermentation);
            updateBlock();
        }
    }

    public void setNowPerfectTime(Level pLevel, BlockPos pPos,FermentationBoxBlockEntity fermentation){
        fermentation.setTemperature(getNowTemperature(pLevel,pPos));
        fermentation.setPerfectTime(getNowPerfectTime(fermentation.getTemperature()));
    }

    public int getNowTemperature(Level pLevel, BlockPos pPos){
        Holder<Biome> biome = pLevel.getBiome(pPos);
        float biomeTemperature = biome.value().getTemperature(pPos);
        //小于等于0.2寒冷，等于2炎热，其余正常气温
        //浮动温度
        int temperature;
        if (biomeTemperature <= 0.2){
            temperature = BakeriesMod.floatingTemperature;
        }else if (biomeTemperature == 2){
            temperature = 30 + BakeriesMod.floatingTemperature;
        }else {
            temperature = 23 + BakeriesMod.floatingTemperature;
        }
        return temperature;
    }

    private int getNowPerfectTime(double currentTemp) {
        double temp = Math.max(-5.0, Math.min(40.0, currentTemp));
        final int BASE_TICKS = 600;
        final int MIN_TICKS = 100;
        final int MAX_TICKS = 1200;

        if (temp > 23.0) {
            int ticks = (int) (BASE_TICKS - (temp - 23.0) * 10);
            return Math.min(MAX_TICKS, Math.max(MIN_TICKS, ticks));
        } else {
            int ticks = (int) (BASE_TICKS + (23.0 - temp) * 20);
            return Math.min(MAX_TICKS, Math.max(MIN_TICKS, ticks));
        }
    }

    /*通过时间检测是否为完美发酵*/
    private boolean isPerfectFermentation(FermentationBoxBlockEntity box) {
        int deviation = Math.abs(box.fermentationMaxTime - perfectTime);
        return deviation <= 100;
    }

    private Optional<FermentationBoxRecipe> getCurrentRecipe(ItemStack stack) {
        return level.getRecipeManager().getRecipeFor(BakeriesRecipeTypes.FERMENTATION_BOX.get(), new SimpleContainer(stack), level);
    }

    @Override
    public void clearContent() {
        for (int i = 0; i < this.items.getSlots(); i++) {
            this.items.setStackInSlot(i, ItemStack.EMPTY);
        }
    }

    @Override
    public boolean canPlaceItem(int pIndex, @NotNull ItemStack pStack) {
        return items.getStackInSlot(pIndex).isEmpty();
    }

    @Override
    public int getMaxStackSize() {
        return 1;
    }

    public enum State {
        OPEN_PROCESS,
        OPEN,
        CLOSE_PROCESS,
        CLOSE,
    }
}
