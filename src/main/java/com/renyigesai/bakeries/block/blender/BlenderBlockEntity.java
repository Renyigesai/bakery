package com.renyigesai.bakeries.block.blender;

import com.renyigesai.bakeries.api.block.WrappedHandler;
import com.renyigesai.bakeries.init.BakeriesBlocks;
import com.renyigesai.bakeries.inventory.blender.BlenderMenu;
import com.renyigesai.bakeries.recipe.blender.BlenderRecipe;
import com.renyigesai.bakeries.util.ItemUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.Optional;

public class BlenderBlockEntity extends BlockEntity implements MenuProvider {

    private static final int CONTAINER_SLOT = 9;
    private static final int OUTPUT_SLOT = 10;
    private static final int[] INPUT_SLOTS = new int[]{0,1,2,3,4,5,6,7,8};
    private static final int[] EXART_SLOTS = new int[]{9};
    private static final int[] OUTPUT_SLOTS = new int[]{10};

    protected final ItemStackHandler inventory = new ItemStackHandler(11);//11个槽位
    public void setHandler(ItemStackHandler itemStackHandler) {
        for (int i = 0; i < itemStackHandler.getSlots(); i++) {
            inventory.setStackInSlot(i, itemStackHandler.getStackInSlot(i));
        }
    }


    private LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();
    // itemhandler,extract,insert
    // down -> out2
    // up ->
    // right  -> out1 in1
    // left  -> out 0 or 1 || in0 ro 1
    // forward -> out 2 in false
    // back  -> in 1 out 1
    private final Map<Direction, LazyOptional<WrappedHandler>> directionWrappedHandlerMap =
            Map.of(
                    Direction.DOWN, LazyOptional.of(
                            () -> new WrappedHandler(inventory, (i) -> getIntList(i,OUTPUT_SLOTS), (i, s) -> false)
                    ),
                    Direction.UP, LazyOptional.of(
                            () -> new WrappedHandler(inventory, (i) -> false, (i, s) -> getIntList(i,INPUT_SLOTS) && canPlaceItem(i,s))
                    ),
                    Direction.EAST, LazyOptional.of(
                            () -> new WrappedHandler(inventory, (i) -> false, (i, s) -> getIntList(i,EXART_SLOTS) && canPlaceItem(i,s))
                    ),
                    Direction.WEST, LazyOptional.of(
                            () -> new WrappedHandler(inventory, (i) -> false, (i, s) -> getIntList(i,EXART_SLOTS) && canPlaceItem(i,s))
                    ),
                    Direction.NORTH, LazyOptional.of(
                            () -> new WrappedHandler(inventory, (i) -> false, (i, s) -> getIntList(i,EXART_SLOTS) && canPlaceItem(i,s))
                    ),
                    Direction.SOUTH, LazyOptional.of(
                            () -> new WrappedHandler(inventory, (i) -> false, (i, s) -> getIntList(i,EXART_SLOTS) && canPlaceItem(i,s))
                    )
            );
    protected final ItemStackHandler filtrationinventory = new ItemStackHandler(10){
        @Override
        public int getSlotLimit(int slot) {
            return 1;
        }
    };//9个过滤槽位

    public int cookingTotalTime;
    public boolean compatibility;

    public BlenderBlockEntity(BlockPos pos, BlockState state) {
        super(BakeriesBlocks.BLENDER_ENTITY.get(), pos, state);
    }
    public boolean getIntList(int i,int[] intList){
        for (int j = 0; j < intList.length; j++) {
            if (intList[j] == i){
                return true;
            }
        }
        return false;
    }
    public ItemStackHandler getInventory() {
        return this.inventory;
    }

    public ItemStackHandler getFiltrationinventory() {
        return this.filtrationinventory;
    }
    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (cap == ForgeCapabilities.ITEM_HANDLER){
            if(side == null) {
                return lazyItemHandler.cast();
            }

            if(directionWrappedHandlerMap.containsKey(side)) {
                Direction localDir = this.getBlockState().getValue(BlenderBlock.FACING);

                if(side == Direction.UP || side == Direction.DOWN) {
                    return directionWrappedHandlerMap.get(side).cast();
                }

                return switch (localDir) {
                    default -> directionWrappedHandlerMap.get(side.getOpposite()).cast();
                    case EAST -> directionWrappedHandlerMap.get(side.getClockWise()).cast();
                    case SOUTH -> directionWrappedHandlerMap.get(side).cast();
                    case WEST -> directionWrappedHandlerMap.get(side.getCounterClockWise()).cast();
                };
            }
        }

