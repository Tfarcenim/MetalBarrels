package tfar.metalbarrels.util;

import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.util.Mth;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import org.jetbrains.annotations.Nullable;
import tfar.metalbarrels.blockentity.MetalBarrelBlockEntity;

public class BarrelHandler {

    private final MetalBarrelBlockEntity blockEntity;
    protected final NonNullList<ItemStack> stacks;

    public BarrelHandler(MetalBarrelBlockEntity blockEntity) {
        this(blockEntity.barrelProperties.size(), blockEntity);
    }

    public BarrelHandler(int slots,@Nullable MetalBarrelBlockEntity blockEntity) {
        this.blockEntity = blockEntity;
        stacks = NonNullList.withSize(slots,ItemStack.EMPTY);
    }

    public MetalBarrelBlockEntity getBlockEntity() {
        return blockEntity;
    }


    public void $onContentsChanged(int slot) {
        if (blockEntity != null) {
            blockEntity.setChanged();
        }
    }

    public void $startOpen(Player player) {
        if (getBlockEntity() != null) {
            getBlockEntity().startOpen(player);
        }
    }

    public void $stopOpen(Player pPlayer) {
        if (getBlockEntity() != null) {
            getBlockEntity().stopOpen(pPlayer);
        }
    }

     public Slot addSlot(int slot, int x, int y) {
        return new BarrelHandlerSlot(slot,x,y);
     }

     public class BarrelHandlerSlot extends Slot {
        private static final Container EMPTY = new SimpleContainer(0);
         public BarrelHandlerSlot(int slot, int x, int y) {
             super(EMPTY, slot, x, y);
         }


         @Override
         public boolean mayPlace(ItemStack stack) {
             if (stack.isEmpty())
                 return false;
             return isValid(index, stack);
         }

         @Override
         public ItemStack getItem() {
             return BarrelHandler.this.get(index);
         }

         // Override if your IItemHandler does not implement IItemHandlerModifiable
         @Override
         public void set(ItemStack stack) {
             BarrelHandler.this.set(index, stack);
             this.setChanged();
         }

         @Override
         public void onQuickCraft(ItemStack oldStackIn, ItemStack newStackIn) {}

         @Override
         public int getMaxStackSize() {
             return getSlotLimit(this.index);
         }

         @Override
         public int getMaxStackSize(ItemStack stack) {
             return Math.min(stack.getMaxStackSize(), getSlotLimit(this.index));
         }

         @Override
         public boolean mayPickup(Player playerIn) {
             return !extract(index, 1, true).isEmpty();
         }

         @Override
         public ItemStack remove(int amount) {
             return extract(index, amount, false);
         }

     }

    public int slots() {
        return stacks.size();
    }
    ItemStack getStack(int slot) {
        return  stacks.get(slot);
    }
    void setStack(int slot,ItemStack stack) {
        stacks.set(slot,stack);
    }

    public boolean isValid(int index, ItemStack stack) {
        return switch (index) {
            default -> true;
        };
    }

    public boolean canTake(int index, @Nullable Direction direction) {
        ItemStack stack = stacks.get(index);
        return switch (index) {
            default -> true;
        };
    }

    public boolean canPlace(int index,ItemStack stack, @Nullable Direction direction) {
        return switch (index) {
            default -> true;
        };
    }

    public ItemStack get(int index) {
        return stacks.get(index);
    }

    public void set(int index, ItemStack stack) {
        stacks.set(index, stack);
        onContentsChanged(index);
    }

    public int getSlotLimit(int index) {
        return 99;
    }

    public ItemStack extractWithContext(int slot, int amount, boolean simulate, @Nullable Direction direction) {
        if (!canTake(slot, direction)) return ItemStack.EMPTY;

        return extract(slot, amount, simulate);
    }

    public ItemStack extract(int slot, int amount, boolean simulate) {
        if (amount == 0)
            return ItemStack.EMPTY;


        ItemStack existing = this.stacks.get(slot);

        if (existing.isEmpty())
            return ItemStack.EMPTY;

        int toExtract = Math.min(amount, existing.getMaxStackSize());

        if (existing.getCount() <= toExtract) {
            if (!simulate) {
                this.stacks.set(slot, ItemStack.EMPTY);
                onContentsChanged(slot);
                return existing;
            } else {
                return existing.copy();
            }
        } else {
            if (!simulate) {
                this.stacks.set(slot, existing.copyWithCount(existing.getCount() - toExtract));
                onContentsChanged(slot);
            }

            return existing.copyWithCount(toExtract);
        }
    }

    public ItemStack insertWithContext(int slot, ItemStack stack, boolean simulate,@Nullable Direction direction) {
        if (!canPlace(slot,stack, direction)) return stack;
        return insert(slot, stack, simulate);
    }

    public ItemStack insert(int slot, ItemStack stack, boolean simulate) {
        if (stack.isEmpty())
            return ItemStack.EMPTY;

        if (!isValid(slot, stack))
            return stack;


        ItemStack existing = this.stacks.get(slot);

        int limit = getStackLimit(slot, stack);

        if (!existing.isEmpty()) {
            if (!ItemStack.isSameItemSameComponents(stack, existing))
                return stack;

            limit -= existing.getCount();
        }

        if (limit <= 0)
            return stack;

        boolean reachedLimit = stack.getCount() > limit;

        if (!simulate) {
            if (existing.isEmpty()) {
                this.stacks.set(slot, reachedLimit ? stack.copyWithCount(limit) : stack);
            } else {
                existing.grow(reachedLimit ? limit : stack.getCount());
            }
            onContentsChanged(slot);
        }

        return reachedLimit ? stack.copyWithCount(stack.getCount() - limit) : ItemStack.EMPTY;
    }

    protected int getStackLimit(int slot, ItemStack stack) {
        return Math.min(getSlotLimit(slot), stack.getMaxStackSize());
    }


    public void onContentsChanged(int slot) {
        if (blockEntity != null) {
            blockEntity.setChanged();
        }
    }

    public void serializeNBT(ValueOutput output) {
        ContainerHelper.saveAllItems(output, stacks);
    }

    public void deserializeNBT(ValueInput input) {
        ContainerHelper.loadAllItems(input, stacks);
        //onLoad();
    }


    public int getRedstoneSignal() {
            float totalPercent = 0.0F;

            for (int i = 0; i < slots(); i++) {
                ItemStack itemStack = get(i);
                if (!itemStack.isEmpty()) {
                    totalPercent += (float)itemStack.getCount() / getStackLimit(i,itemStack);
                }
            }

            totalPercent /= slots();
            return Mth.lerpDiscrete(totalPercent, 0, 15);
    }
}
