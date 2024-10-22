package com.renyigesai.bakery.data.provider;


import com.renyigesai.bakery.BakeryMod;
import com.renyigesai.bakery.init.BakeryBlocks;
import com.renyigesai.bakery.init.BakeryItems;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Objects;
import java.util.function.Supplier;

public class BItemModelProvider extends net.minecraftforge.client.model.generators.ItemModelProvider {

    public BItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, BakeryMod.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        blockItem(BakeryBlocks.BAGEL_BLOCK,"_1");
        blockItem(BakeryBlocks.BAGUETTE_BLOCK,"_1");
        blockItem(BakeryBlocks.CINNAMON_ROLL_BLOCK,"_1");
        blockItem(BakeryBlocks.CROISSANT_BLOCK,"_1");

//        basicItem(BakeryItems.BAGEL_DOUGH.get());
//        basicItem(BakeryItems.BAGUETTE_DOUGH.get());
//        basicItem(BakeryItems.BROWN_SUGAR_CUBE.get());
//        basicItem(BakeryItems.BUTTER_CUBE.get());
//        basicItem(BakeryItems.CINNAMON_ROLL_DOUGH.get());
//        basicItem(BakeryItems.COUNTRY_BREAD_DOUGH.get());
//        basicItem(BakeryItems.CROISSANT_DOUGH.get());
//        basicItem(BakeryItems.FLOUR.get());
//        basicItem(BakeryItems.FLOUR_RYE.get());
//        basicItem(BakeryItems.PINEAPPLE_BUN.get());
//        basicItem(BakeryItems.PINEAPPLE_BUN_DOUGH.get());
//        basicItem(BakeryItems.RAW_EGG_TART.get());
//        basicItem(BakeryItems.RAW_PUMPKIN_PIE.get());
//        basicItem(BakeryItems.RAW_TARE_CRUST.get());
//        basicItem(BakeryItems.ROUND_BREAD_DOUGH.get());
//        basicItem(BakeryItems.SALT.get());
//        basicItem(BakeryItems.SALT_CROISSANT.get());
//        basicItem(BakeryItems.SALT_CROISSANT_DOUGH.get());
//        basicItem(BakeryItems.ROUND_BREAD.get());
//        basicItem(BakeryItems.TART_SHELL.get());

    }

    public ItemModelBuilder toolItem(Item item) {
        return toolItem(Objects.requireNonNull(ForgeRegistries.ITEMS.getKey(item)));
    }

    public ItemModelBuilder toolItem(ResourceLocation item) {
        return getBuilder(item.toString())
                .parent(new ModelFile.UncheckedModelFile("item/handheld"))
                .texture("layer0", new ResourceLocation(item.getNamespace(), "item/" + item.getPath()));
    }
    public ItemModelBuilder blockItem(Block block) {
        return this.withExistingParent(this.name(block), this.mcLoc("item/generated"))
                .texture("layer0", this.modLoc("item/" + this.name(block)));
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
        return Objects.requireNonNull(ForgeRegistries.BLOCKS.getKey(block)).getPath();
    }
    private String name(Item item) {
        return Objects.requireNonNull(ForgeRegistries.ITEMS.getKey(item)).getPath();
    }
    public ModelFile generated(Item item, int index){
        return this.generated((ResourceLocation)Objects.requireNonNull(ForgeRegistries.ITEMS.getKey(item)), index);
    }
    public void basicItem_Stack_Size(Item item) {
        this.basicItem((ResourceLocation)Objects.requireNonNull(ForgeRegistries.ITEMS.getKey(item)),1);
        this.basicItem((ResourceLocation)Objects.requireNonNull(ForgeRegistries.ITEMS.getKey(item)),2);
        this.basicItem_Stack_Size((ResourceLocation)Objects.requireNonNull(ForgeRegistries.ITEMS.getKey(item)));
    }
    public void basicItem_Stack_Size(ResourceLocation item) {
        this.getBuilder(item.getPath())
                .parent(new ModelFile.UncheckedModelFile("item/generated"))
                .texture("layer0", new ResourceLocation(item.getNamespace(), "item/" + item.getPath()))
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
        return this.basicItem((ResourceLocation)Objects.requireNonNull(ForgeRegistries.ITEMS.getKey(item)), index);
    }
    public ItemModelBuilder basicItem(ResourceLocation item,int index) {
        return this.getBuilder(item.getPath() +"_"+index)
                        .parent(new ModelFile.UncheckedModelFile("item/generated"))
                        .texture("layer0",
                                new ResourceLocation(BakeryMod.MODID, "item/" + item.getPath()+"_"+index)
                        );
    }
}