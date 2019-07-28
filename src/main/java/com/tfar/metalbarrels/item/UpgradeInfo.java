package com.tfar.metalbarrels.item;

import net.minecraft.block.Block;
import net.minecraft.tags.Tag;

public class UpgradeInfo {

  public final Tag<Block> start_block;
  public final Block end_block;

  public UpgradeInfo(Tag<Block> start_block, Block end_block) {
    this.start_block = start_block;
    this.end_block = end_block;
  }
  public boolean canUpgrade(Block barrel){
    return barrel.isIn(start_block);
  }
}