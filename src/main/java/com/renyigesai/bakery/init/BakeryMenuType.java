
/*
 *	MCreator note: This file will be REGENERATED on each build.
 */
package com.renyigesai.bakery.init;

import com.renyigesai.bakery.BakeryMod;
import com.renyigesai.bakery.inventory.dough_crafting_table.DoughCraftingTableMenu;
import com.renyigesai.bakery.inventory.dough_crafting_table.DoughCraftingTableScreen;
import com.renyigesai.bakery.inventory.oven.OvenMenu;
import com.renyigesai.bakery.inventory.oven.OvenScreen;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class BakeryMenuType {
	public static final DeferredRegister<MenuType<?>> REGISTRY = DeferredRegister.create(ForgeRegistries.MENU_TYPES, BakeryMod.MODID);

	public static final RegistryObject<MenuType<OvenMenu>> OVEN_MENU =
			REGISTRY.register("oven_menu",
					() -> IForgeMenuType.create(OvenMenu::new));
	public static final RegistryObject<MenuType<DoughCraftingTableMenu>> DOUGH_CRAFTING_TABLE_MENU =
			REGISTRY.register("dough_crafting_table_menu",
					() -> IForgeMenuType.create(DoughCraftingTableMenu::new));

	@SubscribeEvent
	public static void clientLoad(FMLClientSetupEvent event) {
		event.enqueueWork(() -> {
			MenuScreens.register(BakeryMenuType.OVEN_MENU.get(), OvenScreen::new);
			MenuScreens.register(BakeryMenuType.DOUGH_CRAFTING_TABLE_MENU.get(), DoughCraftingTableScreen::new);
		});
	}

}
