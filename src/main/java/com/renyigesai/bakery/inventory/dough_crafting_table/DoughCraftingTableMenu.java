package com.renyigesai.bakery.inventory.dough_crafting_table;

import com.google.common.collect.Lists;
import com.renyigesai.bakery.init.BakeryBlocks;
import com.renyigesai.bakery.init.BakeryMenuType;
import com.renyigesai.bakery.recipe.dough_crafting_table.DoughCraftingRecipe;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.Arrays;
import java.util.List;

public class DoughCraftingTableMenu extends AbstractContainerMenu {
   public static final int MAIN_INGREDIENT_SLOT = 0;
   public static final int AUX_INGREDIENT_SLOTS_START = 1;
   public static final int AUX_INGREDIENT_SLOTS_END = 4;
   public static final int RESULT_SLOT = 5;
   private static final int INV_SLOT_START = 6;
   private static final int INV_SLOT_END = 33;
   private static final int USE_ROW_SLOT_START = 33;
   private static final int USE_ROW_SLOT_END = 42;
   private final ContainerLevelAccess access;
   private final DataSlot selectedRecipeIndex = DataSlot.standalone();
   private final Level level;
   private List<DoughCraftingRecipe> recipes = Lists.newArrayList();
   private ItemStack mainIngredient = ItemStack.EMPTY;
   private ItemStack[] auxIngredients = new ItemStack[4];
   long lastSoundTime;
   final Slot mainIngredientSlot;
   final Slot[] auxIngredientSlots;
   final Slot resultSlot;
   Runnable slotUpdateListener = () -> {
   };
   public final Container container = new SimpleContainer(5) {
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
      super(BakeryMenuType.DOUGH_CRAFTING_TABLE_MENU.get(), pContainerId); // 假设存在 MenuType.DOUGH_CRAFTING_TABLE
      this.access = pAccess;
      this.level = pPlayerInventory.player.level();

      this.mainIngredientSlot = this.addSlot(new Slot(this.container, 0, 22, 55));
      this.auxIngredientSlots = new Slot[4];
       for (int i = 0; i < 2; i++) {
           for (int j = 0; j < 2; j++) {
               this.auxIngredientSlots[i * 2 + j] = this.addSlot(new Slot(this.container, i * 2 + j + 1, 13 + j * 18, 17 + i * 18));
           }
       }
      this.resultSlot = this.addSlot(new Slot(this.resultContainer, 5, 154, 36) {
         public boolean mayPlace(ItemStack p_40362_) {
            return false;
         }

         public void onTake(Player p_150672_, ItemStack p_150673_) {
            p_150673_.onCraftedBy(p_150672_.level(), p_150672_, p_150673_.getCount());
            DoughCraftingTableMenu.this.resultContainer.awardUsedRecipes(p_150672_, this.getRelevantItems());
            ItemStack mainIngredient = DoughCraftingTableMenu.this.mainIngredientSlot.remove(1);
            for (int i = 0; i < 4; i++) {
               DoughCraftingTableMenu.this.auxIngredients[i] = DoughCraftingTableMenu.this.auxIngredientSlots[i].remove(1);
            }
            if (!mainIngredient.isEmpty()) {
               DoughCraftingTableMenu.this.setupResultSlot();
            }

            pAccess.execute((p_40364_, p_40365_) -> {
               long l = p_40364_.getGameTime();
               if (DoughCraftingTableMenu.this.lastSoundTime != l) {
                  p_40364_.playSound((Player)null, p_40365_, SoundEvents.UI_STONECUTTER_TAKE_RESULT, SoundSource.BLOCKS, 1.0F, 1.0F);
                  DoughCraftingTableMenu.this.lastSoundTime = l;
               }

            });
            super.onTake(p_150672_, p_150673_);
         }

         private List<ItemStack> getRelevantItems() {
            List<ItemStack> items = Lists.newArrayList();
            items.add(DoughCraftingTableMenu.this.mainIngredientSlot.getItem());
            for (int i = 0; i < 4; i++) {
               items.add(DoughCraftingTableMenu.this.auxIngredientSlots[i].getItem());
            }
            return items;
         }
      });

       for (int si = 0; si < 3; ++si)
           for (int sj = 0; sj < 9; ++sj)
               this.addSlot(new Slot(pPlayerInventory, sj + (si + 1) * 9, 10 + sj * 18, 86 + si * 18));
       for (int si = 0; si < 9; ++si)
           this.addSlot(new Slot(pPlayerInventory, si, 10 + si * 18, 144));

      this.addDataSlot(this.selectedRecipeIndex);
   }
public List<DoughCraftingRecipe> getRecipes() {
   for (DoughCraftingRecipe recipe : this.level.getRecipeManager().getAllRecipesFor(DoughCraftingRecipe.Type.INSTANCE)) {
      if (recipe.hasMainIngredient(this.mainIngredient)) {
         this.recipes.add(recipe);
      }
   }
    System.out.println("Number of recipes: " + this.recipes.size());
    return this.recipes;
}

   public int getSelectedRecipeIndex() {
      return this.selectedRecipeIndex.get();
   }

    public int getNumRecipes() {
      return this.recipes.size();
   }

   public boolean hasMainIngredient() {
      return this.mainIngredientSlot.hasItem();
   }

