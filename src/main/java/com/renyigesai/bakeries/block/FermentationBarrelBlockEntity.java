package com.renyigesai.bakeries.block;

import com.renyigesai.bakeries.block.blender.BlenderBlockEntity;
import com.renyigesai.bakeries.init.BakeriesBlocks;
import com.renyigesai.bakeries.inventory.fermentation_barrel.FermentationBarrelMenu;
import com.renyigesai.bakeries.recipe.FermentationRecipe;
import com.renyigesai.bakeries.util.ItemUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.Containers;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class FermentationBarrelBlockEntity extends BaseContainerBlockEntity {
    protected final ItemStackHandler inventory = new ItemStackHandler(9){
        @Override
        protected int getStackLimit(int slot, @NotNull ItemStack stack) {
            if (slot == 8){
                return 1;
            }
            return super.getStackLimit(slot,stack);
        }

        @Override
        public @NotNull ItemStack insertItem(int slot, @NotNull ItemStack stack, boolean simulate) {
            return super.insertItem(slot, stack, simulate);
        }

        @Override
        public @NotNull ItemStack extractItem(int slot, int amount, boolean simulate) {
            return super.extractItem(slot, amount, simulate);
        }
    };
    private int cookingTotalTime;
    private static final int STORAGE_CONTAINER_SLOT = 6;
    private static final int STORAGE_SLOT = 7;
    private static final int OUTPUT_SLOT = 8;
    public FermentationBarrelBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(BakeriesBlocks.FERMENTATION_BARREL_ENTITY.get(), pPos, pBlockState);
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        if (tag.contains("Inventory")) {
            inventory.deserializeNBT(tag.getCompound("Inventory"));
        }
        cookingTotalTime = tag.getInt("CookingTotalTime");
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        tag.put("Inventory", inventory.serializeNBT());
        tag.putInt("CookingTotalTime", cookingTotalTime);
    }

    @Override
    protected @NotNull Component getDefaultName() {
        return Component.translatable("container.fermentation_barrel");
    }

    @Override
    protected AbstractContainerMenu createMenu(int containerId, Inventory playerInventory) {
        return new FermentationBarrelMenu(containerId, playerInventory, this);
    }

    @Override
    public CompoundTag getUpdateTag() {
        return saveWithoutMetadata();
    }

    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt) {
        load(pkt.getTag());
    }

    public int getCookingTotalTime() {
        return this.cookingTotalTime;
    }

    public void drops(FermentationBarrelBlockEntity blockEntity) {
        SimpleContainer inventory = new SimpleContainer(7);
        for (int i = 0; i < 6; i++) {
            inventory.setItem(i, blockEntity.inventory.getStackInSlot(i));
        }
        inventory.setItem(6,blockEntity.inventory.getStackInSlot(OUTPUT_SLOT));
        if (this.level != null) {
            Containers.dropContents(this.level, this.worldPosition, inventory);
        }
    }

    private Optional<FermentationRecipe> getCurrentRecipe() {
        SimpleContainer inventory = new SimpleContainer(6);
        List<ItemStack> inputs = new ArrayList<>();

        for (int i = 0; i < 6; i++) {
            ItemStack stack = this.inventory.getStackInSlot(i);
            if (!stack.isEmpty()) {
                inputs.add(stack);
            }
        }

        for (int i = 0; i < inputs.size(); i++) {
            inventory.setItem(i, inputs.get(i));
        }

        return level.getRecipeManager()
                .getRecipeFor(FermentationRecipe.Type.INSTANCE, inventory, level);
    }

    public static void tick(Level level, BlockPos pos, BlockState state, FermentationBarrelBlockEntity blockEntity) {
        blockEntity.craftItem();
        blockEntity.takeTick();
        level.setBlock(pos,state.setValue(FermentationBarrelBlock.FILL,isFill(blockEntity)),3);
        setChanged(level, pos, state);
        if (!level.isClientSide) {
            level.sendBlockUpdated(pos, state, state, 3);
        }
    }

    private void craftItem() {
        Optional<FermentationRecipe> recipeOptional = getCurrentRecipe();
        if (recipeOptional.isEmpty()) {
            cookingTotalTime = 0; // 重置进度
            if (inventory.getStackInSlot(STORAGE_SLOT).isEmpty()){
                inventory.setStackInSlot(STORAGE_CONTAINER_SLOT,ItemStack.EMPTY);
            }
            return;
        }

        FermentationRecipe recipe = recipeOptional.get();
        ItemStack resultItem = recipe.getResultItem(level.registryAccess()).copy();
        ItemStack outputStack = inventory.getStackInSlot(STORAGE_SLOT);

        if (!recipe.getContainer().isEmpty()){
            inventory.setStackInSlot(STORAGE_CONTAINER_SLOT,recipe.getContainer());
        }
        for (int i = 0; i < 6; i++) {
            if (inventory.getStackInSlot(i).isEmpty()&&inventory.getStackInSlot(STORAGE_SLOT).isEmpty()){
                inventory.setStackInSlot(STORAGE_CONTAINER_SLOT,ItemStack.EMPTY);
            }else {
                break;
            }
        }

        if (!canCraft(resultItem, outputStack)) {
            cookingTotalTime = 0;
            return;
        }

        List<Ingredient> ingredientsToConsume = new ArrayList<>(recipe.getIngredients());
        List<Integer> slotsToConsume = new ArrayList<>();

        outer:
        for (Ingredient ingredient : ingredientsToConsume) {
            for (int i = 0; i < 6; i++) {
                ItemStack stack = inventory.getStackInSlot(i);
                if (!stack.isEmpty() && ingredient.test(stack) && !slotsToConsume.contains(i)) {
                    slotsToConsume.add(i);
                    continue outer;
                }
            }
            cookingTotalTime = 0;
            return;
        }
        if (cookingTotalTime < 3600) {
            cookingTotalTime++;
        } else {
            for (int slot : slotsToConsume) {
                ItemStack stack = inventory.getStackInSlot(slot);
                if (!stack.is(Items.WATER_BUCKET)) {
                    if (stack.hasCraftingRemainingItem()) {
                        ejectIngredientRemainder(stack.getCraftingRemainingItem());
                    }
                    inventory.extractItem(slot, 1, false);
                }
            }

            if (outputStack.isEmpty()) {
                inventory.setStackInSlot(STORAGE_SLOT, resultItem);
            } else {
                outputStack.grow(resultItem.getCount());
            }

            cookingTotalTime = 0;
            setChanged();
            level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), 3);
        }
    }

    private void takeTick(){
        if (!inventory.getStackInSlot(STORAGE_SLOT).isEmpty()){
            ItemStack stackInSlot = inventory.getStackInSlot(STORAGE_CONTAINER_SLOT);
            ItemStack stackInOutputSlot = inventory.getStackInSlot(OUTPUT_SLOT);
            if (stackInOutputSlot.is(stackInSlot.getItem())){
                ItemStack copy = inventory.getStackInSlot(STORAGE_SLOT).copy();
                copy.setCount(1);
                inventory.setStackInSlot(OUTPUT_SLOT, copy);
                inventory.extractItem(STORAGE_SLOT,1,false);
            }
        }
    }

    public static boolean isFill(FermentationBarrelBlockEntity blockEntity){
        return !blockEntity.inventory.getStackInSlot(STORAGE_SLOT).isEmpty() || !blockEntity.inventory.getStackInSlot(OUTPUT_SLOT).isEmpty();
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
    protected void ejectIngredientRemainder(ItemStack remainderStack) {
        BlockPos pos = this.getBlockPos();
        Level level = this.level;
        Direction facing = level.getBlockState(pos).getValue(HorizontalDirectionalBlock.FACING).getClockWise();
        double x = pos.getX() + 0.5D;
        double z = pos.getZ() + 0.5D;
        double newX = x + (facing.getStepX()*1.0D);
        double newZ = z + (facing.getStepZ()*1.0D);
        System.out.println(newX);
        System.out.println(newZ);
        ItemUtil.spawnItemEntity(this.level,remainderStack, newX, pos.getY(), newZ,new Vec3(0.0D,0.0D,0.0D));
    }

    public IItemHandler getInventory() {
        return inventory;
    }

    @Override
    public int getContainerSize() {
        return inventory.getSlots();
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public ItemStack getItem(int pSlot) {
        return inventory.getStackInSlot(pSlot);
    }

    @Override
    public ItemStack removeItem(int pSlot, int pAmount) {
        return inventory.extractItem(pSlot,pAmount,false);
    }

    @Override
    public ItemStack removeItemNoUpdate(int pSlot) {
        return null;
    }

    @Override
    public void setItem(int pSlot, ItemStack pStack) {
        inventory.setStackInSlot(pSlot,pStack);
    }

    public boolean stillValid(Player player) {
        if (this.level == null || this.level.getBlockEntity(this.worldPosition) != this) {
            return false;
        }
        return player.distanceToSqr((double) this.worldPosition.getX() + 0.5D,
                (double) this.worldPosition.getY() + 0.5D,
                (double) this.worldPosition.getZ() + 0.5D) <= 64.0D;
    }

    @Override
    public void clearContent() {
        for (int i = 0; i < inventory.getSlots(); i++) {
            inventory.setStackInSlot(i, ItemStack.EMPTY);
        }
    }
}
