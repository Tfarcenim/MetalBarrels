package tfar.metalbarrels.util;

import net.minecraft.world.level.block.entity.BlockEntityType;
import tfar.metalbarrels.blockentity.MetalBarrelBlockEntity;
import tfar.metalbarrels.init.ModBlockEntityTypes;
import tfar.metalbarrels.menu.MetalBarrelMenu;
import tfar.metalbarrels.platform.Services;

public record BarrelProperties(int width, int height, BarrelMenuFactory barrelMenuFactory) {

    public static BarrelProperties copper = new BarrelProperties(5,9, MetalBarrelMenu::copperS);
    public static BarrelProperties iron = new BarrelProperties(6,9, MetalBarrelMenu::ironS);
    public static BarrelProperties silver = new BarrelProperties(8,9, MetalBarrelMenu::silverS);
    public static BarrelProperties gold = new BarrelProperties(9,9, MetalBarrelMenu::goldS);
    public static BarrelProperties diamond = new BarrelProperties(9,12, MetalBarrelMenu::diamondS);
    public static BarrelProperties crystal = new BarrelProperties(9,12, MetalBarrelMenu::diamondS);
    public static BarrelProperties netherite = new BarrelProperties(9,15, MetalBarrelMenu::netheriteS);

    public static BlockEntityType.BlockEntitySupplier<MetalBarrelBlockEntity<?>> copper() {
        return (pPos, pState) -> Services.PLATFORM.blockEntity(ModBlockEntityTypes.COPPER,pPos,pState);
    }

    public static BlockEntityType.BlockEntitySupplier<MetalBarrelBlockEntity<?>> iron() {
        return (pPos, pState) -> Services.PLATFORM.blockEntity(ModBlockEntityTypes.IRON,pPos,pState);
    }

    public static BlockEntityType.BlockEntitySupplier<MetalBarrelBlockEntity<?>> silver() {
        return (pPos, pState) -> Services.PLATFORM.blockEntity(ModBlockEntityTypes.SILVER,pPos,pState);
    }

    public static BlockEntityType.BlockEntitySupplier<MetalBarrelBlockEntity<?>> gold() {
        return (pPos, pState) -> Services.PLATFORM.blockEntity(ModBlockEntityTypes.GOLD,pPos,pState);
    }

    public static BlockEntityType.BlockEntitySupplier<MetalBarrelBlockEntity<?>> diamond() {
        return (pPos, pState) -> Services.PLATFORM.blockEntity(ModBlockEntityTypes.DIAMOND,pPos,pState);
    }

    public static BlockEntityType.BlockEntitySupplier<MetalBarrelBlockEntity<?>> crystal() {
        return (pPos, pState) -> Services.PLATFORM.blockEntity(ModBlockEntityTypes.CRYSTAL,pPos,pState);
    }

    public static BlockEntityType.BlockEntitySupplier<MetalBarrelBlockEntity<?>> netherite() {
        return (pPos, pState) -> Services.PLATFORM.blockEntity(ModBlockEntityTypes.NETHERITE,pPos,pState);
    }

}
