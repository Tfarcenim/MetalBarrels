package tfar.metalbarrels.item;

import net.fabricmc.fabric.api.transfer.v1.item.ItemStorage;
import net.fabricmc.fabric.api.transfer.v1.item.ItemVariant;
import net.fabricmc.fabric.api.transfer.v1.storage.Storage;
import net.fabricmc.fabric.api.transfer.v1.storage.StorageView;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import tfar.metalbarrels.blockentity.MetalBarrelBlockEntityFabric;
import tfar.metalbarrels.util.UpgradeInfo;

import java.util.List;

public class BarrelUpgradeItemFabric extends BarrelUpgradeItem{
    public BarrelUpgradeItemFabric(Properties properties, UpgradeInfo info) {
        super(properties, info);
    }

    @Override
    protected void copyOldItems(Level level, BlockPos pos, BlockState state, BlockEntity blockEntity, List<ItemStack> list) {
        Storage<ItemVariant> storageViews = ItemStorage.SIDED.find(level, pos, state, blockEntity, null);
        if (storageViews != null) {
            for (StorageView<ItemVariant> storageView : storageViews) {
                ItemVariant itemVariant = storageView.getResource();
                if (itemVariant.isBlank()) {
                    list.add(ItemStack.EMPTY);
                } else {
                    ItemStack stack = itemVariant.toStack();
                    stack.setCount((int)storageView.getAmount());
                    list.add(stack);
                }
            }
        }
    }

    @Override
    protected void setNewItems(Level level, BlockPos pos, BlockState state, BlockEntity blockEntity, List<ItemStack> list) {
        if (blockEntity instanceof MetalBarrelBlockEntityFabric metalBarrelBlockEntityFabric) {
            for (int i = 0; i < list.size(); i++) {
                ItemStack stack = list.get(i);
                metalBarrelBlockEntityFabric.barrelHandler.setItem(i, stack);
            }
        }
    }
}
