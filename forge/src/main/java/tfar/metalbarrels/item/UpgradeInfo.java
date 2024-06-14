package tfar.metalbarrels.item;

import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class UpgradeInfo {

  public final TagKey<Block> start_blocks;
  public final Block end_block;

  public UpgradeInfo(TagKey<Block> start_blocks, Block end_block) {
    this.start_blocks = start_blocks;
    this.end_block = end_block;
  }

  public Block getBlock(BlockState toUpgrade) {
    return end_block;
  }

  public boolean canUpgrade(BlockState barrel){
    return barrel.is(start_blocks);
  }
}