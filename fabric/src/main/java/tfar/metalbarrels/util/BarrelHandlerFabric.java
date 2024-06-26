package tfar.metalbarrels.util;

import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
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
        ListTag listTag = createTag(levelRegistry);
        CompoundTag tag = new CompoundTag();
        tag.put("dummy",listTag);
        return tag;
    }

    @Override
    public void $deserialize(CompoundTag invTag, HolderLookup.Provider levelRegistry) {
        ListTag listTag = invTag.getList("dummy", Tag.TAG_COMPOUND);
        fromTag(listTag,levelRegistry);
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
