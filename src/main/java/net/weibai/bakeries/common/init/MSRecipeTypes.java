package net.weibai.bakeries.common.init;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLConstructModEvent;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.weibai.mechanical_soar.common.MechanicalSoarMod;
import net.weibai.mechanical_soar.common.recipe.collision.CollisionRecipe;


@EventBusSubscriber(modid = MechanicalSoarMod.MODID)
public class MSRecipeTypes {
    public static final DeferredRegister<RecipeSerializer<?>> SERIALIZERS = DeferredRegister.create(Registries.RECIPE_SERIALIZER, MechanicalSoarMod.MODID);
    public static final DeferredRegister<RecipeType<?>> RECIPE_TYPE = DeferredRegister.create(Registries.RECIPE_TYPE, MechanicalSoarMod.MODID);

    @SuppressWarnings("removal")
    @SubscribeEvent
    public static void register(FMLConstructModEvent event) {
        event.enqueueWork(() -> {
            SERIALIZERS.register(CollisionRecipe.ID, () -> CollisionRecipe.Serializer.INSTANCE);
            RECIPE_TYPE.register(CollisionRecipe.ID, () -> CollisionRecipe.Type.INSTANCE);
//            SERIALIZERS.register(RotRecipe.ID, () -> RotRecipe.Serializer.INSTANCE);
//            RECIPE_TYPE.register(RotRecipe.ID, () -> RotRecipe.Type.INSTANCE);
//            SERIALIZERS.register(UsePlaceItemRecipe.ID, () -> UsePlaceItemRecipe.Serializer.INSTANCE);
//            RECIPE_TYPE.register(UsePlaceItemRecipe.ID, () -> UsePlaceItemRecipe.Type.INSTANCE);
//            SERIALIZERS.register(ClosedCombustionRecipe.ID, () -> ClosedCombustionRecipe.Serializer.INSTANCE);
//            RECIPE_TYPE.register(ClosedCombustionRecipe.ID, () -> ClosedCombustionRecipe.Type.INSTANCE);

        });
    }
    public static void getREGISTER(IEventBus bus){
        SERIALIZERS.register(bus);
        RECIPE_TYPE.register(bus);
    }
}
