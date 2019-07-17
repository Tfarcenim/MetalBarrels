package com.tfar.metalbarrels.container;

import com.tfar.metalbarrels.block.AbstractBarrelBlock;
import com.tfar.metalbarrels.tiles.AbstractBarrelTile;
import net.minecraft.block.BarrelBlock;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;
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

  protected int playerCount = 0;

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
    tileEntity = world.getTileEntity(pos);
    world.setBlockState(pos, tileEntity.getBlockState().with(AbstractBarrelBlock.OPEN, true), 3);
    if (playerCount == 0) func_213965_a(world, pos, tileEntity.getBlockState(), SoundEvents.BLOCK_BARREL_OPEN);
    playerCount++;
    this.playerEntity = player;

    for (int i = 0; i < height; i++)
      for (int j = 0; j < width; j++)
        addSlot(new SlotItemHandler(((AbstractBarrelTile) tileEntity).handler,
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

  private void func_213965_a(World w, BlockPos pos, BlockState p_213965_1_, SoundEvent p_213965_2_) {
    Vec3i vec3i = p_213965_1_.get(AbstractBarrelBlock.FACING).getDirectionVec();
    double d0 = pos.getX() + 0.5 + vec3i.getX() / 2d;
    double d1 = pos.getY() + 0.5 + vec3i.getY() / 2d;
    double d2 = pos.getZ() + 0.5 + vec3i.getZ() / 2d;
    w.playSound(null, d0, d1, d2, p_213965_2_, SoundCategory.BLOCKS, 0.5F, w.rand.nextFloat() * 0.1F + 0.9F);
  }

  /**
   * Called when the container is closed.
   */
  public void onContainerClosed(PlayerEntity playerIn) {
    playerCount--;
    if (tileEntity == null)return;
    func_213965_a(tileEntity.getWorld(), tileEntity.getPos(), tileEntity.getBlockState(), SoundEvents.BLOCK_BARREL_CLOSE);
    tileEntity.getWorld().setBlockState(this.tileEntity.getPos(), tileEntity.getBlockState().with(AbstractBarrelBlock.OPEN, false), 3);
  }
}

