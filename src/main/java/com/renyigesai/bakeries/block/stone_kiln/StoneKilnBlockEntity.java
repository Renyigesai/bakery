package com.renyigesai.bakeries.block.stone_kiln;

import com.renyigesai.bakeries.init.BakeriesBlocks;
import com.renyigesai.bakeries.item.CustomPizzaItem;
import com.renyigesai.bakeries.recipe.StoneKilnRecipe;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.util.Mth;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.SmokingRecipe;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Optional;

public class StoneKilnBlockEntity extends BlockEntity {
    private final ItemStackHandler inventory = new ItemStackHandler(1){
        @Override
        protected int getStackLimit(int slot, @NotNull ItemStack stack) {
            return 1;
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
    private int cookingTime = 0;
    private int maxCookingTime = 0;
    private int stageCookingTime = 0;
    private int maxStageCookingTime = 0;
    public int turnOver = 0;
    public boolean isTurnOver;
    private int maxTurnOver;
    private float size = 0.0f;
    private int nextStage = 0;
    public float progress;
    public float progressOld;
    private StoneKilnBlockEntity.AnimationStatus animationStatus = AnimationStatus.STOP;

    public StoneKilnBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(BakeriesBlocks.STONE_KILN_ENTITY.get(), pPos, pBlockState);
    }

    public void setAnimationStatus(AnimationStatus animationStatus) {
        this.animationStatus = animationStatus;
    }

    public int getContainerSize() {
        return 1;
    }

    public boolean isEmpty() {
        return this.inventory.getStackInSlot(0).isEmpty();
    }

    public ItemStack getItem(int pSlot) {
        return this.inventory.getStackInSlot(pSlot);
    }

    public ItemStack removeItem(int pSlot, int pAmount) {
        return inventory.extractItem(pSlot,pAmount,false);
    }


    public void setItem(int pSlot, ItemStack pStack) {
        this.inventory.setStackInSlot(pSlot,pStack);
    }

    public ItemStackHandler getInventory() {
        return inventory;
    }

    public float getSize() {
        return size;
    }

    public int getCookingTime() {
        return cookingTime;
    }

    public int getMaxCookingTime() {
        return maxCookingTime;
    }

    public int getStageCookingTime() {
        return stageCookingTime;
    }

    public int getMaxStageCookingTime() {
        return maxStageCookingTime;
    }

    public int getTurnOver() {
        return turnOver;
    }

    public int getMaxTurnOver() {
        return maxTurnOver;
    }

    public int getNextStage() {
        return nextStage;
    }

    public float getProgress(float pPartialTicks) {
        return Mth.lerp(pPartialTicks, this.progressOld, this.progress);
    }

    public boolean isTurnOver(){
        return this.isTurnOver;
    }

    public boolean addItem(ItemStack stack){
        if (isEmpty()){
            this.inventory.setStackInSlot(0,stack);
            this.size = 0f;
            initialize();
            this.setChanged();
            level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), 3);
            return true;
        }else {
            return false;
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
        tag.putIntArray("CookingTime", new int[]{cookingTime,maxCookingTime});
        tag.putIntArray("StageCookingTime", new int[]{stageCookingTime,maxStageCookingTime});
        tag.putIntArray("TurnOver", new int[]{turnOver,maxTurnOver});
        tag.putBoolean("IsTurnOver", isTurnOver);
        tag.putFloat("Size", size);

        return tag;
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        if (tag.contains("Inventory")) {
            inventory.deserializeNBT(tag.getCompound("Inventory"));
        }

        int[] cookingTimes = tag.getIntArray("CookingTime");
        cookingTime = cookingTimes[0];
        maxCookingTime = cookingTimes[1];

        int[] stageCookingTimes = tag.getIntArray("StageCookingTime");
        stageCookingTime = stageCookingTimes[0];
        maxStageCookingTime = stageCookingTimes[1];

        int[] turnOvers = tag.getIntArray("TurnOver");
        turnOver = turnOvers[0];
        maxTurnOver = turnOvers[1];

        isTurnOver = tag.getBoolean("IsTurnOver");
        size = tag.getFloat("Size");
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        tag.put("Inventory", inventory.serializeNBT());
        tag.putIntArray("CookingTime",new int[]{cookingTime,maxCookingTime});
        tag.putIntArray("StageCookingTime",new int[]{stageCookingTime,maxStageCookingTime});
        tag.putIntArray("TurnOver",new int[]{turnOver,maxTurnOver});
        tag.putBoolean("IsTurnOver", isTurnOver);
        tag.putFloat("Size", size);
    }

    public void initialize(){
        this.maxCookingTime = 0;
        this.maxStageCookingTime = 0;
        this.cookingTime = 0;
        this.stageCookingTime = 0;
        this.maxTurnOver = 0;
        this.isTurnOver = false;
        this.turnOver = 0;
        this.nextStage = 0;
    }

    public static void tick(Level level, BlockPos pos, BlockState state, StoneKilnBlockEntity blockEntity){
        if (!state.getValue(StoneKilnBlock.LIT)){
            return;
        }
        if (!blockEntity.isEmpty()){
            blockEntity.cookingTick();
            blockEntity.setChanged();
            if (!level.isClientSide) {
                level.sendBlockUpdated(pos, state, state, 3);
            }
        }
    }
    @Override
    public boolean triggerEvent(int pId, int pType) {
        if (pId == 1) {
            if (pType == 0) {
                this.animationStatus = AnimationStatus.TURN_OVER;
                doNeighborUpdates(this.getLevel(), this.worldPosition, this.getBlockState());
            }
            return true;
        } else {
            return super.triggerEvent(pId, pType);
        }
    }

