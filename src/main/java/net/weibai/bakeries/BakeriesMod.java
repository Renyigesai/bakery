package net.weibai.bakeries;

import com.mojang.logging.LogUtils;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.weibai.bakeries.common.capabilities.BakeriesCapabilities;
import net.weibai.bakeries.common.init.*;
import org.slf4j.Logger;

@Mod(BakeriesMod.MODID)
public class BakeriesMod {
    public static final String MODID = "bakeries";
    public static final Logger LOGGER = LogUtils.getLogger();

    public BakeriesMod(IEventBus modEventBus, ModContainer modContainer) {
//        NeoForge.EVENT_BUS.register(this);

        BakeriesCreativeModeTabs.getREGISTER().register(modEventBus);
        BakeriesBlocks.getREGISTER().register(modEventBus);
        BakeriesBlocks.MSBlockEntities.getREGISTER().register(modEventBus);
        BakeriesItems.getREGISTER().register(modEventBus);
        BakeriesRecipeTypes.getREGISTER(modEventBus);
        BakeriesMenuType.getREGISTRY().register(modEventBus);
        BakeriesSounds.getREGISTRY().register(modEventBus);
        BakeriesDataComponents.getREGISTER().register(modEventBus);

        modEventBus.addListener(BakeriesCapabilities::registerFluidCapabilities);

//        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    public static ResourceLocation rl(String path) {
        return ResourceLocation.fromNamespaceAndPath(MODID, path);
    }



}
