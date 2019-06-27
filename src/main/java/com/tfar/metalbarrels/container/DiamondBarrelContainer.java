package com.tfar.metalbarrels.container;

import com.tfar.metalbarrels.MetalBarrels;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.IWorldPosCallable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nonnull;

public class DiamondBarrelContainer extends AbstractBarrelContainer {

public DiamondBarrelContainer(int id, World world, BlockPos pos, PlayerInventory playerInventory, PlayerEntity player){
  super(MetalBarrels.ObjectHolders.DIAMOND_CONTAINER,id,world,pos,playerInventory,player,
          12,9,-19,-37,8,139);
}

  @Override
  public boolean canInteractWith(@Nonnull PlayerEntity playerIn) {
    return isWithinUsableDistance(IWorldPosCallable.of(tileEntity.getWorld(), tileEntity.getPos()), playerEntity, MetalBarrels.ObjectHolders.DIAMOND_BARREL)
            || isWithinUsableDistance(IWorldPosCallable.of(tileEntity.getWorld(), tileEntity.getPos()), playerEntity, MetalBarrels.ObjectHolders.OBSIDIAN_BARREL);
  }
}
