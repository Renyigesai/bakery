package net.weibai.bakeries.common.capabilities;

import net.minecraft.core.Direction;
import net.neoforged.neoforge.capabilities.BlockCapability;
import net.neoforged.neoforge.capabilities.EntityCapability;
import net.neoforged.neoforge.capabilities.ItemCapability;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;
import net.neoforged.neoforge.fluids.capability.IFluidHandlerItem;
import org.jetbrains.annotations.Nullable;

public record FluidCapability(BlockCapability<IFluidHandler, @Nullable Direction> block,
                               ItemCapability<IFluidHandlerItem, Void> item,
                               EntityCapability<IFluidHandler, @Nullable Direction> entity) implements IMultiTypeCapability<IFluidHandler, IFluidHandlerItem> {
}