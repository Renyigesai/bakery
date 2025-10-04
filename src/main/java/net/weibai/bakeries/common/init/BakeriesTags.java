package net.weibai.bakeries.common.init;

import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.weibai.bakeries.BakeriesMod;

public class BakeriesTags {
    public static class Blocks {
        /**陨铁矿*/
//        public static final TagKey<Block> METEROITE_IRON_ORES = tag("meteorite_iron_ores");

        private static TagKey<Block> tag(String name) {
            return BlockTags.create(BakeriesMod.rl(name));
        }
    }
    public static class Items {
        //陨铁矿
//        public static final TagKey<Item> METEROITE_IRON_ORES = tag("meteorite_iron_ores");

        private static TagKey<Item> tag(String name) {
            return ItemTags.create(BakeriesMod.rl(name));
        }
    }
}
