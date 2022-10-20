package com.tfar.metalbarrels.container;

import com.tfar.metalbarrels.MetalBarrels;
import com.tfar.metalbarrels.tile.MetalBarrelBlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IWorldPosCallable;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.World;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.SlotItemHandler;

import javax.annotation.Nonnull;

public class MetalBarrelContainer extends Container {

  protected PlayerEntity playerEntity;
	private final IWorldPosCallable callable;
	public int width;
  public int height;

  //client
  public MetalBarrelContainer(ContainerType<?> containerType,
															int id, PlayerInventory playerInventory,
															int width, int height, int containerX, int containerY, int playerX, int playerY) {this
          (containerType,id, playerInventory, width,height,containerX,containerY,playerY,playerX,IWorldPosCallable.NULL);}

  public MetalBarrelContainer(ContainerType<?> containerType,
															int id, PlayerInventory playerInventory,
															int width, int height, int containerX, int containerY, int playerY, int playerX,IWorldPosCallable callable) {
    super(containerType, id);
    this.width = width;
    this.height = height;
		this.playerEntity = playerInventory.player;
		this.callable = callable;

		ItemStackHandler stackHandler = callable.evaluate(World::getBlockEntity).map(MetalBarrelBlockEntity.class::cast)
						.map(metalBarrelBlockEntity -> metalBarrelBlockEntity.handler)
						.orElse(new ItemStackHandler(width * height));

		for (int i = 0; i < height; i++)
      for (int j = 0; j < width; j++)
        addSlot(new SlotItemHandler(stackHandler,
                j + width * i, containerX + j * 18, containerY + i * 18));

    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 9; j++) {
        this.addSlot(new Slot(playerInventory, j + i * 9 + 9, j * 18 + playerX, i * 18 + playerY));
      }
    }

    for (int i = 0; i < 9; i++) {
      this.addSlot(new Slot(playerInventory, i, i * 18 + playerX, playerY + 58));
    }
  }

  public static MetalBarrelContainer copper(int id, PlayerInventory playerInventory) {
  	return new MetalBarrelContainer(MetalBarrels.ObjectHolders.COPPER_CONTAINER,id,playerInventory, 9,5,8,18, 8,122);
	}

	public static MetalBarrelContainer iron(int id, PlayerInventory playerInventory) {
		return new MetalBarrelContainer(MetalBarrels.ObjectHolders.IRON_CONTAINER,id,playerInventory,
						9,6,8,18, 8,140);
	}

	public static MetalBarrelContainer silver(int id, PlayerInventory playerInventory) {
		return new MetalBarrelContainer(MetalBarrels.ObjectHolders.SILVER_CONTAINER,id,playerInventory,
						9,8,8,18, 8,176);
	}

	public static MetalBarrelContainer gold(int id, PlayerInventory playerInventory) {
		return new MetalBarrelContainer(MetalBarrels.ObjectHolders.GOLD_CONTAINER,id,playerInventory,
						9,9,8,18, 8,194);
	}

	public static MetalBarrelContainer diamond(int id, PlayerInventory playerInventory) {
		return new MetalBarrelContainer(MetalBarrels.ObjectHolders.DIAMOND_CONTAINER,id, playerInventory,
						12,9,8,18, 35,194);
	}

	public static MetalBarrelContainer netherite(int id, PlayerInventory playerInventory) {
		return new MetalBarrelContainer(MetalBarrels.ObjectHolders.NETHERITE_CONTAINER,id, playerInventory,
						15,9,8,18, 62, 194);
	}

	//////////////////////////

	public static MetalBarrelContainer copperS(int id, PlayerInventory playerInventory,IWorldPosCallable callable) {
		return new MetalBarrelContainer(MetalBarrels.ObjectHolders.COPPER_CONTAINER,id,playerInventory, 9,5,8,18, 8,122,callable);
	}

	public static MetalBarrelContainer ironS(int id, PlayerInventory playerInventory,IWorldPosCallable callable) {
		return new MetalBarrelContainer(MetalBarrels.ObjectHolders.IRON_CONTAINER,id,playerInventory,
						9,6,8,18, 8,140,callable);
	}

	public static MetalBarrelContainer silverS(int id, PlayerInventory playerInventory,IWorldPosCallable callable) {
		return new MetalBarrelContainer(MetalBarrels.ObjectHolders.SILVER_CONTAINER,id,playerInventory,
						9,8,8,18, 8,176,callable);
	}

	public static MetalBarrelContainer goldS(int id, PlayerInventory playerInventory,IWorldPosCallable callable) {
		return new MetalBarrelContainer(MetalBarrels.ObjectHolders.GOLD_CONTAINER,id,playerInventory,
						9,9,8,18, 8,194,callable);
	}

	public static MetalBarrelContainer diamondS(int id, PlayerInventory playerInventory,IWorldPosCallable callable) {
		return new MetalBarrelContainer(MetalBarrels.ObjectHolders.DIAMOND_CONTAINER,id, playerInventory,
						12,9,8,18, 35,194,callable);
	}

	public static MetalBarrelContainer netheriteS(int id, PlayerInventory playerInventory,IWorldPosCallable callable) {
		return new MetalBarrelContainer(MetalBarrels.ObjectHolders.NETHERITE_CONTAINER,id, playerInventory,
						15,9,8,18, 62, 194,callable);
	}

  @Override
  public boolean stillValid(@Nonnull PlayerEntity playerIn) {
    return true;
  }

  @Nonnull
  @Override
  public ItemStack quickMoveStack(PlayerEntity playerIn, int index) {
    ItemStack itemstack = ItemStack.EMPTY;
    Slot slot = this.slots.get(index);
    if (slot != null && slot.hasItem()) {
      ItemStack itemstack1 = slot.getItem();
      itemstack = itemstack1.copy();
      if (index < this.height * this.width) {
        if (!this.moveItemStackTo(itemstack1, this.height * this.width, this.slots.size(), true)) {
          return ItemStack.EMPTY;
        }
      } else if (!this.moveItemStackTo(itemstack1, 0, this.height * this.width, false)) {
        return ItemStack.EMPTY;
      }

      if (itemstack1.isEmpty()) {
        slot.set(ItemStack.EMPTY);
      } else {
        slot.setChanged();
      }
    }
    return itemstack;
  }

  /**
   * Called when the container is closed.
   */
  public void removed(PlayerEntity playerIn) {
  	super.removed(playerIn);
		this.callable.execute((world, pos) -> {
			TileEntity tileEntity = world.getBlockEntity(pos);
			if (tileEntity == null) {
				MetalBarrels.logger.warn("unexpected null on container close");
				return;
			}
			MetalBarrelBlockEntity metalBarrelBlockEntity = (MetalBarrelBlockEntity) tileEntity;
			if (!playerIn.isSpectator()) {
				--metalBarrelBlockEntity.players;
			}
			if (metalBarrelBlockEntity.players <= 0) {
				metalBarrelBlockEntity.soundStuff(tileEntity.getBlockState(), SoundEvents.BARREL_CLOSE);
				metalBarrelBlockEntity.changeState(playerIn.level.getBlockState(tileEntity.getBlockPos()), false);
			}
		});
	}
}

