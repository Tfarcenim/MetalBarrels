package tfar.metalbarrels;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.transfer.v1.item.ItemStorage;
import net.minecraft.world.level.block.entity.BlockEntityType;
import tfar.metalbarrels.blockentity.MetalBarrelBlockEntityFabric;
import tfar.metalbarrels.init.ModBlockEntityTypes;

public class MetalBarrelsFabric implements ModInitializer {
    
    @Override
    public void onInitialize() {
        ItemStorage.SIDED.registerForBlockEntity(MetalBarrelBlockEntityFabric::getStorage, (BlockEntityType<MetalBarrelBlockEntityFabric>)
                (Object) ModBlockEntityTypes.COPPER);

        // This method is invoked by the Fabric mod loader when it is ready
        // to load your mod. You can access Fabric and Common code in this
        // project.

        // Use Fabric to bootstrap the Common mod.
        MetalBarrels.init();
    }
}
