package tfar.metalbarrels.platform;

import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.fml.ModList;
import net.neoforged.fml.loading.FMLLoader;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.transfer.ResourceHandler;
import net.neoforged.neoforge.transfer.item.ItemResource;
import tfar.metalbarrels.InventoryHandler;
import tfar.metalbarrels.ItemStackHandler;
import tfar.metalbarrels.blockentity.MetalBarrelBlockEntity;
import tfar.metalbarrels.platform.services.IPlatformHelper;

import java.util.List;

public class NeoForgePlatformHelper implements IPlatformHelper {

    @Override
    public String getPlatformName() {

        return "NeoForge";
    }

    @Override
    public boolean isModLoaded(String modId) {

        return ModList.get().isLoaded(modId);
    }

    @Override
    public boolean isDevelopmentEnvironment() {
        return !FMLLoader.getCurrent().isProduction();
    }

    @Override
    public InventoryHandler createResourceHandler(MetalBarrelBlockEntity metalBarrelBlockEntity) {
        return new ItemStackHandler(metalBarrelBlockEntity,metalBarrelBlockEntity.barrelProperties.size());
    }

    @Override
    public void copyOldItems(Level level, BlockPos pos, BlockState state, BlockEntity blockEntity, List<ItemStack> list) {
        ResourceHandler<ItemResource> capability = Capabilities.Item.BLOCK.getCapability(level, pos, state, blockEntity, null);
        if (capability != null) {
            for (int i =0;i < capability.size();i++) {
                ItemResource resource = capability.getResource(i);
                int count = capability.getAmountAsInt(i);
                list.add(resource.toStack().copyWithCount(count));
            }
        }
    }
}