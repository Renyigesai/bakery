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
    public static String CUTOUT = "cutout";
    public BItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, BakeryMod.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        blockItem(BakeryBlocks.BAGEL_BLOCK,"_1");
        blockItem(BakeryBlocks.BAGUETTE_BLOCK,"_1");
        blockItem(BakeryBlocks.CINNAMON_ROLL_BLOCK,"_1");
        blockItem(BakeryBlocks.CROISSANT_BLOCK,"_1");
        basicBlockItem(BakeryBlocks.COUNTRY_BREAD_BLOCK,"block/country_bread");
        basicItem(BakeryItems.BAGEL_DOUGH, "custom/bagel_1", "item/raw_dough");
        basicItem(BakeryItems.BAGUETTE_DOUGH, "custom/baguette_1", "item/raw_dough_2");
        basicItem(BakeryItems.CINNAMON_ROLL_DOUGH, "custom/cinnamon_roll_dough", "item/cinnamon_roll_dough");
        basicItem(BakeryItems.COUNTRY_BREAD_DOUGH, "custom/country_bread_1", "item/raw_dough_2");
        basicItem(BakeryItems.CROISSANT_DOUGH, "custom/croissant_1", "item/raw_dough");
        basicItem(BakeryItems.BROWN_SUGAR_CUBE.get());
        basicItem(BakeryItems.BUTTER_CUBE.get());
        basicItem(BakeryItems.FLOUR.get());
        basicItem(BakeryItems.FLOUR_RYE.get());
        basicItem(BakeryItems.SALT.get());
        basicItem(BakeryItems.PINEAPPLE_BUN, "custom/pineapple_bun", "block/pineapple_bun");
        basicItem(BakeryItems.PINEAPPLE_BUN_DOUGH, "custom/pineapple_bun", "item/raw_dough");
        basicItem(BakeryItems.RAW_EGG_TART, "custom/raw_egg_tart", "item/tart_shell");
        basicItem(BakeryItems.RAW_PUMPKIN_PIE, "custom/raw_pumpkin_pie", "item/pumpkin_stuffing", "item/raw_tare_crust");
        basicItem(BakeryItems.RAW_TARE_CRUST, "custom/raw_tare_crust", "item/raw_tare_crust");
        basicItem(BakeryItems.ROUND_BREAD, "custom/round_bread","block/round_bread");
        basicItem(BakeryItems.ROUND_BREAD_DOUGH, "custom/round_bread","item/raw_dough");
        basicItem(BakeryItems.SALT_CROISSANT, "custom/salt_croissant", "block/salt_croissant");
        basicItem(BakeryItems.SALT_CROISSANT_DOUGH, "custom/salt_croissant","item/raw_dough");
        basicItem(BakeryItems.TART_SHELL, "custom/tart_shell", "item/tart_shell");

    }
    public void basicItem(Supplier<Item> item, String pModelFile, String pTexture, String pTexture1) {
        this.withExistingParent(this.name(item.get()), this.modLoc(pModelFile))
                .texture("0", this.modLoc(pTexture))
                .texture("1", this.modLoc(pTexture1))
                .renderType(CUTOUT);;
    }
    public void basicBlockItem(Supplier<Block> block, String pModelFile, String pTexture, String pTexture1) {
        this.withExistingParent(this.name(block.get()), this.modLoc(pModelFile))
                .texture("0", this.modLoc(pTexture))
                .texture("1", this.modLoc(pTexture1))
                .renderType(CUTOUT);;
    }
    public void basicItem(Supplier<Item> item, String pModelFile, String pTexture) {
        this.withExistingParent(this.name(item.get()), this.modLoc(pModelFile))
                .texture("0", this.modLoc(pTexture))
                .renderType(CUTOUT);;
    }
    public void basicBlockItem(Supplier<Block> block, String pModelFile, String pTexture) {
        this.withExistingParent(this.name(block.get()), this.modLoc(pModelFile))
                .texture("0", this.modLoc(pTexture))
                .renderType(CUTOUT);;
    }
    public void basicItem(Supplier<Item> item, String pModelFile) {
        this.withExistingParent(this.name(item.get()), this.modLoc(pModelFile))
                .renderType(CUTOUT);;
    }
    public void basicBlockItem(Supplier<Block> block, String pModelFile) {
        this.withExistingParent(this.name(block.get()), this.modLoc(pModelFile))
                .renderType(CUTOUT);;
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