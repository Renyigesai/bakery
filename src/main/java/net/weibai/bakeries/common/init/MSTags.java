package net.weibai.bakeries.common.init;

import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.weibai.mechanical_soar.common.MechanicalSoarMod;

public class MSTags {
    public static class Blocks {
        /**陨铁矿*/
//        public static final TagKey<Block> METEROITE_IRON_ORES = tag("meteorite_iron_ores");

        /**石子*/
        public static final TagKey<Block> TINY_STONES = tag("tiny_stone");
        private static TagKey<Block> tag(String name) {
            return BlockTags.create(MechanicalSoarMod.prefix(name));
        }
    }
    public static class Items {
        //陨铁矿
//        public static final TagKey<Item> METEROITE_IRON_ORES = tag("meteorite_iron_ores");
        /**石子*/
        public static final TagKey<Item> TINY_STONES = tag("tiny_stone");
        /**锋利的石子*/
        public static final TagKey<Item> SHARP_TINY_STONES = tag("sharp_tiny_stone");
        /**熔炼*/
        public static final TagKey<Item> CLOSED_COMBUSTION = tag("closed_combustion");
        private static TagKey<Item> tag(String name) {
            return ItemTags.create(MechanicalSoarMod.prefix(name));
        }
    }
}
