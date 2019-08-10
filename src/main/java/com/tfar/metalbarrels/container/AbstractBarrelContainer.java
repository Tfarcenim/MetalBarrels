package com.tfar.metalbarrels.container;

import com.tfar.metalbarrels.MetalBarrels;
import com.tfar.metalbarrels.tile.AbstractBarrelTile;
import net.minecraft.block.BarrelBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import net.minecraftforge.items.wrapper.InvWrapper;

import javax.annotation.Nonnull;

public abstract class AbstractBarrelContainer extends Container {

  public AbstractBarrelTile tileEntity;
  protected PlayerEntity playerEntity;
  protected IItemHandler playerInventory;
  private int width;
  private int height;

  public AbstractBarrelContainer(ContainerType<?> containerType,
                                 int id, World world, BlockPos pos, PlayerInventory playerInventory, PlayerEntity player,
                                 int width, int height, int containerX, int containerY, int playerY) {this
          (containerType,id,world,pos,playerInventory,player,width,height,containerX,containerY,playerY,8);}

  public AbstractBarrelContainer(ContainerType<?> containerType,
                                 int id, World world, BlockPos pos, PlayerInventory playerInventory, PlayerEntity player,
                                 int width, int height, int containerX, int containerY, int playerY, int playerX) {
    super(containerType, id);
    this.width = width;
    this.height = height;
    tileEntity = (AbstractBarrelTile)world.getTileEntity(pos);
    world.setBlockState(pos, tileEntity.getBlockState().with(BarrelBlock.PROPERTY_OPEN, true), 3);
    if (tileEntity.players == 0) {
      this.tileEntity.soundStuff(tileEntity.getBlockState(), SoundEvents.BLOCK_BARREL_OPEN);
      this.tileEntity.changeState(tileEntity.getBlockState(), true);
    }
    tileEntity.players++;
    this.playerEntity = player;

    for (int i = 0; i < height; i++)
      for (int j = 0; j < width; j++)
        addSlot(new SlotItemHandler(tileEntity.handler,
                j + width * i, containerX + j * 18, containerY + i * 18));
    this.playerInventory = new InvWrapper(playerInventory);

    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 9; j++) {
        this.addSlot(new Slot(playerInventory, j + i * 9 + 9, j * 18 + playerX, i * 18 + playerY));
      }
    }

    for (int i = 0; i < 9; i++) {
      this.addSlot(new Slot(playerInventory, i, i * 18 + playerX, playerY + 58));
    }
  }

  @Nonnull
  @Override
  public ItemStack transferStackInSlot(PlayerEntity playerIn, int index) {
    ItemStack itemstack = ItemStack.EMPTY;
    Slot slot = this.inventorySlots.get(index);
    if (slot != null && slot.getHasStack()) {
      ItemStack itemstack1 = slot.getStack();
      itemstack = itemstack1.copy();
      if (index < this.height * this.width) {
        if (!this.mergeItemStack(itemstack1, this.height * this.width, this.inventorySlots.size(), true)) {
          return ItemStack.EMPTY;
        }
      } else if (!this.mergeItemStack(itemstack1, 0, this.height * this.width, false)) {
        return ItemStack.EMPTY;
      }

      if (itemstack1.isEmpty()) {
        slot.putStack(ItemStack.EMPTY);
      } else {
        slot.onSlotChanged();
      }
    }
    return itemstack;
  }

  /**
   * Called when the container is closed.
   */
  public void onContainerClosed(PlayerEntity playerIn) {
    if (tileEntity == null){
      MetalBarrels.logger.warn("unexpected null on container close");
      return;
    }
    if (!playerIn.isSpectator()) {
      --this.tileEntity.players;
    }
    if (tileEntity.players <= 0){
      tileEntity.soundStuff(tileEntity.getBlockState(),SoundEvents.BLOCK_BARREL_CLOSE);
      tileEntity.changeState(playerIn.world.getBlockState(tileEntity.getPos()),false);
    }
  }
}

