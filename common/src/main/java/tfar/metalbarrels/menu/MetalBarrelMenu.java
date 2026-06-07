package tfar.metalbarrels.menu;

import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import org.jspecify.annotations.Nullable;
import tfar.metalbarrels.InventoryHandler;
import tfar.metalbarrels.blockentity.MetalBarrelBlockEntity;
import tfar.metalbarrels.init.ModMenuTypes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public class MetalBarrelMenu extends AbstractContainerMenu {

    public int width;
    public int height;
    public final InventoryHandler handler;

    public MetalBarrelMenu(MenuType<?> containerType, int id, Inventory playerInventory,
                           int width, int height, int containerX, int containerY, int playerX, int playerY, InventoryHandler handler) {
        super(containerType, id);
        this.width = width;
        this.height = height;

        if (handler == null) {
            handler = new Dummy(width * height);
        }

        this.handler = handler;

        handler.startOpen(playerInventory.player);

        for (int i = 0; i < height; i++)
            for (int j = 0; j < width; j++)
                addSlot(handler.createSlot(j + width * i, containerX + j * 18, containerY + i * 18));

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 9; j++) {
                this.addSlot(new Slot(playerInventory, j + i * 9 + 9, j * 18 + playerX, i * 18 + playerY));
            }
        }

        for (int i = 0; i < 9; i++) {
            this.addSlot(new Slot(playerInventory, i, i * 18 + playerX, playerY + 58));
        }
    }

    public static MetalBarrelMenu copper(int id, Inventory playerInventory) {
        return copperS(id, playerInventory,null);
    }

    public static MetalBarrelMenu iron(int id, Inventory playerInventory) {
        return ironS(id, playerInventory, null);
    }

    public static MetalBarrelMenu silver(int id, Inventory playerInventory) {
        return silverS(id, playerInventory, null);
    }

    public static MetalBarrelMenu gold(int id, Inventory playerInventory) {
        return goldS(id, playerInventory, null);
    }

    public static MetalBarrelMenu diamond(int id, Inventory playerInventory) {
        return diamondS(id, playerInventory, null);
    }

    public static MetalBarrelMenu netherite(int id, Inventory playerInventory) {
        return netheriteS(id, playerInventory, null);
    }

    //////////////////////////

    public static MetalBarrelMenu copperS(int id, Inventory playerInventory, InventoryHandler handler) {
        return new MetalBarrelMenu(ModMenuTypes.COPPER, id, playerInventory,
                9, 5, 8, 18, 8, 122,handler);
    }

    public static MetalBarrelMenu ironS(int id, Inventory playerInventory, InventoryHandler handler) {
        return new MetalBarrelMenu(ModMenuTypes.IRON, id, playerInventory,
                9, 6, 8, 18, 8, 140, handler);
    }

    public static MetalBarrelMenu silverS(int id, Inventory playerInventory, InventoryHandler handler) {
        return new MetalBarrelMenu(ModMenuTypes.SILVER, id, playerInventory,
                9, 8, 8, 18, 8, 176, handler);
    }

    public static MetalBarrelMenu goldS(int id, Inventory playerInventory, InventoryHandler handler) {
        return new MetalBarrelMenu(ModMenuTypes.GOLD, id, playerInventory,
                9, 9, 8, 18, 8, 194, handler);
    }

    public static MetalBarrelMenu diamondS(int id, Inventory playerInventory, InventoryHandler handler) {
        return new MetalBarrelMenu(ModMenuTypes.DIAMOND, id, playerInventory,
                12, 9, 8, 18, 35, 194, handler);
    }

    public static MetalBarrelMenu netheriteS(int id, Inventory playerInventory, InventoryHandler handler) {
        return new MetalBarrelMenu(ModMenuTypes.NETHERITE, id, playerInventory,
                15, 9, 8, 18, 62, 194, handler);
    }

    private record Dummy(NonNullList<ItemStack> stacks) implements InventoryHandler {
        
        private static final Container EMPTY = new SimpleContainer(0);
        public Dummy(int slots){
            this(NonNullList.withSize(slots, ItemStack.EMPTY));
        }

        @Override
        public Slot createSlot(int slot, int x, int y) {
            return new DummySlot(slot, x, y);
        }
        
        private class DummySlot extends Slot {
            public DummySlot(int slot, int x, int y) {
                super(EMPTY, slot, x, y);
            }

            @Override
            public void set(ItemStack itemStack) {
                stacks.set(getContainerSlot(), itemStack);
            }

            @Override
            public ItemStack getItem() {
                return stacks.get(getContainerSlot());
            }
        }

        @Override
        public int getRedstoneSignal() {
            return 0;
        }

        @Override
        public MetalBarrelBlockEntity getBlockEntity() {
            return null;
        }

        @Override
        public void load(ValueInput input) {

        }

        @Override
        public void save(ValueOutput output) {

        }

        @Override
        public void dropItems(@Nullable Level level, BlockPos pos) {

        }

        @Override
        public void setNewItems(List<ItemStack> items) {

        }
    }

    @Override
    public boolean stillValid(Player playerIn) {
        return true;
    }

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
        this.handler.stopOpen(playerIn);
    }
}

