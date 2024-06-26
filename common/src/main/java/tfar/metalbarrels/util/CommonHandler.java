package tfar.metalbarrels.util;

import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;

public interface CommonHandler {
    int $getSlotCount();
    ItemStack $getStack(int slot);
    void $setStack(int slot,ItemStack stack);
    CompoundTag $serialize(HolderLookup.Provider levelRegistry);
    void $deserialize(CompoundTag invTag, HolderLookup.Provider levelRegistry);
}