        return super.getCapability(cap, side);
    }

    @Override
    public void onLoad() {
        super.onLoad();
        lazyItemHandler = LazyOptional.of(() -> inventory);

    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        lazyItemHandler.invalidate();

    }
    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        if (tag.contains("Inventory")) {
            inventory.deserializeNBT(tag.getCompound("Inventory"));
        }
        if (tag.contains("FiltrationInventory")) {
            filtrationinventory.deserializeNBT(tag.getCompound("FiltrationInventory"));
        }
        cookingTotalTime = tag.getInt("CookingTotalTime");
        compatibility = tag.getBoolean("Compatibility");
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        tag.put("Inventory", inventory.serializeNBT());
        tag.put("FiltrationInventory", filtrationinventory.serializeNBT());
        tag.putInt("CookingTotalTime", cookingTotalTime);
        tag.putBoolean("Compatibility", compatibility);
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

    public void drops(BlenderBlockEntity blockEntity) {
        SimpleContainer inventory = new SimpleContainer(blockEntity.inventory.getSlots());
        SimpleContainer filtrationinventory = new SimpleContainer(blockEntity.filtrationinventory.getSlots());
        for (int i = 0; i < blockEntity.inventory.getSlots(); i++) {
            inventory.setItem(i, blockEntity.inventory.getStackInSlot(i));
        }
        for (int i = 0; i < blockEntity.filtrationinventory.getSlots(); i++) {
            filtrationinventory.setItem(i, blockEntity.filtrationinventory.getStackInSlot(i));
        }
        if (this.level != null) {
            Containers.dropContents(this.level, this.worldPosition, inventory);
            Containers.dropContents(this.level, this.worldPosition, filtrationinventory);
        }
    }

    public boolean isCloseCompatibility(){
        for (int i = 0; i < filtrationinventory.getSlots(); i++) {
            if (!filtrationinventory.getStackInSlot(i).isEmpty()){
                return false;
            }
        }
        return true;
    }

    public boolean stillValid(Player player) {
        if (this.level == null || this.level.getBlockEntity(this.worldPosition) != this) {
            return false;
        }
        return player.distanceToSqr((double) this.worldPosition.getX() + 0.5D,
                (double) this.worldPosition.getY() + 0.5D,
                (double) this.worldPosition.getZ() + 0.5D) <= 64.0D;
    }

    private Optional<BlenderRecipe> getCurrentRecipe() {
        SimpleContainer inventory = new SimpleContainer(11); // 假设输入容器大小为 10
        for (int i = 0; i < 10; i++) {
            inventory.setItem(i, this.inventory.getStackInSlot(i));
        }
        if (level == null) {
            return Optional.empty();
        }
        return level.getRecipeManager()
                .getRecipeFor(BlenderRecipe.Type.INSTANCE, inventory, level);
    }

    public static void craftTick(Level level, BlockPos pos, BlockState state, BlenderBlockEntity blockEntity) {
        blockEntity.craftItem();
        boolean temp = blockEntity.cookingTotalTime > 0;
        level.setBlock(pos,state.setValue(BlenderBlock.POWERED,temp),3);
        setChanged(level, pos, state);
        if (!level.isClientSide) {
            level.sendBlockUpdated(pos, state, state, 3);
        }
    }

    private void craftItem() {
        Optional<BlenderRecipe> recipeOptional = getCurrentRecipe();
        if (recipeOptional.isPresent()) {
            BlenderRecipe recipe = recipeOptional.get();

            boolean canCraft = false;
            for (int i = 0; i < recipe.getIngredients().size(); i++) {
                if (recipe.getIngredients().get(i).test(inventory.getStackInSlot(i)) && isContainer()) {
                        canCraft = true;
                        break;
                }
            }

            ItemStack resultItem = recipe.getResultItem(level.registryAccess()).copy();
            ItemStack outputStack = inventory.getStackInSlot(OUTPUT_SLOT);
            if (canCraft && canCraft(resultItem,outputStack)) {

                if (cookingTotalTime < 100){
                    cookingTotalTime ++;
                    spawnParticle();
                }else {
                    for (int i = 0; i < recipe.getIngredients().size(); i++) {
                        if (inventory.getStackInSlot(i).hasCraftingRemainingItem()){
                            ejectIngredientRemainder(inventory.getStackInSlot(i).getCraftingRemainingItem());
                        }
                        inventory.extractItem(i, 1, false);
                    }
                    if (!recipe.getContainer().isEmpty() && recipe.getContainer().is(this.inventory.getStackInSlot(CONTAINER_SLOT).getItem())){
                        inventory.extractItem(CONTAINER_SLOT,1,false);
                    }
                    if (!compatibility) {
                        if (outputStack.isEmpty()) {
                            inventory.setStackInSlot(OUTPUT_SLOT, resultItem);
                        } else if (outputStack.getItem() == resultItem.getItem()) {
                            outputStack.grow(resultItem.getCount());
                        }
                    }else {
                        ejectionResultItem(resultItem);
                    }
                    cookingTotalTime = 0;
                    setChanged();
                    level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), 3);

                }
            }
        }
    }

    protected void ejectionResultItem(ItemStack pStack){
        Level level1 = this.level;
        BlockPos pos = this.getBlockPos();
        Direction facing = level1.getBlockState(pos).getValue(BlenderBlock.FACING).getOpposite();
        ItemUtil.spawnItemEntity(level1,pStack,pos.getX() + 0.5,pos.getY(),pos.getZ() + 0.5,
                new Vec3(facing.getStepX() * 0.5,0.05,facing.getStepZ() * 0.5));
    }

    protected void ejectIngredientRemainder(ItemStack remainderStack) {
        double x = worldPosition.getX() + 0.5;
        double y = worldPosition.getY() + 0.5;
        double z = worldPosition.getZ() + 0.5;
        ItemUtil.spawnItemEntity(this.level,remainderStack,x,y,z,new Vec3(0.0,0.0,0.0));
    }

    private boolean isContainer(){
        Optional<BlenderRecipe> recipeOptional = getCurrentRecipe();
        if (recipeOptional.isPresent()){
            BlenderRecipe recipe = recipeOptional.get();
            if (recipe.getContainer().is(this.inventory.getStackInSlot(CONTAINER_SLOT).getItem())){
                return true;
            }
            return recipe.getContainer().isEmpty();
        }
        return false;
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

    private void spawnParticle(){
        BlockPos pos = this.getBlockPos();
        Level pLevel = this.level;
        if (pLevel instanceof ServerLevel serverLevel){
            BlockEntity blockEntity = pLevel.getBlockEntity(pos);
            if (blockEntity instanceof BlenderBlockEntity blenderBlockEntity){
                for (int i = 0; i < blenderBlockEntity.inventory.getSlots(); i++) {
                    ItemStack tempStack = blenderBlockEntity.inventory.getStackInSlot(i);
                    if (!tempStack.isEmpty()) {
                        serverLevel.sendParticles(new ItemParticleOption(ParticleTypes.ITEM, tempStack),
                                pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, 1, 0.0, 0.0, 0.0, 0.075);
                        if (Math.random() < 0.25){
                            break;
                        }
                    }else {
                        break;
                    }
                }
            }
        }
    }

    public boolean canPlaceItem(int pIndex, ItemStack stack) {
            for (int i = 0; i < filtrationinventory.getSlots(); ++i) {
                ItemStack filtrationStack = filtrationinventory.getStackInSlot(i);
                if (stack.is(filtrationStack.getItem()) && inventory.getStackInSlot(i).isEmpty()) {
                    return true;
                }
            }
        return false;
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("container.blender");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int pContainerId, Inventory pPlayerInventory, Player pPlayer) {
        return new BlenderMenu(pContainerId, pPlayerInventory, this);
    }
}
