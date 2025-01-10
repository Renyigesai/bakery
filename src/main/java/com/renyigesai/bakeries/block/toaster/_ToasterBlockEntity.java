//package com.renyigesai.bakeries.block.toaster;
//
//import com.renyigesai.bakeries.api.Shortcuts;
//import com.renyigesai.bakeries.init.BakeriesBlocks;
//import net.minecraft.core.BlockPos;
//import net.minecraft.nbt.CompoundTag;
//import net.minecraft.network.Connection;
//import net.minecraft.network.protocol.Packet;
//import net.minecraft.network.protocol.game.ClientGamePacketListener;
//import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
//import net.minecraft.resources.ResourceLocation;
//import net.minecraft.world.SimpleContainer;
//import net.minecraft.world.entity.player.Player;
//import net.minecraft.world.item.ItemStack;
//import net.minecraft.world.item.crafting.CampfireCookingRecipe;
//import net.minecraft.world.item.crafting.RecipeType;
//import net.minecraft.world.level.Level;
//import net.minecraft.world.level.block.entity.BlockEntity;
//import net.minecraft.world.level.block.state.BlockState;
//import net.minecraft.world.phys.Vec2;
//import net.minecraftforge.items.ItemStackHandler;
//import org.jetbrains.annotations.NotNull;
//import org.jetbrains.annotations.Nullable;
//
//import java.util.Optional;
//
//public class _ToasterBlockEntity extends BlockEntity {
//    private ItemStackHandler inventory = new ItemStackHandler(2){
//        @Override
//        protected int getStackLimit(int slot, @NotNull ItemStack stack) {
//            return 1;
//        }
//    };
//    private ResourceLocation[] lastRecipeIDs;
//    private static final int INVENTORY_SLOT_COUNT = 2;
//
//    public _ToasterBlockEntity(BlockPos pPos, BlockState pBlockState) {
//        super(BakeriesBlocks.TOASTER_ENTITY.get(), pPos, pBlockState);
//        lastRecipeIDs = new ResourceLocation[INVENTORY_SLOT_COUNT];
//    }
//
//    public void addItem(ItemStack item){
//        for (int i = 0; i < this.inventory.getSlots(); i++) {
//            ItemStack stack = this.inventory.getStackInSlot(i);
//            if (stack.isEmpty()){
//                this.inventory.setStackInSlot(i,item.split(1));
//                setChanged();
//                return;
//            }
//        }
//    }
//
//    public void getItem(Player player){
//        for (int i = 0; i < this.inventory.getSlots(); i++) {
//            ItemStack stack = this.inventory.getStackInSlot(i);
//            if (!stack.isEmpty()){
//                this.inventory.setStackInSlot(i,ItemStack.EMPTY.split(1));
//                Shortcuts.givePlayerItem(player,stack.getItem());
//                setChanged();
//                return;
//            }
//        }
//    }
//
//    public ItemStackHandler getInventory(){
//        return this.inventory;
//    }
//
//    public Vec2 getItemOffset(int i){
//        float x = 0.2F;
//        float y = 0.2F;
//        Vec2[] offset = new Vec2[]{
//                new Vec2(x,y),new Vec2(-x,y),
//                new Vec2(x,-y),new Vec2(-x,-y)
//        };
//        return offset[i];
//    }
//
//    @Nullable
//    @Override
//    public Packet<ClientGamePacketListener> getUpdatePacket() {
//        return ClientboundBlockEntityDataPacket.create(this);
//    }
//
//    @Override
//    public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt) {
//        this.load(pkt.getTag());
//    }
//
//    @Override
//    public void setChanged() {
//        super.setChanged();
//        if (this.level != null){
//            this.level.sendBlockUpdated(this.getBlockPos(),this.getBlockState(),this.getBlockState(),2);
//        }
//    }
//
//    private CompoundTag writeItems(CompoundTag compound) {
//        super.saveAdditional(compound);
//        compound.put("Inventory",this.inventory.serializeNBT());
////        compound.putIntArray("CookingTimes", cookingTimes);
//        return compound;
//    }
//
//    @Override
//    public CompoundTag getUpdateTag() {
//        return this.writeItems(new CompoundTag());
//    }
//
//    @Override
//    protected void saveAdditional(CompoundTag pTag) {
//        this.writeItems(pTag);
//    }
//
//    public static void updateBlock(_ToasterBlockEntity toasterBlockEntity) {
//        Level world = toasterBlockEntity.getLevel();
//        BlockPos pos = toasterBlockEntity.getBlockPos();
//        BlockState state = world.getBlockState(pos);
//        setChanged(world, pos, state);
//        world.sendBlockUpdated(pos, state, state, 3);
//    }
//
//    public static void cookingTick(Level level, BlockPos pos, BlockState state, _ToasterBlockEntity toaster) {
//        toaster.cookAndOutputItems(toaster);
//    }
//
//    private void cookAndOutputItems(_ToasterBlockEntity toaster){
//        boolean temp = false;
//        for (int i = 0; i < this.inventory.getSlots(); i++) {
//            ItemStack toasterStack = inventory.getStackInSlot(i);
//            if (!toasterStack.isEmpty()){
//                Optional<CampfireCookingRecipe> recipe = getCurrentRecipe(i);
//                if (recipe.isPresent()){
//                    ItemStack resultItemTemp = recipe.get().getResultItem(null);
//                    ItemStack resultItem = new ItemStack(resultItemTemp.getItem(),resultItemTemp.getCount());
//                    if (!resultItem.isEmpty()){
//                        System.out.println(resultItem);
//                        inventory.setStackInSlot(i,resultItem);
//                        temp = true;
//                    }
//                }
//            }
//        }
//        if (temp){
//            updateBlock(toaster);
//        }
//    }
//
//    public Optional<CampfireCookingRecipe> getCurrentRecipe(int slot) {
//        SimpleContainer inventory = new SimpleContainer(this.inventory.getSlots());
//        inventory.setItem(slot, this.inventory.getStackInSlot(slot));
//        return this.level.getRecipeManager().getRecipeFor(RecipeType.CAMPFIRE_COOKING, inventory, level);
//    }
//
////    public Optional<CampfireCookingRecipe> getMatchingRecipe(Container recipeWrapper, int slot) {
////        if (level == null) return Optional.empty();
////
////        if (lastRecipeIDs[slot] != null) {
////            Recipe<Container> recipe = ((RecipeManagerAccessor) level.getRecipeManager())
////                    .getRecipeMap(RecipeType.CAMPFIRE_COOKING)
////                    .get(lastRecipeIDs[slot]);
////            if (recipe instanceof CampfireCookingRecipe && recipe.matches(recipeWrapper, level)) {
////                return Optional.of((CampfireCookingRecipe) recipe);
////            }
////        }
////
////        return level.getRecipeManager().getRecipeFor(RecipeType.CAMPFIRE_COOKING, recipeWrapper, level);
////    }
//
//    @Override
//    public void load(CompoundTag pTag) {
//        super.load(pTag);
//        if (pTag.contains("Inventory")){
//            this.inventory.deserializeNBT(pTag.getCompound("Inventory"));
//        }else {
//            this.inventory.deserializeNBT(pTag);
//        }
//    }
//}
