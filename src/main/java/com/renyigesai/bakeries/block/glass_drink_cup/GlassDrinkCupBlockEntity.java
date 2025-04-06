package com.renyigesai.bakeries.block.glass_drink_cup;

import com.renyigesai.bakeries.init.BakeriesBlocks;
import com.renyigesai.bakeries.recipe.CoffeeRecipe;
import com.renyigesai.bakeries.util.ItemUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.Containers;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.items.ItemStackHandler;

import java.util.Optional;

public class GlassDrinkCupBlockEntity extends BlockEntity {
    protected final ItemStackHandler inventory = new ItemStackHandler(5);
    public int stage;

    public GlassDrinkCupBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(BakeriesBlocks.DRINK_CUP_ENTITY.get(), pPos, pBlockState);
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        if (tag.contains("Inventory")) {
            inventory.deserializeNBT(tag.getCompound("Inventory"));
        }
        if (tag.contains("Stage")) {
            stage = tag.getInt("Stage");
        }
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        tag.put("Inventory", inventory.serializeNBT());
        tag.putInt("Stage", stage);
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

    public ItemStackHandler getInventory() {
        return inventory;
    }

    public boolean isInventoryFull(){
        for (int i = 0; i < inventory.getSlots()-1; i++) {
            if (inventory.getStackInSlot(i).isEmpty()){
                return true;
            }
        }
        return false;
    }

    public void addItem(ItemStack stack, Player player) {
        for (int i = 0; i < inventory.getSlots()-1; i++) {
            if (inventory.getStackInSlot(i).isEmpty()) {
                if (stack.hasCraftingRemainingItem()) {
                    inventory.setStackInSlot(i, stack);
                    ItemUtil.givePlayerItem(player, stack.getCraftingRemainingItem());
                    break;
                }
                inventory.setStackInSlot(i, stack);
                break;
            }
        }
    }

    public void removeItems(){
        for (int i = 0; i < inventory.getSlots(); i++) {
            inventory.setStackInSlot(i,ItemStack.EMPTY);
        }
    }

    public void drops(GlassDrinkCupBlockEntity blockEntity) {
        SimpleContainer inventory = new SimpleContainer(blockEntity.inventory.getSlots());
        for (int i = 0; i < blockEntity.inventory.getSlots()-1; i++) {
            ItemStack stackInSlot = blockEntity.inventory.getStackInSlot(i);
            if (!stackInSlot.hasCraftingRemainingItem()) {
                inventory.setItem(i, blockEntity.inventory.getStackInSlot(i));
            }
        }
        if (this.level != null) {
            Containers.dropContents(this.level, this.worldPosition, inventory);
        }
    }

    public ItemStack getCraftItem(){
        Optional<CoffeeRecipe> recipeOptional = getCurrentRecipe();
        if (recipeOptional.isPresent()) {
            CoffeeRecipe recipe = recipeOptional.get();
            for (int i = 0; i < recipe.getIngredients().size(); i++) {
                if (recipe.getIngredients().get(i).test(inventory.getStackInSlot(i))) {
                    break;
                }
            }
            return recipe.getResultItem(level.registryAccess()).copy();
        }
        return ItemStack.EMPTY;
    }

    public Optional<CoffeeRecipe> getCurrentRecipe() {
        SimpleContainer inventory = new SimpleContainer(4); // 假设输入容器大小为 4
        for (int i = 0; i < 4; i++) {
            inventory.setItem(i, this.inventory.getStackInSlot(i));
        }
        if (level == null) {
            return Optional.empty();
        }
        return level.getRecipeManager()
                .getRecipeFor(CoffeeRecipe.Type.INSTANCE, inventory, level);
    }

    public static void tick(Level level, BlockPos pos, BlockState state, GlassDrinkCupBlockEntity blockEntity) {
        blockEntity.craftTick();
        if (!level.isClientSide) {
            level.sendBlockUpdated(pos, state, state, 2);
        }
    }

    public void craftTick(){
        Optional<CoffeeRecipe> recipeOptional = getCurrentRecipe();
        if (recipeOptional.isPresent()) {
            CoffeeRecipe recipe = recipeOptional.get();
            for (int i = 0; i < recipe.getIngredients().size(); i++) {
                if (recipe.getIngredients().get(i).test(inventory.getStackInSlot(i))) {
                    break;
                }
            }
            inventory.setStackInSlot(4,recipe.getResultItem(level.registryAccess()).copy());
        }
    }
}
