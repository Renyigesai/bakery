package net.weibai.bakeries.data;

import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.common.data.SoundDefinitionsProvider;
import net.weibai.bakeries.BakeriesMod;

public class MSSoundsProvider extends SoundDefinitionsProvider {
    public MSSoundsProvider(PackOutput output, ExistingFileHelper helper) {
        super(output, BakeriesMod.MODID, helper);
    }
    @Override
    public void registerSounds() {
//        this.add(MSSounds.WOOD_LIGHTER_USE.unwrapKey().orElseThrow().location(),
//                 definition().with(sound(MechanicalSoarMod.prefix("item/wood_lighter_use"))));
//

//        this.add(MSSounds.WOOD_LIGHTER_USE.get().getLocation(),
//                definition().with(sound(MechanicalSoarMod.prefix("item/wood_lighter_use")).stream())
//                        .subtitle(UtilTranslatable.setSounds("wood_lighter_use")));
    }
}
