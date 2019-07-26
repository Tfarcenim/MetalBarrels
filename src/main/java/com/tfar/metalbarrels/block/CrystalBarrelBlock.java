package com.tfar.metalbarrels.block;

import com.tfar.metalbarrels.tile.CrystalBarrelTile;
import net.minecraft.block.BlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.world.IBlockReader;

import javax.annotation.Nonnull;
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

  @Nonnull
  @Override
  public BlockRenderLayer getRenderLayer() {
    return BlockRenderLayer.CUTOUT;
  }

  @Override
  public int getHarvestLevel(BlockState state) {
    return 2;
  }
}
