package com.renyigesai.bakeries.data;


import com.renyigesai.bakeries.BakeriesMod;
import com.renyigesai.bakeries.api.annotation.ItemData;
import com.renyigesai.bakeries.common.init.BakeriesBlocks;
import com.renyigesai.bakeries.common.init.BakeriesItems;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.client.model.generators.ItemModelBuilder;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredItem;

import java.lang.reflect.Field;
import java.util.Objects;
import java.util.function.Supplier;

public class ItemModel extends ItemModelProvider {

    public ItemModel(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, BakeriesMod.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        Class<BakeriesItems> _class = BakeriesItems.class;
        for (Field field : _class.getDeclaredFields()) {
            boolean isAnnotationPresent = field.isAnnotationPresent(ItemData.class);
            if (isAnnotationPresent){
                try {
                    Object object = field.get(null);
                    if (object instanceof DeferredItem<?> deferredItem){
                        ItemData annotation = field.getAnnotation(ItemData.class);
                        ItemData.ModelType model = annotation.model();
                        if (model != ItemData.ModelType.CUSTOM) {
                            Item item = deferredItem.get();
                            if (model == ItemData.ModelType.GENERAL) {
                                basicItem(item);
                            }
                            if (model == ItemData.ModelType.TOOL) {
                                toolItem(item);
                            }
                            if (isBlockItem(item)){
                                BlockItem blockItem = (BlockItem) item;
                                if (model == ItemData.ModelType.BLOCK){
                                    blockItem(blockItem::getBlock);
                                }
                                if (model == ItemData.ModelType.BREAD){
                                    blockItem(blockItem::getBlock,"_1");
                                }
                            }
                        }
                    }
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        /*一般物品 食材 食物*/
        rawBreadItem(BakeriesItems.BAGEL_DOUGH,
                BakeriesBlocks.BAGEL::get, "_1");
        rawBreadItem(BakeriesItems.WHOLE_WHEAT_BAGEL_DOUGH,
                BakeriesBlocks.WHOLE_WHEAT_BAGEL::get, "_1");
        rawBreadItem(BakeriesItems.ROUND_BREAD_DOUGH,
                BakeriesBlocks.ROUND_BREAD::get, "_1");
        rawBreadItem(BakeriesItems.BROWN_SUGAR_ROLL_DOUGH,
                BakeriesBlocks.BROWN_SUGAR_ROLL::get, "_1");
        rawBreadItem(BakeriesItems.PINEAPPLE_BUN_DOUGH,
                BakeriesBlocks.PINEAPPLE_BUN::get, "_1");
        rawBreadItem(BakeriesItems.CROISSANT_DOUGH,
                BakeriesBlocks.CROISSANT::get, "_1");
        rawBreadItem(BakeriesItems.SALT_CROISSANT_DOUGH,
                BakeriesBlocks.SALT_CROISSANT::get, "_1");
        rawBreadItem(BakeriesItems.BAGUETTE_DOUGH,
                BakeriesBlocks.BAGUETTE::get, "_1");
        rawBreadItem(BakeriesItems.CIABATTA_DOUGH,
                BakeriesBlocks.CIABATTA::get, "_1");
        rawBreadItem(BakeriesItems.FOCACCIA_DOUGH,
                BakeriesBlocks.FOCACCIA::get, "_1");
        rawBreadItem(BakeriesItems.ROUND_BREAD_DOUGH,
                BakeriesBlocks.ROUND_BREAD::get, "_1");
        rawBreadItem(BakeriesItems.RICE_BREAD_DOUGH,
                BakeriesBlocks.RICE_BREAD, "_1");
        customModelItem(BakeriesItems.COUNTRY_BREAD_DOUGH,"custom/country_bread_dough");
        customModelItem(BakeriesItems.MOULD_TOAST_DOUGH,"custom/mould_toast_dough");
        customModelItem(BakeriesItems.MOULD_CHEESE_COCOA_TOAST_DOUGH,"custom/mould_toast_dough");
        customModelItem(BakeriesItems.FLOUR_SIEVE,"custom/flour_sieve");
        customModelItem(BakeriesItems.MOKA_POT,"custom/moka_pot");
        customModelItem(BakeriesItems.MOKA_POT_FILL,"custom/moka_pot");
        customModelItem(BakeriesItems.DRINK_CUP,"block/drink_cup");
        customModelItem(BakeriesItems.ETERNAL_BAGUETTE,"block/baguette_1");
    }

    private boolean isBlockItem(Item item){
        return item instanceof BlockItem;
    }

    private ItemModelBuilder rawBreadItem(Supplier<Item> bread, Supplier<Block> block, String index) {
        return this.getBuilder(this.name(bread.get()))
                .parent(new ModelFile.UncheckedModelFile(this.modLoc("block/" + this.name(block.get())+index)))
                .texture("0", this.modLoc("item/" + this.name(bread.get())));
    }

    private ItemModelBuilder customModelItem(Supplier<Item> item, String path) {
        return this.getBuilder(this.name(item.get())).parent(new ModelFile.UncheckedModelFile(this.modLoc(path)));
    }

    private void usingItem(Item item) {
        this.toolItem((ResourceLocation) Objects.requireNonNull(BuiltInRegistries.ITEM.getKey(item)),1);
        usingItem(Objects.requireNonNull(BuiltInRegistries.ITEM.getKey(item)));
    }
    private ItemModelBuilder usingItem(ResourceLocation item) {
        return getBuilder(item.getPath())
                .parent(new ModelFile.UncheckedModelFile("item/handheld"))
                .texture("layer0", ResourceLocation.fromNamespaceAndPath(item.getNamespace(), "item/" + item.getPath()))
                .override()
                .predicate(this.modLoc("using"), 0.0f)
                .model(this.generated(item,0)).end()
                .override()
                .predicate(this.modLoc("using"), 1.0f)
                .model(this.generated(item,1))
                .end();
    }
    private ItemModelBuilder createToolItem(ResourceLocation item,String name) {
        return getBuilder(item.toString()+name)
                .parent(new ModelFile.UncheckedModelFile("item/handheld"))
                .texture("layer0", ResourceLocation.fromNamespaceAndPath(item.getNamespace(), "item/" + item.getPath()));
    }

    public ItemModelBuilder toolItem(Item item) {
        return createToolItem(Objects.requireNonNull(BuiltInRegistries.ITEM.getKey(item)),"");
    }

    public ItemModelBuilder toolItem(ResourceLocation item) {
        return createToolItem(item,"");
    }
    private ItemModelBuilder createBlockItem(Block block, String texturePath) {
        return this.withExistingParent(this.name(block), this.mcLoc("item/generated"))
                .texture("layer0", texturePath != null ? this.modLoc("item/" + texturePath) : this.modLoc("item/" + this.name(block)));
    }

    public ItemModelBuilder blockItem(Block block, String recource) {
        return createBlockItem(block, recource);
    }

    public ItemModelBuilder blockItem(Block block) {
        return createBlockItem(block, null);
    }
    private ItemModelBuilder simpleBlockItem(Supplier<Item> item) {
        return getBuilder(this.name(item.get()))
                .parent(new ModelFile.UncheckedModelFile(this.modLoc("block/" + this.name(item.get()))));
    }
    private ItemModelBuilder blockItem(Supplier<Block> block, String index) {
        return this.getBuilder(this.name(block.get()))
                .parent(new ModelFile.UncheckedModelFile(this.modLoc("block/" + this.name(block.get())+index)));
    }
    private void blockItem(Supplier<Block> block) {
        this.withExistingParent(this.name(block.get()), this.modLoc("block/" + this.name(block.get())));
    }
    private String name(Block block) {
        return Objects.requireNonNull(BuiltInRegistries.BLOCK.getKey(block)).getPath();
    }
    private String name(Item item) {
        return Objects.requireNonNull(BuiltInRegistries.ITEM.getKey(item)).getPath();
    }
    public ModelFile generated(Item item, int index){
        return this.generated((ResourceLocation)Objects.requireNonNull(BuiltInRegistries.ITEM.getKey(item)), index);
    }
    public void basicItem_Stack_Size(Item item) {
        this.basicItem((ResourceLocation)Objects.requireNonNull(BuiltInRegistries.ITEM.getKey(item)),1);
        this.basicItem((ResourceLocation)Objects.requireNonNull(BuiltInRegistries.ITEM.getKey(item)),2);
        this.basicItem_Stack_Size((ResourceLocation)Objects.requireNonNull(BuiltInRegistries.ITEM.getKey(item)));
    }
    public void basicItem_Stack_Size(ResourceLocation item) {
        this.getBuilder(item.getPath())
                .parent(new ModelFile.UncheckedModelFile("item/generated"))
                .texture("layer0", ResourceLocation.fromNamespaceAndPath(item.getNamespace(), "item/" + item.getPath()))
                .override()
                .predicate(this.modLoc("stack_size"), 0.0F)
                .model(this.generated(item, 0)).end()
                .override()
                .predicate(this.modLoc("stack_size"), 1.0F)
                .model(this.generated(item, 1)).end()
                .override()
                .predicate(this.modLoc("stack_size"), 2.0F)
                .model(this.generated(item, 2))
                .end();
    }
    public ModelFile generated(ResourceLocation item, int index){
        if(index == 0){
            return this.getExistingFile(this.modLoc(item.getPath()));
        }
        return this.getExistingFile(this.modLoc(item.getPath()+"_"+index));
    }
    public ItemModelBuilder basicItem(Item item, int index) {
        return this.basicItem((ResourceLocation)Objects.requireNonNull(BuiltInRegistries.ITEM.getKey(item)), index);
    }
    public ItemModelBuilder basicItem(ResourceLocation item,int index) {
        return this.getBuilder(item.getPath() +"_"+index)
                        .parent(new ModelFile.UncheckedModelFile("item/generated"))
                        .texture("layer0",
                                ResourceLocation.fromNamespaceAndPath(BakeriesMod.MODID, "item/" + item.getPath()+"_"+index)
                        );
    }
    public ItemModelBuilder toolItem(ResourceLocation item,int index) {
        return this.getBuilder(item.getPath() +"_"+index)
                .parent(new ModelFile.UncheckedModelFile("item/handheld"))
                .texture("layer0",
                        ResourceLocation.fromNamespaceAndPath(BakeriesMod.MODID, "item/" + item.getPath()+"_"+index)
                );
    }
    public ResourceLocation blockLoc(Block block ,String name) {
        ResourceLocation  blockLoc = BuiltInRegistries.BLOCK.getKey(block);
        return ResourceLocation.fromNamespaceAndPath(blockLoc.getNamespace(), name);
    }
}