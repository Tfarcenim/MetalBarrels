package tfar.metalbarrels.util;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class ModTags {

  public static class Items {

    public static final TagKey<Item> WOOD_BARRELS = tag("barrels/wood");
    public static final TagKey<Item> COPPER_BARRELS = tag("barrels/copper");
    public static final TagKey<Item> IRON_BARRELS = tag("barrels/iron");
    public static final TagKey<Item> SILVER_BARRELS = tag("barrels/silver");
    public static final TagKey<Item> GOLD_BARRELS = tag("barrels/gold");
    public static final TagKey<Item> DIAMOND_BARRELS = tag("barrels/diamond");
    public static final TagKey<Item> OBSIDIAN_BARRELS = tag("barrels/obsidian");
    public static TagKey<Item> tag(String name) {
      return TagKey.create(Registries.ITEM, new ResourceLocation("forge",name));
    }
  }

  public static class Blocks {
    public static final TagKey<Block> WOOD_BARRELS = tag("barrels/wood");
    public static final TagKey<Block> COPPER_BARRELS = tag("barrels/copper");
    public static final TagKey<Block> IRON_BARRELS = tag("barrels/iron");
    public static final TagKey<Block> SILVER_BARRELS = tag("barrels/silver");
    public static final TagKey<Block> GOLD_BARRELS = tag("barrels/gold");
    public static final TagKey<Block> DIAMOND_BARRELS = tag("barrels/diamond");
    public static final TagKey<Block> OBSIDIAN_BARRELS = tag("barrels/obsidian");

    private static TagKey<Block> tag(String name) {
      return TagKey.create(Registries.BLOCK, new ResourceLocation("forge",name));
    }
  }
}
