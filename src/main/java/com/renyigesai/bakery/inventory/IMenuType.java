package com.renyigesai.bakery.inventory;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;

public interface IMenuType <T extends AbstractContainerMenu> extends MenuType.MenuSupplier<T> {
    T create(int windowId, Inventory playerInv, FriendlyByteBuf extraData, IContainerData data);
//    T create(int windowId, Inventory playerInv, FriendlyByteBuf extraData);
    @Override
    default T create(int p_create_1_, Inventory p_create_2_)
    {
        return create(p_create_1_, p_create_2_, null, null);
    }
    static <T extends AbstractContainerMenu> MenuType<T> create(IContainerFactory<T> factory) {
        return new MenuType<>(factory, FeatureFlags.DEFAULT_FLAGS);
    }
    interface IContainerFactory<T extends AbstractContainerMenu> extends MenuType.MenuSupplier<T> {
        T create(int windowId, Inventory inv, FriendlyByteBuf byteBuf, IContainerData data);

        @Override
        default T create(int p_create_1_, Inventory p_create_2_)
        {
            return create(p_create_1_, p_create_2_, null, null);
        }
    }

   
}
