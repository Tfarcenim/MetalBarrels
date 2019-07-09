package com.tfar.metalbarrels.utils;

import java.util.Random;

import com.tfar.metalbarrels.tiles.AbstractBarrelTile;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nullable;

public class InventoryHelper {
  private static final Random RANDOM = new Random();

  public static void dropInventoryItems(World worldIn, BlockPos pos, ItemStackHandler inventory) {
    dropInventoryItems(worldIn, pos.getX(), pos.getY(), pos.getZ(), inventory);
  }

  public static void dropInventoryItems(World worldIn, Entity entityAt, ItemStackHandler inventory) {
    dropInventoryItems(worldIn, entityAt.posX, entityAt.posY, entityAt.posZ, inventory);
  }

  private static void dropInventoryItems(World worldIn, double x, double y, double z, ItemStackHandler inventory) {
    for(int i = 0; i < inventory.getSlots(); ++i) {
      spawnItemStack(worldIn, x, y, z, inventory.getStackInSlot(i));
    }

  }

  public static void dropItems(World p_219961_0_, BlockPos p_219961_1_, NonNullList<ItemStack> p_219961_2_) {
    p_219961_2_.forEach((p_219962_2_) -> {
      spawnItemStack(p_219961_0_, p_219961_1_.getX(), p_219961_1_.getY(), p_219961_1_.getZ(), p_219962_2_);
    });
  }

  public static void spawnItemStack(World worldIn, double x, double y, double z, ItemStack stack) {
    double d0 = EntityType.ITEM.getWidth();
    double d1 = 1.0D - d0;
    double d2 = d0 / 2.0D;
    double d3 = Math.floor(x) + RANDOM.nextDouble() * d1 + d2;
    double d4 = Math.floor(y) + RANDOM.nextDouble() * d1;
    double d5 = Math.floor(z) + RANDOM.nextDouble() * d1 + d2;

    while(!stack.isEmpty()) {
      ItemEntity itementity = new ItemEntity(worldIn, d3, d4, d5, stack.split(RANDOM.nextInt(21) + 10));
      float f = 0.05F;
      itementity.setMotion(RANDOM.nextGaussian() * f, RANDOM.nextGaussian() * f + 0.2, RANDOM.nextGaussian() * f);
      worldIn.addEntity(itementity);
    }
  }

  /**
   * Like the version that takes an inventory. If the given TileEntity is not an Inventory, 0 is returned instead.
   */
  public static int calcRedstone(@Nullable TileEntity te) {
    return te instanceof AbstractBarrelTile ? calcRedstoneFromInventory(((AbstractBarrelTile) te).handler) : 0;
  }

  public static int calcRedstoneFromInventory(@Nullable ItemStackHandler inv) {
    if (inv == null) {
      return 0;
    } else {
      int i = 0;
      float f = 0;

      for(int j = 0; j < inv.getSlots(); ++j) {
        ItemStack itemstack = inv.getStackInSlot(j);
        if (!itemstack.isEmpty()) {
          f += (float)itemstack.getCount() / Math.min(inv.getSlotLimit(j), itemstack.getMaxStackSize());
          ++i;
        }
      }
      f = f / (float)inv.getSlots();
      return MathHelper.floor(f * 14.0F) + (i > 0 ? 1 : 0);
    }
  }
}