package com.tfar.metalbarrels.util;

import com.mojang.datafixers.types.Type;
import net.minecraft.block.Block;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Set;
import java.util.function.Supplier;

public class MetalBarrelBlockEntityType<T extends TileEntity> extends TileEntityType<T> {

	public final int width;
	public final int height;
	public final ContainerFunction<Integer, World, BlockPos, PlayerInventory, PlayerEntity, Container> containerFunction;

	public MetalBarrelBlockEntityType(Supplier<T> factoryIn, Set<Block> validBlocksIn, Type dataFixerType, int width, int height, ContainerFunction<Integer, World, BlockPos, PlayerInventory, PlayerEntity, Container> containerFunction) {
		super(factoryIn, validBlocksIn, dataFixerType);
		this.width = width;
		this.height = height;
		this.containerFunction = containerFunction;
	}
}
