package net.weibai.bakeries.common.init;


import lombok.Getter;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.weibai.bakeries.BakeriesMod;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public class MSItems {
    @Getter
    private static final DeferredRegister.Items REGISTER = DeferredRegister.createItems(BakeriesMod.MODID);
    private MSItems(){}
    /**普通*/
    public static Rarity common(){return Rarity.COMMON;}
    /**罕见*/
    public static Rarity uncommon(){return Rarity.UNCOMMON;}
    /**稀有*/
    public static Rarity rare(){return Rarity.RARE;}
    /**史诗*/
    public static Rarity epic(){return Rarity.EPIC;}
    /**mod图标*/
    public static final DeferredItem<Item> MS_ICON = registerItem(
            "ms_icon",
            ()-> new Item(new Item.Properties()
                    .fireResistant()
                    .stacksTo(1)
                    .rarity(epic()))
    );


    private static DeferredItem<Item> registerBlock(Holder<Block> block) {
        return registerBlock(block, new Item.Properties());
    }
    private static DeferredItem<Item> registerBlock(String name ,Supplier<BlockItem> blockItem) {
        return registerItem(name, blockItem);
    }
    public static DeferredItem<Item>  registerBlock(Holder<Block> block, final Supplier<? extends Block>... others) {
        return registerBlock(block.unwrapKey().orElseThrow().location().getPath(), ()->new BlockItem(block.value(), new Item.Properties()) {
            public void registerBlocks(Map<Block, Item> map, Item self) {
                super.registerBlocks(map, self);

                for(Supplier<? extends Block> b : others) {
                    map.put(b.get(), self);
                }

            }

            @SuppressWarnings("removal")
            @Override
            public void removeFromBlockToItemMap(Map<Block, Item> map, Item self) {
                super.removeFromBlockToItemMap(map, self);

                for(Supplier<? extends Block> b : others) {
                    map.remove(b.get());
                }

            }
        });
    }
    private static DeferredItem<Item> itemRarity(String name, Rarity rarity){
        return REGISTER.register(name, ()-> new Item(new Item.Properties().rarity(rarity)));
    }
    private static DeferredItem<Item> registerBlock(Holder<Block> block, Item.Properties properties) {
        return registerBlock(block.unwrapKey().orElseThrow().location().getPath(), block::value, properties);
    }
    private static DeferredItem<Item> registerItemNameBlockItem(String name, Supplier<? extends Block> block) {
        return registerItem(name, ()-> new ItemNameBlockItem(block.get(), new Item.Properties()));
    }
    private static DeferredItem<Item> registerItemNameBlockItemHasAppendHoverText(String name, Supplier<? extends Block> block, Supplier<List<Component>> tooltip) {
        return registerItem(name, ()-> new ItemNameBlockItem(block.get(), new Item.Properties()){
            @Override
            public void appendHoverText(@NotNull ItemStack pStack, @NotNull Item.@NotNull TooltipContext pContext, @NotNull List<Component> pTooltip, @NotNull TooltipFlag pFlag) {
                super.appendHoverText(pStack, pContext, pTooltip, pFlag);
                pTooltip.addAll(tooltip.get());
            }
        });
    }

    private static DeferredItem<Item> registerBlock(String name, Supplier<? extends Block> block, Item.Properties properties) {
        return registerItem(name, ()-> new BlockItem(block.get(), properties));
    }
    private static DeferredItem<Item> registerItem(String name, Supplier<? extends Item> item){
        return REGISTER.register(name, item);
    }

}
