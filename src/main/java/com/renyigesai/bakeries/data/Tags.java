package com.renyigesai.bakeries.data;

import com.renyigesai.bakeries.BakeriesMod;
import com.renyigesai.bakeries.common.init.BakeriesBlocks;
import com.renyigesai.bakeries.common.init.BakeriesItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import javax.annotation.Nullable;
import java.util.concurrent.CompletableFuture;

public class Tags {

    public static void builder(net.minecraft.data.DataGenerator generator,PackOutput output,CompletableFuture<HolderLookup.Provider> provider,ExistingFileHelper existingFileHelper,boolean run){
        BlockTag blockTag = new BlockTag(output, provider, BakeriesMod.MODID, existingFileHelper);
        generator.addProvider(run,blockTag);
        generator.addProvider(run,new ItemTag(output,provider,blockTag.contentsGetter()));
    }

    private static class ItemTag extends ItemTagsProvider {
        private ItemTag(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, CompletableFuture<TagLookup<Block>> blockTags) {
            super(output, lookupProvider, blockTags);
        }

        @Override
        protected void addTags(HolderLookup.Provider provider) {
            tag(ItemTags.create(ResourceLocation.fromNamespaceAndPath("c","salt"))).add(BakeriesItems.SALT.get());
            tag(ItemTags.create(ResourceLocation.fromNamespaceAndPath("c","doughs"))).add(BakeriesItems.SWEET_DOUGH.get(), BakeriesItems.SALTED_DOUGH.get(), BakeriesItems.WHOLE_WHEAT_DOUGH.get());
            tag(ItemTags.create(ResourceLocation.fromNamespaceAndPath("c", "doughs/wheat"))).add(BakeriesItems.WHOLE_WHEAT_DOUGH.get());
            tag(ItemTags.create(ResourceLocation.fromNamespaceAndPath("c", "foods/milk"))).add(BakeriesItems.BOTTLE_MILK.get());
            tag(ItemTags.create(ResourceLocation.fromNamespaceAndPath("c", "flours"))).add(BakeriesItems.WHOLE_WHEAT_FLOUR.get(), BakeriesItems.FLOUR.get());
            tag(ItemTags.create(ResourceLocation.fromNamespaceAndPath("c", "flours/wheat"))).add(BakeriesItems.WHOLE_WHEAT_FLOUR.get());
            tag(ItemTags.create(ResourceLocation.fromNamespaceAndPath("c", "butter"))).add(BakeriesItems.BUTTER_CUBE.get());
            tag(ItemTags.create(ResourceLocation.fromNamespaceAndPath("c", "eggs"))).add(BakeriesItems.WHOLE_EGG.get());
            tag(ItemTags.create(ResourceLocation.fromNamespaceAndPath("c", "tools/knife"))).add(BakeriesItems.BREAD_KNIFE.get());
            tag(ItemTags.create(ResourceLocation.fromNamespaceAndPath("c", "tools"))).add(BakeriesItems.BREAD_KNIFE.get(), BakeriesItems.FLOUR_SIEVE.get());
            tag(ItemTags.create(ResourceLocation.fromNamespaceAndPath("c", "crops/tomato"))).add(BakeriesItems.TOMATO.get());
            tag(ItemTags.create(ResourceLocation.fromNamespaceAndPath("c", "foods/vegetables"))).add(BakeriesItems.TOMATO.get());
            tag(ItemTags.create(ResourceLocation.fromNamespaceAndPath("c", "foods/tomato"))).add(BakeriesItems.TOMATO.get());
            tag(ItemTags.create(ResourceLocation.fromNamespaceAndPath("c", "coffee_grounds"))).add(BakeriesItems.GROUND_COFFEE.get());
            tag(ItemTags.create(ResourceLocation.fromNamespaceAndPath("c", "matcha"))).add(BakeriesItems.MATCHA_POWDER.get());
            tag(ItemTags.create(ResourceLocation.fromNamespaceAndPath("c", "cheese"))).add(BakeriesItems.CHEESE_CUBE.get());
            tag(ItemTags.create(ResourceLocation.fromNamespaceAndPath("c", "enchantables"))).add(BakeriesItems.BREAD_KNIFE.get(), BakeriesItems.FLOUR_SIEVE.get());
            tag(ItemTags.create(ResourceLocation.fromNamespaceAndPath("forge", "cream"))).add(BakeriesItems.FOAMED_CREAM.get());
            tag(ItemTags.create(ResourceLocation.fromNamespaceAndPath("forge", "flour/wheat"))).add(BakeriesItems.WHOLE_WHEAT_FLOUR.get());
            tag(ItemTags.create(ResourceLocation.fromNamespaceAndPath("c", "tools/enchantables"))).add(BakeriesItems.ETERNAL_BAGUETTE.get(), BakeriesItems.BREAD_KNIFE.get(), BakeriesItems.FLOUR_SIEVE.get());
            tag(ItemTags.create(ResourceLocation.fromNamespaceAndPath("c", "tools/tools"))).add(BakeriesItems.BREAD_KNIFE.get(), BakeriesItems.FLOUR_SIEVE.get());

            tag(ItemTags.create(ResourceLocation.fromNamespaceAndPath("bakeries", "upright_on_oven")))
                    .add(
                            BakeriesItems.MOULD_TOAST.get(),
                            BakeriesItems.MOULD_TOAST_DOUGH.get(),
                            BakeriesItems.MOULD_CHEESE_COCOA_TOAST.get(),
                            BakeriesItems.MOULD_CHEESE_COCOA_TOAST_DOUGH.get(),
                            BakeriesItems.CIABATTA.get(),
                            BakeriesItems.CIABATTA_DOUGH.get(),
                            BakeriesItems.COUNTRY_BREAD.get(),
                            BakeriesItems.COUNTRY_BREAD_DOUGH.get(),
                            BakeriesItems.BAGUETTE.get(),
                            BakeriesItems.BAGUETTE_DOUGH.get());

            tag(ItemTags.create(ResourceLocation.fromNamespaceAndPath("bakeries", "bread_knife")))
                    .add(
                            BakeriesItems.BREAD_KNIFE.get());

            /*附魔标签*/
            /*耐久*/
            tag(ItemTags.DURABILITY_ENCHANTABLE).add(
                    BakeriesItems.BREAD_KNIFE.get(),
                    BakeriesItems.ETERNAL_BAGUETTE.get(),
                    BakeriesItems.FLOUR_SIEVE.get(),
                    BakeriesItems.BAGUETTE.get()
            );
            /*火焰附加*/
            tag(ItemTags.FIRE_ASPECT_ENCHANTABLE).add(
                    BakeriesItems.BREAD_KNIFE.get(),
                    BakeriesItems.ETERNAL_BAGUETTE.get()
            );
            /*效率*/
            tag(ItemTags.MINING_ENCHANTABLE).add(
                    BakeriesItems.BREAD_KNIFE.get(),
                    BakeriesItems.ETERNAL_BAGUETTE.get(),
                    BakeriesItems.FLOUR_SIEVE.get()
            );
            /*?*/
            tag(ItemTags.MINING_LOOT_ENCHANTABLE).add(
                    BakeriesItems.BREAD_KNIFE.get(),
                    BakeriesItems.ETERNAL_BAGUETTE.get(),
                    BakeriesItems.FLOUR_SIEVE.get()
            );
            /*锋利*/
            tag(ItemTags.SHARP_WEAPON_ENCHANTABLE).add(
                    BakeriesItems.BREAD_KNIFE.get(),
                    BakeriesItems.ETERNAL_BAGUETTE.get()
            );
            /*剑*/
            tag(ItemTags.SWORD_ENCHANTABLE).add(
                    BakeriesItems.BREAD_KNIFE.get(),
                    BakeriesItems.ETERNAL_BAGUETTE.get()
            );
            /*?*/
            tag(ItemTags.VANISHING_ENCHANTABLE).add(
                    BakeriesItems.BREAD_KNIFE.get(),
                    BakeriesItems.ETERNAL_BAGUETTE.get(),
                    BakeriesItems.FLOUR_SIEVE.get()
            );
            /*武器*/
            tag(ItemTags.WEAPON_ENCHANTABLE).add(
                    BakeriesItems.BREAD_KNIFE.get(),
                    BakeriesItems.ETERNAL_BAGUETTE.get()
            );
        }
    }

