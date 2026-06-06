package tfar.metalbarrels;

import net.minecraft.world.level.block.entity.BarrelBlockEntity;
import net.neoforged.neoforge.transfer.ResourceHandler;
import net.neoforged.neoforge.transfer.item.ItemResource;
import net.neoforged.neoforge.transfer.transaction.TransactionContext;
import tfar.metalbarrels.blockentity.MetalBarrelBlockEntity;

public class BarrelResourceHandler implements ResourceHandler<ItemResource> {

    private final MetalBarrelBlockEntity barrelBlockEntity;

    BarrelResourceHandler(MetalBarrelBlockEntity barrelBlockEntity) {
        this.barrelBlockEntity = barrelBlockEntity;
    }

    public static BarrelResourceHandler of(MetalBarrelBlockEntity barrelBlockEntity) {
        return new BarrelResourceHandler(barrelBlockEntity);
    }

    @Override
    public int size() {
        return barrelBlockEntity.barrelHandler.slots();
    }

    @Override
    public ItemResource getResource(int index) {
        return null;
    }

    @Override
    public long getAmountAsLong(int index) {
        return 0;
    }

    @Override
    public long getCapacityAsLong(int index, ItemResource resource) {
        return 0;
    }

    @Override
    public boolean isValid(int index, ItemResource resource) {
        return false;
    }

    @Override
    public int insert(int index, ItemResource resource, int amount, TransactionContext transaction) {
        return 0;
    }

    @Override
    public int extract(int index, ItemResource resource, int amount, TransactionContext transaction) {
        return 0;
    }
}
