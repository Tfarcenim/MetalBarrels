package tfar.metalbarrels.item;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import tfar.metalbarrels.util.BarrelHandler;
import tfar.metalbarrels.util.UpgradeInfo;

import java.util.List;
import java.util.stream.IntStream;

public class BarrelUpgradeItemForge extends BarrelUpgradeItem{
    public BarrelUpgradeItemForge(Properties properties, UpgradeInfo info) {
        super(properties, info);
    }

    @Override
    protected void copyOldItems(BlockEntity oldBarrel, List<ItemStack> list) {
        oldBarrel.getCapability(ForgeCapabilities.ITEM_HANDLER)
                .ifPresent((itemHandler) -> IntStream.range(0, itemHandler.getSlots())
                        .mapToObj(itemHandler::getStackInSlot).forEach(list::add));
    }

    @Override
    protected void setNewItems(BlockEntity newBarrel, List<ItemStack> list) {
        newBarrel.getCapability(ForgeCapabilities.ITEM_HANDLER).ifPresent((itemHandler) -> IntStream.range(0, list.size()).forEach(i -> itemHandler.insertItem(i, list.get(i), false)));

    }
}
