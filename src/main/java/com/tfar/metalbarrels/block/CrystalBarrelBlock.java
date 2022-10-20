package com.tfar.metalbarrels.block;

import net.minecraft.block.BlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.function.Supplier;

import net.minecraft.block.AbstractBlock.Properties;

public class CrystalBarrelBlock extends MetalBarrelBlock {
	public CrystalBarrelBlock(Properties properties, Supplier<TileEntity> tileEntitySupplier) {
		super(properties, tileEntitySupplier);
	}

	@OnlyIn(Dist.CLIENT)
	public float getShadeBrightness(BlockState state, IBlockReader worldIn, BlockPos pos) {
		return 1.0F;
	}

	public boolean propagatesSkylightDown(BlockState state, IBlockReader reader, BlockPos pos) {
		return true;
	}

}
