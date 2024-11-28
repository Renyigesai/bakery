package com.renyigesai.bakeries.inventory.dough_crafting_table;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;

import com.renyigesai.bakeries.init.BakeriesBlocks;
import com.renyigesai.bakeries.init.BakeriesItemTag;
import com.renyigesai.bakeries.init.BakeriesMenuType;
import com.renyigesai.bakeries.recipe.dough_crafting_table.DoughCraftingRecipe;
import lombok.Getter;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BannerPatternTags;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.StonecutterRecipe;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BannerPattern;

import java.util.List;

public class DoughCraftingTableMenu extends AbstractContainerMenu {
   private final Level level;
   private final ContainerLevelAccess access;
   private final DataSlot selectedRecipeIndex = DataSlot.standalone();
   private List<DoughCraftingRecipe> recipes = Lists.newArrayList();
   Runnable slotUpdateListener = () -> {
   };
   @Getter
   final Slot mainSlot;
   private ItemStack mainSlot_input = ItemStack.EMPTY;
   @Getter
   final Slot flavoring;
   private ItemStack flavoring_input = ItemStack.EMPTY;
   @Getter
   final Slot additive;
   private ItemStack additive_input = ItemStack.EMPTY;
   @Getter
   final Slot additive_food;
   private ItemStack additive_food_input = ItemStack.EMPTY;
   @Getter
   final Slot resultSlot;
   long lastSoundTime;
   private final Container inputContainer = new SimpleContainer(4) {
      /**
       * For block entities, ensures the chunk containing the block entity is saved to disk later - the game won't think
       * it hasn't changed and skip it.
       */
      public void setChanged() {
         super.setChanged();
         DoughCraftingTableMenu.this.slotsChanged(this);
         DoughCraftingTableMenu.this.slotUpdateListener.run();
      }
   };
   final ResultContainer resultContainer = new ResultContainer();

   public DoughCraftingTableMenu(int pContainerId, Inventory pPlayerInventory) {
      this(pContainerId, pPlayerInventory, ContainerLevelAccess.NULL);
   }

