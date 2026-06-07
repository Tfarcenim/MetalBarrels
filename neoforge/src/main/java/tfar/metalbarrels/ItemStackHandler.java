package tfar.metalbarrels;

import net.minecraft.core.BlockPos;
import net.minecraft.world.Containers;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.neoforged.neoforge.transfer.item.ItemStacksResourceHandler;
import net.neoforged.neoforge.transfer.item.ResourceHandlerSlot;
import org.jetbrains.annotations.UnknownNullability;
import org.jspecify.annotations.Nullable;
import tfar.metalbarrels.blockentity.MetalBarrelBlockEntity;

import java.util.List;

//avoids creating a bunch of services
public class ItemStackHandler extends ItemStacksResourceHandler implements InventoryHandler {
    private final MetalBarrelBlockEntity metalBarrelBlockEntity;

    public ItemStackHandler(MetalBarrelBlockEntity metalBarrelBlockEntity, int size) {
        super(size);
        this.metalBarrelBlockEntity = metalBarrelBlockEntity;
    }

    @Override
    public Slot createSlot(int slot, int x, int y) {
        return new ResourceHandlerSlot(this, this::set,slot,x,y);
    }

    @Override
    public int getRedstoneSignal() {
        return getSignalFromItems(stacks);
    }

    @Override
    public MetalBarrelBlockEntity getBlockEntity() {
        return metalBarrelBlockEntity;
    }

    @Override
    public void save(ValueOutput output) {
        serialize(output);
    }

    @Override
    public void dropItems(@Nullable Level level, BlockPos pos) {
        for (ItemStack stack : stacks) {
            Containers.dropItemStack(level,pos.getX(),pos.getY(),pos.getZ(),stack);
        }
    }

    @Override
    public void setNewItems(@UnknownNullability List<ItemStack> items) {
        for (int i = 0; i < items.size(); i++) {
            ItemStack stack = items.get(i);
            stacks.set(i, stack);
        }
    }

    @Override
    public void load(ValueInput input) {
        deserialize(input);
    }
}
