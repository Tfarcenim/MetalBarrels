package com.tfar.metalbarrels.block;

import com.tfar.metalbarrels.tiles.CopperBarrelTile;

import net.minecraft.block.BlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockReader;

import javax.annotation.Nullable;

public class CopperBarrelBlock extends AbstractBarrelBlock {

  public CopperBarrelBlock(Properties properties) {
    super(properties);
  }

  @Nullable
  @Override
  public TileEntity createTileEntity(BlockState state, IBlockReader world) {
    return new CopperBarrelTile();
  }

}