   public boolean hasAuxIngredients() {
      for (int i = 0; i < 4; i++) {
         if (this.auxIngredientSlots[i].hasItem()) {
            return true;
         }
      }
      return false;
   }

   public boolean stillValid(Player pPlayer) {
      return stillValid(this.access, pPlayer, BakeryBlocks.DOUGH_CRAFTING_TABLE.get()); // 假设使用 Crafting Table
   }
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
   public void slotsChanged(Container pInventory) {
      ItemStack mainIngredient = this.mainIngredientSlot.getItem();
      for (int i = 0; i < 4; i++) {
         this.auxIngredients[i] = this.auxIngredientSlots[i].getItem();
      }
      if (!mainIngredient.is(this.mainIngredient.getItem()) || !Arrays.equals(this.auxIngredients, this.auxIngredients)) {
         this.mainIngredient = mainIngredient.copy();
         for (int i = 0; i < 4; i++) {
            this.auxIngredients[i] = this.auxIngredientSlots[i].getItem().copy();
         }
         this.setupRecipeList(pInventory, mainIngredient, this.auxIngredients);
      }

   }

   private void setupRecipeList(Container pContainer, ItemStack mainIngredient, ItemStack[] auxIngredients) {
      this.recipes.clear();
      this.selectedRecipeIndex.set(-1);
      this.resultSlot.set(ItemStack.EMPTY);
      if (!mainIngredient.isEmpty()) {
         this.recipes.addAll(this.level.getRecipeManager().getRecipesFor(DoughCraftingRecipe.Type.INSTANCE, pContainer, this.level));
         for (int i = 0; i < 4; i++) {
            if (auxIngredients[i] != null && !auxIngredients[i].isEmpty()) {
               this.recipes.addAll(this.level.getRecipeManager().getRecipesFor(DoughCraftingRecipe.Type.INSTANCE, pContainer, this.level));
            }
         }
      }

   }

   void setupResultSlot() {
      if (!this.recipes.isEmpty() && this.isValidRecipeIndex(this.selectedRecipeIndex.get())) {
         DoughCraftingRecipe doughCraftingRecipe = this.recipes.get(this.selectedRecipeIndex.get());
         ItemStack itemstack = doughCraftingRecipe.assemble(this.container, this.level.registryAccess());
         if (itemstack.isItemEnabled(this.level.enabledFeatures())) {
            this.resultContainer.setRecipeUsed(doughCraftingRecipe);
            this.resultSlot.set(itemstack);
         } else {
            this.resultSlot.set(ItemStack.EMPTY);
         }
      } else {
         this.resultSlot.set(ItemStack.EMPTY);
      }

      this.broadcastChanges();
   }

   public MenuType<?> getType() {
      return BakeryMenuType.DOUGH_CRAFTING_TABLE_MENU.get(); // 假设存在 MenuType.DOUGH_CRAFTING_TABLE
   }
   public void registerUpdateListener(Runnable pListener) {
      this.slotUpdateListener = pListener;
   }
   public boolean canTakeItemForPickAll(ItemStack pStack, Slot pSlot) {
      return pSlot.container != this.resultContainer && super.canTakeItemForPickAll(pStack, pSlot);
   }
   public ItemStack quickMoveStack(Player pPlayer, int pIndex) {
      ItemStack itemstack = ItemStack.EMPTY;
      Slot slot = this.slots.get(pIndex);
      if (slot != null && slot.hasItem()) {
         ItemStack itemstack1 = slot.getItem();
         Item item = itemstack1.getItem();
         itemstack = itemstack1.copy();
         if (pIndex == 5) {
            item.onCraftedBy(itemstack1, pPlayer.level(), pPlayer);
            if (!this.moveItemStackTo(itemstack1, 6, 42, true)) {
               return ItemStack.EMPTY;
            }

            slot.onQuickCraft(itemstack1, itemstack);
         } else if (pIndex >= 0 && pIndex < 5) {
            if (!this.moveItemStackTo(itemstack1, 6, 42, false)) {
               return ItemStack.EMPTY;
            }
         } else if (this.level.getRecipeManager().getRecipeFor(DoughCraftingRecipe.Type.INSTANCE, new SimpleContainer(itemstack1), this.level).isPresent()) {
            if (!this.moveItemStackTo(itemstack1, 0, 5, false)) {
               return ItemStack.EMPTY;
            }
         } else if (pIndex >= 6 && pIndex < 33) {
            if (!this.moveItemStackTo(itemstack1, 33, 42, false)) {
               return ItemStack.EMPTY;
            }
         } else if (pIndex >= 33 && pIndex < 42 && !this.moveItemStackTo(itemstack1, 6, 33, false)) {
            return ItemStack.EMPTY;
         }

         if (itemstack1.isEmpty()) {
            slot.setByPlayer(ItemStack.EMPTY);
         }

         slot.setChanged();
         if (itemstack1.getCount() == itemstack.getCount()) {
            return ItemStack.EMPTY;
         }

         slot.onTake(pPlayer, itemstack1);
         this.broadcastChanges();
      }

      return itemstack;
   }

   public void removed(Player pPlayer) {
      super.removed(pPlayer);
      this.resultContainer.removeItemNoUpdate(5);
      this.access.execute((p_40313_, p_40314_) -> {
         this.clearContainer(pPlayer, this.container);
      });
   }
}
