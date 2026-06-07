package tfar.metalbarrels;

import net.fabricmc.fabric.api.transfer.v1.item.ItemVariant;
import net.fabricmc.fabric.api.transfer.v1.item.base.SingleStackStorage;
import net.fabricmc.fabric.api.transfer.v1.storage.base.CombinedSlottedStorage;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.world.Container;
import net.minecraft.world.Containers;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import org.jspecify.annotations.Nullable;
import tfar.metalbarrels.blockentity.MetalBarrelBlockEntity;

import java.util.*;

public class ItemStackHandlerFabric extends CombinedSlottedStorage<ItemVariant, ItemStackHandlerFabric.ItemWrapper>
        implements InventoryHandler {

    private final MetalBarrelBlockEntity metalBarrelBlockEntity;

    public ItemStackHandlerFabric(MetalBarrelBlockEntity metalBarrelBlockEntity, int size) {
        super(Collections.emptyList());
        this.metalBarrelBlockEntity = metalBarrelBlockEntity;

        List<ItemWrapper> backingList = new ArrayList<>(size);

        for (int i = 0; i < size; i++) {
            backingList.add(new ItemWrapper());
        }

        parts = Collections.unmodifiableList(backingList);
    }

    @Override
    public Slot createSlot(int slot, int x, int y) {
        return new StorageSlot(parts.get(slot),slot, x, y);
    }

    @Override
    public int getRedstoneSignal() {
        return 0;
    }

    @Override
    public MetalBarrelBlockEntity getBlockEntity() {
        return metalBarrelBlockEntity;
    }

    ////
    @Override
    public void load(ValueInput input) {
        input.read("stacks", ItemStack.OPTIONAL_CODEC.listOf()).ifPresent(l -> {
            for(int i = 0; i < l.size(); i++) {
                parts.get(i).setStack(l.get(i));
            }
        });
    }

    @Override
    public void save(ValueOutput output) {
        NonNullList<ItemStack> items = NonNullList.withSize(parts.size(), ItemStack.EMPTY);
        for(int i = 0; i < parts.size(); i++) {
            items.set(i, parts.get(i).getStack());
        }
        output.store("stacks",ItemStack.OPTIONAL_CODEC.listOf(), items);
    }

    @Override
    public void dropItems(@Nullable Level level, BlockPos pos) {
        for (ItemWrapper wrapper : parts) {
            Containers.dropItemStack(level,pos.getX(),pos.getY(),pos.getZ(),wrapper.getStack());
        }
    }

    @Override
    public void setNewItems(List<ItemStack> items) {
        for( int i = 0; i < items.size(); i++ ) {
            parts.get(i).setStack(items.get(i));
        }
    }

    ////

    public class StorageSlot extends Slot {
        private static final Container EMPTY = new SimpleContainer(0);
        private final ItemWrapper storageViews;

        @Nullable
        private ItemStack cachedReturnedStack = null;

        public StorageSlot(ItemWrapper storageViews, int slot, int x, int y) {
            super(EMPTY, slot, x, y);
            this.storageViews = storageViews;
        }

        @Override
        public ItemStack getItem() {
            return cachedReturnedStack = getStackCopy();
        }

        @Override
        public void set(ItemStack itemStack) {
            setStackCopy(itemStack);
            cachedReturnedStack = itemStack;
        }

        @Override
        public int getMaxStackSize() {
            return (int) storageViews.getCapacity();
        }

        @Override
        public ItemStack remove(int amount) {
            ItemStack stack = getStackCopy().copy();
            ItemStack ret = stack.split(amount);
            set(stack);
            cachedReturnedStack = null;
            return ret;
        }

        protected ItemStack getStackCopy() {
            return storageViews.getResource().toStack((int) storageViews.getAmount());
        }

        protected void setStackCopy(ItemStack stack) {
            storageViews.setStack(stack.copy());
        }

        @Override
        public void setChanged() {
            // Verify that the stack has actually changed before setting it.
            // Vanilla menu logic (like AbstractContainerMenu#moveItemStackTo) often already sets the stack through Slot#setByPlayer.
            // This is done to prevent slot change logic from running multiple times when not necessary.
            if (cachedReturnedStack != null && !ItemStack.matches(cachedReturnedStack, getStackCopy())) {
                set(cachedReturnedStack);
            }
        }
    }
    public class ItemWrapper extends SingleStackStorage {
        ItemStack stack = ItemStack.EMPTY;

        @Override
        protected ItemStack getStack() {
            return stack;
        }

        @Override
        protected void setStack(ItemStack stack) {
            this.stack = stack;
        }
    }
}
