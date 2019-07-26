package com.tfar.metalbarrels.util;

import net.minecraft.block.Block;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.Tag;
import net.minecraft.util.ResourceLocation;

public class Tags {

  public static class Blocks {
    public static final Tag<Block> WOOD_BARRELS = tag("barrels");
    public static final Tag<Block> COPPER_BARRELS = tag("barrels/copper");
    public static final Tag<Block> IRON_BARRELS = tag("barrels/iron");
    public static final Tag<Block> SILVER_BARRELS = tag("barrels/silver");
    public static final Tag<Block> GOLD_BARRELS = tag("barrels/gold");
    public static final Tag<Block> DIAMOND_BARRELS = tag("barrels/diamond");

    private static Tag<Block> tag(String name) {
      return new BlockTags.Wrapper(new ResourceLocation("forge", name));
    }
  }
}
