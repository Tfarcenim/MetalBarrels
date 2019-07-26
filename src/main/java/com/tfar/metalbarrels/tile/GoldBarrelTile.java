package com.tfar.metalbarrels.tile;

import com.tfar.metalbarrels.MetalBarrels;
import com.tfar.metalbarrels.container.GoldBarrelContainer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

import javax.annotation.Nullable;

public class GoldBarrelTile extends AbstractBarrelTile {
  public GoldBarrelTile() {
    super(MetalBarrels.ObjectHolders.GOLD_TILE,9,9);
  }

  @Override
  protected ITextComponent getDefaultName() {
    return new TranslationTextComponent("metalbarrels.gold_barrel");
  }

  @Nullable
  @Override
  public Container createMenu(int id, PlayerInventory playerInventory, PlayerEntity player) {
    return new GoldBarrelContainer(id, world, pos, playerInventory, player);
  }
}


