package tfar.metalbarrels.blockentity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemHandlerHelper;
import tfar.metalbarrels.util.BarrelHandlerForge;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class MetalBarrelBlockEntityForge extends MetalBarrelBlockEntity<BarrelHandlerForge> {
    public MetalBarrelBlockEntityForge(BlockEntityType<?> tileEntityType, BlockPos pos, BlockState state) {
        super(tileEntityType, pos, state);
        barrelHandler = new BarrelHandlerForge(this.barrelProperties.width() * this.barrelProperties.height(),this);
    }

    protected LazyOptional<IItemHandler> optional;

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        return cap == ForgeCapabilities.ITEM_HANDLER ? optional.cast() : super.getCapability(cap, side);
    }

    @Override
    public int calculateRedstone() {
        return ItemHandlerHelper.calcRedstoneFromInventory(barrelHandler);
    }

    @Override
    public void setRemoved() {
        super.setRemoved();
        optional.invalidate();
    }

    @Override
    public void reviveCaps() {
        super.reviveCaps();
        optional = LazyOptional.of(() -> barrelHandler);
    }

}
