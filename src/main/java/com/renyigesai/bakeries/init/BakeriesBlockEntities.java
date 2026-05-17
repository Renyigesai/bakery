package com.renyigesai.bakeries.init;

import com.renyigesai.bakeries.BakeriesMod;
import com.renyigesai.bakeries.block.entity.MachineBlockEntity;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.entity.BlockEntityType;

public final class BakeriesBlockEntities {
    public static final BlockEntityType<MachineBlockEntity> MACHINE = Registry.register(
            BuiltInRegistries.BLOCK_ENTITY_TYPE,
            new ResourceLocation(BakeriesMod.MODID, "machine"),
            BlockEntityType.Builder.of(
                    MachineBlockEntity::new,
                    BakeriesBlocks.OVEN,
                    BakeriesBlocks.TOASTER,
                    BakeriesBlocks.BLENDER,
                    BakeriesBlocks.FERMENTATION_BOX,
                    BakeriesBlocks.DOUGH_CRAFTING_TABLE,
                    BakeriesBlocks.CUPBOARD,
                    BakeriesBlocks.MENU,
                    BakeriesBlocks.MOKA_POT,
                    BakeriesBlocks.DRINK_CUP,
                    BakeriesBlocks.MIX_BLOCK
            ).build(null)
    );

    private BakeriesBlockEntities() {
    }

    public static void init() {
        BakeriesMod.LOGGER.info("Registered Bakeries block entities.");
    }
}
