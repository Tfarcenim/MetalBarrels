package tfar.metalbarrels.blockentity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.items.ItemHandlerHelper;
import tfar.metalbarrels.util.BarrelHandlerForge;

public class MetalBarrelBlockEntityForge extends MetalBarrelBlockEntity<BarrelHandlerForge> {
    public MetalBarrelBlockEntityForge(BlockEntityType<?> tileEntityType, BlockPos pos, BlockState state) {
        super(tileEntityType, pos, state);
        barrelHandler = new BarrelHandlerForge(this.barrelProperties.width() * this.barrelProperties.height(),this) {
            @Override
            protected void onContentsChanged(int slot) {
                super.onContentsChanged(slot);
                setChanged();
            }
        };
    }

    @Override
    public int calculateRedstone() {
        return ItemHandlerHelper.calcRedstoneFromInventory(barrelHandler);
    }
}
