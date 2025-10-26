package com.renyigesai.bakeries.block.pizza;

import com.renyigesai.bakeries.init.BakeriesBlocks;
import com.renyigesai.bakeries.init.BakeriesItems;
import com.renyigesai.bakeries.recipe.PizzaRecipe;
import com.renyigesai.bakeries.util.ItemUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.Containers;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec2;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class PizzaFlatbreadBlockEntity extends BlockEntity {

    private ItemStackHandler inventory = new ItemStackHandler(4){
        @Override
        protected int getStackLimit(int slot, @NotNull ItemStack stack) {
            return 1;
        }
    };

    private ItemStackHandler cheeses = new ItemStackHandler(1){
        @Override
        protected int getStackLimit(int slot, @NotNull ItemStack stack) {
            return 1;
        }
    };

    public PizzaFlatbreadBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(BakeriesBlocks.PIZZA_FLATBREAD_ENTITY.get(), pPos, pBlockState);
    }

    public ItemStackHandler getInventory() {
        return this.inventory;
    }

    public ItemStackHandler getCheeses() {
        return this.cheeses;
    }

    public int getContainerSize() {
        return inventory.getSlots();
    }

    public boolean isEmpty() {
        for (int i = 0; i < inventory.getSlots(); i++) {
            ItemStack stackInSlot = inventory.getStackInSlot(i);
            if (stackInSlot.isEmpty()) {
                return true;
            }
        }
        return false;
    }

    public ItemStack getItem(int pSlot) {
        return this.inventory.getStackInSlot(pSlot);
    }

    public ItemStack removeItem(int pSlot, int pAmount) {
        return inventory.extractItem(pSlot,pAmount,false);
    }

    public ItemStack removeItemNoUpdate(int pSlot) {
        return null;
    }

    public void setItem(int pSlot, ItemStack pStack) {
        this.inventory.setStackInSlot(pSlot,pStack);
    }

    public void drops(PizzaFlatbreadBlockEntity blockEntity) {
        SimpleContainer inventory = new SimpleContainer(blockEntity.inventory.getSlots() + 1);
        for (int i = 0; i < blockEntity.inventory.getSlots(); i++) {
            ItemStack stackInSlot = blockEntity.inventory.getStackInSlot(i);
            if (!stackInSlot.hasCraftingRemainingItem()){
                inventory.setItem(i, blockEntity.inventory.getStackInSlot(i));
            }
        }
        inventory.setItem(4,cheeses.getStackInSlot(0));
        if (this.level != null) {
            Containers.dropContents(this.level, this.worldPosition, inventory);
        }
    }

    public void removeItems(){
        for (int i = 0; i < inventory.getSlots(); i++) {
            inventory.setStackInSlot(i,ItemStack.EMPTY);
        }
        cheeses.setStackInSlot(0,ItemStack.EMPTY);
        updateBlock();
    }

    public boolean isSynthesis() {
        if (level != null) {
            return !cheeses.getStackInSlot(0).isEmpty() && level.getBlockState(worldPosition).getValue(PizzaFlatbreadBlock.SAUCE);
        }
        return false;
    }

    public boolean addItem(ItemStack stack, Player player){
        for (int i = 0; i < inventory.getSlots(); i++) {
            ItemStack stackInSlot = inventory.getStackInSlot(i);
            if (stackInSlot.isEmpty()){
                if (stack.getItem().isEdible() && !stack.is(ItemTags.create(new ResourceLocation("bakeries:not_pizza_ingredients")))){
                    if (stack.hasCraftingRemainingItem()) {
                        ItemUtil.givePlayerItem(player, stack.getCraftingRemainingItem());
                    }
                    stack.setCount(1);
                    inventory.setStackInSlot(i,stack);
                    updateBlock();
                    return true;
                }else {
                    return false;
                }
            }
        }
        return false;
    }

    public boolean startSynthesis(){
        if (level == null){
            return false;
        }
        if (isSynthesis() && isEmpty()){
            removeItems();
            level.setBlockAndUpdate(worldPosition,BakeriesBlocks.RAW_PIZZA.get().defaultBlockState());
            return true;
        }
        Optional<PizzaRecipe> currentRecipe = getCurrentRecipe();
            if (currentRecipe.isPresent()){
                PizzaRecipe pizzaRecipe = currentRecipe.get();
                ItemStack resultItem = pizzaRecipe.getResultItem(null);
                removeItems();
                if (resultItem.getItem() instanceof BlockItem blockItem){
                    level.setBlock(worldPosition,blockItem.getBlock().defaultBlockState(),3);
                }else {
                    level.removeBlock(worldPosition,false);
                    ItemUtil.spawnItemEntity(level,resultItem,worldPosition);
                }
                return true;
            }
            return false;
    }

    public ItemStack getSynthesisItem(){
        if (level == null){
            return ItemStack.EMPTY;
        }
        if (isSynthesis() && isEmpty()){
            return new ItemStack(BakeriesItems.RAW_PIZZA.get());
        }
        Optional<PizzaRecipe> currentRecipe = getCurrentRecipe();
        if (currentRecipe.isPresent()){
            PizzaRecipe pizzaRecipe = currentRecipe.get();
            return pizzaRecipe.getResultItem(null);
        }else {
            ItemStack pizza = new ItemStack(BakeriesItems.RAW_CUSTOM_PIZZA.get(),1);
            CompoundTag compoundTag = new CompoundTag();
            ItemStackHandler handler = new ItemStackHandler(4);
            for (int i = 0; i < this.inventory.getSlots(); i++) {
                handler.setStackInSlot(i,this.inventory.getStackInSlot(i));
            }
            compoundTag.put("Inventory",handler.serializeNBT());
            pizza.setTag(compoundTag);
            return pizza;
        }
    }

    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }
    @Override
    public CompoundTag getUpdateTag() {
        CompoundTag tag = new CompoundTag();
        tag.put("Inventory",inventory.serializeNBT());
        tag.put("Cheeses",cheeses.serializeNBT());
        return tag;
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        if (tag.contains("Inventory")) {
            inventory.deserializeNBT(tag.getCompound("Inventory"));
        }
        if (tag.contains("Cheeses")) {
            cheeses.deserializeNBT(tag.getCompound("Cheeses"));
        }
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        tag.put("Inventory", inventory.serializeNBT());
        tag.put("Cheese", cheeses.serializeNBT());
    }

    public Optional<PizzaRecipe> getCurrentRecipe() {
        SimpleContainer inventory = new SimpleContainer(4);
        for (int i = 0; i < 4; i++) {
            inventory.setItem(i, this.inventory.getStackInSlot(i));
        }
        if (level == null) {
            return Optional.empty();
        }
        return level.getRecipeManager()
                .getRecipeFor(PizzaRecipe.Type.INSTANCE, inventory, level);
    }

    public void updateBlock() {
        if (level == null){
            return;
        }
        BlockState state = level.getBlockState(worldPosition);
        setChanged(level, worldPosition, state);
        level.sendBlockUpdated(worldPosition, state, state, 3);
    }


    public Vec2[] getVec2(){
        Vec2[] vec2s;
        float size = 0.15f;
        vec2s = new Vec2[]{
                new Vec2(0.5f, 0.5f + size),
                new Vec2(0.5f, 0.5f - size),
                new Vec2(0.5f + size,0.5f),
                new Vec2(0.5f - size,0.5f),
        };
        return vec2s;
    }

    public Vec2[] getCheeseVec2(){
        Vec2[] vec2s;
        float size = 0.2f;
        vec2s = new Vec2[]{
                new Vec2(0.46875f,0.46875f),//Z
                new Vec2(0.5f + size, 0.5f + size),//0.5 +
                new Vec2(0.5f - size, 0.5f + size),
                new Vec2(0.5f + size,0.5f - size),
                new Vec2(0.5f - size,0.5f - size),
        };
        return vec2s;
    }

}
