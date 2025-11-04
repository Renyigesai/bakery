package com.renyigesai.bakeries.data;


import com.renyigesai.bakeries.BakeriesMod;
import com.renyigesai.bakeries.common.init.BakeriesBlocks;
import com.renyigesai.bakeries.common.init.BakeriesItems;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.client.model.generators.ItemModelBuilder;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import java.util.Objects;
import java.util.function.Supplier;

public class MSItemModelProvider extends ItemModelProvider {

    public MSItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, BakeriesMod.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {

        /*一般物品 食材 食物*/
        basicItem(BakeriesItems.FLOUR.get());
        basicItem(BakeriesItems.WHOLE_WHEAT_FLOUR.get());
        basicItem(BakeriesItems.COCOA_POWDER.get());
        basicItem(BakeriesItems.MATCHA_POWDER.get());
        basicItem(BakeriesItems.SALT.get());
        basicItem(BakeriesItems.BUTTER_CUBE.get());
        basicItem(BakeriesItems.FOAMED_CREAM.get());
        basicItem(BakeriesItems.CHEESE_CREAM.get());
        basicItem(BakeriesItems.BUTTER_FLOUR_SAND.get());
        basicItem(BakeriesItems.HONEY_BUTTER.get());
        basicItem(BakeriesItems.WHOLE_EGG.get());
        basicItem(BakeriesItems.RAW_PROTEIN.get());
        basicItem(BakeriesItems.RAW_EGG_YOLK.get());
        basicItem(BakeriesItems.CHEESE_CUBE.get());
        basicItem(BakeriesItems.FRESH_CHEESE_CUBE.get());
        basicItem(BakeriesItems.BROWN_SUGAR_CUBE.get());
        basicItem(BakeriesItems.RAW_COFFEE_BEAN.get());
        basicItem(BakeriesItems.COFFEE_BEAN.get());
        basicItem(BakeriesItems.GROUND_COFFEE.get());
        basicItem(BakeriesItems.BEARNAISE.get());
        basicItem(BakeriesItems.OLIVE_OIL.get());
        basicItem(BakeriesItems.MEAT_FLOSS.get());
        basicItem(BakeriesItems.SCONE.get());
        basicItem(BakeriesItems.TOMATO.get());
        basicItem(BakeriesItems.OLIVE.get());
        basicItem(BakeriesItems.SLICED_TOAST.get());
        basicItem(BakeriesItems.HONEY_BUTTER_SPREAD_TOAST.get());
        basicItem(BakeriesItems.SLICED_CHEESE_COCOA_TOAST.get());
        basicItem(BakeriesItems.COUNTRY_BREAD_SLICE.get());
        basicItem(BakeriesItems.HONEY_BUTTER_SPREAD_COUNTRY_BREAD.get());
        basicItem(BakeriesItems.PASTRY.get());
        basicItem(BakeriesItems.SWEET_DOUGH.get());
        basicItem(BakeriesItems.SALTED_DOUGH.get());
        basicItem(BakeriesItems.WHOLE_WHEAT_DOUGH.get());

        basicItem(BakeriesItems.BOTTLE_YEAST.get());
        basicItem(BakeriesItems.BOTTLE_MILK.get());
        basicItem(BakeriesItems.BOTTLE_CREAM.get());
        basicItem(BakeriesItems.BOTTLE_BUTTER.get());


        blockItem(BakeriesBlocks.BAGEL::get, "_1");
        blockItem(BakeriesBlocks.WHOLE_WHEAT_BAGEL::get, "_1");
        blockItem(BakeriesBlocks.ROUND_BREAD::get, "_1");
        blockItem(BakeriesBlocks.BERRY_BREAD::get, "_1");
        blockItem(BakeriesBlocks.CHEESE_CREAM_BREAD::get, "_1");
        blockItem(BakeriesBlocks.BROWN_SUGAR_ROLL::get, "_1");
        blockItem(BakeriesBlocks.PINEAPPLE_BUN::get, "_1");
        blockItem(BakeriesBlocks.MEAT_FLOSS_BREAD_ROLL::get, "_1");
        blockItem(BakeriesBlocks.CROISSANT::get, "_1");
        blockItem(BakeriesBlocks.DIRTY_CHOCO_CROISSANT::get, "_1");
        blockItem(BakeriesBlocks.SALT_CROISSANT::get, "_1");
        blockItem(BakeriesBlocks.CIABATTA::get, "_1");
        blockItem(BakeriesBlocks.FOCACCIA::get, "_1");
        blockItem(BakeriesBlocks.BERRY_BAGEL::get, "_1");
        blockItem(BakeriesBlocks.BAGEL_FILLED_SAUCE::get, "_1");
        blockItem(BakeriesBlocks.BAGUETTE_WITH_FILLING::get, "_1");
        blockItem(BakeriesBlocks.TOMATO_CHEESE_CROISSANT_SANDWICH::get, "_1");
        blockItem(BakeriesBlocks.BAGUETTE::get, "_1");

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
//        rawBreadItem(BakeriesItems.COUNTRY_BREAD_DOUGH,
//                BakeriesBlocks.COUNTRY_BREAD_DOUGH::get, "_1");

        basicItem(BakeriesItems.ICE_CUBES.get());
        blockItem(BakeriesBlocks.OVEN::get);
        blockItem(BakeriesBlocks.BLENDER);
        toolItem(BakeriesItems.BREAD_KNIFE.get());
        customModelItem(BakeriesItems.FLOUR_SIEVE,"custom/flour_sieve");
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