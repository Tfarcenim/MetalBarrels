package com.tfar.metalbarrels.utils;

import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.items.ItemStackHandler;

public class BarrelHandler extends ItemStackHandler {

  public TileEntity tileEntity;

  public BarrelHandler(int size, TileEntity tileEntity){
    super(size);
    this.tileEntity = tileEntity;
  }
  @Override
  protected void onContentsChanged(int slot)
  {
    this.tileEntity.markDirty();
  }
}
