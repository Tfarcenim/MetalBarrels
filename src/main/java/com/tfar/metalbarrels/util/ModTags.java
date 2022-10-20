package com.tfar.metalbarrels.util;

import net.minecraft.world.level.block.Block;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.Tag;

public class ModTags {

  public static class Blocks {
    public static final Tag.Named<Block> WOODEN_BARRELS = tag("barrels/wooden");
    public static final Tag.Named<Block> COPPER_BARRELS = tag("barrels/copper");
    public static final Tag.Named<Block> IRON_BARRELS = tag("barrels/iron");
    public static final Tag.Named<Block> SILVER_BARRELS = tag("barrels/silver");
    public static final Tag.Named<Block> GOLD_BARRELS = tag("barrels/gold");
    public static final Tag.Named<Block> DIAMOND_BARRELS = tag("barrels/diamond");
    public static final Tag.Named<Block> OBSIDIAN_BARRELS = tag("barrels/obsidian");
    public static final Tag.Named<Block> COPPER_CHESTS = tag("chests/copper");
    public static final Tag.Named<Block> IRON_CHESTS = tag("chests/iron");
    public static final Tag.Named<Block> SILVER_CHESTS = tag("chests/silver");
    public static final Tag.Named<Block> GOLD_CHESTS = tag("chests/gold");
    public static final Tag.Named<Block> DIAMOND_CHESTS = tag("chests/diamond");

    private static Tag.Named<Block> tag(String name) {
      return BlockTags.bind("forge:"+name);
    }
  }
}
