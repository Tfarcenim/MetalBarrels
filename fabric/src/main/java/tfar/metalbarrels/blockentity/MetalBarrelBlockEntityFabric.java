package tfar.metalbarrels.blockentity;

import net.fabricmc.fabric.api.transfer.v1.item.InventoryStorage;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import tfar.metalbarrels.util.BarrelHandlerFabric;

public class MetalBarrelBlockEntityFabric extends MetalBarrelBlockEntity<BarrelHandlerFabric> {


    public MetalBarrelBlockEntityFabric(BlockEntityType tileEntityType, BlockPos pos, BlockState state) {
        super(tileEntityType, pos, state);
        barrelHandler = new BarrelHandlerFabric(barrelProperties.height() * barrelProperties.width(),this){
            @Override
            public void setChanged() {
                super.setChanged();
                MetalBarrelBlockEntityFabric.this.setChanged();
            }
        };
    }

    //item api

    private InventoryStorage storage;


    public InventoryStorage getStorage(Direction direction) {
        if (storage == null) {
            storage = InventoryStorage.of(barrelHandler,null);
        }
        return storage;
    }

    @Override
    public int calculateRedstone() {
        return AbstractContainerMenu.getRedstoneSignalFromContainer(barrelHandler);
    }
}
