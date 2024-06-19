package tfar.metalbarrels.init;

import com.google.common.collect.Sets;
import net.minecraft.world.level.block.entity.BlockEntityType;
import tfar.metalbarrels.blockentity.MetalBarrelBlockEntity;
import tfar.metalbarrels.platform.Services;

public class ModBlockEntityTypes {
    public static final BlockEntityType<MetalBarrelBlockEntity<?>> COPPER = new BlockEntityType<>(copper(), Sets.newHashSet(ModBlocks.COPPER_BARREL), null);
    public static final BlockEntityType<MetalBarrelBlockEntity<?>> IRON = new BlockEntityType<>(iron(), Sets.newHashSet(ModBlocks.IRON_BARREL), null);
    public static final BlockEntityType<MetalBarrelBlockEntity<?>> SILVER = new BlockEntityType<>(silver(), Sets.newHashSet(ModBlocks.SILVER_BARREL), null);
    public static final BlockEntityType<MetalBarrelBlockEntity<?>> GOLD = new BlockEntityType<>(gold(), Sets.newHashSet(ModBlocks.GOLD_BARREL),null);
    public static final BlockEntityType<MetalBarrelBlockEntity<?>> DIAMOND = new BlockEntityType<>(diamond(), Sets.newHashSet(ModBlocks.DIAMOND_BARREL,ModBlocks.OBSIDIAN_BARREL), null);
    public static final BlockEntityType<MetalBarrelBlockEntity<?>> CRYSTAL = new BlockEntityType<>(diamond(), Sets.newHashSet(ModBlocks.CRYSTAL_BARREL), null);
    public static final BlockEntityType<MetalBarrelBlockEntity<?>> NETHERITE = new BlockEntityType<>(netherite(), Sets.newHashSet(ModBlocks.NETHERITE_BARREL), null);


    public static BlockEntityType.BlockEntitySupplier<MetalBarrelBlockEntity<?>> copper() {
        return (pPos, pState) -> Services.PLATFORM.createUpgrade(COPPER,pPos,pState);
    }

    public static BlockEntityType.BlockEntitySupplier<MetalBarrelBlockEntity<?>> iron() {
        return (pPos, pState) -> Services.PLATFORM.createUpgrade(IRON,pPos,pState);
    }

    public static BlockEntityType.BlockEntitySupplier<MetalBarrelBlockEntity<?>> silver() {
        return (pPos, pState) -> Services.PLATFORM.createUpgrade(SILVER,pPos,pState);
    }

    public static BlockEntityType.BlockEntitySupplier<MetalBarrelBlockEntity<?>> gold() {
        return (pPos, pState) -> Services.PLATFORM.createUpgrade(GOLD,pPos,pState);
    }

    public static BlockEntityType.BlockEntitySupplier<MetalBarrelBlockEntity<?>> diamond() {
        return (pPos, pState) -> Services.PLATFORM.createUpgrade(DIAMOND,pPos,pState);
    }

    public static BlockEntityType.BlockEntitySupplier<MetalBarrelBlockEntity<?>> netherite() {
        return (pPos, pState) -> Services.PLATFORM.createUpgrade(NETHERITE,pPos,pState);
    }
}
