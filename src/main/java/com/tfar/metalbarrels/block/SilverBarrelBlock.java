package com.tfar.metalbarrels.block;

import com.tfar.metalbarrels.tiles.SilverBarrelTile;
import net.minecraft.block.BlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockReader;

import javax.annotation.Nullable;

public class SilverBarrelBlock extends AbstractBarrelBlock {

  public SilverBarrelBlock(Properties properties) {
    super(properties);
  }

  @Nullable
  @Override
  public TileEntity createTileEntity(BlockState state, IBlockReader world) {
    return new SilverBarrelTile();
  }

}
