package com.tfar.metalbarrels.container;

import com.tfar.metalbarrels.block.AbstractBarrelBlock;
import com.tfar.metalbarrels.tiles.AbstractBarrelTile;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import net.minecraftforge.items.wrapper.InvWrapper;

import javax.annotation.Nonnull;

public abstract class AbstractBarrelContainer extends Container {

  public TileEntity tileEntity;
  protected PlayerEntity playerEntity;
  protected IItemHandler playerInventory;
  private int width;
  private int height;

  public AbstractBarrelContainer(ContainerType<?> containerType,
                                 int id, World world, BlockPos pos, PlayerInventory playerInventory, PlayerEntity player,
                                 int width, int height, int containerX, int containerY, int playerY) {
    super(containerType, id);
    this.width = width;
    this.height = height;
    tileEntity = world.getTileEntity(pos);
    world.setBlockState(pos, tileEntity.getBlockState().with(AbstractBarrelBlock.OPEN, true), 3);
    this.playerEntity = player;

    for (int i = 0; i < height; i++)
      for (int j = 0; j < width; j++)
        addSlot(new SlotItemHandler(((AbstractBarrelTile) tileEntity).handler,
                j + width * i, containerX + j * 18, containerY + i * 18));
    this.playerInventory = new InvWrapper(playerInventory);

    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 9; j++) {
        this.addSlot(new Slot(playerInventory, j + i * 9 + 9, 8 + j * 18, i * 18 + playerY));
      }
    }

    for (int i = 0; i < 9; i++) {
      this.addSlot(new Slot(playerInventory, i, 8 + i * 18, playerY + 58));
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
    tileEntity.getWorld().setBlockState(this.tileEntity.getPos(), tileEntity.getBlockState().with(AbstractBarrelBlock.OPEN, false), 3);
  }
}
