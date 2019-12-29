package com.tfar.metalbarrels.block;

import com.tfar.metalbarrels.tile.CrystalBarrelTile;
import net.minecraft.block.BlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockReader;

import javax.annotation.Nullable;

public class CrystalBarrelBlock extends AbstractBarrelBlock {

  public CrystalBarrelBlock(Properties properties) {
    super(properties);
  }

  @Nullable
  @Override
  public TileEntity createTileEntity(BlockState state, IBlockReader world) {
    return new CrystalBarrelTile();
  }
}
