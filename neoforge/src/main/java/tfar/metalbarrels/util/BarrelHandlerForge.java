package tfar.metalbarrels.util;

import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.items.ItemStackHandler;
import net.neoforged.neoforge.items.SlotItemHandler;
import tfar.metalbarrels.blockentity.MetalBarrelBlockEntity;

public class BarrelHandlerForge extends ItemStackHandler implements BarrelHandler {

    private final MetalBarrelBlockEntity<?> metalBarrelBlockEntity;

    public BarrelHandlerForge(int slots, MetalBarrelBlockEntity<?> metalBarrelBlockEntity) {
        super(slots);
        this.metalBarrelBlockEntity = metalBarrelBlockEntity;
    }

    @Override
    public ItemStack $getStack(int slot) {
        return getStackInSlot(slot);
    }

    @Override
    public void $setStack(int slot, ItemStack stack) {
        setStackInSlot(slot, stack);
    }

    @Override
    public int $getSlotCount() {
        return getSlots();
    }

    @Override
    public MetalBarrelBlockEntity<?> getBlockEntity() {
        return metalBarrelBlockEntity;
    }

    @Override
    public CompoundTag $serialize(HolderLookup.Provider levelRegistry) {
        return this.serializeNBT(levelRegistry);
    }

    @Override
    public void $deserialize(CompoundTag invTag,HolderLookup.Provider levelRegistry) {
        deserializeNBT(levelRegistry, invTag);
    }

    @Override
    public Slot addInvSlot(int slot, int x, int y) {
        return new SlotItemHandler(this,slot,x,y);
    }
}
