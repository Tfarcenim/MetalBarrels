package tfar.metalbarrels.container;

import tfar.metalbarrels.MetalBarrels;
import tfar.metalbarrels.init.ModMenuTypes;
import tfar.metalbarrels.tile.MetalBarrelBlockEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.Level;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.SlotItemHandler;

import javax.annotation.Nonnull;

public class MetalBarrelContainer extends AbstractContainerMenu {

  protected Player playerEntity;
	private final ContainerLevelAccess callable;
	public int width;
  public int height;
  public MetalBarrelContainer(MenuType<?> containerType,
															int id, Inventory playerInventory,
															int width, int height, int containerX, int containerY, int playerX, int playerY,ContainerLevelAccess callable) {
    super(containerType, id);
    this.width = width;
    this.height = height;
		this.playerEntity = playerInventory.player;
		this.callable = callable;

		ItemStackHandler stackHandler = callable.evaluate(Level::getBlockEntity).map(MetalBarrelBlockEntity.class::cast)
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

  public static MetalBarrelContainer copper(int id, Inventory playerInventory) {
  	return copperS(id,playerInventory,ContainerLevelAccess.NULL);
	}

	public static MetalBarrelContainer iron(int id, Inventory playerInventory) {
		return ironS(id,playerInventory,ContainerLevelAccess.NULL);
	}

	public static MetalBarrelContainer silver(int id, Inventory playerInventory) {
		return silverS(id,playerInventory,ContainerLevelAccess.NULL);
	}

	public static MetalBarrelContainer gold(int id, Inventory playerInventory) {
		return goldS(id,playerInventory,ContainerLevelAccess.NULL);
	}

	public static MetalBarrelContainer diamond(int id, Inventory playerInventory) {
		return diamondS(id,playerInventory,ContainerLevelAccess.NULL);
	}

	public static MetalBarrelContainer netherite(int id, Inventory playerInventory) {
		return netheriteS(id,playerInventory,ContainerLevelAccess.NULL);
	}

	//////////////////////////

	public static MetalBarrelContainer copperS(int id, Inventory playerInventory,ContainerLevelAccess callable) {
		return new MetalBarrelContainer(ModMenuTypes.COPPER,id,playerInventory,
				9,5,8,18, 8,122,callable);
	}

	public static MetalBarrelContainer ironS(int id, Inventory playerInventory,ContainerLevelAccess callable) {
		return new MetalBarrelContainer(ModMenuTypes.IRON,id,playerInventory,
						9,6,8,18, 8,140,callable);
	}

	public static MetalBarrelContainer silverS(int id, Inventory playerInventory,ContainerLevelAccess callable) {
		return new MetalBarrelContainer(ModMenuTypes.SILVER,id,playerInventory,
						9,8,8,18, 8,176,callable);
	}

	public static MetalBarrelContainer goldS(int id, Inventory playerInventory,ContainerLevelAccess callable) {
		return new MetalBarrelContainer(ModMenuTypes.GOLD,id,playerInventory,
						9,9,8,18, 8,194,callable);
	}

	public static MetalBarrelContainer diamondS(int id, Inventory playerInventory,ContainerLevelAccess callable) {
		return new MetalBarrelContainer(ModMenuTypes.DIAMOND,id, playerInventory,
						12,9,8,18, 35,194,callable);
	}

	public static MetalBarrelContainer netheriteS(int id, Inventory playerInventory,ContainerLevelAccess callable) {
		return new MetalBarrelContainer(ModMenuTypes.NETHERITE,id, playerInventory,
						15,9,8,18, 62, 194,callable);
	}

  @Override
  public boolean stillValid(@Nonnull Player playerIn) {
    return true;
  }

  @Nonnull
  @Override
  public ItemStack quickMoveStack(Player playerIn, int index) {
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
  public void removed(Player playerIn) {
  	super.removed(playerIn);
		this.callable.execute((world, pos) -> {
			BlockEntity tileEntity = world.getBlockEntity(pos);
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
				metalBarrelBlockEntity.changeState(playerIn.level().getBlockState(tileEntity.getBlockPos()), false);
			}
		});
	}
}

