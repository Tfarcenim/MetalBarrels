package tfar.metalbarrels.util;

import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import tfar.metalbarrels.blockentity.MetalBarrelBlockEntity;

public class BarrelHandlerFabric extends SimpleContainer implements BarrelHandler {

    private final MetalBarrelBlockEntity<?> metalBarrelBlockEntity;

    public BarrelHandlerFabric(int slots, MetalBarrelBlockEntity<?> metalBarrelBlockEntity) {
        super(slots);
        this.metalBarrelBlockEntity = metalBarrelBlockEntity;
    }


    @Override
    public CompoundTag $serialize(HolderLookup.Provider levelRegistry) {
       return serializeNBT(levelRegistry);
    }

    public CompoundTag serializeNBT(HolderLookup.Provider provider) {
        ListTag nbtTagList = new ListTag();
        for (int i = 0; i < items.size(); i++) {
            if (!items.get(i).isEmpty()) {
                CompoundTag itemTag = new CompoundTag();
                itemTag.putInt("Slot", i);
                nbtTagList.add(items.get(i).save(provider, itemTag));
            }
        }
        CompoundTag nbt = new CompoundTag();
        nbt.put("Items", nbtTagList);
        nbt.putInt("Size", items.size());
        return nbt;
    }


    @Override
    public void $deserialize(CompoundTag invTag, HolderLookup.Provider levelRegistry) {
        deserializeNBT(invTag, levelRegistry);
    }

    public void deserializeNBT(CompoundTag nbt, HolderLookup.Provider provider) {
        ListTag tagList = nbt.getList("Items", Tag.TAG_COMPOUND);
        for (int i = 0; i < tagList.size(); i++) {
            CompoundTag itemTags = tagList.getCompound(i);
            int slot = itemTags.getInt("Slot");
            if (slot >= 0 && slot < items.size()) {
                ItemStack.parse(provider, itemTags).ifPresent(stack -> items.set(slot, stack));
            }
        }
    }

    @Override
    public MetalBarrelBlockEntity<?> getBlockEntity() {
        return metalBarrelBlockEntity;
    }

    @Override
    public int $getSlotCount() {
        return getContainerSize();
    }

    @Override
    public ItemStack $getStack(int slot) {
        return getItem(slot);
    }

    @Override
    public void $setStack(int slot, ItemStack stack) {
        setItem(slot,stack);
    }

    @Override
    public Slot addInvSlot(int slot, int x, int y) {
        return new Slot(this,slot,x,y);
    }
}
