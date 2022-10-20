package com.tfar.metalbarrels.item;

import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.fml.ModList;

import java.util.List;
import java.util.stream.IntStream;

public class UpgradeInfo {

  private final List<TagKey<Block>> start_blocks;
  private final List<Block> end_blocks;

  public UpgradeInfo(List<TagKey<Block>> start_blocks, List<Block> end_blocks) {
    this.start_blocks = start_blocks;
    this.end_blocks = end_blocks;
  }

  public int getIndex(BlockState barrel){
    for (int i = 0; i < start_blocks.size() ; i++){
      if (barrel.is(start_blocks.get(i)))return i;
    }
    throw new IllegalArgumentException(barrel +" is not valid for upgrading!");
  }

  public Block getBlock(BlockState toUpgrade){
    return end_blocks.get(getIndex(toUpgrade));
  }

  public UpgradeInfo add(TagKey<Block> start, Block end,String modid){
    if (ModList.get().isLoaded(modid)) {
      start_blocks.add(start);
      end_blocks.add(end);
    }
    return this;
  }

  public boolean canUpgrade(BlockState barrel){
    return IntStream.range(0, start_blocks.size()).anyMatch(i -> barrel.is(start_blocks.get(i)));
  }
}