package com.renyigesai.bakeries.block.bread_rack;

import com.renyigesai.bakeries.block.glass_drink_cup.GlassDrinkCupBlockEntity;
import com.renyigesai.bakeries.block.oven.OvenBlock;
import com.renyigesai.bakeries.block.oven.OvenBlockEntity;
import com.renyigesai.bakeries.init.BakeriesBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.util.Mth;
import net.minecraft.world.Containers;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;

public class BreadRackBlockEntity extends BlockEntity {

    private final ItemStackHandler items = new ItemStackHandler(4){
        @Override
        protected int getStackLimit(int slot, @NotNull ItemStack stack) {
            return 1;
        }
    };

    public BreadRackBlockEntity.State state = BreadRackBlockEntity.State.CLOSE;
    public float progress;
    public float progressOld;

    public BreadRackBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(BakeriesBlocks.BREAD_RACK_ENTITY.get(), pPos, pBlockState);
    }

    public void drops(BreadRackBlockEntity blockEntity) {
        SimpleContainer inventory = new SimpleContainer(blockEntity.items.getSlots());
        for (int i = 0; i < blockEntity.items.getSlots()-1; i++) {
            ItemStack stackInSlot = blockEntity.items.getStackInSlot(i);
            if (!stackInSlot.isEmpty()){
                inventory.setItem(i, blockEntity.items.getStackInSlot(i));
            }
        }
        if (this.level != null) {
            Containers.dropContents(this.level, this.worldPosition, inventory);
        }
    }

    public boolean setItem(int slot ,ItemStack stack){
        items.setStackInSlot(slot,stack);
        updateBlock();
        return true;
    }

    public boolean putItem(int slot , ItemStack stack){
        if (items.getStackInSlot(slot).isEmpty()){
            items.setStackInSlot(slot,stack);
            updateBlock();
            return true;
        }
        return false;
    }

    public ItemStackHandler getItems() {
        return items;
    }

    public ItemStack getItem(int slot){
        return items.getStackInSlot(slot);
    }

    public int getItemsCount() {
        int count = 0;
        for (int i = 0; i < items.getSlots(); i++) {
            if (!items.getStackInSlot(i).isEmpty()){
                count ++;
            }
        }
        return count;
    }

    public boolean isEmpty(){
        for (int i = 0; i < items.getSlots(); i++) {
            if (!items.getStackInSlot(i).isEmpty()){
                return false;
            }
        }
        return true;
    }

    @Override
    public CompoundTag getUpdateTag() {
        return this.saveWithoutMetadata();
    }

    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    public void load(CompoundTag pTag) {
        super.load(pTag);
        if (pTag.contains("Items")) {
            items.deserializeNBT(pTag.getCompound("Items"));
        }
    }

    protected void saveAdditional(CompoundTag pTag) {
        super.saveAdditional(pTag);
        pTag.put("Items", items.serializeNBT());
    }

    public void updateBlock() {
        if (level == null){
            return;
        }
        BlockState state = level.getBlockState(worldPosition);
        setChanged(level, worldPosition, state);
        level.sendBlockUpdated(worldPosition, state, state, 3);
    }

    public float getProgress(float pPartialTicks) {
        return Mth.lerp(pPartialTicks, this.progressOld, this.progress);
    }

    @Override
    public boolean triggerEvent(int pId, int pType) {
        if (pId == 0) {
            if (pType == 0) {
                this.state = BreadRackBlockEntity.State.OPEN_PROCESS;
            }
            if (pType == 1) {
                this.state = BreadRackBlockEntity.State.CLOSE_PROCESS;
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

    public static void clientTick(Level level, BlockPos pos, BlockState state, BreadRackBlockEntity blockEntity){
        blockEntity.progressOld = blockEntity.progress;
        switch (blockEntity.state) {
            case OPEN_PROCESS:
                blockEntity.progress += 0.25F;
                if (blockEntity.progress >= 1.0F) {
                    blockEntity.progress = 1.0F;
                    blockEntity.state = BreadRackBlockEntity.State.OPEN;
                }
                break;
            case OPEN:
                blockEntity.progress = 1.0F;
                break;
            case CLOSE_PROCESS:
                blockEntity.progress -= 0.25F;
                if (blockEntity.progress <= 0F) {
                    blockEntity.progress = 0F;
                    blockEntity.state = BreadRackBlockEntity.State.CLOSE;
                }
                break;
            case CLOSE:
                blockEntity.progress = 0.0F;
                break;
        }
    }

    public enum State {
        OPEN_PROCESS,
        OPEN,
        CLOSE_PROCESS,
        CLOSE,
    }
}
