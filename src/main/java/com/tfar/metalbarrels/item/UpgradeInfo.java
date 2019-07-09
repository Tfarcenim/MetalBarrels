package com.tfar.metalbarrels.item;

import com.tfar.metalbarrels.tiles.AbstractBarrelTile;
import net.minecraft.block.Block;
import net.minecraft.tags.Tag;

public class UpgradeInfo {

  public final Tag<Block> start_block;
  public final Block end_block;
  public final AbstractBarrelTile tile;

  public UpgradeInfo(Tag<Block> start_block, Block end_block, AbstractBarrelTile newTileEntity) {
    this.start_block = start_block;
    this.end_block = end_block;
    this.tile = newTileEntity;
  }
  public boolean canUpgrade(Block barrel){
    return barrel.isIn(start_block);
  }
}