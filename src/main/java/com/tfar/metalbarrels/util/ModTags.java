package com.tfar.metalbarrels.util;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.Tag;

public class ModTags {

  public static class Blocks {
    public static final TagKey<Block> WOODEN_BARRELS = tag("barrels/wooden");
    public static final TagKey<Block> COPPER_BARRELS = tag("barrels/copper");
    public static final TagKey<Block> IRON_BARRELS = tag("barrels/iron");
    public static final TagKey<Block> SILVER_BARRELS = tag("barrels/silver");
    public static final TagKey<Block> GOLD_BARRELS = tag("barrels/gold");
    public static final TagKey<Block> DIAMOND_BARRELS = tag("barrels/diamond");
    public static final TagKey<Block> OBSIDIAN_BARRELS = tag("barrels/obsidian");
    public static final TagKey<Block> COPPER_CHESTS = tag("chests/copper");
    public static final TagKey<Block> IRON_CHESTS = tag("chests/iron");
    public static final TagKey<Block> SILVER_CHESTS = tag("chests/silver");
    public static final TagKey<Block> GOLD_CHESTS = tag("chests/gold");
    public static final TagKey<Block> DIAMOND_CHESTS = tag("chests/diamond");

    private static TagKey<Block> tag(String name) {
      return TagKey.create(Registry.BLOCK_REGISTRY, new ResourceLocation("forge",name));
    }
  }
}
