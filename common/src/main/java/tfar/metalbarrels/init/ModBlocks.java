package tfar.metalbarrels.init;

import tfar.metalbarrels.block.CrystalBarrelBlock;
import tfar.metalbarrels.block.MetalBarrelBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import tfar.metalbarrels.util.BarrelProperties;

public class ModBlocks {

    static Block.Properties metal = Block.Properties.of().strength(1,6).sound(SoundType.METAL).requiresCorrectToolForDrops();
    static Block.Properties obsidian = Block.Properties.of().strength(2.5f,6000).requiresCorrectToolForDrops();
    
    public static final Block COPPER_BARREL = new MetalBarrelBlock(metal, ModBlockEntityTypes.copper(), BarrelProperties.copper);
    public static final Block IRON_BARREL = new MetalBarrelBlock(metal, ModBlockEntityTypes.iron(),BarrelProperties.iron);
    public static final Block GOLD_BARREL = new MetalBarrelBlock(metal, ModBlockEntityTypes.gold(),BarrelProperties.gold);
    public static final Block DIAMOND_BARREL = new MetalBarrelBlock(metal, ModBlockEntityTypes.diamond(),BarrelProperties.diamond);
    public static final Block NETHERITE_BARREL = new MetalBarrelBlock(obsidian, ModBlockEntityTypes.netherite(),BarrelProperties.netherite);
    public static final Block OBSIDIAN_BARREL = new MetalBarrelBlock(obsidian, ModBlockEntityTypes.diamond(),BarrelProperties.diamond);
    public static final Block SILVER_BARREL = new MetalBarrelBlock(metal, ModBlockEntityTypes.silver(),BarrelProperties.silver);
    public static final Block CRYSTAL_BARREL = new CrystalBarrelBlock(metal.noOcclusion(), ModBlockEntityTypes.diamond(),BarrelProperties.diamond);
}