   public DoughCraftingTableMenu(int pContainerId, Inventory pPlayerInventory, final ContainerLevelAccess pAccess) {
      super(BakeriesMenuType.DOUGH_CRAFTING_TABLE_MENU.get(), pContainerId);
      this.access = pAccess;
      this.level = pPlayerInventory.player.level();
      this.mainSlot = this.addSlot(new Slot(this.inputContainer, 0, 20, 51));
      this.flavoring = this.addSlot(new Slot(this.inputContainer, 1, 11, 33));
      this.additive = this.addSlot(new Slot(this.inputContainer, 2, 20, 15));
      this.additive_food = this.addSlot(new Slot(this.inputContainer, 3, 29, 33));
      this.resultSlot = this.addSlot(new Slot(this.resultContainer, 0, 144, 33) {
         public boolean mayPlace(ItemStack p_40362_) {
            return false;
         }

         public void onTake(Player player, ItemStack itemStack) {
            DoughCraftingTableMenu.this.setupResultSlot();

            pAccess.execute((p_40364_, p_40365_) -> {
               long l = p_40364_.getGameTime();
               if (DoughCraftingTableMenu.this.lastSoundTime != l) {
                  p_40364_.playSound((Player)null, p_40365_, SoundEvents.UI_STONECUTTER_TAKE_RESULT, SoundSource.BLOCKS, 1.0F, 1.0F);
                  DoughCraftingTableMenu.this.lastSoundTime = l;
               }

            });
            super.onTake(player, itemStack);
         }


      });

      for(int i = 0; i < 3; ++i) {
         for(int j = 0; j < 9; ++j) {
            this.addSlot(new Slot(pPlayerInventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
         }
      }

      for(int k = 0; k < 9; ++k) {
         this.addSlot(new Slot(pPlayerInventory, k, 8 + k * 18, 142));
      }
      this.addDataSlot(this.selectedRecipeIndex);
   }

   /**
    * Determines whether supplied player can use this container
    */
   public boolean stillValid(Player pPlayer) {
      return stillValid(this.access, pPlayer, BakeriesBlocks.DOUGH_CRAFTING_TABLE.get());
   }

   /**
    * Returns the index of the selected recipe.
    */
   public int getSelectedRecipeIndex() {
      return this.selectedRecipeIndex.get();
   }

   public List<DoughCraftingRecipe> getRecipes() {
      return this.recipes;
   }

   public int getNumRecipes() {
      return this.recipes.size();
   }
   /**
    * Handles the given Button-click on the server, currently only used by enchanting. Name is for legacy.
    */
   public boolean clickMenuButton(Player pPlayer, int pId) {
      if (this.isValidRecipeIndex(pId)) {
         this.selectedRecipeIndex.set(pId);
         this.setupResultSlot();
      }

      return true;
   }

   private boolean isValidRecipeIndex(int pRecipeIndex) {
      return pRecipeIndex >= 0 && pRecipeIndex < this.recipes.size();
   }

   /**
    * Callback for when the crafting matrix is changed.
    */
   public void slotsChanged(Container pInventory) {
      ItemStack itemstack = this.mainSlot.getItem();
      if (!itemstack.is(this.mainSlot_input.getItem())) {
         this.mainSlot_input = itemstack.copy();
         this.setupRecipeList(pInventory, itemstack);
      }
      ItemStack itemstack1 = this.flavoring.getItem();
      if (!itemstack1.is(this.flavoring_input.getItem())) {
         this.flavoring_input = itemstack1.copy();
         this.setupRecipeList(pInventory, itemstack1);
      }
      ItemStack itemstack2 = this.additive.getItem();
      if (!itemstack2.is(this.additive_input.getItem())) {
         this.additive_input = itemstack2.copy();
         this.setupRecipeList(pInventory, itemstack2);
      }
      ItemStack itemstack3 = this.additive_food.getItem();
      if (!itemstack3.is(this.additive_food_input.getItem())) {
         this.additive_food_input = itemstack3.copy();
         this.setupRecipeList(pInventory, itemstack3);
      }

   }

   private void setupRecipeList(Container pContainer, ItemStack pStack) {
      this.recipes.clear();
      this.selectedRecipeIndex.set(-1);
      this.resultSlot.set(ItemStack.EMPTY);
      if (!pStack.isEmpty()) {
         this.recipes = this.level.getRecipeManager().getRecipesFor(DoughCraftingRecipe.Type.INSTANCE, pContainer, this.level);
      }

   }

   void setupResultSlot() {
      if (!this.recipes.isEmpty() && this.isValidRecipeIndex(this.selectedRecipeIndex.get())) {
         DoughCraftingRecipe doughCraftingRecipe = this.recipes.get(this.selectedRecipeIndex.get());
         ItemStack itemstack = doughCraftingRecipe.assemble(this.resultContainer, this.level.registryAccess());
         if (doughCraftingRecipe.matches(inputContainer, this.level)) {
            this.resultSlot.set(itemstack);
         } else {
            this.resultSlot.set(ItemStack.EMPTY);
         }
      } else {
         this.resultSlot.set(ItemStack.EMPTY);
      }

      this.broadcastChanges();
   }
   public boolean hasInputItem() {
      if (!this.recipes.isEmpty() && this.isValidRecipeIndex(this.selectedRecipeIndex.get())) {
         DoughCraftingRecipe doughCraftingRecipe = this.recipes.get(this.selectedRecipeIndex.get());
         return doughCraftingRecipe.matches(inputContainer, this.level);
      }
      return false;
   }
   boolean canRecipe(DoughCraftingRecipe recipe){
      ItemStack mainIngredient = this.mainSlot.getItem();
      ItemStack flavoring = this.flavoring.getItem();
      ItemStack additive = this.additive.getItem();
      ItemStack additiveFood = this.additive_food.getItem();
      boolean mainIngredientValid = recipe.getIngredients().get(0).test(mainIngredient);
      boolean flavoringValid = recipe.getFlavoringItem(null).equals(additive);
      boolean additiveValid = recipe.getAdditiveItem(null).equals(flavoring);
      boolean additiveFoodValid = recipe.getAdditiveFoodItem(null).equals(additiveFood);
      return recipe.matches(inputContainer, this.level);
   }
   public MenuType<?> getType() {
      return BakeriesMenuType.DOUGH_CRAFTING_TABLE_MENU.get();
   }

   public void registerUpdateListener(Runnable pListener) {
      this.slotUpdateListener = pListener;
   }

   /**
    * Called to determine if the current slot is valid for the stack merging (double-click) code. The stack passed in is
    * null for the initial slot that was double-clicked.
    */
   public boolean canTakeItemForPickAll(ItemStack pStack, Slot pSlot) {
      return pSlot.container != this.resultContainer && super.canTakeItemForPickAll(pStack, pSlot);
   }

   /**
    * Handle when the stack in slot {@code index} is shift-clicked. Normally this moves the stack between the player
    * inventory and the other inventory(s).
    */
   public ItemStack quickMoveStack(Player pPlayer, int pIndex) {
      ItemStack itemstack = ItemStack.EMPTY;
      Slot slot = this.slots.get(pIndex);
      if (slot != null && slot.hasItem()) {
         ItemStack itemstack1 = slot.getItem();
         itemstack = itemstack1.copy();
         if (pIndex == this.resultSlot.index) {
            if (!this.moveItemStackTo(itemstack1, 4, 40, true)) {
               return ItemStack.EMPTY;
            }
            slot.onQuickCraft(itemstack1, itemstack);
         } else if (pIndex != this.mainSlot.index && pIndex != this.flavoring.index && pIndex != this.additive.index && pIndex != this.additive_food.index) {
            if (itemstack1.is(BakeriesItemTag.MAIN_FOOD)) {
               if (!this.moveItemStackTo(itemstack1, this.mainSlot.index, this.mainSlot.index + 1, false)) {
                  return ItemStack.EMPTY;
               }
            } else if (itemstack1.is(BakeriesItemTag.FLAVORING)) {
               if (!this.moveItemStackTo(itemstack1, this.flavoring.index, this.flavoring.index + 1, false)) {
                  return ItemStack.EMPTY;
               }
            } else if (itemstack1.is(BakeriesItemTag.ADDITIVE)) {
               if (!this.moveItemStackTo(itemstack1, this.additive.index, this.additive.index + 1, false)) {
                  return ItemStack.EMPTY;
               }
            } else if (itemstack1.is(BakeriesItemTag.ADDITIVE_FOOD)) {
               if (!this.moveItemStackTo(itemstack1, this.additive_food.index, this.additive_food.index + 1, false)) {
                  return ItemStack.EMPTY;
               }
            } else if (pIndex >= 4 && pIndex < 31) {
               if (!this.moveItemStackTo(itemstack1, 31, 40, false)) {
                  return ItemStack.EMPTY;
               }
            } else if (pIndex >= 31 && pIndex < 40 && !this.moveItemStackTo(itemstack1, 4, 31, false)) {
               return ItemStack.EMPTY;
            }
         } else if (!this.moveItemStackTo(itemstack1, 4, 40, false)) {
            return ItemStack.EMPTY;
         }

         if (itemstack1.isEmpty()) {
            slot.setByPlayer(ItemStack.EMPTY);
         } else {
            slot.setChanged();
         }

         if (itemstack1.getCount() == itemstack.getCount()) {
            return ItemStack.EMPTY;
         }

         slot.onTake(pPlayer, itemstack1);
      }

      return itemstack;
   }

   /**
    * Called when the container is closed.
    */
   public void removed(Player pPlayer) {
      super.removed(pPlayer);
      this.access.execute((p_39871_, p_39872_) -> {
         this.clearContainer(pPlayer, this.inputContainer);
      });
   }
}
