package com.renyigesai.bakeries.api.creative_mode_tab;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class StackingCreativeModeTab extends CreativeModeTab {
    public List<TabEntry> tabs = new ArrayList<>();
    protected StackingCreativeModeTab(Builder builder) {
        super(builder);
    }

    public final StackingCreativeModeTab add(TabEntry... tabs){
        this.tabs.addAll(Arrays.asList(tabs));
        return this;
    }

    @Override
    public Collection<ItemStack> getDisplayItems() {
        if (!tabs.isEmpty()){
            CreativeModeTab creativeModeTab = tabs.get(0).tab.get();
            return creativeModeTab.getDisplayItems();
        }
        return super.getDisplayItems();
    }

    @SafeVarargs
    public static Builder builder(TabEntry... tabs) {
        return new Builder(Row.TOP, 0,tabs);
    }

    public static Builder builder() {
        return new Builder(Row.TOP, 0);
    }

    public static class Builder extends CreativeModeTab.Builder {

        private static final DisplayItemsGenerator DEFAULT_FILLING = (p_270422_, output) -> {output.accept(Items.STICK);};

        /**在构造器中将tabFactory设为StackingCreativeModeTab并填充displayItemsGenerator*/

        public Builder(Row row, int column, TabEntry... tabs) {
            super(row, column);
            this.tabFactory = builder -> new StackingCreativeModeTab((Builder) builder).add(tabs);
            this.displayItems(DEFAULT_FILLING);
        }

        public Builder(Row row, int column) {
            super(row, column);
            this.tabFactory = builder -> new StackingCreativeModeTab((Builder) builder);
            this.displayItems(DEFAULT_FILLING);
        }
    }

}
