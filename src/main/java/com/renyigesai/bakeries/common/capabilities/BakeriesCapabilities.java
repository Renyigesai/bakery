package com.renyigesai.bakeries.common.capabilities;

import com.renyigesai.bakeries.BakeriesMod;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.energy.IEnergyStorage;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;
import net.neoforged.neoforge.fluids.capability.IFluidHandlerItem;
import net.neoforged.neoforge.items.IItemHandler;

public class BakeriesCapabilities {
    private static ResourceLocation create(String path) {
        return BakeriesMod.rl(path);
    }
    private BakeriesCapabilities() {
    }
    public static final MultiTypeCapability<IEnergyStorage> ENERGY =
            new MultiTypeCapability<>(
                    Capabilities.EnergyStorage.BLOCK,
                    Capabilities.EnergyStorage.ITEM,
                    Capabilities.EnergyStorage.ENTITY);
    public static final IMultiTypeCapability<IFluidHandler, IFluidHandlerItem> FLUID =
            new FluidCapability(
                    Capabilities.FluidHandler.BLOCK,
                    Capabilities.FluidHandler.ITEM,
                    Capabilities.FluidHandler.ENTITY);
    public static final MultiTypeCapability<IItemHandler> ITEM =
            new MultiTypeCapability<>(
                    Capabilities.ItemHandler.BLOCK,
                    Capabilities.ItemHandler.ITEM,
                    Capabilities.ItemHandler.ENTITY);
    public static void registerFluidCapabilities(RegisterCapabilitiesEvent event) {
//        event.registerBlockEntity(
//                ITEM.block(),
//                BakeriesBlocks.MSBlockEntities.OVEN_BLOCK_ENTITY.get(),
//                (blockEntity, side) -> {
//                    return blockEntity.getOptionalIItemHandler().isPresent() ? blockEntity.getOptionalIItemHandler().get() : null;
//                }
//        );



    }

}
