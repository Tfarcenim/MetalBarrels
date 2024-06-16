package tfar.metalbarrels.container;

import tfar.metalbarrels.init.ModMenuTypes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.SlotItemHandler;
import tfar.metalbarrels.util.BarrelHandler;

import javax.annotation.Nonnull;

public class MetalBarrelContainer extends AbstractContainerMenu {

    public int width;
    public int height;
    public final ItemStackHandler handler;

    public MetalBarrelContainer(MenuType<?> containerType, int id, Inventory playerInventory,
                                int width, int height, int containerX, int containerY, int playerX, int playerY, BarrelHandler handler) {
        super(containerType, id);
        this.width = width;
        this.height = height;

        if (handler == null) {
            handler = new BarrelHandler(width * height,null);
        }

        this.handler = handler;

        handler.startOpen(playerInventory.player);

        for (int i = 0; i < height; i++)
            for (int j = 0; j < width; j++)
                addSlot(new SlotItemHandler(handler,
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
        return copperS(id, playerInventory,null);
    }

    public static MetalBarrelContainer iron(int id, Inventory playerInventory) {
        return ironS(id, playerInventory, null);
    }

    public static MetalBarrelContainer silver(int id, Inventory playerInventory) {
        return silverS(id, playerInventory, null);
    }

    public static MetalBarrelContainer gold(int id, Inventory playerInventory) {
        return goldS(id, playerInventory, null);
    }

    public static MetalBarrelContainer diamond(int id, Inventory playerInventory) {
        return diamondS(id, playerInventory, null);
    }

    public static MetalBarrelContainer netherite(int id, Inventory playerInventory) {
        return netheriteS(id, playerInventory, null);
    }

    //////////////////////////

    public static MetalBarrelContainer copperS(int id, Inventory playerInventory, BarrelHandler handler) {
        return new MetalBarrelContainer(ModMenuTypes.COPPER, id, playerInventory,
                9, 5, 8, 18, 8, 122,handler);
    }

    public static MetalBarrelContainer ironS(int id, Inventory playerInventory,BarrelHandler handler) {
        return new MetalBarrelContainer(ModMenuTypes.IRON, id, playerInventory,
                9, 6, 8, 18, 8, 140, handler);
    }

    public static MetalBarrelContainer silverS(int id, Inventory playerInventory,BarrelHandler handler) {
        return new MetalBarrelContainer(ModMenuTypes.SILVER, id, playerInventory,
                9, 8, 8, 18, 8, 176, handler);
    }

    public static MetalBarrelContainer goldS(int id, Inventory playerInventory,BarrelHandler handler) {
        return new MetalBarrelContainer(ModMenuTypes.GOLD, id, playerInventory,
                9, 9, 8, 18, 8, 194, handler);
    }

    public static MetalBarrelContainer diamondS(int id, Inventory playerInventory,BarrelHandler handler) {
        return new MetalBarrelContainer(ModMenuTypes.DIAMOND, id, playerInventory,
                12, 9, 8, 18, 35, 194, handler);
    }

    public static MetalBarrelContainer netheriteS(int id, Inventory playerInventory,BarrelHandler handler) {
        return new MetalBarrelContainer(ModMenuTypes.NETHERITE, id, playerInventory,
                15, 9, 8, 18, 62, 194, handler);
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
    }
}

