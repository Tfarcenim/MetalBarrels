package com.tfar.metalbarrels.block;

import com.tfar.metalbarrels.MetalBarrels;
import com.tfar.metalbarrels.tile.DiamondBarrelTile;
import net.minecraft.block.BlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockReader;

import javax.annotation.Nullable;

public class DiamondBarrelBlock extends AbstractBarrelBlock {

  public DiamondBarrelBlock(Properties properties) {
    super(properties);
  }

  @Nullable
  @Override
  public TileEntity createTileEntity(BlockState state, IBlockReader world) {
    return new DiamondBarrelTile();
  }

  @Override
  public int getHarvestLevel(BlockState state) {
    return state.getBlock() == MetalBarrels.ObjectHolders.OBSIDIAN_BARREL ? 3 : 2;
  }
}
