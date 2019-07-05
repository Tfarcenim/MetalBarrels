package com.tfar.metalbarrels.tiles;

import com.tfar.metalbarrels.MetalBarrels;
import com.tfar.metalbarrels.container.DiamondBarrelContainer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

import javax.annotation.Nullable;

public class DiamondBarrelTile extends AbstractBarrelTile {
  public DiamondBarrelTile() {
    super(MetalBarrels.ObjectHolders.DIAMOND_TILE,12,9);
  }

  @Override
  public ITextComponent getDisplayName() {
    if (this.getBlockState().getBlock() == MetalBarrels.ObjectHolders.OBSIDIAN_BARREL)
      return new TranslationTextComponent("metalbarrels.obsidian_barrel");
    return new TranslationTextComponent("metalbarrels.diamond_barrel");
  }

  @Nullable
  @Override
  public Container createMenu(int id, PlayerInventory playerInventory, PlayerEntity player) {
    return new DiamondBarrelContainer(id, world, pos, playerInventory, player);
  }
}