    public static void clientTick(Level level, BlockPos pos, BlockState state, StoneKilnBlockEntity blockEntity){
        blockEntity.progressOld = blockEntity.progress;
        switch (blockEntity.animationStatus){
            case TURN_OVER:
                blockEntity.progress += 0.1F;
                if (blockEntity.progress >= 1.0F) {
                    blockEntity.progress = 1.0F;
                }
                break;
        }
    }

    private static void doNeighborUpdates(Level pLevel, BlockPos pPos, BlockState pState) {
        pState.updateNeighbourShapes(pLevel, pPos, 3);
    }

    public Optional<StoneKilnRecipe> getCurrentRecipe() {
        SimpleContainer inventory = new SimpleContainer(1);
        inventory.setItem(0, this.inventory.getStackInSlot(0));
        if (level == null) {
            return Optional.empty();
        }
        return level.getRecipeManager()
                .getRecipeFor(StoneKilnRecipe.Type.INSTANCE, inventory, level);
    }

    public void startTurnOver(){
        this.turnOver ++;
        this.progress = 0.0f;
        this.isTurnOver = false;
        this.level.blockEvent(worldPosition,this.getBlockState().getBlock(), 1,0);
    }

    public Optional<SmokingRecipe> getSmokerRecipe() {
        SimpleContainer inventory = new SimpleContainer(1);
        inventory.setItem(0, this.inventory.getStackInSlot(0));
        if (level == null) {
            return Optional.empty();
        }
        return level.getRecipeManager()
                .getRecipeFor(RecipeType.SMOKING, inventory, level);
    }

    private void cookingTick(){
        Optional<StoneKilnRecipe> currentRecipe = getCurrentRecipe();/*优先检测石窑炉自己的配方*/
        if (currentRecipe.isPresent()){
            StoneKilnRecipe stoneKilnRecipe = currentRecipe.get();
            if (this.maxCookingTime == 0) {
                this.maxCookingTime = getAllTime(stoneKilnRecipe.getTime());
            }
            if (this.maxTurnOver == 0) {
                this.maxTurnOver = stoneKilnRecipe.getTime().length-1;
            }
            stoneKilnCookingTick(stoneKilnRecipe);
            return;
        }
        Optional<SmokingRecipe> smokerRecipe = getSmokerRecipe();/*如果找不到,尝试查找是否属于篝火配方*/
        if (smokerRecipe.isPresent()){
            SmokingRecipe smokingRecipe = smokerRecipe.get();
            if (this.maxCookingTime == 0) {
                this.maxCookingTime = smokingRecipe.getCookingTime();
            }
            smokingCookingTick(smokingRecipe);
            return;
        }
        initialize();
    }

    private void smokingCookingTick(SmokingRecipe recipe){
        int time = recipe.getCookingTime();
        boolean flag = false;
        this.cookingTime ++;
        if (this.cookingTime >= time){
            flag = true;
        }
        if (flag){
            this.inventory.setStackInSlot(0,getCookingItem(recipe.getResultItem(null),time));
        }
    }

    private void stoneKilnCookingTick(StoneKilnRecipe recipe){
        boolean flag = false;
        boolean flags = false;
        int[] times = recipe.getTime();/*获取时间阶段*/
        if (times.length == 1){/*如果只有一个时间阶段简单处理烹饪*/
            if (this.cookingTime < times[0]){
                this.cookingTime ++;
                this.size += (float) (0.075/times[0]);
            }else {
                flag = true;
            }
        }else {
            flags = true;
        }
        if (flags){/*处理多个时间阶段的烹饪逻辑*/
            if (this.cookingTime >= this.maxCookingTime){
                flag = true;
            }else {
                this.maxStageCookingTime = times[nextStage];
                if (this.turnOver == this.nextStage){
                    if (this.stageCookingTime < this.maxStageCookingTime){
                        this.stageCookingTime++;
                        this.size += (float) (0.075/this.maxStageCookingTime/times.length);
                    }else {
                        this.cookingTime += stageCookingTime;
                        this.stageCookingTime = 0;
                        this.maxStageCookingTime = 0;
                        if (this.nextStage + 1 < times.length){
                            this.nextStage ++;
                        }
                    }
                }else {
                    this.isTurnOver = true;/*翻面标记,提醒玩家需要翻面后烹饪才会继续*/
                }
            }
        }
        if (flag){
            ItemStack resultItem = recipe.getResultItem(null);
            if (this.inventory.getStackInSlot(0).getItem() instanceof CustomPizzaItem pizzaItem){
                CompoundTag tag = new CompoundTag();
                List<ItemStack> inventoryList = pizzaItem.getInventoryList(this.inventory.getStackInSlot(0));
                if (!inventoryList.isEmpty()){
                    ItemStackHandler handler = new ItemStackHandler(4);
                    for (int i = 0; i < inventoryList.size(); i++) {
                        handler.setStackInSlot(i,inventoryList.get(i));
                    }
                    tag.put("Inventory",handler.serializeNBT());
                    resultItem.setTag(tag);
                }
            }
            this.inventory.setStackInSlot(0,resultItem);
        }
    }

    public int getAllTime(int[] times){
        int time = 0;
        for (int i : times) {
            time += i;
        }
        return time;
    }

    private ItemStack getCookingItem(ItemStack resultItem,int time){
        if (this.cookingTime >= time + 200){
            return new ItemStack(Items.COAL);
        }else {
            return resultItem;
        }
    }

    public enum AnimationStatus {
        STOP,
        TURN_OVER
    }
}
