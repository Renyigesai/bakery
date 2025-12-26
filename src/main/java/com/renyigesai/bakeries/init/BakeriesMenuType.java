
package com.renyigesai.bakeries.init;

import com.renyigesai.bakeries.BakeriesMod;
import com.renyigesai.bakeries.inventory.blender.BlenderMenu;
import com.renyigesai.bakeries.inventory.blender.BlenderScreen;
import com.renyigesai.bakeries.inventory.dough_crafting_table.DoughCraftingTableMenu;
import com.renyigesai.bakeries.inventory.dough_crafting_table.DoughCraftingTableScreen;
import com.renyigesai.bakeries.inventory.fermentation_barrel.FermentationBarrelMenu;
import com.renyigesai.bakeries.inventory.fermentation_barrel.FermentationBarrelScreen;
import com.renyigesai.bakeries.inventory.oven.OvenMenu;
import com.renyigesai.bakeries.inventory.oven.OvenScreen;

import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.world.flag.FeatureFlag;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.network.IContainerFactory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class BakeriesMenuType {
	public static final DeferredRegister<MenuType<?>> REGISTRY = DeferredRegister.create(ForgeRegistries.MENU_TYPES, BakeriesMod.MODID);

	public static final RegistryObject<MenuType<OvenMenu>> OVEN_MENU =
			create("oven_menu", OvenMenu::new);
	public static final RegistryObject<MenuType<DoughCraftingTableMenu>> DOUGH_CRAFTING_TABLE_MENU =
			register("dough_crafting_table_menu", DoughCraftingTableMenu::new);
	private static<T extends AbstractContainerMenu> RegistryObject<MenuType<T>> register(String pKey, MenuType.MenuSupplier<T> pFactory) {
		return REGISTRY.register(pKey, () -> new MenuType<>(pFactory, FeatureFlags.VANILLA_SET));
	}
	public static final RegistryObject<MenuType<BlenderMenu>> BLENDER_MENU = REGISTRY.register("blender_menu",
			() -> IForgeMenuType.create(BlenderMenu::create));

	public static final RegistryObject<MenuType<FermentationBarrelMenu>> FERMENTATION_BARREL_MENU = REGISTRY.register("fermentation_barrel_menu",
			() -> IForgeMenuType.create(FermentationBarrelMenu::create));

	private static<T extends AbstractContainerMenu> RegistryObject<MenuType<T>> register(String pKey, MenuType.MenuSupplier<T> pFactory, FeatureFlag... pRequiredFeatures) {
		return REGISTRY.register(pKey, () -> new MenuType<>(pFactory, FeatureFlags.REGISTRY.subset(pRequiredFeatures)));
	}
	private static<T extends AbstractContainerMenu> RegistryObject<MenuType<T>> create(String pKey, IContainerFactory<T> pFactory) {
		return REGISTRY.register(pKey, () -> IForgeMenuType.create(pFactory));
	}
	@SubscribeEvent
	public static void clientLoad(FMLClientSetupEvent event) {
		event.enqueueWork(() -> {
			MenuScreens.register(BakeriesMenuType.OVEN_MENU.get(), OvenScreen::new);
			MenuScreens.register(BakeriesMenuType.DOUGH_CRAFTING_TABLE_MENU.get(), DoughCraftingTableScreen::new);
			MenuScreens.register(BakeriesMenuType.BLENDER_MENU.get(), BlenderScreen::new);
			MenuScreens.register(BakeriesMenuType.FERMENTATION_BARREL_MENU.get(), FermentationBarrelScreen::new);
		});
	}

}
