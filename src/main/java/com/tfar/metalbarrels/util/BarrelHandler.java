package com.tfar.metalbarrels.util;

import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.items.ItemStackHandler;

import java.util.List;
import java.util.stream.IntStream;

public class BarrelHandler extends ItemStackHandler {

  public TileEntity tileEntity;

  public BarrelHandler(int size, TileEntity tileEntity) {
    super(size);
    this.tileEntity = tileEntity;
  }

  @Override
  protected void onContentsChanged(int slot) {
    this.tileEntity.markDirty();
  }

  public List<ItemStack> getContents() {
    return this.stacks;
  }

  public void setContents(List<ItemStack> oldContents, int newSize) {
    this.setSize(newSize);
    IntStream.range(0, oldContents.size()).forEach(i -> stacks.set(i, oldContents.get(i)));
  }
}

