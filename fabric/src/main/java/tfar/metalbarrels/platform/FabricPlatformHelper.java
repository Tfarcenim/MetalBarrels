package tfar.metalbarrels.platform;

import net.fabricmc.fabric.api.transfer.v1.item.ItemStorage;
import net.fabricmc.fabric.api.transfer.v1.item.ItemVariant;
import net.fabricmc.fabric.api.transfer.v1.storage.SlottedStorage;
import net.fabricmc.fabric.api.transfer.v1.storage.Storage;
import net.fabricmc.fabric.api.transfer.v1.storage.base.SingleSlotStorage;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import tfar.metalbarrels.InventoryHandler;
import tfar.metalbarrels.ItemStackHandlerFabric;
import tfar.metalbarrels.blockentity.MetalBarrelBlockEntity;
import tfar.metalbarrels.platform.services.IPlatformHelper;
import net.fabricmc.loader.api.FabricLoader;

import java.util.List;

public class FabricPlatformHelper implements IPlatformHelper {

    @Override
    public String getPlatformName() {
        return "Fabric";
    }

    @Override
    public boolean isModLoaded(String modId) {

        return FabricLoader.getInstance().isModLoaded(modId);
    }

    @Override
    public boolean isDevelopmentEnvironment() {

        return FabricLoader.getInstance().isDevelopmentEnvironment();
    }

    @Override
    public InventoryHandler createResourceHandler(MetalBarrelBlockEntity metalBarrelBlockEntity) {
        return new ItemStackHandlerFabric(metalBarrelBlockEntity,metalBarrelBlockEntity.barrelProperties.size());
    }

    @Override
    public void copyOldItems(Level level, BlockPos pos, BlockState state, BlockEntity blockEntity, List<ItemStack> list) {
        Storage<ItemVariant> capability = ItemStorage.SIDED.find(level, pos, state, blockEntity, null);
        if (capability instanceof SlottedStorage<ItemVariant> slottedStorage) {
            for (int i = 0; i < slottedStorage.getSlotCount(); i++) {
                SingleSlotStorage<ItemVariant> slot = slottedStorage.getSlot(i);
                ItemVariant resource = slot.getResource();
                int count = (int) slot.getAmount();
                ItemStack stack = resource.toStack(count);
                list.add(stack);
            }
        }
    }
}
