package tfar.metalbarrels.util;

import com.mojang.datafixers.types.Type;
import tfar.metalbarrels.init.ModBlockEntityTypes;
import tfar.metalbarrels.tile.MetalBarrelBlockEntity;
import net.minecraft.world.level.block.Block;
import net.minecraft.data.models.blockstates.PropertyDispatch.TriFunction;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.inventory.ContainerLevelAccess;

import java.util.Set;

public class MetalBarrelBlockEntityType<T extends BlockEntity> extends BlockEntityType<T> {

	public final int width;
	public final int height;
	public final TriFunction<Integer,  Inventory, ContainerLevelAccess,AbstractContainerMenu> containerFactory;

	public MetalBarrelBlockEntityType(BlockEntityType.BlockEntitySupplier<T> factoryIn, Set<Block> validBlocksIn, Type dataFixerType, int width, int height,
																		TriFunction<Integer, Inventory, ContainerLevelAccess, AbstractContainerMenu> containerFactory) {
		super(factoryIn, validBlocksIn, dataFixerType);
		this.width = width;
		this.height = height;
		this.containerFactory = containerFactory;
	}

	public static BlockEntityType.BlockEntitySupplier<BlockEntity> copper() {
		return (pPos, pState) -> new MetalBarrelBlockEntity(ModBlockEntityTypes.COPPER,pPos,pState);
	}

	public static BlockEntityType.BlockEntitySupplier<BlockEntity> iron() {
		return (pPos, pState) -> new MetalBarrelBlockEntity(ModBlockEntityTypes.IRON,pPos,pState);
	}

	public static BlockEntityType.BlockEntitySupplier<BlockEntity> silver() {
		return (pPos, pState) -> new MetalBarrelBlockEntity(ModBlockEntityTypes.SILVER,pPos,pState);
	}

	public static BlockEntityType.BlockEntitySupplier<BlockEntity> gold() {
		return (pPos, pState) -> new MetalBarrelBlockEntity(ModBlockEntityTypes.GOLD,pPos,pState);
	}

	public static BlockEntityType.BlockEntitySupplier<BlockEntity> diamond() {
		return (pPos, pState) -> new MetalBarrelBlockEntity(ModBlockEntityTypes.DIAMOND,pPos,pState);
	}

	public static BlockEntityType.BlockEntitySupplier<BlockEntity> netherite() {
		return (pPos, pState) -> new MetalBarrelBlockEntity(ModBlockEntityTypes.NETHERITE,pPos,pState);
	}
}
