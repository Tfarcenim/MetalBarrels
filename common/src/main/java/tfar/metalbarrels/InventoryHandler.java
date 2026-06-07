package tfar.metalbarrels;

import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import org.jspecify.annotations.Nullable;
import tfar.metalbarrels.blockentity.MetalBarrelBlockEntity;

import java.util.List;

public interface InventoryHandler {

    Slot createSlot(int slot, int x,int y);

    int getRedstoneSignal();

    default int getSignalFromItems(NonNullList<ItemStack> items) {
        float totalPercent = 0.0F;

        for (int i = 0; i < items.size(); i++) {
            ItemStack itemStack = items.get(i);
            if (!itemStack.isEmpty()) {
                totalPercent += (float)itemStack.getCount() / itemStack.getMaxStackSize();
            }
        }

        totalPercent /= items.size();
        return Mth.lerpDiscrete(totalPercent, 0, 15);
    }

    MetalBarrelBlockEntity getBlockEntity();

    default void startOpen(Player player) {
        if (getBlockEntity() != null) {
            getBlockEntity().startOpen(player);
        }
    }

    default void stopOpen(Player playerIn) {
        if (getBlockEntity() != null) {
            getBlockEntity().stopOpen(playerIn);
        }
    }

    void load(ValueInput input);

    void save(ValueOutput output);

    void dropItems(@Nullable Level level, BlockPos pos);

    void setNewItems(List<ItemStack> items);
}
