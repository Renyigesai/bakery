package com.renyigesai.bakeries.common.blocks.moka_pot;

import com.renyigesai.bakeries.common.blocks.blander.BlenderBlockEntity;
import com.renyigesai.bakeries.common.init.BakeriesBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.neoforged.neoforge.items.ItemStackHandler;

public class MokaPotBlockEntity extends BlockEntity {
    protected final ItemStackHandler inventory = new ItemStackHandler(1);//11个槽位
    public int cookingTotalTime;
    public boolean fill;
    public long wobbleStartedAtTick;
    public MokaPotBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(BakeriesBlocks.Entities.MOKA_POT_ENTITY.get(), pPos, pBlockState);
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider registries) {
        return saveWithoutMetadata(registries);
    }

    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag,registries);
        if (tag.contains("Inventory")) {
            inventory.deserializeNBT(registries,tag.getCompound("Inventory"));
        }
        cookingTotalTime = tag.getInt("CookingTotalTime");
        fill = tag.getBoolean("Fill");
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        tag.put("Inventory", inventory.serializeNBT(registries));
        tag.putInt("CookingTotalTime", cookingTotalTime);
        tag.putBoolean("Fill", fill);
    }

    public boolean isEmpty(){
        return inventory.getStackInSlot(0).isEmpty();
    }

    public int getCookingTotalTime() {
        return cookingTotalTime;
    }

    public boolean getFill(){
        return fill;
    }

    public void addGroundCoffee(ItemStack stack){
        inventory.setStackInSlot(0,stack);
    }

    public boolean isCraft(Level level,BlockPos pos){
        BlockState state = level.getBlockState(pos.below());
        return state.getBlock().getStateDefinition().getProperty("lit") instanceof BooleanProperty booleanProperty && state.getValue(booleanProperty);
    }

    @Override
    public boolean triggerEvent(int id, int type) {
        if (id == 0) {
            if (type == 0 && this.level != null) {
                this.wobbleStartedAtTick = this.level.getGameTime();
            }
            return true;
        } else {
            return super.triggerEvent(id, type);
        }
    }

    public static void craftTick(Level level, BlockPos pos, BlockState state, MokaPotBlockEntity blockEntity) {
        if (blockEntity.isCraft(level, pos) && !blockEntity.isEmpty()) {
            blockEntity.tick();
            setChanged(level, pos, state);
            if (!level.isClientSide) {
                level.sendBlockUpdated(pos, state, state, 3);
            }
        }
    }

    public void tick(){
        if (inventory.getStackInSlot(0).is(ItemTags.create(ResourceLocation.fromNamespaceAndPath("c","coffee_grounds")))){
            if (cookingTotalTime < 200){
                ++ cookingTotalTime;
                if (cookingTotalTime % 10 == 0){
                    if (level != null && !level.isClientSide()) {
                        level.blockEvent(this.worldPosition, this.getBlockState().getBlock(), 0, 0);
                    }
                }
            }else {
                inventory.extractItem(0,1,false);
                cookingTotalTime = 0;
                fill = true;
            }
        }
    }

    public ItemStack extractGroundCoffee(){
        // Extract item from slot 0
        return inventory.extractItem(0, 64, false);
    }
}
