package tfar.metalbarrels;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.transfer.v1.item.ItemStorage;
import net.fabricmc.fabric.api.transfer.v1.item.ItemVariant;
import net.fabricmc.fabric.api.transfer.v1.storage.Storage;
import net.minecraft.core.Direction;
import tfar.metalbarrels.blockentity.MetalBarrelBlockEntity;
import tfar.metalbarrels.init.ModBlockEntityTypes;

public class MetalBarrelsFabric implements ModInitializer {
    
    @Override
    public void onInitialize() {
        ItemStorage.SIDED.registerForBlockEntity(MetalBarrelsFabric::getStorage, ModBlockEntityTypes.COPPER);
        ItemStorage.SIDED.registerForBlockEntity(MetalBarrelsFabric::getStorage, ModBlockEntityTypes.IRON);
        ItemStorage.SIDED.registerForBlockEntity(MetalBarrelsFabric::getStorage, ModBlockEntityTypes.SILVER);
        ItemStorage.SIDED.registerForBlockEntity(MetalBarrelsFabric::getStorage, ModBlockEntityTypes.GOLD);
        ItemStorage.SIDED.registerForBlockEntity(MetalBarrelsFabric::getStorage, ModBlockEntityTypes.DIAMOND);
        ItemStorage.SIDED.registerForBlockEntity(MetalBarrelsFabric::getStorage, ModBlockEntityTypes.NETHERITE);

        // This method is invoked by the Fabric mod loader when it is ready
        // to load your mod. You can access Fabric and Common code in this
        // project.

        // Use Fabric to bootstrap the Common mod.
        MetalBarrels.init();
        MetalBarrels.register();
    }

    //item api


    public static Storage<ItemVariant> getStorage(MetalBarrelBlockEntity blockEntity,Direction direction) {
        return (Storage<ItemVariant>) blockEntity.resourceHandler;
    }
}
