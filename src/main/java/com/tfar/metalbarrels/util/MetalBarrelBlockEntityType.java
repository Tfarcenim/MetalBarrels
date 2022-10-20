package com.tfar.metalbarrels.util;

import com.mojang.datafixers.types.Type;
import net.minecraft.world.level.block.Block;
import net.minecraft.data.models.blockstates.PropertyDispatch.TriFunction;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.inventory.ContainerLevelAccess;

import java.util.Set;
import java.util.function.Supplier;

public class MetalBarrelBlockEntityType<T extends BlockEntity> extends BlockEntityType<T> {

	public final int width;
	public final int height;
	public final TriFunction<Integer,  Inventory, ContainerLevelAccess,AbstractContainerMenu> containerFactory;

	public MetalBarrelBlockEntityType(Supplier<T> factoryIn, Set<Block> validBlocksIn, Type dataFixerType, int width, int height,
																		TriFunction<Integer, Inventory, ContainerLevelAccess, AbstractContainerMenu> containerFactory) {
		super(factoryIn, validBlocksIn, dataFixerType);
		this.width = width;
		this.height = height;
		this.containerFactory = containerFactory;
	}
}
