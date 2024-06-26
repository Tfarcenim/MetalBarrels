package tfar.metalbarrels.item;

import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import tfar.metalbarrels.util.UpgradeInfo;

import java.util.List;
import java.util.stream.IntStream;

public class BarrelUpgradeItemForge extends BarrelUpgradeItem{
    public BarrelUpgradeItemForge(Properties properties, UpgradeInfo info) {
        super(properties, info);
    }

    @Override
    protected void copyOldItems(Level level, BlockPos pos, BlockState state, BlockEntity blockEntity, List<ItemStack> list) {
        blockEntity.getCapability(ForgeCapabilities.ITEM_HANDLER)
                .ifPresent((itemHandler) -> IntStream.range(0, itemHandler.getSlots())
                        .mapToObj(itemHandler::getStackInSlot).forEach(list::add));
    }

    @Override
    protected void setNewItems(Level level, BlockPos pos, BlockState state, BlockEntity blockEntity, List<ItemStack> list) {
        blockEntity.getCapability(ForgeCapabilities.ITEM_HANDLER).ifPresent((itemHandler) -> IntStream.range(0, list.size()).forEach(i -> itemHandler.insertItem(i, list.get(i), false)));

    }
}
