package com.renyigesai.bakeries.block.pizza_dough;


import com.renyigesai.bakeries.init.BakeriesBlocks;
import com.renyigesai.bakeries.recipe.PizzaRecipe;
import com.renyigesai.bakeries.util.Shortcuts;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class PizzaDoughBlockEntity extends BlockEntity {

    private ItemStackHandler inventory = new ItemStackHandler(4){
        @Override
        protected int getStackLimit(int slot, @NotNull ItemStack stack) {
            return 1;
        }
    };

    public PizzaDoughBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(BakeriesBlocks.PIZZA_DOUGH_ENTITY.get(), pPos, pBlockState);
    }

    public void addItem(ItemStack item){
        for (int i = 0; i < this.inventory.getSlots(); i++) {
            ItemStack stack = this.inventory.getStackInSlot(i);
            if (stack.isEmpty()){
                this.inventory.setStackInSlot(i,item.split(1));
                setChanged();
                return;
            }
        }
    }

    public void getItem(Player player){
        for (int i = 0; i < this.inventory.getSlots(); i++) {
            ItemStack stack = this.inventory.getStackInSlot(i);
            if (!stack.isEmpty()){
                this.inventory.setStackInSlot(i,ItemStack.EMPTY.split(1));
                Shortcuts.givePlayerItem(player,stack);
                setChanged();
                return;
            }
        }
    }

    public ItemStackHandler getInventory(){
        return this.inventory;
    }

    @Nullable
    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt) {
        this.load(pkt.getTag());
    }

    @Override
    public void setChanged() {
        super.setChanged();
        if (this.level != null){
            this.level.sendBlockUpdated(this.getBlockPos(),this.getBlockState(),this.getBlockState(),2);
        }
    }

    private CompoundTag writeItems(CompoundTag compound) {
        super.saveAdditional(compound);
        compound.put("Inventory",this.inventory.serializeNBT());
        return compound;
    }

    @Override
    public CompoundTag getUpdateTag() {
        return this.writeItems(new CompoundTag());
    }

    @Override
    protected void saveAdditional(CompoundTag pTag) {
        this.writeItems(pTag);
    }

    public static void updateBlock(PizzaDoughBlockEntity pizzaDoughBlockEntity) {
        Level world = pizzaDoughBlockEntity.getLevel();
        BlockPos pos = pizzaDoughBlockEntity.getBlockPos();
        BlockState state = world.getBlockState(pos);
        setChanged(world, pos, state);
        world.sendBlockUpdated(pos, state, state, 3);
    }

    private Optional<PizzaRecipe> getCurrentRecipe(int slot) {
        ItemStack stack = inventory.getStackInSlot(slot);
        if (!stack.isEmpty()) {
            return level.getRecipeManager().getRecipeFor(PizzaRecipe.Type.INSTANCE, new SimpleContainer(stack), level);
        }
        return Optional.empty();
    }

    public static void craftTick(Level level, BlockPos pos, BlockState state, PizzaDoughBlockEntity pizzaDoughBlockEntity) {
        pizzaDoughBlockEntity.craftItem(level, pizzaDoughBlockEntity, pos, state);
        setChanged(level,pos,state);
        if (!level.isClientSide){
            level.sendBlockUpdated(pos,state,state,3);
        }
    }

    private void craftItem(Level level,PizzaDoughBlockEntity pizzaDoughBlockEntity, BlockPos pos, BlockState state){
        updateBlock(pizzaDoughBlockEntity);
        boolean temp = false;
        int size = this.inventory.getSlots();
        for (int i = 0; i < size; i++) {
            ItemStack pizzaDoughStack = inventory.getStackInSlot(i);
            if (!pizzaDoughStack.isEmpty()){
                Optional<PizzaRecipe> recipe = getCurrentRecipe(i);
                if (!recipe.isPresent()){
                    return;
                }
                System.out.println(recipe);
                if (i == size-1){
                    ItemStack resultItemTemp = recipe.get().getResultItem(null);
                    ItemStack resultItem = new ItemStack(resultItemTemp.getItem(),resultItemTemp.getCount());
                    if (!resultItem.isEmpty()){
                        inventory.setStackInSlot(i,ItemStack.EMPTY);
                        Shortcuts.spawnItemEntity(level, resultItem, pos.getX() + 0.5, pos.getY() + 0.3, pos.getZ() + 0.5,
                                new Vec3(0.0,0.0,0.0));
                        level.removeBlock(pos,false);
                        temp = true;
                    }
                }
            }
        }
        if (temp){
            updateBlock(pizzaDoughBlockEntity);
            level.sendBlockUpdated(pos,state,state,3);
        }

//        ItemStack resultItemTemp = recipe.get().getResultItem(null);
//        ItemStack resultItem = new ItemStack(resultItemTemp.getItem(),resultItemTemp.getCount());
//        updateBlock(pizzaDoughBlockEntity);
//        if (!resultItem.isEmpty()){
//            inventory.setStackInSlot(i,ItemStack.EMPTY);
//            Shortcuts.spawnItemEntity(level, resultItem, pos.getX() + 0.5, pos.getY() + 0.3, pos.getZ() + 0.5,
//                    new Vec3(0.0,0.0,0.0));
//            level.removeBlock(pos,false);
//            temp = true;
//        }
    }

    @Override
    public void load(CompoundTag pTag) {
        super.load(pTag);
        if (pTag.contains("Inventory")){
            this.inventory.deserializeNBT(pTag.getCompound("Inventory"));
        }else {
            this.inventory.deserializeNBT(pTag);
        }
    }
}
