package tfar.metalbarrels.init;

import com.google.common.collect.Sets;
import net.minecraft.world.level.block.entity.BlockEntityType;
import tfar.metalbarrels.blockentity.MetalBarrelBlockEntity;
import tfar.metalbarrels.platform.Services;
import tfar.metalbarrels.util.BarrelProperties;

public class ModBlockEntityTypes {
    public static final BlockEntityType<MetalBarrelBlockEntity<?>> COPPER = new BlockEntityType<>(BarrelProperties.copper(), Sets.newHashSet(ModBlocks.COPPER_BARREL), null);
    public static final BlockEntityType<MetalBarrelBlockEntity<?>> IRON = new BlockEntityType<>(BarrelProperties.iron(), Sets.newHashSet(ModBlocks.IRON_BARREL), null);
    public static final BlockEntityType<MetalBarrelBlockEntity<?>> SILVER = new BlockEntityType<>(BarrelProperties.silver(), Sets.newHashSet(ModBlocks.SILVER_BARREL), null);
    public static final BlockEntityType<MetalBarrelBlockEntity<?>> GOLD = new BlockEntityType<>(BarrelProperties.gold(), Sets.newHashSet(ModBlocks.GOLD_BARREL),null);
    public static final BlockEntityType<MetalBarrelBlockEntity<?>> DIAMOND = new BlockEntityType<>(BarrelProperties.diamond(), Sets.newHashSet(ModBlocks.DIAMOND_BARREL,ModBlocks.OBSIDIAN_BARREL), null);
    public static final BlockEntityType<MetalBarrelBlockEntity<?>> CRYSTAL = new BlockEntityType<>(BarrelProperties.crystal(), Sets.newHashSet(ModBlocks.CRYSTAL_BARREL), null);
    public static final BlockEntityType<MetalBarrelBlockEntity<?>> NETHERITE = new BlockEntityType<>(BarrelProperties.netherite(), Sets.newHashSet(ModBlocks.NETHERITE_BARREL), null);

}
