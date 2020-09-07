package com.tfar.metalbarrels.util;

import net.minecraft.block.Block;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ITag;

public class ModTags {

  public static class Blocks {
    public static final ITag.INamedTag<Block> WOODEN_BARRELS = tag("barrels/wooden");
    public static final ITag.INamedTag<Block> COPPER_BARRELS = tag("barrels/copper");
    public static final ITag.INamedTag<Block> IRON_BARRELS = tag("barrels/iron");
    public static final ITag.INamedTag<Block> SILVER_BARRELS = tag("barrels/silver");
    public static final ITag.INamedTag<Block> GOLD_BARRELS = tag("barrels/gold");
    public static final ITag.INamedTag<Block> DIAMOND_BARRELS = tag("barrels/diamond");

    public static final ITag.INamedTag<Block> COPPER_CHESTS = tag("chests/copper");
    public static final ITag.INamedTag<Block> IRON_CHESTS = tag("chests/iron");
    public static final ITag.INamedTag<Block> SILVER_CHESTS = tag("chests/silver");
    public static final ITag.INamedTag<Block> GOLD_CHESTS = tag("chests/gold");
    public static final ITag.INamedTag<Block> DIAMOND_CHESTS = tag("chests/diamond");

    private static ITag.INamedTag<Block> tag(String name) {
      return BlockTags.makeWrapperTag("forge:"+name);
    }
  }
}
