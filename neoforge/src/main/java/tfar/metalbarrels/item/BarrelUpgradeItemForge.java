package tfar.metalbarrels.item;

import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import tfar.metalbarrels.blockentity.MetalBarrelBlockEntity;
import tfar.metalbarrels.util.UpgradeInfo;

import java.util.List;

public class BarrelUpgradeItemForge extends BarrelUpgradeItem{
    public BarrelUpgradeItemForge(Properties properties, UpgradeInfo info) {
        super(properties, info);
    }

    @Override
    protected void copyOldItems(Level level, BlockPos pos, BlockState state, BlockEntity blockEntity, List<ItemStack> list) {

        /*IItemHandler capability = Capabilities.ItemHandler.BLOCK.getCapability(level, pos, state, blockEntity, null);

        for (int i = 0; i < capability.getSlots();i++) {
            list.add(capability.getStackInSlot(i));
        }*/
    }

    @Override
    protected void setNewItems(Level level, BlockPos pos, BlockState state, BlockEntity blockEntity, List<ItemStack> list) {
        if (blockEntity instanceof MetalBarrelBlockEntity metalBarrelBlockEntityForge) {
            for (int i = 0; i < list.size();i++) {
                metalBarrelBlockEntityForge.barrelHandler.set(i,list.get(i));
            }
        }
    }
}
