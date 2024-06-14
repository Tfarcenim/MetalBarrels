package tfar.metalbarrels.init;

import com.google.common.collect.Sets;
import tfar.metalbarrels.container.MetalBarrelContainer;
import tfar.metalbarrels.util.MetalBarrelBlockEntityType;
import net.minecraft.world.level.block.entity.BlockEntity;

public class ModBlockEntityTypes {
    public static final MetalBarrelBlockEntityType<BlockEntity> COPPER = new MetalBarrelBlockEntityType<>(MetalBarrelBlockEntityType.copper(),
            Sets.newHashSet(ModBlocks.COPPER_BARREL),
            null, 9, 5, MetalBarrelContainer::copperS);
    public static final MetalBarrelBlockEntityType<BlockEntity> IRON = new MetalBarrelBlockEntityType<>(MetalBarrelBlockEntityType.iron(),
            Sets.newHashSet(ModBlocks.IRON_BARREL),
            null, 9, 6, MetalBarrelContainer::ironS);
    public static final MetalBarrelBlockEntityType<BlockEntity> SILVER = new MetalBarrelBlockEntityType<>(MetalBarrelBlockEntityType.silver(),
            Sets.newHashSet(ModBlocks.SILVER_BARREL),
            null, 8, 9, MetalBarrelContainer::silverS);
    public static final MetalBarrelBlockEntityType<BlockEntity> GOLD = new MetalBarrelBlockEntityType<>(MetalBarrelBlockEntityType.gold(),
            Sets.newHashSet(ModBlocks.GOLD_BARREL),
            null, 9, 9, MetalBarrelContainer::goldS);
    public static final MetalBarrelBlockEntityType<BlockEntity> DIAMOND = new MetalBarrelBlockEntityType<>(MetalBarrelBlockEntityType.diamond(),
            Sets.newHashSet(ModBlocks.DIAMOND_BARREL,ModBlocks.OBSIDIAN_BARREL),
            null, 12, 9, MetalBarrelContainer::diamondS);
    public static final MetalBarrelBlockEntityType<BlockEntity> CRYSTAL = new MetalBarrelBlockEntityType<>(MetalBarrelBlockEntityType.diamond(),
            Sets.newHashSet(ModBlocks.CRYSTAL_BARREL),
            null, 9, 5, MetalBarrelContainer::diamondS);
    public static final MetalBarrelBlockEntityType<BlockEntity> NETHERITE = new MetalBarrelBlockEntityType<>(MetalBarrelBlockEntityType.netherite(),
            Sets.newHashSet(ModBlocks.NETHERITE_BARREL),
            null, 15, 9, MetalBarrelContainer::netheriteS);


}
