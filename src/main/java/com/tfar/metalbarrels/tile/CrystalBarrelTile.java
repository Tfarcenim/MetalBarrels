package com.tfar.metalbarrels.tile;

import com.tfar.metalbarrels.MetalBarrels;
import com.tfar.metalbarrels.container.DiamondBarrelContainer;
import com.tfar.metalbarrels.network.PacketHandler;
import com.tfar.metalbarrels.network.PacketTopStackSyncChest;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.fml.network.PacketDistributor;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class CrystalBarrelTile extends AbstractBarrelTile implements ITickableTileEntity {

  public NonNullList<ItemStack> topStacks;

  private boolean inventoryTouched;

  private boolean hadStuff;


  public CrystalBarrelTile() {
    super(MetalBarrels.ObjectHolders.CRYSTAL_TILE, 12, 9);
    this.topStacks = NonNullList.withSize(8, ItemStack.EMPTY);
  }

  @Override
  protected ITextComponent getDefaultName() {
    return new TranslationTextComponent("metalbarrels.crystal_barrel");
  }

  @Nullable
  @Override
  public Container createMenu(int id, @Nonnull PlayerInventory playerInventory, @Nonnull PlayerEntity player) {
    return new DiamondBarrelContainer(id, world, pos, playerInventory, player);
  }

  @Override
  public void tick() {
    //   super.tick();

    if (!this.world.isRemote && this.inventoryTouched) {
      this.inventoryTouched = false;

      this.sortTopStacks();
    }
  }

  private void sortTopStacks() {
    if (this.world != null && this.world.isRemote) {
      return;
    }

    NonNullList<ItemStack> tempCopy = NonNullList.withSize(this.handler.getSlots(), ItemStack.EMPTY);

    boolean hasStuff = false;

    int compressedIdx = 0;

    mainLoop:
    for (int i = 0; i < this.handler.getSlots(); i++) {
      ItemStack itemStack = this.handler.getStackInSlot(i);

      if (!itemStack.isEmpty()) {
        for (int j = 0; j < compressedIdx; j++) {
          ItemStack tempCopyStack = tempCopy.get(j);

          if (ItemStack.areItemsEqualIgnoreDurability(tempCopyStack, itemStack)) {
            if (itemStack.getCount() != tempCopyStack.getCount()) {
              tempCopyStack.grow(itemStack.getCount());
            }

            continue mainLoop;
          }
        }

        tempCopy.set(compressedIdx, itemStack.copy());

        compressedIdx++;

        hasStuff = true;
      }
    }

    if (!hasStuff && this.hadStuff) {
      this.hadStuff = false;

      for (int i = 0; i < this.topStacks.size(); i++) {
        this.topStacks.set(i, ItemStack.EMPTY);
      }

      if (this.world != null) {
        BlockState iblockstate = this.world.getBlockState(this.pos);

        this.world.notifyBlockUpdate(this.pos, iblockstate, iblockstate, 3);
      }

      return;
    }

    this.hadStuff = true;

    tempCopy.sort((stack1, stack2) -> stack1.isEmpty() ? 1 : stack2.isEmpty() ? -1 : stack2.getCount() - stack1.getCount());

    int p = 0;

    for (ItemStack element : tempCopy) {
      if (!element.isEmpty() && element.getCount() > 0) {
        if (p == this.topStacks.size()) {
          break;
        }

        this.topStacks.set(p, element);

        p++;
      }
    }

    for (int i = p; i < this.topStacks.size(); i++) {
      this.topStacks.set(i, ItemStack.EMPTY);
    }

    if (this.world != null) {
      BlockState iblockstate = this.world.getBlockState(this.pos);

      this.world.notifyBlockUpdate(this.pos, iblockstate, iblockstate, 3);
    }

    sendTopStacksPacket();
  }

  private NonNullList<ItemStack> buildItemStackDataList() {
    NonNullList<ItemStack> sortList = NonNullList.withSize(this.topStacks.size(), ItemStack.EMPTY);

    int pos = 0;

    for (ItemStack is : this.topStacks) {
      if (!is.isEmpty()) {
        sortList.set(pos, is);
      } else {
        sortList.set(pos, ItemStack.EMPTY);
      }

      pos++;
    }

    return sortList;

  }

  protected void sendTopStacksPacket() {
    NonNullList<ItemStack> stacks = this.buildItemStackDataList();

    PacketHandler.send(PacketDistributor.TRACKING_CHUNK.with(() -> (Chunk) this.getWorld().getChunk(this.getPos())), new PacketTopStackSyncChest(this.getWorld().getDimension().getType().getId(), this.getPos(), stacks));
  }

  @Override
  public void markDirty() {
    super.markDirty();
    this.inventoryTouched = true;
  }

  public void receiveMessageFromServer(NonNullList<ItemStack> topStacks) {
    this.topStacks = topStacks;
  }
}


