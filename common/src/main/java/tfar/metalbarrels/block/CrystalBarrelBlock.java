package tfar.metalbarrels.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import tfar.metalbarrels.blockentity.MetalBarrelBlockEntity;
import tfar.metalbarrels.util.BarrelProperties;

public class CrystalBarrelBlock extends MetalBarrelBlock {
	public CrystalBarrelBlock(Properties properties, BlockEntityType.BlockEntitySupplier<MetalBarrelBlockEntity> tileEntitySupplier, BarrelProperties barrelProperties) {
		super(properties, tileEntitySupplier,barrelProperties);
	}

	@Override
    public float getShadeBrightness(BlockState state, BlockGetter worldIn, BlockPos pos) {
		return 1.0F;
	}

	public boolean propagatesSkylightDown(BlockState state, BlockGetter reader, BlockPos pos) {
		return true;
	}

}
