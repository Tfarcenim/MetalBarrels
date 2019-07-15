package com.tfar.metalbarrels.container;

import com.tfar.metalbarrels.MetalBarrels;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.IWorldPosCallable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nonnull;

public class GoldBarrelContainer extends AbstractBarrelContainer {

public GoldBarrelContainer(int id, World world, BlockPos pos, PlayerInventory playerInventory, PlayerEntity player){
  super(MetalBarrels.ObjectHolders.GOLD_CONTAINER,id,world,pos,playerInventory,player,
          9,9,8,18, 194);
}

  @Override
  public boolean canInteractWith(@Nonnull PlayerEntity playerIn) {
    return isWithinUsableDistance(IWorldPosCallable.of(tileEntity.getWorld(), tileEntity.getPos()), playerEntity, MetalBarrels.ObjectHolders.GOLD_BARREL);
  }
}