    private static class BlockTag extends BlockTagsProvider {
        private BlockTag(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, String modId, @Nullable ExistingFileHelper existingFileHelper) {
            super(output, lookupProvider, modId, existingFileHelper);
        }

        @Override
        protected void addTags(HolderLookup.Provider provider) {
            /* Minecraft标签 - 镐子可挖掘 */
            tag(BlockTags.MINEABLE_WITH_PICKAXE)
                    .add(
                            BakeriesBlocks.BLENDER.get(),
                            BakeriesBlocks.OVEN.get(),
                            BakeriesBlocks.DEEPSLATE_SALT_ORE.get(),
                            BakeriesBlocks.SALT_ORE.get(),
                            BakeriesBlocks.MOULD_TOAST.get(),
                            BakeriesBlocks.MOULD_CHEESE_COCOA_TOAST.get(),
                            BakeriesBlocks.RAW_SALT_BLOCK.get(),
                            BakeriesBlocks.GLASS_CABINET_DOOR.get(),
                            BakeriesBlocks.CASH_REGISTER_COMPUTER.get(),
                            BakeriesBlocks.MOKA_POT.get(),
                            BakeriesBlocks.DRINK_CUP.get(),
                            BakeriesBlocks.TOASTER.get(),
                            BakeriesBlocks.FERMENTATION_BOX.get(),
                            BakeriesBlocks.LUMINOUS_LIGHT_SIGN.get(),
                            BakeriesBlocks.BLACK_WHITE_CONCRETE.get());

            /* Minecraft标签 - 斧子可挖掘 */
            tag(BlockTags.MINEABLE_WITH_AXE)
                    .add(
                            BakeriesBlocks.CUPBOARD.get(),
                            BakeriesBlocks.DOUGH_CRAFTING_TABLE.get(),
                            BakeriesBlocks.GLASS_CABINET_DOOR.get(),
                            BakeriesBlocks.SOFA_WHITE.get(),
                            BakeriesBlocks.SOFA_RED.get(),
                            BakeriesBlocks.SOFA_LIGHT_GRAY.get(),
                            BakeriesBlocks.COFFEE_TABLE.get(),
                            BakeriesBlocks.MENU.get(),
                            BakeriesBlocks.WOOD_TRAY.get(),
                            BakeriesBlocks.BREAD_RACK.get(),
                            BakeriesBlocks.GLASS_BREAD_RACK.get(),
                            BakeriesBlocks.BREAD_HOLDERS.get());

            /* 烘焙坊自定义标签 - 沙发 */
            tag(BlockTags.create(ResourceLocation.fromNamespaceAndPath("bakeries", "sofa")))
                    .add(
                            BakeriesBlocks.SOFA_WHITE.get(),
                            BakeriesBlocks.SOFA_RED.get(),
                            BakeriesBlocks.SOFA_LIGHT_GRAY.get());
        }
    }

}
