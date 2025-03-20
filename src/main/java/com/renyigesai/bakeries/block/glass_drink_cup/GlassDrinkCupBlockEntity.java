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
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.items.ItemStackHandler;

import java.util.Optional;

public class GlassDrinkCupBlockEntity extends BlockEntity {
    protected final ItemStackHandler inventory = new ItemStackHandler(4);
    public int stage;

    public GlassDrinkCupBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(BakeriesBlocks.GLASS_DRINK_CUP_ENTITY.get(), pPos, pBlockState);
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

    public void addItem(ItemStack stack,Player player) {
        if (stage < 4) {
            for (int i = 0; i < inventory.getSlots(); i++) {
                if (inventory.getStackInSlot(i).isEmpty()) {
                    if (stack.hasCraftingRemainingItem()) {
                        inventory.setStackInSlot(i, stack);
                        ItemUtil.givePlayerItem(player, stack.getCraftingRemainingItem());
                        ++this.stage;
                        break;
                    }
                    inventory.setStackInSlot(i, stack);
                    ++this.stage;
                    break;
                }
            }
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
        level.setBlock(pos,state.setValue(GlassDrinkCupBlock.STAGE, blockEntity.stage),3);
        if (!level.isClientSide) {
            level.sendBlockUpdated(pos, state, state, 2);
        }
    }
}
